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

	  <tags:button id="btnUpdate">
    	修改<i class="fas fa-user-plus"></i>
      </tags:button>

	  <tags:button id="btnDelete">
    	刪除<i class="fas fa-user-plus"></i>
      </tags:button>

		<tags:button id="btnBack">
    	回主畫面<i class="fas fa-user-plus"></i>
      </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
		<tags:fieldset  legend="駕駛人資料" >
		<form:form id="eip07w010Form" name="eip07w010Form" modelAttribute="${caseKey}" method="POST">
    	<tags:form-row>
				<div class="col-md-4 d-flex ">
					<form:label cssClass="col-form-label star" path="name">駕駛人姓名：</form:label>
					<form:input id="name"  name="name"  path="eip07w010QueryDataList[0].name" cssClass="form-control" size="10" maxlength="10"/>
				</div>
			   <div class="col-md-4 d-flex ">
				   <form:label cssClass="col-form-label" path="name">職稱：</form:label>
					<form:select id="title"  name="title"  path="eip07w010QueryDataList[0].title" cssClass="form-control">
						<c:forEach var="item" items="${caseData.titleList}" varStatus="status">
							<form:option value="${item.codeno}"><c:out value="${item.codeno}"/>-<c:out value="${item.codename}"/></form:option>
						</c:forEach>
					</form:select>
			   </div>
		</tags:form-row>

		<tags:form-row>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label" path="staffno">員工編號：</form:label>
					<form:input id="staffno"  name="staffno"  path="eip07w010QueryDataList[0].staffno" cssClass="form-control" size="10" maxlength="10"/>
				</div>
				<div class="col-md-4 d-flex ">
					<form:label cssClass="col-form-label star" path="id">身分證號：</form:label>
					<form:input id="id"  name="id"  path="eip07w010QueryDataList[0].id" cssClass="form-control idn" size="10" maxlength="10"/>
				</div>
		</tags:form-row>

		<tags:form-row>
			<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label star" path="cellphone">手機號碼：</form:label>
					<form:input id="cellphone"  name="cellphone"  path="eip07w010QueryDataList[0].cellphone" cssClass="form-control num_only" size="10" maxlength="10" />
			</div>
			<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label" path="phone">連絡電話：</form:label>
					<form:input id="phone"  name="phone"  path="eip07w010QueryDataList[0].phone" cssClass="form-control num_only" size="10" maxlength="10" />
			</div>
		</tags:form-row>
		<tags:form-row>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label" path="brdte">出生日期：</form:label>
					<form:input id="brdte"  name="brdte"  path="eip07w010QueryDataList[0].brdte" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
				</div>
		</tags:form-row>
		<tags:form-row>
			<div class="col-md d-flex">
					<form:label cssClass="col-form-label star" path="stillWork">在職註記：</form:label>
					<form:select id="stillWork" path="eip07w010QueryDataList[0].stillWork" cssClass="form-control">
                    	<form:option value="Y">是</form:option>
                    	<form:option value="N">否</form:option>
                    </form:select>
			</div>
			<div class="col-md d-flex">
					<form:label cssClass="col-form-label star" path="startworkDate">到職日期：</form:label>
					<form:input id="startworkDate"  name="startworkDate"  path="eip07w010QueryDataList[0].startworkDate" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
			</div>
			<div class="col-md d-flex">
				<form:label cssClass="col-form-label star" id="endworkDate" path="endworkDate">離職日期：</form:label>
				<form:input id="endworkDate"  name="endworkDate"  path="eip07w010QueryDataList[0].endworkDate" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
			</div>
		</tags:form-row>
		<tags:form-row>
			<div class="col-md-4 d-flex">
				<form:label cssClass="col-form-label" path="licenceExpireDate">駕照到期日：</form:label>
				<form:input id="licenceExpireDate"  name="licenceExpireDate"  path="eip07w010QueryDataList[0].licenceExpireDate" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
			</div>
			<div class="col-md-4 d-flex">
				<form:label cssClass="col-form-label" path="licenceCarType">駕照種類：</form:label>
				<form:select id="licenceCarType" path="eip07w010QueryDataList[0].licenceCarType" cssClass="form-control">
                    	<form:option value="1">1.小客車</form:option>
                    	<form:option value="2">2.大客車</form:option>
						<form:option value="3">3.其它</form:option>
                    </form:select>
			</div>
		</tags:form-row>
		<tags:form-row>
				<div class="col-md d-flex">
					<form:label cssClass="col-form-label star" path="carno">預定保管車號：</form:label>
					<form:select id="carno"  name="carno"  path="eip07w010QueryDataList[0].carno" cssClass="form-control">
							<form:option value="">請選擇</form:option>
							<c:forEach var="item" items="${caseData.carnoList}" varStatus="status">
								<form:option value="${item.carno}"><c:out value="${item.carno}"/></form:option>
							</c:forEach>
						</form:select>
                </div>
		</tags:form-row>
		<tags:form-row>
				<div class="col-md d-flex">
				  <form:label cssClass="col-form-label" path="tempStaff">代理人：</form:label>
				  <form:select id="tempStaff"  name="tempStaff"  path="eip07w010QueryDataList[0].tempStaff" cssClass="form-control">
						<form:option value="">請選擇</form:option>
						<c:forEach var="item" items="${caseData.tempStaffList}" varStatus="status">
							<form:option value="${item.name}"><c:out value="${item.name}"/></form:option>
						</c:forEach>
					</form:select>
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
			controlChangMk();
			$('#btnInster').click(function() {
           		$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_inster.action" />').submit();
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

			$('#stillWork').change(function() {//控制異動原因下拉式選單
				controlChangMk();
			});

			function controlChangMk() {
				var stillWork =$("#stillWork option:selected").val();
				if (stillWork=='Y'){
					$("#endworkDate").removeClass("star");
				}else {
					$("#endworkDate").addClass("star");
				}
			}


         });


</script>
</jsp:attribute>
</tags:layout>