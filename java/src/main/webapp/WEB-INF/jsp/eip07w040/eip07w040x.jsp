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
    <tags:fieldset>
		<form:form id="eip07w040Form" name="eip07w040Form" modelAttribute="${caseKey}" method="POST">
		    <tags:form-row>
            	<div class="col-4 col-md-4">派車單號：<c:out value="${caseData.carBookingDetailData.applyid}"/></div>
            </tags:form-row>
            <tags:form-row>
            	<form:hidden path="using" value="${caseData.carBookingDetailData.using}"/>
            	<div class="col-4 col-md-4">申請人：<c:out value="${caseData.carBookingDetailData.apply_user}"/></div>
            	<div class="col-4 col-md-4">申請單位：<c:out value="${caseData.carBookingDetailData.apply_dept}"/></div>
            	<div class="col-4 col-md-4">車輛種類：<c:out value="${caseData.carBookingDetailData.apply_car_type}"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">用車事由：<c:out value="${caseData.carBookingDetailData.apply_memo}"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">目的地：<c:out value="${caseData.carBookingDetailData.destination}"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">人數：<c:out value="${caseData.carBookingDetailData.num_of_people}"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">用車日期：<func:minguo value="${caseData.carBookingDetailData.using_date}"/>
            	<form:hidden path="using_date" value="${caseData.carBookingDetailData.using_date}"/>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">用車時間：<c:out value="${caseData.carBookingDetailData.using_time_s}"/>~<c:out value="${caseData.carBookingDetailData.using_time_e}"/></div>
            </tags:form-row>
            <tags:form-row>
            <form:label path="carno" cssClass="col-form-label star">派車車號：</form:label>
            <form:select path="carno" cssClass="form-control add">
            <form:option value=""></form:option>
            <c:forEach items="${caseData.carnoList}" var="item" varStatus="status">
                  <form:option value="${item.carno1}-${item.carno2}">${item.carno1}-${item.carno2}</form:option>
            </c:forEach>
            </form:select>
            </tags:form-row>
            <form:hidden path="timeMK" value="${caseData.timeMK}"/>
            
            <c:if test="${not empty caseData.carBookingList}">
            	<c:choose>
            		<c:when test="${caseData.timeMK eq 'Y'}">
            			該用車時間已有人預約
            		</c:when>
            		<c:otherwise>
            			該用車時間無人預約
            		</c:otherwise>
            	</c:choose>
            	<table  class="table table-hover m-2">
            		<thead>
            			<th  style="width: 10%">派車單號</th>
            			<th  style="width: 10%">申請人</th>
            			<th  style="width: 10%">申請單位</th>
            			<th  style="width: 10%">用車時間起迄</th>
            			<th  style="width: 60%">用車事由</th>
            		</thead>
		            <c:forEach items="${caseData.carBookingList}" var="item" varStatus="status">
            		<tbody>
            		<tr>
		                 <td><c:out value="${item.applyid }"/></td>
		                 <td class="text-left"><c:out value="${item.apply_user }"/></td>
		                 <td class="text-left"><c:out value="${item.apply_dept }"/></td>
		                 <td><c:out value="${item.using_time_s }"/>~<c:out value="${item.using_time_e}"/></td>
		                 <td class="text-left"><c:out value="${item.apply_memo }"/></td>
            		</tr>
            		</tbody>
		            </c:forEach>
            	</table>
            </c:if>
            
            <c:if test="${empty caseData.carBookingList && caseData.showEmptyStr}">
            今日尚未有人預約使用
            </c:if>
            
            <c:if test="${caseData.showButton}">
                <div class="row vertical-align-center d-flex">
					<div class="col-3">
	            		<label class="col-form-label">併單：</label>
		            	<label><input type="radio" name="merge" value="Y"/>是</label>
		            	<label><input type="radio" name="merge" value="N"/>否</label>
					</div>
					<div class="col-9 d-flex">
		            	<label class="col-form-label">併單原因：</label>
	            		<form:input path="mergeReason" cssClass="add form-control" maxlength="100"/>      
					</div>
	           	</div>
            	<div class="row vertical-align-center d-flex">
            	<label class="col-form-label">派車結果選項：</label>
	        	<form:select path="carprocess_status" cssClass="form-control">
					<form:option value="">請選擇</form:option>
						<c:forEach items="${caseData.carprostsList}" var="item" varStatus="status">
							<form:option value="${item.codeno}">${item.codeno}-${item.codename}</form:option>
						</c:forEach>
				</form:select>
	           	</div>
	         </c:if>
	           	
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
        	
        	$('#carno').change(function(e){
        		if($('#carno').val()!==''){
        			$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_getUsingData.action" />').submit();
        		}
        	});
        	
        	$("input[name='merge']").change(function(){
        		changeOption();
        		if($("input[name='merge']:checked").val()=='Y'){
        			$('#mergeReason').attr("disabled",false);
        		} else if($("input[name='merge']:checked").val()=='N'){
        			$('#mergeReason').attr("disabled",true);
        		}
        	});
        	
        	function changeOption(){
        		$("#carprocess_status>option").attr("disabled", false);
        		var num; 
        		var timeMK = $('#timeMK').val();
        		var merge = $("input[name='merge']:checked").val();
        		if(timeMK=='Y' && merge=='N'){num = [1,2,4,5];}
        		if(timeMK=='N' && merge=='N'){num = [4,5];}
        		if(merge=='Y'){num = [1,2];}
        		
        		num.forEach(e => $("#carprocess_status>option").eq(e).attr("disabled", true));
        	}


            
            $('#btnReturn').click(function(){
           		$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_enter.action" />').submit();
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