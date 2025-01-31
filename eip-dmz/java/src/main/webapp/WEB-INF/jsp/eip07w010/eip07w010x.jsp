<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w010Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	  <tags:button id="btnSelect">
    	確認<i class="fas fa-user-plus"></i>
      </tags:button>

	  <tags:button id="btnClearn">
    	回上一頁<i class="fas fa-user-plus"></i>
      </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip07w010Form" name="eip07w010Form" modelAttribute="${caseKey}" method="POST">
		<div class="table-responsive" id="div1">
			<table class="table" id="driver">
				<thead data-orderable="true">
				<tr>
					<th class="text-center align-middle">序號</th>
					<th class="text-center align-middle">駕駛人姓名</th>
					<th class="text-center align-middle">手機號碼</th>
					<th class="text-center align-middle">職稱</th>
					<th class="text-center align-middle">在職註記</th>
					<th class="text-center align-middle">預定保管車號</th>
					<th class="text-center align-middle"></th>

				</tr>
				</thead>
				<tbody>
				<c:forEach items="${caseData.eip07w010QueryDataList}" var="item" varStatus="status">
                                <tr class="text-left">
									<td class="text-center">
										<c:out value='${status.count}'/>
									</td>
									<td id="name">
										<c:out value='${item.name}'/>
									</td>
									<td id="cellphone">
										<c:out value='${item.cellphone}'/>
									</td>
									<td id="title">
										<c:out value='${item.title}'/>
									</td>
									<td id="stillWork">
										<c:out value='${item.stillWork}'/>
									</td>
									<td id="carno1">
										<c:out value='${item.carno1}'/>-
										<c:out value='${item.carno2}'/>
									</td>
									<td class="text-left" >
										<tags:button onclick="printDetailReport('${item.driverid}','${item.stillWork}')"  >明細</tags:button>
									</td>
								</tr>
                            </c:forEach>
				</tbody>
			</table>
		</div>

			<table class="table" id="car">
				<thead data-orderable="true">
				<tr>
					<th class="text-center align-middle">序號</th>
					<th class="text-center align-middle">車牌號碼</th>
					<th class="text-center align-middle">車輛種類</th>
					<th class="text-center align-middle">首長專用</th>
					<th class="text-center align-middle">財產編號</th>
					<th class="text-center align-middle">保險公司</th>
					<th class="text-center align-middle">保險期間(起)~(迄)</th>
					<th class="text-center align-middle"></th>

				</tr>
				</thead>
				<tbody>
				<c:forEach items="${caseData.eip07w010CarDataList}" var="item" varStatus="status">
                                <tr class="text-left">
									<td class="text-center">
										<c:out value='${status.count}'/>
									</td>
									<td id="carno">
										<c:out value='${item.carno1}'/>-
										<c:out value='${item.carno2}'/>
									</td>
									<td id="carType">
										<c:out value='${item.carType}'/>
									</td>
									<td id="bossMk">
										<c:out value='${item.bossMk}'/>
									</td>
									<td id="owned">
										<c:out value='${item.owned}'/>
									</td>
									<td id="insuranceCompany">
										<c:out value='${item.insuranceCompany}'/>
									</td>
									<td id="date">
										<c:out value='${item.insuranceStart}'/>~
										<c:out value='${item.InsuranceEnd}'/>
									</td>
									<td class="text-left" >
										<tags:button onclick="printDetailReport('${item.driverid}','${item.stillWork}')"  >明細</tags:button>
									</td>
								</tr>
                            </c:forEach>
				</tbody>
			</table>
		</div>


		<form:hidden id="driveridDetail" path="driveridDetail" />
		<form:hidden id="stillWork" path="stillWork" />
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

        $(function() {
			controlBt();
			var config = getDataTablesConfig();
            var table = $('#listTable').DataTable(config);

            $('#btnSelect').click(function() {
				$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_query.action" />').submit();
            });

			$('#btnClearn').click(function() {
				$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_enter.action" />').submit();
			});

			$('input[id=processTy]:radio').change(function(e) {
				controlBt();
			});

			function controlBt(){
				var processTy=$("input[id=processTy]:checked").val();
				if(processTy == 'D'){
					$("#driver").show();
					$("#car").hide();
				} else {
					$("#car").show();
					$("#driver").hide();
				}
			}
         });

		function printDetailReport(driverid,stillWork){
			console.log(driverid)
			var fun = '<c:url value="/Eip07w010_detail.action" />';
			$('input[id="driveridDetail"]').val(driverid);
			$('input[id="stillWork"]').val(stillWork);
			$('#eip07w010Form').attr('action', fun).submit();
		}
</script>
</jsp:attribute>
</tags:layout>