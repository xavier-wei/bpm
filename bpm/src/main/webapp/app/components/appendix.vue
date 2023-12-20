<template>
  <div>

    <div class="bpm_background" v-show="applyAppendix">

      <!-- 新增附件 -->
      <div class="card-body bpm_background">

        <i-table
          ref="iTable"
          stacked="sm"
          striped
          class="test-table table-sm table-hover"
          :itemsUndefinedBehavior="'loading'"
          :items="appendixData.appendix"
          :fields="appendixData.fields"
          :totalItems="appendixData.totalItems"
          :is-server-side-paging="false"
          :hideNo="true"
          :hideCardHeader="true"
          :hideCardFooter="true"
        >

          <template #cell(file)="row">

            <b-form-file
              v-model="row.item.file"
              :accept="'.jpg,.jpeg,.png,.gif,.pdf,.doc,.docx,.odt,.xls,.xlsx,.ods'"
              trim
              multiple
              browse-text="選擇檔案"
              @change="upload($event,row.index,row.item)"
              style="text-align: left;"
            ></b-form-file>
          </template>

          <template #cell(fileSize)="row">
            <b-form-input v-model="row.item.fileSize" maxlength="200" disabled></b-form-input>
          </template>
          <template #cell(upDataTime)="row">
            <div v-model="row.item.upDataTime">{{ formatToString(row.item.updateTime, '/') }}</div>
          </template>

          <template #cell(authorName)="row">
            <b-form-input v-model="row.item.authorName" maxlength="200" disabled></b-form-input>
          </template>
          <template #cell(fileDescription)="row">
            <b-form-input v-model="row.item.fileDescription" maxlength="200"
                          :disabled="row.item.file === undefined || (Array.isArray(row.item.file) && row.item.file.length < 1)"></b-form-input>
          </template>

          <template #cell(action)="row">

            <i-button class="ml-2" type="trash"  @click="removeAnnouncement(row.index)"
                      v-if="appendixData.appendix.length > 1"/>

            <i-button class="ml-2" type="folder-plus"
                      v-if="row.index == appendixData.appendix.length - 1"
                      :disabled="row.item.file === undefined || (Array.isArray(row.item.file) && row.item.file.length < 1)"
                      @click="addAnnouncement"/>
          </template>


        </i-table>


        <b-container class="mt-3">
          <b-row class="justify-content-center">
            <i-button class="ml-2" type="x-circle"  @click="reset"/>
          </b-row>
        </b-container>

      </div>
    </div>

    <div class="bpm_background" v-show="readAppendix">
      <div class="card-body">

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
          :hideCardHeader="true"
          :hideCardFooter="true"
        >
          <template #cell(fileName)="row">
            <button class="btn_login" @click="downloadBpmFile(row.item)">
              {{ row.item.fileName }}
            </button>
          </template>

          <template #cell(action)="row">
            <i-button class="ml-2" type="trash" :disabled="formStatusRef !== FormStatusEnum.MODIFY"
                      @click="deleteFile(row.item.id)"/>
          </template>
        </i-table>

      </div>
    </div>

    <i-pdf-viewer ref="pdfViewer"/>
  </div>
</template>


<script lang="ts">

import IButton from '@/shared/buttons/i-button.vue';
import {onMounted, reactive, ref, toRef, watch,onActivated} from "@vue/composition-api";
import {FileModel} from "@/shared/model/bpm/fileModel";
import {formatToString} from "@/shared/date/minguo-calendar-utils";
import {useGetters} from "@u3u/vue-hooks";
import axios from 'axios';
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import {downloadFile} from "@/shared/formatter/common";
import IPdfViewer from "@/shared/report/i-pdf-viewer.vue";
import {useBvModal} from "@/shared/modal";
import ITable from "@/shared/i-table/i-table.vue";


