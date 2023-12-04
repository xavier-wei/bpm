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
		<form:form id="eip07w040Form" name="eip07w040Form" modelAttribute="${caseKey}" method="POST">
     	 <fieldset>
      	<legend>查詢條件</legend>
            <tags:form-row>
            <form:label path="carprocess_status" cssClass="col-form-label">派車狀態：</form:label>
            <form:select path="carprocess_status" cssClass="form-control add">
               	  <form:option value=""></form:option>
                  <form:option value="1">1-未處理</form:option>
                  <form:option value="2">2-已處理</form:option>
                  <form:option value="3">3-秘書處主管複核(同意核派)</form:option>
                  <form:option value="4">4-秘書處主管退單(不同意核派)</form:option>
            </form:select>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label" path="applydateStart">申請日期：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
                    <form:input path="applydateStart" cssClass="add form-control dateTW cdate " maxlength="7"/>〜
                    <form:input path="applydateEnd" cssClass="add form-control dateTW cdate " maxlength="7" />
                </div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label" path="using_time_s">用車日期：</form:label>
                <div class="col-12 col-md d-flex align-items-center">
                    <form:input path="using_time_s" cssClass="add form-control dateTW cdate" maxlength="7" />〜
                    <form:input path="using_time_e" cssClass="add form-control dateTW cdate" maxlength="7" />
                </div>
            </tags:form-row>
            <tags:form-note>
                派車狀態為必選條件，申請日期、用車日期請擇一輸入。
            </tags:form-note>
            </fieldset>
            
            <fieldset>
            <legend>待處理派車案件</legend>
            <tags:form-row>
            <div class="table-responsive">	 
            	    <table id="notHandleListTable" class="table">
                        <thead>
                        	<tr>
	                            <th style="width: 5%">序號</th>
	                            <th style="width: 10%">申請人</th>
	                            <th style="width: 15%">申請單位</th>
	                            <th style="width: 10%">申請用車日期</th>
	                            <th style="width: 10%">申請用車時間起迄</th>
	                            <th style="width: 24%">用車事由</th>
	                            <th style="width: 10%">表單狀態</th>
	                            <th style="width: 8%">補單註記</th>
	                            <th style="width: 8%">明細</th>
                        	</tr>
                        </thead>
                        <c:forEach items="${caseData.notHandleList}" var="item" varStatus="status">
                        <tbody><tr>
                        	<td><c:out value="${status.index+1}"/></td>
                        	<td><c:out value="${item.apply_user}"/>
                        	</td>
                        	<td>
                        		<c:out value="${item.apply_dept}"/>
                        	</td>
                        	<td><func:minguo value="${item.using_date}"/></td>
                        	<td>
	                        	<c:out value="${item.using_time_s}"/>~
	                        	<c:out value="${item.using_time_e}"/>
                        	</td>
                        	<td class="text-left">
							<span class="ellipsisStr">
		                 		<c:out value="${item.apply_memo}"/>
		                 	</span>
                        	</td>
                        	<td>${item.carprocess_status}
                        	</td>
                        	<td><c:out value="${item.fillmk}"/></td>
                        	<td>
                        		<tags:button cssClass="btnDetail" value="${item.applyid}">
									明細<i class="fas fa-file-alt"></i>
								</tags:button>
                        	</td>
                        	</tr>
                        </tbody>
                        </c:forEach>
                    </table>
                    </div>
            </tags:form-row>
            </fieldset>
            
            <fieldset>
            <legend>已處理派車案件，用車日期：
            <func:minguo value="${caseData.using_time_sStrForTable2}"/>~<func:minguo value="${caseData.using_time_eStrForTable2}"/></legend>
            <tags:form-row>
            <div class="table-responsive">	 
                     <table id="qryListTable" class="table">
                       <thead data-orderable="true">
                        <tr>
                            <th class="align-middle" style="width: 5%">全選<input type="checkbox" id="handledListTabcheckAllP" name="handledListTabcheckAllP"></th>
                            <th class="align-middle" style="width: 10%">派車單號</th>
                            <th class="align-middle" style="width: 10%">申請人</th>
                            <th class="align-middle" style="width: 10%">申請單位</th>
                            <th class="align-middle" style="width: 7%">用車日期</th>
                            <th class="align-middle" style="width: 8%">核定用車時間起迄</th>
                            <th class="align-middle" style="width: 11%">用車事由</th>
                            <th class="align-middle" style="width: 6%">已列印派車單註記</th>
                            <th class="align-middle" style="width: 8%">車號</th>
                            <th class="align-middle" style="width: 7%">補印</th>
                            <th class="align-middle" style="width: 8%">臨時取消</th>
                            <th class="align-middle" style="width: 10%">改派他車</th>
                        </tr>
                       </thead>
                      <tbody>

                      <c:forEach items="${caseData.handledList}" var="item" varStatus="status">
                      <tr>
                      	<td>
                      	<c:if test="${empty item.print_mk}">
                      		<form:checkbox path="applyids" name="applyids" value="${item.applyid}"/>
                      	</c:if>
                      	</td>
                      	<td><c:out value="${item.applyid}"/></td>
                      	<td><c:out value="${item.apply_user}"/>
                      	</td>
                      	<td>
                      		<c:out value="${item.apply_dept}"/>
                      	</td>
                      	<td><func:minguo value="${item.using_date}"/></td>
                      	<td>
                    	    <c:choose>
                      		<c:when test="${item.using ne item.approve_using}">
                      			<font color="red">
                      			<c:out value="${item.approve_using_time_s}"/>~
                       			<c:out value="${item.approve_using_time_e}"/></font>
	                       	</c:when>
	                       	<c:otherwise>
	                        	<c:out value="${item.approve_using_time_s}"/>~
	                        	<c:out value="${item.approve_using_time_e}"/>
	                       	</c:otherwise>
	                       	</c:choose>                      	
                      	</td>
                      	<td class="text-left">
					<span class="ellipsisStr">
                 		<c:out value="${item.apply_memo}"/>
                 	</span>
                      	</td>
                      	<td>
                      		<c:out value="${item.print_mk}"/>
                      	</td>
                      	<td>
                      		<c:out value="${item.carno1}"/>-<c:out value="${item.carno2}"/>
                      	</td>
                      	<td>
                      	<c:if test="${item.print_mk eq 'Y'}">
                      		<tags:button cssClass="btnPrint2" value="${item.applyid}">
							補印
							</tags:button>
						</c:if>
                      	</td>
	                      	<c:choose>
	                      		<c:when test="${item.carprocess_status eq '9'}">
	                      			<td>Y</td>
	                      			<td></td>
	                      		</c:when>
	                      		<c:otherwise>
	                      			<td>
			                      		<tags:button cssClass="btnDetail2" value="${item.applyid}">
											臨時取消
										</tags:button>
									</td>
									<td>
	                    				<tags:button cssClass="btnChangeCar" value="${item.applyid}">
											改派他車
										</tags:button>
									</td>
	                      		</c:otherwise>
	                      	</c:choose>

                      </tr>
                      </c:forEach>
                      </tbody>
                  </table>
            </div>
            </tags:form-row>
            </fieldset>
			<form:hidden path="applyid"/>
			<form:hidden path="reprintApplyid"/>

            <fieldset>
            <legend>秘書處長官核派案件，核定用車日期：
            <func:minguo value="${caseData.using_time_sStrForTable3}"/> ~ <func:minguo value="${caseData.using_time_eStrForTable3}"/></legend>
            <tags:form-row>
            <div class="table-responsive">	 
                     <table id="dataList" class="table">
                       <thead data-orderable="true">
                        <tr>
                            <th style="width:15%" class="align-middle">派車單號</th>
                            <th style="width:10%" class="align-middle">申請人</th>
                            <th style="width:10%" class="align-middle">申請單位</th>
                            <th style="width:10%" class="align-middle">用車日期</th>
                            <th style="width:10%" class="align-middle">核定用車時間起迄</th>
                            <th style="width:35%" class="align-middle">用車事由</th>
                            <th style="width:20%" class="align-middle">核派狀態</th>
                        </tr>
                       </thead>
                      <tbody>

                      <c:forEach items="${caseData.reconfime_mk2List}" var="item" varStatus="status">
                      <tr>
                      	<td><c:out value="${item.applyid}"/></td>
                      	<td><c:out value="${item.apply_user}"/>
                      	</td>
                      	<td>
                      		<c:out value="${item.apply_dept}"/>
                      	</td>
                      	<td><func:minguo value="${item.using_date}"/></td>
                      	<td>
                      	<c:if test="${not empty item.approve_using_time_s and not empty item.approve_using_time_e}">
                       		<c:out value="${item.approve_using_time_s}"/>~<c:out value="${item.approve_using_time_e}"/>
                      	</c:if>
                      	</td>
                        <td class="text-left">
							<span class="ellipsisStr">
		                 		<c:out value="${item.apply_memo}"/>
		                 	</span>
                      	</td>
                      	<td>
                      		<c:if test="${item.reconfirm_mk2=='Y'}">
                      			Y-同意
                      		</c:if>
                      		<c:if test="${item.reconfirm_mk2=='N'}">
                      			N-不同意
                      		</c:if>
                      	</td>
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
            var table = $("#qryListTable,#dataList,#notHandleListTable").DataTable(config);
            
            $('.btnDetail').click(function() {
				$('#applyid').val($(this).val());
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_query.action" />').submit();
            });
            
            $('.btnDetail2').click(function() {
				$('#applyid').val($(this).val());
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_detail.action" />').submit();
            });
            
            
            $('#btnPrint').click(function() {
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_print.action" />').submit();
            });
            
            
            $(".btnPrint2").click(function(){
            	$("#reprintApplyid").val($(this).val());
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_print.action" />').submit();
            });
            
            $('#btnSearch').click(function() {
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_search.action" />').submit();
            });
            
            $('.btnChangeCar').click(function() {
            	$('#applyid').val($(this).val());
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_changecar.action" />').submit();
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
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_enter.action" />').submit();
            });


         });
</script>
</jsp:attribute>
</tags:layout>