<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnConfirm">
    	申請<i class="fas fa-user-plus"></i>
    </tags:button>
    <tags:button id="btnSearch">
    	查詢<i class="fas fa-search"></i>
    </tags:button>       
    <tags:button id="btnClear">
    	清除<i class="fas fa-eraser"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <fieldset>
	<legend>申請條件</legend>
		<form:form id="eip08w020Form" name="eip08w020Form" modelAttribute="${caseKey}" method="POST">
            <form:input path="hidden"/>
            <tags:form-row>
            	<form:label cssClass="col-form-label apply_user" path="apply_user">申請人：</form:label>
                <div class="col-12 col-md">
                    <form:input path="apply_user" cssClass="add form-control star" disabled="true"/>
                </div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label apply_dept" path="apply_dept">申請單位：</form:label>
                <div class="col-12 col-md">
                    <form:input path="apply_dept" cssClass="add form-control" disabled="true"/>
                </div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label apply_date" path="apply_date">申請日期：</form:label>
                <div class="col-12 col-md">
                    <form:input path="apply_date" cssClass="add form-control dateTW cdate" maxlength="7"/>
                </div>
            </tags:form-row>
            <form:hidden path="oriApply_user"/>
            <form:hidden path="oriApply_dept"/>
        </form:form>
    </fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
        	$("#hidden").hide();
            $('#btnConfirm').click(function() {
           		$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_add.action" />').submit();
            });
            
            $('#btnSearch').click(function() {
	    		$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_query.action" />').submit();
            });
                        
            $('#btnClear').click(function() {
            	$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_enter.action" />').submit();
            });
         });
</script>
</jsp:attribute>
</tags:layout>