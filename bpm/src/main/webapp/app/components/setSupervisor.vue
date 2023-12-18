<template>
  <div>
    <div>
      <div class="bpm_form_header">
        <b-row class="d-flex">
          <p class="ml-4" style="color: white">表單特例管理</p>
        </b-row>
      </div>
    </div>

    <div class="card-body clo-12" style="background-color: #d3ede8">
      <b-form-row>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'申請者員編：'"
          :item="$v.queryPecard"
        >
          <b-form-input class="d-inline col-5" v-model="$v.queryPecard.$model"/>
        </i-form-group-check>

      </b-form-row>

      <div class="text-center pt-5">
        <b-button class="ml-2" style="background-color: #17a2b8; color: white" @click="toggleButton">
          {{ buttonText }}
        </b-button>
        <b-button class="ml-2" style="background-color: #17a2b8" @click="toQuery()">查詢</b-button>
        <b-button class="ml-2" style="background-color: #17a2b8" @click="toReset()">清除</b-button>
      </div>
    </div>

    <div class="card-body clo-12" style="background-color: #d3ede8" v-show="newDataStatus">
      <b-form-row>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'申請者員編：'"
          :item="$v.pecard"
        >
          <b-form-input class="d-inline col-5" v-model="$v.pecard.$model"/>
        </i-form-group-check>

        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'申請者單位名稱：'"
          :item="$v.unitName"
        >
          <b-form-select v-model="$v.unitName.$model" :options="bpmDeptsOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>
      </b-form-row>

      <b-form-row>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'申請者姓名：'"
          :item="$v.pename"
        >
          <b-form-input class="d-inline col-5" v-model="$v.pename.$model"/>
        </i-form-group-check>

        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'申請者職稱：'"
          :item="$v.posname"
        >
          <b-form-select v-model="$v.posname.$model" :options="bpmTitleOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>
      </b-form-row>

      <b-form-row>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'第一層上級員編：'"
          :item="$v.f1Account"
        >
          <b-form-input class="d-inline col-5" v-model="$v.f1Account.$model"/>
        </i-form-group-check>

        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'第一層上級單位：'"
          :item="$v.f1UnitName"
        >
          <b-form-select v-model="$v.f1UnitName.$model" :options="bpmDeptsOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>
      </b-form-row>

      <b-form-row>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'第一層上級姓名：'"
          :item="$v.f1Pename"
        >
          <b-form-input class="d-inline col-5" v-model="$v.f1Pename.$model"/>
        </i-form-group-check>

        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'第一層上級職稱：'"
          :item="$v.f1Posname"
        >
          <b-form-select v-model="$v.f1Posname.$model" :options="bpmTitleOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>
      </b-form-row>

      <b-form-row>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'第二層上級員編：'"
          :item="$v.f2Account"
        >
          <b-form-input class="d-inline col-5" v-model="$v.f2Account.$model"/>
        </i-form-group-check>

        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'第二層上級單位：'"
          :item="$v.f12UnitName"
        >
          <b-form-select v-model="$v.f2UnitName.$model" :options="bpmDeptsOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>
      </b-form-row>

      <b-form-row>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'第二層上級姓名：'"
          :item="$v.f2Pename"
        >
          <b-form-input class="d-inline col-5" v-model="$v.f2Pename.$model"/>
        </i-form-group-check>

        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="5"
          content-cols-md="7"
          label-align-md="right"
          :label="'第二層上級職稱：'"
          :item="$v.f2Posname"
        >
          <b-form-select v-model="$v.f2Posname.$model" :options="bpmTitleOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>
      </b-form-row>

      <b-container class="mt-3">
        <b-row class="justify-content-center">
          <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                    @click="submitForm()">送出
          </b-button>
          <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                    @click="reset()">清除
          </b-button>
        </b-row>
      </b-container>
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
      <template #cell(pecard)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y' ">
          <b-form-input class="d-inline col-5" v-model="row.item.pecard"/>
        </div>
        <div v-else>
          {{ row.item.pecard }}
        </div>
      </template>

      <template #cell(unitName)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-select v-model="row.item.unitName" :options="bpmDeptsOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.unitName, bpmDeptsOptions) }}
        </div>
      </template>

      <template #cell(pename)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-input class="d-inline col-5" v-model="row.item.pename"/>
        </div>
        <div v-else>
          {{ row.item.pename }}
        </div>
      </template>

      <template #cell(posname)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-select v-model="row.item.posname" :options="bpmTitleOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.posname, bpmTitleOptions) }}
        </div>
      </template>

      <template #cell(f1Account)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y' ">
          <b-form-input class="d-inline col-5" v-model="row.item.f1Account"/>
        </div>
        <div v-else>
          {{ row.item.f1Account }}
        </div>
      </template>

      <template #cell(f1UnitName)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-select v-model="row.item.f1UnitName" :options="bpmDeptsOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.f1UnitName, bpmDeptsOptions) }}
        </div>
      </template>

      <template #cell(f1Pename)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-input class="d-inline col-5" v-model="row.item.f1Pename"/>
        </div>
        <div v-else>
          {{ row.item.f1Pename }}
        </div>
      </template>

      <template #cell(f1Posname)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-select v-model="row.item.f1Posname" :options="bpmTitleOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.f1Posname, bpmTitleOptions) }}
        </div>
      </template>

      <template #cell(f2Account)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y' ">
          <b-form-input class="d-inline col-5" v-model="row.item.f2Account"/>
        </div>
        <div v-else>
          {{ row.item.f2Account }}
        </div>
      </template>

      <template #cell(f2UnitName)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-select v-model="row.item.f2UnitName" :options="bpmDeptsOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.f2UnitName, bpmDeptsOptions) }}
        </div>
      </template>

      <template #cell(f2Pename)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-input class="d-inline col-5" v-model="row.item.f2Pename"/>
        </div>
        <div v-else >
          {{ row.item.f2Pename }}
        </div>
      </template>

      <template #cell(f2Posname)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-select v-model="row.item.f2Posname" :options="bpmTitleOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.f2Posname, bpmTitleOptions) }}
        </div>
      </template>

      <template #cell(action)="row">
        <b-button class="ml-2" style="background-color: #17a2b8; color: white" variant="outline-secondary"
                  v-show="formStatus === FormStatusEnum.READONLY" @click="changeEdit(row.item)">編輯
        </b-button>
        <b-button class="ml-2" style="background-color: #17a2b8; color: white" variant="outline-secondary"
                  v-show="formStatus === FormStatusEnum.READONLY" @click="deletePecard(row.item.pecard)">刪除
        </b-button>
        <b-input-group>
          <b-button class="ml-2" style="background-color: #17a2b8; color: white" variant="outline-secondary"
                    v-show="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'"
                    @click="toEdit(row.item) ">送出
          </b-button>
          <b-button class="ml-2" style="background-color: #17a2b8; color: white" variant="outline-secondary"
                    v-show="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'"
                    @click="cancelEdit(row.item) ">取消
          </b-button>
        </b-input-group>
      </template>
    </i-table>

  </div>
