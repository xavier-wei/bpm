<template>
  <div>
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
                <b-form-input v-model="$v.filEmpid.$model"
                              disabled/>
              </i-form-group-check>

              <i-form-group-check class="col-sm-3" label-cols="5" content-cols="7" :label="`姓名：`"
                                  :item="$v.filName">
                <!--填表人姓名 :　filName-->
                <b-form-input v-model="$v.filName.$model"
                              disabled/>
              </i-form-group-check>

              <i-form-group-check class="col-sm-4" label-cols="5" content-cols="7" :label="`單位：`"
                                  :item="$v.filUnit">
                <!--填表人單位名稱　: filUnit-->
                <b-form-select v-model="$v.filUnit.$model" :options="bpmDeptsOptions"
                               disabled>
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
                <b-form-input v-model="$v.appName.$model"
                              :disabled="form.isSubmit === '1'  || formStatusRef !== FormStatusEnum.MODIFY"/>
              </i-form-group-check>

              <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`單位別 ：`"
                                  :item="$v.appUnit">
                <!--單位別 : -->
                <b-form-select v-model="$v.appUnit.$model" :options="bpmDeptsOptions"
                               :disabled="form.isSubmit === '1'  || formStatusRef !== FormStatusEnum.MODIFY">
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
                <b-form-input v-model="$v.appEngName.$model"
                              :disabled="form.isSubmit === '1'   || formStatusRef !== FormStatusEnum.MODIFY"/>
              </i-form-group-check>


              <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7" :label="`職稱 ：`"
                                  :item="$v.position">
                <!--職稱 : -->
                <b-form-input v-model="$v.position.$model"
                              :disabled="form.isSubmit === '1'   || formStatusRef !== FormStatusEnum.MODIFY"/>
              </i-form-group-check>
            </b-form-row>

            <b-form-row>
              <i-form-group-check label-star class="col-sm-5" label-cols="5" content-cols="7"
                                  :label="'員工編號 ：'"
                                  :item="$v.appEmpid">
                <!--員工編號 : -->
                <b-form-input v-model="$v.appEmpid.$model"
                              :disabled="form.isSubmit === '1'   || formStatusRef !== FormStatusEnum.MODIFY"/>
              </i-form-group-check>

              <i-form-group-check class="col-sm-5" label-cols="5" content-cols="7" :label="'分機 ：'"
                                  :item="$v.extNum">
                <!--分機 : -->
                <b-form-input v-model="$v.extNum.$model"
                              :disabled="form.isSubmit === '1'   || formStatusRef !== FormStatusEnum.MODIFY"/>
              </i-form-group-check>

            </b-form-row>

            <b-form-row>
              <p class="test1">二、 <span class="text-danger">*</span>申請事由：</p>
            </b-form-row>

            <b-form-row>

              <b-input-group>
                <b-form-group
                  class="col-sm-6 mb-0"
                  label-cols-md="4"
                  content-cols-md="8"
                  label-align-md="right"
                >

                  <!--申請事由-->
                  <b-form-radio-group v-model="$v.appReason.$model"
                                      :disabled="form.isSubmit === '1'   || formStatusRef !== FormStatusEnum.MODIFY">
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

                </b-form-group>
              </b-input-group>

            </b-form-row>

            <b-form-row>

              <b-input-group>
                <b-form-group
                  class="col-sm-6 mb-0"
                  label-cols-md="4"
                  content-cols-md="8"
                  label-align-md="right"
                >

                  <!--生效日期checkbox : isEnableDate-->
                  <b-form-checkbox v-model="$v.isEnableDate.$model" value="1" unchecked-value="0"
                                   :disabled="form.isSubmit === '1'   || formStatusRef !== FormStatusEnum.MODIFY">
                    生效日期 :
                  </b-form-checkbox>
                  <!--生效日期 : enableDate-->
                  <i-date-picker
                    placeholder="yyy/MM/dd"
                    v-model="$v.enableDate.$model"
                    :disabled="form.isSubmit === '1' || $v.isEnableDate.$model !== '1'   ||  formStatusRef !== FormStatusEnum.MODIFY"
                    lazy
                    trim
                  ></i-date-picker>
                  <div class="text-danger text1"
                       v-if="$v.enableDate.$model === null && $v.isEnableDate.$model === '1'">請輸入值
                  </div>

                </b-form-group>
                <b-form-group
                  class="col-sm-6 mb-0"
                  label-cols-md="4"
                  content-cols-md="8"
                  label-align-md="right"
                >
                  <!--其他 checkbox : isOther-->
                  <b-form-checkbox v-model="$v.isOther.$model" value="1" unchecked-value="0"
                                   :disabled="form.isSubmit === '1'   || formStatusRef !== FormStatusEnum.MODIFY">
                    其他 :
                  </b-form-checkbox>
                  <!--其他說明 : otherReason-->
                  <b-form-input v-model="$v.otherReason.$model"
                                :disabled="form.isSubmit === '1'   || formStatusRef !== FormStatusEnum.MODIFY"/>
                  <div class="text-danger text1" v-if="$v.otherReason.$model === '' && $v.isOther.$model === '1'">
                    請輸入值
                  </div>
                </b-form-group>

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
                                   :disabled="form.isSubmit === '1' || formStatusRef !== FormStatusEnum.MODIFY"
                                   @change="resetCheckboxValue(row.item)"/>
                </template>

                <!--申請項目-->
                <template #cell(applyItem)="row">

                  <div v-if="row.item.applyVersion == '0'">
                    <div>系統名稱 :</div>
                    <b-form-input v-model="row.item.otherSys" maxlength="200"
                                  :disabled="form.isSubmit === '1' || formStatusRef !== FormStatusEnum.MODIFY"></b-form-input>
                    <div>帳號 :</div>
                    <b-form-input v-model="row.item.otherSysAccount" maxlength="200"
                                  :disabled="form.isSubmit === '1' || formStatusRef !== FormStatusEnum.MODIFY"></b-form-input>
                  </div>

                  <div v-else-if="row.item.applyVersion === '1'">
                    {{ row.item.systemApplyName }}
                  </div>

                  <div v-else-if="row.item.applyVersion === '2'">
                    {{ row.item.systemApplyName }}
                    <span v-if=" row.item.isColon === '1'"> : </span>

                    <b-form-input
                      v-if="row.item.systemApply === '1'
                      ||  row.item.systemApply === '6'||
                      row.item.systemApply === '8'||
                      row.item.systemApply === '9'||
                      row.item.systemApply === '10'"
                      maxlength="200" v-model="row.item.systemApplyInput"
                      :disabled="form.isSubmit === '1' || formStatusRef !== FormStatusEnum.MODIFY">
                    </b-form-input>
                    <b-form-input
                      v-else
                      maxlength="200" v-model="row.item.systemApplyInput"
                      :disabled="userData.deptId !== row.item.admUnit || taskDataRef.taskName !== row.item.systemApplyName || row.item.checkbox !== '1' || formStatusRef !== FormStatusEnum.VERIFY">
                    </b-form-input>

                    <span v-if="row.item.systemApply === '4'">@mail.pcc.gov.tw</span>
                  </div>

                  <div v-else-if="row.item.applyVersion === '3'">
                    {{ row.item.systemApplyName }}
                    <span v-if=" row.item.isColon === '1'"> : </span>
                    <b-form-checkbox-group v-model="$v.applyItem.$model" :options="options.type"
                                           :disabled="form.isSubmit === '1' || formStatusRef !== FormStatusEnum.MODIFY"/>
                  </div>

                </template>

                <!--處理權限-->
                <template #cell(permissions)="row">

                  <div v-if="row.item.permissionsVersion == '0'">
                    <b-form-radio-group v-model="row.item.sys" @change="resetValue(row.item)"
                                        :disabled="form.isSubmit === '1' || formStatusRef !== FormStatusEnum.MODIFY">
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
                                        :disabled="form.isSubmit === '1' || row.item.sys !== '4' || formStatusRef !== FormStatusEnum.MODIFY"/>
                        </b-input-group>
                      </b-form-radio>
                    </b-form-radio-group>
                  </div>

                  <div v-if="row.item.permissionsVersion == '1'">
                    <b-form-radio-group v-model="row.item.sys" @change="resetValue(row.item)"
                                        :disabled="form.isSubmit === '1' || formStatusRef !== FormStatusEnum.MODIFY">
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
                                        :disabled="form.isSubmit === '1' || row.item.sys !== '3'|| formStatusRef !== FormStatusEnum.MODIFY"/>
                        </b-input-group>
                      </b-form-radio>
                    </b-form-radio-group>
                  </div>

                  <div v-if="row.item.permissionsVersion == '2'">
                    <b-form-radio-group v-model="row.item.sys" @change="resetValue(row.item)"
                                        :disabled="form.isSubmit === '1' || formStatusRef !== FormStatusEnum.MODIFY">
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
                                        :disabled="form.isSubmit === '1' || row.item.sys !== '5' || formStatusRef !== FormStatusEnum.MODIFY"/>
                        </b-input-group>
                      </b-form-radio>
                    </b-form-radio-group>
                  </div>

                  <div v-if="row.item.permissionsVersion == '3'">
                    <b-form-radio-group v-model="row.item.sys" @change="resetValue(row.item)"
                                        :disabled="form.isSubmit === '1' || formStatusRef !== FormStatusEnum.MODIFY">
                      <b-form-radio class="col-12" value="1">
                        <div>新增</div>
                        <b-input-group>
                          <div>1.　</div>
                          <b-form-input maxlength="200" v-model="row.item.emailApply1"
                                        :disabled="form.isSubmit === '1' || row.item.sys !== '1' || formStatusRef !== FormStatusEnum.MODIFY"/>
                        </b-input-group>
                        <b-input-group>
                          <div>2.　</div>
                          <b-form-input maxlength="200" v-model="row.item.emailApply2"
                                        :disabled="form.isSubmit === '1' || row.item.sys !== '1' || formStatusRef !== FormStatusEnum.MODIFY"/>
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
                                        :disabled="form.isSubmit === '1' || row.item.sys !== '3' || formStatusRef !== FormStatusEnum.MODIFY"/>
                        </b-input-group>
                      </b-form-radio>
                    </b-form-radio-group>
                  </div>

                  <div v-if="row.item.permissionsVersion == '4'">
                    <b-form-radio-group v-model="row.item.sys" @change="resetValue(row.item)"
                                        :disabled="form.isSubmit === '1' || formStatusRef !== FormStatusEnum.MODIFY">
                      <b-form-radio class="col-12" value="1">
                        <div>新增</div>
                      </b-form-radio>

                      <b-form-radio class="col-12" value="2">
                        <div>異動 :　</div>
                      </b-form-radio>

                      <div class="ml-1">
                        <b-form-checkbox value="1" unchecked-value="0" v-model="row.item.isUnitAdm"
                                         :disabled="form.isSubmit === '1' || row.item.sys !== '2' || formStatusRef !== FormStatusEnum.MODIFY">
                          部門管理者
                        </b-form-checkbox>

                        <b-form-checkbox value="1" unchecked-value="0" v-model="row.item.isUnitDataMgr"
                                         :disabled="form.isSubmit === '1' || row.item.sys !== '2' || formStatusRef !== FormStatusEnum.MODIFY">
                          部門資料維護者
                        </b-form-checkbox>

                        <b-form-checkbox value="1" unchecked-value="0" v-model="row.item.isWebSiteOther"
                                         :disabled="form.isSubmit === '1' || row.item.sys !== '2' || formStatusRef !== FormStatusEnum.MODIFY">
                          其他 :　
                        </b-form-checkbox>
                        <b-form-input maxlength="200" v-model="row.item.otherRemark"
                                      :disabled="form.isSubmit === '1' || row.item.isWebSiteOther !== '1' || formStatusRef !== FormStatusEnum.MODIFY"/>
                      </div>

                      <b-form-radio class="col-12" value="3">
                        <div>帳號停用</div>
                      </b-form-radio>
                    </b-form-radio-group>
                  </div>
                </template>

                <!--管理單位-->
                <template #cell(managementUnit)="row">
                  <div v-if="!!row.item.admUnit" v-model="row.item.admUnit">
                    {{ changeDealWithUnit(row.item.admUnit, bpmDeptsOptions) }}
                  </div>
                </template>

                <!--處理情形及生效日期-->
                <template #cell(lastWork)="row">
                  <b-form-input maxlength="200" v-model="row.item.admStatus"
                                :disabled="userData.deptId !== row.item.admUnit || taskDataRef.taskName.replace('加簽-', '') !== row.item.systemApplyName ||
                                row.item.checkbox !== '1'  || formStatusRef !== FormStatusEnum.VERIFY"/>
                  <div class="text-danger text1 mx-1"
                       v-if="userData.deptId === row.item.admUnit &&
                       taskDataRef.taskName.replace('加簽-', '') === row.item.systemApplyName &&
                       row.item.checkbox === '1'  &&
                       formStatusRef !== FormStatusEnum.READONLY &&
                       row.item.admStatus === ''">
                    請輸入值
                  </div>
                  <i-date-picker
                    placeholder="yyy/MM/dd"
                    lazy
                    trim
                    v-model="row.item.admEnableDate"
                    :disabled="userData.deptId !== row.item.admUnit ||
                    taskDataRef.taskName.replace('加簽-', '') !== row.item.systemApplyName ||
                    row.item.checkbox !== '1'  ||
                    formStatusRef !== FormStatusEnum.VERIFY"
                  ></i-date-picker>
                  <div class="text-danger text1 mx-1"
                       v-if="userData.deptId === row.item.admUnit &&
                       taskDataRef.taskName.replace('加簽-', '') === row.item.systemApplyName &&
                       row.item.checkbox === '1'  &&
                       formStatusRef !== FormStatusEnum.READONLY &&
                       row.item.admEnableDate === null">
                    請輸入值
                  </div>
                </template>

                <!--處理人員-->
                <template #cell(reviewStaffName)="row">
                  <b-form-input maxlength="200" v-model="row.item.admName"
                                :disabled=" userData.deptId !== row.item.admUnit ||taskDataRef.taskName.replace('加簽-', '') !== row.item.systemApplyName ||
                                row.item.checkbox !== '1'  || formStatusRef !== FormStatusEnum.VERIFY"/>
                  <div class="text-danger text1 mx-1"
                       v-if="userData.deptId === row.item.admUnit &&
                       taskDataRef.taskName.replace('加簽-', '') === row.item.systemApplyName &&
                       row.item.checkbox === '1'  &&
                       formStatusRef !== FormStatusEnum.READONLY &&
                       row.item.admName === ''">
                    請輸入值
                  </div>
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

            <!--簽核流程資訊模組-->
            <signerList :formId="formIdProp" :formStatus="formStatusRef" :opinion="opinion"
                        :processInstanceStatus="processInstanceStatusRef"></signerList>


            <b-container class="mt-3">
              <b-row class="justify-content-center">
                <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                          @click="submitForm('0')"
                          v-show="formStatusRef === FormStatusEnum.MODIFY">暫存
                </b-button>
                <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                          @click="submitForm('1')"
                          v-show="formStatusRef === FormStatusEnum.MODIFY">送出
                </b-button>

                <div v-if="userData.isSupervisor && formStatusRef === FormStatusEnum.VERIFY">
                  <!--除了申請者的上級會有同意跟不同意，其餘只會有送出按鈕-->
                  <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                            @click="reviewStart('同意',true)">同意
                  </b-button>
                  <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                            @click="reviewStart('不同意',true)">不同意
                  </b-button>
                </div>
                <div v-else-if="formStatusRef === FormStatusEnum.VERIFY">
                  <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                            @click="reviewStart('審核',true)">送出
                  </b-button>
                </div>

                <b-button class="ml-2" style="background-color: #17a2b8" @click="showModel()"
                          v-show="formStatusRef === FormStatusEnum.VERIFY && isSignatureRef">加簽
                </b-button>
                <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                          @click="reviewStart('補件',true)"
                          v-show="configTitleName(userData.titleName) && formStatusRef === FormStatusEnum.VERIFY">補件
                </b-button>
                <b-button class="ml-2" style="background-color: #17a2b8; color: white"
                          variant="outline-secondary"
                          v-show="isCancelRef && userData.userId === form.appEmpid && (form.processInstanceStatus === '0' || form.processInstanceStatus === '2') "
                          @click="toCancel">撤銷
                </b-button>
                <b-button class="ml-2" style="background-color: #17a2b8; color: white"
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

    <signatureBmodel ref="signatureBmodel" :formData="form" :taskData="taskDataRef"></signatureBmodel>
  </div>
