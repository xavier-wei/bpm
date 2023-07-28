<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w040Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip08w040Form" name="eip08w040Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<div class="col-4 col-md-4">申請日期(起)：<c:out value="${caseData.apply_dateStart}"/></div>
            	<div class="col-4 col-md-4">申請日期(迄)：<c:out value="${caseData.apply_dateEnd}"/></div>
            </tags:form-row>
            <tags:form-row>
            	    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th style="width: 20%">領物單號</th>
                            <th style="width: 20%">申請人</th>
                            <th style="width: 30%">申請單位</th>
                            <th style="width: 10%">申請日期</th>
                            <th style="width: 10%">表單狀態</th>
                            <th style="width: 10%">明細</th>
                        </thead>
                        <c:forEach items="${caseData.dataList}" var="item" varStatus="status">
                        <tbody>
                        	<td>PL<c:out value="${item.applyno}"/></td>
                        	<td><c:out value="${item.apply_user}"/></td>
                        	<td class="text-left"><c:out value="${item.unit}"/></td>
                        	<td><c:out value="${item.apply_date}"/></td>
                        	<td><c:out value="${item.process_status}"/></td>
                        	<td>
	                        	<tags:button cssClass="btnDetail" value="${item.applyno}">
									明細
								</tags:button>
                        	</td>
                        </tbody>
                        </c:forEach>
                        <form:hidden path="applyno"/>
                    </table>
            </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
            
            $('#btnReturn').click(function(){
           		$('#eip08w040Form').attr('action', '<c:url value="/Eip08w040_enter.action" />').submit();
            });
            
            
            $('.btnDetail').click(function(){
            	$('#applyno').val($(this).val());
           		$('#eip08w040Form').attr('action', '<c:url value="/Eip08w040_detail.action" />').submit();
            });
            
         });
</script>
</jsp:attribute>
</tags:layout>