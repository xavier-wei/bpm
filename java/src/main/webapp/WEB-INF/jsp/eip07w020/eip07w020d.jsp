<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->


	<tags:button id="btnUpdate">
    	修改<i class="fas fa-user-edit"></i>
      </tags:button>

	  <tags:button id="btnRmUpdate">
    	異動申請<i class="fas fa-user-check"></i>
      </tags:button>

	 <tags:button id="btnPrint">
    	列印<i class="fas fa-download"></i>
      </tags:button>

	<tags:button id="btnDelete">
    	刪除<i class="fas fa-trash-alt"></i>
      </tags:button>

	  <tags:button id="btnClearn">
    	回主畫面<i class="fas fa-reply"></i>
      </tags:button>

</jsp:attribute>
	<jsp:attribute name="contents">
	<tags:fieldset legend="派車單明細" >
		<form:form id="eip07w020Form" name="eip07w020Form" modelAttribute="${caseKey}" method="POST">

			<tags:form-row>
				<div  class="col-md d-flex">
					<form:label cssClass="col-form-label" path="lable">申請人：</form:label>
					<label class="col-form-label text-left col-3" ><func:username userid="${caseData.detailsList[0].apply_user}"/></label>


				</div>
				<div  class="col-md d-flex">
					<form:label cssClass="col-form-label" path="lable">申請單位：</form:label>
					<label class="col-form-label text-left col-3" ><func:dept deptid="${caseData.detailsList[0].apply_dept}"/></label>
				</div>
				<div  class="col-md d-flex">
					<form:label cssClass="col-form-label" path="lable">申請日期：</form:label>
					<label class="col-form-label text-left col-3" ><func:minguo value="${caseData.detailsList[0].apply_date}"/></label>
				</div>
			</tags:form-row>
			<tags:form-row>
				<div class="col-md d-flex">
					<form:label cssClass="col-form-label" path="lable">派車單號：</form:label>
					<form:input id="applyid" name="applyid" path="detailsList[0].applyid" cssClass="form-control"  disabled="true"  size="15" maxlength="15"/>
				</div>
			</tags:form-row>



			<div id="rmData">
						<tags:form-row>
					<div  class="col-md d-flex">
						<br>
						<form:label cssClass="col-form-label red" path="lable">[異動前申請相關資料]：</form:label>
						<br>
					</div>
				</tags:form-row>
				<tags:form-row>
						<div  class="col-md d-flex">
							<form:label cssClass="col-form-label star" path="lable">用車事由：</form:label>
							<form:textarea id="b_apply_memo" path="detailsList[0].b_apply_memo" cssClass="form-control" cols="70" rows="3" maxlength="100" disabled="true"/>
						</div>
				</tags:form-row>
				<tags:form-row>
					<div  class="col-md d-flex">
						<form:label cssClass="col-form-label star" path="lable">目的地：</form:label>
						<form:textarea id="b_destination" path="detailsList[0].b_destination" cssClass="form-control" cols="70" rows="3" maxlength="100" disabled="true"/>
					</div>
				</tags:form-row>
				<tags:form-row>
					<div c class="col-md-4 d-flex">
						<form:label cssClass="col-form-label" path="lable">車輛總類：</form:label>
						<form:select id="b_apply_car_type"  name="b_apply_car_type"  path="detailsList[0].b_apply_car_type" cssClass="form-control" disabled="true">
							<form:option value="1">4人座</form:option>
							<c:forEach var="item" items="${caseData.carTyList}" varStatus="status">
								<form:option value="${item.codeno}"><c:out value="${item.codename}"/></form:option>
							</c:forEach>
						</form:select>
					</div>
					<div  class="col-md-4 d-flex">
						<form:label cssClass="col-form-label star" path="lable">人數：</form:label>
						<form:input id="b_num_of_people" name="b_num_of_people" path="detailsList[0].b_num_of_people" cssClass="form-control"   size="3" maxlength="3" disabled="true"/>
					</div>
				</tags:form-row>
				<tags:form-row>
						<div  class="col-md d-flex">
							<form:label cssClass="col-form-label star" path="lable">用車日期：</form:label>
							<form:input  path="detailsList[0].using_date" cssClass="form-control num_only dateTW"   size="9"
										maxlength="9" disabled="true"/>
						</div>
				</tags:form-row>
				<tags:form-row>
						<div  class="col-md d-flex">
					<form:label cssClass="col-form-label" path="lable">用車時間：</form:label>
					<form:select   path="detailsList[0].rmStarH" cssClass="form-control " disabled="true">
						<c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
							<form:option value="${hour}"><c:out value="${hour}"/></form:option>
						</c:forEach>
					</form:select>
					<span class="input-group-text px-1">:</span>
					<form:select   path="detailsList[0].rmStarM" cssClass="form-control" disabled="true">
						<c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
							<form:option value="${minute}"><c:out value="${minute}"/></form:option>
						</c:forEach>
					</form:select>
					<span class="input-group-text px-1">-</span>
					<form:select   path="detailsList[0].rmEndH" cssClass="form-control" disabled="true">
						<c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
							<form:option value="${hour}"><c:out value="${hour}"/></form:option>
						</c:forEach>
					</form:select>
					<span class="input-group-text px-1">:</span>
					<form:select   path="detailsList[0].rmEndM" cssClass="form-control" disabled="true">
						<c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
							<form:option value="${minute}"><c:out value="${minute}"/></form:option>
						</c:forEach>
					</form:select>
					</tags:form-row>
								</div>
			</div>

			<div id="data">
					<tags:form-row>
				<div  class="col-md d-flex">
					<br>
					<form:label cssClass="col-form-label" path="lable">[申請相關資料]：</form:label>
					<br>
				</div>
			</tags:form-row>
				<tags:form-row>
					<div  class="col-md d-flex">
						<form:label cssClass="col-form-label star" path="lable">用車事由：</form:label>
						<form:textarea id="apply_memo" path="detailsList[0].apply_memo" cssClass="form-control" cols="70" rows="3" maxlength="100"/>
					</div>
			</tags:form-row>
				<tags:form-row>
				<div  class="col-md d-flex">
					<form:label cssClass="col-form-label star" path="lable">目的地：</form:label>
					<form:textarea id="destination" path="detailsList[0].destination" cssClass="form-control" cols="70" rows="3" maxlength="100"/>
				</div>
			</tags:form-row>
				<tags:form-row>
					<div c class="col-md-4 d-flex">
						<form:label cssClass="col-form-label" path="lable">車輛總類：</form:label>
						<form:select id="apply_car_type"  name="apply_car_type"  path="detailsList[0].apply_car_type" cssClass="form-control">
