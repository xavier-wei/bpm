<template>
  <div>

    <div class="m-4">
      <P> 簽核流程資訊： </P>
      <b-input-group>
        <p class="col-4">申請人所屬之科長：</p>
        <p class="col-3">員工編號：2456</p>
        <p class="col-3">姓名：張科長</p>
      </b-input-group>
      <b-input-group>
        <p class="col-4">申請人所屬之單位主管：</p>
        <p class="col-3">員工編號：2543</p>
        <p class="col-3"> 姓名：王處長</p>
      </b-input-group>
      <b-input-group>
        <p class="col-4">資訊小組承辦人：</p>
        <p class="col-3">員工編號：2256</p>
        <p class="col-3">姓名：楊資安</p>
      </b-input-group>
      <b-input-group>
        <p class="col-4">資訊小組簡任技正/科長：</p>
        <p class="col-3">員工編號：2273</p>
        <p class="col-3">姓名：蔡簡任</p>
      </b-input-group>
      <b-input-group>
        <p class="col-4">機房操作人員：</p>
        <p class="col-3">員工編號：2145</p>
        <p class="col-3">姓名：王操作</p>
      </b-input-group>
      <b-input-group>
        <p class="col-4">複核人員：</p>
        <p class="col-3">員工編號：2369</p>
        <p class="col-3">姓名：李複核</p>
      </b-input-group>
      <b-input-group>
        <p class="col-4">機房管理人員：</p>
        <p class="col-3">員工編號：2038</p>
        <p class="col-3">姓名：張管理</p>
      </b-input-group>
    </div>

    <div class="m-2">
      <b-table sticky-header :items="table.data" :fields="table.fields" bordered responsive="sm">

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

      </b-table>

      <div class="m-1" v-show="formStatusRef === FormStatusEnum.VERIFY">
        <P> 填寫意見： </P>
      </div>

      <b-form-textarea v-model="opinionProp.opinionData" rows="1" maxlength="2000" trim lazy
                       v-show="formStatusRef === FormStatusEnum.VERIFY"/>
    </div>


  </div>
</template>

<script lang="ts">
import {onActivated, onMounted, reactive, Ref, ref, toRef, toRefs, watch} from '@vue/composition-api';
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
  },
  setup(props){

    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;

    const formIdProp = toRef(props, 'formId');
    const formStatusRef = toRef(props, 'formStatus');
    let opinionProp = reactive(props.opinion);

    console.log('formIdProp',formIdProp)
    console.log('formStatusRef',formStatusRef)

    const notificationService = useNotification();

    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY = '簽核'
    }

    const table = reactive({
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
          formatter: value => (value === undefined ? '' : changeDealWithUnit(value, bpmDeptsOptions)),
        },
        {
          key: 'signingDatetime',
          label: '處理日期時間',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
          formatter: value => (value === undefined ? '' : newformatDate(new Date(value), '/')),
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
      console.log('id',id)
      axios
        .get(`/eip/getBpmSignStatus/${id}`)
        .then(({data}) => {
          console.log('/getBpmSignStatus:: ', data)
          if (data.length === 0) return;
          table.data = data;

          data.forEach(i => {
            if (i.taskName === '機房管理人員') {
              table.data.push({
                taskName: '結束',
              });
            }
          })
        })
        .catch(notificationErrorHandler(notificationService));
    }


    watch(formIdProp,(value)=>{
        getBpmSignStatus(value);
    },
      {immediate: true}
    )

    return{
      table,
      FormStatusEnum,
      formIdProp,
      formStatusRef,
      opinionProp,

    }
  }
}
</script>

<style scoped>

</style>
