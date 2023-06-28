<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w060Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	<tags:button id="btUpdate">
    	修改<i class="fas fa-user-plus"></i>
    </tags:button>

	<tags:button id="btDelete">
    	刪除<i class="fas fa-user-plus"></i>
    </tags:button>

	<tags:button id="btPrint">
    	列印<i class="fas fa-user-plus"></i>
    </tags:button>

	<tags:button id="btBack">
    	回主畫面<i class="fas fa-user-plus"></i>
    </tags:button>
</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="Eip08w060xForm" name="Eip08w060xForm" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
				<label class="col-form-label text-left col-3">選項:<c:out value="${caseData.applyTpNm}"/></label>
			</tags:form-row>
			<tags:form-row>
				<label class="col-form-label text-left col-3">申請人:<c:out value="${caseData.user}"/></label>
				<label class="col-form-label text-left col-3">申請日期:<c:out value="${caseData.applyDate}"/></label>
				<label class="col-form-label text-left col-3">暫存:<c:out value="${caseData.save}"/></label>
            </tags:form-row>

			<tags:form-row>
             <label class="col-form-label text-left col-3">請購/請修單號:</label>
            </tags:form-row>
     <div class="table-responsive" id="div1">
		 <table class="table" id="tb1">
			 <thead data-orderable="true">
			 <tr>
				 <th class="text-center align-middle">序號</th>
				 <th class="text-center align-middle">品名及規格</th>
				 <th class="text-center align-middle">用途說明</th>
				 <th class="text-center align-middle">數量</th>
				 <th class="text-center align-middle">單位</th>
			 </tr>
			 </thead>
			 <tbody>
			 <c:forEach items="${caseData.eip08w060CaseList}" var="caseData" varStatus="status">
				 <tr>
				<td class="text-center align-middle"><c:out value="${status.index+1}" /></td>
				 <td cssClass="text-center align-middle"><form:input  path="eip08w060CaseList[${status.index}].item"/></td>
				 <td cssClass="text-center align-middle"><form:input  path="eip08w060CaseList[${status.index}].desc_memo"/></td>
				 <td cssClass="text-center align-middle"><form:input  path="eip08w060CaseList[${status.index}].cnt"/></td>
				 <td cssClass="text-center align-middle"><form:input  path="eip08w060CaseList[${status.index}].unit"/></td>
				 </tr>
			 </c:forEach>
			 </tbody>
		 </table>
	 </div>
			<form:hidden id="itemId" path="itemId" />
			<form:hidden id="save" path="save" />
            <form:hidden id="processTy" path="processTy" />
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
	controlBt();
        $(function() {

			var config = getDataTablesConfig();
            var table = $('#listTable').DataTable(config);
			var reportType = $("input[id=reportType]:checked").val();

            $('#btnInsert').click(function() {
                $('input[id="processTy"]').val("A");
           		$('#Eip08w060xForm').attr('action', '<c:url value="/Eip08w060_inster.action" />').submit();
            });

			$('#btnPrint').click(function() {
				$('#Eip08w060xForm').attr('action', '<c:url value="/Eip08w060_enter.action" />').submit();
			});

			$('#btBack').click(function() {
				$('#Eip08w060xForm').attr('action', '<c:url value="/Eip08w060_enter.action" />').submit();
			});

			$('#btUpdate').click(function() {
				$('#Eip08w060xForm').attr('action', '<c:url value="/Eip08w060_update.action" />').submit();
			});

			$('#btDelete').click(function() {
				showConfirm('確定要刪除資料？', () => {
					$('#Eip08w060xForm').attr('action', '<c:url value="/Eip08w060_delete.action" />').submit();
				});
			});
         });
		function controlBt(){
			var save=$("#save").val();
			if(save == 'Y'){
				$("#btUpdate").show();
				$("#btDelete").show();
			} else {
				$("#btUpdate").hide();
				$("#btDelete").hide()
			}
		}
</script>
</jsp:attribute>
</tags:layout>