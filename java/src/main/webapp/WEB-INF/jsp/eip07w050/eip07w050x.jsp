<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w050Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnSubmit">
    	審核<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="查詢結果">
		<form:form id="eip07w050Form" name="eip07w050Form" modelAttribute="${caseKey}" method="POST">
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
                            <th class="align-middle"  style="width: 10%">全選<input type="checkbox" id="dataListTabcheckAllP" name="dataListTabcheckAllP"></th>
                            <th class="align-middle"  style="width: 10%">派車單號</th>
                            <th style="width: 10%">申請人<br>申請單位</th>
                            <th style="width: 20%">用車日期<br>用車時間起迄</th>
                            <th class="align-middle"  style="width: 40%">用車事由</th>
                            <th class="align-middle"  style="width: 10%">表單狀態</th>
                        </thead>
                        <c:forEach items="${caseData.dataList}" var="item" varStatus="status">
                        <tbody>
                        	<td><form:checkbox path="dataList[${status.index}].Check"  value="applyid" cssClass="checkedgreen"/></td>
                        	<td><c:out value="${item.applyid}"/></td>
                        	<td><c:out value="${item.apply_user}"/><br>
                        		<c:out value="${item.apply_dept}"/>
                        	</td>
                        	<td>
	                        	<func:minguo value="${item.using_date}"/><br>
	                        	<c:out value="${item.using_time_s}"/>~
	                        	<c:out value="${item.using_time_e}"/>
                        	</td>
                        	<td class="text-left">
								<span class="ellipsisStr">
			                 		<c:out value="${item.apply_memo}"/>
			                 	</span>
                        	</td>
                        	<td>
                        		<c:out value="${item.carprocess_status}"/>
                        	</td>
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
        	$('#selectFile').click(function(e){
        		$('input[name="agree"]')[0].checked = true;
        	});
            
            $('#btnReturn').click(function(){
           		$('#eip07w050Form').attr('action', '<c:url value="/Eip07w050_enter.action" />').submit();
            });
            
            
            $('.btnDetail').click(function(){
            	$('#applyno').val($(this).val());
           		$('#eip07w050Form').attr('action', '<c:url value="/Eip07w050_detail.action" />').submit();
            });
            
            $('#btnSubmit').click(function(){
            	$('#eip07w050Form').attr('action', '<c:url value="/Eip07w050_update.action" />').submit();
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