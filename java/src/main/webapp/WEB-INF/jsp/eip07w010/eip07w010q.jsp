<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w010Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->

		 <tags:button id="btnSelect">
    	查詢<i class="fas fa-search"></i>
      </tags:button>

	  <tags:button id="btnAdd">
    	新增<i class="fas fa-user-plus"></i>
      </tags:button>

	  <tags:button id="btnClearn">
    	清除<i class="fas fa-eraser"></i>
      </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset >
		<form:form id="eip07w010Form" name="eip07w010Form" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
				<div class="col-md d-flex align-items-center">
					<form:label cssClass="col-form-label star" path="processTy">選項：</form:label>
					<form:radiobutton id="processTy" cssClass="checkedgreen" value="D" path="processTy"/>駕駛人資料
					&nbsp;&nbsp;&nbsp;
					<form:radiobutton id="processTy" cssClass="checkedgreen" value="C" path="processTy"/>車籍資料
				</div>
			</tags:form-row>
			<div id="driver">
			<tags:form-row>
				<div class="col-md d-flex align-items-center">
					<form:label cssClass="col-form-label " path="name" name="name">駕駛人姓名：</form:label>
					<form:input id="name" name="name" path="eip07w010QueryDataList[0].name" cssClass="form-control"   size="10"
								maxlength="10" />
				</div>
			</tags:form-row>
			<tags:form-row>
				<div class="col-md d-flex align-items-center">
					<form:label cssClass="col-form-label star" path="stillWork">在職註記：</form:label>
                    <form:select id="stillWork" path="eip07w010QueryDataList[0].stillWork" cssClass="form-control">
                    	<form:option value="Y">是</form:option>
                    	<form:option value="N">否</form:option>
						<form:option value="A">全部</form:option>
                    </form:select>
				</div>
            </tags:form-row>
			</div>

			<div id="car" style="margin: 0; padding: 0;">
				<div id="carnoList" style="margin: 0; padding: 0;">
				<tags:form-row>
				<div class="col-md d-flex align-items-center">
					<form:label cssClass="col-form-label " path="carno">車牌號碼：</form:label>
							<form:select id="carno"  name="carno"  path="eip07w010QueryDataList[0].carno" cssClass="form-control">
							<form:option value="">請選擇</form:option>
							<c:forEach var="item" items="${caseData.carnoList}" varStatus="status">
								<form:option value="${item.carno}"><c:out value="${item.carno}"/></form:option>
							</c:forEach>
						</form:select>
				</div>
            </tags:form-row>
				</div>
			<form:hidden id="workTy" path="workTy" />
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
			controlBt();

            $('#btnSelect').click(function() {
				var processTy =$("input[id=processTy]:checked").val();
				$("#workTy").val('Q');
				if (processTy == 'D'){
					$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_query.action" />').submit();
				}else {

					$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_queryCar.action" />').submit();
				}
            });

			$('#btnAdd').click(function() {
				var processTy =$("input[id=processTy]:checked").val();
				$("#workTy").val('A');
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
                // controlTitle();
				controlBt();
			});

			$('input[id=workTy]:radio').change(function(e) {
                // controlTitle();
				controlBt();
			});

			function controlBt(){
				var processTy=$("input[id=processTy]:checked").val();
				// var workTy=$("input[id=workTy]:checked").val();
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