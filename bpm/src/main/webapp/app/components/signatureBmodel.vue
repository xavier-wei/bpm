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


<!--                        <i-form-group-check class="col-4 mb-0" label-cols="5" content-cols="7" :label="`姓名：`"-->
<!--                                :item="$v.selectName">-->
<!--              &lt;!&ndash;姓名 :　&ndash;&gt;-->
<!--              <b-form-input v-model="$v.selectName.$model"/>-->
<!--            </i-form-group-check>-->

<!--            <i-form-group-check class="col-4" label-cols="5" content-cols="7" :label="`單位：`"-->
<!--                                :item="$v.selectUnit">-->
<!--              &lt;!&ndash;單位名稱&ndash;&gt;-->
<!--              <b-form-select v-model="$v.selectUnit.$model" :options="bpmDeptsOptions">-->
<!--                <template #first>-->
<!--                  <b-form-select-option value="" disabled>請選擇</b-form-select-option>-->
<!--                </template>-->
<!--              </b-form-select>-->
<!--            </i-form-group-check>-->

<!--            <i-form-group-check class="col-4" label-cols="5" content-cols="7" :label="`職稱：`"-->
<!--                                :item="$v.selectTitle">-->
<!--              &lt;!&ndash;姓名 :　&ndash;&gt;-->
<!--              <b-form-input v-model="$v.selectTitle.$model"/>-->
<!--            </i-form-group-check>-->

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

        <b-form-row>
          <i-form-group-check class="col-sm-12" label-cols="2" content-cols="8" :label="'加簽對象 : '"
                              :item="$v.chooseName">
            <div>{{ $v.chooseName.$model }} ({{ $v.chooseId.$model }})</div>

          </i-form-group-check>
          <i-form-group-check class="col-sm-12" label-cols="2" content-cols="8" :label="'單位 : '"
                              :item="$v.chooseUnit">
            <div>{{ changeDealWithUnit($v.chooseUnit.$model, bpmDeptsOptions) }}</div>
          </i-form-group-check>

        </b-form-row>
        <b-form-row>
          <i-form-group-check class="col-sm-12" label-cols="2" content-cols="8" :label="'職稱 : '"
                              :item="$v.chooseTitle">
            <div>{{ $v.chooseTitle.$model }}</div>
          </i-form-group-check>
        </b-form-row>

        <b-form-row>
          <i-form-group-check class="col-sm-12 fixedWidth" label-cols="2" content-cols="8" :label="'加簽理由 : '"
                              :item="$v.reason">
            <b-form-textarea v-model="$v.reason.$model" rows="1" maxlength="100" trim lazy/>
          </i-form-group-check>
        </b-form-row>



        <b-container class="mt-3">
          <b-row class="justify-content-center">

            <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                      variant="outline-secondary"
                      @click="signature" :disabled="$v.chooseId.$model === ''">加簽
            </b-button>
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
import {l410NameToFormUnit} from "@/shared/word/l410NameToFormUnit";

export default {
  name: "signatureBmodel",
  props: {
    formData: {
      required: false,
      type: Object,
    },
    taskData: {
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
    //接收與傳送值給父層
    const formDataProp = reactive(props.formData);

    //傳進來的taskData{processInstanceId、taskId、taskName、decisionRole、additional}
    const taskDataProp = reactive(props.taskData);

    //登入者資訊
    const userData = ref(useGetters(['getUserData']).getUserData).value;

    //單位下拉選單資訊
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;
    const iTable = ref(null);

    //是否顯示iTable
    const queryStatus = ref(false);

    //l410進來加簽時，需要傳給後端的物件
    const l410Form = ref({});
    const $bvModal = useBvModal();
    const notificationService = useNotification();
    // 區塊是否顯示
    const dialogIsVisible = reactive({
      step: false,
    });
    const title = '加簽人員清單';

    const formDefault = {
      chooseId: '', //選擇加簽的userId
      chooseName: '', //選擇加簽的user名稱
      chooseUnit: '', //選擇加簽的user單位
      chooseTitle: '', //選擇加簽的user職稱
      selectName: '', //查詢加簽的user名稱
      selectUnit: '', //查詢加簽的user單位
      selectTitle: '', //查詢加簽的user職稱
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
          table.data = data
          table.totalItems = data.length;
        })
        .catch(notificationErrorHandler(notificationService))
    }

    //關閉加簽選擇器
    function closeModal() {
      reset();
      dialogIsVisible.step = false;
    }

    //從父層呼叫此function，用來開啟加簽選擇器
    async function isShowDia(isVisible) {
      await signatureOptions();
      dialogIsVisible.step = isVisible;
    }

    //把從子層選到的資訊用formDataProp傳給父層
    function choose(i) {
      form.chooseId = i.pecard;
      form.chooseName = i.pename;
      form.chooseUnit = i.peunit;
      form.chooseTitle = i.title;
      if (formDataProp.formId.substring(0, 4) === 'L410') {
        l410Form.value = l410NameToFormUnit(taskDataProp.taskName, formDataProp, i.peunit);
      }
    }

    //送出加簽流程
    async function signature() {

      const isValid = await checkValidity();
      if (isValid) {
        const isOK = await $bvModal.msgBoxConfirm('是否送出加簽？');
        if (isOK) {
          let body = {
            mainFormId: formDataProp.formId,
            mainProcessInstanceId: formDataProp.processInstanceId,
            mainProcessTaskId: taskDataProp.taskId,
            requesterId: userData.empId,
            requester: userData.userName,
            additionalSignerId: form.chooseId,
            additionalSigner: form.chooseName,
            additionalSignReason: form.reason,
            processInstanceStatus: '0',
            taskName: taskDataProp.taskName,
          };

          let body1 = {
            "Additional": JSON.stringify(body)
          }

          const formData = new FormData();

          formData.append('form', new Blob([JSON.stringify(body1)], {type: 'application/json'}));

          //只有在加簽l410時會進來這裡面，L410需要再加簽的時候去更新表單，讓畫面的單位跟著變更
          if (l410Form.value !== null) {
            if (formDataProp.formId !== undefined) {
              if (formDataProp.formId.substring(0, 4) === 'L410') {
                formDataProp.l410Variables = []
                formData.append('bpmIsmsL410', new Blob([JSON.stringify(formDataProp)], {type: 'application/json'}));
              }
            }
          }

          axios
            .post(`/process/start/Additional`, formData)
            .then(({data}) => {
              $bvModal.msgBoxOk('加簽申請成功');
              navigateByNameAndParams('pending', {isReload: false, isNotKeepAlive: true});
            })
            .catch(notificationErrorHandler(notificationService));

        }
      }
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
      signature,
      bpmDeptsOptions,
      changeDealWithUnit,
      signatureOptions
    }
  }
}
</script>

<style>


.fixedWidth .custom-control-label{
  width: 100%;
}


</style>
