<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->

	<tags:button id="btnUpdate">
    	修改<i class="fas fa-search"></i>
      </tags:button>

	  <tags:button id="btnRmUpdate">
    	異動申請<i class="fas fa-search"></i>
      </tags:button>

	 <tags:button id="btnPrint">
    	列印<i class="fas fa-search"></i>
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
				<div class="col-4 col-md form-inline">
					<form:input id="apply_user" name="apply_user" path="detailsList[0].apply_user" cssClass="form-control"   size="8"
								maxlength="8" disabled="true"/>
				</div>
					<label class="col-form-label text-left ">申請單位:</label>
				<div class="col-4 col-md form-inline">
					<form:input id="apply_dept" name="apply_dept" path="detailsList[0].apply_dept" cssClass="form-control"   size="8"
								maxlength="8" disabled="true"/>
				</div>
					<label class="col-form-label text-left  ">申請日期:</label>
				<div class="col-4 col-md form-inline">
					<form:input id="apply_date" name="apply_date" path="detailsList[0].apply_date" cssClass="form-control"   size="7"
								maxlength="7" disabled="true"/>
				</div>
			</tags:form-row>
			<tags:form-row>
				<div class="col-6 col-md form-inline">
					<br>
					<label class="col-form-label text-left">[申請相關資料]:</label>
					<br>
				</div>
			</tags:form-row>
			<tags:form-row>
						<label class="col-form-label text-left  star">用車事由:</label>
					<div class="col-6 col-md form-inline text-left ">
						<form:input id="apply_memo" name="apply_memo" path="detailsList[0].apply_memo" cssClass="form-control"   size="50"
								maxlength="50" />
					</div>
			</tags:form-row>
			<tags:form-row>
					<label class="col-form-label text-left  star">目的地:</label>
				<div class="col-6 col-md form-inline">
					<form:input id="destination" name="destination" path="detailsList[0].destination" cssClass="form-control"   size="50"
								maxlength="50" />
				</div>
			</tags:form-row>
			<tags:form-row>
					   <label class="col-form-label text-left">車輛總類:</label>
				   <div class="col-6 col-md form-inline">
					   <form:select id="apply_car_type"  name="apply_car_type"  path="detailsList[0].apply_car_type" cssClass="form-control">
	                    	<form:option value="1">4人座</form:option>
	                        <c:forEach var="item" items="${caseData.carTyList}" varStatus="status">
	                            <form:option value="${item.codeno}"><c:out value="${item.codename}"/></form:option>
	                        </c:forEach>
	                    </form:select>
				   </div>
							<label class="col-form-label text-left  star">人數:</label>
						<div class="col-6 col-md form-inline">
							<form:input id="num_of_people" name="num_of_people" path="detailsList[0].num_of_people" cssClass="form-control"   size="3" maxlength="3" />
						</div>
			</tags:form-row>
			<tags:form-row>
						<label class="col-form-label text-left  ">用車日期:</label>
					<div class="col-6 col-md form-inline">
						<form:input id="using_date" name="using_date" path="detailsList[0].using_date" cssClass="form-control num_only dateTW"   size="7"
									maxlength="7" disabled="true"/>
					</div>
			</tags:form-row>
			<tags:form-row>
				 <label class="col-form-label text-left  ">用車時間:</label>
			 <div class="d-flex">
				   <form:select id="starH"  name="starH"  path="detailsList[0].starH" cssClass="form-control">
					<form:option value=""></form:option>
					<c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
						<form:option value="${hour}"><c:out value="${hour}"/></form:option>
					</c:forEach>
				</form:select>
				 <span class="input-group-text px-1">:</span>
				 <form:select id="starM"  name="starM"  path="detailsList[0].starM" cssClass="form-control">
					<form:option value=""></form:option>
					<c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
						<form:option value="${minute}"><c:out value="${minute}"/></form:option>
					</c:forEach>
				</form:select>
				 <span class="input-group-text px-1">~</span>
				 <form:select id="endH"  name="endH"  path="detailsList[0].endH" cssClass="form-control">
					<form:option value=""></form:option>
					<c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
						<form:option value="${hour}"><c:out value="${hour}"/></form:option>
					</c:forEach>
				</form:select>
				 <span class="input-group-text px-1">:</span>
				 <form:select id="endM"  name="endM"  path="detailsList[0].endM" cssClass="form-control">
					<form:option value=""></form:option>
					<c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
						<form:option value="${minute}"><c:out value="${minute}"/></form:option>
					</c:forEach>
				</form:select>
			 </div>
			</tags:form-row>
			<tags:form-row>
							<label class="col-form-label text-left  star">表單狀態:</label>
						<div class="col-6 col-md form-inline">
							<form:input id="codeName" name="codeName" path="detailsList[0].codeName" cssClass="form-control"    disabled="true"/>
						</div>
			</tags:form-row>

			<div id="mark">
				<tags:form-row>
							<label class="col-form-label text-left ">併單註記:</label>
						<div class="col-6 col-md form-inline">
							<form:input id="combine_mk" name="combine_mk" path="detailsList[0].combine_mk" cssClass="form-control"    disabled="true" size="6" maxlength="6"/>
						</div>
							<label class="col-form-label text-left ">併單派車單號:</label>
						<div class="col-6 col-md form-inline">
							<form:input id="combine_applyid" name="combine_applyid" path="detailsList[0].combine_applyid" cssClass="form-control"    disabled="true" size="6" maxlength="6"/>
						</div>
				</tags:form-row>

			</div>

			<div id="car">
					<tags:form-row>
						<div class="col-6 col-md form-inline">
							<br>
							<label class="col-form-label text-left">[車輛相關資料]:</label>
							<br>
						</div>
					</tags:form-row>
				<tags:form-row>
							<label class="col-form-label text-left ">派車單號:</label>
						<div class="col-6 col-md form-inline">
							<form:input id="applyid" name="applyid" path="detailsList[0].applyid" cssClass="form-control"  disabled="true"  size="15" maxlength="15"/>
						</div>
				</tags:form-row>
				<tags:form-row>
						<label class="col-form-label text-left ">駕駛人姓名:</label>
					<div class="col-6 col-md form-inline" >
							<form:input id="name" name="name" path="detailsList[0].name" cssClass="form-control"    size="6" maxlength="6"/>
					</div>
							<label class="col-form-label text-left ">手機號碼:</label>
						<div class="col-6 col-md form-inline">
							<form:input id="cellphone" name="cellphone" path="detailsList[0].cellphone" cssClass="form-control"    size="10" maxlength="10"/>
						</div>
				</tags:form-row>
				<tags:form-row>

							<label class="col-form-label text-left ">車牌車號:</label>
						<div class="col-12 col-md form-inline">
							<table>
								<tr>
									<td><form:input id="carno1" name="carno1"  path="detailsList[0].carno1" cssClass="form-control num_eng_only" size="3" maxlength="3" /></td>
									<td>-</td>
									<td><form:input id="carno2" name="carno2"  path="detailsList[0].carno2" cssClass="form-control num_eng_only" size="4" maxlength="4" /></td>
								</tr>
							</table>
						</div>
					</tags:form-row>
				<tags:form-row>
							<label class="col-form-label text-left ">車輛種類:</label>
						<div class="col-6 col-md form-inline">
							<form:input id="cartype" name="cartype" path="detailsList[0].cartype" cssClass="form-control"   size="6" maxlength="6"/>

						</div>
							<label class="col-form-label text-left ">顏色:</label>
						<div class="col-6 col-md form-inline">
							<form:input id="carcolor" name="carcolor" path="detailsList[0].carcolor" cssClass="form-control"    disabled="true" size="6" maxlength="6"/>
						</div
				</tags:form-row>
			</div>
				<tags:form-row id="checkMk">

						<label class="col-form-label text-left ">異動註記</label>
						<div  class="col-6 col-md form-inline" >
							<form:checkbox name="checkMk" path="checkMk" value="true"  />
