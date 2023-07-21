<template>
  <div>
    <b-container>
      <section class="container mt-2">

        <div class="card">

          <b-card-body>
            <b-tabs>
              <b-tab title="表單" :active="activeTab(0)" @click="changeTabIndex(0)">

                <div style="background-color: #008b8b;padding-top: 10px;">
                  <b-row class=" d-flex">
                    <p class="ml-3" style="color: white">
                      L414-網路服務連結申請單
                    </p>

                    <P class="ml-3">機密等級： 敏感</P>
                  </b-row>
                </div>

                <div class="card" style="background-color: #8fd4ce">

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
                      ></i-date-picker>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-3" label-cols="5" content-cols="7" :label="`表單編號：`"
                                        :item="$v.formId">
                      <!--表單編號 : formId -->
                      <b-form-input v-model="$v.formId.$model"/>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-4" label-cols="5" content-cols="7" :label="`註：`">
                      <span class="text-danger">*</span>
                      <p style="margin-top: 10px">為申請必填欄位</p>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'填表人：員工編號：'"
                                        :item="$v.filEmpid">
                      <!--填表人員工編號 : filEmpid-->
                      <b-form-input v-model="$v.filEmpid.$model"/>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-3" label-cols="5" content-cols="7" :label="`姓名：`"
                                        :item="$v.filName">
                      <!--填表人姓名 :　filName-->
                      <b-form-input v-model="$v.filName.$model"/>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-4" label-cols="5" content-cols="7" :label="`單位：`"
                                        :item="$v.filUnit">
                      <!--填表人單位名稱　: filUnit-->
                      <b-form-select v-model="$v.filUnit.$model" :options="options.filUnitOptions">
                        <template #first>
                          <b-form-select-option value="null" disabled>請選擇</b-form-select-option>
                          <b-form-select-option value="">全部</b-form-select-option>
                        </template>
                      </b-form-select>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'申請人：員工編號：'"
                                        :item="$v.appEmpid">
                      <!--申請人員工編號 : appEmpid-->
                      <b-form-input v-model="$v.appEmpid.$model"/>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-3" label-cols="5" content-cols="7" :label="`姓名：`"
                                        :item="$v.appName">
                      <!--申請人姓名 :　appName-->
                      <b-form-input v-model="$v.appName.$model"/>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-4" label-cols="5" content-cols="7" :label="`單位：`"
                                        :item="$v.appUnit">
                      <!--申請人單位名稱 : appUnit-->
                      <b-form-select v-model="$v.appUnit.$model" :options="options.appUnitOptions">
                        <template #first>
                          <b-form-select-option value="null" disabled>請選擇</b-form-select-option>
                          <b-form-select-option value="">全部</b-form-select-option>
                        </template>
                      </b-form-select>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`是否暫存：`"
                                        :item="$v.isSubmit">
                      <!--是否暫存、送出 : isSubmit-->
                      <b-form-select v-model="$v.isSubmit.$model">
                        <template #first>
                          <b-form-select-option value="" disabled>請選擇</b-form-select-option>
                          <b-form-select-option value="0">暫存</b-form-select-option>
                          <b-form-select-option value="1">送出</b-form-select-option>
                        </template>
                      </b-form-select>
                    </i-form-group-check>
                  </b-form-row>

                  <b-row>
                    <b-col class="col-sm-5">
                      <i-form-group-check class="col-12" label-cols="5" content-cols="7" :label="`規則：`"
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

                      <i-form-group-check
                        class="col-12"
                        label-cols="5"
                        content-cols="7"
                        :label="`使用時段：`"
                        :item="$v.enableTime"
                      >
                        <!--使用時段 : enableTime-->
                        <b-form-radio-group v-model="$v.enableTime.$model">

                          <b-form-radio value="1">
                            <div style="height: 34px;">每日24小時</div>
                          </b-form-radio>

                          <b-form-radio value="2">
                            <div style="height: 34px;">每周一至周五</div>
                          </b-form-radio>

                          <b-form-radio value="3">
                            <div style="height: 34px;">特殊時段 :</div>
                            <!--使用特殊時段內容 : otherEnableTime-->
                            <b-form-input v-model="$v.otherEnableTime.$model"/>
                          </b-form-radio>

                        </b-form-radio-group>

                      </i-form-group-check>
                    </b-col>
                    <b-col>
                      <i-form-group-check
                        class="col-sm-12"
                        label-cols="2"
                        content-cols="10"
                        :label="`啟用期間：`"
                        :item="$v.selecteDate"
                      >
                        <!--啟用期間類別 : selecteDate-->
                        <b-form-radio-group v-model="$v.selecteDate.$model">
                          <b-form-radio value="1">
                            <!--啟用期間開始時間 : sDate 、啟用期間結束時間 : eDate-->
                            <i-dual-date-picker :dual1.sync="$v.sDate.$model" :dual2.sync="$v.eDate.$model"/>
                          </b-form-radio>
                          <b-form-radio value="2">
                            <!--職務異動止說明 : othereDate-->
                            <b-form-input class="d-inline col-5" v-model="$v.othereDate.$model"/>
                            <span class="d-inline col-7">職務異動止</span>
                          </b-form-radio>
                          <b-form-radio value="3">
                            <div style="height: 34px;">永久使用(僅電腦機房可勾選)</div>
                          </b-form-radio>
                        </b-form-radio-group>

                      </i-form-group-check>

                      <i-form-group-check
                        class="col-sm-12"
                        label-cols="2"
                        content-cols="10"
                        :label="`停用期間：`"
                        :item="$v.selecteDate"
                      >
                        <!--啟用期間類別 : selecteDate-->
                        <b-form-radio-group v-model="$v.selecteDate.$model">

                          <b-form-radio value="4">
                            <!--刪除規則時間 : delEnableDate-->
                            <i-date-picker
                              placeholder="yyy/MM/dd"
                              v-model="$v.delEnableDate.$model"
                              :state="validateState($v.delEnableDate)"
                              lazy
                              trim
                            ></i-date-picker>
                          </b-form-radio>
                          <div><span class="text-danger">*</span>註：申請使用期限最長一年</div>
                        </b-form-radio-group>

                      </i-form-group-check>

                    </b-col>
                  </b-row>


                  <b-form-row>
                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'來源IP：'"
                                        :item="$v.sourceIp">
                      <!--來源IP : sourceIp-->
                      <b-form-input v-model="$v.sourceIp.$model"/>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="`目的IP：`"
                                        :item="$v.targetIp">
                      <!--目的IP : targetIp-->
                      <b-form-input v-model="$v.targetIp.$model"/>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'使用協定(port)：'"
                                        :item="$v.port">
                      <!--使用協定(port) : port-->
                      <b-form-input v-model="$v.port.$model"/>
                    </i-form-group-check>

                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="`傳輸模式 ：`"
                    >

                      <b-input-group>
                        <!--傳輸模式是否為tcp: isTcp-->
                        <b-form-checkbox class="col-6" v-model="$v.isTcp.$model" value="Y" unchecked-value="N">
                          TCP
                        </b-form-checkbox>
                        <!--傳輸模式是否為udp: isUdp-->
                        <b-form-checkbox class="col-6" v-model="$v.isUdp.$model" value="Y" unchecked-value="N">
                          UDP
                        </b-form-checkbox>
                      </b-input-group>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check class="col-sm-12 " label-cols="2" content-cols="8" :label="'用途說明 ：'"
                                        :item="$v.instructions" style="margin-left: 7px">
                      <!--用途說明 : instructions-->
                      <b-form-textarea v-model="$v.instructions.$model" rows="3" maxlength="2000" trim lazy/>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check class="col-sm-3" label-cols="12" content-cols="0" :label="'以下由資訊推動小組填寫'">
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <!--處理意見 : agreeType-->
                    <i-form-group-check class="col-sm-12"
                                        label-cols="2"
                                        content-cols="10" :label="'處理意見：'">
                      <b-form-radio-group v-model="$v.agreeType.$model">

                        <!--預定完成日期 : scheduleDate-->
                        <b-form-radio class="col-12" value="1">
                          <b-input-group>
                            <div>同意設定 : 預定完成日期 : 　</div>
                            <i-date-picker
                              placeholder="yyy/MM/dd"
                              v-model="$v.scheduleDate.$model"
                              :state="validateState($v.scheduleDate)"
                              lazy
                              trim
                            ></i-date-picker>
                          </b-input-group>
                        </b-form-radio>

                        <!--部分同意設定原因 : partialAgreeReason-->
                        <b-form-radio class="col-12" value="2">
                          <b-input-group>
                            <div>部分同意設定 : 原因 :　　 　</div>
                            <b-form-textarea v-model="$v.partialAgreeReason.$model" rows="1" maxlength="2000" trim
                                             lazy/>
                          </b-input-group>
                        </b-form-radio>

                        <!--不同意設定原因 : notAgreeReason-->
                        <b-form-radio class="col-12" value="3">
                          <b-input-group>
                            <div>不同意設定 : 原因 :　　　 　</div>
                            <b-form-textarea v-model="$v.notAgreeReason.$model" rows="1" maxlength="2000" trim lazy/>
                          </b-input-group>
                        </b-form-radio>
                      </b-form-radio-group>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="`規則：`">
                      <b-input-group>
                        <!--是否為外部防火牆 : isExternalFirewall-->
                        <b-form-checkbox v-model="$v.isExternalFirewall.$model" value="Y" unchecked-value="N">
                          外部防火牆
                        </b-form-checkbox>
                        <!--變更設備：是否為內部防火牆 : isInternalFirewall-->
                        <b-form-checkbox v-model="$v.isInternalFirewall.$model" value="Y" unchecked-value="N">
                          外部防火牆
                        </b-form-checkbox>
                      </b-input-group>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check class="col-sm-12" label-cols="2" content-cols="8" :label="'設定內容 ：'"
                                        :item="$v.firewallContent" style="margin-left: 7px">
                      <!--設定內容 : firewallContent-->
                      <b-form-textarea v-model="$v.firewallContent.$model" rows="1" maxlength="2000" trim lazy/>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'實際完成日期:'"
                                        :item="$v.finishDatetime">
                      <!--實際完成日期 : finishDatetime-->
                      <i-date-picker
                        placeholder="yyy/MM/dd"
                        v-model="$v.finishDatetime.$model"
                        :state="validateState($v.finishDatetime)"
                        lazy
                        trim
                      ></i-date-picker>
                    </i-form-group-check>
                  </b-form-row>

                  <div class="m-5">
                    <P>
                      備註：
                    </P>
                    <P>
                      網路服務連結申請經審核通過後，申請人應隨時注意使用期間(時段)之必要性，遇有系統續用、調整、停用或使用屆期時，應主動申請網路服務續用、調整或撤銷。
                    </P>

                  </div>
                </div>

              </b-tab>
              <b-tab title="附件" :active="activeTab(1)" @click="changeTabIndex(1)">

                <appendix>

                </appendix>

              </b-tab>
              <b-tab title="流程圖" :active="activeTab(2)" @click="changeTabIndex(3)">
                <flowChart>

                </flowChart>
              </b-tab>
            </b-tabs>
          </b-card-body>

        </div>
        <b-form-row>
          <b-col cols="4" offset="6">
            <b-button style="background-color: #17a2b8; color: white" size="sm" variant="outline-secondary"
                      @click="submitForm()">申請
            </b-button>
          </b-col>
        </b-form-row>
      </section>
    </b-container>
  </div>
