<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w070Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnSearch">
    	查詢<i class="fas fa-search"></i>
    </tags:button>          
    <tags:button id="btnPrint">
    	列印<i class="fas fa-print"></i>
    </tags:button>
    <tags:button id="btnClear">
    	清除<i class="fas fa-eraser"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip07w070Form" name="eip07w070Form" modelAttribute="${caseKey}" method="POST">
		<form:input path="hidden"/>
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="using_date_s">用車日期：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
                    <form:input path="using_date_s" cssClass="add form-control dateTW date num_only" />~
                    <form:input path="using_date_e" cssClass="add form-control dateTW date num_only" />
                </div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label" path="carno1">車牌號碼：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
		            <form:select path="carno" cssClass="form-control add">
		            <form:option value=""></form:option>
		            <c:forEach items="${caseData.carnoList}" var="item" varStatus="status">
		                  <form:option value="${item.carno1}-${item.carno2}">${item.carno1}-${item.carno2}</form:option>
		            </c:forEach>
		            </form:select>
                </div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label" path="using_date_s">駕駛人：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
                    <form:input path="name" cssClass="add form-control" maxlength="30"/>
                </div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-12 col-md form-inline">
                    <form:label cssClass="col-form-label star" path="orderCondition">排序條件：</form:label>
                	<form:radiobutton path="orderCondition" label="用車日期" value="1" checked="true" />
                    <form:radiobutton path="orderCondition" label="車牌號碼" value="2" />
                </div>
            </tags:form-row>
            <tags:form-note>
                用車日期(起)、排序條件為必填欄位
            </tags:form-note>
        </form:form>
    </tags:fieldset>
    
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
        	$("#hidden").hide();
            $('#btnSearch').click(function() {
                if($('#using_date_s').val()==''){
                	showAlert('用車日期(起)為必需輸入');
                	return;
                }
                
                if($('#applydateEnd').val()==''){
                	$('#applydateEnd').val(changeDateType(getSysdate()));
                }   	
            	$('#eip07w070Form').attr('action', '<c:url value="/Eip07w070_search.action" />').submit();
            	
            });
            
            $('#btnClear').click(function() {
                $(".add").val('');
            });
            
            $('#btnPrint').click(function() {
            	$('#eip07w070Form').attr('action', '<c:url value="/Eip07w070_print.action" />').submit();
            });
         });
</script>
</jsp:attribute>
</tags:layout>