<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w060Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
     <c:choose>
        <c:when test="${caseData.carType == 'N' && caseData.carbooking.carprocess_status == 'F'}">
	  	</c:when>
	  	<c:otherwise>
	  		<tags:button id="btnSubmit">確認<i class="fas fa-user-check"></i>
    </tags:button>
	  	</c:otherwise>
	</c:choose>
    
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="里程鍵入">
		<form:form id="eip07w060Form" name="eip07w060Form" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
				<div class="col-12 col-md-6">
            		<form:label path="keyinYm" cssClass="col-form-label">鍵入年月：</form:label>
            		<func:minguo value="${caseData.keyinYm}"/>
            	</div> 
			</tags:form-row>
            <tags:form-row>
            	<div class="col-12 col-md-6">
            		<form:label path="carType" cssClass="col-form-label">首長專用車：</form:label>
            		<c:out value="${caseData.carType}"/>
           			<c:if test = "${caseData.carType == 'Y'}">
	           		是
	           		</c:if>
	           		<c:if test = "${caseData.carType == 'N'}">
	           		否
	           		</c:if>
            	</div>
            	<c:if test = "${caseData.carType == 'Y'}">
	            	<div class="col-12 col-md-6">
	            		<form:label path="btmk" cssClass="col-form-label">出差排程：</form:label>
	            		<c:out value="${caseData.btmk}"/>
	           			<c:if test = "${caseData.btmk == 'Y'}">
		           		是
		           		</c:if>
		           		<c:if test = "${caseData.btmk == 'N'}">
		           		否
		           		</c:if>
	            	</div>           	
            	</c:if>
            </tags:form-row>
            
            <c:choose>
            	<c:when test="${caseData.carType == 'Y' && caseData.btmk =='N'}">
            	</c:when>
            	<c:otherwise>
	            	<tags:form-row>
		            	<c:if test = "${caseData.btmk != 'Y'}">
			            	<div class="col-12 col-md-6">
			            		<form:label path="carbooking.applyid" cssClass="col-form-label">派車單號：</form:label>
			            		<c:out value="${caseData.carbooking.applyid}"/>
			            	</div> 
		            	</c:if>
		            	<c:if test = "${caseData.btmk == 'Y'}">
				           	<form:label path="carbooking.applyid" cssClass="col-form-label">派車單號：</form:label>
				           	<div class="col-12 col-md-6  form-in-line">
				           		<form:input path="carbooking.applyid" cssClass="form-control bossYapplyid" size="13" maxlength="13"/>
				           	</div>  
		            	</c:if>
	                </tags:form-row>
            	</c:otherwise>
            </c:choose>
            
            <tags:form-row>
            	<div class="col-12 col-md-6">
            		<form:label path="carbooking.carno1" cssClass="col-form-label">車牌號碼：</form:label>
            		<c:out value="${caseData.carbooking.carno1}"/>-<c:out value="${caseData.carbooking.carno2}"/>
            	</div>
            	<div class="col-12 col-md-6 ">
            		<form:label path="carbooking.name" cssClass="col-form-label">駕駛人：</form:label>
            		<c:out value="${caseData.carbooking.name}"/>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<c:if test = "${caseData.carType == 'N'}">
	                <div class="col-12 col-md-6">
	                	<form:label path="carbooking.apply_memo" cssClass="col-form-label">用車事由：</form:label>
	                    <c:out value="${caseData.carbooking.apply_memo}"/>
	                </div>
		        </c:if>
            	<c:if test = "${caseData.carType == 'Y'}">
            		 <form:label path="carbooking.apply_memo" cssClass="col-form-label">用車事由：</form:label>
	                 <div class="col-12 col-md-6 form-in-line">
	                    <form:input path="carbooking.apply_memo" cssClass="form-control" size="50" maxlength="50"/>
	                 </div>
            	</c:if>
            </tags:form-row>
            <tags:form-row>
            	<c:if test = "${caseData.carType == 'N'}">
	                <div class="col-12 col-md-6">
	                	<form:label path="carbooking.using_date" cssClass="col-form-label">用車日期：</form:label>
	                    <func:minguo value="${caseData.carbooking.using_date}"/>
	                </div>
            	</c:if>
            	<c:if test = "${caseData.carType == 'Y' && caseData.btmk == 'Y'}">
            		 <form:label path="carbooking.using_date" cssClass="col-form-label">用車日期：</form:label>
	                 <div class="col-12 col-md-6">
	                	<form:input path="carbooking.using_date" cssClass="add form-control dateTW num_only usingdate" size="7" maxlength="7" onchange="usingdatechange()"/>
	                 </div>
            	</c:if>
            </tags:form-row>
            <tags:form-row>
            	<c:if test = "${caseData.carType == 'N'}">
					<div class="col-12 col-md-2">
						<form:label path="carbooking.apply_memo" cssClass="col-form-label">用車時間起：</form:label>
	                    <c:out value="${caseData.carbooking.using_time_s}"/>
	            	</div>
					<div class="col-12 col-md-2">
	                    <form:label path="carbooking.apply_memo" cssClass="col-form-label">~迄：</form:label>
	                    <c:out value="${caseData.carbooking.using_time_e}"/>
	            	</div>
            	</c:if>
            	<c:if test = "${caseData.carType == 'Y' && caseData.btmk == 'Y'}">
            		<form:label path="usehms" cssClass="col-form-label star">用車時間起：</form:label>
	            	<div class="col-12 col-md-6 form-inline">
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
            	<div class="col-12 col-md">
            		<form:label path="carbooking.destination" cssClass="col-form-label">目的地：</form:label>
            		<c:out value="${caseData.carbooking.destination}"/>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-12 col-md">
            		<form:label path="carbooking.carprocess_status" cssClass="col-form-label">表單狀態：</form:label>
            		<c:out value="${caseData.carbooking.carprocess_status}"/>-<func:code codekind = 'CARPROCESSSTATUS' codeno = '${caseData.carbooking.carprocess_status}' />
            	</div>
            </tags:form-row>
            <c:choose>
            	<c:when test="${caseData.carType == 'Y' && caseData.btmk =='N'}">
            	</c:when>
            	<c:otherwise>
		            <tags:form-row>
		            	<div class="col-12 col-md">
						<tags:text-item label="[使用記錄登入]" ></tags:text-item>
						</div>
		            </tags:form-row>
		            <tags:form-row>
		            	<c:choose>
		            		<c:when test="${caseData.carType == 'N' && caseData.carbooking.carprocess_status == 'F'}">
								<div class="col-12 col-md-2">
									<form:label path="caruserec.driver_time_s" cssClass="col-form-label">開出時間：</form:label>
									<c:out value="${caseData.caruserec.driver_time_s}"/>
								</div>
								<div class="col-12 col-md-2">
									<form:label path="caruserec.driver_time_e" cssClass="col-form-label">到達時間：</form:label>
									<c:out value="${caseData.caruserec.driver_time_e}"/>
								</div>
						  	</c:when>
						  	<c:otherwise>
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
						  	</c:otherwise>
						</c:choose>
		
		            </tags:form-row>
		            <tags:form-row>
		            	<c:choose>
		            		<c:when test="${caseData.carType == 'N' && caseData.carbooking.carprocess_status == 'F'}">
								<div class="col-12 col-md-6">
									<form:label path="caruserec.drive_road" cssClass="col-form-label star">行駛路線：</form:label>
									<c:out value="${caseData.caruserec.drive_road}"/>
								</div>
						  	</c:when>
						  	<c:otherwise>
						  		<form:label path="road" cssClass="col-form-label star">行駛路線：</form:label>
				                <div class="col-12 col-md-6  form-inline">
				                    <form:input path="road" cssClass="form-control" size="50" maxlength="50"/>
				                </div>
						  	</c:otherwise>
						</c:choose>
		
		            </tags:form-row>
            	</c:otherwise>
            </c:choose>

			<c:choose>
				<c:when test="${caseData.carType == 'Y' && caseData.btmk =='N'}">
					<tags:form-row>
	            	<div class="table-responsive">	           
						<table class="table">
							<thead data-orderable="true">
								<tr>
									<th class="text-center" width=10%>用車日期</th>
									<th class="text-center" width=25%>用車時間起</th>
									<th class="text-center" width=25%>用車時間迄</th>
									<th class="text-center" width=10%>出場公里數</th>
									<th class="text-center" width=10%>回場公里數</th>
									<th class="text-center" width=10%>行駛公里數</th>
									<th class="text-center" width=10%>耗油公里數</th>
								</tr>
							</thead>
						    <tbody>
		                        <c:forEach items="${caseData.bosscarMonthlyList}" var="item" varStatus="status">
							        <tr>
							        	<td class="text-center">
							        		<func:minguo value="${item.using_date}"/>
							        	</td>
							        	<td class="text-center">
							        		<div class="col-12 col-md form-inline">
											 <form:select  path="bosscarMonthlyList[${status.index}].startuseH" cssClass="form-control">
							                   	<form:option value=""></form:option>
							                       <c:forEach var="hour" items="${caseData.hourList}" varStatus="timestatus">
							                           <form:option value="${hour}"><c:out value="${hour}"/></form:option>
							                       </c:forEach>
							                    </form:select>
											 ：
											 <form:select  path="bosscarMonthlyList[${status.index}].startuseM" cssClass="form-control">
							                   	<form:option value=""></form:option>
							                       <c:forEach var="minute" items="${caseData.minuteList}" varStatus="timestatus">
							                           <form:option value="${minute}"><c:out value="${minute}"/></form:option>
							                       </c:forEach>
							                 </form:select>
							                </div>
							        	</td>
							        	<td class="text-center">
							        		<div class="col-12 col-md form-inline">
							        		 <form:select path="bosscarMonthlyList[${status.index}].enduseH" cssClass="form-control">
							                   	<form:option value=""></form:option>
							                       <c:forEach var="hour" items="${caseData.hourList}" varStatus="timestatus">
							                           <form:option value="${hour}"><c:out value="${hour}"/></form:option>
							                       </c:forEach>
							                    </form:select>
											 ：
											 <form:select path="bosscarMonthlyList[${status.index}].enduseM" cssClass="form-control">
							                   	<form:option value=""></form:option>
							                       <c:forEach var="minute" items="${caseData.minuteList}" varStatus="timestatus">
							                           <form:option value="${minute}"><c:out value="${minute}"/></form:option>
							                       </c:forEach>
							                 </form:select>
							                </div>
							        	</td>
							        	<td class="text-center">
											<form:input path="bosscarMonthlyList[${status.index}].milageStart" cssClass="form-control num_only milageStart${status.index}" onchange="calListMilage(${status.index})" size="10" maxlength="10"/>
							        	</td>
							        	<td class="text-center">
											<form:input path="bosscarMonthlyList[${status.index}].milageEnd" cssClass="form-control num_only milageEnd${status.index}" onchange="calListMilage(${status.index})" size="10" maxlength="10"/>
							        	</td>
										<td class="text-center">
											<form:input path="bosscarMonthlyList[${status.index}].milage" cssClass="form-control num_only milage${status.index}" size="10" maxlength="10"/>
							            </td>
							            <td class="text-center">	
											<form:input path="bosscarMonthlyList[${status.index}].gasUsed" cssClass="form-control float_only" size="10" maxlength="10"/>
		 	                        	</td>
							        </tr>
						        </c:forEach>
						        	<tr>
						        		<td class="text-center">
						        			合計
		 	                        	</td>
		 	                        	<td class="text-center">
		 	                        	</td>
		 	                        	<td class="text-center">
		 	                        	</td>
		 	                        	<td class="text-center">
		 	                        	</td>
		 	                        	<td class="text-center">
		 	                        	</td>
		 	                        	<td class="text-center">
		 	                        	</td>
		 	                        	<td class="text-center">
		 	                        	</td>
						        	</tr>
							</tbody> 
						</table>  
					</div>
	                </tags:form-row>
				</c:when>
				<c:otherwise>
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
										<c:choose>
							           		<c:when test="${caseData.carType == 'N' && caseData.carbooking.carprocess_status == 'F'}">
							           			<c:out value="${caseData.caruserec.milage_start}"/>
										  	</c:when>
										  	<c:otherwise>
										  		<form:input path="milageStart" cssClass="form-control num_only" size="10" maxlength="10"/>
										  	</c:otherwise>
										</c:choose>
						        		
						        	</td>
						        	<td class="text-center">
						        		<c:choose>
							           		<c:when test="${caseData.carType == 'N' && caseData.carbooking.carprocess_status == 'F'}">
							           			<c:out value="${caseData.caruserec.milage_end}"/>
										  	</c:when>
										  	<c:otherwise>
										  		<form:input path="milageEnd" cssClass="form-control num_only" size="10" maxlength="10"/>
										  	</c:otherwise>
										</c:choose>
						        	</td>
									<td class="text-center">
										<c:choose>
							           		<c:when test="${caseData.carType == 'N' && caseData.carbooking.carprocess_status == 'F'}">
							           			<c:out value="${caseData.caruserec.milage}"/>
										  	</c:when>
										  	<c:otherwise>
										  		<form:input path="milage" cssClass="form-control num_only" size="10" maxlength="10"/>
										  	</c:otherwise>
										</c:choose>
						            </td>
						            <td class="text-center">	
						            	<c:choose>
							           		<c:when test="${caseData.carType == 'N' && caseData.carbooking.carprocess_status == 'F'}">
							           			<c:out value="${caseData.caruserec.gas_used}"/>
										  	</c:when>
										  	<c:otherwise>
										  		<form:input path="gasUsed" cssClass="form-control float_only" size="10" maxlength="10"/>	
										  	</c:otherwise>
										</c:choose>
	 	                        	</td>
						        </tr>
							</tbody> 
						</table>  
					</div>
	                </tags:form-row>
				</c:otherwise>
			</c:choose>
            
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
            
            $('#btnReturn').click(function(){
           		$('#eip07w060Form').attr('action', '<c:url value="/Eip07w060_enter.action" />').submit();
            });
            
            $('#btnSubmit').click(function(){
            	$('#eip07w060Form').attr('action', '<c:url value="/Eip07w060_update.action" />').submit();
            });
            
			$('#milageStart').change(function(){
				calmilage();
			});
			$('#milageEnd').change(function(){
				calmilage();
			});	
			
			<c:if test = "${caseData.carType == 'Y'}">
				$('#startuseH').change(function(){
					$('#startH').val($('#startuseH').val());
				});	
				$('#startuseM').change(function(){
					$('#startM').val($('#startuseM').val());
				});	
				$('#enduseH').change(function(){
					$('#endH').val($('#enduseH').val());
				});	
				$('#enduseM').change(function(){
					$('#endM').val($('#enduseM').val());
				});	
			</c:if>
			
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
        
        function usingdatechange(){
			var last3carno = '<c:out value="${caseData.last3carno}"/>';	
			var ymdString = $('.usingdate').val().replaceAll('/','');
			if(ymdString.length < 8){
				var ystring = parseInt(ymdString.substring(0,3))+1911;
				var mdstring = ymdString.substring(3);
				ymdString = ystring + mdstring;
			}
			$('.bossYapplyid').val( "DC" + ymdString + last3carno);
        }
        
        function calListMilage(dataindex){
        	var smilstring = '.milageStart'+dataindex;
        	var emilstring = '.milageEnd'+dataindex;
        	var milagestring = '.milage'+dataindex;
        	
        	var smil = $(smilstring).val();
        	var emil = $(emilstring).val();
        	
        	if(smil && emil){
        		if((emil - smil) > 0){
        			$(milagestring).val(emil - smil);
        		}else{
        			$(milagestring).val('');
        		}
        	}else{
        		$(milagestring).val('');
        	}
        }
        

</script>
</jsp:attribute>
</tags:layout>