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

	  <tags:button id="btnUpdate">
    	修改<i class="fas fa-user-plus"></i>
      </tags:button>

	  <tags:button id="btnDelete">
    	刪除<i class="fas fa-trash-alt"></i>
      </tags:button>

	 <tags:button id="btnBack">
    	回主畫面<i class="fas fa-user-plus"></i>
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
							<div style="width: 35%" class="d-flex" >
								<label class="col-form-label text-left star">車牌號碼:</label>
								<table>
									<tr>
										<td><form:input id="carno1" name="carno1" path="eip07w010CarDataList[0].carno1" cssClass="form-control" size="3" maxlength="3" disabled="true"  oninput="value=value.replace(/[^\d]/g,'')"/></td>
										<td>-</td>
										<td><form:input id="carno2" name="carno2" path="eip07w010CarDataList[0].carno2" cssClass="form-control" size="4" maxlength="4" disabled="true"  oninput="value=value.replace(/[^\d]/g,'')"/></td>
									</tr>
								</table>
							</div>
								<label class="col-form-label text-left star">車輛種類:</label>
								<form:select id="carType" path="eip07w010CarDataList[0].carType" cssClass="form-control">
                    			<form:option value="1">1:4人座</form:option>
                    			<form:option value="2">2:7人座</form:option>
                  		 		</form:select>
						</tags:form-row>
						<tags:form-row>
							<div style="width: 35%" class="d-flex">
								<label class="col-form-label text-left star ">財產編號:</label>
								<div>
									<form:input id="owned" name="owned" path="eip07w010CarDataList[0].owned" cssClass="form-control" size="7" maxlength="6"/>
								</div>
							</div>

								<label class="col-form-label text-left">購置年份:</label>
									<div>
										<form:input id="carYear" name="carYear" path="eip07w010CarDataList[0].carYear" cssClass="form-control form-control num_only padL" size="3" maxlength="3" />
									</div>
			            </tags:form-row>
						<tags:form-row>
							<div style="width: 35%" class="d-flex">
								<label class="col-form-label text-left ">車輛廠牌:</label>
								<div>
									<form:input id="carSource" name="carSource" path="eip07w010CarDataList[0].carSource" cssClass="form-control" size="7" maxlength="6"/>
								</div>
							</div>
								<label class="col-form-label text-left">顏色:</label>
									<div>
										<form:input id="carColor" name="carColor" path="eip07w010CarDataList[0].carColor" cssClass="form-control" size="7" maxlength="6"/>
									</div>
							&emsp;&emsp;&emsp;&emsp;
								<label class="col-form-label text-left">排放量cc:</label>
									<div>
										<form:input id="ccSize" name="ccSize" path="eip07w010CarDataList[0].ccSize" cssClass="form-control num_only" size="7" maxlength="6"/>
									</div>
						</tags:form-row>
						<tags:form-row>
							<div style="width: 35%" class="d-flex">
								<label class="col-form-label text-left  star">首長專用車:</label>
								<div>
										<form:select id="bossMk" path="eip07w010CarDataList[0].bossMk" cssClass="form-control">
										<form:option value="Y">是</form:option>
										<form:option value="N">否</form:option>
									</form:select>
								</div>
							</div>
								<label class="col-form-label text-left">首長:</label>
									<div>
										<form:input id="bossName"  name="bossName"  path="eip07w010CarDataList[0].bossName" cssClass="form-control" size="7" maxlength="6"/>
									</div>
						</tags:form-row>

						<tags:form-row>
								<label class="col-form-label text-left">車輛狀態:</label>
								<div>
									<form:select id="carStatus" path="eip07w010CarDataList[0].carStatus" cssClass="form-control">
										<form:option value="1">1.可使用</form:option>
										<form:option value="2">2.不可使用-首長</form:option>
										<form:option value="3">3.維修中</form:option>
										<form:option value="4">4.報廢</form:option>
									</form:select>
								</div>
						</tags:form-row>

						<tags:form-row>
							<br>
								<label class="col-form-label text-left">[保險資料]:</label>
							<br>
						</tags:form-row>

						<tags:form-row>
							<label class="col-form-label text-left">保險公司:</label>
								<div>
									<form:input id="insuranceCompany"  name="insuranceCompany"  path="eip07w010CarDataList[0].insuranceCompany" cssClass="form-control" size="40" maxlength="100"/>
								</div>

						</tags:form-row>

						<tags:form-row>
							<div style="width: 35%" class="d-flex">
								<label class="col-form-label text-left">保險期間(起):</label>
								<div>
									<form:input id="insuranceStart"  name="insuranceStart"  path="eip07w010CarDataList[0].insuranceStart" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
								</div>
							</div>

							<label class="col-form-label text-left">保險期間(迄):</label>
								<div>
									<form:input id="InsuranceEnd"  name="InsuranceEnd"  path="eip07w010CarDataList[0].InsuranceEnd" cssClass="form-control num_only dateTW" size="7" maxlength="7"/>
								</div>
						</tags:form-row>
						<tags:form-row></tags:form-row>
					</div>

					<!-- 油料 -->
					<div class="tab-pane table-responsive" id="oilList" role="tabpanel" aria-labelledby="oilList-tab">
			            <tags:form-row>
							 <label class="col-form-label text-left col-3">車牌號碼：<c:out value="${caseData.eip07w010CarDataList[0].carno1}-${caseData.eip07w010CarDataList[0].carno2}"/></label>
							<label class="col-form-label text-left col-3">車輛種類：<c:out value="${caseData.eip07w010CarDataList[0].carType}"/></label>
							<label class="col-form-label text-left col-3">財產編號：<c:out value="${caseData.eip07w010CarDataList[0].owned}"/></label>
			            </tags:form-row>
						<tags:form-row></tags:form-row>
						<table class="table" id="oilListTab">
							<thead>
							<tr>
								<th style="vertical-align:middle;">序號</th>
								<th style="vertical-align:middle;">加油日期</th>
								<th style="vertical-align:middle;">時間</th>
								<th style="vertical-align:middle;">加油金額</th>
								<th style="vertical-align:middle;">加油量</th>
