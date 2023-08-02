<template>
  <div>
    <b-container>
      <section class="container mt-2">

        <div class="card">

          <b-card-body>
            <b-tabs>
              <b-tab title="表單" :active="activeTab(0)" @click="changeTabIndex(0)">

                <div style="background-color: #b0ded4;padding-top: 10px;">
                  <b-row class=" d-flex">
                    <p class="ml-3" style="color: white">
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
                    <p class="test1">申請者資訊</p>
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

<!--                  <b-form-row>-->
<!--                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`是否暫存：`"-->
<!--                                        :item="$v.isSubmit">-->
<!--                      &lt;!&ndash;是否暫存、送出 : isSubmit&ndash;&gt;-->
<!--                      <b-form-select v-model="$v.isSubmit.$model">-->
<!--                        <template #first>-->
<!--                          <b-form-select-option value="" disabled>請選擇</b-form-select-option>-->
<!--                          <b-form-select-option value="0">暫存</b-form-select-option>-->
<!--                          <b-form-select-option value="1">送出</b-form-select-option>-->
<!--                        </template>-->
<!--                      </b-form-select>-->
<!--                    </i-form-group-check>-->
<!--                  </b-form-row>-->

                  <b-form-row>
                    <p class="test1">一、個人基本資料：</p>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="'姓名(中文) ：'"
                                        :item="$v.chName">
                      <!--姓名(中文) : -->
                      <b-form-input v-model="$v.chName.$model"/>
                    </i-form-group-check>

                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`單位別 ：`"
                                        :item="$v.appUnit">
                      <!--單位別 : -->
                      <b-form-select v-model="$v.appUnit.$model" :options="options.appUnitOptions">
                        <template #first>
                          <b-form-select-option value="null" disabled>請選擇</b-form-select-option>
                          <b-form-select-option value="">全部</b-form-select-option>
                        </template>
                      </b-form-select>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="'姓名(英文) ：'"
                                        :item="$v.enName">
                      <!--姓名(英文) : -->
                      <b-form-input v-model="$v.enName.$model"/>
                    </i-form-group-check>

                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`科別 ：`"
                                        :item="$v.subject">
                      <!--科別 : -->
                      <b-form-input v-model="$v.subject.$model"/>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="'員工編號 ：'"
                                        :item="$v.appEmpid">
                      <!--員工編號 : -->
                      <b-form-input v-model="$v.appEmpid.$model"/>
                    </i-form-group-check>

                    <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`職稱 ：`"
                                        :item="$v.jobName">
                      <!--職稱 : -->
                      <b-form-input v-model="$v.jobName.$model"/>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'分機 ：'"
                                        :item="$v.extendNumber">
                      <!--分機 : -->
                      <b-form-input v-model="$v.extendNumber.$model"/>
                    </i-form-group-check>
                  </b-form-row>

                  <b-form-row>
                    <p class="test1">二、 <span class="text-danger">*</span>申請事由：</p>
                  </b-form-row>

                  <b-form-row>
                    <!--申請事由-->
                    <b-form-radio-group class="offset-1" v-model="$v.applicationReason.$model">
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
                      <!--生效日期checkbox : effective-->
                      <b-form-checkbox class="col-2" v-model="$v.effective.$model" value="Y" unchecked-value="N">
                        生效日期 :
                      </b-form-checkbox>
                      <!--生效日期 : effectiveDate-->
                      <i-date-picker
                        class="col-2"
                        placeholder="yyy/MM/dd"
                        v-model="$v.effectiveDate.$model"
                        lazy
                        trim
                      ></i-date-picker>
                      <!--other: other-->
                      <b-form-checkbox class="col-2 offset-1" v-model="$v.other.$model" value="Y" unchecked-value="N">
                        其他 :
                      </b-form-checkbox>
                      <!--其他說明 : otherRemark-->
                      <b-form-input class="col-3" v-model="$v.otherRemark.$model"/>
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
                      :items="mockdata"
                      :fields="table.fields"
                    >

                      <template #cell(checkbox)="row">
                        <b-form-checkbox v-model="row.item.checkbox" :value="true" :unchecked-value="false"/>
                      </template>

                      <!--TODO: 未綁 v-model-->
                      <!--申請項目-->
                      <template #cell(applyItem)="row">
                        <div>
                          {{ systemToName(row.item.systemApply) }}
                        </div>
                        <b-form-input v-if="row.item.systemApply === '1'" v-model="row.item.applyItem"
                                      maxlength="200"></b-form-input>
                        <b-form-input v-if="row.item.systemApply === '4'" v-model="row.item.applyItem"
                                      maxlength="200"></b-form-input>
                        <span v-if="row.item.systemApply === '4'">@mail.pcc.gov.tw</span>

                        <b-form-radio-group class="col-12" v-if="row.item.systemApply === '2'"
                                            v-model="row.item.applyItem"
                                            :options="options.type"/>
                        <b-form-input v-if="row.item.systemApply === '7'" v-model="row.item.applyItem"
                                      maxlength="200"></b-form-input>

                        <div
                          v-if="row.item.systemApply === '13' || row.item.systemApply === '14' || row.item.systemApply === '15'"
                          v-model="row.item.applyItem">
                          <div>系統名稱 :</div>
                          <b-form-input maxlength="200"></b-form-input>
                          <div>帳號 :</div>
                          <b-form-input maxlength="200"></b-form-input>
                        </div>
                      </template>

                      <!--TODO: 未綁 v-model-->
                      <!--處理權限-->
                      <template #cell(permissions)="row">
                        <b-form-radio-group v-if="row.item.systemApply === '4'" v-model="row.item.permissions">
                          <b-form-radio class="col-12" value="1">
                            <div>新增</div>
                            <b-input-group>
                              <div>1.　</div>
                              <b-form-input maxlength="200"/>
                            </b-input-group>
                            <b-input-group>
                              <div>2.　</div>
                              <b-form-input maxlength="200"/>
                            </b-input-group>
                          </b-form-radio>

                          <p>(第一個帳號名稱若已有人設定,則設為第二個帳號)</p>

                          <b-form-radio class="col-12" value="2">
                            <div>刪除</div>
                          </b-form-radio>

                          <b-form-radio class="col-12" value="3">
                            <b-input-group>
                              <div>異動 :　</div>
                              <b-form-input maxlength="200"/>
                            </b-input-group>
                          </b-form-radio>
                        </b-form-radio-group>

                        <b-form-radio-group v-else-if="row.item.systemApply === '2'" v-model="row.item.permissions">
                          <b-form-radio class="col-12" value="1">
                            <div>新增</div>
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
                              <b-form-input maxlength="200"/>
                            </b-input-group>
                          </b-form-radio>
                        </b-form-radio-group>

                        <b-form-radio-group v-else-if="row.item.systemApply === '5' || row.item.systemApply === '6' "
                                            v-model="row.item.permissions">
                          <b-form-radio class="col-12" value="1">
                            <div>新增</div>
                          </b-form-radio>

                          <b-form-radio class="col-12" value="2">
                            <b-input-group>
                              <div>異動 :　</div>
                              <b-form-input maxlength="200"/>
                            </b-input-group>
                          </b-form-radio>

                          <b-form-checkbox value="Y" unchecked-value="N">
                            部門管理者
                          </b-form-checkbox>

                          <b-form-checkbox value="Y" unchecked-value="N">
                            部門資料維護者
                          </b-form-checkbox>

                          <b-form-checkbox value="Y" unchecked-value="N">
                            其他 :　
                          </b-form-checkbox>
                          <b-form-input maxlength="200"/>

                          <b-form-radio class="col-12" value="3">
                            <div>帳號停用</div>
                          </b-form-radio>

                        </b-form-radio-group>

                        <b-form-radio-group v-else v-model="row.item.permissions">
                          <b-form-radio class="col-12" value="1">
                            <div>新增</div>
                          </b-form-radio>

                          <b-form-radio class="col-12" value="2">
                            <div>帳號停用</div>
                          </b-form-radio>

                          <b-form-radio class="col-12" value="3">
                            <b-input-group>
                              <div>異動 :　</div>
                              <b-form-input maxlength="200"/>
                            </b-input-group>
                          </b-form-radio>
                        </b-form-radio-group>
                      </template>

                      <!--管理單位-->
                      <template #cell(managementUnit)="row">
                        <b-form-select v-model="row.item.managementUnit" :options="options.managementUnit">
                          <template #first>
                            <b-form-select-option value="null" disabled>請選擇</b-form-select-option>
                            <b-form-select-option value="">全部</b-form-select-option>
                          </template>
                        </b-form-select>
                      </template>

                      <!--TODO: 未綁 v-model-->
                      <!--處理情形及生效日期-->
                      <template #cell(lastWork)="row">
                        <b-form-input maxlength="200"/>
                        <i-date-picker
                          placeholder="yyy/MM/dd"
                          v-model="row.item.lastWork"
                          lazy
                          trim
                        ></i-date-picker>
                      </template>

                      <!--TODO: 未綁 v-model-->
                      <!--處理人員-->
                      <template #cell(reviewStaffName)="row">
                        <b-form-input maxlength="200"/>
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
                      <b-button class="mr-1" style="background-color: #17a2b8; color: white" size="sm"
                                variant="outline-secondary"
                                @click="submitForm('0')">暫存
                      </b-button>
                      <b-button class="mr-1" style="background-color: #17a2b8; color: white" size="sm"
                                variant="outline-secondary"
                                @click="submitForm('1')">申請
                      </b-button>
                    </b-row>
                  </b-container>

                </div>

              </b-tab>
              <b-tab title="附件" :active="activeTab(1)" @click="changeTabIndex(1)">

                <appendix>

                </appendix>

              </b-tab>
              <b-tab title="流程圖" :active="activeTab(2)" @click="changeTabIndex(3)">
                <flowChart :filePathName="fileData">

                </flowChart>
              </b-tab>
            </b-tabs>
          </b-card-body>

        </div>
      </section>
    </b-container>
  </div>
