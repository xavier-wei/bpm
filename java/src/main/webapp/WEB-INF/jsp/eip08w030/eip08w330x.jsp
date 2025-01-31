<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w030Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="查詢結果">
		<form:form id="eip08w030Form" name="eip08w030Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<div class="col-4 col-md-4">領物單號：PL<c:out value="${caseData.applyno}"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">申請人：<c:out value="${caseData.apply_user}"/></div>
            	<div class="col-4 col-md-4">申請單位：<c:out value="${caseData.apply_dept}"/></div>
            	<div class="col-4 col-md-4">申請日期：<func:minguo value = "${caseData.apply_date}" pattern="yyy/MM/dd"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">申請用途：<c:out value="${caseData.apply_memo}"/></div>
            </tags:form-row>
            <tags:form-row>
          	 <div class="table-responsive mt-2">	 
            	    <table id="qryListTable" class="table">
	            	    <thead data-orderable="true">
	            	    <tr>
                            <th style="width: 10%">序號</th>
                            <th style="width: 30%">品名大類</th>
                            <th style="width: 40%">品名</th>
                            <th style="width: 10%">申請數量</th>
                            <th style="width: 10%">單位</th>
                        </tr>
                        </thead>
	                    <tbody>
	                       <c:forEach items="${caseData.detailList}" var="item" varStatus="status">
                        	<tr>
                        	<td><c:out value="${status.index +1 }"/></td>
                        	<td class="text-left"><c:out value="${item.itemkind}"/></td>
                        	<td class="text-left"><c:out value="${item.itemno}"/></td>
                        	<td><c:out value="${item.apply_cnt}"/></td>
                        	<td><c:out value="${item.unit}"/></td>
                        	</tr>
	                        </c:forEach>
	                    </tbody>
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
            $('#btnReturn').click(function(){
           		$('#eip08w030Form').attr('action', '<c:url value="/Eip08w030_query.action" />').submit();
            });
            
         });
</script>
</jsp:attribute>
</tags:layout>