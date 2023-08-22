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
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" :label="'關鍵字：'" :item="$v.word">
              <b-form-input v-model="$v.word.$model"></b-form-input>
            </i-form-group-check>

            <i-form-group-check class="col-4" label-cols="4" content-cols="8" :label="`版號：`" :item="$v.number">
              <b-form-select v-model="$v.number.$model" :options="queryOptions.number">
                <template #first>
                  <option value="">請選擇</option>
                </template>
              </b-form-select>
            </i-form-group-check>
          </b-form-row>

          <div class="text-center pt-5">
            <b-button class="ml-2" style="background-color: #17a2b8" @click="toL414Apply()"> 新增</b-button>
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
            :hideNo="true"
        >
          <template #cell(formId)="row">
            <b-button variant="link" style="color: blue" @click="toDetail(row.item)">
              <u>{{ row.item.formId }}</u>
            </b-button>
          </template>

          <template #cell(isEnable)="row">
            <div v-if="row.item.isEnable === '0'">
              停用
            </div>
            <div v-if="row.item.isEnable === '1'">
              啟用
            </div>
          </template>

          <template #cell(needNarrative)="row">
            <b-row>
              <b-col class="col-12" >
                <div>
                  來源IP：{{row.item.sourceIp}},
                </div>
              </b-col>
              <b-col class="col-12">
                <div>
                  目的IP：{{row.item.sourceIp}},
                </div>
              </b-col>
              <b-col class="col-12">
                <div>
                  使用協定(Port)：{{row.item.port}},
                </div>
              </b-col>
              <b-col class="col-12">
                <div>
                  <b-input-group>
                    <div>傳輸模式：</div>
                    <div class="mx-1" v-if="row.item.isTcp ==='Y'">TCP</div>
                    <div class="mx-1" v-if="row.item.isUdp ==='Y'">UDP</div>
                    <div>,</div>
                  </b-input-group>
                </div>
              </b-col>
              <b-col class="col-12">
                <div>
                  用途說明：{{row.item.instructions}}
                </div>
              </b-col>
            </b-row>
          </template>
        </i-table>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import {ref, reactive, defineComponent, onMounted, onActivated} from '@vue/composition-api';
import IDatePicker from '../../shared/i-date-picker/i-date-picker.vue';
import ITable from '../../shared/i-table/i-table.vue';
import IFormGroupCheck from '../../shared/form/i-form-group-check.vue';
import {useValidation, validateState} from '@/shared/form';
import {navigateByNameAndParams} from '@/router/router';
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import {newformatDate} from '@/shared/date/minguo-calendar-utils';
import {useGetters} from "@u3u/vue-hooks";
import {changeDealWithUnit} from "@/shared/word/directions";

export default {
  name: 'l414Query',
  components: {
    IDatePicker,
    ITable,
    IFormGroupCheck,
  },
  setup() {
    const iTable = ref(null);
    const queryStatus = ref(false);
    const notificationService = useNotification();
    const userData = ref(useGetters(['getUserData']).getUserData).value.user;
    const bpmUnitOptions = ref(useGetters(['getBpmUnitOptions']).getBpmUnitOptions).value;
    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY ='簽核'
    }

    const formDefault = {
      word: '', //關鍵字
      number: '0', //版號
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
          key: 'filEmpid',
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
          formatter: value => (value == undefined ? '' : changeDealWithUnit(value, bpmUnitOptions)),
        },
        {
          key: 'isEnable',
          label: '規則',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
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

    // 下拉選單選項
    const queryOptions = reactive({
      number: [
        {value: '0', text: '1_0_2'},
        {value: '1', text: '1_0_1'},
        {value: '2', text: '1_0_0'},
      ],
    });

    onActivated(() => {
      toQuery();
    });

    const toQuery = () => {
      table.data = [];
      const params = new URLSearchParams()
      params.append('word', form.word)
      params.append('number', form.number)
      params.append('processInstanceStatus', '1')

      axios.get(`/eip/eip-bpm-isms-l414/findByWord?${params.toString()}`)
          .then(({data}) => {
            queryStatus.value = true
            if (iTable.value) iTable.value.state.pagination.currentPage = 1;
            if (data) {
              console.log('l414Query - data :: ',data)
              table.data = data
            }
          })
          .catch(notificationErrorHandler(notificationService))
    };

    const toL414Apply = () => {
      navigateByNameAndParams('l414Apply', {
        formStatus: FormStatusEnum.CREATE,
        isNotKeepAlive: true
      });
    };

    const toDetail = (item) => {
      navigateByNameAndParams('l414Edit', {
        l414Data: item,
        formStatus: FormStatusEnum.READONLY,
        isNotKeepAlive: false,
        stateStatus : userData === 'InfoTester'
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
      toL414Apply,
      toDetail,
      queryStatus,
    };
  },
};
</script>

<style scoped>
</style>
