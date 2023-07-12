<template>
  <div>
    <section class="container mt-3">
      <div class="card">
        <div class="card-header py-1 text-left">
          <div class="row align-items-center">
            <div class="col-sm-11 p-0">
              <h5 class="m-0">
                <font-awesome-icon icon="search" />
                查詢條件3
              </h5>
            </div>
          </div>
        </div>
        <div class="card-body">
          <b-form inline class="mb-3">
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
          </b-form>

          <b-form inline class="mb-3">
            <b-form-group class="col-6" label-cols="3" content-cols="4" label="表單:">
              <b-form-select v-model="formDefault.seqNo">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </b-form-group>
            <b-form-group class="col-6" label-cols="3" content-cols="4" label="處理狀況：">
              <b-form-select v-model="formDefault.usrMail" :options="queryOptions.city">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </b-form-group>
          </b-form>
          <b-form inline class="mb-3">
            <b-form-group class="col-6" label-cols="3" content-cols="4" label="表單分類：">
              <b-form-select v-model="formDefault.usrMail">
                <template #first>
                  <option value="">請選擇</option>
                </template></b-form-select
              >
            </b-form-group>
          </b-form>
          <!-- 填表日期 -->
          <b-form inline class="mb-3">
            <b-form-group
              :label="'期間:'"
              class="col-12"
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
          </b-form>

          <div class="text-center pt-5">
            <b-button class="ml-2" style="background-color: #1aa4b7" @click="toQuery()">查詢</b-button>
            <b-button class="ml-2" style="background-color: #1aa4b7" @click="rest()">清除</b-button>
          </div>
        </div>
      </div>
    </section>
    <section class="pt-5" v-if="stepVisible">
      <div>
        <b-table striped hover :items="mockdata" :fields="table.fields">
          <!-- <template #cell(index)="row">
              {{ row.index + 1 }}
            </template> -->
          <!-- <template #cell(active)="row">
              {{ row.item.active === 'Y' ? '是' : '否' }}
            </template> -->
          <template #cell(action)="row">
            <b-button class="ml-2" style="background-color: #1aa4b7" @click="toEdit(row.item)">編輯</b-button>
          </template>
        </b-table>
        <b-pagination v-model="page" :total-rows="table.totalItems" :per-page="perPage" align="center" />
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import { ref, reactive, computed, toRefs, defineComponent } from '@vue/composition-api';
import IDatePicker from '../shared/i-date-picker/i-date-picker.vue';
export default defineComponent({
  name: 'deal3',
  components: {
    IDatePicker,
  },
  setup() {
    const stepVisible = ref(true);
    const page = ref(1); //當前頁面
    const perPage = ref(20); //每頁顯示的資料

    // 根據當前頁碼和每頁顯示的資料數，計算當前頁面顯示的資料
    const paginatedItems = computed(() => {
      const start = (page.value - 1) * perPage.value;
      const end = start + perPage.value;
      return table.data.slice(start, end);
    });

    // 計算總頁數
    const totalPages = computed(() => {
      return Math.ceil(table.data.length / perPage.value);
    });

    const formDefault = {
      deaprtmant: '',
      result: 'N',
      seqNo: '',
      usrMail: '',
      usrMaster: '',
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
      paginatedItems,
      totalPages,
      perPage,
      page,
      form,
      mockdata,
      queryOptions,
    };
  },
});
</script>

<style scoped>
/* .container {
  min-height: 100%;
  display: flex;
  flex-direction: column;
} */
</style>