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

        <div class="card-body clo-12" style="background-color: #d3ede8">
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
              <b-button class="ml-2" style="background-color: #17a2b8" @click="signatureOptions()">查詢</b-button>
              <b-button class="ml-2" style="background-color: #17a2b8" @click="reset()">清除</b-button>
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
            <b-button class="ml-1" style="background-color: #17a2b8"
                      @click="choose(row.item)">選擇
            </b-button>
          </template>
        </i-table>

        <b-container class="mt-3">
          <b-row class="justify-content-center">
            <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                      variant="outline-secondary" @click="closeModal()">關閉
            </b-button>
          </b-row>
        </b-container>
      </b-modal>
    </b-container>
  </div>
</template>

<script lang="ts">
import {reactive, ref} from "@vue/composition-api";
import {useValidation} from "@/shared/form";
import {changeDealWithUnit} from "@/shared/word/directions";
import axios from "axios";
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import IDatePicker from "@/shared/i-date-picker/i-date-picker";
import ITable from '@/shared/i-table/i-table';
import IFormGroupCheck from '@/shared/form/i-form-group-check';
import {useGetters} from "@u3u/vue-hooks";
import {useBvModal} from "@/shared/modal";
import {navigateByNameAndParams} from "@/router/router";
import { Pagination } from "@/shared/pagination.model";

export default {
  name: "errandBmodel",
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
  },
  setup(props) {

    const formDataProp = reactive(props.formData);
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;
    const iTable = ref(null);
    const queryStatus = ref(false);
    const notificationService = useNotification();
    // 區塊是否顯示
    const dialogIsVisible = reactive({
      step: false,
    });
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

    function closeModal() {
      reset();
      dialogIsVisible.step = false;
    }

    async function isShowDia(isVisible) {
      await signatureOptions();
      dialogIsVisible.step = isVisible;
    }

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
      changeDealWithUnit,
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
