<template>
  <div>
    <!--    <b-container>-->
    <!--      <section class="container mt-2"></section>-->
    <!--    </b-container>-->


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
                <b-form-input v-model="$v.filEmpid.$model" disabled/>
              </i-form-group-check>

              <i-form-group-check class="col-sm-3" label-cols="5" content-cols="7" :label="`姓名：`"
                                  :item="$v.filName">
                <!--填表人姓名 :　filName-->
                <b-form-input v-model="$v.filName.$model" disabled/>
              </i-form-group-check>

              <i-form-group-check class="col-sm-4" label-cols="5" content-cols="7" :label="`單位：`"
                                  :item="$v.filUnit">
                <!--填表人單位名稱　: filUnit-->
                <b-form-select v-model="$v.filUnit.$model" :options="bpmDeptsOptions" disabled>
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
                <b-form-input v-model="$v.appName.$model"/>
              </i-form-group-check>

              <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`單位別 ：`"
                                  :item="$v.appUnit">
                <!--單位別 : -->
                <b-form-select v-model="$v.appUnit.$model" :options="bpmDeptsOptions">
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
                <b-form-input v-model="$v.appEngName.$model"/>
              </i-form-group-check>

              <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`職稱 ：`"
                                  :item="$v.position">
                <!--職稱 : -->
                <b-form-input v-model="$v.position.$model"/>
              </i-form-group-check>

            </b-form-row>

            <b-form-row>
              <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7"
                                  :label="'員工編號 ：'"
                                  :item="$v.appEmpid">
                <!--員工編號 : -->
                <b-form-input v-model="$v.appEmpid.$model"/>
              </i-form-group-check>

              <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'分機 ：'"
                                  :item="$v.extNum">
                <!--分機 : -->
                <b-form-input v-model="$v.extNum.$model"/>
              </i-form-group-check>

            </b-form-row>

            <b-form-row>
              <p class="test1">二、 <span class="text-danger">*</span>申請事由：</p>
            </b-form-row>

            <b-form-row>
              <!--申請事由-->
              <b-form-radio-group class="offset-1" v-model="$v.appReason.$model">
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
                <b-form-checkbox class="col-2" v-model="$v.isEnableDate.$model" value="1" unchecked-value="0">
                  生效日期 :
                </b-form-checkbox>
                <!--生效日期 : enableDate-->
                <i-date-picker
                  class="col-2"
                  placeholder="yyy/MM/dd"
                  v-model="$v.enableDate.$model"
                  lazy
                  trim
                  :disabled="$v.isEnableDate.$model !== '1'"
                ></i-date-picker>
                <div class="text-danger text1 mx-1"
                     v-if="$v.enableDate.$model === null || $v.isEnableDate.$model === '0'">請輸入值
                </div>
                <!--其他 checkbox : isOther-->
                <b-form-checkbox class="col-2 offset-1" v-model="$v.isOther.$model" value="1" unchecked-value="0">
                  其他 :
                </b-form-checkbox>
                <!--其他說明 : otherReason-->
                <b-form-input class="col-3" v-model="$v.otherReason.$model"/>
                <div class="text-danger text1 mx-1" v-if="$v.otherReason.$model === '' && $v.isOther.$model === '1'">
                  請輸入值
                </div>
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
                  <b-form-checkbox :value="'1'" :unchecked-value="'0'" v-model="row.item.checkbox"
                                   @change="resetCheckboxValue(row.item)"/>
                </template>

                <!--申請項目-->
                <template #cell(applyItem)="row">

                  <div v-if="row.item.applyVersion == '0'">
                    <div>系統名稱 :</div>
                    <b-form-input v-model="row.item.otherSys" maxlength="200"></b-form-input>
                    <div>帳號 :</div>
                    <b-form-input v-model="row.item.otherSysAccount" maxlength="200"></b-form-input>
                  </div>

                  <div v-else-if="row.item.applyVersion == '1'">
                    {{ row.item.systemApplyName }}
                  </div>

                  <div v-else-if="row.item.applyVersion == '2'">
                    {{ row.item.systemApplyName }}
                    <span v-if=" row.item.isColon === '1'"> : </span>
                    <b-form-input
                      maxlength="200" v-model="row.item.systemApplyInput"
                      :disabled="row.item.systemApply === '4'">
                    </b-form-input>
                    <span v-if="row.item.systemApply === '4'">@mail.pcc.gov.tw</span>
                  </div>

                  <div v-else-if="row.item.applyVersion == '3'">
                    {{ row.item.systemApplyName }}
                    <span v-if=" row.item.isColon === '1'"> : </span>
                    <b-form-checkbox-group v-model="$v.applyItem.$model" :options="options.type"/>
                  </div>

                  <div v-else-if="row.item.applyVersion == '4'">
                    <b-form-checkbox-group v-model="$v.webSiteList.$model" :options="options.webSite"/>
                  </div>

                </template>

                <!--處理權限-->
                <template #cell(permissions)="row">

                  <div v-if="row.item.permissionsVersion == '0'">
                    <b-form-radio-group v-model="row.item.sys" @change="resetValue(row.item)">
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
                                        :disabled="row.item.sys !== '4'"/>
                        </b-input-group>
                      </b-form-radio>
                    </b-form-radio-group>
                  </div>

                  <div v-if="row.item.permissionsVersion == '1'">
                    <b-form-radio-group v-model="row.item.sys" @change="resetValue(row.item)">
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
                                        :disabled="row.item.sys !== '3'"/>
                        </b-input-group>
                      </b-form-radio>
                    </b-form-radio-group>
                  </div>

                  <div v-if="row.item.permissionsVersion == '2'">
                    <b-form-radio-group v-model="row.item.sys" @change="resetValue(row.item)">
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
                                        :disabled="row.item.sys !== '5'"/>
                        </b-input-group>
                      </b-form-radio>
                    </b-form-radio-group>
                  </div>

                  <div v-if="row.item.permissionsVersion == '3'">
                    <b-form-radio-group v-model="row.item.sys" @change="resetValue(row.item)">
                      <b-form-radio class="col-12" value="1">
                        <div>新增</div>
                        <b-input-group>
                          <div>1.　</div>
                          <b-form-input maxlength="200" v-model="row.item.emailApply1"
                                        :disabled="row.item.sys !== '1'"/>
                        </b-input-group>
                        <b-input-group>
                          <div>2.　</div>
                          <b-form-input maxlength="200" v-model="row.item.emailApply2"
                                        :disabled="row.item.sys !== '1'"/>
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
                                        :disabled="row.item.sys !== '3'"/>
                        </b-input-group>
                      </b-form-radio>
                    </b-form-radio-group>
                  </div>

                  <div v-if="row.item.permissionsVersion == '4'">
                    <b-form-radio-group v-model="row.item.sys" @change="resetValue(row.item)">
                      <b-form-radio class="col-12" value="1">
                        <div>新增</div>
                      </b-form-radio>

                      <b-form-radio class="col-12" value="2">
                        <div>異動 :　</div>
                      </b-form-radio>

                      <div class="ml-1">
                        <b-form-checkbox value="1" unchecked-value="0" v-model="row.item.isUnitAdm"
                                         :disabled="row.item.sys !== '2'">
                          部門管理者
                        </b-form-checkbox>

                        <b-form-checkbox value="1" unchecked-value="0" v-model="row.item.isUnitDataMgr"
                                         :disabled="row.item.sys !== '2'">
                          部門資料維護者
                        </b-form-checkbox>

                        <b-form-checkbox value="1" unchecked-value="0" v-model="row.item.isWebSiteOther"
                                         :disabled="row.item.sys !== '2'">
                          其他 :　
                        </b-form-checkbox>
                        <b-form-input maxlength="200" v-model="row.item.otherRemark"
                                      :disabled="row.item.isWebSiteOther !== '1'"/>
                      </div>

                      <b-form-radio class="col-12" value="3">
                        <div>帳號停用</div>
                      </b-form-radio>
                    </b-form-radio-group>
                  </div>
                </template>

                <!--管理單位-->
                <template #cell(managementUnit)="row">
                  <b-form-select :options="bpmDeptsOptions" v-model="row.item.admUnit" disabled>
                    <template #first>
                      <b-form-select-option value="null" disabled>請選擇</b-form-select-option>
                    </template>
                  </b-form-select>
                </template>

                <!--處理情形及生效日期-->
                <template #cell(lastWork)="row">
                  <b-form-input maxlength="200" v-model="row.item.admStatus" disabled/>
                  <i-date-picker
                    placeholder="yyy/MM/dd"
                    lazy
                    trim
                    v-model="row.item.admEnableDate"
                    disabled
                  ></i-date-picker>
                </template>

                <!--處理人員-->
                <template #cell(reviewStaffName)="row">
                  <b-form-input maxlength="200" v-model="row.item.admName" disabled/>
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
                          @click="submitForm('0')">暫存
                </b-button>
                <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                          @click="submitForm('1')">申請
                </b-button>
                <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                          @click="resetAll()">清除
                </b-button>
                <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                          @click="toQueryView">返回
                </b-button>
              </b-row>
            </b-container>

          </div>

        </b-tab>
        <b-tab title="附件" :active="activeTab(1)" @click="changeTabIndex(1)">

          <appendix :vData="appendixData" :formStatus="formStatusRef">

          </appendix>

        </b-tab>
        <b-tab title="流程圖" :active="activeTab(2)" @click="changeTabIndex(3)">
          <flowChart :filePathName="filePathData">

          </flowChart>
        </b-tab>
      </b-tabs>
    </b-card-body>

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

