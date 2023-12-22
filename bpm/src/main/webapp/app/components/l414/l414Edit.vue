<template>
  <div>

    <div>
      <b-card-body>
        <b-tabs v-model="tabIndex">
          <b-tab title="表單" :active="activeTab(0)" @click="changeTabIndex(0)">
            <div class="bpm_form_header">
              <b-row class="d-flex">
                <p class="ml-4" style="color: white">L414-網路服務連結申請單</p>

                <P class="ml-3">機密等級： 敏感</P>
              </b-row>
            </div>

            <div class="card-body bpm_background">

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

                <i-form-group-check class="col-sm-4" label-cols="4" content-cols="8" :label="`表單編號：`"
                                    :item="$v.formId">
                  <!--表單編號 : formId -->
                  <b-form-input v-model="$v.formId.$model" disabled/>
                </i-form-group-check>

                <i-form-group-check class="col-sm-3" label-cols="3" content-cols="7" :label="`註：`">
                  <span class="text-danger">*</span>
                  <p style="margin-top: 10px">為申請必填欄位</p>
                </i-form-group-check>
              </b-form-row>

              <b-form-row>
                <i-form-group-check
                  class="col-sm-5"
                  label-cols="5"
                  content-cols="7"
                  :label="'填表人：員工編號：'"
                  :item="$v.filEmpid"
                >
                  <!--填表人員工編號 : filEmpid-->
                  <b-form-input v-model="$v.filEmpid.$model"
                                :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit"/>
                </i-form-group-check>

                <i-form-group-check class="col-sm-4" label-cols="4" content-cols="8" :label="`姓名：`"
                                    :item="$v.filName">
                  <!--填表人姓名 :　filName-->
                  <b-form-input v-model="$v.filName.$model"
                                :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit "/>
                </i-form-group-check>

                <i-form-group-check class="col-sm-3" label-cols="3" content-cols="7" :label="`單位：`"
                                    :item="$v.filUnit">
                  <!--填表人單位名稱　: filUnit-->
                  <b-form-select v-model="$v.filUnit.$model" :options="bpmDeptsOptions"
                                 :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit">
                    <template #first>
                      <b-form-select-option value="" disabled>請選擇</b-form-select-option>
                    </template>
                  </b-form-select>
                </i-form-group-check>
              </b-form-row>

              <b-form-row>
                <i-form-group-check
                  class="col-sm-5"
                  label-cols="5"
                  content-cols="7"
                  :label="'申請人：員工編號：'"
                  :item="$v.appEmpid"
                >
                  <b-input-group>
                    <!--申請人員工編號 : appEmpid-->
                    <b-form-input v-model="$v.appEmpid.$model"
                                  :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit"/>
                    <b-button class="ml-2" v-show="formStatusRef === FormStatusEnum.MODIFY  && isEdit"
                              variant="outline-dark" @click="showErrandModel()">選擇人員
                    </b-button>
                  </b-input-group>

                </i-form-group-check>

                <i-form-group-check class="col-sm-4" label-cols="4" content-cols="8" :label="`姓名：`"
                                    :item="$v.appName">
                  <!--申請人姓名 :　appName-->
                  <b-form-input v-model="$v.appName.$model"
                                :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit"/>
                </i-form-group-check>

                <i-form-group-check class="col-sm-3" label-cols="3" content-cols="7" :label="`單位：`"
                                    :item="$v.appUnit">
                  <!--申請人單位名稱 : appUnit-->
                  <b-form-select v-model="$v.appUnit.$model" :options="bpmDeptsOptions"
                                 :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit">
                    <template #first>
                      <b-form-select-option value="" disabled>請選擇</b-form-select-option>
                    </template>
                  </b-form-select>
                </i-form-group-check>
              </b-form-row>

              <div class="card m-3" style="background-color: white">
                <b-row>
                  <b-col class="col-sm-5">
                    <i-form-group-check class="col-12" label-cols="3" content-cols="7" :label="`規則：`"
                                        :item="$v.isEnable">
                      <!--規則 : isEnable-->
                      <b-form-radio-group
                        v-model="$v.isEnable.$model"
                        :options="[
                              { value: '1', text: '啟用' },
                              { value: '0', text: '停用' },
                            ]"
                        :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit"
                      />
                    </i-form-group-check>

                    <i-form-group-check class="col-12" label-cols="3" content-cols="7" :label="`使用時段：`"
                                        :item="$v.enableTime">
                      <!--使用時段 : enableTime-->
                      <b-form-radio-group v-model="$v.enableTime.$model"
                                          @change="changeEnableTime"
                                          :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit">
                        <b-form-radio value="1">
                          <div style="height: 34px">每日24小時</div>
                        </b-form-radio>

                        <b-form-radio value="2">
                          <div style="height: 34px">每周一至周五 :</div>
                          <!--每周一至周五使用時段內容 : workingTime-->
                          <b-form-input
                            :disabled="$v.enableTime.$model !== '2' && formStatusRef === FormStatusEnum.READONLY  || isEdit"
                            v-model="$v.workingTime.$model"/>
                        </b-form-radio>

                        <b-form-radio value="3">
                          <div style="height: 34px">特殊時段 :</div>
                          <!--使用特殊時段內容 : otherEnableTime-->
                          <b-form-input
                            :disabled="$v.enableTime.$model !== '3' && formStatusRef === FormStatusEnum.READONLY  || isEdit"
                            v-model="$v.otherEnableTime.$model"/>
                        </b-form-radio>
                      </b-form-radio-group>
                    </i-form-group-check>
                  </b-col>
                  <b-col>
                    <i-form-group-check
                      class="col-sm-12"
                      label-cols="3"
                      content-cols="7"
                      :label="`啟用期間：`"
                      :item="$v.selecteEdateType"
                    >
                      <!--啟用期間類別 : selecteEdateType-->
                      <b-form-radio-group v-model="$v.selecteEdateType.$model"
                                          @change="changeSelecteEdateType"
                                          :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit">
                        <b-form-radio value="1">
                          <!--啟用期間開始時間 : sdate 、啟用期間結束時間 : edate-->
                          <i-dual-date-picker
                            :disabled="$v.selecteEdateType.$model !== '1'"
                            :dual1.sync="$v.sdate.$model"
                            :dual2.sync="$v.edate.$model"
                          />
                        </b-form-radio>
                        <b-form-radio value="2">
                          <!--職務異動止說明 : othereEdate-->
                          <b-form-input class="d-inline col-5" v-model="$v.othereEdate.$model"
                                        :disabled="$v.selecteEdateType.$model !== '2' && formStatusRef === FormStatusEnum.READONLY  || isEdit"/>
                          <span class="d-inline col-7">職務異動止</span>
                        </b-form-radio>
                        <b-form-radio value="3">
                          <div class="m-1 col-12" style="height: 34px">永久使用(僅電腦機房可勾選)</div>
                        </b-form-radio>
                      </b-form-radio-group>
                    </i-form-group-check>

                    <i-form-group-check
                      class="col-sm-12"
                      label-cols="3"
                      content-cols="7"
                      :label="`停用期間：`"
                      :item="$v.selecteEdateType"
                    >
                      <!--啟用期間類別 : selecteEdateType-->
                      <b-form-radio-group v-model="$v.selecteEdateType.$model"
                                          @change="changeSelecteEdateType"
                                          :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit">
                        <b-form-radio value="4">
                          <!--刪除規則時間 : delEnableDate-->
                          <b-input-group>
                            <i-date-picker
                              :disabled="$v.selecteEdateType.$model !== '4' && formStatusRef === FormStatusEnum.READONLY  || isEdit"
                              placeholder="yyy/MM/dd"
                              v-model="$v.delEnableDate.$model"
                              :state="validateState($v.delEnableDate)"
                              lazy
                              trim
                            ></i-date-picker>
                            <span class="m-2">刪除規則</span>
                          </b-input-group>
                        </b-form-radio>
                        <div><span class="text-danger">*</span>註：申請使用期限最長一年</div>
                      </b-form-radio-group>
                    </i-form-group-check>
                  </b-col>
                </b-row>

                <b-form-row>
                  <i-form-group-check class="col-sm-5" label-cols="3" content-cols="7" :label="'來源IP：'"
                                      :item="$v.sourceIp">
                    <!--來源IP : sourceIp-->
                    <b-form-textarea v-model="$v.sourceIp.$model" rows="3" maxlength="2000" lazy
                                     :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit"/>
                  </i-form-group-check>

                  <i-form-group-check class="col-sm-7" label-cols="3" content-cols="7" :label="`目的IP：`"
                                      :item="$v.targetIp">
                    <!--目的IP : targetIp-->
                    <b-form-textarea v-model="$v.targetIp.$model" rows="3" maxlength="2000" lazy
                                     :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit"/>

                  </i-form-group-check>
                </b-form-row>

                <b-form-row>
                  <i-form-group-check class="col-sm-5" label-cols="3" content-cols="7" :label="'使用協定(port)：'"
                                      :item="$v.port">
                    <!--使用協定(port) : port-->
                    <b-form-input v-model="$v.port.$model"
                                  :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit"/>
                  </i-form-group-check>

                  <i-form-group-check class="col-sm-7" label-cols="3" content-cols="7" :label="`傳輸模式 ：`">
                    <b-input-group>
                      <!--傳輸模式是否為tcp: isTcp-->
                      <b-form-checkbox class="col-6" v-model="$v.isTcp.$model" value="Y" unchecked-value="N"
                                       :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit">
                        TCP
                      </b-form-checkbox>
                      <!--傳輸模式是否為udp: isUdp-->
                      <b-form-checkbox class="col-6" v-model="$v.isUdp.$model" value="Y" unchecked-value="N"
                                       :disabled="formStatusRef === FormStatusEnum.READONLY  || isEdit">
                        UDP
                      </b-form-checkbox>
                    </b-input-group>
                  </i-form-group-check>
                </b-form-row>

                <b-form-row>
                  <i-form-group-check
                    class="col-sm-5"
                    label-cols="3"
                    content-cols="8"
                    :label="'用途說明 ：'"
                    :item="$v.instructions"
                  >
                    <!--用途說明 : instructions-->
                    <b-form-textarea v-model="$v.instructions.$model" rows="3" maxlength="2000" trim lazy
                                     :disabled="formStatusRef === FormStatusEnum.READONLY || isEdit"/>
                  </i-form-group-check>
                </b-form-row>
              </div>

              <b-form-row>
                <i-form-group-check class="col-sm-3" label-cols="12" content-cols="0"
                                    :label="'以下由資訊推動小組填寫'">
                </i-form-group-check>
              </b-form-row>

              <div class="card m-3" style="background-color: white">
                <b-form-row>
                  <!--處理意見 : agreeType-->
                  <i-form-group-check class="col-sm-12" label-cols="1" content-cols="11" :label="'處理意見：'">
                    <b-form-radio-group v-model="$v.agreeType.$model"
                                        @change="changeAgreeType"
                                        :disabled="!configRoleToBpmIpt(userData.userRole) || form.isSubmit !== '1' || formStatusRef === FormStatusEnum.READONLY">
                      <!--預定完成日期 : scheduleDate-->
                      <b-form-radio class="col-12" value="1">
                        <b-input-group>
                          <div>同意設定 : 預定完成日期 : 　</div>
                          <i-date-picker
                            :disabled="$v.agreeType.$model !== '1' || !configRoleToBpmIpt(userData.userRole) || form.isSubmit !== '1' || formStatusRef === FormStatusEnum.READONLY"
                            placeholder="yyy/MM/dd"
                            v-model="$v.scheduleDate.$model"
                            :state="validateState($v.scheduleDate)"
                            lazy
                            trim
                          ></i-date-picker>
                        </b-input-group>
                      </b-form-radio>

                      <!--部分同意設定原因 : partialAgreeReason-->
                      <b-form-radio class="col-12 fixedWidth" value="2">
                        <b-input-group>
                          <div>部分同意設定 : 原因 :　　 　</div>
                          <b-form-textarea
                            :disabled="$v.agreeType.$model !== '2' || !configRoleToBpmIpt(userData.userRole) || form.isSubmit !== '1' || formStatusRef === FormStatusEnum.READONLY "
                            v-model="$v.partialAgreeReason.$model"
                            rows="1"
                            maxlength="2000"
                            trim
                            lazy
                          />
                        </b-input-group>
                      </b-form-radio>

                      <!--不同意設定原因 : notAgreeReason-->
                      <b-form-radio class="col-12 fixedWidth" value="3">
                        <b-input-group>
                          <div>不同意設定 : 原因 :　　　 　</div>
                          <b-form-textarea
                            :disabled="$v.agreeType.$model !== '3' || !configRoleToBpmIpt(userData.userRole) || form.isSubmit !== '1' || formStatusRef === FormStatusEnum.READONLY"
                            v-model="$v.notAgreeReason.$model"
                            rows="1"
                            maxlength="2000"
                            trim
                            lazy
                          />
                        </b-input-group>
                      </b-form-radio>
                    </b-form-radio-group>
                  </i-form-group-check>
                </b-form-row>
              </div>

              <div class="card m-3" style="background-color: white">
                <b-form-row>
                  <i-form-group-check class="col-sm-12" label-cols="1" content-cols="11" :label="`變更設備 ：`">
                    <b-input-group>
                      <!--是否為外部防火牆 : isExternalFirewall-->
                      <b-form-checkbox v-model="$v.isExternalFirewall.$model" value="Y" unchecked-value=""
                                       :disabled="!configRoleToBpmCrOperator(userData.userRole) || form.isSubmit !== '1' || formStatusRef === FormStatusEnum.READONLY">
                        外部防火牆
                      </b-form-checkbox>
                      <!--變更設備：是否為內部防火牆 : isInternalFirewall-->
                      <b-form-checkbox class="mx-3" v-model="$v.isInternalFirewall.$model" value="Y" unchecked-value=""
                                       :disabled="!configRoleToBpmCrOperator(userData.userRole) || form.isSubmit !== '1' || formStatusRef === FormStatusEnum.READONLY">
                        外部防火牆
                      </b-form-checkbox>
                    </b-input-group>
                  </i-form-group-check>
                </b-form-row>

                <b-form-row>
                  <i-form-group-check
                    class="col-sm-12"
                    label-cols="1"
                    content-cols="9"
                    :label="'設定內容 ：'"
                    :item="$v.firewallContent"
                  >
                    <!--設定內容 : firewallContent-->
                    <b-form-textarea v-model="$v.firewallContent.$model" rows="1" maxlength="2000" trim lazy
                                     :disabled="!configRoleToBpmCrOperator(userData.userRole) || form.isSubmit !== '1' || formStatusRef === FormStatusEnum.READONLY"/>
                  </i-form-group-check>
                </b-form-row>

                <b-form-row>
                  <i-form-group-check
                    class="col-sm-12"
                    label-cols="1"
                    content-cols="11"
                    :label="'實際完成日期 : '"
                    :item="$v.finishDatetime"
                  >
                    <!--實際完成日期 : finishDatetime-->
                    <b-input-group>
                      <i-date-picker
                        class="col-3"
                        placeholder="yyy/MM/dd"
                        v-model="$v.finishDatetime.$model"
                        :state="validateState($v.finishDatetime)"
                        lazy
                        trim
                        :disabled="!configRoleToBpmCrOperator(userData.userRole) || form.isSubmit !== '1' || formStatusRef === FormStatusEnum.READONLY"
                      ></i-date-picker>
                      <span class="m-1">，並以電話通知申請單位。</span>
                    </b-input-group>
                  </i-form-group-check>
                </b-form-row>
              </div>

              <div class="m-5">
                <P> 備註： </P>
                <P>
                  網路服務連結申請經審核通過後，申請人應隨時注意使用期間(時段)之必要性，遇有系統續用、調整、停用或使用屆期時，應主動申請網路服務續用、調整或撤銷。
                </P>
              </div>


              <!--簽核流程資訊模組-->
              <signerList :formId="formIdProp" :formStatus="formStatusRef" :opinion="opinion"
                          :processInstanceStatus="processInstanceStatusRef"></signerList>

              <b-container class="mt-3">
                <b-row class="justify-content-center">

                  <i-button class="ml-2" type="tag" @click="submitForm('0')"
                            v-show="formStatusRef === FormStatusEnum.MODIFY"/>
                  <i-button class="ml-2" type="node-plus" @click="submitForm('1')"
                            v-show="formStatusRef === FormStatusEnum.MODIFY"/>

                  <div v-if="userData.isSupervisor && formStatusRef === FormStatusEnum.VERIFY">
                    <i-button class="ml-2" type="check" @click="reviewStart('同意',true)"/>
                    <i-button class="ml-2" type="x" @click="reviewStart('不同意',true)"/>
                  </div>
                  <div v-else-if="formStatusRef === FormStatusEnum.VERIFY">
                    <i-button class="ml-2" type="send-check" @click="reviewStart('審核',true)"/>
                  </div>

                  <i-button class="ml-2" type="check2-circle" @click="showModel()"
                            v-show="formStatusRef === FormStatusEnum.VERIFY && isSignatureRef"/>
                  <i-button class="ml-2" type="upload" @click="reviewStart('補件',true)"
                            v-show="configTitleName(userData.titleName) && formStatusRef === FormStatusEnum.VERIFY"/>
                  <i-button class="ml-2" type="reply" @click="toCancel"
                            v-show="isCancelRef && userData.userId === form.appEmpid && (form.processInstanceStatus === '0' || form.processInstanceStatus === '2')"/>
                  <i-button class="ml-2" type="arrow-left" @click="toQueryView()"/>

                </b-row>
              </b-container>

            </div>
          </b-tab>
          <b-tab title="附件" :active="activeTab(1)" @click="changeTabIndex(1)">

            <appendix :vData="appendixData" :fileDataId="fileDataId" :formStatus="formStatusRef">

            </appendix>

          </b-tab>
          <b-tab title="流程圖" :active="activeTab(2)" @click="changeTabIndex(2)">
            <flowChart :filePathName="filePathData"></flowChart>
          </b-tab>
        </b-tabs>
      </b-card-body>
    </div>
    <!--加簽的彈出視窗-->
    <signatureBmodel ref="signatureBmodel" :formData="form" :taskData="taskDataRef"></signatureBmodel>
    <!--申請人選擇器的彈出視窗-->
    <errandBmodel ref="errandBmodel" :formData="form"></errandBmodel>
  </div>
