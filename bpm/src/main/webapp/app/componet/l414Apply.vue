<template>
  <div>

    <div style="background-color: #8fd4ce; height: 2000px">

      <div class="belt">
        <b-row class=" d-flex">
          <p class="ml-3" style="color: white">
            L414-網路服務連結申請單
          </p>

          <P class="ml-3">機密等級： 敏感</P>
        </b-row>
      </div>

      <section class="container mt-2" >
        <div class="card" style="background-color: #8fd4ce">

          <b-form-row>
            <i-form-group-check
              label-cols="5"
              content-cols="7"
              :label="'申請日期:'"
              :item="[$v.startOpenDate, $v.endOpenDate]"
            >
              <i-dual-date-picker :dual1.sync="$v.startOpenDate.$model" :dual2.sync="$v.endOpenDate.$model"/>
            </i-form-group-check>

            <i-form-group-check :label="`結訓日期：`" :item="[$v.startCloseDate, $v.endCloseDate]">
              <b-form-input v-model="$v.openType.$model"/>
            </i-form-group-check>
          </b-form-row>
        </div>
      </section>

    </div>

  </div>
</template>

<script lang="ts">

import {BRow, BFormRow,BFormInput} from 'bootstrap-vue';
import IDualDatePicker from '@/shared/i-date-picker/i-dual-date-picker.vue';
import {reactive} from '@vue/composition-api';
import {useValidation} from '@/shared/form';
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';

export default {
  name: "l414Apply",
  components: {
    'b-row': BRow,
    'i-form-group-check': IFormGroupCheck,
    'i-dual-date-picker': IDualDatePicker,
    'b-form-row': BFormRow,
    'b-form-input': BFormInput,
  },
  setup() {

    const formDefault = {
      startOpenDate: new Date(new Date().getFullYear(), 0, 1),
      endOpenDate: new Date(new Date().getFullYear(), 11, 31),
      className: '',
      reviewStatus2: '',
      unitIsValid: 'Y',
      qtClassIsValid: '',
      qtyCodeList: [],
      qtClassKind: '',
      timeSession: '',
      openType: '',
      projectState: '',
      startCloseDate: null,
      endCloseDate: null,
      projectCodes: [],
    };
    const form = reactive(Object.assign({}, formDefault));
    const rules = {
      startOpenDate: {},
      endOpenDate: {},
      className: {},
      reviewStatus2: {},
      unitIsValid: {},
      qtClassIsValid: {},
      qtyCodeList: {},
      qtClassKind: {},
      timeSession: {},
      openType: {},
      projectState: {},
      startCloseDate: {},
      endCloseDate: {},
      projectCodes: {},
    };
    const {$v, checkValidity, reset} = useValidation(rules, form, formDefault);

    return {
      $v,
      form,
      checkValidity,
    }
  }
}
</script>

<style scoped lang="scss">
.belt {
  background-color: #008b8b;
  padding-top: 10px;
}
</style>
