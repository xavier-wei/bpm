<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<c:if test="${caseData.detailList[0].process_status=='1'}">
    <tags:button id="btnDelete">
    	單號刪除<i class="fas fa-trash-alt"></i>
    </tags:button>
</c:if>     
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip08w020Form" name="eip08w020Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<div class="col-4 col-md-4">領物單號：PL<c:out value="${caseData.applyno}"/></div>
            	<div class="col-4 col-md-4">表單狀態：<c:out value="${caseData.detailList[0].process_status }"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">申請人：<c:out value="${caseData.apply_user}"/></div>
            	<div class="col-4 col-md-4">申請單位：<c:out value="${caseData.apply_dept}"/></div>
            	<div class="col-4 col-md-4">申請日期：<c:out value="${caseData.apply_date}"/></div>
            </tags:form-row>
            <tags:form-row>
            	備註 : 主管未複核前才可刪除領物單
            </tags:form-row>
            <tags:form-row>
            	    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th style="width: 10%">序號</th>
                            <th style="width: 30%">品名大類</th>
                            <th style="width: 40%">品名</th>
                            <th style="width: 10%">申請數量</th>
                            <th style="width: 10%">單位</th>
                        </thead>
	                       <c:forEach items="${caseData.detailList}" var="item" varStatus="status">
	                        <tbody>
		                        	<td><c:out value="${status.index +1 }"/></td>
		                        	<td><c:out value="${item.itemkind}"/></td>
		                        	<td><c:out value="${item.itemno}"/></td>
		                        	<td><c:out value="${item.apply_cnt}"/></td>
		                        	<td><c:out value="${item.apply_dept}"/></td>
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
            $('#btnReturn').click(function(){
           		$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_enter.action" />').submit();
            });
            
            $('#btnDelete').click(function(){
            	showConfirmCallback("是否確認刪除?",deleteData,cancel);
            });
            
            function deleteData(){
            	$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_delete.action" />').submit();
            }
            
            function cancel(){
            	return;
            }
         });
</script>
</jsp:attribute>
</tags:layout>