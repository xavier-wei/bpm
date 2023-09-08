<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w050Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnSearch">
    	查詢<i class="fas fa-search"></i>
    </tags:button>
    <tags:button id="btnConfirm">
    	列印<I class="fas fa-download"></i>
    </tags:button>
    <tags:button id="btnClear">
    	清除<i class="fas fa-eraser"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip08w050Form" name="eip08w050Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="applyYearMonth">申請年月：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
                    <form:input path="applyYearMonth" cssClass="form-control" />
                </div>
            </tags:form-row>
            <tags:form-note>
                申請年月為必填欄位
            </tags:form-note>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
            $('#btnSearch').click(function() {
            	$('#eip08w050Form').attr('action', '<c:url value="/Eip08w050_query.action" />').submit();
            });

            $('#btnConfirm').click(function() {
            	$('#eip08w050Form').attr('action', '<c:url value="/Eip08w050_print.action" />').submit();
            });
            
            $('#btnClear').click(function() {
                $("#applyYearMonth").val('');
            });            
         });
</script>
</jsp:attribute>
</tags:layout>