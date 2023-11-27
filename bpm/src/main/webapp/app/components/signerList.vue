<template>
  <div>
    <div class="card m-3" style="background-color: white">


      <div class="m-2">
        <P> 簽核流程資訊： </P>

        <b-table :items="signerListTable.data" :fields="signerListTable.fields" bordered responsive="sm">

          <template #cell(idAndName)="row">
            <template v-for="(staffList, index) in row.item.empIds">
              <p>
                員工編號: {{ staffList }}
                <span v-if="row.item.empNames && row.item.empNames[index]"> 姓名: {{ row.item.empNames[index] }}</span>
              </p>
            </template>
          </template>
        </b-table>

      </div>

      <div class="m-2">
        <b-table :items="signStatusTable.data" :fields="signStatusTable.fields" bordered responsive="sm">
          <template #cell(situation)="row">
            <div v-if="row.item.taskName === '申請人確認'">
              申請
            </div>
            <div v-else-if="row.item.taskName === '結束'" style="color: red">
              結束
            </div>
            <div v-else-if="row.item.taskName === '撤銷'" style="color: red">
              撤銷
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
    //單位下拉選單資訊
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;

    //接收父層傳過來的formId
    const formIdProp = toRef(props, 'formId');

    //接收父層傳過來的表單的模式
    const formStatusRef = toRef(props, 'formStatus');

    //傳進來的表單處理狀態 0處理中 1已完成 2註銷
    const processInstanceStatusRef = toRef(props, 'processInstanceStatus');

    //接收父層傳過來的處理意見物件
    const opinionProp = reactive(props.opinion);

    //暫存所有簽核流程資訊，在查完後端後會把所有data整理完附值給signerListTable，在給畫面的 b-table
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

    const signerListTable = reactive({
      fields: [
        {
          key: 'taskName',
          label: '簽核名稱',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
        },
        {
          key: 'idAndName',
          label: '員工編號/姓名',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
        },
      ],
      data: undefined,
      totalItems: undefined,
    });

    //取得指定表單的表單簽核紀錄
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
          } else if (processInstanceStatusRef.value === '3') {
            signStatusTable.data.push({
              taskName: '撤銷',
              signingDatetime: ''
            });
          }
        })
        .catch(notificationErrorHandler(notificationService));
    }

    //取得指定表單的簽核者清單
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
          signerListTable.data = signerList
        })
        .catch(notificationErrorHandler(notificationService));
    }

    //監聽父層傳來的formId，有變就會去後端取資料給前端
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
      signerListTable,
    }
  }
}
</script>

<style>
.table-bordered thead th, .table-bordered thead td {
  border-bottom-width: 2px;
  background-color: #b0ded4;
}
</style>
