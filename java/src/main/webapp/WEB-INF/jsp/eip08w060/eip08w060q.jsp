<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w060Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->

    <tags:button id="btnInsert">
    	新增<i class="fas fa-user-plus"></i>
    </tags:button>

	  <tags:button id="btnSelect">
    	查詢<i class="fas fa-search"></i>
    </tags:button>

	<tags:button id="btnClearn">
    	清除<i class="fas fa-eraser"></i>
    </tags:button>

</jsp:attribute>

	<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip08W060Form" name="eip08W060Form" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
				<div class="col-md d-flex  align-items-center">
					<form:label cssClass="col-form-label star" path="user">選項：</form:label>
					<form:radiobutton id="reportType" cssClass="checkedgreen" value="I-物品請購單" path="applyTpNm"/>物品請購單
					&nbsp;&nbsp;&nbsp;
					<form:radiobutton id="reportType" cssClass="checkedgreen" value="F-修繕請修單" path="applyTpNm"/>修繕請修單
					&nbsp;&nbsp;&nbsp;
					<form:radiobutton id="reportType" cssClass="checkedgreen" value="P-軟體請購單" path="applyTpNm"/>軟體請購單
				</div>
			</tags:form-row>
			<tags:form-row>
				<div class="col-md-4 d-flex">
					<form:label cssClass="col-form-label star" path="userName">申請人：</form:label>
					<form:input id="userName" name="userName" path="userName" cssClass="form-control"   size="12"
								maxlength="12" disabled="true" />
				</div>
            </tags:form-row>

			<tags:form-row>
					<div class="col-md-4 d-flex">
						<form:label cssClass="col-form-label star" path="applyDate">申請日期：</form:label>
						<form:input id="applyDate" name="applyDate" path="applyDate" cssClass="form-control num_only dateTW" size="8" maxlength="7"/>
				</div>
            </tags:form-row>

			<tags:form-row>
					<div class="col-md-4 d-flex">
						<form:label cssClass="col-form-label star" path="save">暫存：</form:label>
						<form:select id="workType" path="save" cssClass="form-control">
                    	<form:option value="N">N否</form:option>
                    	<form:option value="Y">Y是</form:option>
                    </form:select>
				</div>
            </tags:form-row>
		   <tags:form-note>
			請完成後，請務必列印報表出來跑蓋章簽核流程。
			</tags:form-note>

		<form:hidden id="processTy" path="processTy" />
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {

			var config = getDataTablesConfig();
            var table = $('#listTable').DataTable(config);
			var reportType = $("input[id=reportType]:checked").val();

            $('#btnSelect').click(function() {
           		$('#eip08W060Form').attr('action', '<c:url value="/Eip08w060_query.action" />').submit();
            });

			$('#btnClearn').click(function() {
				$('#eip08W060Form').attr('action', '<c:url value="/Eip08w060_enter.action" />').submit();
			});

			$('#btnInsert').click(function() {
				$('input[id="processTy"]').val("A");
				$('#eip08W060Form').attr('action', '<c:url value="/Eip08w060_transfer.action" />').submit();
			});

			<%--$('#btnDelete').click(function() {--%>
			<%--	showConfirm('確定要刪除資料？', () => {--%>
			<%--		$('#eip08W060Form').attr('action', '<c:url value="/Eip08W060_delete.action" />').submit();--%>
			<%--	});--%>
			<%--});  --%>




         });

</script>
</jsp:attribute>
</tags:layout>