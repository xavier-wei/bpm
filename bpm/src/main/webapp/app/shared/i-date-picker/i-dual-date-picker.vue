<template>
  <b-input-group v-if="!isVertical">
    <i-dual-date-child-picker v-model="innerDual1" :compareData="fromDataObj" :disabled-date="notAfterDateEnd" :disabled="disabled" />
    <b-input-group-text aria-label="日期">至</b-input-group-text>
    <i-dual-date-child-picker v-model="innerDual2" :compareData="toDataObj" :disabled-date="notBeforeDateStart" :disabled="disabled" />
  </b-input-group>
  <div v-else>
    <i-dual-date-child-picker v-model="innerDual1" :compareData="fromDataObj" :disabled-date="notAfterDateEnd" :disabled="disabled" />
    至
    <i-dual-date-child-picker v-model="innerDual2" :compareData="toDataObj" :disabled-date="notBeforeDateStart" :disabled="disabled" />
  </div>
</template>

<script>
import IDualDateChildPicker from '@/shared/i-date-picker/i-dual-date-child-picker.vue';
import { ref, toRefs, watch, reactive } from '@vue/composition-api';
import { BInputGroup, BInputGroupText } from 'bootstrap-vue';
import { disabledDateFrom, disabledDateTo } from '@/shared/i-date-picker/share-func';

export default {
  name: 'i-dual-date-picker',
  components: { IDualDateChildPicker, BInputGroup, BInputGroupText },
  // props: ['dual1', 'dual2', 'disabled'],
  props: {
    dual1: {
      required: true,
    },
    dual2: {
      required: true,
    },
    disabled: {
      type: Boolean,
      required: false,
      default: false,
    },
    isVertical: {
      type: Boolean,
      required: false,
      default: false,
    },
  },
  setup(props, context) {
    const { dual1, dual2 } = toRefs(props);
    const innerDual1 = ref(dual1.value);
    const innerDual2 = ref(dual2.value);

    const fromDataObj = reactive({ fromKey: true, toKey: false, value: innerDual2.value });
    const toDataObj = reactive({ fromKey: false, toKey: true, value: innerDual1.value });

    const updateInnerDual1 = value => {
      innerDual1.value = value;
      toDataObj.value = value;
    };
    const updateInnerDual2 = value => {
      innerDual2.value = value;
      fromDataObj.value = value;
    };

    const updateOuterDual1 = value => {
      if (dual1.value !== innerDual1.value) {
        context.emit('update:dual1', value);
        context.emit('change', value, innerDual2.value);
      }
    };
    const updateOuterDual2 = value => {
      if (dual2.value !== innerDual2.value) {
        context.emit('update:dual2', value);
        context.emit('change', innerDual1.value, value);
      }
    };

    const notAfterDateEnd = date => {
      return disabledDateFrom(date, innerDual2.value);
    };
    const notBeforeDateStart = date => {
      return disabledDateTo(date, innerDual1.value);
    };

    watch(dual1, updateInnerDual1);
    watch(dual2, updateInnerDual2);
    watch(innerDual1, updateOuterDual1);
    watch(innerDual2, updateOuterDual2);

    return {
      innerDual1,
      innerDual2,
      notBeforeDateStart,
      notAfterDateEnd,
      fromDataObj,
      toDataObj,
    };
  },
};
</script>
<style></style>
