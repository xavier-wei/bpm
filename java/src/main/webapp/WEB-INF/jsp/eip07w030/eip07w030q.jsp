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
	<form:form id="eip07w030Form" name="eip07w030Form" modelAttribute="${caseKey}" method="POST">
	<form:input path="hidden"/>
    <fieldset>
    <legend>查詢條件</legend>
		    <tags:form-row>
            	<form:label cssClass="col-form-label" path="apply_dept">申請單位：</form:label>
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

     </fieldset>
	    <fieldset>
	    <legend>查詢結果</legend>  
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="applydateStart">審核選項：</form:label>
                <div class="col-12 col-md d-flex align-items-center pt-2">
	            <label><input type="radio" name="agree"  value="agree">同意</label>
            	<label><input type="radio" name="agree"  value="disagree">不同意</label>
                </div>
            </tags:form-row>
          	<tags:form-row>
          	 <div class="table-responsive mt-2">
				<table id="qryListTable" class="table">
					<thead data-orderable="true">
						<tr>
						<th style="width: 4%">全選<input type="checkbox" id="dataListTabcheckAllP" name="dataListTabcheckAllP"></th>
						<th style="width: 6%">派車單號</th>
						<th style="width: 10%">申請人</th>
						<th style="width: 10%">申請單位</th>
						<th style="width: 4%">用車日期</th>
						<th style="width: 5%">申請用車時間起迄</th>
						<th style="width: 8%">目的地</th>
                        <th style="width: 5%">用車人數</th>
						<th style="width: 14%">用車事由</th>
						<th style="width: 5%">表單狀態</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${caseData.dataList}" var="item" varStatus="status">
						<tr>
							<td><form:checkbox path="dataList[${status.index}].Check"  value="applyid" cssClass="checkedgreen"/></td>
							<td><c:out value="${item.applyid}"/></td>
							<td>
								<func:username userid="${item.apply_user}" />
							</td>
							<td><func:dept deptid="${item.apply_dept}"/></td>
							<td><func:minguo value="${item.using_date}"/></td>
							<td><func:timeconvert value="${item.using_time_s}"/>~<func:timeconvert value="${item.using_time_e}"/></td>
							<td><c:out value="${item.destination}"/></td>
							<td><c:out value="${item.num_of_people}"/></td>
							<td class="text-left">
								<span class="ellipsisStr">
								<c:out value="${item.apply_memo}"/>
								</span>
							</td>
							<td><c:out value="${item.carprocess_status}"/></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
            </tags:form-row>
        </fieldset>
        </form:form>

</jsp:attribute>
<jsp:attribute name="footers">
<script>

        
        $(function() {
        	$("#hidden").hide();
            let config = getDataTablesConfig();
            var table = $("#qryListTable").DataTable(config);

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