</template>

<script lang="ts">
import {reactive, ref, watch} from '@vue/composition-api';
import axios from "axios";
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import {useGetters} from "@u3u/vue-hooks";
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';
import {useValidation} from '@/shared/form';
import ITable from '@/shared/i-table/i-table.vue';
import {changeCodeNoToCh} from "@/shared/word/directions";
import {useBvModal} from "@/shared/modal";


export default {
  name: "setSupervisor",//表單特例管理
  components: {
    IFormGroupCheck,
    ITable,
  },
  setup() {

    //是否顯示iTable
    const queryStatus = ref(false);

    //是否顯示新增的模板
    const newDataStatus = ref(false);

    //職稱下拉選單資訊
    const bpmTitleOptions = ref(useGetters(['getBpmTitleOptions']).getBpmTitleOptions).value;

    //單位下拉選單資訊
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;
    const $bvModal = useBvModal();

    //用來切換畫面上要顯示哪個區塊的模板
    const formStatus = ref('');

    //切換新增 取消
    const isAdding = ref(true);

    //切換畫面上的新增按鈕要顯示 新增還是取消
    const buttonText = ref('新增');

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
      queryPecard: '', //職稱
      pecard: '', //申請者員工編號
      unitName: '', //申請者單位名稱
      pename: '', //申請者姓名
      posname: '', //申請者職稱
      f1UnitName: '', //第一層上級單位
      f1Account: '', //第一層上級員工編號
      f1Pename: '', //第一層上級姓名
      f1Posname: '', //第一層上級職稱
      f2UnitName: '', //第二層上級單位
      f2Account: '', //第二層上級員工編號
      f2Pename: '', //第二層上級姓名
      f2Posname: '', //第二層上級職稱
    };

    const form = reactive(Object.assign({}, formDefault));

    // 表單物件驗證規則
    const rules = ref({
      queryPecard: {},
      pecard: {},
      unitName: {},
      pename: {},
      posname: {},
      f1UnitName: {},
      f1Account: {},
      f1Pename: {},
      f1Posname: {},
      f2UnitName: {},
      f2Account: {},
      f2Pename: {},
      f2Posname: {},
    });

    const {$v, checkValidity, reset} = useValidation(rules, form, formDefault);

    const table = reactive({
      fields: [
        {
          key: 'pecard',
          label: '申請者員編',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'unitName',
          label: '申請者單位名稱',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : changeCodeNoToCh(value, bpmDeptsOptions)),
        },
        {
          key: 'pename',
          label: '申請者姓名',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'posname',
          label: '申請者職稱',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'f1Account',
          label: '第一層上級員編',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'f1UnitName',
          label: '第一層上級單位',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : changeCodeNoToCh(value, bpmDeptsOptions)),
        },

        {
          key: 'f1Pename',
          label: '第一層上級姓名',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'f1Posname',
          label: '第一層上級職稱',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'f2Account',
          label: '第二層上級員編',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'f2UnitName',
          label: '第二層上級單位',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : changeCodeNoToCh(value, bpmDeptsOptions)),
        },
        {
          key: 'f2Pename',
          label: '第二層上級姓名',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'f2Posname',
          label: '第二層上級職稱',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'action',
          label: '動作',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
      ],
      data: [],
      totalItems: 0,
    });

    //查詢所有表單特例管理
    const toQuery = () => {
      newDataStatus.value = false;
      isAdding.value = true;
      buttonText.value = '新增'
      formStatus.value = FormStatusEnum.READONLY
      table.data = undefined;
      const params = new URLSearchParams()
      params.append('pecard', form.queryPecard)
      axios
        .get(`/eip/setSupervisor?${params.toString()}`)
        .then(({data}) => {
          queryStatus.value = true;
          table.data = [];
          if (iTable.value) iTable.value.state.pagination.currentPage = 1;
          if (data.length < 1) return;
          table.data = data
        })
        .catch(notificationErrorHandler(notificationService));
    }

    //新增表單特例管理
    async function submitForm() {
      const isValid = await checkValidity();
      if (isValid) {
        if (form.pecard == '' || form.unitName == '' || form.pename == '' || form.posname == '') return await $bvModal.msgBoxConfirm('申請者資訊 不可為空');

        const isOK = await $bvModal.msgBoxConfirm('是否確認送出內容？');
        if (isOK) {
          let body = {
            pecard: form.pecard,
            unitName: form.unitName,
            pename: form.pename,
            posname: form.posname,
            f1UnitName: form.f1UnitName != '' ? form.f1UnitName : null,
            f1Account: form.f1Account != '' ? form.f1Account : null,
            f1Pename: form.f1Pename != '' ? form.f1Pename : null,
            f1Posname: form.f1Posname != '' ? form.f1Posname : null,
            f2UnitName: form.f2UnitName != '' ? form.f2UnitName : null,
            f2Account: form.f2Account != '' ? form.f2Account : null,
            f2Pename: form.f2Pename != '' ? form.f2Pename : null,
            f2Posname: form.f2Posname != '' ? form.f2Posname : null,
            f3Account: null,
            f3Pename: null,
            f3Posname: null,
          }
          axios
            .patch(`/eip/save/setSupervisor`, body)
            .then(({data}) => {
              $bvModal.msgBoxOk('表單新增完畢');
              reset();
              toQuery();
            })
            .catch(notificationErrorHandler(notificationService));
        }
      }
    }

    //編輯表單特例管理
    async function toEdit(item) {
      if (item.pecard == '' || item.unitName == '' || item.pename == '' || item.posname == '') return await $bvModal.msgBoxConfirm('申請者資訊 不可為空');

      const isOK = await $bvModal.msgBoxConfirm('是否確認送出修改內容？');

      if (isOK) {
        let body = {
          pecard: item.pecard,
          unitName: item.unitName,
          pename: item.pename,
          posname: item.posname,
          f1UnitName: item.f1UnitName != '' ? item.f1UnitName : null,
          f1Account: item.f1Account != '' ? item.f1Account : null,
          f1Pename: item.f1Pename != '' ? item.f1Pename : null,
          f1Posname: item.f1Posname != '' ? item.f1Posname : null,
          f2UnitName: item.f2UnitName != '' ? item.f2UnitName : null,
          f2Account: item.f2Account != '' ? item.f2Account : null,
          f2Pename: item.f2Pename != '' ? item.f2Pename : null,
          f2Posname: item.f2Posname != '' ? item.f2Posname : null,
          f3Account: null,
          f3Pename: null,
          f3Posname: null,
        }
        axios
          .patch(`/eip/save/setSupervisor`, body)
          .then(({data}) => {
            $bvModal.msgBoxOk('表單修改完畢');
            item.isEdit = 'N'
            formStatus.value = FormStatusEnum.READONLY
            reset();
            toQuery();
          })
          .catch(notificationErrorHandler(notificationService));
      }
    }


    //切換編輯模式
    function changeEdit(item) {
      item.isEdit = 'Y'
      formStatus.value = FormStatusEnum.MODIFY
    }

    //取消編輯模式
    function cancelEdit(item) {
      item.isEdit = 'N'
      formStatus.value = FormStatusEnum.READONLY
    }

    //切換新增 取消
    const toggleButton = () => {
      isAdding.value = !isAdding.value;
    };

    //刪除特例資料
   async function deletePecard(id) {
      const isOK = await $bvModal.msgBoxConfirm('是否刪除此特例？');
      if (isOK) {
        axios
          .delete(`/eip/delete/setSupervisor/${id}`)
          .then(({data}) => {
            $bvModal.msgBoxOk('表單特例刪除完畢');
            reset();
            toQuery();
          })
          .catch(notificationErrorHandler(notificationService));
      }
    }

    //清空畫面的所有值,清空iTable
    function toReset() {
      reset();
      table.data = [];
    }

    //切換新增 取消
    watch(isAdding, () => {
      buttonText.value = isAdding.value ? '新增' : '取消';
      newDataStatus.value = !isAdding.value;
    });

    return {
      $v,
      checkValidity,
      toQuery,
      toReset,
      bpmTitleOptions,
      queryStatus,
      table,
      iTable,
      toEdit,
      FormStatusEnum,
      formStatus,
      changeCodeNoToCh,
      bpmDeptsOptions,
      submitForm,
      changeEdit,
      reset,
      cancelEdit,
      newDataStatus,
      isAdding,
      toggleButton,
      buttonText,
      deletePecard
    }
  }
}
</script>

<style scoped>

</style>
