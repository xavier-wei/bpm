<template>
  <div>
    <section class="container mt-2">
      <div class="card">
        <div class="card-header py-1 text-left" style="background-color: #b0ded4">
          <div class="row align-items-center">
            <div class="col-sm-11 p-0">
              <h5 class="m-0">
                <font-awesome-icon icon="search"/>
                查詢條件
              </h5>
            </div>
          </div>
        </div>
        <div class="card-body clo-12" style="background-color: #d3ede8">
          <b-form-row>
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" :label="'部門：'" :item="$v.dept">
              <b-form-select v-model="$v.dept.$model">
                <template #first>
                  <option value="">請選擇</option>
                </template>
              </b-form-select>
            </i-form-group-check>

            <i-form-group-check class="col-4" label-cols="4" content-cols="8" :label="`申請者：`" :item="$v.createUser">
              <b-form-select v-model="$v.createUser.$model"
              >
                <template #first>
                  <option value="">請選擇</option>
                </template>
              </b-form-select>
            </i-form-group-check>
          </b-form-row>
          <b-form-row>
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" label="表單：" :item="$v.formCase">
              <b-form-select v-model="$v.formCase.$model" :options="queryOptions.formCase">
                <template #first>
                  <option value="">請選擇</option>
                </template>
              </b-form-select
              >
            </i-form-group-check>
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" label="處理狀況：" :item="$v.status">
              <b-form-select v-model="$v.status.$model" :options="queryOptions.status">
                <template #first>
                  <option value="">請選擇</option>
                </template>
              </b-form-select
              >
            </i-form-group-check>
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" label="表單分類：" :item="$v.formType">
              <b-form-select v-model="$v.formType.$model">
                <template #first>
                  <option value="">請選擇</option>
                </template>
              </b-form-select
              >
            </i-form-group-check>
          </b-form-row>
          <!-- 填表日期 -->
          <b-form-row>
            <i-form-group-check :label="'期間：'" class="col-8" label-cols="2" content-cols="6" :dual1="$v.seqDate"
                                :dual2="$v.seqDateEnd">
              <b-input-group>
                <i-date-picker v-model="$v.seqDate.$model" placeholder="yyy/MM/dd"
                               :disabled-date="notAfterPublicDateEnd"></i-date-picker>
                <b-input-group-text>至</b-input-group-text>
                <i-date-picker
                  v-model="$v.seqDateEnd.$model"
                  placeholder="yyy/MM/dd"
                  :disabled-date="notBeforePublicDateStart"
                ></i-date-picker>
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
            <b-button class="ml-2" style="background-color: #17a2b8" @click="toEdit(row)">檢視</b-button>
          </template>
        </i-table>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import {ref, reactive, computed, toRefs, defineComponent} from '@vue/composition-api';
import IDatePicker from '../shared/i-date-picker/i-date-picker.vue';
import ITable from '../shared/i-table/i-table.vue';
import IFormGroupCheck from '../shared/form/i-form-group-check.vue';
import {useValidation, validateState} from '../shared/form';
import {useBvModal} from '../shared/modal';
import {required} from '@/shared/validators';
import { Pagination } from '@/shared/model/pagination.model';
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

    function notBeforePublicDateStart(date: Date) {
      if (form.seqDate) return date < new Date(form.seqDate);
    }

    function notAfterPublicDateEnd(date: Date) {
      if (form.seqDateEnd) return date > new Date(form.seqDateEnd);
    }

    const formDefault = {
      dept: '', //部門
      createUser: '', //申請者
      formCase: '', //表單
      status: '', //處理狀態
      formType: '', //表單分類
      seqDate: undefined, //起
      seqDateEnd: undefined, //迄
    };

    const form = reactive(Object.assign({}, formDefault));

    // 表單物件驗證規則
    const rules = ref({
      dept: {},
      createUser: {},
      formCase: {},
      status: {notnull: required},
      formType: {},
      seqDate: {},
      seqDateEnd: {},
    });

    const {$v, checkValidity, reset} = useValidation(rules, form, formDefault);

    const table = reactive({
      fields: [
        {
          key: 'action',
          label: '',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'index',
          label: '申請者/填表人',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'hostname',
          label: '申請日期',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'port',
          label: '目前處理單位',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'active2',
          label: '處理狀況',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'active3',
          label: '主旨',
          sortable: false,
          thStyle: 'width:40%',
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
      status: [
        {value: '0', text: '申請'},
        {value: '1', text: '處理中'},
        {value: '2', text: '處理過'},
      ],
      formCase: [
        {value: '0', text: 'L410-共用系統使用者帳號申請單'},
        {value: '1', text: 'L414-網路服務連結申請單'},
      ],
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

    const handlePaginationChanged = (pagination: Pagination) => {
      //todo:未做方法先放著
    };

    function toEdit(i) {
      //todo:未做方法先放著
    }

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
      notBeforePublicDateStart,
      notAfterPublicDateEnd,
      toEdit,
      handlePaginationChanged,
    };
  },
});
</script>

<style scoped>
</style>

