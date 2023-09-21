<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">     
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip08w020Form" name="eip08w020Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<div class="col-4 col-md-4">申請人：<c:out value="${caseData.apply_user}"/></div>
            	<div class="col-4 col-md-4">申請單位：<c:out value="${caseData.apply_dept}"/></div>
            	<div class="col-4 col-md-4">申請日期：<c:out value="${caseData.apply_date}"/></div>
            </tags:form-row>
            <tags:form-row>
            	    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th style="width: 10%">序號</th>
                            <th style="width: 10%">領物單號</th>
                            <th style="width: 10%">申請日期</th>
                            <th style="width: 50%">申請用途</th>
                            <th style="width: 10%">表單狀態</th>
                            <th style="width: 10%">明細</th>
                        </thead>
                        <c:forEach items="${caseData.applyitemList}" var="item" varStatus="status">
                        <tbody>
                        	<td><c:out value="${status.index+1}"/></td>
                        	<td>PL<c:out value="${item.applyno}"/></td>
                        	<td><c:out value="${item.apply_date}"/></td>
                        	<td class="text-left"><c:out value="${item.apply_memo}"/></td>
                        	<td><c:out value="${item.process_status}"/></td>
                        	<td>
	                        	<tags:button cssClass="btnDetail" value="${item.applyno}">
									明細<i class="fas fa-user-plus"></i>
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
            $('.btnDetail').click(function() {
            	$('#applyno').val($(this).val());
           		$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_detail.action" />').submit();
            });
            
            $('#btnReturn').click(function(){
           		$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_enter.action" />').submit();
            });
            
         });

</script>
</jsp:attribute>
</tags:layout>