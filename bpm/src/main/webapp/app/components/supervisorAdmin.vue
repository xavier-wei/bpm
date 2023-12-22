<template>
  <div>
    <div>
      <div class="bpm_form_header">
        <b-row class="d-flex">
          <p class="ml-4" style="color: white">表單簽核上級管理</p>
        </b-row>
      </div>
    </div>

    <div class="card-body clo-12" style="background-color: #d3ede8">
      <b-form-row>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="4"
          content-cols-md="8"
          label-align-md="right"
          :label="'申請者職稱：'"
          :item="$v.title"
        >
          <b-form-select v-model="$v.title.$model" :options="bpmTitleOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>

      </b-form-row>

      <div class="text-center mt-1 mb-1">
        <i-button class="ml-2 mb-1" :type="buttonIcon" @click="toggleButton()"/>
        <i-button class="ml-2 mb-1" type="search"  @click="toQuery()"/>
        <i-button class="ml-2 mb-1" type="x-circle"  @click="toReset()"/>
      </div>
    </div>

    <div class="card-body clo-12" style="background-color: #d3ede8" v-show="newDataStatus">
      <b-form-row>
        <i-form-group-check
          class="col-sm-4 mb-0"
          label-cols-md="4"
          content-cols-md="8"
          label-align-md="right"
          :label="'申請者單位：'"
          :item="$v.appUnit"
        >
          <b-form-select v-model="$v.appUnit.$model" :options="bpmDeptsOptions">
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
          :label="'申請者職稱：'"
          :item="$v.appTitle"
        >
          <b-form-select v-model="$v.appTitle.$model" :options="bpmTitleOptions">
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
          :label="'第一層上級單位：'"
          :item="$v.firstLayerUnit"
        >
          <b-form-select v-model="$v.firstLayerUnit.$model" :options="bpmDeptsOptions">
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
          :label="'第一層上級職稱：'"
          :item="$v.firstLayerSupervisor"
        >
          <b-form-select v-model="$v.firstLayerSupervisor.$model" :options="bpmTitleOptions">
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
          :label="'第二層上級單位：'"
          :item="$v.secondLayerUnit"
        >
          <b-form-select v-model="$v.secondLayerUnit.$model" :options="bpmDeptsOptions">
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
          :label="'第二層上級職稱：'"
          :item="$v.secondLayerSupervisor"
        >
          <b-form-select v-model="$v.secondLayerSupervisor.$model" :options="bpmTitleOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </i-form-group-check>
      </b-form-row>

      <b-container class="mt-3">
        <b-row class="justify-content-center">
          <i-button class="ml-2" type="send-check"  @click="submitForm()"/>
          <i-button class="ml-2" type="x-circle"  @click="reset()"/>
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
      <template #cell(appUnit)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y' ">
          <b-form-select v-model="row.item.appUnit" :options="bpmDeptsOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.appUnit, bpmDeptsOptions) }}
        </div>
      </template>

      <template #cell(appTitle)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-select v-model="row.item.appTitle" :options="bpmTitleOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.appTitle, bpmTitleOptions) }}
        </div>
      </template>

      <template #cell(firstLayerUnit)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-select v-model="row.item.firstLayerUnit" :options="bpmDeptsOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.firstLayerUnit, bpmDeptsOptions) }}
        </div>
      </template>

      <template #cell(firstLayerSupervisor)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-select v-model="row.item.firstLayerSupervisor" :options="bpmTitleOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.firstLayerSupervisor, bpmTitleOptions) }}
        </div>
      </template>

      <template #cell(secondLayerUnit)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-select v-model="row.item.secondLayerUnit" :options="bpmDeptsOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.secondLayerUnit, bpmDeptsOptions) }}
        </div>
      </template>

      <template #cell(secondLayerSupervisor)="row">
        <div v-if="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'">
          <b-form-select v-model="row.item.secondLayerSupervisor" :options="bpmTitleOptions">
            <template #first>
              <option value="">請選擇</option>
            </template>
          </b-form-select>
        </div>
        <div v-else>
          {{ changeCodeNoToCh(row.item.secondLayerSupervisor, bpmTitleOptions) }}
        </div>
      </template>

      <template #cell(action)="row">
        <i-button class="ml-2" type="pencil-square"  v-show="formStatus === FormStatusEnum.READONLY" @click="changeEdit(row.item)"/>

        <b-input-group>
          <i-button class="ml-2" type="send-check" v-show="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'" @click="toEdit(row.item) "/>
          <i-button class="ml-2" type="arrow-counterclockwise" v-show="formStatus === FormStatusEnum.MODIFY && row.item.isEdit === 'Y'" @click="cancelEdit(row.item)"/>
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
import IButton from '@/shared/buttons/i-button.vue';

