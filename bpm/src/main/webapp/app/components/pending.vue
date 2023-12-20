<template>
  <div>
    <div class="card-header py-1 text-left " style="background-color: #66bfab">
      <div class="row align-items-center">
        <div class="col-sm-11 p-0">
          <h5 class="m-0">
            <font-awesome-icon icon="search"/>
            查詢條件
          </h5>
        </div>
      </div>
    </div>
    <div class="card-body bpm_background clo-12">
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
              <option value="" disabled>請選擇</option>
            </template>
          </b-form-select
          >
        </i-form-group-check>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="4"
          content-cols-md="8"
          label-align-md="right"
          label="處理狀況："
        >
          <b-form-select v-model="$v.processInstanceStatus.$model" :options="queryOptions.status">
            <template #first>
              <option value="" disabled>請選擇</option>
            </template>
          </b-form-select
          >
        </i-form-group-check>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="4"
          content-cols-md="8"
          label-align-md="right"
          label="表單分類："
        >
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
        <i-button class="ml-2" type="search"  @click="toQuery()"/>
        <i-button class="ml-2" type="diagram-3"  v-show="superiorFilter(userData.titleName)" @click="toSubordinateQuery()"/>
        <i-button class="ml-2" type="x-circle"  @click="toReset()"/>
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
        <i-button class="ml-2" type="pencil-square" @click="toEdit(row.item,'0')" v-if="userData.userName === row.item.appName && row.item.isSubmit === '0'"/>
        <i-button class="ml-2" v-else type="clipboard-check" @click="toEdit(row.item,'1')"/>
      </template>
    </i-table>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import {ref, reactive, defineComponent, onActivated, toRef} from '@vue/composition-api';
import IDatePicker from '@/shared/i-date-picker/i-date-picker.vue';
import ITable from '@/shared/i-table/i-table.vue';
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';
import {useValidation, validateState} from '@/shared/form';
import {required} from '@/shared/validators';
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import {newformatDate} from "@/shared/date/minguo-calendar-utils";
import {changeSubject} from "@/shared/word/change-word-utils";
import {configRoleToBpmIpt} from "@/shared/word/configRole";
import {useGetters} from "@u3u/vue-hooks";
import {navigateByNameAndParams} from "@/router/router";
import {superiorFilter} from "@/shared/word/superiorFilter";
import IButton from '@/shared/buttons/i-button.vue';

export default defineComponent({
  name: 'pending',//待處理表單
  methods: {changeSubject, configRoleToBpmIpt},
  props: {
    query: {
      type: String,
      required: false,
      default: '1',
    },
  },
  components: {
    IDatePicker,
    ITable,
    IFormGroupCheck,
    'i-button': IButton,
  },
  setup(props) {
    //登入者資訊
    const userData = ref(useGetters(['getUserData']).getUserData).value;

    //是否顯示iTable
    const queryStatus = ref(false);

    //當返回此頁面時,檢查props.query帶了神魔參數,1是一般查詢 2是下屬查詢
    const queryProp = toRef(props, 'query');

    //用來判斷當前頁面是一般查詢還是下屬表單查詢，防止每次去Edit頁在回來時只會停留在一般查詢
    const query = ref('');

    const iTable = ref(null);
    const notificationService = useNotification();

    //列舉型別
    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY = '簽核'
    }

    onActivated(() => {
      if (queryProp.value === '2') {
        toSubordinateQuery();
      }else {
        toQuery();
      }
    });

    function notBeforePublicDateStart(date: Date) {
      if (form.dateStart) return date < new Date(form.dateStart);
    }

    function notAfterPublicDateEnd(date: Date) {
      if (form.dateEnd) return date > new Date(form.dateEnd);
    }

    const formDefault = {
      formId: '', //表單
      processInstanceStatus: '', //處理狀態
      formType: '', //表單分類
      dateStart: undefined, //起
      dateEnd: undefined, //迄
    };

    const form = reactive(Object.assign({}, formDefault));

    // 表單物件驗證規則
    const rules = ref({
      formId: {},
      processInstanceStatus: {notnull: required},
      formType: {},
      dateStart: {},
      dateEnd: {},
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
        {value: '0', text: '處理中'},
        {value: '1', text: '處理過'},
        {value: '2', text: '補件'},
      ],
      formCase: [
        {value: 'L410', text: 'L410-共用系統使用者帳號申請單'},
        {value: 'L414', text: 'L414-網路服務連結申請單'},
      ],
      formTypeList: [
        {value: '0', text: 'ISMS簽核表單'},
      ],
    });

    //一般查詢
    const toQuery = () => {
      table.data = undefined;
      query.value = '1';
      const body = {
       ...form,
      };
      axios.post(`/process/queryTask`, body).then(({data}) => {
        queryStatus.value = true;
        table.data = [];
        if (data.length <= 0) return;

        table.data = data.slice(0, data.length);

        //過濾table.data所有物件 要把畫面要顯示的值都先塞進table.data內 不然iTable內的b-modal會沒有值
        table.data.forEach(i => {
          i.subject = changeSubject(i.formId,i.taskName,i.applyDate, true)
          i.filAndApp = (i.appEmpid === i.filEmpid) ? i.appName : i.appName + '/' + i.filName
        });

        table.totalItems = data.length;
      })
        .catch(notificationErrorHandler(notificationService));
    };

    //下屬表單查詢
    const toSubordinateQuery = () => {
      table.data = undefined;
      query.value = '2';
      const body = {
        ...form,
      };
      axios.post(`/process/getAllSubordinateTask`, body).then(({data}) => {
        queryStatus.value = true;
        table.data = [];
        if (data.length <= 0) return;

        table.data = data.slice(0, data.length);

        //過濾table.data所有物件 要把畫面要顯示的值都先塞進table.data內 不然iTable內的b-modal會沒有值
        table.data.forEach(i => {
          i.subject = changeSubject(i.formId,i.taskName,i.applyDate, true)
          i.filAndApp = (i.appEmpid === i.filEmpid) ? i.appName : i.appName + '/' + i.filName
        });

        table.totalItems = data.length;
      })
        .catch(notificationErrorHandler(notificationService));
    };

    //判斷要導頁去哪
    function toEdit(item, i) {

      let prefix = item.formId.substring(0, 4).toLowerCase()

      let taskData = {
        processInstanceId: item.processInstanceId,
        taskId: item.taskId,
        taskName: item.taskName,
        decisionRole: item.decisionRole,
        additional: item.additional,
      }

      if (i === '0') {
        navigateByNameAndParams(prefix + 'Edit', {
          formId: item.formId,
          taskData: taskData,
          formStatus: FormStatusEnum.MODIFY,
          isNotKeepAlive: false,
          isSignature: false,
          isCancel:true,
          query:query.value,
        });
      } else {
        navigateByNameAndParams(prefix + 'Edit', {
          formId: item.formId,
          taskData: taskData,
          formStatus: FormStatusEnum.VERIFY,
          isNotKeepAlive: false,
          isSignature: item.taskName.substring(0, 2) !== '加簽',
          query:query.value,
        });
      }
    }

    //重置form所有資料、iTable清空
    function toReset() {
      reset();
      table.data = [];
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
      toSubordinateQuery,
      superiorFilter,
      toReset,
    };
  },
});
</script>

<style scoped>
</style>