</template>

<script lang="ts">


import IDualDatePicker from '@/shared/i-date-picker/i-dual-date-picker.vue';
import {reactive, ref, Ref, toRef, watch} from '@vue/composition-api';
import {useValidation, validateState} from '@/shared/form';
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';
import {required} from '@/shared/validators';
import IDatePicker from '@/shared/i-date-picker/i-date-picker.vue';
import {useBvModal} from '@/shared/modal';
import {systemToName} from "@/shared/i-system/system-to-name"

const appendix = () => import('@/components/appendix.vue');
const flowChart = () => import('@/components/flowChart.vue');
export default {
  name: "l410Apply",
  components: {
    'i-form-group-check': IFormGroupCheck,
    'i-dual-date-picker': IDualDatePicker,
    'i-date-picker': IDatePicker,
    appendix,
    flowChart,
  },
  setup() {

    const $bvModal = useBvModal();
    const formDefault = {
      formId: '',//表單編號
      applyDate: new Date(new Date().getFullYear(), 0, 1),//	申請日期
      filEmpid: '',//	填表人員工編號
      filName: '',//	填表人姓名
      filUnit: '',//	填表人單位名稱
      isSubmit: '',//	是否暫存、送出
      chName: '', // 中文姓名
      enName: '', // 英文姓名
      appEmpid: '',//	申請人員工編號
      extendNumber: '',//	分機
      appUnit: '',//	單位別
      subject: '',//	科別
      jobName: '',//	職稱
      applicationReason: '',//	申請事由 1.新進 2.離職 3.職務異動
      effective: '',//	生效日期
      effectiveDate: '',//	生效日期
      other: '',//	其他
      otherRemark: '',//	其他說明
      formName: 'L410',
    };
    const form = reactive(Object.assign({}, formDefault));
    const rules = {
      formId: {},
      applyDate: {},
      filEmpid: {},
      filName: {},
      filUnit: {},
      appName: {},
      isSubmit: {},
      chName: {},
      enName: {},
      appEmpid: {},
      extendNumber: {},
      appUnit: {},
      subject: {},
      jobName: {},
      applicationReason: {},
      effective: {},
      effectiveDate: {},
      other: {},
      otherRemark: {},
    };
    const {$v, checkValidity, reset} = useValidation(rules, form, formDefault);

    const fileData = reactive({
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
          thStyle: 'width:25%',
        },
        {
          key: 'permissions',
          label: '處理權限',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-left align-middle',
          thStyle: 'width:30%',
        },
        {
          key: 'managementUnit',
          label: '管理單位',
          sortable: false,
          thClass: 'text-center',
          tdClass: 'text-center align-middle',
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

    const mockdata = [
      {
        systemApply: '0',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '1',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '2',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '3',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '4',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '5',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '6',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '7',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '8',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '9',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '10',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '11',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '12',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '13',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '14',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
      {
        systemApply: '15',
        checkbox: null,
        applyItem: '',
        permissions: '',
        managementUnit: '',
        lastWork: '',
        reviewStaffName: '',
      },
    ];

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
      type: [
        {value: '0', text: '主管'},
        {value: '1', text: '副主管'},
        {value: '3', text: '科長'},
        {value: '4', text: '承辦人'},
        {value: '5', text: '登記桌'},
        {value: '6', text: '總收文'},
        {value: '7', text: '總發文'},
        {value: '8', text: '繕打人員'},
        {value: '9', text: '檔案歸檔'},
      ],
      managementUnit: [
        {value: '0', text: '人事室'},
        {value: '1', text: '資訊推動小組'},
        {value: '3', text: '秘書處'},
      ],
    });

    const submitForm = (isSubmit) => {
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
      activeTab,
      table,
      mockdata,
      systemToName,
      fileData,
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


