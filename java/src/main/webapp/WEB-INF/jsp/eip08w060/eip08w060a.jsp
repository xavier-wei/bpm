<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w060Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->

    <tags:button id="btnInsert">
    	 新增<i class="fas fa-user-plus"></i>
    </tags:button>

	<tags:button id="btBack">
    	回主畫面<i class="fas fa-user-plus"></i>
    </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset legend="物品請購/修繕請修單 申請作業">
		<form:form id="Eip08W060xForm" name="Eip08W060xForm" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
				<label class="col-form-label text-left col-3">選項:<c:out value="${fn:substring(caseData.applyTpNm, 2,-1)}"/></label>
			</tags:form-row>
			<tags:form-row>
				<label class="col-form-label text-left col-3">申請人:<c:out value="${caseData.user}"/></label>
				<label class="col-form-label text-left col-3">申請日期:<c:out value="${caseData.applyDate}"/></label>
				<label class="col-form-label text-left col-3">暫存:<c:out value="${caseData.save}"/></label>
            </tags:form-row>

     <div class="table-responsive" id="div1">
		 <table class="table" id="tb1">
			 <thead data-orderable="true">
			 <tr>
				 <th class="text-center align-middle" width="4%">序號</th>
				 <th class="text-center align-middle" width="44%">品名及規格</th>
				 <th class="text-center align-middle" width="44%">用途說明</th>
				 <th class="text-center align-middle" width="4%">數量</th>
				 <th class="text-center align-middle"width="4%">單位</th>
			 </tr>
			 </thead>
			 <tbody>
<%--			 <c:forEach items="${caseData.Eip08w060CaseList}" var="caseData" varStatus="status">--%>
							<tr>
								<td cssClass="text-center align-middle"><c:out value="1" /></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[0].item" cssClass="form-control"  size="75" maxlength="100" /></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[0].desc_memo" cssClass="form-control"  size="75" maxlength="200"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[0].cnt"  cssClass="form-control"  size="3" maxlength="3" onkeyup="value=value.replace(/[^(\d)]/g,'')" /></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[0].unit"  cssClass="form-control" size="7" maxlength="7"/></td>
							</tr>
							<tr>
								<td cssClass="text-center align-middle"><c:out value="2"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[1].item" cssClass="form-control"  size="75" maxlength="50"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[1].desc_memo" cssClass="form-control"  size="75" maxlength="100"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[1].cnt" cssClass="form-control" size="3" maxlength="3" onkeyup="value=value.replace(/[^(\d)]/g,'')" /></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[1].unit" cssClass="form-control"   size="7" maxlength="7"/></td>
							</tr>
							<tr>
								<td cssClass="text-center align-middle"><c:out value="3"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[2].item" cssClass="form-control"  size="75" maxlength="50"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[2].desc_memo" cssClass="form-control"  size="75" maxlength="100"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[2].cnt" cssClass="form-control"  size="3" maxlength="3" onkeyup="value=value.replace(/[^(\d)]/g,'')" /></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[2].unit" cssClass="form-control"  size="7" maxlength="7"/></td>
							</tr>
							<tr>
								<td cssClass="text-center align-middle"><c:out value="4"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[3].item" cssClass="form-control"   size="75" maxlength="50"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[3].desc_memo" cssClass="form-control"   size="75" maxlength="100"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[3].cnt" cssClass="form-control"  size="3" maxlength="3" onkeyup="value=value.replace(/[^(\d)]/g,'')" /></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[3].unit" cssClass="form-control"  size="7" maxlength="7"/></td>
							</tr>
							<tr>
								<td cssClass="text-center align-middle"><c:out value="5"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[4].item" cssClass="form-control"   size="75" maxlength="50"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[4].desc_memo" cssClass="form-control"   size="75" maxlength="100"/></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[4].cnt" cssClass="form-control"   size="3" maxlength="3" onkeyup="value=value.replace(/[^(\d)]/g,'')" /></td>
								<td cssClass="text-center align-middle"><form:input path="eip08w060CaseList[4].unit" cssClass="form-control"   size="7" maxlength="7"/></td>
							</tr>
<%--                        </c:forEach>--%>
			 </tbody>
		 </table>
	 </div>
            <form:hidden id="processTy" path="processTy" />
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {

			var config = getDataTablesConfig();
            var table = $('#listTable').DataTable(config);
			var reportType = $("input[id=reportType]:checked").val();

            $('#btnInsert').click(function() {
                $('input[id="processTy"]').val("A");
           		$('#Eip08W060xForm').attr('action', '<c:url value="/Eip08w060_inster.action" />').submit();
            });



			$('#btBack').click(function() {
				$('#Eip08W060xForm').attr('action', '<c:url value="/Eip08w060_enter.action" />').submit();
			});

			<%--$('#btnDelete').click(function() {--%>
			<%--	showConfirm('確定要刪除資料？', () => {--%>
			<%--		$('#Eip08W060xForm').attr('action', '<c:url value="/Eip08W060_delete.action" />').submit();--%>
			<%--	});--%>
			<%--});  --%>




         });

</script>
</jsp:attribute>
</tags:layout>