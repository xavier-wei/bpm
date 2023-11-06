<template>
  <div>
    <user-sys></user-sys>
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
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="4"
          content-cols-md="8"
          label-align-md="right"
          :label="'部門：'"
          :item="$v.unit"
        >
          <b-form-select v-model="$v.unit.$model" :options="bpmDeptsOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>

        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="4"
          content-cols-md="8"
          label-align-md="right"
          :label="`申請者：`" :item="$v.appName"
        >
          <b-form-select v-model="$v.appName.$model" :options="queryOptions.peunitOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>
      </b-form-row>
      <b-form-row>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="4"
          content-cols-md="8"
          label-align-md="right"
          label="表單："
          :item="$v.formId"
        >
          <b-form-select v-model="$v.formId.$model" :options="queryOptions.formCase">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="4"
          content-cols-md="8"
          label-align-md="right"
          label="處理狀況："
          :item="$v.processInstanceStatus"
        >
          <b-form-select v-model="$v.processInstanceStatus.$model" :options="queryOptions.status">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="4"
          content-cols-md="8"
          label-align-md="right" label="表單分類：" :item="$v.formType"
        >
          <b-form-select v-model="$v.formType.$model" :options="queryOptions.formTypeList">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>
      </b-form-row>
      <!-- 填表日期 -->
      <b-form-row>
        <i-form-group-check
          :label="'期間：'"
          class="col-sm-4 mb-0"
          label-cols-md="4"
          content-cols-md="8"
          label-align-md="right"
          :dual1="$v.dateStart"
          :dual2="$v.dateEnd"
        >
          <b-input-group>
            <i-date-picker v-model="$v.dateStart.$model" placeholder="yyy/MM/dd"
                           :disabled-date="notAfterPublicDateEnd"></i-date-picker>
            <b-input-group-text>至</b-input-group-text>
            <i-date-picker
              v-model="$v.dateEnd.$model"
              placeholder="yyy/MM/dd"
              :disabled-date="notBeforePublicDateStart"
            ></i-date-picker>
          </b-input-group>
        </i-form-group-check>
      </b-form-row>

      <div class="text-center pt-5">
        <b-button class="ml-2" style="background-color: #17a2b8" @click="toQuery()">查詢</b-button>
        <b-button class="ml-2" style="background-color: #17a2b8" @click="toReset()">清除</b-button>
      </div>
    </div>

    <i-table
      ref="iTable"
      stacked="sm"
      striped
      class="test-table table-sm table-hover"
      :itemsUndefinedBehavior="'loading'"
      :items="table.data"
      :fields="table.fields"
      :totalItems="table.totalItems"
      :is-server-side-paging="false"
      v-show="queryStatus"
    >


      <template #cell(action)="row">
        <b-button class="ml-2" style="background-color: #17a2b8" @click="toEdit(row.item)">檢視</b-button>
      </template>

    </i-table>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import {ref, reactive, defineComponent, onMounted,onActivated} from '@vue/composition-api';
import IDatePicker from '../shared/i-date-picker/i-date-picker.vue';
import ITable from '../shared/i-table/i-table.vue';
import IFormGroupCheck from '../shared/form/i-form-group-check.vue';
import {useValidation, validateState} from '@/shared/form';
import {useGetters} from '@u3u/vue-hooks';
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import {newformatDate} from "@/shared/date/minguo-calendar-utils";
import {changeFormId, changeSubject} from "@/shared/word/change-word-utils";
import {changeDealWithUnit} from "@/shared/word/directions";
import {currentProcessingUnit} from "@/shared/word/iTable-convert-unit";
import {navigateByNameAndParams} from "@/router/router";
import userSys from "@/components/userSys.vue";


