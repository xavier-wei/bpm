<template>
  <div>

    <div style="background-color: #b0ded4;padding-top: 10px;">
      <b-row class=" d-flex">
        <p class="ml-4" style="color: white">
          L410-共用系統使用者帳號申請單
        </p>

        <P class="ml-3">機密等級： 敏感</P>
      </b-row>
    </div>

        <div class="card-body clo-12" style="background-color: #d3ede8">
          <b-form-row>
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" :label="'關鍵字：'" :item="$v.word">
              <b-form-input v-model="$v.word.$model"></b-form-input>
            </i-form-group-check>

            <!--            <i-form-group-check class="col-4" label-cols="4" content-cols="8" :label="`版號：`" :item="$v.number">-->
            <!--              <b-form-select v-model="$v.number.$model" :options="queryOptions.number">-->
            <!--                <template #first>-->
            <!--                  <option value="">請選擇</option>-->
            <!--                </template>-->
            <!--              </b-form-select>-->
            <!--            </i-form-group-check>-->
          </b-form-row>

          <div class="text-center pt-5">
            <b-button class="ml-2" style="background-color: #17a2b8" @click="toL410Apply()"> 新增</b-button>
            <b-button class="ml-2" style="background-color: #17a2b8" @click="toQuery()">查詢</b-button>
            <b-button class="ml-2" style="background-color: #17a2b8" @click="reset()">清除</b-button>
          </div>
        </div>

        <i-table
          ref="iTable"
          :itemsUndefinedBehavior="'loading'"
          :items="table.data"
          :fields="table.fields"
          :totalItems="table.totalItems"
          :is-server-side-paging="false"
          :hideNo="true"
          v-show="queryStatus"
        >
          <template #cell(formId)="row">
            <!-- <b-button variant="link" class="ml-2" style="background-color: #17a2b8" @click="toEdit(row)">{{ roq.item.number }}</b-button> -->
            <b-button variant="link" style="color: blue" @click="toEdit(row.item)"
            ><u>{{ row.item.formId }}</u></b-button
            >
          </template>

          <template #cell(appName)="row">
            <div v-if="!!row.item.appName"> {{ row.item.appName }}</div>
            <div v-if="!!row.item.appEmpid"> ({{ row.item.appEmpid }})</div>
          </template>

          <template #cell(filName)="row">
            <div v-if="!!row.item.filName"> {{ row.item.filName }}</div>
            <div v-if="!!row.item.filEmpid"> ({{ row.item.filEmpid }})</div>
          </template>

          <template #cell(applicationReason)="row">
            <div v-if="row.item.appReason ==='1'"> 新進 , </div>
            <div v-else-if="row.item.appReason ==='2'"> 新進 , </div>
            <div v-else-if="row.item.appReason ==='3'"> 職務異動 , </div>
            <div v-if="row.item.isEnableDate ==='1'"> 生效日期： {{ formatToString(new Date(row.item.enableDate),'/')}}</div>
            <div v-if="row.item.isOther ==='1'"> 其他理由： {{ row.item.otherReason }}</div>
          </template>

          <template #cell(systemItem)="row">
            <div> {{changeProject(row.item)}} </div>
          </template>

        </i-table>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import {ref, reactive, computed, toRefs, defineComponent} from '@vue/composition-api';
import IDatePicker from '../../shared/i-date-picker/i-date-picker.vue';
import ITable from '../../shared/i-table/i-table.vue';
import IFormGroupCheck from '../../shared/form/i-form-group-check.vue';
import {useValidation, validateState} from '@/shared/form';
import {useBvModal} from '@/shared/modal';
import {navigateByNameAndParams} from '@/router/router';
import {useGetters, useStore} from '@u3u/vue-hooks';
import {Pagination} from '@/shared/pagination.model';
import {formatToString, newformatDate} from "@/shared/date/minguo-calendar-utils";
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import {changeProject} from "@/shared/word/project-conversion";

export default defineComponent({
  name: 'l410Query',
  methods:{changeProject},
  components: {
    IDatePicker,
    ITable,
    IFormGroupCheck,
  },
  setup() {
    const iTable = ref(null);
    const $bvModal = useBvModal();
    const queryStatus = ref(false);
    const notificationService = useNotification();
    const userData = ref(useGetters(['getUserData']).getUserData).value;

    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY ='簽核'
    }

    const formDefault = {
      word: '', //關鍵字
      number: '1', //版號
    };

    const form = reactive(Object.assign({}, formDefault));

    // 表單物件驗證規則
    const rules = ref({
      word: {},
      number: {},
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
        },
        {
          key: 'applicationReason',
          label: '申請事由',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'systemItem',
          label: '系統項目',
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
      number: [
        {value: '0', text: '1_0_2'},
        {value: '1', text: '1_0_1'},
        {value: '2', text: '1_0_0'},
      ],
    });

    const toQuery = () => {
      table.data = [];
      const params = new URLSearchParams()
      params.append('word', form.word)
      // params.append('number', form.number)
      params.append('processInstanceStatus', '1')

      axios.get(`/eip/eip-bpm-isms-l410/findByWord?${params.toString()}`)
        .then(({data}) => {
          queryStatus.value = true
          if (iTable.value) iTable.value.state.pagination.currentPage = 1;
          if (data) {
            console.log('l410Query - data :: ', data)
            table.data = data
          }
        })
        .catch(notificationErrorHandler(notificationService))
    };

    function toEdit(item) {
      navigateByNameAndParams('l410Edit', {
        formId: item.formId,
        formStatus: FormStatusEnum.READONLY,
        isNotKeepAlive: false,
        stateStatus : userData.cpape05m.unitName !== '資訊推動小組',
        isSignature : false
      });

    }

    const toL410Apply = () => {
      navigateByNameAndParams('l410Apply', {
        formStatus: FormStatusEnum.CREATE,
        isNotKeepAlive: true
      });
    };


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
      toEdit,
      toL410Apply,
      formatToString,
      queryStatus
    };
  },
});
</script>

<style scoped>
</style>