export default {
  name: "appendix",//新增附件共用模組
  components: {
    IPdfViewer,
    ITable,
    'i-button': IButton,
  },
  props: {
    vData: {
      type: Object,
      required: false,
    },
    fileDataId: {
      type: Object,
      required: false,
    },
    formStatus: {
      type: String,
      required: false,
    },
  },
  setup(props) {
    const iTable = ref(null);

    //接收父層傳來的空物件
    let filePathNameProp = reactive(props.vData);

    //暫存畫面上的所有附件
    let appendixDataList = ref([]);

    //接收父層傳來的formId,再去後端查詢
    let fileDataIdProp = reactive(props.fileDataId);

    //進入表單的模式
    const formStatusRef = toRef(props, 'formStatus');

    //登入者資訊
    const userData = ref(useGetters(['getUserData']).getUserData).value;

    //附件是pdf的，用它來傳給IPdfViewer
    const pdfViewer = ref(null);

    //是否顯示 新增附件功能
    const applyAppendix = ref(false);

    //是否顯示 查看附件功能
    const readAppendix = ref(false);

    const notificationService = useNotification();
    const $bvModal = useBvModal();

    //列舉型別
    enum FormStatusEnum {
      CREATE = '新增',
      READONLY = '檢視',
      MODIFY = '編輯',
      VERIFY = '簽核'
    }

    onMounted(() => {
      //表單的模式是CREATE，只會顯示新增的元件
      if (formStatusRef.value === FormStatusEnum.CREATE) {
        doQuery();
        applyAppendix.value = true;

        //表單的模式是READONLY，只會顯示查看當前表單的所有附件
      } else if (formStatusRef.value === FormStatusEnum.READONLY) {
        doReadonly();
        readAppendix.value = true;

        //表單的模式是MODIFY或VERIFY，新增元件跟查看元件都會顯示
      } else if (formStatusRef.value === FormStatusEnum.MODIFY || formStatusRef.value === FormStatusEnum.VERIFY) {
        doQuery();
        doReadonly();
        applyAppendix.value = true;
        readAppendix.value = true;
      }
    });

    const appendixData: { appendix: FileModel[], fields: any, totalItems: any } = reactive({
      appendix: [],
      totalItems: 0,
      fields: [
        {
          key: 'file',
          label: '附件名稱',
          sortable: false,
          thStyle: 'width:28%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'fileSize',
          label: '大小',
          sortable: false,
          thStyle: 'width:7%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'upDataTime',
          label: '修改時間',
          sortable: false,
          thStyle: 'width:15%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'authorName',
          label: '作者',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'fileDescription',
          label: '附件說明',
          sortable: false,
          thStyle: 'width:30%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'action',
          label: '動作',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
      ],
    });

    const table = reactive({
      fields: [
        {
          key: 'action',
          label: '刪除',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
        },
        {
          key: 'fileName',
          label: '附件名稱',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
        },
        {
          key: 'fileSize',
          label: '大小(KB)',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
        },
        {
          key: 'authorName',
          label: '作者',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
        },
        {
          key: 'fileDescription',
          label: '附件說明',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle ',
        },
      ],
      data: undefined,
      totalItems: undefined,
    });

    //新增元件用的，會把所有參數清空、預設一條新增附件給畫面
    function doQuery() {
      appendixData.appendix = [];
      // 給畫面的[新增附件:]預設值
      let fileModel = new FileModel()
      fileModel.updateTime = new Date();
      fileModel.createTime = new Date();
      fileModel.authorName = userData.userName != null ? userData.userName : '';
      appendixData.appendix.push(fileModel);
    }

    //用父層傳來的formId去後端查詢表單有的所有附件
    function doReadonly() {
      table.data = undefined;
      table.data = [];

      if (fileDataIdProp.fileId !== '') {
        axios
          .get(`/eip/bpm-upload-files/formId/${fileDataIdProp.fileId}`)
          .then(({data}) => {
            if (data) {
              table.data.splice(0, table.data.length, ...data);
            }
          })
          .catch(notificationErrorHandler(notificationService));
      }
    }

    //刪除指定的那一條附件
    function removeAnnouncement(index: number) {
      appendixData.appendix.splice(index, 1);
    }

    //新增一條附件
    function addAnnouncement() {
      let fileModel = new FileModel()
      fileModel.updateTime = new Date();
      fileModel.createTime = new Date();
      fileModel.authorName = userData.userName != null ? userData.userName : '';
      appendixData.appendix.push(fileModel);
    }

    //選擇要上傳的附件
    async function upload(e, index, data) {

      // 設定最大檔案大小，單位為 KB，根據需要更改這個值
      const maxSize = 1024;

      const file = e.target.files[0];

      // 檢查檔案是否存在
      if (!file) {
        return;
      }
      // 檢查檔案大小
      const fileSizeKB = file.size / 1024;
      if (fileSizeKB > maxSize) {
        await $bvModal.msgBoxConfirm('檔案大小超過限制');
        // 清空文件輸入的值
        e.target.value = null;
        //初始化畫面的所有欄位值
        data.file = null;
        data.fileSize  = null;
        data.fileDescription  = undefined;
        data.updateTime = new Date();
        data.createTime = new Date();
        data.authorName = userData.userName != null ? userData.userName : '';
        return;
      }

      data.file = e.target.files[0];
      data.fileSize = (e.target.files[0].size / 1024).toFixed(1);
    }

    //清空所有附件並直接給一條新的預設附件
    function reset() {
      appendixData.appendix = [];
      appendixDataList.value = [];
      // 給畫面的[新增附件:]預設值
      let fileModel = new FileModel()
      fileModel.updateTime = new Date();
      fileModel.createTime = new Date();
      fileModel.authorName = userData.userName != null ? userData.userName : '';
      appendixData.appendix.push(fileModel);
    }

    //下載附件或直接用IPdfViewer開啟pdf
    function downloadBpmFile(item) {
      axios
        .get('/eip/bpm-upload-files/downloadFile/' + item.id, {responseType: 'blob'})
        .then(res => {
          const content = String(res.headers['content-disposition']);
          const fileName = decodeURI(
            content
              .substring(content.lastIndexOf('filename*=') + 17)
              .replace(/"/g, '')
              .replace(/\+/g, '')
          );
          const extName = fileName.substring(fileName.lastIndexOf('.'));

          // 檔案是pdf跳出預覽視窗，不是pdf則直接下載
          if (extName === '.pdf') {
            let blob = new Blob([res.data], {type: 'application/pdf'});
            let url = window.URL.createObjectURL(blob);
            pdfViewer.value.pdfSrc = url;
            pdfViewer.value.isShowDia(url, true);
          } else {
            downloadFile(res);
          }
        })
        .catch(notificationErrorHandler(notificationService));
    }

    //刪除所點選的附件檔案
    async function deleteFile(id) {
      const isOK = await $bvModal.msgBoxConfirm('是否刪除檔案？');
      if (isOK) {
        axios
          .delete(`/eip/delete/bpmUploadFiles/${id}`)
          .then(({data}) => {
            doReadonly();
          })
          .catch(notificationErrorHandler(notificationService));
      }
    }

    //先監聽初始的appendixData，如果有新增了附件就把它放進appendixDataList
    watch(appendixData, () => {
        appendixDataList.value = [];
        appendixData.appendix.forEach(i => {
          if (i.file !== undefined) {
            if (i.file.length > 0) {
              appendixDataList.value.push(i);
            }
          }
        })
      },
      {immediate: true}
    )

    //appendixDataList如果有值，就把appendixDataList指定給filePathNameProp 傳給父層
    watch(appendixDataList, () => {
        Object.assign(filePathNameProp, appendixDataList)
      },
      {immediate: true}
    )

    //監聽所有props.formStatus內的物件，根據父層傳的模式，顯示不同的畫面
    watch(() => props.formStatus, (newValue) => {
        if (newValue === FormStatusEnum.CREATE) {
          applyAppendix.value = true;
          doQuery();
        } else if (newValue === FormStatusEnum.READONLY) {
          doReadonly();
          readAppendix.value = true;
        } else if (newValue === FormStatusEnum.MODIFY || newValue === FormStatusEnum.VERIFY) {
          applyAppendix.value = true;
          readAppendix.value = true;
          doQuery();
          doReadonly();
        }
    },
      {immediate: true}
    )

    return {
      appendixData,
      removeAnnouncement,
      addAnnouncement,
      formatToString,
      upload,
      reset,
      table,
      formStatusRef,
      FormStatusEnum,
      downloadBpmFile,
      pdfViewer,
      applyAppendix,
      readAppendix,
      deleteFile,
      iTable
    };
  }
}
</script>

<style scoped>

.card {
  padding: 5px;
  margin: 0 auto 50px auto;
}

.context {
  text-align: justify;
  padding: 15px 20px;
  border: none;
  font-size: 1.2em;
}

.container {
  margin: auto;
  /* max-width: 768px; */
}

.card-body {
  padding: 15px;
}

.submitFormBon {
  color: #fff;
  background-color: #17a2b8;
  border-color: #17a2b8;
}

.btn_login {
  background-color: transparent;
  border-style: none;
  color: blue;
}

.card {
  position: relative;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #d3ede8;
  background-clip: border-box;
  border: 1px solid rgba(0, 0, 0, 0.125);
  border-radius: 0.15rem;
}


</style>