</template>

<script lang="ts">


import IDualDatePicker from '@/shared/i-date-picker/i-dual-date-picker.vue';
import {reactive, ref, toRef, watch} from '@vue/composition-api';
import {useValidation, validateState} from '@/shared/form';
import IFormGroupCheck from '@/shared/form/i-form-group-check.vue';
import {required} from '@/shared/validators';
import IDatePicker from '@/shared/i-date-picker/i-date-picker.vue';
import {useBvModal} from '@/shared/modal';
import {handleBack, navigateByNameAndParams} from "@/router/router";
import axios from "axios";
import {notificationErrorHandler} from "@/shared/http/http-response-helper";
import {useNotification} from "@/shared/notification";
import {useGetters} from "@u3u/vue-hooks";
import {changeDirections} from "@/shared/word/directions";
import {checkboxToMapAndForm} from "@/shared/word/checkboxToMapAndForm";
import {formToCheckbox} from "@/shared/word/formToCheckbox";
import signatureBmodel from "@/components/signatureBmodel.vue";
import signerList from "@/components/signerList.vue";
import {changeDealWithUnit} from "@/shared/word/directions";
import {confirmAllData} from "@/shared/word/confirm-iTable";
import {configTitleName} from '@/shared/word/configRole';

const appendix = () => import('@/components/appendix.vue');
const flowChart = () => import('@/components/flowChart.vue');

