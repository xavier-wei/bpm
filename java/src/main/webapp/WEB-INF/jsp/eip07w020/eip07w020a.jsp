<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	  <tags:button id="btnSelect">
    	確認預約<i class="fas fa-user-check"></i>
      </tags:button>

	  <tags:button id="btnClearn">
    	回主畫面<i class="fas fa-reply"></i>
      </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset legend="請填寫預約內容">
		<form:form id="eip07w020Form" name="eip07w020Form" modelAttribute="${caseKey}" method="POST">

			<tags:form-row>
				<div  class="col-md d-flex">
					<form:label cssClass="col-form-label" path="userName">申請人：</form:label>
					<form:input id="userName" name="userName" path="userName" cssClass="form-control"   size="8"
								maxlength="8" disabled="true"/>
				</div>
				<div  class="col-md d-flex">
					<form:label cssClass="col-form-label" path="applyUnitNm">申請單位：</form:label>
					<form:input id="applyUnitNm" name="applyUnitNm" path="applyUnitNm" cssClass="form-control"   size="8"
								maxlength="8" disabled="true"/>
				</div>
				<div  class="col-md d-flex">
					<form:label cssClass="col-form-label" path="applyDate">申請日期：</form:label>
					<form:input id="applyDate" name="applyDate" path="applyDate" cssClass="form-control  dateTW"   size="9"
								maxlength="7" disabled="true"/>
				</div>
			</tags:form-row>
			<tags:form-row>
				<div  class="col-md d-flex">
					<form:label cssClass="col-form-label star" path="useCarMemo">用車事由：</form:label>
					<form:textarea path="insterList[0].useCarMemo" cssClass="form-control" cols="70" rows="3" maxlength="100"/>
				</div>
			</tags:form-row>
			<tags:form-row>
				<div  class="col-md d-flex">
					<form:label cssClass="col-form-label star" path="destination">目的地：</form:label>
					<form:textarea path="insterList[0].destination" cssClass="form-control" cols="70" rows="3" maxlength="100"/>
				</div>
			</tags:form-row>
			<tags:form-row>
					<div class="col-md-4 d-flex">
					   <form:label cssClass="col-form-label" path="carTy">車輛總類：</form:label>
					   <form:select id="carTy"  name="carTy"  path="insterList[0].carTy" cssClass="form-control">
	                    	<form:option value="1">4人座</form:option>
	                        <c:forEach var="item" items="${caseData.carTyList}" varStatus="status">
	                            <form:option value="${item.codeno}"><c:out value="${item.codename}"/></form:option>
	                        </c:forEach>
	                    </form:select>
				   </div>
					<div class="col-md-4 d-flex">
						<form:label cssClass="col-form-label star" path="number">人數：</form:label>
						<form:input id="number" name="number" path="insterList[0].number" cssClass="form-control num_only"   size="3"
									maxlength="3" />
					</div>
			</tags:form-row>
			<tags:form-row>
					<div  class="col-md d-flex">
						<form:label cssClass="col-form-label star" path="useDate">用車日期：</form:label>
						<form:input id="useDate" name="useDate" path="insterList[0].useDate" cssClass="form-control num_only dateTW"   size="9"
									maxlength="7" />
					</div>
			</tags:form-row>
					 <div  class="col-md d-flex text-left" style="margin: 0; padding: 0;">
						 <form:label cssClass="col-form-label star" path="starH">用車時間：</form:label>
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