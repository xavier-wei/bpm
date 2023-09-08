<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w010Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
	<jsp:attribute name="heads">
		<style>
			.nav-link.active {
				border-radius: 8px 8px 0px 0px;
				padding: 4px;
				background-color: #42b0e3;
				background-image: linear-gradient(to bottom, #f58a38, #f57c20);
				border: 1px solid #c25706;
				box-shadow: inset 0 1px 0 #ffb984, inset 0 -1px 0 #db6f1d, inset 0 0 0 1px #f59851, 0 2px 4px rgba(0, 0, 0, 0.2);
				color: white;
			}
		</style>
	</jsp:attribute>
<jsp:attribute name="buttons">
	  <tags:button id="btnGasAdd">
		新增<i class="fas fa-user-plus"></i>
      </tags:button>

	  <tags:button id="btnUpdate">
    	修改<i class="fas fa-user-edit"></i>
      </tags:button>

	  <tags:button id="btnDelete">
    	刪除<i class="fas fa-trash-alt"></i>
      </tags:button>

	 <tags:button id="btnBack">
    	回主畫面<i class="fas fa-reply"></i>
      </tags:button>

</jsp:attribute>
	<jsp:attribute name="contents">
    	<tags:fieldset legend="查詢結果">
		    <form:form id="eip07w010Form" modelAttribute="${caseKey}">

		    	<!-- 明細錄 -->
			   	<div class="col-12 col-md-12">
					<ul class="nav nav-tabs" id="myTab" role="tablist">
						<li class="nav-item" role="presentation">
							<a class="nav-link" id="base-tab" data-toggle="tab" href="#base" role="tab" aria-controls="base"  aria-selected="false">基本資料</a>
						</li>
						<li class="nav-item" role="presentation">
							<a class="nav-link" id="oilList-tab" data-toggle="tab" href="#oilList" role="tab" aria-controls="oilList" aria-selected="false">油料紀錄</a>
						</li>
						<li class="nav-item" role="presentation">
							<a class="nav-link" id="mileageList-tab" data-toggle="tab" href="#mileageListData" role="tab" aria-controls="mileageListData" aria-selected="false">里程紀錄</a>
						</li>
					</ul>
				</div>
				<div class="col-12 col-md-12 tab-content">
						<%-- 基本資料 --%>
					<div class="tab-pane table-responsive" id="base" role="tabpanel" aria-labelledby="base-tab">
						<tags:form-row>
							<div class="col-md-4 d-flex">
								<form:label cssClass="col-form-label star" path="carno1">車牌號碼:</form:label>
								<table>
									<tr>
										<td><form:input id="carno1" name="carno1" path="eip07w010CarDataList[0].carno1" cssClass="form-control num_eng_only upCase" size="4" maxlength="3" disabled="true"  /></td>
										<td>-</td>
										<td><form:input id="carno2" name="carno2" path="eip07w010CarDataList[0].carno2" cssClass="form-control num_eng_only upCase" size="7" maxlength="4" disabled="true"  /></td>
									</tr>
								</table>
							</div>
							<div class="col-md-4 d-flex">
								<form:label cssClass="col-form-label star" path="carType">車輛種類:</form:label>
								<form:select id="carType" path="eip07w010CarDataList[0].carType" cssClass="form-control">
                    			<form:option value="1">1:4人座</form:option>
                    			<form:option value="2">2:7人座</form:option>
                  		 		</form:select>
							</div>
						</tags:form-row>
						<tags:form-row>
								<div class="col-md-4 d-flex">
									<form:label cssClass="col-form-label star" path="owned">財產編號:</form:label>
									<form:input id="owned" name="owned" path="eip07w010CarDataList[0].owned" cssClass="form-control" size="15" maxlength="12"/>
								</div>
								<div class="col-md-4 d-flex">
									<form:label cssClass="col-form-label" path="carYear">購置年份:</form:label>
									<form:input id="carYear" name="carYear" path="eip07w010CarDataList[0].carYear" cssClass="form-control form-control num_only padL" size="3" maxlength="3" />
								</div>
			            </tags:form-row>
						<tags:form-row>
							<div class="col-md d-flex">
								<form:label cssClass="col-form-label" path="bossMk">車輛廠牌:</form:label>
								<form:input id="carSource" name="carSource" path="eip07w010CarDataList[0].carSource" cssClass="form-control" size="7" maxlength="6"/>
							</div>
							<div class="col-md d-flex">
								<form:label cssClass="col-form-label" path="carColor">顏色:</form:label>
								<form:input id="carColor" name="carColor" path="eip07w010CarDataList[0].carColor" cssClass="form-control" size="7" maxlength="6"/>
							</div>
							<div class="col-md d-flex">
								<form:label cssClass="col-form-label" path="ccSize">排放量cc:</form:label>
								<form:input id="ccSize" name="ccSize" path="eip07w010CarDataList[0].ccSize" cssClass="form-control num_only" size="7" maxlength="6"/>
							</div>
						</tags:form-row>
						<tags:form-row>
							<div  class="col-md-4 d-flex">
								<form:label cssClass="col-form-label star" path="bossMk">首長專用車:</form:label>
								<form:select id="bossMk" path="eip07w010CarDataList[0].bossMk" cssClass="form-control">
									<form:option value="Y">是</form:option>
									<form:option value="N">否</form:option>
								</form:select>
							</div>
							<div class="col-md-4 d-flex">
								<form:label cssClass="col-form-label" path="bossName">首長:</form:label>
								<form:input id="bossName"  name="bossName"  path="eip07w010CarDataList[0].bossName" cssClass="form-control" size="7" maxlength="6"/>
							</div>
						</tags:form-row>

						<tags:form-row>
								<div  class="col-md d-flex">
									<form:label cssClass="col-form-label" path="carStatus">車輛狀態:</form:label>
<%--									<form:select id="carStatus"  name="carStatus"  path="eip07w010CarDataList[0].carStatus" cssClass="form-control">--%>
									<form:select id="carStatus"  name="carStatus"  path="eip07w010CarDataList[0].carStatus" cssClass="form-control">
									<c:forEach var="item" items="${caseData.carstatusList}" varStatus="status">
										<form:option value="${item.codeno}"><c:out value="${item.codeno}"/>-<c:out value="${item.codename}"/></form:option>
									</c:forEach>
								</form:select>
								</div>
						</tags:form-row>

						<tags:form-row>
							<div  class="col-md d-flex">
								<br>
								<form:label cssClass="col-form-label" path="insuranceCompany">[保險資料]:</form:label>
								<br>
							</div>
						</tags:form-row>

						<tags:form-row>
							<div class="col-md d-flex">
								<form:label cssClass="col-form-label" path="insuranceCompany">保險公司:</form:label>
								<form:input id="insuranceCompany"  name="insuranceCompany"  path="eip07w010CarDataList[0].insuranceCompany" cssClass="form-control" size="40" maxlength="100"/>
							</div>
						</tags:form-row>

						<tags:form-row>
							<div class="col-md-4 d-flex">
								<form:label cssClass="col-form-label" path="insuranceStart">保險期間(起):</form:label>
								<form:input id="insuranceStart"  name="insuranceStart"  path="eip07w010CarDataList[0].insuranceStart" cssClass="form-control num_only dateTW" size="9" maxlength="7"/>
							</div>
							<div class="col-md-4 d-flex">
								<form:label cssClass="col-form-label" path="InsuranceEnd">保險期間(迄):</form:label>
								<form:input id="InsuranceEnd"  name="InsuranceEnd"  path="eip07w010CarDataList[0].InsuranceEnd" cssClass="form-control num_only dateTW" size="9" maxlength="7"/>
							</div>
						</tags:form-row>
						<tags:form-row></tags:form-row>
					</div>

					<!-- 油料 -->
					<div class="tab-pane table-responsive" id="oilList" role="tabpanel" aria-labelledby="oilList-tab">
						<tags:form-row>
							 <label class="col-form-label text-left col-3">車牌號碼：<c:out value="${caseData.eip07w010CarDataList[0].carno1}-${caseData.eip07w010CarDataList[0].carno2}"/></label>
							<label class="col-form-label text-left col-3">車輛種類：<c:out value="${caseData.eip07w010CarDataList[0].carTypeNm}"/></label>
							<label class="col-form-label text-left col-3">財產編號：<c:out value="${caseData.eip07w010CarDataList[0].owned}"/></label>
			            </tags:form-row>
						<tags:form-row>
							<div  class="col-md d-flex">
								<br>
								<form:label cssClass="col-form-label " path="lable">[鍵入油料紀錄]：</form:label>
								<br>
							</div>
						</tags:form-row>
						<tags:form-row>
							<div   class="col-md-4 d-flex" >
								<form:label cssClass="col-form-label star" path="lable">加油日期：</form:label>
								<form:input id="fuel_date"  name="fuel_date"  path="gasRec.fuel_date" cssClass="form-control num_only dateTW" size="9" maxlength="7"/>

							</div>
							<div   class="col-md-4 d-flex" >
								<form:label cssClass="col-form-label" path="lable">加油時間：</form:label>
								<form:select id="gasH"  name="gasH"  path="gasH" cssClass="form-control">
									<form:option value=""></form:option>
									<c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
										<form:option value="${hour}"><c:out value="${hour}"/></form:option>
									</c:forEach>
								</form:select>
								<span class="input-group-text px-1">:</span>
								<form:select id="gasM"  name="gasM"  path="gasM" cssClass="form-control">
	                    	<form:option value=""></form:option>
								<c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
									<form:option value="${minute}"><c:out value="${minute}"/></form:option>
								</c:forEach>
							</form:select>

							</div>
						</tags:form-row>
						<tags:form-row>
							<div   class="col-md-4 d-flex" >
								<form:label cssClass="col-form-label " path="lable">加油金額：</form:label>
								<form:input id="gas_money"  name="gas_money"  path="gasRec.gas_money" cssClass="form-control" size="4" maxlength="4"/>

							</div>
							<div   class="col-md-4 d-flex" >
								<form:label cssClass="col-form-label " path="lable">加油量(公升)：</form:label>
								<form:input id="gas_amount"  name="gas_amount"  path="gasRec.gas_amount" cssClass="form-control" size="6" maxlength="6"/>
							</div>
						</tags:form-row>
						<tags:form-row>
							<div  class="col-md d-flex">
								<form:label cssClass="col-form-label " path="lable">[油料紀錄]：</form:label>
							</div>
						</tags:form-row>
						<tags:form-row></tags:form-row>
						<table class="table"  id="oil">
							<thead>
							<tr>
								<th style="vertical-align:middle;">序號</th>
								<th style="vertical-align:middle;">加油日期</th>
								<th style="vertical-align:middle;">時間</th>
								<th style="vertical-align:middle;">加油金額</th>
								<th style="vertical-align:middle;">加油量(公升)</th>
							</thead>
							<tbody>
							<c:forEach items="${caseData.oilList}" var="data" varStatus="status">
								        <tr>
											<td class="text-center"><c:out value="${status.count }"/></td>
											<td class="text-center">
											<func:minguo value="${data.fuelDate}"/>
											</td>
											<td class="text-center">
											<c:choose>
												 <c:when test="${not empty data.fuelTime}">
													<c:out value="${fn:substring(data.fuelTime, 0,2)}"/>:<c:out value="${fn:substring(data.fuelTime, 2,-1)}"/>
												 </c:when>
											</c:choose>
											</td>
											<td class="text-right"><c:out value="${data.gasMoney }"/></td>
											<td class="text-right"><c:out value="${data.gasAmount }"/></td>
										</tr>
							    </c:forEach>
							</tbody>
						</table>
					</div>

					<!-- 里程資料 -->
					<div class="tab-pane table-responsive" id="mileageListData" role="tabpanel" aria-labelledby="mileageListData-tab">
			            <tags:form-row>
			              	 <label class="col-form-label text-left col-3">車牌號碼：<c:out value="${caseData.eip07w010CarDataList[0].carno1}-${caseData.eip07w010CarDataList[0].carno2}"/></label>
							<label class="col-form-label text-left col-3">車輛種類：<c:out value="${caseData.eip07w010CarDataList[0].carTypeNm}"/></label>
							<label class="col-form-label text-left col-3">財產編號：<c:out value="${caseData.eip07w010CarDataList[0].owned}"/></label>
			            </tags:form-row>
						<tags:form-row></tags:form-row>
						<table class="table" id="mileageListDataTab">
							<thead>
							<tr>
								<th style="vertical-align:middle;">序號</th>
								<th style="vertical-align:middle;">用車日期</th>
								<th style="vertical-align:middle;">用車時間起迄</th>
								<th style="vertical-align:middle;">出場里程數</th>
								<th style="vertical-align:middle;">回場里程數</th>
								<th style="vertical-align:middle;">行駛公里數</th>
								<th style="vertical-align:middle;">耗油量(公升)</th>
<%--
<%--							</tr>--%>
							</thead>
							<tbody>
							<c:forEach items="${caseData.mileageList}" var="data" varStatus="status">
								        <tr>
											<td class="text-center"><c:out value="${status.count }"/></td>
											<td class="text-center">
												<func:minguo value="${data.useDate}"/>
											</td>
											<td class="text-center">
												<c:out value="${fn:substring(data.useTimeS, 0,2)}"/>:<c:out value="${fn:substring(data.useTimeS, 2,-1)}"/>
												-<c:out value="${fn:substring(data.useTimeE, 0,2)}"/>:<c:out value="${fn:substring(data.useTimeE, 2,-1)}"/>
											</td>
											<td class="text-right"><c:out value="${data.milageStart }"/></td>
											<td class="text-right"><c:out value="${data.milageEnd }"/></td>
											<td class="text-right"><c:out value="${data.milage }"/></td>
											<td class="text-right"><c:out value="${data.gasUsed }"/></td>
										</tr>
						        </c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			<form:hidden id="tapTy" path="tapTy" />
	    	</form:form>
	    </tags:fieldset>
    </jsp:attribute>

<jsp:attribute name="footers">
<script>
	var tapTy=$("#tapTy").val();
	var config = getDataTablesConfig(null, null, 100);
	var table = $(' #baseTab, #oilListTab, #mileageListDataTab').DataTable(config);
	var table = $('#oil').DataTable({
		...config,
		paging: true, // 增加分頁功能
		pageLength: 10 // oil每頁顯示數量
	});
	var baseTab = document.getElementById('base-tab');
	var oilTab = document.getElementById('oilList-tab');
	document.addEventListener('DOMContentLoaded', function() {
		if (tapTy==='O'){
			oilTab.click();
			$('#btnGasAdd').show();
			$('#btnUpdate').hide();
			$('#btnDelete').hide();
		}else {
			baseTab.click();
			$('#btnUpdate').show();
			$('#btnGasAdd').hide();
		}
	});

	$(function() {
		// controlCarStatusk();
			$('#base-tab').click(function(){
				$('#btnUpdate').show();
				$('#btnBack').show();
				$('#btnDelete').show();
				$('#btnGasAdd').hide();
			})

		    $('#oilList-tab').click(function(){
				$('#btnUpdate').hide();
				$('#btnBack').show();
				$('#btnDelete').hide();
				$('#btnGasAdd').show();
			})

			$('#mileageList-tab').click(function(){
				$('#btnUpdate').hide();
				$('#btnBack').show();
				$('#btnDelete').hide();
				$('#btnGasAdd').hide();
			})

			// controlBtn();
            $('#btnInster').click(function() {
           		$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_carInster.action" />').submit();
            });

			$('#btnBack').click(function() {
				$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_enter.action" />').submit();
			});

			$('#btnDelete').click(function() {
				if (${not empty caseData.mileageList}||${not empty caseData.oilList}){
					showConfirm("車籍已有油料紀錄或里程紀錄，不可刪除");
				}else{
					showConfirm('確定要刪除資料？', () => {
						$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_carDelete.action" />').submit();
					});
				}
			});

			$('#btnUpdate').click(function() {
				showConfirm('確定要修改資料？', () => {
					$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_updateCar.action" />').submit();
				});
			});

		$('#btnGasAdd').click(function() {
				$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_gasAdd.action" />').submit();
		});

		$('#bossMk').change(function() {//控制首長專用下拉式選單
			controlCarStatusk();
		});

		function controlCarStatusk() {
			var bossMk =$("#bossMk option:selected").val();
			if (bossMk=='Y'){
				$("#carStatus").val('2');
			}else{
				$("#carStatus").val('1');
			}
		}
         });
</script>
</jsp:attribute>
</tags:layout>