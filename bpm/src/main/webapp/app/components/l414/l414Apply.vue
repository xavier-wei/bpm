<template>
  <div>

    <b-card-body no-body>
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
              <i-form-group-check
                labelStar
                class="col-sm-5"
                label-cols="5"
                content-cols="7"
                :label="'填表人：員工編號：'"
                :item="$v.filEmpid"
              >
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
                    <b-form-select-option value="" disabled>請選擇</b-form-select-option>
                  </template>
                </b-form-select>
              </i-form-group-check>
            </b-form-row>

            <b-form-row>
              <i-form-group-check
                labelStar
                class="col-sm-5"
                label-cols="5"
                content-cols="7"
                :label="'申請人：員工編號：'"
                :item="$v.appEmpid"
              >
                <b-input-group>
                  <!--申請人員工編號 : appEmpid-->
                  <b-form-input v-model="$v.appEmpid.$model"/>
                  <b-button class="ml-2" variant="outline-dark" @click="showErrandModel()">選擇人員</b-button>
                </b-input-group>
              </i-form-group-check>

              <i-form-group-check class="col-sm-3" label-cols="5" content-cols="7" :label="`姓名：`"
                                  :item="$v.appName">
                <!--申請人姓名 :　appName-->
                <b-form-input v-model="$v.appName.$model"/>
              </i-form-group-check>

              <i-form-group-check class="col-sm-4" label-cols="5" content-cols="7" :label="`單位：`"
                                  :item="$v.appUnit">
                <!--申請人單位名稱 : appUnit-->
                <b-form-select v-model="$v.appUnit.$model" :options="bpmDeptsOptions">
                  <template #first>
                    <b-form-select-option value="" disabled>請選擇</b-form-select-option>
                  </template>
                </b-form-select>
              </i-form-group-check>
            </b-form-row>

            <div class="card m-3" style="background-color: white">
              <b-form-row>

                <b-col class="col-sm-5">
                  <i-form-group-check labelStar class="col-12" label-cols="3" content-cols="7" :label="`規則：`"
                                      :item="$v.isEnable">
                    <!--規則 : isEnable-->
                    <b-form-radio-group
                      v-model="$v.isEnable.$model"
                      :options="[
                              { value: '1', text: '啟用' },
                              { value: '0', text: '停用' },
                            ]"
                    />
                  </i-form-group-check>

                  <i-form-group-check labelStar class="col-12" label-cols="3" content-cols="7" :label="`使用時段：`"
                                      :item="$v.enableTime">
                    <!--使用時段 : enableTime-->
                    <b-form-radio-group v-model="$v.enableTime.$model" @change="changeEnableTime">

                      <b-form-radio class="col-12" value="1">
                        <div style="height: 34px">每日24小時</div>
                      </b-form-radio>


                      <b-form-radio class="col-12" value="2">
                        <div style="height: 34px">每周一至周五 :</div>
                        <!--每周一至周五使用時段內容 : workingTime-->
                        <b-form-input :disabled="$v.enableTime.$model !== '2'"
                                      v-model="$v.workingTime.$model"/>
                      </b-form-radio>

                      <b-form-radio class="col-12" value="3">
                        <div style="height: 34px">特殊時段 :</div>
                        <!--使用特殊時段內容 : otherEnableTime-->
                        <b-form-input :disabled="$v.enableTime.$model !== '3'"
                                      v-model="$v.otherEnableTime.$model"/>
                      </b-form-radio>
                    </b-form-radio-group>
                  </i-form-group-check>
                </b-col>
                <b-col>
                  <i-form-group-check
                    labelStar
                    class="col-sm-12"
                    label-cols="3"
                    content-cols="7"
                    :label="`啟用期間：`"
                    :item="$v.selecteEdateType"
                  >
                    <!--啟用期間類別 : selecteEdateType-->
                    <b-form-radio-group v-model="$v.selecteEdateType.$model" @change="changeSelecteEdateType">
                      <b-form-radio value="1" style="margin-top: 30px">
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
                                      :disabled="$v.selecteEdateType.$model !== '2'"/>
                        <span class="d-inline col-7">職務異動止</span>
                      </b-form-radio>
                      <b-form-radio value="3">
                        <div class="m-1 col-12" style="height: 5px">永久使用(僅電腦機房可勾選)</div>
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
                    <b-form-radio-group v-model="$v.selecteEdateType.$model" @change="changeSelecteEdateType">
                      <b-form-radio value="4">
                        <!--刪除規則時間 : delEnableDate-->
                        <b-input-group>
                          <i-date-picker
                            :disabled="$v.selecteEdateType.$model !== '4'"
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
              </b-form-row>

              <b-form-row>
                <i-form-group-check class="col-sm-5" labelStar label-cols="3" content-cols="7" :label="'來源IP ：'"
                                    :item="$v.sourceIp">
                  <!--來源IP : sourceIp-->
                  <b-form-textarea v-model="$v.sourceIp.$model" rows="3" maxlength="2000" lazy trim/>
                </i-form-group-check>

                <i-form-group-check class="col-sm-7" labelStar label-cols="3" content-cols="7" :label="`目的IP ：`"
                                    :item="$v.targetIp">
                  <!--目的IP : targetIp-->
                  <b-form-textarea v-model="$v.targetIp.$model" rows="3" maxlength="2000" lazy trim/>
                </i-form-group-check>
              </b-form-row>

              <b-form-row>
                <i-form-group-check class="col-sm-5" labelStar label-cols="3" content-cols="7" :label="'使用協定(port) ：'"
                                    :item="$v.port">
                  <!--使用協定(port) : port-->
                  <b-form-input v-model="$v.port.$model"/>
                </i-form-group-check>

                <i-form-group-check class="col-sm-7" labelStar label-cols="3" content-cols="7" :label="`傳輸模式 ：`" :item="[$v.isTcp,$v.isUdp]">
                  <b-input-group>
                    <!--傳輸模式是否為tcp: isTcp-->
                    <b-form-checkbox class="col-6" v-model="$v.isTcp.$model" value="Y" unchecked-value=""> TCP
                    </b-form-checkbox>
                    <!--傳輸模式是否為udp: isUdp-->
                    <b-form-checkbox class="col-6" v-model="$v.isUdp.$model" value="Y" unchecked-value=""> UDP
                    </b-form-checkbox>
                  </b-input-group>
                </i-form-group-check>

              </b-form-row>

              <b-form-row>
                <i-form-group-check
                  class="col-sm-5"
                  label-cols="3"
                  content-cols="7"
                  :label="'用途說明 ：'"
                  :item="$v.instructions"
                >
                  <!--                用途說明 : instructions-->
                  <b-form-textarea v-model="$v.instructions.$model" rows="3" maxlength="2000" trim lazy/>
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
                  <b-form-radio-group v-model="$v.agreeType.$model" disabled>
                    <!--預定完成日期 : scheduleDate-->
                    <b-form-radio class="col-12" value="1">
                      <b-input-group>
                        <div>同意設定 : 預定完成日期 : 　</div>
                        <i-date-picker
                          disabled
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
                          disabled
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
                          disabled
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
                <i-form-group-check class="col-sm-12" label-cols="1" content-cols="11" :label="`變更設備 ： `">
                  <b-input-group>
                    <!--是否為外部防火牆 : isExternalFirewall-->
                    <b-form-checkbox v-model="$v.isExternalFirewall.$model" value="Y" unchecked-value="N"
                                     disabled>
                      外部防火牆
                    </b-form-checkbox>
                    <!--變更設備：是否為內部防火牆 : isInternalFirewall-->
                    <b-form-checkbox class="mx-3" v-model="$v.isInternalFirewall.$model" value="Y" unchecked-value="N"
                                     disabled>
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
                  :label="'設定內容 ： '"
                  :item="$v.firewallContent"
                >
                  <!--設定內容 : firewallContent-->
                  <b-form-textarea v-model="$v.firewallContent.$model" rows="1" maxlength="2000" trim lazy
                                   disabled/>
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
                      disabled
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

            <b-container class="mt-3">
              <b-row class="justify-content-center">
                <i-button class="ml-2" type="tag" @click="submitForm('0')"/>
                <i-button class="ml-2" type="node-plus" @click="submitForm('1')"/>
                <i-button class="ml-2" type="x-circle" @click="reset()"/>
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

    <!--申請人選擇器-->
    <errandBmodel ref="errandBmodel" :formData="form"></errandBmodel>
  </div>
