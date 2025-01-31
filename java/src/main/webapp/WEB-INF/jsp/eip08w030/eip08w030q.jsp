<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w030Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnConfirm">
    	確認<i class="fas fa-user-check"></i>
    </tags:button>          
    <tags:button id="btnClear">
    	清除<i class="fas fa-eraser"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip08w030Form" name="eip08w030Form" modelAttribute="${caseKey}" method="POST">
		<form:input path="hidden"/>
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="applydateStart">申請日期：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
                    <form:input path="applydateStart" cssClass="add form-control dateTW cdate" />~
                    <form:input path="applydateEnd" cssClass="add form-control dateTW cdate" />
                </div>
            </tags:form-row>
            <tags:form-note>
                申請日期(起)為必填欄位
            </tags:form-note>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
			$("#hidden").hide();
            $('#btnConfirm').click(function() {
                if($('#applydateStart').val()==''){
                	showAlert('申請日期(起)為必需輸入');
                	return;
                }
                
                if($('#applydateEnd').val()==''){
                	$('#applydateEnd').val(changeDateType(getSysdate()));
                }
                
            	$('#eip08w030Form').attr('action', '<c:url value="/Eip08w030_query.action" />').submit();
            });
            
            $('#btnClear').click(function() {
            	$('#eip08w030Form').attr('action', '<c:url value="/Eip08w030_enter.action" />').submit();
            });
            
         });
</script>
</jsp:attribute>
</tags:layout>