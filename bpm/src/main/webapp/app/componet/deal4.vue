<template>
  <section class="container mt-3">
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
      <div class="card-body">
        <b-form inline>
          <b-form-group class="col-6" label-cols="2" content-cols="4" label="部門:" label-for="seqNo">
            <b-form-input v-model="formDefault.seqNo" id="seqNo"></b-form-input>
          </b-form-group>
          <b-form-group class="col-6" label-cols="2" content-cols="4" label="申請者：" label-for="usrMail">
            <b-form-input v-model="formDefault.usrMail" id="usrMail"></b-form-input>
          </b-form-group>
        </b-form>

        <b-form inline>
          <b-form-group class="col-6" label-cols="2" content-cols="4" label="表單:" label-for="seqNo">
            <b-form-input v-model="formDefault.seqNo" id="seqNo"></b-form-input>
          </b-form-group>
          <b-form-group class="col-6" label-cols="2" content-cols="4" label="處理狀況：" label-for="usrMail">
            <b-form-input v-model="formDefault.usrMail" id="usrMail"></b-form-input>
          </b-form-group>
        </b-form>
        <b-form inline>
          <b-form-group class="col-6" label-cols="2" content-cols="4" label="表單分類：" label-for="usrMail">
            <b-form-input v-model="formDefault.usrMail" id="usrMail"></b-form-input>
          </b-form-group>
        </b-form>

        <!-- 填表日期 -->
        <!-- <b-form-row>
          <i-form-group-check
            :label="'填表日期'"
            class="col-sm-12"
            label-cols-md="2"
            content-cols-md="7"
            :dual1="formDefault.seqDate"
            :dual2="formDefault.seqDateEnd"
          >
            <b-input-group>
              <i-date-picker v-model="formDefault.seqDate" :disabled-date="notAfterPublicDateEnd" placeholder="yyy/MM/dd"></i-date-picker>
              <b-input-group-text>至</b-input-group-text>
              <i-date-picker
                v-model="formDefault.seqDateEnd"
                :disabled-date="notBeforePublicDateStart"
                placeholder="yyy/MM/dd"
              ></i-date-picker>
            </b-input-group>
          </i-form-group-check>
        </b-form-row> -->

        <div class="text-center pt-5">
          <b-button class="ml-2" style="background-color: #1aa4b7" @click="toQuery">查詢</b-button>
          <b-button class="ml-2" style="background-color: #1aa4b7" @click="rest">清除</b-button>
        </div>
      </div>

      <section class="pt-5" v-if="stepVisible">
        <div>
          <b-table striped hover :items="paginatedItems" :fields="table.fields">
            <!-- <template #cell(index)="row">
              {{ row.index + 1 }}
            </template> -->
            <!-- <template #cell(active)="row">
              {{ row.item.active === 'Y' ? '是' : '否' }}
            </template> -->
            <!-- <template #cell(action)="row">
              <b-button class="ml-2" style="background-color: #1aa4b7" @click="toEdit(row.item)">編輯</b-button>
            </template> -->
          </b-table>
          <b-pagination v-model="page" :total-rows="table.data.length" :per-page="perPage" align="center" />
        </div>
      </section>
    </div>
  </section>
</template>

<script lang="ts">
import { ref, reactive, computed, toRefs } from 'vue';
import axios from 'axios';

export default {
  name: 'deal4',
  component: [],
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

    const formDefault = reactive({
      hostname: '',
      result: 'N',
      seqNo: '',
      usrMail: '',
      usrMaster: '',
      seqDate: '',
      seqDateEnd: '',
    });

    // 表單物件驗證規則
    const rules = ref({
      hostname: {},
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
      totalItems: 0,
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
      formDefault.hostname = '';
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
    };
  },
};
</script>

<style scoped>
.container {
  min-height: 100%;
  display: flex;
  flex-direction: column;
}
</style>