<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w060Controller).CASE_KEY" />
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
    <tags:fieldset legend="里程鍵入">
		<form:form id="eip07w060Form" name="eip07w060Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<div class="col-6 col-md form-inline">
            		<tags:text-item label="首長專用車"><c:out value="${caseData.carType}"/></tags:text-item>
            	</div>
            	<c:if test = "${caseData.carType == 'Y'}">
            	<form:label path="btmk" cssClass="col-form-label">出差排程：</form:label>
            	<div class="col-6 col-md form-inline">
            		<c:out value="${caseData.btmk}"/>
            	</div>           	
            	</c:if>
            </tags:form-row>
            <tags:form-row>
				<div class="col-6 col-md form-inline">
            		<tags:text-item label="派車單號"><c:out value="${caseData.carbooking.applyid}"/></tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
				<div class="col-6 col-md form-inline">
            		<tags:text-item label="車牌號碼"><c:out value="${caseData.carbooking.carno1}"/><c:out value="${caseData.carbooking.carno2}"/></tags:text-item>
            	</div>
            	<form:label path="carbooking.name" cssClass="col-form-label">駕駛人：</form:label>
            	<div class="col-6 col-md form-inline">
            		<c:out value="${caseData.carbooking.name}"/>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<c:if test = "${caseData.carType == 'N'}">
					<div class="col-6 col-md form-inline">
		           		<tags:text-item label="用車事由"><c:out value="${caseData.carbooking.apply_memo}"/></tags:text-item>
		           	</div>
		        </c:if>
            	<c:if test = "${caseData.carType == 'Y'}">
	       			 <form:label path="carbooking.apply_memo" cssClass="col-form-label">用車事由：</form:label>
	                 <div class="col-6 col-md form-inline">
	                    <form:input path="carbooking.apply_memo" cssClass="form-control" size="50" maxlength="50"/>
	                 </div>
            	</c:if>
            </tags:form-row>
            <tags:form-row>
            	<c:if test = "${caseData.carType == 'N'}">
					<div class="col-3 col-md form-inline">
	            		<tags:text-item label="用車日期"><func:minguo value="${caseData.carbooking.using_date}"/></tags:text-item>
	            	</div>
            	</c:if>
            	<c:if test = "${caseData.carType == 'Y'}">
	       			 <form:label path="carbooking.using_date" cssClass="col-form-label">用車日期：</form:label>
	                 <div class="col-6 col-md form-inline">
	                    <form:input path="carbooking.using_date" cssClass="form-control num_only" size="50" maxlength="50"/>
	                 </div>
            	</c:if>
            </tags:form-row>
            <tags:form-row>
            	<c:if test = "${caseData.carType == 'N'}">
					<div class="col-6 col-md form-inline">
	            		<tags:text-item label="用車時間起"><c:out value="${caseData.carbooking.using_time_s}"/></tags:text-item>        
	            		<tags:text-item label="~迄"><c:out value="${caseData.carbooking.using_time_e}"/></tags:text-item>
	            	</div>
	            	<div class="col-6 col-md form-inline">
	            	</div>	
            	</c:if>
            	<c:if test = "${caseData.carType == 'Y'}">
	            	<form:label path="usehms" cssClass="col-form-label">用車時間起：</form:label>
	            	<div class="col-12 col-md form-inline">
					   <form:select  path="startuseH" cssClass="form-control">
	                   	<form:option value=""></form:option>
	                       <c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
	                           <form:option value="${hour}"><c:out value="${hour}"/></form:option>
	                       </c:forEach>
	                   </form:select>
					 <span class="input-group-text px-1">：</span>
					 <form:select  path="startuseM" cssClass="form-control">
	                   	<form:option value=""></form:option>
	                       <c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
	                           <form:option value="${minute}"><c:out value="${minute}"/></form:option>
	                       </c:forEach>
	                    </form:select>
					 <form:label path="usehme" cssClass="col-form-label">迄：</form:label>
					 <form:select path="enduseH" cssClass="form-control">
	                   	<form:option value=""></form:option>
	                       <c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
	                           <form:option value="${hour}"><c:out value="${hour}"/></form:option>
	                       </c:forEach>
	                    </form:select>
					 <span class="input-group-text px-1">:</span>
					 <form:select path="enduseM" cssClass="form-control">
	                   	<form:option value=""></form:option>
	                       <c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
	                           <form:option value="${minute}"><c:out value="${minute}"/></form:option>
	                       </c:forEach>
	                    </form:select>
	                </div>
            	</c:if>
            </tags:form-row>
            <tags:form-row>
                <form:label path="carbooking.destination" cssClass="col-form-label">目的地：</form:label>
            	<div class="col-6 col-md form-inline">
            		<c:out value="${caseData.carbooking.destination}"/>
            	</div>
            </tags:form-row>
            <tags:form-row>
               	<form:label path="carbooking.carprocess_status" cssClass="col-form-label">表單狀態：</form:label>
            	<div class="col-6 col-md form-inline">
            		<c:out value="${caseData.carbooking.carprocess_status}"/>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<form:label path="hms" cssClass="col-form-label">開出時間：</form:label>
            	<div class="col-12 col-md form-inline">
				   <form:select  path="startH" cssClass="form-control">
                   	<form:option value=""></form:option>
                       <c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
                           <form:option value="${hour}"><c:out value="${hour}"/></form:option>
                       </c:forEach>
                   </form:select>
				 <span class="input-group-text px-1">：</span>
				 <form:select  path="startM" cssClass="form-control">
                   	<form:option value=""></form:option>
                       <c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
                           <form:option value="${minute}"><c:out value="${minute}"/></form:option>
                       </c:forEach>
                    </form:select>
				 <form:label path="hme" cssClass="col-form-label">到達時間：</form:label>
				 <form:select path="endH" cssClass="form-control">
                   	<form:option value=""></form:option>
                       <c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
                           <form:option value="${hour}"><c:out value="${hour}"/></form:option>
                       </c:forEach>
                    </form:select>
				 <span class="input-group-text px-1">:</span>
				 <form:select path="endM" cssClass="form-control">
                   	<form:option value=""></form:option>
                       <c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
                           <form:option value="${minute}"><c:out value="${minute}"/></form:option>
                       </c:forEach>
                    </form:select>
                </div>
            </tags:form-row>
            <tags:form-row>
				 <form:label path="road" cssClass="col-form-label">行駛路線：</form:label>
                 <div class="col-6 col-md form-inline">
                    <form:input path="road" cssClass="form-control" size="50" maxlength="50"/>
                 </div>
            </tags:form-row>
            <tags:form-row>
            	<div class="table-responsive">	           
					<table class="table">
						<thead data-orderable="true">
							<tr>
								<th class="text-center" width=20%>出場公里數</th>
								<th class="text-center" width=20%>回場公里數</th>
								<th class="text-center" width=20%>行駛公里數</th>
								<th class="text-center" width=20%>耗油公里數</th>
							</tr>
						</thead>
					    <tbody>
					        <tr>
					        	<td class="text-center">
					        		<form:input path="milageStart" cssClass="form-control num_only" size="10" maxlength="10"/>
					        	</td>
					        	<td class="text-center">
					        		<form:input path="milageEnd" cssClass="form-control num_only" size="10" maxlength="10"/>
					        	</td>
								<td class="text-center">
									<form:input path="milage" cssClass="form-control num_only" size="10" maxlength="10"/>	
					            </td>
					            <td class="text-center">	
					            	<form:input path="gasUsed" cssClass="form-control num_only" size="10" maxlength="10"/>					           
 	                        	</td>
					        </tr>
						</tbody> 
					</table>  
				</div>
            </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
