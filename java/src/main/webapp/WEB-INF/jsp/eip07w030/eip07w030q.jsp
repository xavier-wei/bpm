<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w030Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnSearch">
    	查詢<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnSubmit">
    	審核<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnClear">
    	清除<i class="fas fa-eraser"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip07w030Form" name="eip07w030Form" modelAttribute="${caseKey}" method="POST">
		    <tags:form-row>
            	<form:label cssClass="col-form-label star" path="apply_dept">申請單位：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
                    <form:input path="apply_dept" cssClass="add form-control" disabled="true"/>
                </div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="applydateStart">申請日期：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
                    <form:input path="applydateStart" cssClass="add form-control dateTW date" />~
                    <form:input path="applydateEnd" cssClass="add form-control dateTW date" />
                </div>
            </tags:form-row>
          <div class="row mt-2">
          	   <div class="col-md-3 col-xl-4 flex-fill">
	              <tags:text-item label="審核選項">
	            <label><input type="radio" name="agree"  value="agree">同意</label>
            	<label><input type="radio" name="agree"  value="disagree">不同意</label>
	              </tags:text-item>
	          </div>
          </div>
            
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
	                        	<c:out value="${item.using_time_s}"/>~<c:out value="${item.using_time_e}"/>
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

            $('#btnSearch').click(function() {
                if($('#applydateStart').val()==''){
                	showAlert('申請日期(起)為必需輸入');
                	return;
                }
                
                if($('#applydateEnd').val()==''){
                	$('#applydateEnd').val(changeDateType(getSysdate()));
                }   	
            	$('#eip07w030Form').attr('action', '<c:url value="/Eip07w030_query.action" />').submit();
            });
            
            $('#btnClear').click(function() {
                $("#applydateStart").val('');
                $("#applydateEnd").val('');
            });
            
            $('#btnSubmit').click(function(){
            	$('#eip07w030Form').attr('action', '<c:url value="/Eip07w030_update.action" />').submit();
            });
            
            //全選的切換
            var flag = false;
            $("#dataListTabcheckAllP").click(function(){
            	$("input[name^='dataList']:checkbox").each(function(){
            		$(this).prop('checked',!flag);
            	});
                flag = !flag;
            });

            $('#selectFile').click(function(e){
        		$('input[name="agree"]')[0].checked = true;
        	});
            
            $('.btnDetail').click(function(){
            	$('#applyno').val($(this).val());
           		$('#eip07w030Form').attr('action', '<c:url value="/Eip07w030_detail.action" />').submit();
            });
            
         });
</script>
</jsp:attribute>
</tags:layout>