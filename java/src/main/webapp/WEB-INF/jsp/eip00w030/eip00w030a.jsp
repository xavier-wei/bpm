<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w030Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
    <tags:button id="btnAdd">
    	新增<i class="fas fa-user-plus"></i>
    </tags:button>          
    <tags:button id="btnBack">
    	取消<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip00w030Form" name="eip00w030Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
                   <form:label cssClass="col-form-label star" path="querySystems.sys_id">系統代號：</form:label>
	                <div class="col-12 col-md">
	                    <form:input path="sys_id" cssClass="form-control" size="30" maxlength="50" value="${caseData.querySystems.sys_id}"/>
	                </div>
            </tags:form-row>
            <tags:form-row>
                   <form:label cssClass="col-form-label star" path="sys_name">系統名稱：</form:label>
	                <div class="col-12 col-md">
	                    <form:input path="sys_name" cssClass="form-control" size="30" maxlength="50" value="${caseData.querySystems.sys_name}"/>
	                </div>
            </tags:form-row>
            <tags:form-row>
                   <form:label cssClass="col-form-label star" path="url">網址：</form:label>
	                <div class="col-12 col-md">
	                    <form:input path="url" cssClass="form-control" size="50" maxlength="120" value="${caseData.querySystems.url}"/>
	                </div>
            </tags:form-row>
            <tags:form-row>
                   <form:label cssClass="col-form-label star" path="sort_order">序號：</form:label>
	                <div class="col-12 col-md">
	                    <form:input path="sort_order" cssClass="form-control num_only" size="30" maxlength="9" value="${caseData.querySystems.sort_order}"/>
	                </div>
            </tags:form-row>
            
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
	$('#btnAdd').click(function(){
		$('#eip00w030Form').attr('action', '<c:url value="/Eip00w030_insert.action" />').submit();
	})
	$('#btnBack').click(function(){
		$('#eip00w030Form').attr('action', '<c:url value="/Eip00w030_back.action" />').submit();
	})
})
</script>
</jsp:attribute>
</tags:layout>