//         	$('#selectFile').click(function(e){
//         		$('input[name="agree"]')[0].checked = true;
//         	});
            
            $('#btnReturn').click(function(){
           		$('#eip07w060Form').attr('action', '<c:url value="/Eip07w060_enter.action" />').submit();
            });
            
            
//             $('.btnDetail').click(function(){
//             	$('#applyno').val($(this).val());
//            		$('#eip07w050Form').attr('action', '<c:url value="/Eip07w050_detail.action" />').submit();
//             });
            
            $('#btnSubmit').click(function(){
            	$('#eip07w060Form').attr('action', '<c:url value="/Eip07w060_update.action" />').submit();
            });
            
//             //全選的切換
//             var flag = false;
//             $("#dataListTabcheckAllP").click(function(){
//             	$("input[name^='dataList']:checkbox").each(function(){
//             		$(this).prop('checked',!flag);
//             	});
//                 flag = !flag;
//             });
			$('#milageStart').change(function(){
				calmilage();
			});
			$('#milageEnd').change(function(){
				calmilage();
			});
         });

        function calmilage(){
        	var smil = $('#milageStart').val();
        	var emil = $('#milageEnd').val();
        	
        	if(smil && emil){
        		if((emil - smil) > 0){
        			$('#milage').val(emil - smil);
        		}else{
        			$('#milage').val('');
        		}
        	}else{
        		$('#milage').val('');
        	}
        }
</script>
</jsp:attribute>
</tags:layout>