<%--							<c:out value="${caseData.checkMk}"></c:out>--%>
						</div>



							<label id="rmMemoLabel" class="col-form-label text-left star">異動原因:</label>
						<div id="rmMemo" class="col-6 col-md form-inline">
							<form:select id="rmMemo" name="rmMemo" path="rmMemo" cssClass="form-control">
								<form:option value="1">1.原申請資料需變更</form:option>
								<form:option id="2" value="2">2.取消申請</form:option>
							</form:select>
						</div>
				</tags:form-row>
			<div id="changMk">
				<tags:form-row>
					<br>
				<label class="col-form-label text-left">[異動申請相關資料]:</label>
					<br>
				</tags:form-row>
				<tags:form-row>
						<label class="col-form-label text-left  star">目的地:</label>
					<div class="col-6 col-md form-inline" >
							<form:input id="destination" name="destination" path="changeMkList[0].destination" cssClass="form-control"    size="50" maxlength="50"/>

					</div>
				</tags:form-row>
				<tags:form-row>
						<label class="col-form-label text-left  star">用車事由:</label>
					<div sclass="col-6 col-md form-inline" >
							<form:input id="rmpply_memo" name="rmapply_memo" path="changeMkList[0].apply_memo" cssClass="form-control"    size="50" maxlength="50"/>
					</div>
				</tags:form-row>
				<tags:form-row>
						<label class="col-form-label text-left ">車輛總類:</label>
						<div class="col-6 col-md form-inline">
							<form:select id="cartype"  name="cartype"  path="changeMkList[0].apply_car_type" cssClass="form-control">
	                    	<form:option value="1">4人座</form:option>
	                        <c:forEach var="item" items="${caseData.carTyList}" varStatus="status">
	                            <form:option value="${item.codeno}"><c:out value="${item.codename}"/></form:option>
	                        </c:forEach>
	                    </form:select>
					</div>
							<label class="col-form-label text-left star ">人數:</label>
						<div class="col-6 col-md form-inline">
							<form:input id="num_of_people" name="num_of_people" path="changeMkList[0].num_of_people" cssClass="form-control"    size="3" maxlength="3"/>
						</div>
				</tags:form-row>
				<tags:form-row>
						<label class="col-form-label text-left  star">用車日期:</label>
						<div class="col-6 col-md form-inline">
							<form:input id="usingDateMk" name="usingDateMk" path="detailsList[0].using_date" cssClass="form-control"    size="7" maxlength="7" disabled="true"/>
					</div>
				</tags:form-row>
				<div style="width:35%" class="d-flex">
					<label class="col-form-label text-left star ">用車時間:</label>
					<form:select id="starH"  name="starH"  path="changeMkList[0].starH" cssClass="form-control">
					<form:option value=""></form:option>
					<c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
						<form:option value="${hour}"><c:out value="${hour}"/></form:option>
					</c:forEach>
				</form:select>
					<span class="input-group-text px-1">:</span>
					<form:select id="starM"  name="starM"  path="changeMkList[0].starM" cssClass="form-control">
					<form:option value=""></form:option>
					<c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
						<form:option value="${minute}"><c:out value="${minute}"/></form:option>
					</c:forEach>
				</form:select>
					<span class="input-group-text px-1">~</span>
					<form:select id="endH"  name="endH"  path="changeMkList[0].endH" cssClass="form-control">
					<form:option value=""></form:option>
					<c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
						<form:option value="${hour}"><c:out value="${hour}"/></form:option>
					</c:forEach>
				</form:select>
					<span class="input-group-text px-1">:</span>
					<form:select id="endM"  name="endM"  path="changeMkList[0].endM" cssClass="form-control">
					<form:option value=""></form:option>
					<c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
						<form:option value="${minute}"><c:out value="${minute}"/></form:option>
					</c:forEach>
				</form:select>
				</div>
			</div>
				<br>
				<tags:form-row>
					<label class="col-form-label text-left " >備註:1.表單狀態為1已送出/2申請主管已審核時，才可進行原申請資料修改或刪除<br>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.表單狀態為3派全程/4派單程/5已派滿時，有異動需求時，需勾選「異動註記」欄位告知<br>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;秘書處派車申請有異動，且按「異動申請」後，原派車車輛資料刪除待重新指派後才有相關資料<br></label>
				</tags:form-row>
		<form:hidden id="combine_mk" path="combine_mk" />
		<form:hidden id="carprocess_status" path="detailsList[0].carprocess_status" />
