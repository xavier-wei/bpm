<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w040Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnSubmit">
    	確認<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
     	<fieldset>
      	<legend>待處理派車明細</legend>
		<form:form id="eip07w040Form" name="eip07w040Form" modelAttribute="${caseKey}" method="POST">
		    <tags:form-row>
		        <tags:text-item label="派車單號">
                    <c:out value="${caseData.carBookingDetailData.applyid}"/>
                </tags:text-item>
            </tags:form-row>
            <tags:form-row>
            	<form:hidden path="using" value="${caseData.carBookingDetailData.using}"/>
            	<div class="col-4 col-md-4">
	            	<tags:text-item label="申請人">
	            		<c:out value="${caseData.carBookingDetailData.apply_user}"/>
	            	</tags:text-item>
            	</div>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="申請單位">
            			<c:out value="${caseData.carBookingDetailData.apply_dept}"/>
            		</tags:text-item>
            	</div>
            	<div class="col-4 col-md-4">
	            	<tags:text-item label="車輛種類">
	            		<c:out value="${caseData.carBookingDetailData.apply_car_type}"/>
	            	</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-12 col-md-12">
	            	<tags:text-item label="用車事由">
	            	<span class="ellipsisStr">
	            		<c:out value="${caseData.carBookingDetailData.apply_memo}"/>
	            	</span>
	            	</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">
            	<tags:text-item label="目的地">
            		<c:out value="${caseData.carBookingDetailData.destination}"/>
            	</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">
            	<tags:text-item label="人數">
            		<c:out value="${caseData.carBookingDetailData.num_of_people}"/>
            	</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">
            	<tags:text-item label="用車日期">
	            	<func:minguo value="${caseData.carBookingDetailData.using_date}"/>
	            	<form:hidden path="using_date" value="${caseData.carBookingDetailData.using_date}"/>
            	</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="用車時間">
		            	<c:out value="${caseData.carBookingDetailData.using_time_s}"/>~
		            	<c:out value="${caseData.carBookingDetailData.using_time_e}"/>
            		</tags:text-item>
            	</div>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="用車區間">
		            	<c:out value="${caseData.carBookingDetailData.usingStr}"/>
            		</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
            <form:label path="carno" cssClass="col-form-label star">派車車號：</form:label>
            <form:select path="carno" cssClass="form-control add">
            <form:option value=""></form:option>
            <c:forEach items="${caseData.carnoList}" var="item" varStatus="status">
                  <form:option value="${item.carno1}-${item.carno2}">${item.carno1}-${item.carno2}</form:option>
            </c:forEach>
            </form:select>
            <c:if test="${empty caseData.carBookingList && caseData.showEmptyStr}">
            	<div class="pt-2 text-danger pl-2">本車該用車時間尚未有人預約使用</div>
            </c:if>
            
            <c:if test="${not empty caseData.carBookingList}">
            	<c:choose>
            		<c:when test="${caseData.timeMK eq 'Y'}">
            			<div class="pt-2 text-danger pl-2">本車該用車時間已有人預約</div>
            		</c:when>
            		<c:otherwise>
            			<div class="pt-2 text-danger  pl-2">本車該用車時間無人預約</div>
            		</c:otherwise>
            	</c:choose>
            </c:if>
            </tags:form-row>
            <form:hidden path="timeMK" value="${caseData.timeMK}"/>
            
            <c:if test="${not empty caseData.carBookingList}">
            	<table  class="table table-hover m-2">
            		<thead>
            			<th style="width: 10%">派車單號</th>
            			<th style="width: 10%">申請人</th>
            			<th style="width: 10%">申請單位</th>
            			<th style="width: 10%">用車時間起迄</th>
            			<th style="width: 60%">用車事由</th>
            		</thead>
		            <c:forEach items="${caseData.carBookingList}" var="item" varStatus="status">
            		<tbody>
            		<tr>
		                 <td><c:out value="${item.applyid }"/></td>
		                 <td class="text-left"><c:out value="${item.apply_user}"/></td>
		                 <td class="text-left"><c:out value="${item.apply_dept}"/></td>
		                 <td><c:out value="${item.using_time_s}"/>〜<c:out value="${item.using_time_e}"/></td>
		                 <td class="text-left">
						<span class="ellipsisStr">
	                 		<c:out value="${item.apply_memo}"/>
	                 	</span>
            		</tr>
            		</tbody>
		            </c:forEach>
            	</table>
            </c:if>
            
            <c:if test="${caseData.showButton}">
                <div class="row vertical-align-center d-flex">
	            		<label class="col-form-label">併單：</label>
	            		<div class="mt-2">
			            	<label><input type="radio"  name="merge" value="Y" />是</label>
			            	<label><input type="radio"  name="merge" value="N" checked />否</label>
		            	</div>
	            	<label class="col-form-label mergeApplyid">併單單號：</label>
	        		<form:select path="mergeApplyid" cssClass="form-control mergeApplyid">
					<form:option value="">請選擇</form:option>
						<c:forEach items="${caseData.carBookingList}" var="item" varStatus="status">
							<form:option value="${item.applyid}">${item.applyid}</form:option>
						</c:forEach>
					</form:select>
	           	</div>
	           	<div class="row vertical-align-center d-flex  mergeReason mb-2">
		            <label class="col-form-label mergeReason">併單原因：</label>
	            	<form:input path="mergeReason" cssClass="add form-control mergeReason" maxlength="100" />
				</div>
            	<div class="row vertical-align-center d-flex">
            	<label class="col-form-label">派車結果選項：</label>
	        	<form:select path="status" cssClass="form-control">
					<form:option value="">請選擇</form:option>
						<c:forEach items="${caseData.carprostsList}" var="item" varStatus="status">
							<form:option value="${item.codeno}">${item.codeno}-${item.codename}</form:option>
						</c:forEach>
				</form:select>
	           	</div>
	         </c:if>   	
        </form:form>
 		</fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
        	
        	if($('#timeMK').val() !==''){
        		changeOption();
        	}

			$('.mergeReason').hide();
			$('.mergeApplyid').hide();
        	
        	$('#carno').change(function(e){
        		if($('#carno').val()!==''){
        			$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_getUsingData.action" />').submit();
        		}
        	});
        	
        	$("input[name='merge']").change(function(){
        		changeOption();
        		if($("input[name='merge']:checked").val()=='Y'){
        			$('.mergeReason').show();
        			$('.mergeApplyid').show();
        		} else if($("input[name='merge']:checked").val()=='N'){
        			$('.mergeReason').hide();
        			$('.mergeApplyid').hide();
        			$('#mergeReason').val('');
        			$('#mergeApplyid').val('');
        		}
        	});
        	
        	function changeOption(){
        		$("#carprocess_status>option").show();
        		var num; 
        		var timeMK = $('#timeMK').val();
        		var merge = $("input[name='merge']:checked").val();
        		if(timeMK=='Y' && merge=='N'){num = [1,2,4,5];}
        		if(timeMK=='N' && merge=='N'){num = [4,5];}
        		if(merge=='Y'){num = [1,2];}
        		num.forEach(e => $("#carprocess_status>option").eq(e).hide());
        	}


            
            $('#btnReturn').click(function(){
           		$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_back.action" />').submit();
            });
            
            
            $('.btnDetail').click(function(){
            	$('#applyno').val($(this).val());
           		$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_detail.action" />').submit();
            });
            
            $('#btnSubmit').click(function(){
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_updateAll.action" />').submit();
            });
            
         });

</script>
</jsp:attribute>
</tags:layout>