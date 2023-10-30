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
		<form:form id="eip07w040Form" name="eip07w040Form" modelAttribute="${caseKey}" method="POST">
     	<fieldset>
      	<legend>待處理派車明細</legend>
		    <tags:form-row>
		        <tags:text-item label="派車單號">
                    <c:out value="${caseData.carBookingDetailData.applyid}"/>
                </tags:text-item>
            </tags:form-row>
            <tags:form-row>
            	<form:hidden path="using" value="${caseData.carBookingDetailData.using}"/>
            	<form:hidden path="num_of_people" value="${caseData.carBookingDetailData.num_of_people}"/>
            	<div class="col-4 col-md-4">
	            	<tags:text-item label="申請人">
	            		<func:username userid="${caseData.carBookingDetailData.apply_user}"/>
	            	</tags:text-item>
            	</div>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="申請單位">
            			<func:dept deptid="${caseData.carBookingDetailData.apply_dept}"/>
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
            	<div class="col-4 col-md-7 col-sm-8">
            		<tags:text-item label="申請用車時間">
	            		<func:timeconvert value="${caseData.carBookingDetailData.using_time_s}"/>~
	            		<func:timeconvert value="${caseData.carBookingDetailData.using_time_e}"/>
            		</tags:text-item>
            	</div>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="申請用車區間">
		            	<c:out value="${caseData.carBookingDetailData.usingStr}"/>
		            	<form:hidden path="approve_using_timeStr" value="${caseData.approve_using_timeStr}"/>
            		</tags:text-item>
            	</div>
            </tags:form-row>

		    <tags:form-row>
		    <div class="d-flex align-items-center col-4  col-md-7 col-sm-8">
		    	  <form:label path="starH" cssClass="col-form-label star">核定用車時間：</form:label>
				  <form:select id="starH"  path="starH" cssClass="form-control timeselect">
                  	<form:option value=""></form:option>
                      <c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
                          <form:option value="${hour}"><c:out value="${hour}"/></form:option>
                      </c:forEach>
                  </form:select>
                  ：
                  <form:select id="starM"   path="starM" cssClass="form-control timeselect">
                  	<form:option value=""></form:option>
                      <c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
                          <form:option value="${minute}"><c:out value="${minute}"/></form:option>
                      </c:forEach>
                  </form:select>
                  ~
                  <form:select   path="endH" cssClass="form-control timeselect">
                  	<form:option value=""></form:option>
                      <c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
                          <form:option value="${hour}"><c:out value="${hour}"/></form:option>
                      </c:forEach>
                  </form:select>
                  ：
                  <form:select id="endM"   path="endM" cssClass="form-control timeselect">
                  	<form:option value=""></form:option>
                      <c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
                          <form:option value="${minute}"><c:out value="${minute}"/></form:option>
                      </c:forEach>
                  </form:select>
		    </div>
		    <div class="col-4 col-md-4">
            		<tags:text-item label="核定用車區間">
		            <div class="approve_using_time_s"></div>
					<form:hidden path="approve_using_time_s" cssClass="form-control" maxlength="4" readOnly="true" size="3"/>
		            <form:hidden path="approve_using_time_e" cssClass="form-control" maxlength="4" readOnly="true" size="3"/>
            		</tags:text-item>
            		</div>

       

            </tags:form-row> 

            
            <tags:form-row>
            <div class="col-4 col-md-4 d-flex  align-items-center">
            <form:label path="carno" cssClass="col-form-label star">派車車號：</form:label>
            
            <form:select path="carno" cssClass="form-control add">
            <form:option value=""></form:option>
            <c:forEach items="${caseData.carnoList}" var="item" varStatus="status">
                  <form:option value="${item.carno1}-${item.carno2}">${item.carno1}-${item.carno2}</form:option>
            </c:forEach>
            </form:select>
            </div>
            <c:if test="${empty caseData.carBookingList && caseData.showEmptyStr}">
            	<div class="pt-2 text-danger pl-2">本車該用車時間尚未有人預約使用</div>
            </c:if>
            
            <c:if test="${not empty caseData.carBookingList}">
            	<c:choose>
            		<c:when test="${caseData.timeMK eq 'Y'}">
            			<div class="pt-2 text-danger pl-2 mb-2">本車該用車時間已有人預約</div>
            		</c:when>
            		<c:otherwise>
            			<div class="pt-2 text-danger  pl-2 mb-2">本車該用車時間無人預約</div>
            		</c:otherwise>
            	</c:choose>
            </c:if>
            </tags:form-row>
            <form:hidden path="timeMK" value="${caseData.timeMK}"/>
            <c:if test="${not empty caseData.carBookingList}">
            
			<div class="d-flex no-gutters">
			    <div class="col-label "><func:minguo value="${caseData.carBookingDetailData.using_date}"/>該車輛用車明細</div>
			</div>
                <table  class="table table-hover mb-4">
            		<thead>
            		<tr>
            			<th style="width: 10%">派車單號</th>
            			<th style="width: 10%">申請人</th>
            			<th style="width: 10%">申請單位</th>
            			<th style="width: 10%">用車區間</th>
            			<th style="width: 30%">用車事由</th>
            			<th style="width: 30%">目的地</th>
            		</tr>
            		</thead>
            		<tbody>
		            <c:forEach items="${caseData.carBookingList}" var="item" varStatus="status">
            		<tr>
		                 <td><c:out value="${item.applyid }"/></td>
		                 <td class="text-left"><func:username userid="${item.apply_user}"/></td>
		                 <td class="text-left"><func:dept deptid="${item.apply_dept}"/></td>
		                 <td><c:out value="${item.using_time_s}"/>〜<c:out value="${item.using_time_e}"/></td>
		                 <td class="text-left">
						<span class="ellipsisStr">
	                 		<c:out value="${item.apply_memo}"/>
	                 	</span>
	                 	</td>
	                 	<td class="text-left"><c:out value="${item.destination}"/></td>
            		</tr>
		            </c:forEach>
            		</tbody>
            	</table>
            <div class="d-flex no-gutters">
			    <div class="col-label "><func:minguo value="${caseData.carBookingDetailData.using_date}"/>該車輛用車時間一覽</div>
			</div>
		    <table class="table table-bordered">
		    	<thead>
		        <tr>
		            <th class="p-2 text-center">用車區間</th>
		            <th colspan="2" class="p-2 text-center">0</th>
		            <th colspan="2" class="p-2 text-center">1</th>
		            <th colspan="2" class="p-2 text-center">2</th>
		            <th colspan="2" class="p-2 text-center">3</th>
		            <th colspan="2" class="p-2 text-center">4</th>
		            <th colspan="2" class="p-2 text-center">5</th>
		            <th colspan="2" class="p-2 text-center">6</th>
		            <th colspan="2" class="p-2 text-center">7</th>
		            <th colspan="2" class="p-2 text-center">8</th>
		            <th colspan="2" class="p-2 text-center">9</th>
		            <th colspan="2" class="p-2 text-center">10</th>
		            <th colspan="2" class="p-2 text-center">11</th>
		            <th colspan="2" class="p-2 text-center">12</th>
		            <th colspan="2" class="p-2 text-center">13</th>
		            <th colspan="2" class="p-2 text-center">14</th>
		            <th colspan="2" class="p-2 text-center">15</th>
		            <th colspan="2" class="p-2 text-center">16</th>
		            <th colspan="2" class="p-2 text-center">17</th>
		            <th colspan="2" class="p-2 text-center">18</th>
		            <th colspan="2" class="p-2 text-center">19</th>
		            <th colspan="2" class="p-2 text-center">20</th>
		            <th colspan="2" class="p-2 text-center">21</th>
		            <th colspan="2" class="p-2 text-center">22</th>
		            <th colspan="2" class="p-2 text-center">23</th>
		        </tr>
		        </thead>
		        <c:forEach items="${caseData.carBookingList}" var="cbdata" varStatus="status">
		        <tr style="background:#fff " class="datatr">
		            <td class="text-center"><func:timeconvert value="${cbdata.using_time_s}"/>〜<func:timeconvert value="${cbdata.using_time_e}"/></td>
		            <td class="p-2 datatd"  title="00:00-00:30"></td>
		            <td class="p-2 datatd"  title="00:30-01:00"></td>
		            <td class="p-2 datatd"  title="01:00-01:30"></td>
		            <td class="p-2 datatd"  title="01:30-02:00"></td>
		            <td class="p-2 datatd"  title="02:00-02:30"></td>
		            <td class="p-2 datatd"  title="02:30-03:00"></td>
		            <td class="p-2 datatd"  title="03:00-03:30"></td>
		            <td class="p-2 datatd"  title="03:30-04:00"></td>
		            <td class="p-2 datatd"  title="04:00-04:30"></td>
		            <td class="p-2 datatd"  title="04:30-05:00"></td>
		            <td class="p-2 datatd"  title="05:00-05:30"></td>
		            <td class="p-2 datatd"  title="05:30-06:00"></td>
		            <td class="p-2 datatd"  title="06:00-06:30"></td>
		            <td class="p-2 datatd"  title="06:30-07:00"></td>
		            <td class="p-2 datatd"  title="07:00-07:30"></td>
		            <td class="p-2 datatd"  title="07:30-08:00"></td>
		            <td class="p-2 datatd"  title="08:00-08:30"></td>
		            <td class="p-2 datatd"  title="08:30-09:00"></td>
		            <td class="p-2 datatd"  title="09:00-09:30"></td>
		            <td class="p-2 datatd"  title="09:30-10:00"></td>
		            <td class="p-2 datatd"  title="10:00-10:30"></td>
		            <td class="p-2 datatd"  title="10:30-11:00"></td>
		            <td class="p-2 datatd"  title="11:00-11:30"></td>
		            <td class="p-2 datatd"  title="11:30-12:00"></td>
		            <td class="p-2 datatd"  title="12:00-12:30"></td>
		            <td class="p-2 datatd"  title="12:30-13:00"></td>
		            <td class="p-2 datatd"  title="13:00-13:30"></td>
		            <td class="p-2 datatd"  title="13:30-14:00"></td>
		            <td class="p-2 datatd"  title="14:00-14:30"></td>
		            <td class="p-2 datatd"  title="14:30-15:00"></td>
		            <td class="p-2 datatd"  title="15:00-15:30"></td>
		            <td class="p-2 datatd"  title="15:30-16:00"></td>
		            <td class="p-2 datatd"  title="16:00-16:30"></td>
		            <td class="p-2 datatd"  title="16:30-17:00"></td>
		            <td class="p-2 datatd"  title="17:00-17:30"></td>
		            <td class="p-2 datatd"  title="17:30-18:00"></td>
		            <td class="p-2 datatd"  title="18:00-18:30"></td>
		            <td class="p-2 datatd"  title="18:30-19:00"></td>
		            <td class="p-2 datatd"  title="19:00-19:30"></td>
		            <td class="p-2 datatd"  title="19:30-20:00"></td>
		            <td class="p-2 datatd"  title="20:00-20:30"></td>
		            <td class="p-2 datatd"  title="20:30-21:00"></td>
		            <td class="p-2 datatd"  title="21:00-21:30"></td>
		            <td class="p-2 datatd"  title="21:30-22:00"></td>
		            <td class="p-2 datatd"  title="22:00-22:30"></td>
		            <td class="p-2 datatd"  title="22:30-23:00"></td>
		            <td class="p-2 datatd"  title="23:00-23:30"></td>
		            <td class="p-2 datatd"  title="23:30-24:00"></td>
		            <form:hidden path="usingTimeList" class="usingTimeList" value="${cbdata.usingTimeList}"/>
		        </tr>
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
 		</fieldset>
        </form:form>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
        	
			$(".approve_using_time_s").text($('#approve_using_timeStr').val());

        	//處理時間表
        	if($('#timeMK').val() !==''){
        		changeOption();
        	    var listLength = $(".datatr").length;
        	    for(var i=0; i<listLength; i++){
        	    	let num = $(".usingTimeList").eq(i).val();
        	    	let index = num.split(",");
        	        index.forEach((e)=> $(".datatr").eq(i).children(".datatd").eq(e).addClass("bg-info"));
        	    		     
        	    }
        	}

			$('.mergeReason').hide();
			$('.mergeApplyid').hide();
        	
        	$('#carno').change(function(e){
        		e.preventDefault();
        		
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
        		var num = ""; 
        		var timeMK = $('#timeMK').val();
        		var merge = $("input[name='merge']:checked").val();
        		
        		if($('#num_of_people').val()>4){
        			num = [1,2,3,4,5,7];
        		} else {
	        		if(timeMK=='Y' && merge=='N'){num = [1,2,4,5,6,7];}
	        		if(timeMK=='N' && merge=='N'){num = [4,5,6,7];}
	        		if(merge=='Y'){num = [1,2,6,7];}
        		}

        		num.forEach(e => $("#status>option").eq(e).hide());
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
        
        $(".timeselect").change(function(e){
			if($("#starH").val()!=='' && $("#starM").val()!=='' && $("#endH").val()!=='' && $("#endM").val()!==''){
				var start = $("#starH").val()+$("#starM").val();
				var end = $("#endH").val()+$("#endM").val();
				if(parseInt(end)<parseInt(start)){
					showAlert('核定時間迄不得大於起');
					return;
				}
				
				if(($("#starH").val()== $("#endH").val()) && ($("#starM").val()==$("#endM").val())){
					showAlert('核定時間起訖不可相同');
					return;
				}
				
	        	var data = {};
				data["starH"] = $("#starH").val();
				data["starM"] = $("#starM").val();
				data["endH"] = $("#endH").val();
				data["endM"] = $("#endM").val();
				
				var url = '<c:url value='/Eip07w040_getTime.action' />';
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : url,
					data : JSON.stringify(data),
					dataType : 'json',
					timeout : 100000,
					success : function(data) {
						var str = data.starTime.substring(0,2)+":"+
						data.starTime.substring(2,4)+"~"+
						data.endTime.substring(0,2)+":"+
						data.endTime.substring(2,4);
						$(".approve_using_time_s").text(str);

						$("#approve_using_time_s").val(data.starTime);
						$("#approve_using_time_e").val(data.endTime);
						$("#approve_using_timeStr").val(str);
						
		        		if($('#carno').val()!==''){
		        			$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_getUsingData.action" />').submit();
		        		}
					},
					error : function(e) {
						showAlert("取得核定區間失敗");
					}
	        	});
       		 }
        });

</script>
</jsp:attribute>
</tags:layout>