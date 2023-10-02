<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w040Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnCancel">
    	臨時取消<i class="fas fa-user-times"></i>
    </tags:button>
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
     	<fieldset>
      	<legend>已處理派車明細</legend>
		<form:form id="eip07w040Form" name="eip07w040Form" modelAttribute="${caseKey}" method="POST">
		    <tags:form-row>
		        <tags:text-item label="派車單號">
                    <c:out value="${caseData.carBookingDetailData.applyid}"/>
                </tags:text-item>
            </tags:form-row>
            <tags:form-row>
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
            	<tags:text-item label="申請用車日期">
	            	<func:minguo value="${caseData.carBookingDetailData.using_date}"/>
            	</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="用車時間">
		            	<func:timeconvert value="${caseData.carBookingDetailData.using_time_s}"/>~
		            	<func:timeconvert value="${caseData.carBookingDetailData.using_time_e}"/>
            		</tags:text-item>
            	</div>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="用車區間">
		            	<c:out value="${caseData.carBookingDetailData.usingStr}"/>
            		</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="核定用車時間">
		            	<func:timeconvert value="${caseData.carBookingDetailData.approve_using_time_s}"/>~
		            	<func:timeconvert value="${caseData.carBookingDetailData.approve_using_time_e}"/>
            		</tags:text-item>
            	</div>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="核定用車區間">
		            	<c:out value="${caseData.carBookingDetailData.usingStr}"/>
            		</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="車牌號碼">
		            	<c:out value="${caseData.recData.carno1}"/>-<c:out value="${caseData.recData.carno2}"/>
            		</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="是否併單">
	            		<c:if test="${caseData.carBookingDetailData.combine_mk =='Y'}">是</c:if>
	            		<c:if test="${caseData.carBookingDetailData.combine_mk =='N'}">否</c:if>
            		</tags:text-item>
            	</div>
            	<c:if test="${caseData.carBookingDetailData.combine_mk =='Y'}">
	            	<div class="col-4 col-md-4">
	            		<tags:text-item label="併單單號">
	            			<c:out value="${caseData.carBookingDetailData.combine_applyid}"/>
	            		</tags:text-item>
	            	</div>
	            	<div class="col-4 col-md-4">
	            		<tags:text-item label="併單原因">
	            			<c:out value="${caseData.carBookingDetailData.combine_reason}"/>
	            		</tags:text-item>
	            	</div>
            	</c:if>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="派車單狀態">
						<c:out value="${caseData.carBookingDetailData.carprocess_status}"/>-<func:code codekind = 'CARPROCESSSTATUS' codeno = '${caseData.carBookingDetailData.carprocess_status}' />
            		</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-note>
            	<font color="red">秘書處臨時取消派車(請務必聯絡到申請人才可取消)</font>
            </tags:form-note>
        </form:form>
 		</fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
            $('#btnReturn').click(function(){
           		$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_back.action" />').submit();
            });
            
            $('#btnCancel').click(function(){
            	$('#eip07w040Form').attr('action', '<c:url value="/Eip07w040_delete.action" />').submit();
            });
            
         });
</script>
</jsp:attribute>
</tags:layout>