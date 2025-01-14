<template>
  <div>
    <div class="bpm_form_header">
      <b-row class="d-flex">
        <p class="ml-4" style="color: white">L414-網路服務連結申請單</p>

        <P class="ml-3">機密等級： 敏感</P>
      </b-row>
    </div>
    <div class="card-body bpm_background">
      <b-form-row>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="4"
          content-cols-md="8"
          label-align-md="right"
          :label="'關鍵字：'"
          :item="$v.word"
        >
          <b-form-input v-model="$v.word.$model"></b-form-input>
        </i-form-group-check>
      </b-form-row>

      <div class="text-center mt-1 mb-1">
        <i-button class="ml-2 mb-1" type="folder-plus"  @click="toL414Apply()"/>
        <i-button class="ml-2 mb-1" type="search"  @click="toQuery()"/>
        <i-button class="ml-2 mb-1" type="x-circle"  @click="toReset()"/>
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
      :hideNo="true"
      v-show="queryStatus"
    >
      <template #cell(formId)="row">
        <b-button variant="link" style="color: blue" @click="toEdit(row.item)">
          <u>{{ row.item.formId }}</u>
        </b-button>
      </template>

      <template v-slot:cell(appName)="row">
        <span v-html="row.item.appName"></span>
      </template>

      <template v-slot:cell(filName)="row">
        <span v-html="row.item.filName"></span>
      </template>

      <template v-slot:cell(needNarrative)="row">
        <span v-html="row.item.needNarrative"></span>
      </template>

    </i-table>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import {ref, reactive, onActivated} from '@vue/composition-api';
import IDatePicker from '@/shared/i-date-picker/i-date-picker.vue';
import ITable from '@/shared/i-table/i-table.vue';
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';
import {useValidation, validateState} from '@/shared/form';
import {navigateByNameAndParams} from '@/router/router';
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import {newformatDate} from '@/shared/date/minguo-calendar-utils';
import {useGetters} from "@u3u/vue-hooks";
import {changeCodeNoToCh} from "@/shared/word/directions";
import { appNameUnit, filNameUnit, needNarrativeUnit} from "@/shared/word/iTable-convert-unit";
import IButton from '@/shared/buttons/i-button.vue';

export default {
  name: 'l414Query',
  components: {
    IDatePicker,
    ITable,
    IFormGroupCheck,
    'i-button': IButton,
  },
  setup() {
    //是否顯示iTable
    const queryStatus = ref(false);

    //單位下拉選單資訊
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;

    const iTable = ref(null);
    const notificationService = useNotification();

    //列舉型別
    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY = '簽核'
    }

    const formDefault = {
      word: '', //關鍵字
    };

    const form = reactive(Object.assign({}, formDefault));

    // 表單物件驗證規則
    const rules = ref({
      word: {},
    });

    const {$v, checkValidity, reset} = useValidation(rules, form, formDefault);

    const table = reactive({
      fields: [
        {
          key: 'formId',
          label: '表單編號',
          sortable: false,
          thStyle: 'width:20%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'applyDate',
          label: '申請日期',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : newformatDate(new Date(value), '/')),
        },
        {
          key: 'appName',
          label: '申請人',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'filName',
          label: '填表人',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'appUnit',
          label: '申請單位',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : changeCodeNoToCh(value, bpmDeptsOptions)),
        },
        {
          key: 'isEnable',
          label: '規則',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => {
            if (value !== undefined) {
              switch (value) {
                case '0':
                  return '停用';
                case '1':
                  return '啟用';
                default:
                  return '';
              }
            } else {
              return '';
            }
          }
        },
        {
          key: 'needNarrative',
          label: '需求說明',
          sortable: false,
          thStyle: 'width:40%',
          thClass: 'text-center',
          tdClass: 'text-left align-middle',
        },
      ],
      data: [],
      totalItems: 0,
    });

    onActivated(() => {
      toQuery();
    });

    //查詢L414所有已完成的表單
    const toQuery = () => {
      table.data = undefined;
      const params = new URLSearchParams()
      params.append('word', form.word)
      params.append('processInstanceStatus', '1')

      axios.get(`/eip/eip-bpm-isms-l414/findByWord?${params.toString()}`)
        .then(({data}) => {
          queryStatus.value = true;
          table.data = [];
          if (iTable.value) iTable.value.state.pagination.currentPage = 1;
          if (data) {
            table.data = data;
            //過濾table.data所有物件 要把畫面要顯示的值都先塞進table.data內 不然iTable內的b-modal會沒有值
            table.data.forEach(i => {
              i.appName = appNameUnit(i)
              i.filName = filNameUnit(i)
              i.needNarrative = needNarrativeUnit(i)
            });
          }
        })
        .catch(notificationErrorHandler(notificationService))
    };

    //新增表單
    const toL414Apply = () => {
      navigateByNameAndParams('l414Apply', {
        formStatus: FormStatusEnum.CREATE,
        isNotKeepAlive: true
      });
    };

    //判斷要導頁去哪
    const toEdit = (item) => {
      let taskData = {
        processInstanceId: '',
        taskId: '',
        taskName: '',
        decisionRole: '',
        additional: '',
      }
      navigateByNameAndParams('l414Edit', {
        formId: item.formId,
        taskData: taskData,
        formStatus: FormStatusEnum.READONLY,
        isNotKeepAlive: false,
        isSignature: false,
        processInstanceStatus: item.processInstanceStatus,
      });
    };

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
      iTable,
      toL414Apply,
      toEdit,
      queryStatus,
      toReset,
    };
  },
};
</script>

<style scoped>
</style>