<%--	                    	<form:option value="1">4人座</form:option>--%>
	                        <c:forEach var="item" items="${caseData.carTyList}" varStatus="status">
	                            <form:option value="${item.codeno}"><c:out value="${item.codename}"/></form:option>
	                        </c:forEach>
	                    </form:select>
					</div>
					<div  class="col-md-4 d-flex">
						<form:label cssClass="col-form-label star" path="lable">人數：</form:label>
						<form:input id="num_of_people" name="num_of_people" path="detailsList[0].num_of_people" cssClass="form-control"   size="3" maxlength="3" />
					</div>
			</tags:form-row>
				<tags:form-row>
					<div  class="col-md d-flex">
						<form:label cssClass="col-form-label star" path="lable">用車日期：</form:label>
						<form:input id="using_date" name="using_date" path="detailsList[0].using_date" cssClass="form-control num_only dateTW"   size="9"
									maxlength="9" disabled="true"/>
					</div>
			</tags:form-row>
				<tags:form-row>
				<div  class="col-md d-flex" >
					<form:label cssClass="col-form-label" path="lable">用車時間：</form:label>
					<form:select id="starH"  name="starH"  path="detailsList[0].starH" cssClass="form-control">
<%--					<form:option value=""></form:option>--%>
					<c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
						<form:option value="${hour}"><c:out value="${hour}"/></form:option>
					</c:forEach>
				</form:select>
					<span class="input-group-text px-1">:</span>
					<form:select id="starM"  name="starM"  path="detailsList[0].starM" cssClass="form-control">
