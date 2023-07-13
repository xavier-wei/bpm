<template>
  <div>
    <section class="container mt-2">
      <div class="card">
        <div class="card-header py-1 text-left">
          <div class="row align-items-center">
            <div class="col-sm-11 p-0">
              <h5 class="m-0">
                <font-awesome-icon icon="search" />
                查詢條件
              </h5>
            </div>
          </div>
        </div>
        <div class="card-body clo-12">
          <b-form-row>
            <b-form-group class="col-6" label-cols="3" content-cols="4" label="部門:">
              <b-form-select v-model="formDefault.deaprtmant">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </b-form-group>
            <b-form-group class="col-6" label-cols="3" content-cols="4" label="申請者：">
              <b-form-select v-model="formDefault.usrMail">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </b-form-group>
          </b-form-row>

          <b-form-row>
            <b-form-group class="col-6" label-cols="3" content-cols="4" label="表單:">
              <b-form-select v-model="formDefault.seqNo">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </b-form-group>
            <b-form-group class="col-6" label-cols="3" content-cols="4" label="處理狀況：">
              <b-form-select v-model="formDefault.status" :options="queryOptions.city">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </b-form-group>
          </b-form-row>
          <b-form-row>
            <b-form-group class="col-6" label-cols="3" content-cols="4" label="表單分類：">
              <b-form-select v-model="formDefault.usrMail">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </b-form-group>
          </b-form-row>
          <!-- 填表日期 -->
          <b-form-row>
            <b-form-group
              :label="'期間:'"
              class="col-6"
              label-cols-md="3"
              content-cols-md="9"
              :dual1="formDefault.seqDate"
              :dual2="formDefault.seqDateEnd"
            >
              <b-input-group>
                <i-date-picker v-model="formDefault.seqDate" placeholder="yyy/MM/dd"></i-date-picker>
                <b-input-group-text>至</b-input-group-text>
                <i-date-picker v-model="formDefault.seqDateEnd" placeholder="yyy/MM/dd"></i-date-picker>
              </b-input-group>
            </b-form-group>
          </b-form-row>

          <div class="text-center pt-5">
            <b-button class="ml-2" style="background-color: #1aa4b7" @click="toQuery()">查詢</b-button>
            <b-button class="ml-2" style="background-color: #1aa4b7" @click="rest()">清除</b-button>
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
            <b-button class="ml-2" style="background-color: #1aa4b7" @click="toEdit(row.item)">處理</b-button>
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

export default defineComponent({
  name: 'deal',
  components: {
    IDatePicker,
    ITable,
  },
  setup() {
    const iTable = ref(null);
    const stepVisible = ref(true);

    const formDefault = {
      deaprtmant: '',
      result: 'N',
      seqNo: '',
      usrMail: '',
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
      status: {},
      seqDate: {},
      seqDateEnd: {},
    });

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
      console.log('formDefault', formDefault);
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

    const rest = () => {
      // formDefault.hostname = '';
    };

    return {
      formDefault,
      stepVisible,
      toQuery,
      rest,
      table,
      form,
      mockdata,
      queryOptions,
      iTable,
    };
  },
});
</script>

<style scoped>
</style>