export default {
  name: "l410Edit",
  props: {
    formId: {
      type: String,
      required: false,
    },
    formStatus: {
      type: String,
      required: false,
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
  },
  setup(props) {
    //照片預設物件
    let appendixData = reactive({});

    //進入表單的模式
    const formStatusRef = toRef(props, 'formStatus');

    //L410的form表單
    const l410Data = ref({});

    //接收父層傳過來的formId
    const formIdProp = toRef(props, 'formId');

    //傳進來的taskData{processInstanceId、taskId、taskName、decisionRole、additional}
    const taskDataRef = toRef(props, 'taskData');

    //顯示加簽按鈕判斷
    const isSignatureRef = toRef(props, 'isSignature');

    //傳進來的表單處理狀態 0處理中 1已完成 2註銷
    const processInstanceStatusRef = toRef(props, 'processInstanceStatus');

    //單位下拉選單資訊
    const bpmDeptsOptions = ref(useGetters(['getBpmDeptsOptions']).getBpmDeptsOptions).value;

    //登入者資訊
    const userData = ref(useGetters(['getUserData']).getUserData).value;

    //顯示撤銷按鈕判斷
    const isCancelRef = toRef(props, 'isCancel');

    //判斷頁面審核單位是否跟登入者單位一樣，一致就去後端更新資料
    let iptData = ref(false);
    const $bvModal = useBvModal();
    const notificationService = useNotification();

    //加簽的彈出視窗
    const signatureBmodel = ref(null);

    //接待處理來的狀態，一般查詢:1 下屬表單查詢:2 ，返回待處理時在帶這個參數過去，讓待處理的onActivated()知道要查哪個function
    const queryRef = toRef(props, 'query');

    //用formId查詢表單的附件
    let fileDataId = reactive({
      fileId: ''
    });
    //分頁預設值
    const tabIndex = ref(0);
    //流程圖預設物件
    const filePathData = reactive({
      filePathName: '',
    });
    //處理意見物件
    let opinion = reactive({
      opinionData: ''
    });

    enum FormStatusEnum {
      CREATE = '新增',
      MODIFY = '編輯',
      READONLY = '檢視',
      VERIFY = '簽核'
    }

    const headers = {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    };

    const formDefault = {
      formId: '',//表單編號
      applyDate: '',//	申請日期
      filEmpid: '',//	填表人員工編號
      filName: '',//	填表人姓名
      filUnit: '',//	填表人單位名稱
      isSubmit: '',//	是否暫存、送出
      appName: '', // 中文姓名
      appEngName: '', // 英文姓名
      appEmpid: '',//	申請人員工編號
      extNum: '',//	分機
      appUnit: '',//	單位別
      position: '',//	職稱
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
      processInstanceId: '', //流程實體編號
      processInstanceStatus: '', //流程實體狀態
      taskId: '',  //任務ID
      opinion: '',  //審核的處理意見
      applyItem: [],
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
      opinion: {},
    };

    const {$v, checkValidity, reset} = useValidation(rules, form, formDefault);

    const table = reactive({
      fields: [
        {
          key: 'checkbox',
          label: '',
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
    });

    //根據傳來的formId去後端查出哪個表單
    function handleQuery() {
      l410Data.value = {};
      axios
        .post(`/process/getIsms/L410/${formIdProp.value}`)
        .then(({data}) => {
          if (!data) return;
          l410Data.value = data
          templateQuery(l410Data.value);
        })
        .catch(notificationErrorHandler(notificationService));
    }

    //根據表單內容轉成畫面的checkbox
    function templateQuery(formData) {
      table.data = [];

      axios
        .get(`/eip/bpm-l410-apply-manages`)
        .then(({data}) => {
          data.forEach(i => {
            formToCheckbox(i, formData, taskDataRef.value.taskName.replace('加簽-', ''), userData.userName,formStatusRef.value,FormStatusEnum)
          })
          table.data = data
          if (formData.processInstanceId !== null && formData.processInstanceId !== undefined) {
            handleQueryFlowChart(formData.processInstanceId);
          }

          formData.applyDate = formData.applyDate != null ? new Date(formData.applyDate) : null
          formData.enableDate = formData.enableDate != null ? new Date(formData.enableDate) : null

          //用現在表單編號直接給file模組去自動取值
          fileDataId.fileId = formData.formId;
          Object.assign(formDefault, formData);
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

    //送出申請  狀態: 暫存0 申請1
    async function submitForm(isSubmit) {

      let l410Variables = [];

      form.isSubmit = isSubmit;
      const isValid = await checkValidity();

      if (isValid) {

        const isOK = await $bvModal.msgBoxConfirm('是否確認送出修改內容?');
        if (isOK) {

          //把頁面上iTable內的所有資料逐一轉成form裡的值，並把組出List<HashMap<String, HashMap<String, Object>>>傳給後端
          await Promise.all(table.data.map(data => checkboxToMapAndForm(data, form, l410Variables)));


          const formData = new FormData();

          form.l410Variables = l410Variables;

          let body = {
            "L410": JSON.stringify(form)
          }

          formData.append('form', new Blob([JSON.stringify(body)], {type: 'application/json'}));

          //判斷appendix頁面傳過來的file
          if (JSON.stringify(appendixData.value) !== '[]') {
            for (let i in appendixData.value) {
              formData.append('appendixFiles', appendixData.value[i].file[0]);
            }
            formData.append('fileDto', new Blob([JSON.stringify(appendixData.value)], {type: 'application/json'}));
          }

          axios
            .post(`/process/edit/L410`, formData, headers)
            .then(({data}) => {
              if (isSubmit === '1') {
                $bvModal.msgBoxOk('表單已送出');
                navigateByNameAndParams('pending', {isReload: false, isNotKeepAlive: true,query:queryRef.value});
                // reviewStart(isSubmit, false);
              } else {
                $bvModal.msgBoxOk('表單更新完畢');
                navigateByNameAndParams('pending', {isReload: false, isNotKeepAlive: true,query:queryRef.value});
              }
            })
            .catch(notificationErrorHandler(notificationService));
        }
      } else {
        await $bvModal.msgBoxOk('欄位尚未填寫完畢，請於輸入完畢後再行送出。');
      }
    }

    //根據畫面點選的按送出，同意 不同意 審核(除了申請者的上級會有同意跟不同意，其餘只會有送出按鈕) 補件
    async function reviewStart(item, i) {
      let variables = {};
      let l410Variables = [];

      let isOK = true;

      if (i === true) {
        isOK = await $bvModal.msgBoxConfirm('是否送出' + item + '?');
      }

      //判斷審核者需要填寫的欄位,需要回傳的array都是true 才能繼續往下
      let confirmOK = await Promise.all(table.data.map(data => confirmAllData(data, userData.deptId, taskDataRef.value.taskName.replace('加簽-', ''), notificationService)));

      const allTrue = confirmOK.every(item => item === true);

      if (isOK && allTrue) {

        //把頁面上iTable內的所有資料逐一轉成form裡的值，並把組出List<HashMap<String, HashMap<String, Object>>>傳給後端
        await Promise.all(table.data.map(data => checkboxToMapAndForm(data, form, l410Variables)));

        form.l410Variables = l410Variables;

        //判斷頁面審核單位是否跟登入者單位一樣，一致就去後端更新資料
        await Promise.all(table.data.map(data => {
          if (data.admUnit === userData.deptId) {
            return iptData.value = true;
          }
        }));

        //用後端傳來的taskDataRef組出送審需要的variables
        if (taskDataRef.value.decisionRole !== null) {
          let mapData = new Map<string, string>();
          mapData.set(taskDataRef.value.decisionRole, getItem(item))
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
          form: {"L410": JSON.stringify(form)},
          directions: changeDirections(taskDataRef.value.decisionRole),
          opinion: opinionData,
          ipt: iptData.value
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
              navigateByNameAndParams('pending', {isReload: false, isNotKeepAlive: true,query:queryRef.value});
            } else {
              $bvModal.msgBoxOk('表單審核完畢');
              navigateByNameAndParams('pending', {isReload: false, isNotKeepAlive: true,query:queryRef.value});
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

    //撤銷表單
    async function toCancel() {
      let isOK = await $bvModal.msgBoxConfirm('是否撤銷表單?');
      if (isOK) {
        let request = {
          processInstanceId: form.processInstanceId,
          key:'L410'
        }
        axios.post(`/process/deleteProcessInstance`, request)
          .then(({data}) => {
            $bvModal.msgBoxOk('表單流程撤銷完畢');
            handleBack({isReload: false, isNotKeepAlive: false,query:queryRef.value});
          })
          .catch(notificationErrorHandler(notificationService))
      }
    }

    //返回上一頁
    function toQueryView() {
      handleBack({isReload: true, isNotKeepAlive: true,query:queryRef.value});
    }

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
        default:
          return '';
      }
    };

    //切換處理權限欄位時清除已填資料
    function resetValue(data) {
      data.emailApply1 = null;
      data.emailApply2 = null;
      data.sysChange = null;
      data.isUnitAdm = null;
      data.isUnitDataMgr = null;
      data.isWebSiteOther = null;
      data.otherRemark = null;
    }

    //取消已選申請項目時，清空申請項目欄位跟處理權限欄位內的所有值
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
      }
    }

    //開啟加簽的彈出視窗
    function showModel() {
      signatureBmodel.value.isShowDia(true);
    }

    watch(formIdProp, () => {
        handleQuery();
      },
      {immediate: true}
    )

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
      filePathData,
      appendixData,
      formStatusRef,
      reset,
      toQueryView,
      bpmDeptsOptions,
      fileDataId,
      reviewStart,
      FormStatusEnum,
      isSignatureRef,
      resetValue,
      resetCheckboxValue,
      userData,
      signatureBmodel,
      showModel,
      formIdProp,
      opinion,
      l410Data,
      taskDataRef,
      changeDealWithUnit,
      processInstanceStatusRef,
      toCancel,
      isCancelRef,
      configTitleName,
    }
  }
}
</script>

<style>

.test1 {
  font-family: 'Arial Negreta', 'Arial';
  font-weight: 700;
  font-style: normal;
  font-size: 16px;
  margin-left: 20px;
}

.text1 {
  margin-top: 0.25rem;
  font-size: 80%;
}

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


</style>