</template>

<script lang="ts">

import {
  BRow,
  BFormRow,
  BFormInput,
  BFormRadioGroup,
  BFormRadio,
  BContainer,
  BCol,
  BFormTextarea,
  BFormCheckbox,
  BButton,
  BInputGroup,
  BFormSelect,
  BFormSelectOption,
  BCardBody,
  BTabs,
  BTab,
} from 'bootstrap-vue';
import IDualDatePicker from '@/shared/i-date-picker/i-dual-date-picker.vue';
import {reactive, ref, Ref, toRef, watch} from '@vue/composition-api';
import {useValidation, validateState} from '@/shared/form';
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';
import {required} from '@/shared/validators';
import IDatePicker from '@/shared/i-date-picker/i-date-picker.vue';
import {useBvModal} from '@/shared/modal';

const appendix = () => import('@/componet/appendix.vue');
const flowChart = () => import('@/componet/FlowChart.vue');
export default {
  name: "l414Apply",
  components: {
    'b-row': BRow,
    'i-form-group-check': IFormGroupCheck,
    'i-dual-date-picker': IDualDatePicker,
    'i-date-picker': IDatePicker,
    'b-form-row': BFormRow,
    'b-form-input': BFormInput,
    'b-form-radio-group': BFormRadioGroup,
    'b-form-radio': BFormRadio,
    'b-container': BContainer,
    'b-col': BCol,
    'b-form-textarea': BFormTextarea,
    'b-form-checkbox': BFormCheckbox,
    'b-button': BButton,
    'b-input-group': BInputGroup,
    'b-form-select': BFormSelect,
    'b-form-select-option': BFormSelectOption,
    BCardBody,
    BTabs,
    BTab,
    appendix,
    flowChart
  },
  setup() {

    const $bvModal = useBvModal();
    const formDefault = {
      formId: '',//表單編號
      applyDate: new Date(new Date().getFullYear(), 0, 1),//	申請日期
      filEmpid: '',//	填表人員工編號
      filName: '',//	填表人姓名
      filUnit: '',//	填表人單位名稱
      appEmpid: '',//	申請人員工編號
      appName: '',//	申請人姓名
      appUnit: '',//	申請人單位名稱
      isSubmit: '',//	是否暫存、送出
      isEnable: '1',//	規則
      enableTime: '',//使用時段
      otherEnableTime: '',//使用特殊時段內容
      selecteDate: '',//	啟用期間類別
      sDate: new Date(new Date().getFullYear(), 0, 1),//啟用期間開始時間
      eDate: new Date(new Date().getFullYear(), 0, 1),//啟用期間結束時間
      othereDate: '',//職務異動止說明
      delEnableDate: '',//刪除規則時間
      sourceIp: '',//來源 ip
      targetIp: '',//目的 ip
      port: '',//	使用協定(port)
      isTcp: '',//	傳輸模式是否為tcp
      isUdp: '',//	傳輸模式是否為udp
      instructions: '',//	用途說明
      agreeType: '',//	處理意見
      scheduleDate: '',//預定完成日期
      partialAgreeReason: '',//	部分同意設定原因
      notAgreeReason: '',//不同意設定原因
      isExternalFirewall: '',//	變更設備：是否為外部防火牆
      isInternalFirewall: '',//	變更設備：是否為內部防火牆
      firewallContent: '',//	設定內容
      finishDatetime: '',//	實際完成日期
    };
    const form = reactive(Object.assign({}, formDefault));
    const rules = {
      formId: {},
      applyDate: {},
      filEmpid: {},
      filName: {},
      filUnit: {},
      appEmpid: {},
      appName: {},
      appUnit: {},
      isSubmit: {required},
      isEnable: {},
      enableTime: {},
      otherEnableTime: {},
      selecteDate: {},
      sDate: {},
      eDate: {},
      othereDate: {},
      delEnableDate: {},
      sourceIp: {},
      targetIp: {},
      port: {},
      isTcp: {},
      isUdp: {},
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

    const options = reactive({
      filUnitOptions: [
        {value: '0', text: '主計室'},
        {value: '1', text: '資訊推動小組'},
        {value: '2', text: '主任委員室'},
      ],
      appUnitOptions: [
        {value: '0', text: '主計室'},
        {value: '1', text: '資訊推動小組'},
        {value: '2', text: '主任委員室'},
      ],
    });

    const submitForm = () => {
      checkValidity().then((isValid: boolean) => {
        if (isValid) {
          $bvModal.msgBoxConfirm('是否確認送出修改內容？').then((isOK: boolean) => {
            if (isOK) {
              console.log('form', form)
            }
          });
        } else {
          $bvModal.msgBoxOk('欄位尚未填寫完畢，請於輸入完畢後再行送出。');
        }
      });
    };

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

    return {
      $v,
      form,
      checkValidity,
      validateState,
      options,
      submitForm,
      changeTabIndex,
      activeTab
    }
  }
}
</script>

<style scoped>


</style>


