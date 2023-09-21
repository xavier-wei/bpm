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

<%--	  <tags:button id="btnUpdate">--%>
<%--    	修改<i class="fas fa-user-plus"></i>--%>
<%--      </tags:button>--%>

<%--	  <tags:button id="btnDelete">--%>
<%--    	刪除<i class="fas fa-user-plus"></i>--%>
<%--      </tags:button>--%>

</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip07w010Form" name="eip07w010Form" modelAttribute="${caseKey}" method="POST">
    	<tags:form-row>
			<label class="col-form-label text-left star">車牌號碼:</label>
		       	<div class="col-4">
					<table>
						<tr>
							<td><form:input id="carno1" name="carno1"  path="eip07w010CarDataList[0].carno1" cssClass="form-control" size="9" maxlength="8"/></td>
							<td>-</td>
							<td><form:input id="carno2" name="carno2"  path="eip07w010CarDataList[0].carno2" cssClass="form-control" size="7" maxlength="6"/></td>
						</tr>
					</table>
				</div>
			<label class="col-form-label text-left">車輛種類:</label>
				<div>
                    <form:select id="carType" path="eip07w010CarDataList[0].carType" cssClass="form-control">
                    	<form:option value="1">1:4人座</form:option>
                    	<form:option value="2">2:7人座</form:option>
                    </form:select>
				</div>
		</tags:form-row>

		<tags:form-row>

			<label class="col-form-label text-left star ">財產編號:</label>
		       	<div>
					<form:input id="owned"  name="owned"  path="eip07w010CarDataList[0].owned" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			<label class="col-form-label text-left star">購置年份:</label>
		       	<div>
					<form:input id="carYear"  name="carYear"  path="eip07w010CarDataList[0].carYear" cssClass="form-control" size="7" maxlength="6"/>
				</div>

		</tags:form-row>

		<tags:form-row>

			<label class="col-form-label text-left star">車輛廠牌:</label>
		       	<div>
					<form:input id="carSource"  name="carSource"  path="eip07w010CarDataList[0].carSource" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			<label class="col-form-label text-left">顏色:</label>
		       	<div>
					<form:input id="carColor"  name="carColor"  path="eip07w010CarDataList[0].carColor" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			<label class="col-form-label text-left">排放量cc:</label>
		       	<div>
					<form:input id="ccSize"  name="ccSize"  path="eip07w010CarDataList[0].ccSize" cssClass="form-control" size="7" maxlength="6"/>
				</div>
		</tags:form-row>

		<tags:form-row>
			<label class="col-form-label text-left">首長專用車:</label>
		       	<div>
					<form:input id="bossMk"  name="bossMk"  path="eip07w010CarDataList[0].bossMk" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			<label class="col-form-label text-left">首長:</label>
		       	<div>
					<form:input id="bossName"  name="bossName"  path="eip07w010CarDataList[0].bossName" cssClass="form-control" size="7" maxlength="6"/>
				</div>
		</tags:form-row>

		<tags:form-row>
				<label class="col-form-label text-left">車輛狀態:</label>
		       	<div>
					<form:input id="carStatus"  name="carStatus"  path="eip07w010CarDataList[0].carStatus" cssClass="form-control" size="7" maxlength="6"/>
				</div>
		</tags:form-row>

		<tags:form-row>
		       	<div>


				</div>
			<label class="col-form-label text-left">保險公司:</label>
		       	<div>
					<form:input id="insuranceCompany"  name="insuranceCompany"  path="eip07w010CarDataList[0].insuranceCompany" cssClass="form-control" size="7" maxlength="6"/>
				</div>

		</tags:form-row>

		<tags:form-row>
			<label class="col-form-label text-left">保險期間(起):</label>
		       	<div>
					<form:input id="insuranceStart"  name="insuranceStart"  path="eip07w010CarDataList[0].insuranceStart" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			<label class="col-form-label text-left">保險期間(迄):</label>
		       	<div>
					<form:input id="InsuranceEnd"  name="InsuranceEnd"  path="eip07w010CarDataList[0].InsuranceEnd" cssClass="form-control" size="7" maxlength="6"/>
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