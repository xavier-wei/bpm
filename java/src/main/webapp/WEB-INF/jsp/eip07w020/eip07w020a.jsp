<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	  <tags:button id="btnSelect">
    	確認預約<i class="fas fa-search"></i>
      </tags:button>

	  <tags:button id="btnClearn">
    	回主畫面<i class="fa-step-backward"></i>
      </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip07w020Form" name="eip07w020Form" modelAttribute="${caseKey}" method="POST">

			<tags:form-row>
				<label class="col-form-label text-left  ">申請人:</label>
				<div class="col-sm-2">
					<form:input id="applyName" name="applyName" path="applyName" cssClass="form-control"   size="8"
								maxlength="8" disabled="true"/>
				</div>
				<label class="col-form-label text-left ">申請單位:</label>
				<div class="col-sm-2">
					<form:input id="applyUnit" name="applyUnit" path="applyUnit" cssClass="form-control"   size="8"
								maxlength="8" disabled="true"/>
				</div>
				<label class="col-form-label text-left  ">申請日期:</label>
				<div class="col-sm-2">
					<form:input id="applyDate" name="applyDate" path="applyDate" cssClass="form-control"   size="7"
								maxlength="7" disabled="true"/>
				</div>
			</tags:form-row>
			<tags:form-row>
				<label class="col-form-label text-left  star">用車事由:</label>
				<div class="col-sm-2">
					<form:input id="useCarMemo" name="useCarMemo" path="insterList[0].useCarMemo" cssClass="form-control"   size="50"
								maxlength="50" />
				</div>
			</tags:form-row>
			<tags:form-row>
				<label class="col-form-label text-left  star">目的地:</label>
				<div class="col-sm-2">
					<form:input id="destination" name="destination" path="insterList[0].destination" cssClass="form-control"   size="50"
								maxlength="50" />
				</div>
			</tags:form-row>
			<tags:form-row>
					<label class="col-form-label text-left">車輛總類:</label>
				   <div>
	                    <form:select id="carTy"  name="carTy"  path="insterList[0].carTy" cssClass="form-control">
	                    	<form:option value="1">4人座</form:option>
	                        <c:forEach var="item" items="${caseData.carTyList}" varStatus="status">
	                            <form:option value="${item.codeno}"><c:out value="${item.codename}"/></form:option>
	                        </c:forEach>
	                    </form:select>
				   </div>
					<label class="col-form-label text-left  star">人數:</label>
						<div class="col-sm-2">
							<form:input id="number" name="number" path="insterList[0].number" cssClass="form-control"   size="3"
										maxlength="3" />
						</div>
			</tags:form-row>
			<tags:form-row>
						<label class="col-form-label text-left   star ">用車日期:</label>
					<div class="col-sm-2">
						<form:input id="useDate" name="useDate" path="insterList[0].useDate" cssClass="form-control num_only dateTW"   size="7"
									maxlength="7" />
					</div>
			</tags:form-row>
					 <div class="d-flex">
						 <label class="col-form-label text-left   star ">用車時間:</label>
						   <form:select id="starH"  name="starH"  path="insterList[0].starH" cssClass="form-control">
	                    	<form:option value=""></form:option>
	                        <c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
	                            <form:option value="${hour}"><c:out value="${hour}"/></form:option>
	                        </c:forEach>
	                    </form:select>
						 <span class="input-group-text px-1">:</span>
						 <form:select id="starM"  name="starM"  path="insterList[0].starM" cssClass="form-control">
	                    	<form:option value=""></form:option>
	                        <c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
	                            <form:option value="${minute}"><c:out value="${minute}"/></form:option>
	                        </c:forEach>
	                    </form:select>
						 <span class="input-group-text px-1">~</span>
						 <form:select id="endH"  name="endH"  path="insterList[0].endH" cssClass="form-control">
	                    	<form:option value=""></form:option>
	                        <c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
	                            <form:option value="${hour}"><c:out value="${hour}"/></form:option>
	                        </c:forEach>
	                    </form:select>
						 <span class="input-group-text px-1">:</span>
						 <form:select id="endM"  name="endM"  path="insterList[0].endM" cssClass="form-control">
	                    	<form:option value=""></form:option>
	                        <c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
	                            <form:option value="${minute}"><c:out value="${minute}"/></form:option>
	                        </c:forEach>
	                    </form:select>

					 </div>


			<tags:form-row>

			</tags:form-row>
	  </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

        $(function() {
            $('#btnSelect').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_inster.action" />').submit();
            });

			$('#btnClearn').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_enter.action" />').submit();
			});

			$('input[id=processTy]:radio').change(function(e) {
				controlBt();
			});
         });


</script>
</jsp:attribute>
</tags:layout>