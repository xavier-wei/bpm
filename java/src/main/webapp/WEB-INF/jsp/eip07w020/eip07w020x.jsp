<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	  <tags:button id="btnClearn">
    	回主畫面<i class="fas fa-user-plus"></i>
      </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip07w020Form" name="eip07w020Form" modelAttribute="${caseKey}" method="POST">
		<div class="table-responsive" id="div1">
			<table class="table" id="driver">
				<thead data-orderable="true">
				<tr>
					<th class="text-center align-middle">序號</th>
					<th class="text-center align-middle">派車單號</th>
					<th class="text-center align-middle">申請日期<br>用車日期</th>
					<th class="text-center align-middle">用車事由</th>
					<th class="text-center align-middle">用車時間</th>
					<th class="text-center align-middle">表單狀態</th>
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
										<c:out value='${item.applyDate}'/><br>
										<c:out value='${item.useDate}'/>
									</td>
									<td id="useCarMemo">
										<c:out value='${item.useCarMemo}'/>
									</td>
									<td id="time" class="text-center">
										<c:out value='${item.applyTimeS}'/>~
										<c:out value='${item.applyTimeE}'/>
									</td>
									<td id="processStaus" class="text-center">
										<c:out value='${item.processStaus}'/>-<c:out value='${item.processStausNm}'/>
									</td>
									<td class="text-left" >
										<tags:button onclick="detailReport('${item.applyId}')"  >明細</tags:button>
									</td>
								</tr>
                            </c:forEach>
				</tbody>
			</table>
		</div>
		</div>
		<form:hidden id="applyId" path="applyId" />
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

        $(function() {

			var config = getDataTablesConfig();


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