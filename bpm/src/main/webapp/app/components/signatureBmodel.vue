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
            <i-form-group-check class="col-4" label-cols="5" content-cols="7" :label="`姓名：`"
                                :item="$v.selectName">
              <!--姓名 :　-->
              <b-form-input v-model="$v.selectName.$model"/>
            </i-form-group-check>

            <i-form-group-check class="col-4" label-cols="5" content-cols="7" :label="`單位：`"
                                :item="$v.selectUnit">
              <!--單位名稱-->
              <b-form-select v-model="$v.selectUnit.$model" :options="bpmDeptsOptions">
                <template #first>
                  <b-form-select-option value="" disabled>請選擇</b-form-select-option>
                </template>
              </b-form-select>
            </i-form-group-check>

            <i-form-group-check class="col-4" label-cols="5" content-cols="7" :label="`職稱：`"
                                :item="$v.selectTitle">
              <!--姓名 :　-->
              <b-form-input v-model="$v.selectTitle.$model"/>
            </i-form-group-check>

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
          <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'加簽對象 : '"
                              :item="$v.chooseName">
            <div>{{ $v.chooseName.$model }} ({{ $v.chooseId.$model }})</div>

          </i-form-group-check>
          <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'單位 : '"
                              :item="$v.chooseUnit">
            <div>{{ changeDealWithUnit($v.chooseUnit.$model, bpmDeptsOptions) }}</div>
          </i-form-group-check>

        </b-form-row>
        <b-form-row>
          <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'職稱 : '"
                              :item="$v.chooseTitle">
            <div>{{ $v.chooseTitle.$model }}</div>
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
import {newformatDate} from "@/shared/date/minguo-calendar-utils";
import {changeDealWithUnit} from "@/shared/word/directions";
import axios from "axios";
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import IDatePicker from "@/shared/i-date-picker/i-date-picker";
import ITable from "@/shared/i-table/i-table";
import IFormGroupCheck from "@/shared/form/i-form-group-check";
import {useGetters} from "@u3u/vue-hooks";
import {useBvModal} from "@/shared/modal";
import {navigateByNameAndParams} from "@/router/router";

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

    const formDataProp = reactive(props.formData);
    const taskDataProp = reactive(props.taskData);
    const userData = ref(useGetters(['getUserData']).getUserData).value;
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;
    const iTable = ref(null);
    const queryStatus = ref(false);
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
          // thStyle: 'width:20%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'unitName',
          label: '單位名稱',
          sortable: false,
          // thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'pename',
          label: '姓名',
          sortable: false,
          // thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'title',
          label: '職稱',
          sortable: false,
          // thStyle: 'width:10%',
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
          table.data = data
          table.totalItems = data.length;
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
      form.chooseId = i.pecard
      form.chooseName = i.pename
      form.chooseUnit = i.peunit
      form.chooseTitle = i.title
    }

    async function signature() {

      const isValid = await checkValidity();

      if (isValid) {
        const isOK = await $bvModal.msgBoxConfirm('是否送出加簽？');
        if (isOK) {
          let body = {
            mainFormId: formDataProp.formId,
            mainProcessInstanceId: formDataProp.processInstanceId,
            mainProcessTaskId: formDataProp.taskId,
            requesterId: userData.empId,
            requester: userData.userName,
            additionalSignerId: form.chooseId,
            additionalSigner: form.chooseName,
            additionalSignReason: formDataProp.opinion,
            processInstanceStatus: '0',
            taskName:taskDataProp.taskName,
          };

          console.log(' signatureBmodel.vue - signature - 268: ', JSON.parse(JSON.stringify(body)))

          let body1 = {
            "Additional": JSON.stringify(body)
          }

          const formData = new FormData();

          formData.append('form', new Blob([JSON.stringify(body1)], {type: 'application/json'}));

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

<style scoped>

</style>
