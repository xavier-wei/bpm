<template>
  <div>
    <section class="container mt-2">
      <div class="card">
        <div class="card-header py-1 text-left" style="background-color: #008b8b">
          <div class="row align-items-center">
            <div class="col-sm-11 p-0">
              <h5 class="m-0">
                <font-awesome-icon icon="search" />
                查詢條件
              </h5>
            </div>
          </div>
        </div>
        <div class="card-body clo-12" style="background-color: #8fd4ce">
          <b-form-row>
            <i-form-group-check   class="col-4" label-cols="4" content-cols="8" :label="'部門：'" :item="$v.deaprtmant">
              <b-form-select v-model="$v.deaprtmant.$model" ><template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              ></b-form-select>
            </i-form-group-check>

            <i-form-group-check  class="col-4" label-cols="4" content-cols="8" :label="`申請者：`" :item="$v.usrMail">
              <b-form-select v-model="$v.usrMail.$model" ><template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              ></b-form-select>
            </i-form-group-check>
          </b-form-row>
          <!-- <b-form-row>
            <b-form-group class="col-4" label-cols="4" content-cols="8" label="部門:">
              <b-form-select v-model="$v.deaprtmant.$moddel">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </b-form-group>
            <b-form-group class="col-4" label-cols="4" content-cols="8" label="申請者：">
              <b-form-select v-model="$v.usrMail.$model">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </b-form-group>
          </b-form-row> -->

          <b-form-row>
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" label="表單:">
              <b-form-select v-model="$v.seqNo.$model">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </i-form-group-check>
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" label="處理狀況：">
              <b-form-select v-model="$v.status.$model" :options="queryOptions.city">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </i-form-group-check>
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" label="表單分類：">
              <b-form-select v-model="$v.usrMail.$model">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </i-form-group-check>
          </b-form-row>
          <!-- 填表日期 -->
          <b-form-row>
            <i-form-group-check
              :label="'期間:'"
               class="col-4" 
               label-cols="4"
              content-cols="8"
              :dual1="$v.seqDate.$model"
              :dual2="$v.seqDateEnd.$model"
            >
              <b-input-group>
                <i-date-picker v-model="$v.seqDate" placeholder="yyy/MM/dd"></i-date-picker>
                <b-input-group-text>至</b-input-group-text>
                <i-date-picker v-model="$v.seqDateEnd" placeholder="yyy/MM/dd"></i-date-picker>
              </b-input-group>
            </i-form-group-check>
          </b-form-row>

          <div class="text-center pt-5">
            <b-button class="ml-2" style="background-color: #17a2b8" @click="toQuery()">查詢</b-button>
            <b-button class="ml-2" style="background-color: #17a2b8" @click="reset()">清除</b-button>
          </div>
        </div>
      </div>
    </section>

    <section class="mt-2">
      <div class="container">
        <i-table
          ref="iTable"
          :itemsUndefinedBehavior="'loading'"
          :items="mockdata"
          :fields="table.fields"
          :totalItems="table.totalItems"
          :is-server-side-paging="true"
          @changePagination="handlePaginationChanged($event)"
        >
          <template #cell(action)="row">
            <b-button class="ml-2" style="background-color: #17a2b8" @click="toEdit(row.item)">處理</b-button>
          </template>
          <template #cell(action2)="row">
            <b-button class="ml-2" style="background-color: #17a2b8" @click="toEdit(row.item)">刪除</b-button>
          </template>
        </i-table>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import { ref, reactive, computed, toRefs, defineComponent } from '@vue/composition-api';
import IDatePicker from '../shared/i-date-picker/i-date-picker.vue';
import ITable from '../shared/i-table/i-table.vue';
import IFormGroupCheck from '../shared/form/i-form-group-check.vue';
import { useValidation, validateState } from '../shared/form';
import { useBvModal } from '../shared/modal';
import { required } from '../shared/validators';

export default defineComponent({
  name: 'deal2',
  components: {
    IDatePicker,
    ITable,
    IFormGroupCheck,
  },
  setup() {
    const iTable = ref(null);
    const stepVisible = ref(true);
    const $bvModal = useBvModal();

    const formDefault = {
      deaprtmant: '',
      result: 'N',
      seqNo: '',
      usrMail: '',
      usrMaster: '',
      status: '',
      seqDate: undefined,
      seqDateEnd: undefined,
    };

    const form = reactive(Object.assign({}, formDefault));

    // 表單物件驗證規則
    const rules = ref({
      deaprtmant: {},
      result: {},
      seqNo: {},
      usrMail: {},
      usrMaster: {},
      seqDate: {},
      seqDateEnd: {},
      status: { notnull: required },
    });

    const { $v, checkValidity, reset } = useValidation(rules, form, formDefault);

    const table = reactive({
      fields: [
        {
          key: 'action',
          label: '',
          sortable: false,
          thStyle: 'width:20%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'action2',
          label: '',
          sortable: false,
          thStyle: 'width:20%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'index',
          label: '申請者/填表人',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'hostname',
          label: '申請日期',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'port',
          label: '目前處理單位',
          sortable: false,
          thStyle: 'width:20%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'active2',
          label: '處理狀況',
          sortable: false,
          thStyle: 'width:20%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'active3',
          label: '主旨',
          sortable: false,
          thStyle: 'width:20%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
      ],
      data: [],
      totalItems: 5,
    });

    const mockdata = [
      {
        action: '4,02.03.04',
        index: '其他材料檢驗審查紀錄缺失',
        id: '14',
        hostname: '77.78%',
        port: '0',
        active2: '0',
        active3: '14',
      },
      {
        action: '4,02.03.04',
        index: '其他材料檢驗審查紀錄缺失',
        id: '14',
        hostname: '77.78%',
        port: '0',
        active2: '0',
        active3: '14',
      },
      {
        action: '4,02.03.04',
        index: '其他材料檢驗審查紀錄缺失',
        id: '14',
        hostname: '77.78%',
        port: '0',
        active2: '0',
        active3: '14',
      },
      {
        action: '4,02.03.04',
        index: '其他材料檢驗審查紀錄缺失',
        id: '14',
        hostname: '77.78%',
        port: '0',
        active2: '0',
        active3: '14',
      },
      {
        action: '4,02.03.04',
        index: '其他材料檢驗審查紀錄缺失',
        id: '14',
        hostname: '77.78%',
        port: '0',
        active2: '0',
        active3: '14',
      },
    ];

    // 下拉選單選項
    const queryOptions = reactive({
      city: [
        { value: '0', text: '申請' },
        { value: '1', text: '處理中' },
        { value: '2', text: '處理過' },
      ],
      city1: [],
    });

    const toQuery = () => {
      stepVisible.value = true;
      checkValidity().then((isValid: boolean) => {
        if (isValid) {
          $bvModal.msgBoxConfirm('是否確認送出修改內容？').then((isOK: boolean) => {
            if (isOK) {
              console.log('form', form);
            }
          });
        } else {
          $bvModal.msgBoxOk('欄位尚未填寫完畢，請於輸入完畢後再行送出。');
        }
      });
      // axios;
      // .post('/find/iwgHosts', formDefault)
      // .then(data => {
      //   // ele.forEach((e) => {});
      //   table.data = data.data;
      // })
      // .catch(error => {
      //   console.log('catch', error);
      // });

      table.data = [];
      table.totalItems = 1;
      table.data.splice(0, table.data.length, ...mockdata);
    };

    return {
      $v,
      form,
      checkValidity,
      validateState,
      stepVisible,
      toQuery,
      reset,
      table,
      mockdata,
      queryOptions,
      iTable,
    };
  },
});
</script>

<style scoped>
</style>