</template>

<script lang="ts">

import IDualDatePicker from '@/shared/i-date-picker/i-dual-date-picker.vue';
import {reactive, ref, toRef, watch,computed} from '@vue/composition-api';
import {useValidation, validateState} from '@/shared/form';
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';
import {required,cofTransmission,confPort,confIp} from '@/shared/validators';
import IDatePicker from '@/shared/i-date-picker/i-date-picker.vue';
import {useBvModal} from '@/shared/modal';
import {notificationErrorHandler} from '@/shared/http/http-response-helper';
import {useNotification} from '@/shared/notification';
import axios from 'axios';
import {useGetters} from '@u3u/vue-hooks';
import {navigateByNameAndParams} from "@/router/router";
import {handleBack} from '@/router/router';
import errandBmodel from "@/components/errandBmodel.vue";
import IButton from '@/shared/buttons/i-button.vue';

const appendix = () => import('@/components/appendix.vue');
const flowChart = () => import('@/components/flowChart.vue');

export default {
  name: 'l414Apply',
  props: {
    formStatus: {
      required: false,
      type: String,
    },
  },
  components: {
    'i-form-group-check': IFormGroupCheck,
    'i-dual-date-picker': IDualDatePicker,
    'i-date-picker': IDatePicker,
    appendix,
    flowChart,
    errandBmodel,
    'i-button': IButton,
  },
  setup(props) {
    //登入者資訊
    const userData = ref(useGetters(['getUserData']).getUserData).value

    //單位下拉選單資訊
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;

    //進入表單的模式
    const formStatusRef = toRef(props, 'formStatus');

    //分頁預設值
    const tabIndex = ref(0);

    //申請人選擇器
    const errandBmodel = ref(null);

    //流程圖預設物件,這裡要預設參數防止出現前端檢查錯誤
    const filePathData = reactive({
      filePathName: '',
    });

    //附件上傳預設物件,這裡要預設參數防止出現前端檢查錯誤
    let appendixData = reactive({});

    //用formId查詢表單的附件，這裡要預設參數防止出現前端檢查錯誤
    let fileDataId = reactive({
      fileId: ''
    });

    const notificationService = useNotification();
    const $bvModal = useBvModal();

    const headers = {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    };

    const formDefault = {
      formId: '', //表單編號
      applyDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate(), new Date().getHours(), new Date().getMinutes(), new Date().getSeconds(), new Date().getMilliseconds()), //	申請日期
      filEmpid: userData.empId != null ? userData.empId : '', //	填表人員工編號
      filName: userData.userName != null ? userData.userName : '', //	填表人姓名
      filUnit: userData.deptId != null ? userData.deptId : '', //	填表人單位名稱
      appEmpid: userData.empId != null ? userData.empId : '', //	申請人員工編號
      appName: userData.userName != null ? userData.userName : '', //	申請人姓名
      appUnit: userData.deptId != null ? userData.deptId : '', //	申請人單位名稱
      isSubmit: '', //	是否暫存、送出
      isEnable: '1', //	規則
      enableTime: '', //使用時段
      workingTime: '', //每周一至周五使用時段內容
      otherEnableTime: '', //使用特殊時段內容
      selecteEdateType: '', //	啟用期間類別
      sdate: null, //啟用期間開始時間
      edate: null, //啟用期間結束時間
      othereEdate: '', //職務異動止說明
      delEnableDate: null, //刪除規則時間
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
    };
    const {$v, checkValidity, reset} = useValidation(rules, form, formDefault);

    //送出申請  狀態: 暫存0 申請1
    async function submitForm(isSubmit) {

      checkValidity().then((isValid: boolean) => {

        if (isValid) {
          $bvModal.msgBoxConfirm('是否確認送出修改內容？').then((isOK: boolean) => {
            if (isOK) {
              form.isSubmit = isSubmit;

              let body = {
                "L414": JSON.stringify(form)
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
                .post(`/process/start/L414`, formData, headers)
                .then(({data}) => {
                  $bvModal.msgBoxOk('表單新增完畢');
                  reset();
                  navigateByNameAndParams('l414Query', {isReload: false, isNotKeepAlive: true});
                })
                .catch(notificationErrorHandler(notificationService));

            }
          });
        } else {
          $bvModal.msgBoxOk('欄位尚未填寫完畢，請於輸入完畢後再行送出。');
        }
      });
    }

    //b-tab分頁用
    const changeTabIndex = (index: number) => {
      tabIndex.value = index;
    };
    //b-tab分頁用
    const activeTab = (index: number) => {
      return tabIndex.value === index;
    };

    //返回上一頁
    function toQueryView() {
      handleBack({isReload: false, isNotKeepAlive: true});
    }

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

    //開啟申請人選擇器
    function showErrandModel() {
      errandBmodel.value.isShowDia(true);
    }

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
      bpmDeptsOptions,
      fileDataId,
      changeEnableTime,
      changeSelecteEdateType,
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

.flex-column {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.fixedWidth .custom-control-label {
  width: 100%;
}

.text-danger {
  font-weight: bold;
  width: 100%;
  margin-top: 0.25rem;
  font-size: 80%;
  color: #dc3545;
}

</style>


