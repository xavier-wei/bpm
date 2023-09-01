<template>
  <div>
    <b-container>
      <section class="container mt-2">
          <b-card-body>
            <b-tabs>
              <b-tab title="表單" :active="activeTab(0)" @click="changeTabIndex(0)">

                <div style="background-color: #b0ded4;padding-top: 10px;">
                  <b-row class=" d-flex">
                    <p class="ml-4" style="color: white">
                      L410-共用系統使用者帳號申請單
                    </p>

                    <P class="ml-3">機密等級： 敏感</P>
                  </b-row>
                </div>

                <div class="card" style="background-color: #d3ede8 ">

                  <b-form-row>
                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'申請日期:'"
                                        :item="$v.applyDate">
                      <!--申請日期 : applyDate-->
                      <i-date-picker
                        placeholder="yyy/MM/dd"
                        v-model="$v.applyDate.$model"
                        :state="validateState($v.applyDate)"
                        lazy
                        trim
                        disabled
                      ></i-date-picker>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-3" label-cols="5" content-cols="7" :label="`表單編號：`"
                                        :item="$v.formId">
                      <!--表單編號 : formId -->
                      <b-form-input v-model="$v.formId.$model" disabled/>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-4" label-cols="5" content-cols="7" :label="`註：`">
                      <span class="text-danger">*</span>
                      <p style="margin-top: 10px">為申請必填欄位</p>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <p class="test1">申請者資訊</p>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'填表人：員工編號：'"
                                        :item="$v.filEmpid">
                      <!--填表人員工編號 : filEmpid-->
                      <b-form-input v-model="$v.filEmpid.$model" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-3" label-cols="5" content-cols="7" :label="`姓名：`"
                                        :item="$v.filName">
                      <!--填表人姓名 :　filName-->
                      <b-form-input v-model="$v.filName.$model" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-4" label-cols="5" content-cols="7" :label="`單位：`"
                                        :item="$v.filUnit">
                      <!--填表人單位名稱　: filUnit-->
                      <b-form-select v-model="$v.filUnit.$model" :options="bpmUnitOptions" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                        <template #first>
                          <b-form-select-option value="null" disabled>請選擇</b-form-select-option>
                        </template>
                      </b-form-select>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <p class="test1">一、個人基本資料：</p>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7"
                                        :label="'姓名(中文) ：'"
                                        :item="$v.appName">
                      <!--姓名(中文) : -->
                      <b-form-input v-model="$v.appName.$model" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                    </i-form-group-check>

                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`單位別 ：`"
                                        :item="$v.appUnit1">
                      <!--單位別 : -->
                      <b-form-select v-model="$v.appUnit1.$model" :options="bpmUnitOptions" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                        <template #first>
                          <b-form-select-option value="null" disabled>請選擇</b-form-select-option>
                        </template>
                      </b-form-select>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7"
                                        :label="'姓名(英文) ：'"
                                        :item="$v.appEngName">
                      <!--姓名(英文) : -->
                      <b-form-input v-model="$v.appEngName.$model" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                    </i-form-group-check>

                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`科別 ：`"
                                        :item="$v.appUnit2">
                      <!--科別 : -->
                      <b-form-input v-model="$v.appUnit2.$model" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7"
                                        :label="'員工編號 ：'"
                                        :item="$v.appEmpid">
                      <!--員工編號 : -->
                      <b-form-input v-model="$v.appEmpid.$model" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                    </i-form-group-check>

                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`職稱 ：`"
                                        :item="$v.position">
                      <!--職稱 : -->
                      <b-form-input v-model="$v.position.$model" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'分機 ：'"
                                        :item="$v.extNum">
                      <!--分機 : -->
                      <b-form-input v-model="$v.extNum.$model" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <p class="test1">二、 <span class="text-danger">*</span>申請事由：</p>
                  </b-form-row>

                  <b-form-row>
                    <!--申請事由-->
                    <b-form-radio-group class="offset-1" v-model="$v.appReason.$model" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                      <b-form-radio value="1">
                        <div style="height: 34px;">新進</div>
                      </b-form-radio>

                      <b-form-radio value="2">
                        <div style="height: 34px;">離職</div>
                      </b-form-radio>

                      <b-form-radio value="3">
                        <div style="height: 34px;">職務異動</div>
                      </b-form-radio>
                    </b-form-radio-group>

                  </b-form-row>

                  <b-form-row>
                    <b-input-group class="offset-1 mb-3">
                      <!--生效日期checkbox : isEnableDate-->
                      <b-form-checkbox class="col-2" v-model="$v.isEnableDate.$model" value="1" unchecked-value="0" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                        生效日期 :
                      </b-form-checkbox>
                      <!--生效日期 : enableDate-->
                      <i-date-picker
                        class="col-2"
                        placeholder="yyy/MM/dd"
                        v-model="$v.enableDate.$model"
                        :disabled="$v.isEnableDate.$model !== '1' || stateStatusRef || formStatusRef === FormStatusEnum.READONLY"
                        lazy
                        trim
                      ></i-date-picker>
                      <!--其他 checkbox : isOther-->
                      <b-form-checkbox class="col-2 offset-1" v-model="$v.isOther.$model" value="1" unchecked-value="0" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                        其他 :
                      </b-form-checkbox>
                      <!--其他說明 : otherReason-->
                      <b-form-input class="col-3" v-model="$v.otherReason.$model" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                    </b-input-group>
                  </b-form-row>

                  <hr/>

                  <b-form-row>
                    <p class="test1">三、系統申請/異動/停用項目：</p>
                  </b-form-row>


                  <div class="card context">
                    <b-table
                      class="table-sm"
                      show-empty
                      responsive
                      bordered
                      empty-text="無資料"
                      :items="table.data"
                      :fields="table.fields"
                    >

                      <template #cell(checkbox)="row">
                        <b-form-checkbox :value="'1'" :unchecked-value="false" v-model="row.item.checkbox" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                        {{row.item.checkbox}}
                      </template>

                      <!--申請項目-->
                      <template #cell(applyItem)="row">

                        <div v-if="row.item.applyVersion == '0'">
                          <div>系統名稱 :</div>
                          <b-form-input v-model="row.item.otherSys" maxlength="200" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"></b-form-input>
                          <div>帳號 :</div>
                          <b-form-input v-model="row.item.otherSysAccount" maxlength="200" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"></b-form-input>
                        </div>

                        <div v-else-if="row.item.applyVersion == '1'">
                          {{ row.item.systemApplyName }}
                        </div>

                        <div v-else-if="row.item.applyVersion == '2'">
                          {{ row.item.systemApplyName }}
                          <span v-if=" row.item.isColon === '1'"> : </span>
                          <b-form-input
                            maxlength="200" v-model="row.item.systemApplyInput" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                          </b-form-input>
                          <span v-if="row.item.systemApply === '4'" >@mail.pcc.gov.tw</span>
                        </div>

                        <div v-else-if="row.item.applyVersion == '3'">
                          {{ row.item.systemApplyName }}
                          <span v-if=" row.item.isColon === '1'"> : </span>
                          <b-form-checkbox-group v-model="$v.applyItem.$model" :options="options.type" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                        </div>

                        <div v-else-if="row.item.applyVersion == '4'">
                          <b-form-checkbox-group v-model="$v.webSiteList.$model" :options="options.webSite" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                        </div>

                      </template>

                      <!--處理權限-->
                      <template #cell(permissions)="row">

                        <div v-if="row.item.permissionsVersion == '0'" >
                          <b-form-radio-group v-model="row.item.sys" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                            <b-form-radio class="col-12" value="1">
                              <div>新增</div>
                            </b-form-radio>
                            <b-form-radio class="col-12" value="2">
                              <div>刪除</div>
                            </b-form-radio>
                            <b-form-radio class="col-12" value="3">
                              <div>帳號停用</div>
                            </b-form-radio>

                            <b-form-radio class="col-12" value="4">
                              <b-input-group>
                                <div>異動 :　</div>
                                <b-form-input maxlength="200" v-model="row.item.sysChange"
                                              :disabled="row.item.sys !== '3' || stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                              </b-input-group>
                            </b-form-radio>
                          </b-form-radio-group>
                        </div>

                        <div v-if="row.item.permissionsVersion == '1'" >
                          <b-form-radio-group v-model="row.item.sys" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                            <b-form-radio class="col-12" value="1">
                              <div>新增</div>
                            </b-form-radio>
                            <b-form-radio class="col-12" value="2">
                              <div>帳號停用</div>
                            </b-form-radio>
                            <b-form-radio class="col-12" value="3">
                              <b-input-group>
                                <div>異動 :　</div>
                                <b-form-input maxlength="200" v-model="row.item.sysChange"
                                              :disabled="row.item.sys !== '3'|| stateStatusRef || formStatusRef === FormStatusEnum.READONLY" />
                              </b-input-group>
                            </b-form-radio>
                          </b-form-radio-group>
                        </div>

                        <div v-if="row.item.permissionsVersion == '2'" >
                          <b-form-radio-group  v-model="row.item.sys" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                            <b-form-radio class="col-12" value="1">
                              <div>新增人員</div>
                            </b-form-radio>

                            <b-form-radio class="col-12" value="2">
                              <div>單位科別異動</div>
                            </b-form-radio>

                            <b-form-radio class="col-12" value="3">
                              <div>角色異動</div>
                            </b-form-radio>

                            <b-form-radio class="col-12" value="4">
                              <div>帳號停用</div>
                            </b-form-radio>

                            <b-form-radio class="col-12" value="5">
                              <b-input-group>
                                <div>異動 :　</div>
                                <b-form-input maxlength="200" v-model="row.item.sysChange"
                                              :disabled="row.item.sys !== '5' || stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                              </b-input-group>
                            </b-form-radio>
                          </b-form-radio-group>
                        </div>

                        <div v-if="row.item.permissionsVersion == '3'" >
                          <b-form-radio-group v-model="row.item.sys" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                            <b-form-radio class="col-12" value="1">
                              <div>新增</div>
                              <b-input-group>
                                <div>1.　</div>
                                <b-form-input maxlength="200" v-model="row.item.emailApply1"
                                              :disabled="row.item.sys !== '1' || stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                              </b-input-group>
                              <b-input-group>
                                <div>2.　</div>
                                <b-form-input maxlength="200" v-model="row.item.emailApply2"
                                              :disabled="row.item.sys !== '1' || stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                              </b-input-group>
                            </b-form-radio>

                            <p>(第一個帳號名稱若已有人設定,則設為第二個帳號)</p>

                            <b-form-radio class="col-12" value="2">
                              <div>刪除</div>
                            </b-form-radio>

                            <b-form-radio class="col-12" value="3">
                              <b-input-group>
                                <div>異動 :　</div>
                                <b-form-input maxlength="200" v-model="row.item.sysChange"
                                              :disabled="row.item.sys !== '3' || stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                              </b-input-group>
                            </b-form-radio>
                          </b-form-radio-group>
                        </div>

                        <div v-if="row.item.permissionsVersion == '4'" >
                          <b-form-radio-group v-model="row.item.sys" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                            <b-form-radio class="col-12" value="1">
                              <div>新增</div>
                            </b-form-radio>

                            <b-form-radio class="col-12" value="2">
                              <div>異動 :　</div>
                            </b-form-radio>

                            <div class="ml-1">
                              <b-form-checkbox value="1" unchecked-value="0" v-model="row.item.isUnitAdm"
                                               :disabled="row.item.sys !== '2' || stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                                部門管理者
                              </b-form-checkbox>

                              <b-form-checkbox value="1" unchecked-value="0" v-model="row.item.isUnitDataMgr"
                                               :disabled="row.item.sys !== '2' || stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                                部門資料維護者
                              </b-form-checkbox>

                              <b-form-checkbox value="1" unchecked-value="0" v-model="row.item.isWebSiteOther"
                                               :disabled="row.item.sys !== '2' || stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                                其他 :　
                              </b-form-checkbox>
                              <b-form-input maxlength="200" v-model="row.item.otherRemark"
                                            :disabled="row.item.isWebSiteOther !== '1' || stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                            </div>

                            <b-form-radio class="col-12" value="3">
                              <div>帳號停用</div>
                            </b-form-radio>
                          </b-form-radio-group>
                        </div>
                      </template>

                      <!--管理單位-->
                      <template #cell(managementUnit)="row">
                        <b-form-select :options="bpmUnitOptions" v-model="row.item.admUnit" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY">
                          <template #first>
                            <b-form-select-option value="null" disabled>請選擇</b-form-select-option>
                          </template>
                        </b-form-select>
                      </template>

                      <!--處理情形及生效日期-->
                      <template #cell(lastWork)="row">
                        <b-form-input maxlength="200" v-model="row.item.admStatus" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                        <i-date-picker
                          placeholder="yyy/MM/dd"
                          lazy
                          trim
                          v-model="row.item.admEnableDate"
                          :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"
                        ></i-date-picker>
                      </template>

                      <!--處理人員-->
                      <template #cell(reviewStaffName)="row">
                        <b-form-input maxlength="200" v-model="row.item.admName" :disabled=" stateStatusRef || formStatusRef === FormStatusEnum.READONLY"/>
                      </template>

                    </b-table>
                  </div>

                  <div class="m-5">
                    <P>
                      備註：
                    </P>
                    <P>
                      1.本會各資通系統使用者應遵守本會相關資通安全規定，倘有違反規定時，本會得逕行取消或停用其帳號。
                    </P>
                    <P>
                      2.本申請表奉核後，請送管理單位辦理，各管理單位完成權限賦予即以電話通知申請人。
                    </P>
                    <P>
                      3.第1次登入系統後，請立即修改使用者密碼，以免他人使用您的帳號作業。
                    </P>
                    <P>
                      4.使用者遇有職務異動時，應主動申請相關帳號異動或撤銷。
                    </P>
                    <P>
                      5.需用日期之填寫，新進人員請填到職日；離職人員請填離職日；人員異動請填單位異動生效日期。
                    </P>
                    <P>
                      6.帳號增刪及異動時依表單辦理。除系統管理者需主管核可，其他由申請單位科長核可。
                    </P>

                  </div>

                  <b-container class="mt-3">
                    <b-row class="justify-content-center">
                      <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                                variant="outline-secondary"
                                @click="submitForm('0')"
                                v-show="formStatusRef === FormStatusEnum.MODIFY">暫存
                      </b-button>
                      <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                                variant="outline-secondary"
                                @click="submitForm('1')"
                                v-show="formStatusRef === FormStatusEnum.MODIFY">送出
                      </b-button>
                      <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                                variant="outline-secondary"
                                @click="reviewStart('1')"
                                v-show="formStatusRef === FormStatusEnum.VERIFY">同意
                      </b-button>
                      <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                                variant="outline-secondary"
                                @click="reviewStart('0')"
                                v-show="formStatusRef === FormStatusEnum.VERIFY">不同意
                      </b-button>
                      <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                                variant="outline-secondary"
                                @click="reviewStart('2')"
                                v-show="formStatusRef === FormStatusEnum.VERIFY">補件
                      </b-button>
                      <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                                variant="outline-secondary"
                                @click="toQueryView">返回
                      </b-button>
                    </b-row>
                  </b-container>

                </div>

              </b-tab>
              <b-tab title="附件" :active="activeTab(1)" @click="changeTabIndex(1)">

                <appendix :vData="appendixData" :fileDataId="fileDataId" :formStatus="formStatusRef">

                </appendix>

              </b-tab>
              <b-tab title="流程圖" :active="activeTab(2)" @click="changeTabIndex(3)">
                <flowChart :filePathName="filePathData">

                </flowChart>
              </b-tab>
            </b-tabs>
          </b-card-body>
      </section>
    </b-container>
  </div>