<%--		<form:hidden id="combineMk" path="detailsList[0].combine_mk" />--%>
	  </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

        $(function() {
			controlDiv();
			controlcheckMk();
			btnRmUpdate();
			CombineMk();

			$('#btnUpdate').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_update.action" />').submit();
			});
			$('#btnRmUpdate').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_changeApplication.action" />').submit();
            });

			$('#btnPrint').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_print.action" />').submit();
			});

			$('#btnClearn').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_enter.action" />').submit();
			});

			function controlDiv(){//控制[申請相關資料]及[車輛相關資料]
				var combineMk=$("#combine_mk").val();
				var carprocess_status=$("#carprocess_status").val();
				if(combineMk == 'Y'){
					$("#mark").show();
				} else {
					$("#mark").hide();
				}
				if(carprocess_status == '3'||carprocess_status == '4'||carprocess_status == '6'||carprocess_status == '7'){//後面加了67
					$("#car").show();
					$("#btnPrint").show();
				} else {
					$("#car").hide();
					$("#btnPrint").hide();
				}
			}

			function controlcheckMk(){//控制是否顯示"異動註記"checkBox
				var carprocess_status=$("#carprocess_status").val();
				if(carprocess_status == '3'||carprocess_status == '4'||carprocess_status == '5'||carprocess_status == '6'||carprocess_status == '7'){
					$("#checkMk").show();
				} else {
					$("#checkMk").hide();
				}

				if (carprocess_status==1){
					$("#btnUpdate").show();
				}else{
					$("#btnUpdate").hide();
				}

				if (carprocess_status!=2){
					$("#using_date").removeAttr("disabled");
				}
			}

				$('input[name="checkMk"]:checkbox').change(function() {//控制"異動註記"是否勾選
					btnRmUpdate();
				});


			function btnRmUpdate() {
				var check =$("input[name=checkMk]:checked").val();
				if(check){
					$("#btnRmUpdate").show();
					$("#rmMemo").show();
					$("#rmMemoLabel").show();
					controlChangMk();
				} else {
					$("#btnRmUpdate").hide();
					$("#rmMemo").hide()
					$("#rmMemoLabel").hide()
					$("#changMk").hide();
				}
			}

			$('#rmMemo').change(function() {//控制異動原因下拉式選單
				controlChangMk();
			});

			function controlChangMk() {
				var rmMemo =$("#rmMemo option:selected").val();
				if (rmMemo=='1'){
					$("#changMk").show();
				}else {
					$("#changMk").hide();
				}
			}
			function CombineMk() {//控制併單註記為Y時
				var combineMk = $("#combine_mk").val();
				if (combineMk == 'Y') {
					$('#rmMemo option[value="1"]').remove()
				}
			}
         });
</script>
</jsp:attribute>
</tags:layout>