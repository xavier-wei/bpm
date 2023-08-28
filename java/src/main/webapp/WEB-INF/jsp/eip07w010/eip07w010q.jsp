<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w010Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->

		 <tags:button id="btnSelect">
    	查詢<i class="fas fa-user-plus"></i>
      </tags:button>

	  <tags:button id="btnAdd">
    	新增<i class="fas fa-user-plus"></i>
      </tags:button>

	  <tags:button id="btnClearn">
    	清除<i class="fas fa-user-plus"></i>
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
<%--			<tags:form-row>--%>
<%--				<div class="col-md d-flex align-items-center">--%>
<%--					<form:label cssClass="col-form-label star" path="workTy">功能：</form:label>--%>
<%--					<form:radiobutton id="workTy" cssClass="checkedgreen" value="A" path="workTy"/>新增--%>
<%--					&nbsp;&nbsp;&nbsp;--%>
<%--					<form:radiobutton id="workTy" cssClass="checkedgreen" value="Q" path="workTy"/>查詢--%>
<%--				</div>--%>
<%--			</tags:form-row>--%>
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
                    </form:select>
				</div>
            </tags:form-row>
			</div>

			<div id="car" style="margin: 0; padding: 0;">
<%--				<div class="col-4 " id="carno" style="margin: 0; padding: 0;">--%>
<%--			<tags:form-row>--%>
<%--				<div class="col-md d-flex align-items-center" >--%>
<%--					<form:label cssClass="col-form-label star" path="carno1A">車牌號碼：</form:label>--%>
<%--					<table>--%>
<%--						<tr>--%>
<%--							<td><form:input id="carno1" name="carno1"  path="eip07w010QueryDataList[0].carno1" cssClass="form-control num_eng_only upCase" size="4" maxlength="3" /></td>--%>
<%--							<td>-</td>--%>
<%--							<td><form:input id="carno2" name="carno2"  path="eip07w010QueryDataList[0].carno2" cssClass="form-control num_eng_only upCase" size="7" maxlength="4" /></td>--%>
<%--							</tr>--%>
<%--					</table>--%>
<%--				</div>--%>
<%--				</div>--%>
<%--			</tags:form-row>--%>
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
<%--					<span class="input-group-text px-1">-</span>--%>
<%--					<form:select id="carno2Q"  name="carno2Q"  path="eip07w010QueryDataList[0].carno2Q" cssClass="form-control">--%>
<%--							<form:option value="">請選擇</form:option>--%>
<%--							<c:forEach var="item" items="${caseData.carnoList}" varStatus="status">--%>
<%--								<form:option value="${item.carno2}"><c:out value="${item.carno2}"/></form:option>--%>
<%--							</c:forEach>--%>
<%--						</form:select>--%>
				</div>
            </tags:form-row>
				</div>
<%--			</div>--%>
			<form:hidden id="workTy" path="workTy" />
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
			controlBt();
            // controlTitle();
			// var config = getDataTablesConfig();
            // var table = $('#listTable').DataTable(config);

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

				// if (workTy=='Q'){
				// 	$("#carnoList").show();
				// 	$("#carno").hide();
				// 	$("#btnSelect").text("查詢");
				// 	$('#stillWork').append('<option value="A">全部</option>');
				// 	$("label[name='name']").removeClass('star');
				// }else {
				// 	$("#carno").show();
				// 	$("#carnoList").hide();
				// 	$("#btnSelect").text("新增");
				// 	$('#stillWork option[value="A"]').remove()
				// 	$("label[name='name']").addClass('star');
				// }
			}



            function controlTitle(){
                var processTy=$("input[id=processTy]:checked").val();
                var workTy=$("input[id=workTy]:checked").val();
                if(processTy == 'D'){
                 if (workTy=='Q'){
                     $("#main>fieldset>legend").html("駕駛人查詢條件")
                 }else{
                     $("#main>fieldset>legend").html("駕駛人新增條件")
                 }
                } else {
                    if (workTy=='Q'){
                        $("#main>fieldset>legend").html("車籍查詢條件")
                    }else{
                        $("#main>fieldset>legend").html("車籍新增條件")
                    }

                }
                // if (workTy=='Q'){
                //     $("#carnoList").show();
                //     $("#carno").hide();
                //     $("#main>fieldset>legend").html("查詢條件")
                //     $("#btnSelect").text("查詢");
                //     $('#stillWork').append('<option value="A">全部</option>');
                //
                //     $("label[name='name']").removeClass('star');
                // }else {
                //     $("#carno").show();
                //     $("#carnoList").hide();
                //     $("#main>fieldset>legend").html("新增條件")
                //     $("#btnSelect").text("新增");
                //     $('#stillWork option[value="A"]').remove()
                //     $("label[name='name']").addClass('star');
                // }
            // }

         });


</script>
</jsp:attribute>
</tags:layout>