export default defineComponent({
  name: 'notify',
  methods: {changeFormId, changeSubject},
  components: {
    IDatePicker,
    ITable,
    IFormGroupCheck,
    'user-sys': userSys
  },
  setup() {
    const userAllData = ref(useGetters(['getUserAllData']).getUserAllData)
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;
    const iTable = ref(null);
    const stepVisible = ref(true);
    const queryStatus = ref(false);
    const notificationService = useNotification();
    const userData = ref(useGetters(['getUserData']).getUserData).value;

    onMounted(() => {
      peunitOptions();
    });

    onActivated(() => {
      toQuery();
    });


    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY = '簽核'
    }

    function notBeforePublicDateStart(date: Date) {
      if (form.dateStart) return date < new Date(form.dateStart);
    }

    function notAfterPublicDateEnd(date: Date) {
      if (form.dateEnd) return date > new Date(form.dateEnd);
    }

    const formDefault = {
      unit: '', //部門
      appName: '', //申請者
      formId: '', //表單
      processInstanceStatus: '', //處理狀態
      formType: '', //表單分類
      dateStart: null, //起
      dateEnd: null, //迄
    };

    const form = reactive(Object.assign({}, formDefault));

    // 表單物件驗證規則
    const rules = {
      unit: {},
      appName: {},
      formId: {},
      processInstanceStatus: {},
      formType: {},
      dateStart: {},
      dateEnd: {},
    }

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
          key: 'filAndApp',
          label: '申請者/填表人',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'formId',
          label: '申請表單',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : changeFormId(value)),
        },
        {
          key: 'applyDate',
          label: '申請日期',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : newformatDate(new Date(value), '/')),

        },
        {
          key: 'pendingUserId',
          label: '目前處理單位',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : currentProcessingUnit(value, bpmDeptsOptions, userAllData.value)),
        },
        {
          key: 'processInstanceStatus',
          label: '處理狀況',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => {
            if (value !== undefined) {
              switch (value) {
                case '0':
                  return '處理中';
                case '1':
                  return '已處理完畢';
                case '2':
                  return '補件';
                case '3':
                  return '撤銷';
                default:
                  return '';
              }
            } else {
              return '';
            }
          }
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
      totalItems: 0,
    });


    // 下拉選單選項
    const queryOptions = reactive({
      status: [
        {value: '0', text: '處理中'},
        {value: '1', text: '處理過'},
        {value: '2', text: '補件'},
        {value: '3', text: '撤銷'},
      ],
      formCase: [
        {value: 'L410', text: 'L410-共用系統使用者帳號申請單'},
        {value: 'L414', text: 'L414-網路服務連結申請單'},
      ],
      formTypeList: [
        {value: '0', text: 'ISMS簽核表單'},
      ],
      peunitOptions: [],
    });

    const toQuery = () => {
      table.data = undefined;
      const params = new FormData();
      params.append('bpmFormQueryDto', new Blob([JSON.stringify(form)], {type: 'application/json'}));
      axios.post(`/process/notify/queryTask`, params).then(({data}) => {
        queryStatus.value = true;
        table.data = [];
        if (data.length <= 0) return;

        // 最新的日期到最舊的日期排序
        table.data = data.sort((a, b) => {
          const dateA: any = new Date(a.signingDatetime);
          const dateB: any = new Date(b.signingDatetime);
          return dateB - dateA;
        });

        //過濾table.data所有物件 要把畫面要顯示的值都先塞進table.data內 不然iTable內的b-modal會沒有值
        table.data.forEach(i => {
          i.subject = changeSubject(i, true)
          i.filAndApp = (i.appEmpid === i.filEmpid) ? i.appName : i.appName + '/' + i.filName
        });
        table.totalItems = data.length;
      }).catch(notificationErrorHandler(notificationService));
    };

    function toEdit(item) {
      let taskData = {
        processInstanceId: item.processInstanceId,
        taskId: item.taskId,
        taskName: item.taskName,
        decisionRole: item.decisionRole,
        additional: item.additional,
      }
      let prefix = item.formId.substring(0, 4).toLowerCase()
      navigateByNameAndParams(prefix + 'Edit', {
        formId: item.formId,
        taskData: taskData,
        formStatus: FormStatusEnum.READONLY,
        isNotKeepAlive: false,
        processInstanceStatus: item.processInstanceStatus,
        isCancel:true,
      });
    }

    function peunitOptions() {
      axios.get(`/eip/peunitOptions/${userData.empId}`)
        .then(({data}) => {
          queryOptions.peunitOptions = data.map(item => {
            return {value: item.pename, text: item.pename};
          })
        })
        .catch(notificationErrorHandler(notificationService))
    }

    function toReset() {
      reset();
      table.data = [];
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
      queryOptions,
      iTable,
      notBeforePublicDateStart,
      notAfterPublicDateEnd,
      toEdit,
      queryStatus,
      bpmDeptsOptions,
      changeDealWithUnit,
      toReset
    };
  },
});
</script>

<style scoped>
</style>
