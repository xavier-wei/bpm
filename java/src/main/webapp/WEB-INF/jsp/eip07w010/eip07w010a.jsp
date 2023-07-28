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
    <tags:fieldset>
		<form:form id="eip07w010Form" name="eip07w010Form" modelAttribute="${caseKey}" method="POST">
    	<tags:form-row>
			<div style="width: 35%" class="d-flex" >
				<label class="col-form-label text-left star">駕駛人姓名:</label>
				<div>
					<form:input id="name"  name="name"  path="eip07w010QueryDataList[0].name" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			</div>
			<label class="col-form-label text-left">職稱:</label>
				   <div>
	                    <form:select id="title"  name="title"  path="eip07w010QueryDataList[0].title" cssClass="form-control">
	                    	<form:option value="29">29-駕駛</form:option>
	                        <c:forEach var="item" items="${caseData.titleList}" varStatus="status">
	                            <form:option value="${item.codeno}"><c:out value="${item.codeno}"/>-<c:out value="${item.codename}"/></form:option>
	                        </c:forEach>
	                    </form:select>
				   </div>
		</tags:form-row>

		<tags:form-row>
			<div style="width: 35%" class="d-flex" >
				<label class="col-form-label text-left">員工編號:</label>
				<div>
					<form:input id="staffno"  name="staffno"  path="eip07w010QueryDataList[0].staffno" cssClass="form-control" size="7" maxlength="6"/>
				</div>
			</div>
			<label class="col-form-label text-left star">身分證號(駕照證號):</label>
		       	<div>
					<form:input id="id"  name="id"  path="eip07w010QueryDataList[0].id" cssClass="form-control idn" size="10" maxlength="10"/>
				</div>

		</tags:form-row>

		<tags:form-row>
			<div style="width: 35%" class="d-flex" >
				<label class="col-form-label text-left star">手機號碼:</label>
				<div>
					<form:input id="cellphone"  name="cellphone"  path="eip07w010QueryDataList[0].cellphone" cssClass="form-control num_only" size="10" maxlength="10" />
				</div>
			</div>
			<label class="col-form-label text-left">連絡電話:</label>
		       	<div>
					<form:input id="phone"  name="phone"  path="eip07w010QueryDataList[0].phone" cssClass="form-control num_only" size="10" maxlength="10" />
				</div>
		</tags:form-row>

		<tags:form-row>
			<label class="col-form-label text-left ">出生日期:</label>
		       	<div>
					<form:input id="brdte"  name="brdte"  path="eip07w010QueryDataList[0].brdte" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
				</div>
		</tags:form-row>

		<tags:form-row>
			<div style="width: 35%" class="d-flex" >
				<label class="col-form-label text-left star">在職註記:</label>
				<div>
                    <form:select id="stillWork" path="eip07w010QueryDataList[0].stillWork" cssClass="form-control">
                    	<form:option value="Y">是</form:option>
                    	<form:option value="N">否</form:option>
						<form:option value="A">全部</form:option>
                    </form:select>
				</div>
			</div>
			<label class="col-form-label text-left ">到職日期:</label>
		       	<div>
					<form:input id="startworkDate"  name="startworkDate"  path="eip07w010QueryDataList[0].startworkDate" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
				</div>
			&emsp;&emsp;&emsp;&emsp;
				<label class="col-form-label text-left">離職日期:</label>
		       	<div>
					<form:input id="endworkDate"  name="endworkDate"  path="eip07w010QueryDataList[0].endworkDate" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
				</div>
		</tags:form-row>

		<tags:form-row>
			<div style="width: 35%" class="d-flex" >
				<label class="col-form-label text-left">駕照到期日:</label>
				<div>
					<form:input id="licenceExpireDate"  name="licenceExpireDate"  path="eip07w010QueryDataList[0].licenceExpireDate" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
				</div>
			</div>
			<label class="col-form-label text-left">駕照種類:</label>
				<div>
                    <form:select id="licenceCarType" path="eip07w010QueryDataList[0].licenceCarType" cssClass="form-control">
                    	<form:option value="1">1.小客車</form:option>
                    	<form:option value="2">2.大客車</form:option>
						<form:option value="3">3.其它</form:option>
                    </form:select>
				</div>
		</tags:form-row>

		<tags:form-row>
			<label class="col-form-label text-left star">預定保管車號:</label>
		       	<div class="col-4">
					<table>
						<tr>
							<td><form:input id="carno1" name="carno1"  path="eip07w010QueryDataList[0].carno1" cssClass="form-control num_eng_only" size="3" maxlength="3" /></td>
							<td>-</td>
							<td><form:input id="carno2" name="carno2"  path="eip07w010QueryDataList[0].carno2" cssClass="form-control num_eng_only" size="4" maxlength="4" /></td>
						</tr>
					</table>
				</div>
		</tags:form-row>

		<tags:form-row>

			<label class="col-form-label text-left">代理人:</label>
				  <div>
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

         });


</script>
</jsp:attribute>
</tags:layout>