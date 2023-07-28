<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w030Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnSubmit">
    	複核<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip08w030Form" name="eip08w030Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<div class="col-4 col-md-4">申請日期(起)：<c:out value="${caseData.applydateStart}"/></div>
            	<div class="col-4 col-md-4">申請日期(迄)：<c:out value="${caseData.applydateEnd}"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">複核選項：
            	<label><input type="radio" name="agree"  value="agree">同意</label>
            	<label><input type="radio" name="agree"  value="disagree">不同意</label>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th style="width: 10%">全選<input type="checkbox" id="dataListTabcheckAllP" name="dataListTabcheckAllP"></th>
                            <th style="width: 10%">領物單號</th>
                            <th style="width: 10%">申請人</th>
                            <th style="width: 50%">申請單位</th>
                            <th style="width: 10%">明細</th>
                        </thead>
                        <c:forEach items="${caseData.dataList}" var="item" varStatus="status">
                        <tbody>
                        	<td><form:checkbox path="dataList[${status.index}].Check"  value="applynos" cssClass="checkedgreen"/></td>
                        	<td>PL<c:out value="${item.applyno}"/></td>
                        	<td><c:out value="${item.apply_user}"/></td>
                        	<td class="text-left"><c:out value="${item.apply_dept}"/></td>
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
        	$('#selectFile').click(function(e){
        		$('input[name="agree"]')[0].checked = true;
        	});
            
            $('#btnReturn').click(function(){
           		$('#eip08w030Form').attr('action', '<c:url value="/Eip08w030_enter.action" />').submit();
            });
            
            
            $('.btnDetail').click(function(){
            	$('#applyno').val($(this).val());
           		$('#eip08w030Form').attr('action', '<c:url value="/Eip08w030_detail.action" />').submit();
            });
            
            $('#btnSubmit').click(function(){
            	$('#eip08w030Form').attr('action', '<c:url value="/Eip08w030_update.action" />').submit();
            });
            
            //全選的切換
            var flag = false;
            $("#dataListTabcheckAllP").click(function(){
            	$("input[name^='dataList']:checkbox").each(function(){
            		$(this).prop('checked',!flag);
            	});
                flag = !flag;
            });
         });

</script>
</jsp:attribute>
</tags:layout>