<%--					<form:option value=""></form:option>--%>
					<c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
						<form:option value="${minute}"><c:out value="${minute}"/></form:option>
					</c:forEach>
				</form:select>
					<span class="input-group-text px-1">~</span>
					<form:select id="endH"  name="endH"  path="detailsList[0].endH" cssClass="form-control">
<%--					<form:option value=""></form:option>--%>
					<c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
						<form:option value="${hour}"><c:out value="${hour}"/></form:option>
					</c:forEach>
				</form:select>
					<span class="input-group-text px-1">:</span>
					<form:select id="endM"  name="endM"  path="detailsList[0].endM" cssClass="form-control">
<%--					<form:option value=""></form:option>--%>
					<c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
						<form:option value="${minute}"><c:out value="${minute}"/></form:option>
					</c:forEach>
				</form:select>
				</tags:form-row>
				</div>

			</div>

					<tags:form-row>
						<div  class="col-md d-flex">
							<form:label cssClass="col-form-label star" path="lable">表單狀態：</form:label>
							<form:input id="codeName" name="codeName" path="detailsList[0].codeName" cssClass="form-control"    disabled="true"/>
						</div>
					</tags:form-row>
					<tags:form-row>
						<form:label id="status_5" cssClass="col-form-label " path="lable">[車輛相關資料]：公務車已滿請改其他大眾運輸工具。</form:label>
					</tags:form-row>

			<div id="mark">
				<tags:form-row>
						<div  class="col-md-4 d-flex">
							<form:label cssClass="col-form-label " path="lable">併單註記：</form:label>
							<form:input id="combine_mk" name="combine_mk" path="detailsList[0].combine_mk" cssClass="form-control"    disabled="true" size="6" maxlength="6"/>
						</div>
						<div  class="col-md-4 d-flex">
							<form:label cssClass="col-form-label " path="lable">併單派車單號：</form:label>
							<form:input id="combine_applyid" name="combine_applyid" path="detailsList[0].combine_applyid" cssClass="form-control"    disabled="true" size="15" maxlength="15"/>
						</div>
				</tags:form-row>

			</div>

			<div id="car">
					<tags:form-row>
						<div  class="col-md d-flex">
							<br>
							<form:label cssClass="col-form-label " path="lable">[車輛相關資料]：</form:label>
							<br>
						</div>
					</tags:form-row>

				<tags:form-row>
					<div   class="col-md-4 d-flex" >
						<form:label cssClass="col-form-label " path="lable">駕駛人姓名：</form:label>
						<label class="col-form-label text-left col-3" ><c:out value="${caseData.detailsList[0].name}"/></label>
					</div>
					<div   class="col-md-4 d-flex" >
										<form:label cssClass="col-form-label " path="lable">手機號碼：</form:label>
									<label class="col-form-label text-left col-3" ><c:out value="${caseData.detailsList[0].cellphone}"/></label>
					</div>
				</tags:form-row>
				<tags:form-row>
					<div   class="col-md d-flex" >
						<form:label cssClass="col-form-label " path="lable">車牌車號：</form:label>
						<label class="col-form-label text-left col-3"><c:out value="${caseData.detailsList[0].carno1}"/>-<c:out value="${caseData.detailsList[0].carno2}"/></label>
					</div>
				</tags:form-row>
				<tags:form-row>
					<div   class="col-md-4 d-flex" >
						<form:label cssClass="col-form-label " path="lable">車輛種類：</form:label>
					<label class="col-form-label text-left col-3"><c:out value="${caseData.detailsList[0].carTyNm}"/></label>
					</div>
					<div   class="col-md-4 d-flex" >
						<form:label cssClass="col-form-label " path="lable">顏色：</form:label>
					<label class="col-form-label text-left col-3"><c:out value="${caseData.detailsList[0].carcolor}"/></label>
					</div>
				</tags:form-row>
			</div>
			<div id="checkMk">
					<tags:form-row>
						<div  class="col-md d-flex">
							<br>
							<form:label cssClass="col-form-label " path="lable">[若派車後申請異動，請勾選異動註記]：</form:label>
							<br>
						</div>
					</tags:form-row>
					<tags:form-row >
						<div   class="col-md d-flex" >
							<form:label cssClass="col-form-label " path="lable">異動註記：</form:label>
							<form:checkbox name="checkMk" path="checkMk" value="true"  />
						</div>
					</tags:form-row>
					<tags:form-row >
						<div  class="col-md d-flex">
							<form:label id="rmMemoLabel" cssClass="col-form-label star" path="lable">異動原因：</form:label>
							<form:select id="rmMemo" name="rmMemo" path="rmMemo" cssClass="form-control">
								<form:option value="1">1.原申請資料需變更</form:option>
								<form:option id="2" value="2">2.取消申請</form:option>
							</form:select>
							<div id="cancelMg" class="text-danger" >&nbsp;&nbsp;&nbsp;&nbsp;請電話通知管理者及駕駛取消派車</div>
						</div>

					</tags:form-row>
			</div>
