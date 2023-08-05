<template>
  <div>
    <section class="container mt-2">
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
  </div>
</template>


<script lang="ts">

import IButton from '@/shared/buttons/i-button.vue';
import {onMounted, reactive, ref, watch} from "@vue/composition-api";
import {FileModel} from "@/shared/model/qua/fileModel,";
import {formatToString} from "@/shared/date/minguo-calendar-utils";
import {useGetters} from "@u3u/vue-hooks";

export default {
  name: "appendix",
  components: {
    IButton,
  },
  props: {
    vData: {
      type: Object,
      required: false,
    },
  },
  setup(props) {

    let filePathNameProp = reactive(props.vData);

    const userData = ref(useGetters(['getUserData']).getUserData).value.user;
    let appendixDataList = ref([]);

    onMounted(() => {
      doQuery();
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

    function doQuery() {
      appendixData.appendix = [];
      // 給畫面的[新增附件:]預設值
      let fileModel = new FileModel()
      fileModel.updateTime = new Date();
      fileModel.createTime = new Date();
      fileModel.authorName = userData
      appendixData.appendix.push(fileModel);
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

    // function test() {
    //
    //   for (let i = 0; i < appendixData.appendix.length; i++) {
    //
    //     let body = {
    //       formId: 'L414-112070001',
    //       fileName: appendixData.appendix[i].file[0].name,
    //       fileSize: appendixData.appendix[i].fileSize,
    //       authorName: appendixData.appendix[i].authorName,
    //       fileDescription: appendixData.appendix[i].fileDescription,
    //     };
    //
    //     const params = new FormData();
    //     params.append('dto', new Blob([JSON.stringify(body)], {type: 'application/json'}));
    //     params.append('file', appendixData.appendix[i].file[0]);
    //
    //     axios
    //         .post('/eip/bpmUploadFile', params)
    //         .then(({data}) => {
    //
    //         })
    //         .catch(notificationErrorHandler(notificationService));
    //   }
    // }

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

    watch(appendixData, () => {

      appendixDataList.value = []
      appendixData.appendix.forEach(i => {
        if (i.file !== undefined) {
          if (i.file.length > 0) {
            appendixDataList.value.push(i)
          }
        }
      })
    })

    watch(appendixDataList, () => {
      Object.assign(filePathNameProp, appendixDataList)
    })

    return {
      appendixData,
      removeAnnouncement,
      addAnnouncement,
      formatToString,
      upload,
      reset,
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

</style>
