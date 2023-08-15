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
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" label="表單：" :item="$v.formCase">
              <b-form-select v-model="$v.formCase.$model" :options="queryOptions.formCase">
                <template #first>
                  <option value="" disabled>請選擇</option>
                </template>
              </b-form-select
              >
            </i-form-group-check>
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" label="處理狀況：">
              <b-form-select v-model="$v.status.$model" :options="queryOptions.status">
                <template #first>
                  <option value="" disabled>請選擇</option>
                </template>
              </b-form-select
              >
            </i-form-group-check>
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" label="表單分類：">
              <b-form-select v-model="$v.formType.$model" :options="queryOptions.formTypeList">
                <template #first>
                  <option value="" disabled>請選擇</option>
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

    <section class="mt-2" v-show="queryStatus">
      <div class="container">
        <i-table
            ref="iTable"
            :itemsUndefinedBehavior="'loading'"
            :items="table.data"
            :fields="table.fields"
            :totalItems="table.totalItems"
            :is-server-side-paging="false"
        >

          <template #cell(filAndApp)="row">
            <div v-if="row.item.appEmpid === row.item.filEmpid">
              {{ row.item.appName }}
            </div>
            <div v-else>
              {{ row.item.appName }} / {{ row.item.filName }}
            </div>
          </template>

          <template #cell(subject)="row">
            <div>
              {{ changeSubject(row.item) }}
            </div>
          </template>

          <template #cell(action)="row">
            <b-button class="ml-1" v-if="userData === row.item.appName" style="background-color: #17a2b8" @click="toEdit(row.item,'0')">編輯</b-button>
            <b-button class="ml-1" v-if="userData !== 'ApplyTester'" style="background-color: #17a2b8" @click="toEdit(row.item,'1')">處理</b-button>
          </template>
        </i-table>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import {ref, reactive, computed, toRefs, defineComponent, onActivated} from '@vue/composition-api';
import IDatePicker from '../shared/i-date-picker/i-date-picker.vue';
import ITable from '../shared/i-table/i-table.vue';
import IFormGroupCheck from '../shared/form/i-form-group-check.vue';
import {useValidation, validateState} from '@/shared/form';
import {useBvModal} from '../shared/modal';
import {required} from '@/shared/validators';
import {Pagination} from "@/shared/model/pagination.model";
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import { newformatDate} from "@/shared/date/minguo-calendar-utils";
import { changeSubject} from "@/shared/word/change-word-utils";
import {useGetters} from "@u3u/vue-hooks";
import {navigateByNameAndParams} from "@/router/router";

export default defineComponent({
  name: 'pending',
  methods: {changeSubject},
  components: {
    IDatePicker,
    ITable,
    IFormGroupCheck,
  },
  setup() {
    const userData = ref(useGetters(['getUserData']).getUserData).value.user;
    const iTable = ref(null);
    const queryStatus = ref(false);
    const notificationService = useNotification();

    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY ='簽核'
    }

    onActivated(() => {
      toQuery();
    });


    function notBeforePublicDateStart(date: Date) {
      if (form.seqDate) return date < new Date(form.seqDate);
    }

    function notAfterPublicDateEnd(date: Date) {
      if (form.seqDateEnd) return date > new Date(form.seqDateEnd);
    }

    const formDefault = {
      formCase: '', //表單
      status: '', //處理狀態
      formType: '', //表單分類
      seqDate: undefined, //起
      seqDateEnd: undefined, //迄
    };

    const form = reactive(Object.assign({}, formDefault));

    // 表單物件驗證規則
    const rules = ref({
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
          label: '動作',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'applyDate',
          label: '收件日期',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : newformatDate(new Date(value), '/')),
        },
        {
          key: 'filAndApp',
          label: '申請者/填表人',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'subject',
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
      formTypeList: [
        {value: '0', text: 'ISMS簽核表單'},
      ],
    });

    const toQuery = () => {
      console.log('userData',userData)
      table.data = [];
      axios.post(`/process/queryTask/${userData}`).then(({data}) => {
        console.log('data+++', data);
        queryStatus.value = true;
        if(data.length <= 0) return;
        table.data = data.slice(0, data.length);
        table.totalItems = data.length;
      });
    };

    function toEdit(item,i) {
      console.log('item',item.appName)
      console.log('userData',userData)

      if (i === '0') {
        navigateByNameAndParams('l414Edit', {
          l414Data: item,
          formStatus: FormStatusEnum.MODIFY,
          isNotKeepAlive: false
        });
      }else {
        navigateByNameAndParams('l414Edit', {
          l414Data: item,
          formStatus: FormStatusEnum.VERIFY,
          isNotKeepAlive: false
        });
      }

    }

    return {
      $v,
      form,
      checkValidity,
      validateState,
      toQuery,
      reset,
      table,
      queryOptions,
      iTable,
      notBeforePublicDateStart,
      notAfterPublicDateEnd,
      toEdit,
      queryStatus,
      userData,
    };
  },
});
</script>

<style scoped>
</style>

