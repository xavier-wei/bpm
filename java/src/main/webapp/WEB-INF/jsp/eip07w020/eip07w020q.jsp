<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	  <tags:button id="btnSelect">
    	查詢<i class="fas fa-search"></i>
      </tags:button>

		<tags:button id="btnAdd">
    	新增<i class="fas fa-search"></i>
      </tags:button>

	  <tags:button id="btnClearn">
    	清除<i class="fa-step-backward"></i>
      </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip07w020Form" name="eip07w020Form" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
				<div class="col-md-4 d-flex align-items-center">
					<form:label cssClass="col-form-label star" path="workTy">功能：</form:label>
					<form:radiobutton id="workTy" cssClass="checkedgreen" value="A" path="workTy"/>新增預約
					&nbsp;&nbsp;&nbsp;
					<form:radiobutton id="workTy" cssClass="checkedgreen" value="Q" path="workTy"/>查詢派車結果
				</div>
			</tags:form-row>
			<tags:form-row>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label  star" path="applyName">申請人：</form:label>
					<form:input id="applyName" name="applyName" path="applyName" cssClass="form-control"   size="8" disabled="true"
								maxlength="8" />
				</div>
			</tags:form-row>
				<tags:form-row>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label star" path="applyUnit">申請單位：</form:label>
					<form:input id="applyUnit" name="applyUnit" path="applyUnit" cssClass="form-control"   size="8" disabled="true"
								maxlength="8" />
				</div>
			</tags:form-row>
			<div id="A">
				<tags:form-row>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label star" path="applyDate">申請日期：</form:label>
					<form:input id="applyDate" name="applyDate" path="applyDate" cssClass="form-control num_only dateTW"   size="7"
								maxlength="7" />
				</div>
			</tags:form-row>
			</div>

			<div id="Q">
			<tags:form-row>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label" path="applyDateStar">申請日期(起)：</form:label>
					<form:input id="applyDateStar" name="applyDateStar" path="applyDateStar" cssClass="form-control num_only dateTW"   size="7"
								maxlength="7" />
				</div>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label" path="applyDateEnd">申請日期(迄)：</form:label>
					<form:input id="applyDateEnd" name="applyDateEnd" path="applyDateEnd" cssClass="form-control num_only dateTW"   size="7"
								maxlength="7" />
				</div>
			</tags:form-row>
			<tags:form-row>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label" path="useDateStar">用車日期(起)：</form:label>
					<form:input id="useDateStar" name="useDateStar" path="useDateStar" cssClass="form-control num_only dateTW"   size="7"
								maxlength="7" />
				</div>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label " path="useDateEnd">用車日期(迄)：</form:label>
					<form:input id="useDateEnd" name="useDateEnd" path="useDateEnd" cssClass="form-control num_only dateTW"   size="7"
								maxlength="7" />
				</div>
			</tags:form-row>
			<tags:form-note>
                若申請日期起或用車日期起有輸入時，只需擇一輸入
            </tags:form-note>
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
					$("#main>fieldset>legend").html("新增條件")
				} else {
					$("#Q").show();
					$("#A").hide();
					$("#btnSelect").show();
					$("#btnAdd").hide();
					$("#main>fieldset>legend").html("查詢條件")
				}
			}
         });


</script>
</jsp:attribute>
</tags:layout>