</template>

<script lang="ts">


import IDualDatePicker from '@/shared/i-date-picker/i-dual-date-picker.vue';
import {onMounted, reactive, ref, Ref, toRef, watch} from '@vue/composition-api';
import {useValidation, validateState} from '@/shared/form';
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';
import {required} from '@/shared/validators';
import IDatePicker from '@/shared/i-date-picker/i-date-picker.vue';
import {useBvModal} from '@/shared/modal';
import {systemToName} from "@/shared/i-system/system-to-name"
import {handleBack, navigateByNameAndParams} from "@/router/router";
import axios from "axios";
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import {useGetters} from "@u3u/vue-hooks";
import {changeDirections} from "@/shared/word/directions";
import {checkboxToMapAndForm} from "@/shared/word/checkboxToMapAndForm";
import {mapToCheckbox} from "@/shared/word/mapToCheckbox";

const appendix = () => import('@/components/appendix.vue');
const flowChart = () => import('@/components/flowChart.vue');
export default {
  name: "l410Edit",
  methods: {
    checkboxToMapAndForm
  },
  props: {
    l410Data: {
      type: Object,
      required: false,
    },
    formStatus: {
      type: String,
      required: false,
    },
    stateStatus: {
      required: false,
      type: Boolean,
      default: true,
    },
  },
  components: {
    'i-form-group-check': IFormGroupCheck,
    'i-dual-date-picker': IDualDatePicker,
    'i-date-picker': IDatePicker,
    appendix,
    flowChart,
  },
  setup(props) {
    let appendixData = reactive({});
    const formStatusRef = toRef(props, 'formStatus');
    const l410DataProp = toRef(props, 'l410Data');
    const stateStatusRef = toRef(props, 'stateStatus');
    const bpmUnitOptions = ref(useGetters(['getBpmUnitOptions']).getBpmUnitOptions).value;
    const $bvModal = useBvModal();
    const notificationService = useNotification();
    let fileDataId = reactive({
      fileId: ''
    });

    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY = '簽核'
    }

    const formDefault = {
      formId: '',//表單編號
      applyDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(),new Date().getHours(),new Date().getMinutes(),new Date().getSeconds(),new Date().getMilliseconds()),//	申請日期
      filEmpid: '',//	填表人員工編號
      filName: '',//	填表人姓名
      filUnit: '',//	填表人單位名稱
      isSubmit: '',//	是否暫存、送出
      appName: '', // 中文姓名
      appEngName: '', // 英文姓名
      appEmpid: '',//	申請人員工編號
      extNum: '',//	分機
      appUnit1: '',//	單位別
      appUnit2: '',//	科別
      position: '',//	職稱
      appReason: '1',//	申請事由 1.新進 2.離職 3.職務異動
      isEnableDate: '',//	生效日期
      enableDate: null,//	生效日期
      isOther: '',//	其他
      otherReason: '',//	其他說明
      applyItem: [],
      webSiteList: [],
      formName: 'L410',
    };
    const form = reactive(Object.assign({}, formDefault));
    const rules = {
      formId: {},
      applyDate: {required},
      filEmpid: {required},
      filName: {required},
      filUnit: {required},
      appName: {required},
      isSubmit: {required},
      appEngName: {required},
      appEmpid: {required},
      extNum: {},
      appUnit1: {required},
      appUnit2: {required},
      position: {required},
      appReason: {required},
      isEnableDate: {},
      enableDate: {required},
      isOther: {},
      otherReason: {},
      applyItem: {},
      webSiteList: {},
    };
    const {$v, checkValidity, reset} = useValidation(rules, form, formDefault);

    const filePathData = reactive({
      filePathName: '',
    });

    const table = reactive({
      fields: [
        {
          key: 'checkbox',
          label: '全選',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'applyItem',
          label: '申請項目',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-left align-middle',
          thStyle: 'width:20%',
        },
        {
          key: 'permissions',
          label: '處理權限',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-left align-middle',
          thStyle: 'width:25%',
        },
        {
          key: 'managementUnit',
          label: '管理單位',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          thStyle: 'width:15%',
        },
        {
          key: 'lastWork',
          label: '處理情形及生效日期',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
        },
        {
          key: 'reviewStaffName',
          label: '處理人員',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
          thStyle: 'width:15%',
        },
      ],
      data: undefined,
      totalItems: 0,
    });

    const options = reactive({
      type: [
        {value: '0', text: '主管'},
        {value: '1', text: '副主管'},
        {value: '2', text: '科長'},
        {value: '3', text: '承辦人'},
        {value: '4', text: '登記桌'},
        {value: '5', text: '總收文'},
        {value: '6', text: '總發文'},
        {value: '7', text: '繕打人員'},
        {value: '8', text: '檔案歸檔'},
      ],
      webSite: [
        {value: '0', text: '全球資訊網 (https://www.pcc.gov.tw)'},
        {value: '1', text: '會內資訊網站請先至home.pcc.gov.tw「帳號登入」點選「註冊」，填寫個人基本資料'},
      ],
    });

    const headers = {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    };

    onMounted(() => {
      handleQuery();
    });

    function handleQuery() {

      table.data = [];

      axios
        .get(`/eip/bpm-l410-apply-manages`)
        .then(({data}) => {

          console.log('data',data)
          console.log('l410DataProp.value',l410DataProp.value)

          data.forEach(i =>{
            mapToCheckbox(i,l410DataProp.value)
          })

          table.data = data

          if (l410DataProp.value.processInstanceId !== null && l410DataProp.value.processInstanceId !== undefined) {
            filePathData.filePathName = 'http://localhost:8081/pic?processId=' + l410DataProp.value.processInstanceId;
          }

          l410DataProp.value.applyDate = l410DataProp.value.applyDate != null ? new Date(l410DataProp.value.applyDate) : null
          l410DataProp.value.enableDate = l410DataProp.value.enableDate != null ? new Date(l410DataProp.value.enableDate) : null

          //
          // //取得現在表單的處理狀況
          // getBpmSignStatus(l414DataProp.value.formId);
          //
          // //用現在表單編號直接給file模組去自動取值
          fileDataId.fileId = l410DataProp.value.formId;
          Object.assign(formDefault, l410DataProp.value);
          Object.assign(form, formDefault)
          reset();


        })
        .catch(notificationErrorHandler(notificationService));
    }

    const submitForm = (isSubmit) => {

      let variables = [];

      checkValidity().then((isValid: boolean) => {
        if (isValid) {
          $bvModal.msgBoxConfirm('是否確認送出修改內容？').then((isOK: boolean) => {
            if (isOK) {

              table.data.forEach(data => {
                checkboxToMapAndForm(data, form, variables)
              })

              let body = {
                bpmIsmsL410DTO: form,
                variables: variables
              }

              const formData = new FormData();

              form.isSubmit = isSubmit;

              formData.append('form', new Blob([JSON.stringify(body)], {type: 'application/json'}));
              if (JSON.stringify(appendixData.value) !== '[]') {
                console.log('安安')
                for (let i in appendixData.value) {
                  formData.append('appendixFiles', appendixData.value[i].file[0]);
                }
                formData.append('fileDto', new Blob([JSON.stringify(appendixData.value)], {type: 'application/json'}));
              }

              axios
                .post(`/process/start/L410`, formData, headers)
                .then(({data}) => {
                  // filePathData.filePathName = 'http://localhost:8081/pic?processId=' + data;
                  console.log('data', data)
                  $bvModal.msgBoxOk('表單新增完畢');
                  reset();
                  navigateByNameAndParams('l410Query', {isReload: false, isNotKeepAlive: true});
                })
                .catch(notificationErrorHandler(notificationService));
            }
          });
        } else {
          $bvModal.msgBoxOk('欄位尚未填寫完畢，請於輸入完畢後再行送出。');
        }
      });
    };

    function reviewStart(item) {
      //FIXME:方法尚未實作
    }

    const tabIndex = ref(0);

    const changeTabIndex = (index: number) => {
      tabIndex.value = index;
    };

    const activeTab = (index: number) => {
      if (tabIndex.value === index) {
        return true;
      } else {
        return false;
      }
    };

    function toQueryView() {
      handleBack({isReload: true, isNotKeepAlive: true});
    }


    return {
      $v,
      form,
      checkValidity,
      validateState,
      options,
      submitForm,
      changeTabIndex,
      activeTab,
      table,
      systemToName,
      filePathData,
      appendixData,
      formStatusRef,
      reset,
      toQueryView,
      bpmUnitOptions,
      fileDataId,
      reviewStart,
      FormStatusEnum,
      stateStatusRef
    }
  }
}
</script>

<style scoped>

.test1 {
  font-family: 'Arial Negreta', 'Arial';
  font-weight: 700;
  font-style: normal;
  font-size: 16px;
  margin-left: 20px;
}

.input-checkbox {
  height: 15px;
  width: 15px;
}

.hr {
  margin-top: 1rem;
  margin-bottom: 1rem;
  border: 0;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
}

#u68 {
  position: absolute;
  left: 10px;
  top: 425px;
  width: 680px;
  height: 10px;
}

.ax {
  font-size: 13px;
  color: #333333;
  text-align: center;
  line-height: normal;
}

</style>


