<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">    
    <tags:button id="btnEdit">
    	更新<i class="fas fa-edit"></i>
    </tags:button>
        <tags:button id="btnBack">
    	取消<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip00w020Form" name="eip00w020Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
                   <div class="col-6 col-md form-inline">
                   	    <tags:text-item label="員工編號"><c:out value="${caseData.users.user_id}"/></tags:text-item>
                   </div>
                   <div class="col-6 col-md form-inline">
                   	    <tags:text-item label="部門代號"><c:out value="${caseData.users.dept_id}"/></tags:text-item>
                   </div>
            </tags:form-row>
            <tags:form-row>
				   <div class="col-6 col-md form-inline">
                   	    <tags:text-item label="中文姓名"><c:out value="${caseData.users.user_name}"/></tags:text-item>
                   </div>
                   <div class="col-6 col-md form-inline">
                   	    <tags:text-item label="英文姓名"><c:out value=""/></tags:text-item>
                   </div>
            </tags:form-row>
            <tags:form-row>
                   <div class="col-6 col-md form-inline">
                   	    <tags:text-item label="電子郵件"><c:out value="${caseData.users.email}"/></tags:text-item>
                   </div>
            </tags:form-row>
            <tags:form-row>
            	   <div class="col-6 col-md form-inline">
                   	    <tags:text-item label="連絡電話"><c:out value="${caseData.users.tel1}"/></tags:text-item>
                   </div>
                   <div class="col-6 col-md form-inline">
                   	    <tags:text-item label="分機"><c:out value="${caseData.users.tel2}"/></tags:text-item>
                   </div>
            </tags:form-row>
            <tags:form-row>
                   <form:label cssClass="col-form-label star" path="userStatus">使用者狀態：</form:label>
	                <div class="col-12 col-md form-inline">
	                    <form:radiobutton path="userStatus" label="啟用" value="Y" />
	                    <form:radiobutton path="userStatus" label="停用" value="N" />
	                </div>
            </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
	$('#btnBack').click(function(){
		$('#eip00w020Form').attr('action', '<c:url value="/Eip00w020_enter.action" />').submit();
	})
	$('#btnEdit').click(function(){
		$('#eip00w020Form').attr('action', '<c:url value="/Eip00w020_update.action" />').submit();
	})
})
</script>
</jsp:attribute>
</tags:layout>