<template>
  <div>
    <section class="container mt-2">
      <div class="card" style="background-color: #8fd4ce">
        <div class="card-body">

          <!-- 新增附件 -->
          <div class="card context" style="background-color: #8fd4ce">
            <b-table
              class="table-sm"
              show-empty
              responsive
              bordered
              empty-text="無資料"
              :items="appendixData.appendix"
              :fields="appendixData.fields"
            >
<!--              <template #cell(index)="row">{{ row.index + 1 }}</template>-->

              <template #cell(file)="row">

                <b-form-file
                  v-model="row.item.file"
                  trim
                  multiple
                  browse-text="選擇檔案"
                  @change="upload($event,row.index,row.item)"
                ></b-form-file>
              </template>

              <template #cell(size)="row">
                <b-form-input v-model="row.item.size" maxlength="200"></b-form-input>
              </template>
              <template #cell(upDataTime)="row">
                <div v-model="row.item.upDataTime" >{{ formatToString(row.item.updateTime,'/') }}</div>
              </template>

              <template #cell(author)="row">
                <b-form-input v-model="row.item.author" maxlength="200"></b-form-input>
              </template>
              <template #cell(attachmentDescription)="row">
                <b-form-input v-model="row.item.attachmentDescription" maxlength="200"></b-form-input>
              </template>

              <template #cell(action)="row">

                <b-button class="submitFormBon"  @click="removeAnnouncement(row.index)"
                          v-if="appendixData.appendix.length > 1">刪除</b-button>
                <b-button class="submitFormBon"  @click="addAnnouncement"
                          v-if="row.index == appendixData.appendix.length - 1">新增</b-button>

              </template>
            </b-table>


            <b-col>
              <b-form-row class="justify-content-end">
                <b-button class="submitFormBon" @click="test">測試</b-button>
              </b-form-row>
            </b-col>

          </div>
        </div>
      </div>
    </section>
  </div>
</template>


<script lang="ts">
import {
  BCol,
  BFormRow,
  BLink,
  BListGroup,
  BListGroupItem,
  BTableSimple,
  BTbody,
  BTd,
  BTr,
  BTable,
  BFormInput,
  BFormSelect,
  BFormSelectOption,
  BFormFile,
  BButton,
} from "bootstrap-vue";
import IButton from '@/shared/buttons/i-button.vue';
import {onMounted, reactive,ref} from "@vue/composition-api";
import {AuthorModel} from "@/shared/model/qua/authorModel";
import IDatePicker from '@/shared/i-date-picker/i-date-picker.vue';
import axios from "axios";
import NotificationService from "@/shared/notification/notification-service";
import {useBvModal} from "@/shared/modal";
import {formatDate, formatToString} from "@/shared/date/minguo-calendar-utils";
import {loadImage} from "@/shared/file/image2";
export default {
  name: "appendix",
  components: {
    BButton,
    BCol,
    BFormRow,
    BLink,
    BTableSimple,
    BTbody,
    BTd,
    BTr,
    IButton,
    BListGroup,
    BListGroupItem,
    BTable,
    BFormInput,
    BFormSelect,
    BFormSelectOption,
    IDatePicker,
    BFormFile,
  },
  props: {},
  setup(props) {

    // const notificationService: NotificationService = useNotification();
    const $bvModal = useBvModal();
    onMounted(() => {
      doQuery();
    //   handleQuery();
    });

    const appendixData: { appendix: AuthorModel[] } = reactive({
      appendix: [],
      fields: [
        // {
        //   key: 'index',
        //   label: '序號',
        //   sortable: false,
        //   thStyle: 'width:5%',
        //   thClass: 'text-center',
        //   tdClass: 'text-center align-middle',
        // },
        {
          key: 'file',
          label: '附件名稱',
          sortable: false,
          thStyle: 'width:28%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'size',
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
          key: 'author',
          label: '作者',
          sortable: false,
          thStyle: 'width:10%',
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'attachmentDescription',
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

    // function downloadQuaFile(item) {
    //   axios
    //     .get('/service/adm-files/downloadFile/toQua/' + item.sourceId, {responseType: 'blob'})
    //     .then(res => {
    //       const content = String(res.headers['content-disposition']);
    //       const fileName = decodeURI(
    //         content
    //           .substring(content.lastIndexOf('filename*=') + 17)
    //           .replace(/"/g, '')
    //           .replace(/\+/g, '')
    //       );
    //       const extName = fileName.substring(fileName.lastIndexOf('.'));
    //
    //       // 檔案是pdf跳出預覽視窗，不是pdf則直接下載
    //       if (extName === '.pdf') {
    //         let blob = new Blob([res.data], {type: 'application/pdf'});
    //         let url = window.URL.createObjectURL(blob);
    //         pdfViewer.value.pdfSrc = url;
    //         pdfViewer.value.isShowDia(url, true);
    //       } else {
    //         downloadFile(res);
    //       }
    //     })
    //     // .catch(notificationErrorHandler(notificationService));
    // }

    function doQuery() {
      appendixData.appendix = [];
      // 給畫面的[新增附件:]預設值
      let authorModel = new AuthorModel()
      authorModel.updateTime = new Date();
      appendixData.appendix.push(authorModel);
    }

    function removeAnnouncement(index: number) {
      appendixData.appendix.splice(index, 1);
    }

    function addAnnouncement() {
      let authorModel = new AuthorModel()
      authorModel.updateTime = new Date();
      appendixData.appendix.push(authorModel);
    }

    async function test() {
      for (let i = 0; i < appendixData.appendix.length; i++) {

        console.log(' appendixData.appendix[i]', appendixData.appendix[i])
      }
    }

    async function upload(e,index,data) {
      data.file = e.target.files[0];
      data.size = (e.target.files[0].size/1024).toFixed(1)
    }

    // async function submitForm() {
    //
    //   for (let i = 0; i < appendixData.appendix.length; i++) {
    //
    //     if (appendixData.appendix[i].subject != null && (appendixData.appendix[i].quaUrl != null || (appendixData.appendix[i].file !== null && appendixData.appendix[i].file !== undefined))) {
    //
    //       let body = {
    //         subject: appendixData.appendix[i].subject,
    //         quaUrl: appendixData.appendix[i].quaUrl,
    //       };
    //
    //       const params = new FormData();
    //       params.append('dto', new Blob([JSON.stringify(body)], {type: 'application/json'}));
    //       if (appendixData.appendix[i].file !== null && appendixData.appendix[i].file !== undefined) {
    //         params.append('file', appendixData.appendix[i].file[0]);
    //       }
    //
    //       await axios
    //         .post('/qua-appendix/qua0304r1', params)
    //         .then(({data}) => {
    //           console.log('data', data)
    //
    //         })
    //         // .catch(notificationErrorHandler(notificationService));
    //     }else {
    //       return $bvModal.msgBoxOk('請確認至少輸入一個公告 + 任意連結或文件 !');
    //     }
    //   }
    //
    //   await $bvModal.msgBoxOk('儲存成功');
    //   await doQuery()
    // }

    return {
      appendixData,
      removeAnnouncement,
      addAnnouncement,
      // submitForm,
      test,
      formatToString,
      upload,
    };
  }
}
</script>

<style scoped>

.card {
  padding: 5px;
  margin: 0px auto 50px auto;
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

.card-header {
  background-color: #f7f7f7;
  border-bottom: 1px solid #dee2e6;
}

.card-body {
  padding: 15px;
}

.btn_login {
  background-color: transparent;
  border-style: none;
  color: blue;
}

.submitFormBon {
  color: #fff;
  background-color: #17a2b8;
  border-color: #17a2b8;
}

</style>
