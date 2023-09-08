<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w010Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	  <tags:button id="btnClearn">
    	回上一頁<i class="fas fa-reply"></i>
      </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
		<form:form id="eip07w010Form" name="eip07w010Form" modelAttribute="${caseKey}" method="POST">
    <fieldset>
      	<legend>查詢條件</legend>
			 <c:choose>
			 <c:when test="${caseData.processTy eq 'D' }">
				<tags:form-row>
				<div   class="col-md-2 d-flex" >
					<form:label cssClass="col-form-label " path="name">選項：駕駛人資料</form:label>
				</div>
				<div   class="col-md-2 d-flex" >
					<form:label cssClass="col-form-label " path="name">駕駛人姓名：</form:label>
					<label class="col-form-label text-left col-3" ><c:out value="${caseData.name}"/></label>
				</div>
				<div   class="col-md-2 d-flex" >
					<form:label cssClass="col-form-label " path="stillWork">在職註記：</form:label>
					<label class="col-form-label text-left col-3" ><c:out value='${caseData.stillWork}'/>-<c:out value="${caseData.stillWorkNm}"/></label>
				</div>
		</tags:form-row>
			</c:when>
			 <c:otherwise>
				<tags:form-row>
				<div   class="col-md-2 d-flex" >
					<form:label cssClass="col-form-label " path="name">選項：車籍資料</form:label>
				</div>
				<div   class="col-md-4 d-flex" >
					<form:label cssClass="col-form-label " path="carno1">車牌號碼：</form:label>
				</div>
				</tags:form-row>
			</c:otherwise>
		</c:choose>

	</fieldset>
		<fieldset id="resultBox">
		<legend>查詢結果</legend>
		 <c:choose>
			 <c:when test="${caseData.processTy eq 'D' }">
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
					<th data-orderable="false"></th>
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
										<c:out value='${item.title}'/>-<c:out value='${item.titleNm}'/>
									</td>
									<td id="stillWork">
										<c:out value='${item.stillWork}'/>-<c:out value='${item.stillWorkNm}'/>
									</td>
									<td >
										<c:out value='${item.carno1}'/>-
										<c:out value='${item.carno2}'/>
									</td>
									<td class="text-left" >
										<tags:button onclick="driverDetailReport('${item.driverid}','${item.stillWork}')"  >明細<i class="fas fa-file-alt"></i></tags:button>
									</td>
								</tr>
                            </c:forEach>
				</tbody>
			</table>
		</div>
		</c:when>
			 <c:otherwise>
			<table class="table" id="car">
				<thead data-orderable="true">
				<tr>
					<th class="text-center align-middle">序號</th>
					<th class="text-center align-middle">車牌號碼</th>
					<th class="text-center align-middle">車輛種類</th>
					<th class="text-center align-middle">首長專用</th>
					<th class="text-center align-middle">財產編號</th>
					<th class="text-center align-middle">保險公司</th>
					<th class="text-center align-middle">保險期間(起)-(迄)</th>
					<th data-orderable="false"></th>

				</tr>
				</thead>
				<tbody>
				<c:forEach items="${caseData.eip07w010CarDataList}" var="item" varStatus="status">
                                <tr class="text-left">
									<td class="text-center">
										<c:out value='${status.count}'/>
									</td>
									<td >
										<c:out value='${item.carno1}'/>-
										<c:out value='${item.carno2}'/>
									</td>
									<td id="carType">
										<c:out value='${item.carTypeNm}'/>
									</td>
									<td id="bossMk">
										<c:out value='${item.bossMk}'/><c:out value='${item.bossMkNm}'/>
									</td>
									<td id="owned">
										<c:out value='${item.owned}'/>
									</td>
									<td id="insuranceCompany">
										<c:out value='${item.insuranceCompany}'/>
									</td>
									<td id="date" class="text-center">
										<c:choose>
											 <c:when test="${not empty item.insuranceStart}">
												<c:out value="${fn:substring(item.insuranceStart, 0,3)}"/>/<c:out value="${fn:substring(item.insuranceStart, 3,5)}"/>/<c:out value="${fn:substring(item.insuranceStart, 5,-1)}"/>
												-<c:out value="${fn:substring(item.insuranceEnd, 0,3)}"/>/<c:out value="${fn:substring(item.insuranceEnd, 3,5)}"/>/<c:out value="${fn:substring(item.insuranceEnd, 5,-1)}"/>
											 </c:when>
										</c:choose>
									</td>
									<td class="text-left" >
										<tags:button  onclick="carDetailReport('${item.carno1}','${item.carno2}')"  >明細<i class="fas fa-file-alt"></i></tags:button>
									</td>
								</tr>
                            </c:forEach>
				</tbody>
			</table>
			</c:otherwise>
		</c:choose>
		<form:hidden id="carno1" path="carno1" name="carno1" />
		<form:hidden id="carno2" path="carno2" name="carno2"/>
		<form:hidden id="driveridDetail" path="driveridDetail" />
		<form:hidden id="stillWork" path="stillWork" />
		<form:hidden id="processTy" path="processTy" />
		</form:form>
		</fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

        $(function() {
			controlBt();
			var config = getDataTablesConfig();
            var table = $('#driver').DataTable(config);

			$('#car').DataTable(config);
			
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
				var processTy=$("#processTy").val();
				if(processTy == 'D'){
					$("#driver").show();
					$("#car").hide();
				} else {
					$("#car").show();
					$("#driver").hide();
				}
			}
         });

		function driverDetailReport(driverid,stillWork){
			console.log(driverid)
			var fun = '<c:url value="/Eip07w010_detail.action" />';
			$('input[id="driveridDetail"]').val(driverid);
			$('input[id="stillWork"]').val(stillWork);
			$('#eip07w010Form').attr('action', fun).submit();
		}

		function carDetailReport(carno1,carno2){
			console.log(carno1)
			var fun = '<c:url value="/Eip07w010_carDetail.action" />';
			$('input[id="carno1"]').val(carno1);
			$('input[id="carno2"]').val(carno2);
			$('#eip07w010Form').attr('action', fun).submit();
		}

</script>
</jsp:attribute>
</tags:layout>