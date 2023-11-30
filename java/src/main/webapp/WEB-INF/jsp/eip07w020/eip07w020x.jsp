<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	  <tags:button id="btnClearn">
    	回主畫面<i class="fas fa-reply"></i>
      </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">

		<form:form id="eip07w020Form" name="eip07w020Form" modelAttribute="${caseKey}" method="POST">
			 <fieldset>
      	<legend>查詢條件</legend>
				 <c:choose>
					 <c:when test="${caseData.isSecretarial eq 'Y'}">
						 	 <tags:form-row>
							<div   class="col-md-4 d-flex" >
								<form:label cssClass="col-form-label " path="lable">申請人：</form:label>
							</div>
							<div   class="col-md-4 d-flex" >
								<form:label cssClass="col-form-label " path="lable">申請單位：</form:label>
							</div>
						</tags:form-row>
					 </c:when>
					 <c:otherwise>
						 <tags:form-row>
							<div   class="col-md-4 d-flex" >
								<form:label cssClass="col-form-label " path="lable">申請人：</form:label>
								<label class="col-form-label text-left col-3" ><c:out value="${caseData.userName}"/></label>
							</div>
							<div   class="col-md-4 d-flex" >
								<form:label cssClass="col-form-label " path="lable">申請單位：</form:label>
								<label class="col-form-label text-left col-3" ><c:out value="${caseData.applyUnitNm}"/></label>
							</div>
						</tags:form-row>
					 </c:otherwise>
				 </c:choose>


		<tags:form-row>
				<div   class="col-md-4 d-flex" >
					<form:label cssClass="col-form-label " path="lable">申請日期(起)：</form:label>
					<label class="col-form-label text-left col-3" >
						 <c:choose>
							 <c:when test="${not empty caseData.applyDateStar}">
								<c:out value="${fn:substring(caseData.applyDateStar, 0,3)}"/>/<c:out value="${fn:substring(caseData.applyDateStar, 3,5)}"/>/<c:out value="${fn:substring(caseData.applyDateStar, 5,-1)}"/>
							 </c:when>
						</c:choose>
					</label>
				</div>
				<div   class="col-md-4 d-flex" >
					<form:label cssClass="col-form-label " path="lable">申請日期(迄)：</form:label>
					<label class="col-form-label text-left col-3" >
						 <c:choose>
							 <c:when test="${not empty caseData.applyDateEnd}">
								<c:out value="${fn:substring(caseData.applyDateEnd, 0,3)}"/>/<c:out value="${fn:substring(caseData.applyDateEnd, 3,5)}"/>/<c:out value="${fn:substring(caseData.applyDateEnd, 5,-1)}"/>
							 </c:when>
						</c:choose>
					</label>
				</div>
			</tags:form-row>
		<tags:form-row>
				<div   class="col-md-4 d-flex" >
					<form:label cssClass="col-form-label " path="lable">用車日期(起)：</form:label>
					<label class="col-form-label text-left col-3" >
						 <c:choose>
							 <c:when test="${not empty caseData.useDateStar}">
								<c:out value="${fn:substring(caseData.useDateStar, 0,3)}"/>/<c:out value="${fn:substring(caseData.useDateStar, 3,5)}"/>/<c:out value="${fn:substring(caseData.useDateStar, 5,-1)}"/>
							 </c:when>
						</c:choose>
					</label>
				</div>
				<div   class="col-md-4 d-flex" >
					<form:label cssClass="col-form-label " path="lable">用車日期(迄)：</form:label>
					<label class="col-form-label text-left col-3" >
						 <c:choose>
							 <c:when test="${not empty caseData.useDateEnd}">
								<c:out value="${fn:substring(caseData.useDateEnd, 0,3)}"/>/<c:out value="${fn:substring(caseData.useDateEnd, 3,5)}"/>/<c:out value="${fn:substring(caseData.useDateEnd, 5,-1)}"/>
							 </c:when>
						</c:choose>
					</label>
				</div>
			</tags:form-row>
			 </fieldset>
		<fieldset id="resultBox">
		<legend>查詢結果</legend>
		<div class="table-responsive" id="div1">
			<table class="table" id="driver">
				<thead data-orderable="true">
				<tr>
					<th class="text-center align-middle">序號</th>
					<th class="text-center align-middle">派車單號</th>
					<th class="text-center align-middle">申請日期<br>用車日期</th>
					<th class="text-center align-middle">用車時間</th>
					<th class="text-center align-middle">用車事由</th>
					<th class="text-center align-middle">表單狀態</th>
					<th class="text-center align-middle">補單註記</th>
					<th class="text-center align-middle"></th>

				</tr>
				</thead>
				<tbody>
				<c:forEach items="${caseData.resultList}" var="item" varStatus="status">
                                <tr class="text-left">
									<td class="text-center">
										<c:out value='${status.count}'/>
									</td>
									<td id="applyId" class="text-center">
										<c:out value='${item.applyId}'/>
									</td>
									<td id="applyDate" class="text-center">
										<c:out value="${fn:substring(item.applyDate, 0,3)}"/>/<c:out value="${fn:substring(item.applyDate, 3,5)}"/>/<c:out value="${fn:substring(item.applyDate, 5,-1)}"/>
										<br>
										<c:out value="${fn:substring(item.useDate, 0,3)}"/>/<c:out value="${fn:substring(item.useDate, 3,5)}"/>/<c:out value="${fn:substring(item.useDate, 5,-1)}"/>
									</td>

									<td id="time" class="text-center">
										<c:out value='${fn:substring(item.applyTimeS, 0,2)}'/>:<c:out value='${fn:substring(item.applyTimeS, 2,-1)}'/>
										-<c:out value='${fn:substring(item.applyTimeE, 0,2)}'/>:<c:out value='${fn:substring(item.applyTimeE, 2,-1)}'/>
									</td>
									<td id="useCarMemo">
										<c:out value='${item.useCarMemo}'/>
									</td>
									<td id="processStaus" class="text-left">
										<c:out value='${item.processStaus}'/>-<c:out value='${item.processStausNm}'/>
									</td>
									<td id="additionalColumn" class="text-center">
										<c:choose>
											<c:when test="${item.applyDate gt item.useDate}">
												Y
											</c:when>
										</c:choose>
									</td>
									<td class="text-left" >
										<tags:button onclick="detailReport('${item.applyId}')"  >明細<i class="fas fa-file-alt"></i></tags:button>
									</td>
								</tr>
                            </c:forEach>
				</tbody>
			</table>
		</div>
		<form:hidden id="applyId" path="applyId" />
        </form:form>
		</fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

        $(function() {

			var config = getDataTablesConfig(null,null,10);
			var table = $('#driver').DataTable(config);

			$('#btnClearn').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_enter.action" />').submit();
			});

			$('input[id=processTy]:radio').change(function(e) {
				controlBt();
			});
         });

		function detailReport(applyId){
			console.log(applyId)
			var fun = '<c:url value="/Eip07w020_updatePage.action" />';
			$('input[id="applyId"]').val(applyId);
			$('#eip07w020Form').attr('action', fun).submit();
		}


</script>
</jsp:attribute>
</tags:layout>