<%--
<%--							</tr>--%>
							</thead>
							<tbody>
							<c:forEach items="${oilList}" var="data" varStatus="status">
								        <tr>
											<td class="text-left"><c:out value="${status.count }"/></td>
											<td class="text-left"><c:out value="${data.fuelDate }"/></td>
											<td class="text-left"><c:out value="${data.fuelTime }"/></td>
											<td class="text-left"><c:out value="${data.gasMoney }"/></td>
											<td class="text-left"><c:out value="${data.gasAmount }"/></td>
										</tr>>
							    </c:forEach>
							</tbody>
						</table>
					</div>

					<!-- 里程資料 -->
					<div class="tab-pane table-responsive" id="mileageListData" role="tabpanel" aria-labelledby="mileageListData-tab">
			            <tags:form-row>
			              	 <label class="col-form-label text-left col-3">車牌號碼：<c:out value="${caseData.eip07w010CarDataList[0].carno1}-${caseData.eip07w010CarDataList[0].carno2}"/></label>
							<label class="col-form-label text-left col-3">車輛種類：<c:out value="${caseData.eip07w010CarDataList[0].carType}"/></label>
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
								<th style="vertical-align:middle;">耗油量</th>
<%--
<%--							</tr>--%>
							</thead>
							<tbody>
							<c:forEach items="${mileageList}" var="data" varStatus="status">
								        <tr>
											<td class="text-left"><c:out value="${status.count }"/></td>
											<td class="text-left"><c:out value="${data.useDate }"/></td>
											<td class="text-left"><c:out value="${data.useTimeS}-${data.useTimeE}"/></td>
											<td class="text-left"><c:out value="${data.milageStart }"/></td>
											<td class="text-left"><c:out value="${data.milageEnd }"/></td>
											<td class="text-left"><c:out value="${data.milage }"/></td>
											<td class="text-left"><c:out value="${data.gasUsed }"/></td>
										</tr>
						        </c:forEach>
							</tbody>
						</table>
					</div>
				</div>

	    	</form:form>
	    </tags:fieldset>
    </jsp:attribute>

<jsp:attribute name="footers">
<script>
	var config = getDataTablesConfig(null, null, 100);
	var table = $(' #baseTab, #oilListTab, #mileageListDataTab').DataTable(config);
	var baseTab = document.getElementById('base-tab');
	document.addEventListener('DOMContentLoaded', function() {
		baseTab.click();
	});
	$(function() {

			$('#base-tab').click(function(){
				$('#btnUpdate').show();
				$('#btnBack').show();
				$('#btnDelete').show();
			})

		    $('#oilList-tab').click(function(){
				$('#btnUpdate').hide();
				$('#btnBack').show();
				$('#btnDelete').hide();
			})

			$('#mileageList-tab').click(function(){
				$('#btnUpdate').hide();
				$('#btnBack').show();
				$('#btnDelete').hide();
			})

			// controlBtn();
            $('#btnInster').click(function() {
           		$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_carInster.action" />').submit();
            });

			$('#btnBack').click(function() {
				$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_enter.action" />').submit();
			});

			$('#btnDelete').click(function() {
				showConfirm('確定要刪除資料？', () => {
					$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_carDelete.action" />').submit();
				});
			});

			$('#btnUpdate').click(function() {
				showConfirm('確定要修改資料？', () => {
					$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_updateCar.action" />').submit();
				});
			});
			function controlBtn(){
				$("#baseTab").click();
				var workTy=$("#workTy").val();
				if(workTy == 'Q'){
					$("#btnUpdate").show();
					$("#btnDelete").show();
					$("#btnInster").hide();
				} else {
					$("#btnUpdate").hide();
					$("#btnDelete").hide();
				}
			}
         });
</script>
</jsp:attribute>
</tags:layout>