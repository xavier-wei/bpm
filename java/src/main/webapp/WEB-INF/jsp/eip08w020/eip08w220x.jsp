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
    <tags:fieldset legend="查詢結果">
		<form:form id="eip08w020Form" name="eip08w020Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<div class="col-4 col-md-4">申請人：<c:out value="${caseData.apply_user}"/></div>
            	<div class="col-4 col-md-4">申請單位：<c:out value="${caseData.apply_dept}"/></div>
            	<div class="col-4 col-md-4">申請日期：<func:minguo value="${caseData.apply_date}" pattern="yyy/MM/dd"/></div>
            </tags:form-row>
            <tags:form-row>
          	 <div class="table-responsive mt-2">	 
            	    <table id="qryListTable" class="table">
	            	    <thead data-orderable="true">
	            	    <tr>
                            <th style="width: 10%">序號</th>
                            <th style="width: 10%">領物單號</th>
                            <th style="width: 10%">申請日期</th>
                            <th style="width: 50%">申請用途</th>
                            <th style="width: 10%">表單狀態</th>
                            <th style="width: 10%">明細</th>
                         </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${caseData.applyitemList}" var="item" varStatus="status">
                        	<tr>
                        	<td><c:out value="${status.index+1}"/></td>
                        	<td>PL<c:out value="${item.applyno}"/></td>
                        	<td><func:minguo value = "${item.apply_date}"  pattern="yyy/MM/dd"/></td>
                        	<td class="text-left"><c:out value="${item.apply_memo}"/></td>
                        	<td><c:out value="${item.process_status}"/></td>
                        	<td>
	                        	<tags:button cssClass="btnDetail" value="${item.applyno}">
									明細
								</tags:button>
                        	</td>
                        	</tr>
                        </c:forEach>
                        </tbody>
                        <form:hidden path="applyno"/>
                    </table>
            </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

		let config = getDataTablesConfig();
		var table = $("#qryListTable").DataTable(config);

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