export default {
  name: "supervisorAdmin",//表單簽核上級管理
  components: {
    IFormGroupCheck,
    ITable,
    'i-button': IButton,
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

    //用來切換畫面上要顯示哪個區塊的模板
    const formStatus = ref('')

    //切換新增 取消
    const isAdding = ref(true);

    //切換畫面上的新增按鈕要顯示 新增還是取消
    const buttonText = ref('新增');
    //切換畫面上的按鈕圖示
    const buttonIcon = ref('file-earmark-plus');

    const iTable = ref(null);
    const $bvModal = useBvModal();
    const notificationService = useNotification();

    //列舉型別
    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY = '簽核'
    }

    const formDefault = {
      title: '', //職稱
      appUnit: '', //申請者單位
      appTitle: '', //申請者職稱
      firstLayerUnit: '', //第一層上級單位
      firstLayerSupervisor: '', //第一層上級職稱
      secondLayerUnit: '', //第二層上級單位
      secondLayerSupervisor: '', //第二層上級職稱
    };

    const form = reactive(Object.assign({}, formDefault));

    // 表單物件驗證規則
    const rules = ref({
      title: {},
      appUnit: {},
      appTitle: {},
      firstLayerUnit: {},
      firstLayerSupervisor: {},
      secondLayerUnit: {},
      secondLayerSupervisor: {},
    });

    const {$v, checkValidity, reset} = useValidation(rules, form, formDefault);

    const table = reactive({
      fields: [
        {
          key: 'appUnit',
          label: '申請者單位',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : changeCodeNoToCh(value, bpmDeptsOptions)),
        },
        {
          key: 'appTitle',
          label: '申請者職稱',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'firstLayerUnit',
          label: '第一層上級單位',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : changeCodeNoToCh(value, bpmDeptsOptions)),
        },
        {
          key: 'firstLayerSupervisor',
          label: '第一層上級職稱',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'secondLayerUnit',
          label: '第二層上級單位',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          formatter: value => (value == undefined ? '' : changeCodeNoToCh(value, bpmDeptsOptions)),
        },
        {
          key: 'secondLayerSupervisor',
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
      params.append('title', form.title)
      axios
        .get(`/eip/supervisorAdmin?${params.toString()}`)
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
        if (form.appUnit == '' || form.appTitle == '') return await $bvModal.msgBoxConfirm('申請者單位、申請者職稱 不可為空');

        const isOK = await $bvModal.msgBoxConfirm('是否確認送出內容？');
        if (isOK) {
          let body = {
            appUnit: form.appUnit,
            appTitle: form.appTitle,
            firstLayerUnit: form.firstLayerUnit != '' ? form.appTitle : null,
            firstLayerSupervisor: form.firstLayerSupervisor != '' ? form.firstLayerSupervisor : null,
            secondLayerUnit: form.secondLayerUnit != '' ? form.secondLayerUnit : null,
            secondLayerSupervisor: form.secondLayerSupervisor != '' ? form.secondLayerSupervisor : null,
            thirdLayerUnit: null,
            thirdLayerSupervisor: null
          }
          axios
            .patch(`/eip/save/supervisorAdmin`, body)
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
      if (form.appUnit == '' || form.appTitle == '') return await $bvModal.msgBoxConfirm('申請者單位、申請者職稱 不可為空');
      const isOK = await $bvModal.msgBoxConfirm('是否確認送出修改內容？');
      if (isOK) {
        let body = {
          appUnit: item.appUnit,
          appTitle: item.appTitle,
          firstLayerUnit: item.firstLayerUnit,
          firstLayerSupervisor: item.firstLayerSupervisor,
          secondLayerUnit: item.secondLayerUnit,
          secondLayerSupervisor: item.secondLayerSupervisor,
          thirdLayerUnit: null,
          thirdLayerSupervisor: null
        }

        axios
          .patch(`/eip/save/supervisorAdmin`, body)
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

    //清空畫面的所有值,清空iTable
    function toReset() {
      reset();
      table.data = [];
    }

    //切換新增 取消、切換按鈕圖示
    watch(isAdding, () => {
      buttonText.value = isAdding.value ? '新增' : '取消';
      buttonIcon.value = isAdding.value ? 'file-earmark-plus' : 'arrow-counterclockwise';
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
      buttonIcon
    }
  }
}
</script>

<style scoped>

</style>