<%--			<div id="cancelMg">--%>
<%--				<tags:form-note>--%>
<%--                請電話通知管理者及駕駛取消派車--%>
<%--           		 </tags:form-note>--%>
<%--			</div>--%>
			<div id="changMk">
				<tags:form-row>
					<div  class="col-md d-flex" >
						<form:label cssClass="col-form-label star" path="lable">用車事由：</form:label>
						<form:textarea id="mkApply_memo" path="changeMkList[0].apply_memo" cssClass="form-control" cols="70" rows="3" maxlength="100" disabled="true"/>
					</div>
				</tags:form-row>
				<tags:form-row>
					<div  class="col-md d-flex" >
						<form:label cssClass="col-form-label star" path="lable">目的地：</form:label>
						<form:textarea id="mkDestination" path="changeMkList[0].destination" cssClass="form-control" cols="70" rows="3" maxlength="100" disabled="true"/>
					</div>
				</tags:form-row>

				<tags:form-row>
						<div  class="col-md-4 d-flex">
							<form:label cssClass="col-form-label" path="lable">車輛總類：</form:label>
							<form:select id="mkCartype"  name="mkCartype"  path="changeMkList[0].apply_car_type" cssClass="form-control" disabled="true">
							<form:option value="1">4人座</form:option>
							<c:forEach var="item" items="${caseData.carTyList}" varStatus="status">
								<form:option value="${item.codeno}"><c:out value="${item.codename}"/></form:option>
							</c:forEach>
						</form:select>
					</div>
					<div class="col-md-4 d-flex">
						<form:label cssClass="col-form-label star" path="lable">人數：</form:label>
						<form:input id="rmNum_of_people" name="rmNum_of_people" path="changeMkList[0].num_of_people" cssClass="form-control num_only"    size="3" maxlength="3" disabled="true"/>
					</div>
				</tags:form-row>
				<tags:form-row>
						<div  class="col-md-4 d-flex">
							<form:label cssClass="col-form-label star" path="lable">用車日期：</form:label>
							<form:input id="rmUsingDateMk" name="rmUsingDateMk" path="detailsList[0].using_date" cssClass="form-control  dateTW"    size="9" maxlength="7" disabled="true"/>
					</div>
				</tags:form-row>
				<div  class="col-md d-flex"  style="margin: 0; padding: 0;">
					<form:label cssClass="col-form-label star" path="lable">用車時間：</form:label>
					<form:select id="rmStarH"  name="rmStarH"  path="changeMkList[0].starH" cssClass="form-control" disabled="true">
<%--					<form:option value=""></form:option>--%>
					<c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
						<form:option value="${hour}"><c:out value="${hour}"/></form:option>
					</c:forEach>
				</form:select>
					<span class="input-group-text px-1">:</span>
					<form:select id="rmStarM"  name="rmStarM"  path="changeMkList[0].starM" cssClass="form-control" disabled="true">
