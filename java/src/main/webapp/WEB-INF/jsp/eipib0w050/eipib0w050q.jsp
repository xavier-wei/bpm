<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eipib0w050Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
    <tags:button id="btnInsert">
                        新增<i class="fas fa-user-plus"></i>
    </tags:button>          
    <tags:button id="btnSearch">
                        查詢<i class="fas fa-search"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
        <form:form id="bfib0w050Form" name="bfib0w050Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
                <form:label cssClass="col-form-label star" path="codekind">主代號類別清單：</form:label>
                <div class="col-12 col-md">
                <form:select path="codekind" cssClass="form-control selector">
                 <form:option value="0" name="0">請選擇主代號類別</form:option>
                 <c:forEach items="${caseData.optionList}" var="item">
                     <form:option name="${item.codekind }" value="${item.codekind }">${item.codekind }</form:option>
                 </c:forEach>
                </form:select>
                </div>
            </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
	$('#btnSearch').click(function(){
		if($('#codekind').find(':selected').val() === "0"){
			showAlert('請選擇主代號類別');
			return;
		}
		$('#bfib0w050Form').attr('action', '<c:url value="/Code_query.action" />').submit();
	})
	$('#btnInsert').click(function(){
		$('#bfib0w050Form').attr('action', '<c:url value="/Code_insert.action" />').submit();
	})
})
</script>
</jsp:attribute>
</tags:layout>