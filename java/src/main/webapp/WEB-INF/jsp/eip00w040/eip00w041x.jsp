<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w040Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->       
    <tags:button id="btnBack">
    	返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
	<tags:fieldset legend="明細查詢">
		<form:form id="eip00w040Form" name="eip00w040Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
                   <div class="col-6 col-md form-inline">
                   	    <tags:text-item label="部門代號"><c:out value="${caseData.queryDepts.dept_id}"/></tags:text-item>
                   </div>
            </tags:form-row>
            <tags:form-row>
                   <div class="col-6 col-md form-inline">
                   	    <tags:text-item label="部門名稱"><c:out value="${caseData.queryDepts.dept_name}"/></tags:text-item>
                   </div>
            </tags:form-row>
            <tags:form-row>
                   <div class="col-6 col-md form-inline">
                   	    <tags:text-item label="部門說明"><c:out value="${caseData.queryDepts.dept_desc}"/></tags:text-item>
                   </div>
            </tags:form-row>
            
        </form:form>
	</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
	$('#btnBack').click(function(){
		$('#eip00w040Form').attr('action', '<c:url value="/Eip00w040_back.action" />').submit();
	})
})
</script>
</jsp:attribute>
</tags:layout>