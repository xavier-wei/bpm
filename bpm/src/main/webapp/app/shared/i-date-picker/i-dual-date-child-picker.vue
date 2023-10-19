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
      @input="handleTimeZoneOffset($event)"
      @calendar-change="calendarChange($event)"
      @panel-change="panelChange($event)"
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
import { formatDate, parseRocDate } from '@/shared/date/minguo-calendar-utils';
import { validateState } from '@/shared/form';
import { computed, reactive, ref, watch, onMounted } from '@vue/composition-api';
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';
import { LANG } from './lang';
import { includes as _includes, keys as _keys, trim as _trim, isDate as _isDate, forEach as _forEach, replace as _replace } from 'lodash';
import $ from 'jquery';
import { dealWithYearPanel, dealWithMonthPanel } from '@/shared/i-date-picker/share-func';

export default {
  name: 'i-dual-date-child-picker',
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
    compareData: { type: Object, required: false, default: () => {} },
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
    const compareDataProp = reactive(props.compareData);
    const currentPanel = ref(_includes(_keys(context.attrs), 'type') ? context.attrs.type : 'date');
    const panelDate = ref(props.value);
    const langAttr = computed(() => props.lang);
    const isRange = computed(() => context.attrs.range !== undefined);
    const displayType = computed(() => context.attrs.type);
    const isFromKey = compareDataProp.fromKey && !compareDataProp.toKey;
    const isToKey = compareDataProp.toKey && !compareDataProp.fromKey;
    const formState = computed(() => {
      const isValid = props.state ? validateState(props.state) : null;
      if (isValid === null || isValid) {
        return '';
      } else {
        return 'is-invalid';
      }
    });
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
      return new Date(date.getFullYear(), date.getMonth(), date.getDate(), new Date().getHours(), new Date().getMinutes(), new Date().getSeconds(), new Date().getMilliseconds());
    }

    function handleTimeZoneOffset(value) {
      const offset = new Date().getTimezoneOffset();
      if (value) {
        const date1 = new Date(value);
        date.value = new Date(date1.getFullYear(), date1.getMonth(), date1.getDate(), new Date().getHours(), new Date().getMinutes(), new Date().getSeconds(), new Date().getMilliseconds());
        // date.value = new Date(date.value.getTime() - offset * 60 * 1000);
      }
    }

    const calendarChange = cDate => {
      panelDate.value = cDate;
      dealWithChange(currentPanel.value);
    };

    const panelChange = type => {
      currentPanel.value = type;
      dealWithChange(currentPanel.value);
    };

    const dealWithChange = panel => {
      setTimeout(() => {
        if (panel === 'year') dealWithYearPanel('.mx-table-year td', compareDataProp.value, isFromKey, isToKey);
        else if (panel === 'month') dealWithMonthPanel('.mx-table-month td', compareDataProp.value, panelDate.value, isFromKey, isToKey);
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
      calendarChange,
      panelChange,
    };
  },
};
</script>
<style>
.b-form-btn-label-control .form-control {
  border: none !important;
}
</style>