<%--					<form:option value=""></form:option>--%>
					<c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
						<form:option value="${minute}"><c:out value="${minute}"/></form:option>
					</c:forEach>
				</form:select>
					<span class="input-group-text px-1">~</span>
					<form:select id="rmEndH"  name="rmEndH"  path="changeMkList[0].endH" cssClass="form-control" disabled="true">
<%--					<form:option value=""></form:option>--%>
					<c:forEach var="hour" items="${caseData.hourList}" varStatus="status">
						<form:option value="${hour}"><c:out value="${hour}"/></form:option>
					</c:forEach>
				</form:select>
					<span class="input-group-text px-1">:</span>
					<form:select id="rmEndM"  name="rmEndM"  path="changeMkList[0].endM" cssClass="form-control" disabled="true">
<%--					<form:option value=""></form:option>--%>
					<c:forEach var="minute" items="${caseData.minuteList}" varStatus="status">
						<form:option value="${minute}"><c:out value="${minute}"/></form:option>
					</c:forEach>
				</form:select>
				</div>
			</div>
			<div id="footer">
				<tags:form-row>
				<p><br>備註：1.表單狀態為1已送出/2申請主管已審核時，才可進行原申請資料修改或刪除<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.表單狀態為3派全程/4派單程/5已派滿/6併單-派全程/7併單-派單程時，<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;有異動需求時，需勾選「異動註記」欄位告知秘書處派車申請有異動，且按「異動申請」後，<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原派車車輛資料刪除待重新指派後才有相關資料<br></p>
				</tags:form-row>
			</div>
		<form:hidden id="combine_mk" path="combine_mk" />
		<form:hidden id="isSecretarial" path="isSecretarial" />
		<form:hidden id="carprocess_status" path="detailsList[0].carprocess_status" />
	  </form:form>
	</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

        $(function() {
			var isSecretarial=$("#isSecretarial").val();
			controlDiv();
			controlcheckMk();
			btnRmUpdate();
			CombineMk();
			status_U();
			isSecretarial_Y();
			$('#btnUpdate').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_update.action" />').submit();
			});
			$('#btnRmUpdate').click(function() {
				var carprocess_status=$("#carprocess_status").val();
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_changeApplication.action" />').submit();
            });

			$('#btnPrint').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_print.action" />').submit();
			});

			$('#btnClearn').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_enter.action" />').submit();
			});

			$('#btnDelete').click(function() {
				showConfirm('確定要刪除資料？', () => {
					$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_delete.action" />').submit();
				});
			});

			function controlDiv(){//控制[申請相關資料]及[車輛相關資料]
				var combineMk=$("#combine_mk").val();
				var carprocess_status=$("#carprocess_status").val();
				if(combineMk == 'Y'){
					$("#mark").show();
				} else {
					$("#mark").hide();
				}
				//車籍顯示
				if(carprocess_status == '3'||carprocess_status == '4'||carprocess_status == '6'||carprocess_status == '7'||carprocess_status == 'F'){//後面加了67F
					$("#car").show();
					$("#btnPrint").show();
				} else {
					$("#car").hide();
					$("#btnPrint").hide();
				}
				//狀態為U時
				if(carprocess_status == 'U'){
					$("#rmData").show();
					$("#data").hide();
				}else{
					$("#data").show();
					$("#rmData").hide();
					$("#mkDestination").prop("disabled", false);
					$("#mkApply_memo").prop("disabled", false);
					$("#mkCartype").prop("disabled", false);
					$("#rmNum_of_people").prop("disabled", false);
					$("#rmStarH").prop("disabled", false);
					$("#rmStarM").prop("disabled", false);
					$("#rmEndH").prop("disabled", false);
					$("#rmEndM").prop("disabled", false);
				}
				//控制狀態5時"公務車已滿請改其他大眾運輸工具"lable
				if (carprocess_status == '5'){
					$("#status_5").show();
				}else {
					$("#status_5").hide();
				}


			}

			function controlcheckMk(){//控制是否顯示"異動註記"checkBox
				var carprocess_status=$("#carprocess_status").val();
				if(carprocess_status == '3'||carprocess_status == '4'||carprocess_status == '6'||carprocess_status == '7'||carprocess_status == 'U'){//5先取消
					$("#checkMk").show();
					$("#footer").show();
				} else {
					$("#checkMk").hide();
				}
				if (isSecretarial==='Y'){
					$("#btnDelete").hide();
					$("#btnUpdate").hide();
				}else if (carprocess_status=='1'||carprocess_status=='2'){
					$("#btnUpdate").show();
					$("#btnDelete").show();
					$("#footer").show();
					$("#apply_memo").prop("disabled", false);
					$("#destination").prop("disabled", false);
					$("#apply_car_type").prop("disabled", false);
					$("#num_of_people").prop("disabled", false);
					$("#starH").prop("disabled", false);
					$("#starM").prop("disabled", false);
					$("#endH").prop("disabled", false);
					$("#endM").prop("disabled", false);
				}else{
					$("#btnUpdate").hide();
					$("#btnDelete").hide();
					$("#apply_memo").prop("disabled", true);
					$("#destination").prop("disabled", true);
					$("#apply_car_type").prop("disabled", true);
					$("#num_of_people").prop("disabled", true);
					$("#starH").prop("disabled", true);
					$("#starM").prop("disabled", true);
					$("#endH").prop("disabled", true);
					$("#endM").prop("disabled", true);
				}

				if (carprocess_status=='1'){//本來是!=2
					$("#using_date").removeAttr("disabled");
				}


			}

				$('input[name="checkMk"]:checkbox').change(function() {//控制"異動註記"是否勾選
					btnRmUpdate();
				});


			function btnRmUpdate() {
				var check =$("input[name=checkMk]:checked").val();
				var carprocess_status=$("#carprocess_status").val();
				if (isSecretarial==='N'){
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
						$("#cancelMg").hide();
					}
				}else {
					if(check){
						$("#rmMemo").show();
						$("#rmMemoLabel").show();
						controlChangMk();
						$("#changMk").hide();
						if (carprocess_status==='3'||carprocess_status==='4'||carprocess_status==='5'||carprocess_status==='6'||carprocess_status==='7'){
							$("#btnRmUpdate").show();
						}else {
							$("#btnRmUpdate").hide();
						}
					}
				}

			}

			$('#rmMemo').change(function() {//控制異動原因下拉式選單
				controlChangMk();
			});

			function controlChangMk() {
				var rmMemo =$("#rmMemo option:selected").val();
				var carprocess_status=$("#carprocess_status").val();
				if (rmMemo=='1'||carprocess_status=='U'){
					$("#changMk").show();
				}else {
					$("#changMk").hide();
				}
				if (rmMemo=='2'){//寫警告
					$("#cancelMg").show();
				}else {
					$("#cancelMg").hide();
				}
			}
			function CombineMk() {//控制併單註記為Y時
				var combineMk = $("#combine_mk").val();
				var carprocess_status=$("#carprocess_status").val();
				if (combineMk == 'Y'||carprocess_status == '6'||carprocess_status == '7') {
					$('#rmMemo option[value="1"]').remove()
					$("#rmMemo").prop("disabled", true);
					$("#btnRmUpdate").text("取消申請");
				}
			}
			function status_U() {//控制表單狀態為U時
				var carprocess_status=$("#carprocess_status").val();
				if (carprocess_status=='U') {
					$("#rmMemo").prop("disabled", true);
					$("#btnRmUpdate").text("取消申請");
				}
			}
			function isSecretarial_Y() {//控制秘書室登入
				var newOption = $('<option>', {
					value: '2',
					text: '3.秘書處臨時取消派車(請務必聯絡到申請人才可取消)'
				});
				if (isSecretarial==='Y') {
					$("#btnPrint").hide();
					$('#rmMemo option[value="1"]').remove()
					$('#rmMemo option[value="2"]').remove()
					$('#rmMemo').append(newOption);
					$("#btnRmUpdate").text("臨時取消");
					$("form").find(":input").prop("disabled", true);
				}
			}
         });
</script>
</jsp:attribute>
</tags:layout>