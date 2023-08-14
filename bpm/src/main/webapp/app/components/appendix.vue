<template>
  <div>
    <section class="container mt-2" v-show="applyAppendix">
      <div class="card" style="background-color: #d3ede8">
        <div class="card-body">

          <!-- 新增附件 -->
          <div class="card context" style="background-color: #d3ede8">
            <b-table
                class="table-sm"
                show-empty
                responsive
                bordered
                empty-text="無資料"
                :items="appendixData.appendix"
                :fields="appendixData.fields"
            >

              <template #cell(file)="row">

                <b-form-file
                    v-model="row.item.file"
                    trim
                    multiple
                    browse-text="選擇檔案"
                    @change="upload($event,row.index,row.item)"
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
                              :disabled="row.item.file === undefined"></b-form-input>
              </template>

              <template #cell(action)="row">

                <b-button class="submitFormBon" @click="removeAnnouncement(row.index)"
                          v-if="appendixData.appendix.length > 1">刪除
                </b-button>
                <b-button class="submitFormBon" @click="addAnnouncement"
                          v-if="row.index == appendixData.appendix.length - 1"
                          :disabled="row.item.file === undefined">新增
                </b-button>

              </template>
            </b-table>
          </div>

          <b-container class="mt-3">
            <b-row class="justify-content-center">
              <b-button class="ml-2" style="background-color: #17a2b8" @click="reset()">清除</b-button>
            </b-row>
          </b-container>

        </div>
      </div>
    </section>

    <section class="container mt-2" v-show="readAppendix">
      <div class="card" style="background-color: #d3ede8">
        <div class="card-body">

          <b-table sticky-header :items="table.data" :fields="table.fields" bordered responsive="sm">

            <template #cell(fileName)="row">
              <button class="btn_login" @click="downloadBpmFile(row.item)">
                {{ row.item.fileName }}
              </button>
            </template>

            <template #cell(action)="row">
              <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                        variant="outline-secondary"
                        :disabled="formStatusRef !== FormStatusEnum.MODIFY"
              >刪除
                <!--                        @click="submitDelete(row.item.id)"-->
              </b-button>
            </template>

          </b-table>

        </div>
      </div>
    </section>
    <i-pdf-viewer ref="pdfViewer"/>
  </div>
</template>


<script lang="ts">

import IButton from '@/shared/buttons/i-button.vue';
import {onMounted, reactive, ref, toRef, watch} from "@vue/composition-api";
import {FileModel} from "@/shared/model/qua/fileModel,";
import {formatToString} from "@/shared/date/minguo-calendar-utils";
import {useGetters} from "@u3u/vue-hooks";
import axios, {AxiosResponse} from 'axios';
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import {downloadFile} from "@/shared/formatter/common";
import IPdfViewer from "@/shared/report/i-pdf-viewer.vue";

export default {
  name: "appendix",
  components: {
    IButton,
    IPdfViewer,
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

    let filePathNameProp = reactive(props.vData);
    let fileDataIdProp = reactive(props.fileDataId);
    const formStatusRef = toRef(props, 'formStatus');
    const userData = ref(useGetters(['getUserData']).getUserData).value.user;
    let appendixDataList = ref([]);
    const notificationService = useNotification();
    const pdfViewer = ref(null);
    const applyAppendix = ref(false);
    const readAppendix = ref(false);


    enum FormStatusEnum {
      CREATE = '新增',
      READONLY = '檢視',
      MODIFY = '編輯',
      VERIFY = '簽核'
    }

    onMounted(() => {
      if (formStatusRef.value === FormStatusEnum.CREATE) {
        applyAppendix.value = true;
        doQuery();
      } else if (formStatusRef.value === FormStatusEnum.READONLY) {
        doReadonly();
        readAppendix.value = true;
      } else if (formStatusRef.value === FormStatusEnum.MODIFY) {
        applyAppendix.value = true;
        readAppendix.value = true;
        doQuery();
        doReadonly();
      } else if (formStatusRef.value === FormStatusEnum.VERIFY) {
        doReadonly();
        readAppendix.value = true;
      }
    });

    const appendixData: { appendix: FileModel[], fields: any } = reactive({
      appendix: [],
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
          // formatter: (value: string) => formatToString(value, '/', '-'),
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
          label: '大小',
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

    function doQuery() {
      appendixData.appendix = [];
      // 給畫面的[新增附件:]預設值
      let fileModel = new FileModel()
      fileModel.updateTime = new Date();
      fileModel.createTime = new Date();
      fileModel.authorName = userData
      appendixData.appendix.push(fileModel);
    }

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

    function removeAnnouncement(index: number) {
      appendixData.appendix.splice(index, 1);
    }

    function addAnnouncement() {
      let fileModel = new FileModel()
      fileModel.updateTime = new Date();
      fileModel.createTime = new Date();
      fileModel.authorName = userData
      appendixData.appendix.push(fileModel);
    }

    async function upload(e, index, data) {
      data.file = e.target.files[0];
      data.fileSize = (e.target.files[0].size / 1024).toFixed(1)
    }


    function reset() {
      appendixData.appendix = [];
      appendixDataList.value = []
      // 給畫面的[新增附件:]預設值
      let fileModel = new FileModel()
      fileModel.updateTime = new Date();
      fileModel.createTime = new Date();
      fileModel.authorName = userData
      appendixData.appendix.push(fileModel);
    }

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

    watch(appendixData, () => {

          appendixDataList.value = []
          appendixData.appendix.forEach(i => {
            if (i.file !== undefined) {
              if (i.file.length > 0) {
                appendixDataList.value.push(i)
              }
            }
          })
        },
        {immediate: true}
    )

    watch(appendixDataList, () => {
          Object.assign(filePathNameProp, appendixDataList)
        },
        {immediate: true}
    )

    watch(props, newValue => {
          if (formStatusRef.value === FormStatusEnum.CREATE) {
            applyAppendix.value = true;
            doQuery();
          } else if (formStatusRef.value === FormStatusEnum.READONLY) {
            doReadonly();
            readAppendix.value = true;
          } else if (formStatusRef.value === FormStatusEnum.MODIFY) {
            applyAppendix.value = true;
            readAppendix.value = true;
            doQuery();
            doReadonly();
          } else if (formStatusRef.value === FormStatusEnum.VERIFY) {
            doReadonly();
            readAppendix.value = true;
          }
        },
        {immediate: true}
    );

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
      readAppendix
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

</style>
