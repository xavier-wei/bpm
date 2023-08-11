<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w010Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	  <tags:button id="btnInster">
    	新增<i class="fas fa-user-plus"></i>
      </tags:button>

	  <tags:button id="btnBack">
    	回主畫面<i class="fas fa-user-plus"></i>
      </tags:button>


</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip07w010Form" name="eip07w010Form" modelAttribute="${caseKey}" method="POST">
    	<tags:form-row>
			<div  class="col-md-4 d-flex">
					<form:label cssClass="col-form-label star" path="carno1">車牌號碼：</form:label>
					<table>
						<tr>
							<td><form:input id="carno1" name="carno1"  path="eip07w010CarDataList[0].carno1" cssClass="form-control num_eng_only upCase" size="4" maxlength="3"  /></td>
							<td>-</td>
							<td><form:input id="carno2" name="carno2"  path="eip07w010CarDataList[0].carno2" cssClass="form-control num_eng_only upCase" size="7" maxlength="4" /></td>
						</tr>
					</table>
				</div>
			<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label star" path="carType">車輛種類：</form:label>
					<form:select id="carType" path="eip07w010CarDataList[0].carType" cssClass="form-control">
                    	<form:option value="1">1:4人座</form:option>
                    	<form:option value="2">2:7人座</form:option>
						<form:option value="3">3:其它</form:option>
                    </form:select>
				</div>
		</tags:form-row>

		<tags:form-row>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label star" path="owned">財產編號：</form:label>
					<form:input id="owned"  name="owned"  path="eip07w010CarDataList[0].owned" cssClass="form-control" size="24" maxlength="12"/>
				</div>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label" path="carYear">購置年份：</form:label>
					<form:input id="carYear"  name="carYear"  path="eip07w010CarDataList[0].carYear" cssClass="form-control num_only padL" size="3" maxlength="3" />
				</div>
		</tags:form-row>

		<tags:form-row>
				<div class="col-md d-flex">
					<form:label cssClass="col-form-label" path="carSource">車輛廠牌：</form:label>
					<form:input id="carSource"  name="carSource"  path="eip07w010CarDataList[0].carSource" cssClass="form-control" size="7" maxlength="6"/>
				</div>
				<div class="col-md d-flex">
					<form:label cssClass="col-form-label" path="carColor">顏色：</form:label>
					<form:input id="carColor"  name="carColor"  path="eip07w010CarDataList[0].carColor" cssClass="form-control" size="7" maxlength="6"/>
				</div>
				<div class="col-md d-flex">
					<form:label cssClass="col-form-label" path="ccSize">排放量cc：</form:label>
					<form:input id="ccSize"  name="ccSize"  path="eip07w010CarDataList[0].ccSize" cssClass="form-control num_only" size="7" maxlength="6"/>
				</div>
		</tags:form-row>

		<tags:form-row>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label star" path="bossMk">首長專用車：</form:label>
					<form:select id="bossMk" path="eip07w010CarDataList[0].bossMk" cssClass="form-control">
                    	<form:option value="Y">是</form:option>
                    	<form:option value="N">否</form:option>
                    </form:select>
				</div>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label" path="bossName">首長：</form:label>
					<form:input id="bossName"  name="bossName"  path="eip07w010CarDataList[0].bossName" cssClass="form-control" size="10" maxlength="5"/>
				</div>
		</tags:form-row>

		<tags:form-row>
				<div class="col-md d-flex">
					<form:label cssClass="col-form-label" path="carStatus">車輛狀態：</form:label>
					<form:select id="carStatus" path="eip07w010CarDataList[0].carStatus" cssClass="form-control">
                    	<form:option value="1">1.可使用</form:option>
                    	<form:option value="2">2.不可使用-首長</form:option>
						<form:option value="3">3.維修中</form:option>
                    	<form:option value="4">4.報廢</form:option>
                    </form:select>
				</div>
		</tags:form-row>

		<tags:form-row>
			<br>
			<div class="col-md d-flex">
				<form:label cssClass="col-form-label" path="insuranceCompany">[保險資料]：</form:label>
			</div>
			<br>
			<br>
		</tags:form-row>

		<tags:form-row>
				<div class="col-md d-flex">
					<form:label cssClass="col-form-label" path="insuranceCompany">保險公司：</form:label>
					<form:input id="insuranceCompany"  name="insuranceCompany"  path="eip07w010CarDataList[0].insuranceCompany" cssClass="form-control" size="40" maxlength="100"/>
				</div>
		</tags:form-row>



		<tags:form-row>
			<div class="col-md-4 d-flex">
				<form:label cssClass="col-form-label" path="insuranceStart">保險期間(起)：</form:label>
				<form:input id="insuranceStart"  name="insuranceStart"  path="eip07w010CarDataList[0].insuranceStart" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
			</div>
			<div class="col-md-4 d-flex">
				<form:label cssClass="col-form-label" path="InsuranceEnd">保險期間(迄)：</form:label>
				<form:input id="InsuranceEnd"  name="InsuranceEnd"  path="eip07w010CarDataList[0].InsuranceEnd" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
			</div>
		</tags:form-row>
					<form:hidden id="workTy" path="workTy" />
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

        $(function() {
			controlBtn();
            $('#btnInster').click(function() {
           		$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_carInster.action" />').submit();
            });

			$('#btnBack').click(function() {
				$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_enter.action" />').submit();
			});

			$('#btnDelete').click(function() {
				showConfirm('確定要刪除資料？', () => {
					$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_delete.action" />').submit();
				});
			});

			$('#btnUpdate').click(function() {
				showConfirm('確定要修改資料？', () => {
					$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_update.action" />').submit();
				});
			});



			function controlBtn(){
				var workTy=$("#workTy").val();
				if(workTy == 'Q'){
					$("#btnUpdate").show();
					$("#btnDelete").show();
					$("#btnInster").hide();
				} else {
					$("#btnUpdate").hide();
					$("#btnDelete").hide();
				}
			}

         });


</script>
</jsp:attribute>
</tags:layout>