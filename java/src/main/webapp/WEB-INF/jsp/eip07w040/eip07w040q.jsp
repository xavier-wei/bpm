<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w040Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnSearch">
    	查詢<i class="fas fa-search"></i>
    </tags:button>          
    <tags:button id="btnPrint">
    	列印<i class="fas fa-print"></i>
    </tags:button>
    <tags:button id="btnClear">
    	清除<i class="fas fa-eraser"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip07w040Form" name="eip07w040Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            <form:label path="carprocess_status" cssClass="col-form-label star">派車狀態：</form:label>
            <form:select path="carprocess_status" cssClass="form-control add">
               	  <form:option value=""></form:option>
                  <form:option value="1">1-未處理</form:option>
                  <form:option value="2">2-已處理</form:option>
                  <form:option value="A">A-全部申請主管退單</form:option>
            </form:select>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="applydateStart">申請日期：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
                    <form:input path="applydateStart" cssClass="add form-control dateTW date num_only" />~
                    <form:input path="applydateEnd" cssClass="add form-control dateTW date num_only" />
                </div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="using_time_s">用車日期：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
                    <form:input path="using_time_s" cssClass="add form-control dateTW date num_only" />~
                    <form:input path="using_time_e" cssClass="add form-control dateTW date num_only" />
                </div>
            </tags:form-row>
            <tags:form-row>
            <b>待處理派車案件</b>
            	    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th class="align-middle"  style="width: 10%">序號</th>
                            <th style="width: 10%">申請人<br>申請單位</th>
                            <th class="align-middle"  style="width: 10%">用車日期</th>
                            <th class="align-middle"  style="width: 20%">用車時間起迄</th>
                            <th class="align-middle"  style="width: 30%">用車事由</th>
                            <th class="align-middle"  style="width: 10%">表單狀態</th>
                            <th class="align-middle"  style="width: 10%">明細</th>
                        </thead>
                        <c:if test="${empty caseData.notHandleList}">
                        <tbody>
	                        <tr>
	                        	<td colspan="7">目前無待處理派車案件</td>
	                        </tr>
                        </tbody>
                        </c:if>
                        <c:forEach items="${caseData.notHandleList}" var="item" varStatus="status">
                        <tbody><tr>
                        	<td><c:out value="${status.index+1}"/></td>
                        	<td><c:out value="${item.apply_user}"/><br>
                        		<c:out value="${item.apply_dept}"/>
                        	</td>
                        	<td><func:minguo value="${item.using_date}"/></td>
                        	<td>
	                        	<c:out value="${item.using_time_s}"/>~
	                        	<c:out value="${item.using_time_e}"/>
                        	</td>
                        	<td class="text-left">
								<c:out value="${item.apply_memo}"/>
                        	</td>
                        	<td>
                        		<c:out value="${item.carprocess_status}"/>
                        	</td>
                        	<td>
                        		<tags:button cssClass="btnDetail" value="${item.applyid}">
									明細
								</tags:button>
                        	</td>
                        	</tr>
                        </tbody>
                        </c:forEach>
                    </table>
            </tags:form-row>
            <tags:form-row>
            <b>秘書處已複核通過案件</b>
                       <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th class="align-middle"  style="width: 10%">全選<input type="checkbox" id="handledListTabcheckAllP" name="handledListTabcheckAllP"></th>
                            <th class="align-middle"  style="width: 10%">派車單號</th>
                            <th class="align-middle"  style="width: 10%">申請人<br>申請單位</th>
                            <th class="align-middle"  style="width: 10%">用車日期</th>
                            <th class="align-middle"  style="width: 10%">用車時間起迄</th>
                            <th class="align-middle"  style="width: 30%">用車事由</th>
                            <th class="align-middle"  style="width: 10%">已列印派車單註記</th>
                            <th class="align-middle" style="width: 10%">補印</th>
                        </thead>
                        <c:if test="${empty caseData.handledList}">
                        <tbody>
	                        <tr>
	                        	<td colspan="7">目前無秘書處已複核通過案件</td>
	                        </tr>
                        </tbody>
                        </c:if>
                        <c:forEach items="${caseData.handledList}" var="item" varStatus="status">
                        <tbody>
                        	<td>
                        	<form:checkbox path="applyids" name="applyids" value="${item.applyid}"/>
                        	</td>
                        	<td><c:out value="${item.applyid}"/></td>
                        	<td><c:out value="${item.apply_user}"/><br>
                        		<c:out value="${item.apply_dept}"/>
                        	</td>
                        	<td><func:minguo value="${item.using_date}"/></td>
                        	<td>
	                        	<c:out value="${item.using_time_s}"/>~
	                        	<c:out value="${item.using_time_e}"/>
                        	</td>
                        	<td class="text-left">
								<c:out value="${item.apply_memo}"/>
                        	</td>
                        	<td>
                        		<c:out value="${item.print_mk}"/>
                        	</td>
                        	<td>
                        	<c:if test="${item.print_mk eq 'Y'}">
                        		<tags:button cssClass="btnPrint2" value="${item.applyid}">
									補印
								</tags:button>
							</c:if>
                        	</td>
                        </tbody>
                        </c:forEach>
                    </table>
            </tags:form-row>
			<form:hidden path="applyid"/>
			<form:hidden path="reprintApplyid"/>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
        	

            $('.btnDetail').click(function() {
				$('#applyid').val($(this).val());
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_query.action" />').submit();
            });
            
            
            $('#btnPrint').click(function() {
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_print.action" />').submit();
            });
            
            
            $(".btnPrint2").click(function(){
            	$("#reprintApplyid").val($(this).val());
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_print.action" />').submit();
            });
            
            $('#btnSearch').click(function() {
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_enter.action" />').submit();
            });
            
            //全選的切換
            var flag = false;
            $("#handledListTabcheckAllP").click(function(){
            	$("input[name='applyids']:checkbox").each(function(){
            		$(this).prop('checked',!flag);
            	});
                flag = !flag;
            });
            
            $('#btnClear').click(function(){
            	$('.add').val('');
            });

         });
</script>
</jsp:attribute>
</tags:layout>