</template>

<script lang="ts">


import IDualDatePicker from '@/shared/i-date-picker/i-dual-date-picker.vue';
import {reactive, ref, toRef, watch} from '@vue/composition-api';
import {useValidation, validateState} from '@/shared/form';
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';
import IDatePicker from '@/shared/i-date-picker/i-date-picker.vue';
import {useBvModal} from '@/shared/modal';
import {useNotification} from '@/shared/notification';
import {useGetters} from '@u3u/vue-hooks';
import {handleBack, navigateByNameAndParams} from '@/router/router';
import axios from "axios";
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {changeDirections} from "@/shared/word/directions";
import signatureBmodel from "@/components/signatureBmodel.vue";
import {configRoleToBpmIpt, configRoleToBpmCrOperator, configTitleName} from '@/shared/word/configRole';
import errandBmodel from "@/components/errandBmodel.vue";
import IButton from '@/shared/buttons/i-button.vue';
import {required,cofTransmission,confPort,confIp} from '@/shared/validators';

const appendix = () => import('@/components/appendix.vue');
const flowChart = () => import('@/components/flowChart.vue');
const signerList = () => import('@/components/signerList.vue');

export default {
  name: 'l414Edit',
  props: {
    formId: {
      type: String,
      required: false,
    },
    formStatus: {
      required: false,
      type: String,
    },
    taskData: {
      required: false,
      type: Object,
    },
    isSignature: {
      required: false,
      type: Boolean,
      default: true,
    },
    processInstanceStatus: {
      required: false,
      type: String,
    },
    isCancel: {
      required: false,
      type: Boolean,
      default: false,
    },
    query: {
      type: String,
      required: false,
      default: '1',
    },
  },
  components: {
    'i-form-group-check': IFormGroupCheck,
    'i-dual-date-picker': IDualDatePicker,
    'i-date-picker': IDatePicker,
    appendix,
    flowChart,
    signatureBmodel,
    signerList,
    errandBmodel,
    'i-button': IButton,
  },
  setup(props) {
    //登入者資訊
    const userData = ref(useGetters(['getUserData']).getUserData).value;

    //單位下拉選單資訊
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;

    //接收父層傳過來的formId
    const formIdProp = toRef(props, 'formId');

    //進入表單的模式
    const formStatusRef = toRef(props, 'formStatus');

    //傳進來的taskData{processInstanceId、taskId、taskName、decisionRole、additional}
    const taskDataRef = toRef(props, 'taskData');

    //顯示加簽按鈕判斷
    const isSignatureRef = toRef(props, 'isSignature');

    //傳進來的表單處理狀態 0處理中 1已完成 2補件 3撤銷
    const processInstanceStatusRef = toRef(props, 'processInstanceStatus');

    //顯示撤銷按鈕判斷
    const isCancelRef = toRef(props, 'isCancel');

    //接待處理來的狀態，一般查詢:1 下屬表單查詢:2 ，返回待處理時在帶這個參數過去，讓待處理的onActivated()知道要查哪個function
    const queryRef = toRef(props, 'query');

    //分頁預設值
    const tabIndex = ref(0);

    //判斷暫存還是已送出申請(畫面開啟編輯 需要登入者跟申請者一致 且isSubmit是0)
    const isEdit = ref(false);

    //加簽的彈出視窗
    const signatureBmodel = ref(null);

    //流程圖預設物件
    const filePathData = reactive({
      filePathName: '',
    });

    //申請人選擇器
    const errandBmodel = ref(null);

    //附件上傳預設物件
    let appendixData = reactive({});

    //用formId查詢表單的附件
    let fileDataId = reactive({
      fileId: ''
    });

    //處理意見物件
    let opinion = reactive({
      opinionData: ''
    });

    const notificationService = useNotification();
    const $bvModal = useBvModal();

    //列舉型別
    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY = '簽核'
    }

    const formDefault = {
      formId: '', //表單編號
      applyDate: null, //	申請日期
      filEmpid: '', //	填表人員工編號
      filName: '', //	填表人姓名
      filUnit: '', //	填表人單位名稱
      appEmpid: '', //	申請人員工編號
      appName: '', //	申請人姓名
      appUnit: '', //	申請人單位名稱
      isSubmit: '', //	是否暫存、送出
      isEnable: '1', //	規則
      enableTime: '', //使用時段
      workingTime: '', //每周一至周五使用時段內容
      otherEnableTime: '', //使用特殊時段內容
      selecteEdateType: '', //	啟用期間類別
      sdate: null, //啟用期間開始時間
      edate: null, //啟用期間結束時間
      othereEdate: '', //職務異動止說明
      delEnableDate: '', //刪除規則時間
      sourceIp: '', //來源 ip
      targetIp: '', //目的 ip
      port: '', //	使用協定(port)
      isTcp: '', //	傳輸模式是否為tcp
      isUdp: '', //	傳輸模式是否為udp
      instructions: '', //	用途說明
      agreeType: '', //	處理意見
      scheduleDate: null, //預定完成日期
      partialAgreeReason: '', //	部分同意設定原因
      notAgreeReason: '', //不同意設定原因
      isExternalFirewall: '', //	變更設備：是否為外部防火牆
      isInternalFirewall: '', //	變更設備：是否為內部防火牆
      firewallContent: '', //	設定內容
      finishDatetime: null, //	實際完成日期
      processInstanceId: '', //流程實體編號
      processInstanceStatus: '', //流程實體狀態
      taskId: '',   //任務ID
      taskName: '', //任務名稱
      opinion: '', //審核的處理意見
      decisionRole: '',
      formName: 'L414',
    };
    const form = reactive(Object.assign({}, formDefault));
    const rules = {
      formId: {},
      applyDate: {required},
      filEmpid: {required},
      filName: {required},
      filUnit: {required},
      appEmpid: {required},
      appName: {required},
      appUnit: {required},
      isSubmit: {},
      isEnable: {required},
      enableTime: {required},
      workingTime: {},
      otherEnableTime: {},
      selecteEdateType: {required},
      sdate: {},
      edate: {},
      othereEdate: {},
      delEnableDate: {},
      sourceIp: {required,confIp},
      targetIp: {required,confIp},
      port: {required,confPort},
      isTcp: {cofTransmission},
      isUdp: {cofTransmission},
      instructions: {},
      agreeType: {},
      scheduleDate: {},
      partialAgreeReason: {},
      notAgreeReason: {},
      isExternalFirewall: {},
      isInternalFirewall: {},
      firewallContent: {},
      finishDatetime: {},
      opinion: {},
    };
    const {$v, checkValidity, reset} = useValidation(rules, form, formDefault);

    //根據傳來的formId去後端查出哪個表單
    function handleQuery() {
      axios
        .post(`/process/getIsms/L414/${formIdProp.value}`)
        .then(({data}) => {
          if (!data) return;
          if (data.processInstanceId !== null && data.processInstanceId !== undefined) {
            handleQueryFlowChart(data.processInstanceId);
          }

          data.applyDate = data.applyDate != null ? new Date(data.applyDate) : null
          data.sdate = data.sdate != null ? new Date(data.sdate) : null
          data.edate = data.edate != null ? new Date(data.edate) : null
          data.delEnableDate = data.delEnableDate != null ? new Date(data.delEnableDate) : null
          data.scheduleDate = data.scheduleDate != null ? new Date(data.scheduleDate) : null
          data.finishDatetime = data.finishDatetime != null ? new Date(data.finishDatetime) : null

          //判斷暫存還是已送出申請(畫面開啟編輯 需要登入者跟申請者一致 且isSubmit是0)
          isEdit.value = userData.userName !== data.appName || data.isSubmit !== '0'

          Object.assign(formDefault, data);
          Object.assign(form, formDefault);
          reset();
        })
        .catch(notificationErrorHandler(notificationService));

    }

    //查出流程圖
    function handleQueryFlowChart(processInstanceId) {
      axios
        .get(`/process/flowImage/${processInstanceId}`)
        .then(({data}) => {
          filePathData.filePathName = data;
        })
        .catch(notificationErrorHandler(notificationService));
    }

    const headers = {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    };

    //送出申請  狀態: 暫存0 申請1
    async function submitForm(isSubmit) {
      checkValidity().then((isValid: boolean) => {
        if (isValid) {
          $bvModal.msgBoxConfirm('是否確認送出修改內容?').then((isOK: boolean) => {
            if (isOK) {
              const formData = new FormData();

              form.isSubmit = isSubmit;

              let body = {
                "L414": JSON.stringify(form)
              }

              formData.append('form', new Blob([JSON.stringify(body)], {type: 'application/json'}));

              if (JSON.stringify(appendixData.value) !== '[]') {
                for (let i in appendixData.value) {
                  formData.append('appendixFiles', appendixData.value[i].file[0]);
                }
                formData.append('fileDto', new Blob([JSON.stringify(appendixData.value)], {type: 'application/json'}));
              }

              axios
                .patch(`/process/patch/L414`, formData, headers)
                .then(({data}) => {
                  if (isSubmit === '1') {
                    reviewStart(isSubmit, false);
                  } else {
                    $bvModal.msgBoxOk('表單更新完畢');
                    navigateByNameAndParams('pending', {isReload: false, isNotKeepAlive: true, query: queryRef.value});
                  }
                })
                .catch(notificationErrorHandler(notificationService));
            }
          });
        }else {
          $bvModal.msgBoxOk('欄位尚未填寫完畢，請於輸入完畢後再行送出。');
        }
      })
    }

    //根據畫面點選的按送出，同意 不同意 審核(除了申請者的上級會有同意跟不同意，其餘只會有送出按鈕) 補件
    async function reviewStart(item, i) {

      let isOK = true;
      if (i === true) {
        //判斷審核腳色是否為資推或是機房的，正常都是走else裡，資推或是機房的才會用上面if去判斷(資推或是機房的審核要看畫面的form.agreeType去決定是否同意)
        if (configRoleToBpmIpt(userData.userRole) || configRoleToBpmCrOperator(userData.userRole)) {
          isOK = await $bvModal.msgBoxConfirm('是否送出' + getAgreeType(form.agreeType) + '?');
        } else {
          isOK = await $bvModal.msgBoxConfirm('是否送出' + item + '?');
        }
      }

      //只有在審核狀態下才會進入
      if (formStatusRef.value === FormStatusEnum.VERIFY) {

        //判斷是否是資推的人跟畫面必填欄位是否為空
        if (configRoleToBpmIpt(userData.userRole) && form.agreeType === '') {
          isOK = false;
          return notificationService.makeModalComfirmCallback('未選擇處理意見', 'danger');
        }

        //判斷是否是資推的人跟畫面必填欄位是否為空
        if (configRoleToBpmIpt(userData.userRole) && form.partialAgreeReason === '' && form.notAgreeReason === '' && form.scheduleDate === null) {
          isOK = false;
          return notificationService.makeModalComfirmCallback('未填寫預定完成日期或說明原因', 'danger');
        }

        //判斷是否是機房操作的人跟畫面必填欄位是否為空
        if (configRoleToBpmCrOperator(userData.userRole)) {
          if (form.isExternalFirewall === '' && form.isInternalFirewall === '') {
            isOK = false;
            return notificationService.makeModalComfirmCallback('未選擇變更設備', 'danger');
          }
          if (form.firewallContent === '') {
            isOK = false;
            return notificationService.makeModalComfirmCallback('未填寫設定內容', 'danger');
          }
          if (form.finishDatetime === null) {
            isOK = false;
            return notificationService.makeModalComfirmCallback('未選擇實際完成日期', 'danger');
          }
        }
      }


      if (isOK) {

        let variables = {};

        if (taskDataRef.value.decisionRole !== null) {
          let mapData = new Map<string, string>();

          //判斷審核腳色是否為資推或是機房的，正常都是走else裡，資推或是機房的才會用上面if去判斷(資推或是機房的審核要看畫面的form.agreeType去決定是否同意)
          if (configRoleToBpmIpt(userData.userRole) || configRoleToBpmCrOperator(userData.userRole)) {
            mapData.set(taskDataRef.value.decisionRole, getItem(getAgreeType(form.agreeType)))
          } else {
            mapData.set(taskDataRef.value.decisionRole, getItem(item))
          }

          let arrData = Array.from(mapData);
          variables = Object.fromEntries(arrData)
        }

        form.opinion = opinion.opinionData

        let opinionData = '';

        //依照畫面組出審核的處理意見
        if (form.opinion !== '') {
          opinionData = '(意見)' + form.opinion;
        } else {
          if (item !== '1') {
            opinionData = '(' + item + ')';
          }
        }

        const formData = new FormData();

        let completeReqDTO = {
          signer: userData.userName,
          signerId: userData.userId,
          signUnit: userData.deptId,
          processInstanceId: taskDataRef.value.additional ? taskDataRef.value.processInstanceId : form.processInstanceId,
          taskId: taskDataRef.value.taskId !== '' ? taskDataRef.value.taskId : '',
          taskName: taskDataRef.value.taskName !== '' ? taskDataRef.value.taskName : '',
          variables,
          form: {"L414": JSON.stringify(form)},
          directions: changeDirections(taskDataRef.value.decisionRole),
          opinion: opinionData,
          ipt: configRoleToBpmIpt(userData.userRole) || configRoleToBpmCrOperator(userData.userRole),
        };

        formData.append('completeReqDTO', new Blob([JSON.stringify(completeReqDTO)], {type: 'application/json'}));

        //判斷appendix頁面傳過來的file
        if (JSON.stringify(appendixData.value) !== '[]') {
          for (let i in appendixData.value) {
            formData.append('appendixFiles', appendixData.value[i].file[0]);
          }
          formData.append('fileDto', new Blob([JSON.stringify(appendixData.value)], {type: 'application/json'}));
        }

        axios
          .post(`/process/completeTask/${form.formId}`, formData, headers)
          .then(({data}) => {
            if (item === '1') {
              $bvModal.msgBoxOk('表單儲存完畢');
              navigateByNameAndParams('pending', {isReload: false, isNotKeepAlive: true, query: queryRef.value,});
            } else {
              $bvModal.msgBoxOk('表單審核完畢');
              navigateByNameAndParams('pending', {isReload: false, isNotKeepAlive: true, query: queryRef.value,});
            }

          })
          .catch(notificationErrorHandler(notificationService));
      }
    }

    //b-tab分頁用
    const changeTabIndex = (index: number) => {
      tabIndex.value = index;
    };

    //b-tab分頁用
    const activeTab = (index: number) => {
      return tabIndex.value === index;
    };

    const getItem = (item: string) => {
      switch (item) {
        case '不同意':
          return '0';
        case '同意':
          return '1';
        case '補件':
          return '2';
        case '審核':
          return '1';
        case '同意設定':
          return '1';
        case '部分同意設定':
          return '1';
        case '不同意設定':
          return '0';
        default:
          return '';
      }
    };

    const getAgreeType = (item: string) => {
      switch (item) {
        case '1':
          return '同意設定';
        case '2':
          return '部分同意設定';
        case '3':
          return '不同意設定';
        default:
          return '';
      }
    };

    //使用時段 切換時清空已填資料
    function changeEnableTime() {
      form.workingTime = '';
      form.otherEnableTime = '';
    }

    //啟用期間、停用期間  切換時清空已填資料
    function changeSelecteEdateType() {
      form.sdate = null;
      form.edate = null;
      form.othereEdate = '';
      form.delEnableDate = null;
    }

    //處理意見 切換時清空已填資料
    function changeAgreeType() {
      form.scheduleDate = null;
      form.partialAgreeReason = '';
      form.notAgreeReason = '';
    }

    //撤銷表單
    async function toCancel() {
      let isOK = await $bvModal.msgBoxConfirm('是否撤銷表單?');
      if (isOK) {
        let request = {
          processInstanceId: form.processInstanceId,
          key: 'L414'
        }
        axios.post(`/process/deleteProcessInstance`, request)
          .then(({data}) => {
            $bvModal.msgBoxOk('表單流程撤銷完畢');
            handleBack({isReload: true, isNotKeepAlive: false, query: queryRef.value,});
          })
          .catch(notificationErrorHandler(notificationService))
      }
    }

    //返回上一頁
    function toQueryView() {
      handleBack({isReload: true, isNotKeepAlive: false, query: queryRef.value,});
    }

    //開啟加簽的彈出視窗
    function showModel() {
      signatureBmodel.value.isShowDia(true);
    }

    //開啟申請人選擇器
    function showErrandModel() {
      errandBmodel.value.isShowDia(true);
    }

    watch(formIdProp, (value) => {
        //用現在表單編號直接給file模組去自動取值
        fileDataId.fileId = value;
        handleQuery();
      },
      {immediate: true}
    )

    return {
      $v,
      form,
      checkValidity,
      validateState,
      submitForm,
      changeTabIndex,
      activeTab,
      filePathData,
      userData,
      appendixData,
      toQueryView,
      reset,
      formStatusRef,
      FormStatusEnum,
      fileDataId,
      reviewStart,
      bpmDeptsOptions,
      isSignatureRef,
      signatureBmodel,
      showModel,
      formIdProp,
      opinion,
      taskDataRef,
      processInstanceStatusRef,
      configRoleToBpmCrOperator,
      configRoleToBpmIpt,
      changeEnableTime,
      changeSelecteEdateType,
      changeAgreeType,
      isEdit,
      toCancel,
      isCancelRef,
      configTitleName,
      errandBmodel,
      showErrandModel,
      tabIndex,
    }
  }
}
</script>

<style>

.nav-tabs .nav-link.active, .nav-tabs .nav-item.show .nav-link {
  color: black;
  background-color: #d3ede8;
  border-color: #dee2e6 #dee2e6 #e4e5e6;
}

.nav-tabs .nav-link {
  text-align: center;
  width: 150px;
  margin-bottom: -1px;
  border: 1px solid transparent;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.fixedWidth .custom-control-label {
  width: 100%;
}

</style>


