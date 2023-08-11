<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w050Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnPrint">
    	列印<i class="fas fa-print"></i>
    </tags:button>
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="查詢結果">
		<form:form id="eip08w050Form" name="eip08w050Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<div class="col-12 col-md-12">申請年月:<c:out value="${caseData.applyYearMonth}"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-12 col-md-12">領物單核發數量統計表<b>(依申請單位顯示)</b>:</div>
            	    <table class="table table-hover mt-2">
                        <thead>
                        	<th style="width: 5%">序號</th>
                            <th style="width: 25%">申請單位</th>
                            <th style="width: 25%">品名大類</th>
                            <th style="width: 25%">品名</th>
                            <th style="width: 20%">核發數量</th>
                        </thead>
	                       <c:forEach items="${caseData.unitList}" var="item" varStatus="status">
	                        <tbody>
		                        	<td><c:out value="${status.index +1 }"/></td>
		                        	<td class="text-left"><c:out value="${item.apply_dept}"/></td>
		                        	<td class="text-left"><c:out value="${item.itemkind_nm}"/></td>
		                        	<td class="text-left"><c:out value="${item.itemno_nm}"/></td>
		                        	<td class="text-right"><c:out value="${item.approve_cnt}"/></td>
		                        	
	                        </tbody>
	                        </c:forEach>
                    </table>
            </tags:form-row>
 			<tags:form-row>
 				<div class="col-12 col-md-12 mt-4">領物單核發數量統計表<b>(依品名顯示)</b>:</div>
 				    <table class="table table-hover mt-2">
                        <thead>
                        	<th style="width: 5%">序號</th>
                            <th style="width: 33%">品名大類</th>
                            <th style="width: 33%">品名</th>
                            <th style="width: 29%">核發數量</th>
                        </thead>
	                       <c:forEach items="${caseData.itemList}" var="item" varStatus="status">
	                        <tbody>
		                        	<td><c:out value="${status.index +1 }"/></td>
		                        	<td class="text-left"><c:out value="${item.itemkind_nm}"/></td>
		                        	<td class="text-left"><c:out value="${item.itemno_nm}"/></td>
		                        	<td class="text-right"><c:out value="${item.approve_cnt}"/></td>
	                        </tbody>
	                        </c:forEach>
                    </table>
 			</tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {

            $('#btnPrint').click(function() {
            	$('#eip08w050Form').attr('action', '<c:url value="/Eip08w050_print.action" />').submit();
            });
            
            $('#btnReturn').click(function(){
            	$('#eip08w050Form').attr('action', '<c:url value="/Eip08w050_enter.action" />').submit();
            });
            
         });
</script>
</jsp:attribute>
</tags:layout>