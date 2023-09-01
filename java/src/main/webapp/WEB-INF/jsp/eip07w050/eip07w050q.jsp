<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w050Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnSearch">
    	查詢<i class="fas fa-search"></i>
    </tags:button>
    <tags:button id="btnSubmit">
    	核派<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnClear">
    	清除<i class="fas fa-eraser"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
		<form:form id="eip07w050Form" name="eip07w050Form" modelAttribute="${caseKey}" method="POST">
  		 	<fieldset>
  		 		<legend>查詢條件</legend>
	            <tags:form-row>
	            	<form:label cssClass="col-form-label" path="applydateStart">申請日期：</form:label>
	                <div class="col-12 col-md d-flex align-items-center">
	                    <form:input path="applydateStart" cssClass="add form-control dateTW date num_only" />~
	                    <form:input path="applydateEnd" cssClass="add form-control dateTW date num_only" />
	                </div>
	            </tags:form-row>
 			</fieldset>
    
			<fieldset>
			<legend>查詢結果</legend>
          	  <div class="row">
          	   <div class="col-md-3 col-xl-4 flex-fill">
	              <tags:text-item label="核派選項">
		            <label><input type="radio" name="agree"  value="agree">同意</label>
	            	<label><input type="radio" name="agree"  value="disagree">不同意</label>
	              </tags:text-item>
	          </div>
          </div>
            <tags:form-row>
            	    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th style="width: 5%">全選<input type="checkbox" id="dataListTabcheckAllP" name="dataListTabcheckAllP"></th>
                            <th style="width: 10%">派車單號</th>
                            <th style="width: 10%">申請人</th>
                            <th style="width: 10%">申請單位</th>
                            <th style="width: 5%">申請日期</th>
                            <th style="width: 5%">用車日期</th>
                            <th style="width: 10%">用車時間起迄</th>
                            <th style="width: 35%">用車事由</th>
                            <th style="width: 10%">表單狀態</th>
                        </thead>
                        <c:forEach items="${caseData.dataList}" var="item" varStatus="status">
                        <tbody>
                        	<td><form:checkbox path="dataList[${status.index}].Check"  value="applyid" cssClass="checkedgreen"/></td>
                        	<td><c:out value="${item.applyid}"/></td>
                        	<td><func:username userid="${item.apply_user}"/>
                        	</td>
                        	<td>
                        		<func:dept deptid="${item.apply_dept}"/>
                        	</td>
                        	<td>
                        		<func:minguo value="${item.apply_date}"/>
                        	</td>
                        	<td>
	                        	<func:minguo value="${item.using_date}"/>
                        	</td>
                        	<td>
	                        	<func:timeconvert value="${item.using_time_s}"/>~
	                        	<func:timeconvert value="${item.using_time_e}"/>
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
            </fieldset>

        </form:form>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {

            $('#btnConfirm').click(function() {
                if($('#applydateStart').val()==''){
                	showAlert('申請日期(起)為必需輸入');
                	return;
                }
                
                if($('#applydateEnd').val()==''){
                	$('#applydateEnd').val(changeDateType(getSysdate()));
                }   	
            	$('#eip07w050Form').attr('action', '<c:url value="/Eip07w050_query.action" />').submit();
            });
            
            $('#btnClear').click(function() {
                $("#applydateStart").val('');
                $("#applydateEnd").val('');
                $('#eip07w050Form').attr('action', '<c:url value="/Eip07w050_enter.action" />').submit();
            });
            
            $('#btnSearch').click(function() {
            	$('#eip07w050Form').attr('action', '<c:url value="/Eip07w050_query.action" />').submit();
            });
            
            $('#btnSubmit').click(function() {
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