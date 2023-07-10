<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w020controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnConfirm">
    	單號刪除<i class="fas fa-user-plus"></i>
    </tags:button>          
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-trash-alt"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip08w020Form" name="eip08w020Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<div class="col-6 col-md-6">領物單號：<c:out value=""/></div>
            	<div class="col-6 col-md-6">表單狀態：<c:out value=""/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">申請人：<c:out value="${caseData.apply_user}"/></div>
            	<div class="col-4 col-md-4">申請單位：<c:out value="${caseData.apply_dept}"/></div>
            	<div class="col-4 col-md-4">申請日期：<c:out value="${caseData.apply_date}"/></div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="apply_memo">備註:主管未複核前才可刪除領物單</form:label>
                <div class="col-12 col-md">
                    <form:input path="apply_memo" cssClass="add form-control" disabled="true"/>
                </div>
            </tags:form-row>
            <tags:form-row>
            	    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th style="width: 10%">序號</th>
                            <th>品名大類</th>
                            <th style="width: 20%">品名</th>
                            <th style="width: 30%">申請數量</th>
                            <th style="width: 30%">單位</th>
                        </thead>
                        <tbody>
	                       <c:forEach items="${caseData.employeeList}" var="item" varStatus="status">
	                        	<td><c:out value="${status.index}"/></td>
	                        	<td><c:out value="${item.itemkind}"/></td>
	                        	<td><c:out value="${item.itemno}"/></td>
	                        	<td><c:out value="${item.apply_cnt}"/></td>
	                        	<td><c:out value="${item.apply_dept}"/></td>
	                        </c:forEach>
                        </tbody>
                    </table>
            </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
        	$(".apply").show();
        	$(".search").hide();
        	$("#apply_user").addClass("star");
        	$("#apply_dept").addClass("star");
        	
        	
            $('#btnConfirm').click(function() {
           		$('#eip08w020Form').attr('action', '<c:url value="/Eip00w010_add.action" />').submit();
            });
            
            $('#btnClear').click(function() {
                $("#apply_date").val('');
            });
            
         });
        


</script>
</jsp:attribute>
</tags:layout>