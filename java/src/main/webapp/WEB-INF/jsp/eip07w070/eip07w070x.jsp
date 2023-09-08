<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w070Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnPrint">
    	列印<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
		<form:form id="eip07w070Form" name="eip07w070Form" modelAttribute="${caseKey}" method="POST">
		<fieldset>
			<legend>查詢條件</legend>
			<tags:form-row>
				<div class="col-4 ml-2">用車日期：<func:minguo value="${caseData.using_date_s}"/> ~ <func:minguo value="${caseData.using_date_e}"/></div>
				<div class="col-3">車牌號碼：<c:if test="${!empty caseData.carno}">${caseData.carno}</c:if></div>
				<div class="col-3">駕駛人：${caseData.name}</div>
			</tags:form-row>
		</fieldset>
		
		
		<fieldset>
		<c:if test="${caseData.orderCondition eq '1'}">
			<legend>查詢結果：依用車日期排序</legend>
 		        <tags:form-row>
           	    <table id="foodTable" class="table table-hover m-2">
	                <thead>
	                	<th class="align-middle"  style="width: 10%">用車日期</th>
	                    <th class="align-middle"  style="width: 10%">用車時間起迄</th>
	                    <th class="align-middle" style="width: 10%">車牌號碼</th>
	                    <th class="align-middle" style="width: 10%">駕駛人姓名</th>
	                    <th class="align-middle"  style="width: 25%">用車事由</th>
	                    <th class="align-middle"  style="width: 25%">目的地</th>
	                    <th class="align-middle"  style="width: 10%">派車單號</th>
	                </thead>
	                <tbody>
	                <c:forEach items="${caseData.dataList}" var="item" varStatus="status">
	                 	<tr>
	                 		<td><func:minguo value="${item.using_date}"/></td>
	                 		<td><func:timeconvert value="${item.using_time_s}"/>~<func:timeconvert value="${item.using_time_e}"/></td>
	                 		<td><c:out value="${item.carno1}"/>-<c:out value="${item.carno2}"/></td>
	                 		<td><c:out value="${item.name}"/></td>
	                 		<td class="text-left"><span class="ellipsisStr"><c:out value="${item.apply_memo}"/></span></td>
	                 		<td class="text-left"><c:out value="${item.destination}"/></td>
	                 		<td><c:out value="${item.applyid}"/></td>
	                 	</tr>
	                </c:forEach>
	                </tbody>
                </table>
            	</tags:form-row>
         </c:if>
         
         <c:if test="${caseData.orderCondition eq '2'}">
		 	<legend>查詢結果：依車牌號碼排序</legend>
				<tags:form-row>
         	    <table id="foodTable" class="table table-hover m-2">
	                <thead>
	                	<th class="align-middle"  style="width: 10%">用車日期</th>
	                    <th class="align-middle"  style="width: 10%">用車時間起迄</th>
	                    <th class="align-middle"  style="width: 10%">車牌號碼</th>
	                    <th class="align-middle"  style="width: 10%">駕駛人姓名</th>
	                    <th class="align-middle"  style="width: 25%">用車事由</th>
	                    <th class="align-middle"  style="width: 25%">目的地</th>
	                    <th class="align-middle"  style="width: 10%">派車單號</th>
	                </thead>
	                <tbody>
	                <c:forEach items="${caseData.dataList}" var="item" varStatus="status">
	                 	<tr>
	                 		<td><func:minguo value="${item.using_date}"/></td>
	                 		<td><func:timeconvert value="${item.using_time_s}"/>~<func:timeconvert value="${item.using_time_e}"/></td>
	                 		<td><c:out value="${item.carno1}"/>-<c:out value="${item.carno2}"/></td>
	                 		<td><c:out value="${item.name}"/></td>
	                 		<td class="text-left"><span class="ellipsisStr"><c:out value="${item.apply_memo}"/></span></td>
	                 		<td class="text-left"><c:out value="${item.destination}"/></td>
	                 		<td><c:out value="${item.applyid}"/></td>
	                 	</tr>
                     </c:forEach>
	                </tbody>
                 </table>
		         </tags:form-row>
         </c:if>
		 </fieldset>
        </form:form>
        
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
 
            $('#btnReturn').click(function(){
           		$('#eip07w070Form').attr('action', '<c:url value="/Eip07w070_enter.action" />').submit();
            });
                   
            $('#btnPrint').click(function(){
            	$('#eip07w070Form').attr('action', '<c:url value="/Eip07w070_print.action" />').submit();
            });
            
         });
</script>
</jsp:attribute>
</tags:layout>