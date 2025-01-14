<template>
  <div>
    <b-container>
      <b-modal
        v-model="dialogIsVisible.step"
        id="bpm-unit-model"
        size="xl"
        :title="title"
        header-bg-variant="secondary"
        header-text-variant="light"
        hide-footer
        @close="closeModal"
      >

        <div class="card-body bpm_background clo-12" >
          <b-form-row>


            <b-form-group
              class="col-sm-4 mb-0"
              label-cols-md="4"
              content-cols-md="8"
              :label="`姓名：`"
              label-align-md="right"
              label-for="nameData"
            >
              <b-input-group>
                <b-form-input
                  id="nameData"
                  v-model.trim="$v.selectName.$model"
                  placeholder="請輸入關鍵字"
                  aria-label="請輸入關鍵字"
                ></b-form-input>
              </b-input-group>
            </b-form-group>

            <b-form-group
              class="col-sm-4 mb-0"
              label-cols-md="4"
              content-cols-md="8"
              :label="`單位：`"
              label-align-md="right"
              label-for="unitData"
            >
              <b-input-group>
                <b-form-select v-model="$v.selectUnit.$model" :options="bpmDeptsOptions">
                  <template #first>
                    <b-form-select-option value="" disabled>請選擇</b-form-select-option>
                  </template>
                </b-form-select>
              </b-input-group>
            </b-form-group>

            <b-form-group
              class="col-sm-4 mb-0"
              label-cols-md="4"
              content-cols-md="8"
              :label="`職稱：`"
              label-align-md="right"
              label-for="titleData"
            >
              <b-input-group>
                <b-form-input
                  id="titleData"
                  v-model.trim="$v.selectTitle.$model"
                  placeholder="請輸入關鍵字"
                  aria-label="請輸入關鍵字"
                ></b-form-input>
              </b-input-group>
            </b-form-group>
          </b-form-row>

          <b-container class="mt-3">
            <b-row class="justify-content-center">
              <i-button class="ml-2" type="search"  @click="signatureOptions()"/>
              <i-button class="ml-2" type="x-circle"  @click="reset()"/>
            </b-row>
          </b-container>
        </div>

        <i-table
          per-page="10"
          ref="iTable"
          stacked="sm"
          striped
          hide-card-header
          class="test-table table-sm table-hover"
          :itemsUndefinedBehavior="'loading'"
          :items="table.data"
          :fields="table.fields"
          :totalItems="table.totalItems"
          :is-server-side-paging="false"
          v-show="queryStatus"
        >
          <template #cell(action)="row">
            <i-button class="ml-2" type="record-circle"  @click="choose(row.item)"/>

          </template>
        </i-table>

        <b-container class="mt-3">
          <b-row class="justify-content-center">
            <i-button class="ml-2" type="stop" @click="closeModal()"/>
          </b-row>
        </b-container>
      </b-modal>
    </b-container>
  </div>
</template>

<script lang="ts">
import {reactive, ref} from "@vue/composition-api";
import {useValidation} from "@/shared/form";
import {changeCodeNoToCh} from "@/shared/word/directions";
import axios from "axios";
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import IDatePicker from "@/shared/i-date-picker/i-date-picker.vue";
import ITable from '@/shared/i-table/i-table.vue';
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';
import {useGetters} from "@u3u/vue-hooks";
import IButton from '@/shared/buttons/i-button.vue';

export default {
  name: "errandBmodel",//申請人選擇器
  props: {
    formData: {
      required: false,
      type: Object,
    },
  },
  components: {
    IDatePicker,
    ITable,
    IFormGroupCheck,
    'i-button': IButton,
  },
  setup(props) {

    //接收與傳送值給父層
    const formDataProp = reactive(props.formData);
    //單位下拉選單資訊
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;
    //是否顯示iTable
    const queryStatus = ref(false);
    // 區塊是否顯示
    const dialogIsVisible = reactive({
      step: false,
    });
    const iTable = ref(null);
    const notificationService = useNotification();
    const title = '差勤人員';
    const formDefault = {
      chooseId: '', //選擇差勤的userId
      chooseName: '', //選擇差勤的user名稱
      chooseUnit: '', //選擇差勤的user單位
      chooseTitle: '', //選擇差勤的user職稱
      selectName: '', //查詢差勤的user名稱
      selectUnit: '', //查詢差勤的user單位
      selectTitle: '', //查詢差勤的user職稱
      reason:'',
    };

    const form = reactive(Object.assign({}, formDefault));

    // 表單物件驗證規則
    const rules = ref({
      chooseId: {},
      chooseName: {},
      chooseUnit: {},
      chooseTitle: {},
      selectName: {},
      selectUnit: {},
      selectTitle: {},
      reason: {},
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
          key: 'orgName',
          label: '機關名稱',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'unitName',
          label: '單位名稱',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'pename',
          label: '姓名',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'pecard',
          label: '編號',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'title',
          label: '職稱',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
      ],
      data: [],
      totalItems: 0,
    });

    //從差勤撈出符合  PEUNIT != '600037'  PEORG = '360000000G'  PELEVDATE = '' 的所有人
    function signatureOptions() {
      table.data = [];
      const params = new URLSearchParams()
      params.append('selectName', form.selectName)
      params.append('selectUnit', form.selectUnit)
      params.append('selectTitle', form.selectTitle)

      axios.get(`/eip/signatureOptions?${params.toString()}`)
        .then(({data}) => {
          queryStatus.value = true;
          if (data.length > 0) {
            table.data = data;
            table.totalItems = data.length;
          }
        })
        .catch(notificationErrorHandler(notificationService))
    }

    //關閉申請人選擇器
    function closeModal() {
      reset();
      dialogIsVisible.step = false;
    }

    //從父層呼叫此function，用來開啟申請人選擇器
    async function isShowDia(isVisible) {
      await signatureOptions();
      dialogIsVisible.step = isVisible;
    }

    //把從子層選到的資訊用formDataProp傳給父層
    function choose(i) {
      reset();
      dialogIsVisible.step = false;
      formDataProp.appEmpid = i.pecard;
      formDataProp.appName = i.pename;
      formDataProp.appUnit = i.peunit;
    }

    return {
      $v,
      dialogIsVisible,
      title,
      closeModal,
      isShowDia,
      reset,
      table,
      iTable,
      queryStatus,
      choose,
      bpmDeptsOptions,
      changeCodeNoToCh,
      signatureOptions,
    }
  }
}
</script>

<style>


.fixedWidth .custom-control-label{
  width: 100%;
}


</style>
