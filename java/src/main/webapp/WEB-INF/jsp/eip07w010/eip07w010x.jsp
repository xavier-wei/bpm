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
			<label class="col-form-label text-left star">駕駛人姓名:</label>
		       	<div>
					<form:input id="name"  name="name"  path="name" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			<label class="col-form-label text-left">職稱:</label>
		       	<div>
					<form:input id="title"  name="title"  path="title" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			<label class="col-form-label text-left star">手機號碼:</label>
		       	<div>
					<form:input id="cellphone"  name="cellphone"  path="cellphone" cssClass="form-control" size="7" maxlength="6"/>
				</div>

		</tags:form-row>

		<tags:form-row>
			<label class="col-form-label text-left">連絡電話:</label>
		       	<div>
					<form:input id="phone"  name="phone"  path="phone" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			<label class="col-form-label text-left">員工編號:</label>
		       	<div>
					<form:input id="staffno"  name="staffno"  path="staffno" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			<label class="col-form-label text-left star">身分證號(駕照證號):</label>
		       	<div>
					<form:input id="id"  name="id"  path="id" cssClass="form-control" size="7" maxlength="6"/>
				</div>
		</tags:form-row>

		<tags:form-row>
			<label class="col-form-label text-left star">出生日期:</label>
		       	<div>
					<form:input id="brdte"  name="brdte"  path="brdte" cssClass="form-control" size="7" maxlength="6"/>
				</div>
		</tags:form-row>

		<tags:form-row>
			<label class="col-form-label text-left star">在職註記:</label>
		       	<div>
					<form:input id="stillWork"  name="stillWork"  path="stillWork" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			<label class="col-form-label text-left ">到職日期:</label>
		       	<div>
					<form:input id="startworkDate"  name="startworkDate"  path="startworkDate" cssClass="form-control" size="7" maxlength="6"/>
				</div>
				<label class="col-form-label text-left">離職日期:</label>
		       	<div>
					<form:input id="endworkDate"  name="endworkDate"  path="endworkDate" cssClass="form-control" size="7" maxlength="6"/>
				</div>
		</tags:form-row>

		<tags:form-row>
			<label class="col-form-label text-left">駕照到期日:</label>
		       	<div>
					<form:input id="licenceExpireDate"  name="licenceExpireDate"  path="licenceExpireDate" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			<label class="col-form-label text-left">駕照種類:</label>
		       	<div>
					<form:input id="licenceCarType"  name="licenceCarType"  path="licenceCarType" cssClass="form-control" size="7" maxlength="6"/>
				</div>
		</tags:form-row>

		<tags:form-row>
			<label class="col-form-label text-left star">預定保管車號:</label>
		       	<div class="col-4">
					<table>
						<tr>
							<td><form:input id="carno1" name="carno1"  path="carno1" cssClass="form-control" size="9" maxlength="8"/></td>
							<td>-</td>
							<td><form:input id="carno2" name="carno2"  path="carno2" cssClass="form-control" size="7" maxlength="6"/></td>
						</tr>
					</table>
				</div>
		</tags:form-row>

		<tags:form-row>

			<label class="col-form-label text-left">代理人:</label>
		       	<div>
					<form:input id="tempStaff"  name="tempStaff"  path="tempStaff" cssClass="form-control" size="7" maxlength="6"/>
				</div>
		</tags:form-row>



			<%--		<form:hidden id="processTy" path="processTy" />--%>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

        $(function() {

            $('#btnInster').click(function() {
           		$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_inster.action" />').submit();
            });

			$('#btnBack').click(function() {
				$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_enter.action" />').submit();
			});

         });


</script>
</jsp:attribute>
</tags:layout>