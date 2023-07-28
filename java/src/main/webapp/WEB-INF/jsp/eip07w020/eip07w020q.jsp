<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	  <tags:button id="btnSelect">
    	確認<i class="fas fa-search"></i>
      </tags:button>

		<tags:button id="btnAdd">
    	確認<i class="fas fa-search"></i>
      </tags:button>

	  <tags:button id="btnClearn">
    	清除<i class="fa-step-backward"></i>
      </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip07w020Form" name="eip07w020Form" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
				<label class="col-form-label text-left star">功能:</label>
				<div class="d-flex align-items-center">
					<form:radiobutton id="workTy" cssClass="checkedgreen" value="A" path="workTy"/>新增預約
					&nbsp;&nbsp;&nbsp;
					<form:radiobutton id="workTy" cssClass="checkedgreen" value="Q" path="workTy"/>查詢派車結果
				</div>
			</tags:form-row>
			<tags:form-row>
				<label class="col-form-label text-left  star">申請人:</label>
				<div class="col-sm-2">
					<form:input id="applyName" name="applyName" path="applyName" cssClass="form-control"   size="8"
								maxlength="8" />
				</div>
			</tags:form-row>
				<tags:form-row>
				<label class="col-form-label text-left  star">申請單位:</label>
				<div class="col-sm-2">
					<form:input id="applyUnit" name="applyUnit" path="applyUnit" cssClass="form-control"   size="8"
								maxlength="8" />
				</div>
			</tags:form-row>
			<div id="A">
				<tags:form-row>
				<label class="col-form-label text-left  star">申請日期:</label>
				<div class="col-sm-2">
					<form:input id="applyDate" name="applyDate" path="applyDate" cssClass="form-control num_only dateTW"   size="7"
								maxlength="7" />
				</div>
			</tags:form-row>
			</div>

			<div id="Q">
			<tags:form-row>
				<label class="col-form-label text-left ">申請日期(起):</label>
				<div class="col-sm-2">
					<form:input id="applyDateStar" name="applyDateStar" path="applyDateStar" cssClass="form-control num_only dateTW"   size="7"
								maxlength="7" />
				</div>
				<label class="col-form-label text-left">申請日期(迄):</label>
				<div class="col-sm-2">
					<form:input id="applyDateEnd" name="applyDateEnd" path="applyDateEnd" cssClass="form-control num_only dateTW"   size="7"
								maxlength="7" />
				</div>
			</tags:form-row>
			<tags:form-row>
				<label class="col-form-label text-left ">用車日期(起):</label>
				<div class="col-sm-2">
					<form:input id="useDateStar" name="useDateStar" path="useDateStar" cssClass="form-control num_only dateTW"   size="7"
								maxlength="7" />
				</div>
				<label class="col-form-label text-left ">用車日期(迄):</label>
				<div class="col-sm-2">
					<form:input id="useDateEnd" name="useDateEnd" path="useDateEnd" cssClass="form-control num_only dateTW"   size="7"
								maxlength="7" />
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
            $('#btnSelect').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_quary.action" />').submit();
            });

			$('#btnAdd').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_addPage.action" />').submit();
			});

			$('#btnClearn').click(function() {
				$('#eip07w020Form').attr('action', '<c:url value="/Eip07w020_enter.action" />').submit();
			});

			$('input[id=processTy]:radio').change(function(e) {
				controlBt();
			});

			$('input[id=workTy]:radio').change(function(e) {
				controlBt();
			});

			function controlBt(){
				var workTy=$("input[id=workTy]:checked").val();
				if(workTy == 'A'){
					$("#A").show();
					$("#Q").hide();
					$("#btnAdd").show();
					$("#btnSelect").hide();
				} else {
					$("#Q").show();
					$("#A").hide();
					$("#btnSelect").show();
					$("#btnAdd").hide();
				}
			}
         });


</script>
</jsp:attribute>
</tags:layout>