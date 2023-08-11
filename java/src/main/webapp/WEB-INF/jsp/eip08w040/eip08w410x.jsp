<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w040Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnConfirm">
    	確認<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnBack">
    	回上一頁<i class="fas fa-reply"></i>
    </tags:button>    
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset  legend="查詢結果">
		<form:form id="eip08w040Form" name="eip08w040Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<div class="col-4 col-md-4">領物單號：PL<c:out value="${caseData.applyno}"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">申請人：<c:out value="${caseData.apply_user}"/></div>
            	<div class="col-4 col-md-4">申請單位：<c:out value="${caseData.apply_dept}"/></div>
            	<div class="col-4 col-md-4">申請日期：<func:minguo value = "${caseData.apply_date}"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">申請用途：<c:out value="${caseData.apply_memo}"/></div>
            </tags:form-row>
            <tags:form-row>
            	    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th style="width: 10%">序號</th>
                            <th style="width: 30%">品名大類</th>
                            <th style="width: 40%">品名</th>
                            <th style="width: 10%">申請數量</th>
                            <th style="width: 10%">實際庫存數量</th>
                            <th style="width: 10%">帳面庫存數量</th>
                            <th style="width: 10%">核發數量</th>
                        </thead>
	                       <c:forEach items="${caseData.detailList}" var="item" varStatus="status">
	                        <tbody>
		                        	<td><c:out value="${status.index +1}"/></td>
		                        	<td class="text-left"><c:out value="${item.itemkind_nm}"/></td>
		                        	<td class="text-left"><c:out value="${item.itemno_nm}"/></td>
		                        	<td><c:out value="${item.apply_cnt}"/>
		                        	<form:hidden path="apply_cnt" value="${item.apply_cnt}" cssClass="apply_cnt" />
		                        	</td>
		                        	<td><c:out value="${item.final_cnt}"/></td>
		                        	<td><c:out value="${item.book_cnt}"/></td>
		                        	<td><form:input path="detailList[${status.index}].provide_num" cssClass=" form-control provide_num num_only" /></td>
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
           		$('#eip08w040Form').attr('action', '<c:url value="/Eip08w040_enter.action" />').submit();
            });
            
            $('.provide_num').change(function(){
            	var index = $('.provide_num').index(this);
            	if($('.provide_num').eq(index).val()>$('.apply_cnt').eq(index).val()){
            		showAlert('核發數量需小於等於申請數量');
            	}
            });
            
            $('#btnBack').click(function(){
           		$('#eip08w040Form').attr('action', '<c:url value="/Eip08w040_query.action" />').submit();
            });
            
            $('#btnConfirm').click(function(){
           		$('#eip08w040Form').attr('action', '<c:url value="/Eip08w040_update.action" />').submit();
            });
            
         });
</script>
</jsp:attribute>
</tags:layout>