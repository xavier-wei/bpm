<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w040Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->       
    <tags:button id="btnEdit">
    	更新<i class="fas fa-edit"></i>
    </tags:button>
    <tags:button id="btnDelete">
    	刪除<i class="fas fa-trash-alt"></i>
    </tags:button>
    <tags:button id="btnBack">
    	返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
	<tags:fieldset legend="部門管理修改">
		<form:form id="eip00w040Form" name="eip00w040Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
                   <div class="col-6 col-md form-inline">
                   	    <tags:text-item label="部門代號"><c:out value="${caseData.queryDepts.dept_id}"/></tags:text-item>
                   </div>
            </tags:form-row>
            <tags:form-row>
                   <form:label cssClass="col-form-label star" path="dept_name">部門名稱：</form:label>
	                <div class="col-12 col-md">
	                    <form:input path="dept_name" cssClass="form-control" size="30" maxlength="50" value="${caseData.dept_name}"/>
	                </div>
            </tags:form-row>
            <tags:form-row>
                   <form:label cssClass="col-form-label" path="dept_desc">部門說明：</form:label>
	                <div class="col-12 col-md">
	                    <form:input path="dept_desc" cssClass="form-control" size="50" maxlength="120" value="${caseData.dept_desc}"/>
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
	$('#btnEdit').click(function(){
		$('#eip00w040Form').attr('action', '<c:url value="/Eip00w040_update.action" />').submit();
	})
	$('#btnDelete').click(function(e){
        showConfirm('確定刪除嗎？',()=>{
            $('#eip00w040Form').attr('action', '<c:url value="/Eip00w040_delete.action" />').submit();
        });
    })
})
</script>
</jsp:attribute>
</tags:layout>