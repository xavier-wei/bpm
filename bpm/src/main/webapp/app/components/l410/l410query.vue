<template>
  <div>
    <section class="container mt-2">
      <div class="card">
        <div class="card-header py-1 text-left" style="background-color: #b0ded4">
          <div class="row align-items-center">
            <div class="col-sm-11 p-0">
              <h5 class="m-0">
                <font-awesome-icon icon="search" />
                查詢條件
              </h5>
            </div>
          </div>
        </div>

        <div class="card-body clo-12" style="background-color: #d3ede8">
          <b-form-row>
            <i-form-group-check class="col-4" label-cols="4" content-cols="8" :label="'關鍵字：'" :item="$v.word">
              <b-form-input v-model="$v.word.$model"> </b-form-input>
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
            <b-button class="ml-2" style="background-color: #17a2b8" @click="toL410Apply()"> 新增</b-button>
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
          :is-server-side-paging="false"
          :hideNo="true"
        >
          <template #cell(number)="row">
            <!-- <b-button variant="link" class="ml-2" style="background-color: #17a2b8" @click="toEdit(row)">{{ roq.item.number }}</b-button> -->
            <b-button variant="link" style="color: blue" @click="toEdit(row.item)"
              ><u>{{ row.item.number }}</u></b-button
            >
          </template>
        </i-table>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import { ref, reactive, computed, toRefs, defineComponent } from '@vue/composition-api';
import IDatePicker from '../../shared/i-date-picker/i-date-picker.vue';
import ITable from '../../shared/i-table/i-table.vue';
import IFormGroupCheck from '../../shared/form/i-form-group-check.vue';
import { useValidation, validateState } from '../../shared/form';
import { useBvModal } from '../../shared/modal';
import { navigateByNameAndParams } from '@/router/router';
import { useStore } from '@u3u/vue-hooks';
import { Pagination } from '@/shared/pagination.model';
export default defineComponent({
  name: 'l410Query',
  components: {
    IDatePicker,
    ITable,
    IFormGroupCheck,
  },
  setup() {
    const iTable = ref(null);
    const stepVisible = ref(true);
    const $bvModal = useBvModal();

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

    const { $v, checkValidity, reset } = useValidation(rules, form, formDefault);

    const table = reactive({
      fields: [
        {
          key: 'number',
          label: '表單編號',
          sortable: false,
          thStyle: 'width:20%',
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
          key: 'applyDate',
          label: '申請人',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'port',
          label: '填表人',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'active2',
          label: '申請單位',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'active3',
          label: '申請事由',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'active4',
          label: '系統項目',
          sortable: false,
          thStyle: 'width:40%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
      ],
      data: [],
      totalItems: 1,
    });

    const mockdata = [
      {
        number: 'L410-11205-00001',
        hostname: '112/05/24  10:23:43',
        applyDate: '林一郎(3021)',
        port: '楊助理(2753)',
        active2: '企劃部',
        active3: '新進,生效日期:112/06/05',
        active4: '人事差勤系統AD帳號,公文管理系統,電子郵件帳號',
      },
    ];

    // 下拉選單選項
    const queryOptions = reactive({
      number: [
        { value: '0', text: '1_0_2' },
        { value: '1', text: '1_0_1' },
        { value: '2', text: '1_0_0' },
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

    function toEdit(i) {
      useStore().value.commit('syuan', { user: 'syuan' });
      // console.log(useStore().value);
      //todo:未做方法先放著
    }

    const toL410Apply = () => {
      navigateByNameAndParams('l410Apply', { isNotKeepAlive: false });
    };


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
      toEdit,
      toL410Apply,
    };
  },
});
</script>

<style scoped>
</style>
