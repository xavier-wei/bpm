<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w050Controller).CASE_KEY" />
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
		<form:form id="eip07w050Form" name="eip07w050Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="applydateStart">申請日期：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
                    <form:input path="applydateStart" cssClass="add form-control dateTW date" />~
                    <form:input path="applydateEnd" cssClass="add form-control dateTW date" />
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

            $('#btnConfirm').click(function() {
                if($('#applydateStart').val()==''){
                	showAlert('申請日期(起)為必需輸入');
                	return;
                }
                
                if($('#applydateEnd').val()==''){
                	$('#applydateEnd').val(changeDateType(getSysdate()));
                }   	
            	$('#eip07w050Form').attr('action', '<c:url value="/Eip07w050_query.action" />').submit();
            });
            
            $('#btnClear').click(function() {
                $("#applydateStart").val('');
                $("#applydateEnd").val('');
            });
            
         });
</script>
</jsp:attribute>
</tags:layout>