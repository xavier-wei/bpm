<template>
  <div class="b-form-btn-label-control form-control" :class="formState">
    <date-picker
      class="col-12 p-0"
      :placeholder="$attrs.placeholder ? $attrs.placeholder : 'yyy/MM'"
      v-model="date"
      :lang="langAttr"
      :formatter="dateFormat"
      v-bind="$attrs"
      v-on="$listeners"
    >
      <template v-if="!isRange" v-slot:header="{ emit }">
        <div class="w-100 text-center">
          <button class="mx-btn mx-btn-text" @click="emit(new Date())">今日</button>
        </div>
      </template>
    </date-picker>
  </div>
</template>

<script>
import { ref, computed, reactive, watch } from '@vue/composition-api';
import { LANG } from './lang';
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';
import { formatDate, parseRocDate } from '@/shared/date/minguo-calendar-utils';
import { validateState } from '@/shared/form';

export default {
  name: 'mrp-date-picker',
  components: {
    DatePicker,
  },
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
  },
  model: {
    prop: 'value',
    event: 'update',
  },
  setup(props, context) {
    const date = ref(props.value);
    watch(
      computed(() => props.value),
      newValue => {
        date.value = newValue;
      }
    );
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

    const dateFormat = reactive({
      stringify: original => {
        const dateString = formatDate(original, '/');
        switch (displayType.value) {
          case 'month':
            return dateString.substring(0, dateString.lastIndexOf('/'));
          case 'year':
            return dateString.substring(0, dateString.indexOf('/'));
          default:
            return dateString.slice(0, 6);
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

    function handleTimeZoneOffset(value) {
      const offset = new Date().getTimezoneOffset();
      if (value) {
        date.value = new Date(date.value.getTime() - offset * 60 * 1000);
      }
    }

    function setMonthValue(value) {
      if (value) {
        date.value = parseRocDate(value + '/01');
      }
    }

    watch(date, () => {
      // 判斷與父元件值不同再emit，避免清除時emit又觸發父元件值的$touch
      if (date.value !== props.value) {
        context.emit('update', date.value);
      }
    });

    return { langAttr, date, isRange, dateFormat, formState, validateState, handleTimeZoneOffset, setMonthValue };
  },
};
</script>
<style>
.b-form-btn-label-control .form-control {
  border: none !important;
}

.mx-calendar-header-label {
  display: flex;
  position: absolute;
  margin: 0px 65px;
  justify-content: center;
}
</style>
