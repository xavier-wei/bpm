<template>
  <div class="b-form-btn-label-control form-control" :class="formState">
    <date-picker
      class="col-12 p-0"
      v-bind="$attrs"
      v-on="$listeners"
      v-model="date"
      :placeholder="$attrs.placeholder ? $attrs.placeholder : 'yyy/MM/dd'"
      :lang="langAttr"
      :formatter="dateFormat"
      :disabled-date="$attrs['disabled-date'] ? $attrs['disabled-date'] : notBeforeDateStart"
      @input="handleTimeZoneOffset($event)"
      @calendar-change="calendarChange($event)"
      @panel-change="panelChange($event)"
      @open="handleOpen($event)"
    >
      <template v-if="!isRange" v-slot:header="{ emit }">
        <div class="w-100 text-center">
          <button class="mx-btn mx-btn-text" @click="emit(getCurrentDate())">今日</button>
        </div>
      </template>
    </date-picker>
  </div>
</template>

<script>
import {formatDate, parseRocDate} from '../date/minguo-calendar-utils';
import {validateState} from '@/shared/form';
import {computed, onMounted, reactive, ref, watch} from '@vue/composition-api';
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';
import {LANG} from './lang';
import {includes as _includes, keys as _keys, trim as _trim} from 'lodash';
import {dealWithMonthPanel, dealWithYearPanel, disabledDateFrom, disabledDateTo} from './share-func';
import $ from 'jquery';

export default {
  name: 'i-date-picker',
  components: { DatePicker },
  props: {
    lang: {
      required: false,
      default: () => LANG,
    },
    value: {},
    state: {
      required: false,
      default: null,
    },
    dateData: {
      type: Object,
      required: false,
      default: () => ({}),
    },
    cFromKey: {
      type: String,
      required: false,
      default: '',
    },
    cToKey: {
      type: String,
      required: false,
      default: '',
    },
  },
  model: {
    prop: 'value',
    event: 'update',
  },
  setup(props, context) {
    onMounted(() => {
      //DatePicker 原生會有input框,賦予屬屬性
      $('.mx-input-wrapper>input').attr('aria-label', '日期');
    });
    const date = ref(props.value);
    const currentPanel = ref(_includes(_keys(context.attrs), 'type') ? context.attrs.type : 'date');
    const panelDate = ref(props.value);
    const isTypeYear = ref(context.attrs.type === 'year');
    const langAttr = computed(() => props.lang);
    const isRange = computed(() => context.attrs.range !== undefined);
    const displayType = computed(() => context.attrs.type);
    const formState = computed(() => {
      const isValid = props.state ? validateState(props.state) : null;
      if (isValid === null || isValid) {
        return '';
      } else {
        return 'is-invalid';
      }
    });
    const cFromKeyProp = ref(props.cFromKey);
    const cToKeyProp = ref(props.cToKey);
    const dateDataProp = ref(props.dateData);
    const isFromKey = _includes(_keys(dateDataProp.value), cFromKeyProp.value) && _trim(cFromKeyProp.value) !== '';
    const isToKey = _includes(_keys(dateDataProp.value), cToKeyProp.value) && _trim(cToKeyProp.value) !== '';
    const mingGuoLimitDate = ref('1911-12-31');

    const dateFormat = reactive({
      stringify: original => {
        const dateString = formatDate(original, '/');
        switch (displayType.value) {
          case 'month':
            return dateString.substring(0, dateString.lastIndexOf('/'));
          case 'year':
            return dateString.substring(0, dateString.indexOf('/'));
          default:
            return dateString;
        }
      },
      parse: original => {
        const hasDelimiter = original.indexOf('/') > 0;
        switch (displayType.value) {
          case 'month':
            return parseRocDate(original + (hasDelimiter ? '/01' : '01'));
          case 'year':
            return parseRocDate(original + (hasDelimiter ? '/01/01' : '0101'));
          default:
            return parseRocDate(original);
        }
      },
    });

    function getCurrentDate() {
      const date = new Date();
      return new Date(date.getFullYear(), date.getMonth(), date.getDate());
    }

    function handleTimeZoneOffset(value) {
      const offset = new Date().getTimezoneOffset();
      if (value) {
        if (isTypeYear.value && new Date(value).getFullYear() < 1912)
          date.value = new Date(new Date('1912-01-01').getTime() - offset * 60 * 1000);
        else date.value = new Date(date.value.getTime() - offset * 60 * 1000);
      }
    }

    const notBeforeDateStart = nDate => {
      if (isTypeYear.value) return;
      else if (isFromKey) return disabledDateFrom(nDate, dateDataProp.value[cFromKeyProp.value]);
      else if (isToKey) return disabledDateTo(nDate, dateDataProp.value[cToKeyProp.value]);
      else if (isFromKey && isToKey) return;
      return nDate <= new Date(new Date('1911-12-31').setHours(0, 0, 0, 0));
    };

    const calendarChange = cDate => {
      panelDate.value = cDate;
      dealWithChange(currentPanel.value);
    };

    const panelChange = type => {
      currentPanel.value = type;
      dealWithChange(currentPanel.value);
    };

    const handleOpen = () => {
      dealWithChange(currentPanel.value);
    };

    const dealWithChange = panel => {
      const originCValue = dateDataProp.value[isFromKey ? cFromKeyProp.value : cToKeyProp.value];
      setTimeout(() => {
        if (panel === 'year') dealWithYearPanel('.mx-table-year td', originCValue, isFromKey, isToKey);
        else if (panel === 'month') dealWithMonthPanel('.mx-table-month td', originCValue, panelDate.value, isFromKey, isToKey);
      }, 10);
    };

    watch(
      computed(() => props.value),
      newValue => {
        date.value = newValue;
        if (_trim(newValue) === '') panelDate.value = null;
      }
    );

    watch(date, () => {
      // 判斷與父元件值不同再emit，避免清除時emit又觸發父元件值的$touch
      if (date.value !== props.value) {
        context.emit('update', date.value);
      }
    });

    return {
      langAttr,
      date,
      isRange,
      dateFormat,
      formState,
      validateState,
      handleTimeZoneOffset,
      getCurrentDate,
      notBeforeDateStart,
      calendarChange,
      panelChange,
      handleOpen,
    };
  },
};
</script>
<style>
.b-form-btn-label-control .form-control {
  border: none !important;
}
</style>
