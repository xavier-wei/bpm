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
    	清除<i class="fas fa-user-plus"></i>
      </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip07w010Form" name="eip07w010Form" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
				<label class="col-form-label text-left star">選項:</label>
				<div class="d-flex align-items-center">
					<form:radiobutton id="processTy" cssClass="checkedgreen" value="D" path="processTy"/>駕駛人資料
					&nbsp;&nbsp;&nbsp;
					<form:radiobutton id="processTy" cssClass="checkedgreen" value="C" path="processTy"/>車籍資料
				</div>
			</tags:form-row>
			<tags:form-row>
				<label class="col-form-label text-left star">功能:</label>
				<div class="d-flex align-items-center">
					<form:radiobutton id="workTy" cssClass="checkedgreen" value="A" path="workTy"/>新增
					&nbsp;&nbsp;&nbsp;
					<form:radiobutton id="workTy" cssClass="checkedgreen" value="Q" path="workTy"/>查詢
				</div>
			</tags:form-row>
			<div id="driver">
			<tags:form-row>
				<label class="col-form-label text-left ">駕駛人姓名:</label>
				<div class="col-sm-2">
					<form:input id="name" name="name" path="eip07w010QueryDataList[0].name" cssClass="form-control"   size="6"
								maxlength="8" />
				</div>
			</tags:form-row>
			<tags:form-row>
				<label class="col-form-label text-left">在職註記:</label>
                <div>
                    <form:select id="stillWork" path="eip07w010QueryDataList[0].stillWork" cssClass="form-control">
                    	<form:option value="Y">是</form:option>
                    	<form:option value="N">否</form:option>
						<form:option value="A">全部</form:option>
                    </form:select>
				</div>
            </tags:form-row>
			</div>

			<div id="car">
			<tags:form-row>
				<label class="col-form-label text-left  ">車牌號碼:</label>
				<div class="col-4">
					<table>
						<tr>
							<td><form:input id="carno1" name="carno1"  path="eip07w010QueryDataList[0].carno1" cssClass="form-control num_eng_only" size="3" maxlength="3" /></td>
							<td>-</td>
							<td><form:input id="carno2" name="carno2"  path="eip07w010QueryDataList[0].carno2" cssClass="form-control num_eng_only" size="4" maxlength="4" /></td>
							</tr>
					</table>
				</div>
            </tags:form-row>
			</div>
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
				var processTy =$("input[id=processTy]:checked").val();
				if (processTy == 'D'){
					$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_query.action" />').submit();
				}else {

					$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_queryCar.action" />').submit();
				}
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


</script>
</jsp:attribute>
</tags:layout>