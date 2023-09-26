<template>
  <div>
    <div class="card m-3" style="background-color: white">


      <div class="m-4">
        <P> 簽核流程資訊： </P>
        <div v-for="signer in signerList" :key="signer.taskName">
          <b-input-group>
            <p class="col-3">{{ signer.taskName }}</p>

            <template v-for="(ddd, index) in signer.empIds">
              <p class="col-4">
                員工編號: {{ ddd }}
                <span v-if="signer.empNames && signer.empNames[index]"> 姓名: {{ signer.empNames[index] }}</span>
              </p>
            </template>
          </b-input-group>
        </div>

      </div>

      <div class="m-2">
        <b-table sticky-header :items="signStatusTable.data" :fields="signStatusTable.fields" bordered responsive="sm">

          <template #cell(situation)="row">
            <div v-if="row.item.taskName === '申請人確認'">
              申請
            </div>
            <div v-else-if="row.item.taskName === '結束'" style="color: red">
              結束
            </div>
            <div v-else>
              處理
            </div>
          </template>

          <template #cell(signUnit)="row">
            <b-input-group>
              <div v-if="!!row.item.signUnit">
                {{ changeDealWithUnit(row.item.signUnit, bpmDeptsOptions) }}
              </div>
              <div v-if="!!row.item.signUnit">/</div>
              <div v-if="!!row.item.signer">
                {{ row.item.signer }}
              </div>
            </b-input-group>
          </template>

        </b-table>

        <div class="m-1" v-show="formStatusRef === FormStatusEnum.VERIFY">
          <P> 填寫意見： </P>
        </div>

        <b-form-textarea v-model="opinionProp.opinionData" rows="1" maxlength="2000" trim lazy
                         v-show="formStatusRef === FormStatusEnum.VERIFY"/>
      </div>

    </div>
  </div>
</template>

<script lang="ts">
import {reactive, ref, toRef, watch} from '@vue/composition-api';
import {changeDealWithUnit} from "@/shared/word/directions";
import {newformatDate} from '@/shared/date/minguo-calendar-utils';
import {useGetters} from "@u3u/vue-hooks";
import axios from "axios";
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";

export default {
  name: "signerList",
  props: {
    formId: {
      type: String,
      required: false,
    },
    formStatus: {
      required: false,
      type: String,
    },
    opinion: {
      type: Object,
      required: false,
    },
    processInstanceStatus: {
      required: false,
      type: String,
    },
  },
  setup(props) {

    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;
    const formIdProp = toRef(props, 'formId');
    const formStatusRef = toRef(props, 'formStatus');
    const processInstanceStatusRef = toRef(props, 'processInstanceStatus');
    const opinionProp = reactive(props.opinion);
    const signerList = ref([]);

    const notificationService = useNotification();

    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY = '簽核'
    }

    const signStatusTable = reactive({
      fields: [
        {
          key: 'situation',
          label: '狀況',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
        },
        {
          key: 'directions',
          label: '說明',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
        },
        {
          key: 'signUnit',
          label: '單位/姓名',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
        },
        {
          key: 'signingDatetime',
          label: '處理日期時間',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
          formatter: value => (value !== '' ? newformatDate(new Date(value), '/') : ''),
        },
        {
          key: 'opinion',
          label: '意見',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
        },
      ],
      data: undefined,
      totalItems: undefined,
    });


    function getBpmSignStatus(id) {

      signStatusTable.data = [];

      axios
        .get(`/eip/getBpmSignStatus/${id}`)
        .then(({data}) => {
          if (data.length === 0) return;

          signStatusTable.data = data;
          //判斷每張表單狀態
          if (processInstanceStatusRef.value === '1') {
            signStatusTable.data.push({
              taskName: '結束',
              signingDatetime: ''
            });
          } else if (processInstanceStatusRef.value === '2') {
            signStatusTable.data.push({
              taskName: '撤銷',
              signingDatetime: ''
            });
          }
        })
        .catch(notificationErrorHandler(notificationService));
    }

    function getFindByBpmSignerList(id) {

      signerList.value = [];

      axios
        .get(`/eip/getBpmSignerList/${id}`)
        .then(({data}) => {
          if (data.length <= 0) return;
          data.forEach(i => {
            let signer = {
              taskName: '',
              empIds: [],
              empNames: [],
            }

            signer.taskName = i.taskName
            signer.empIds = i.empIds.split(',')
            signer.empNames = i.empNames.split(',')
            signerList.value.push(signer)
          })
        })
        .catch(notificationErrorHandler(notificationService));
    }


    watch(formIdProp, (value) => {
        getBpmSignStatus(value);
        getFindByBpmSignerList(value);
      },
      {immediate: true}
    )

    return {
      signStatusTable,
      FormStatusEnum,
      formIdProp,
      formStatusRef,
      opinionProp,
      signerList,
      changeDealWithUnit,
      bpmDeptsOptions,
    }
  }
}
</script>

<style scoped>

</style>