const appendix = () => import('@/components/appendix.vue');
const flowChart = () => import('@/components/flowChart.vue');
export default {
  name: "l410Apply",
  methods: {
    checkboxToMapAndForm
  },
  props: {
    formStatus: {
      type: String,
      required: false,
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
    const userData = ref(useGetters(['getUserData']).getUserData).value
    const formStatusRef = toRef(props, 'formStatus');
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;
    const $bvModal = useBvModal();
    const notificationService = useNotification();
    const formDefault = {
      formId: '',//表單編號
      applyDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), new Date().getHours(), new Date().getMinutes(), new Date().getSeconds(), new Date().getMilliseconds()),//	申請日期
      filEmpid: userData.empId != null ? userData.empId : '',//	填表人員工編號
      filName: userData.userName != null ? userData.userName : '',//	填表人姓名
      filUnit: userData.deptId != null ? userData.deptId : '',//	填表人單位名稱
      isSubmit: '',//	是否暫存、送出
      appName: userData.userName != null ? userData.userName : '', // 中文姓名
      appEngName: userData.userEName != '' ? userData.userEName : '', // 英文姓名
      appEmpid: userData.empId != null ? userData.empId : '',//	申請人員工編號
      extNum: userData.tel2 != null ? userData.tel2 : '',//	分機
      appUnit: userData.deptId != null ? userData.deptId : '',//	單位別
      position: userData.cpape05m.title != null ? userData.cpape05m.title : '',//	職稱
      appReason: '1',//	申請事由 1.新進 2.離職 3.職務異動
      isEnableDate: '0',//	是否有生效日期
      enableDate: null,//	生效日期
      isOther: '0',//	其他
      otherReason: '',//	其他說明
      isHrSys: '0',
      hrSys: '',
      hrSysChange: '',
      hrSysAdmUnit: '',
      hrSysStatus: '',
      hrSysEnableDate: null,
      hrSysAdmName: '',
      isAdSys: '0',
      adAccount: '',
      adSys: '',
      adSysChange: '',
      adSysAdmUnit: '',
      adSysStatus: '',
      adSysEnableDate: null,
      adSysAdmName: '',
      isMeetingRoom: '0',
      meetingRoom: '',
      meetingRoomChange: '',
      meetingRoomAdmUnit: '',
      meetingRoomStatus: '',
      meetingRoomEnableDate: null,
      meetingRoomAdmName: '',
      isOdSys: '0',
      odSysRole: '',
      odSys: '',
      odSysOther: '',
      odSysAdmUnit: '',
      odSysStatus: '',
      odSysEnableDate: null,
      odSysAdmName: '',
      isEmailSys: '0',
      emailSysAccount: '',
      emailSys: '',
      emailApply1: '',
      emailApply2: '',
      emailSysChange: '',
      emailSysAdmUnit: '',
      emailSysStatus: '',
      emailSysEnableDate: null,
      emailSysAdmName: '',
      isWebSite: '0',
      isPccWww: '0', //全球資訊網
      isPccHome: '0',//會內資訊網站
      webSite: '',
      isUnitAdm: '0',
      isUnitDataMgr: '0',
      isWebSiteOther: '0',
      webSiteOther: '',
      webSiteAdmUnit: '',
      webSiteStatus: '',
      webSiteEnableDate: null,
      webSiteAdmName: '',
      isPccPis: '0',
      pccPisAccount: '',
      pccPis: '',
      pccPisChange: '',
      pccPisAdmUnit: '',
      pccPisStatus: '',
      pccPisEnableDate: null,
      pccPisAdmName: '',
      isEngAndPrjInfoSys: '0',
      engAndPrjInfoSysAccount: '',
      engAndPrjInfoSys: '',
      engAndPrjInfoSysChange: '',
      engAndPrjInfoSysAdmUnit: '',
      engAndPrjInfoSysStatus: '',
      engAndPrjInfoSysEnableDate: null,
      engAndPrjInfoSysAdmName: '',
      isRevSys: '0',
      revSysAccount: '',
      revSys: '',
      revSysChange: '',
      revSysAdmUnit: '',
      revSysStatus: '',
      revSysEnableDate: null,
      revSysAdmName: '',
      isRecSys: '0',
      recSysAccount: '',
      recSys: '',
      recSysChange: '',
      recSysAdmUnit: '',
      recSysStatus: '',
      recSysEnableDate: null,
      recSysAdmName: '',
      isBidSys: '0',
      bidSysAccount: '',
      bidSys: '',
      bidSysChange: '',
      bidSysAdmUnit: '',
      bidSysStatus: '',
      bidSysEnableDate: null,
      bidSysAdmName: '',
      isOtherSys1: '0',
      otherSys1ServerName: '',
      otherSys1Account: '',
      otherSys1: '',
      otherSys1Change: '',
      otherSys1AdmUnit: '',
      otherSys1Status: '',
      otherSys1EnableDate: null,
      otherSys1AdmName: '',


      isOtherSys2: '0',
      otherSys2ServerName: '',
      otherSys2Account: '',
      otherSys2: '',
      otherSys2Change: '',
      otherSys2AdmUnit: '',
      otherSys2Status: '',
      otherSys2EnableDate: null,
      otherSys2AdmName: '',
      isOtherSys3: '0',
      otherSys3ServerName: '',
      otherSys3Account: '',
      otherSys3: '',
      otherSys3Change: '',
      otherSys3AdmUnit: '',
      otherSys3Status: '',
      otherSys3EnableDate: null,
      otherSys3AdmName: '',
      applyItem: [],
      webSiteList: [],
      l410Variables: {},
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
      appUnit: {required},
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

    onMounted(() => {
      handleQuery();
    });

    function handleQuery() {
      table.data = [];
      axios
        .get(`/eip/bpm-l410-apply-manages`)
        .then(({data}) => {
          table.data = data
        })
        .catch(notificationErrorHandler(notificationService));
    }

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


    async function submitForm(isSubmit) {
      let l410Variables = [];
      form.isSubmit = isSubmit;
      const isValid = await checkValidity();

      if (isValid) {
        const isOK = await $bvModal.msgBoxConfirm('是否確認送出修改內容？');

        if (isOK) {
          await Promise.all(table.data.map(data => checkboxToMapAndForm(data, form, l410Variables)));

          console.log(' l410Apply.vue - submitForm - 800: ', JSON.parse(JSON.stringify(table.data)))

          form.l410Variables = l410Variables;

          console.log('form.l410Variables',form.l410Variables)
          console.log(' l410Apply.vue - submitForm - 802: ', JSON.parse(JSON.stringify(form)))

          let body = {
            "L410": JSON.stringify(form)
          }

          const formData = new FormData();

          formData.append('form', new Blob([JSON.stringify(body)], {type: 'application/json'}));
          if (JSON.stringify(appendixData.value) !== '[]') {
            for (let i in appendixData.value) {
              formData.append('appendixFiles', appendixData.value[i].file[0]);
            }
            formData.append('fileDto', new Blob([JSON.stringify(appendixData.value)], {type: 'application/json'}));
          }

          axios
            .post(`/process/start/L410`, formData, headers)
            .then(({data}) => {
              $bvModal.msgBoxOk('表單新增完畢');
              reset();
              navigateByNameAndParams('l410Query', {isReload: false, isNotKeepAlive: true});
            })
            .catch(notificationErrorHandler(notificationService));

        }
      } else {
        await $bvModal.msgBoxOk('欄位尚未填寫完畢，請於輸入完畢後再行送出。');
      }
    }

    const tabIndex = ref(0);

    const changeTabIndex = (index: number) => {
      tabIndex.value = index;
    };

    const activeTab = (index: number) => {
      return tabIndex.value === index;
    };

    function toQueryView() {
      handleBack({isReload: false, isNotKeepAlive: true});
    }

    function resetValue(data) {
      data.emailApply1 = null;
      data.emailApply2 = null;
      data.sysChange = null;
      data.isUnitAdm = null;
      data.isUnitDataMgr = null;
      data.isWebSiteOther = null;
      data.otherRemark = null;
    }

    function resetCheckboxValue(data) {
      if (data.checkbox === '0') {
        data.otherSys = null;
        data.otherSysAccount = null;
        data.systemApplyInput = null;
        data.sys = null;
        data.sysChange = null;
        data.emailApply1 = null;
        data.emailApply2 = null;
        data.isUnitAdm = null;
        data.isUnitDataMgr = null;
        data.isWebSiteOther = null;
        data.otherRemark = null;
        if (data.systemApply === '2') {
          form.applyItem = [];
        }
        if (data.systemApply === '5') {
          form.webSiteList = [];
        }
      }
    }

    function resetAll() {
      reset();
      table.data.forEach(data=>{
        data.checkbox = '0';
        data.otherSys = null;
        data.otherSysAccount = null;
        data.systemApplyInput = null;
        data.sys = null;
        data.sysChange = null;
        data.emailApply1 = null;
        data.emailApply2 = null;
        data.isUnitAdm = null;
        data.isUnitDataMgr = null;
        data.isWebSiteOther = null;
        data.otherRemark = null;
      })
      form.applyItem = [];
      form.webSiteList = [];
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
      bpmDeptsOptions,
      resetValue,
      resetCheckboxValue,
      resetAll
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

.text1 {
  margin-top: 0.25rem;
  font-size: 80%;
}

</style>


