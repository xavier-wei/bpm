<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w010Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	  <tags:button id="btnInster">
    	新增<i class="fas fa-user-plus"></i>
      </tags:button>

	  <tags:button id="btnBack">
    	回主畫面<i class="fas fa-user-plus"></i>
      </tags:button>

<%--	  <tags:button id="btnUpdate">--%>
<%--    	修改<i class="fas fa-user-plus"></i>--%>
<%--      </tags:button>--%>

<%--	  <tags:button id="btnDelete">--%>
<%--    	刪除<i class="fas fa-user-plus"></i>--%>
<%--      </tags:button>--%>

</jsp:attribute>
	<jsp:attribute name="contents">
    	<tags:fieldset legend="執行結果">
		    <form:form id="xprt0w100Form" modelAttribute="${caseKey}">

		    	<!-- 明細錄 -->
			   	<div class="col-12 col-md-12">
					<ul class="nav nav-tabs" id="myTab" role="tablist">

						<li class="nav-item" role="presentation">
							<a class="nav-link" id="all-tab" data-toggle="tab" href="#all" role="tab" aria-controls="all" aria-selected="false">全部</a>
						</li>
						<li class="nav-item" role="presentation">
							<a class="nav-link" id="successData-tab" data-toggle="tab" href="#successData" role="tab" aria-controls="successData" aria-selected="false">轉帳成功資料</a>
						</li>
						<li class="nav-item" role="presentation">
							<a class="nav-link" id="returnData-tab" data-toggle="tab" href="#returnData" role="tab" aria-controls="returnData" aria-selected="false">退匯資料</a>
						</li>
					</ul>
				</div>
				<div class="col-12 col-md-12 tab-content">
						<%-- 全部 --%>
					<div class="tab-pane table-responsive" id="all" role="tabpanel" aria-labelledby="all-tab">
						<table class="table" id="allTab">
							<thead>
							<tr>
								<th style="vertical-align:middle;">行號</th>
								<th style="vertical-align:middle;">入帳日期</th>
								<th style="vertical-align:middle;">受理編號</th>
								<th style="vertical-align:middle;">序</th>
								<th style="vertical-align:middle;">入帳帳號</th>
								<th style="vertical-align:middle;">退匯金額</th>
								<th style="vertical-align:middle;">原因</th>
<%--								<th style="vertical-align:middle;"><c:out value="${caseData.column1 }"/></th>--%>
<%--								<th style="vertical-align:middle;"><c:out value="${caseData.column2 }"/></th>--%>
								<th style="vertical-align:middle;">編審結果</th>
							</tr>
							</thead>
							<tbody>
<%--							<c:forEach items="${file2List}" var="data" varStatus="status">--%>
<%--							        <tr>--%>
<%--										<td class="text-left"><c:out value="${data.line }"/></td>--%>
<%--										<td class="text-left"><func:minguo value="${data.bank_trans_date }"/></td>--%>
<%--										<td class="text-left"><c:out value="${data.apno }"/></td>--%>
<%--										<td class="text-left"><c:out value="${data.idx }"/></td>--%>
<%--										<td class="text-left"><c:out value="${data.account_no1 }${data.account_no2 }${data.account_no3 }"/></td>--%>
<%--										<td class="text-right"><fmt:formatNumber type="number" maxFractionDigits="3" value="${data.cash_amt}" /></td>--%>
<%--										<td class="text-left"><c:out value="${data.reject_code }"/></td>--%>
<%--										<td class="text-left"><c:out value="${data.id_no }"/></td>--%>
<%--										<td class="text-left"><c:out value="${data.per_unit_name }"/></td>--%>
<%--										<td class="text-left" title="${data.process_result2_nm }"><c:out value="${data.process_result2 }"/></td>--%>
<%--									</tr>--%>
<%--							    </c:forEach>--%>
							</tbody>
						</table>
					</div>

					<!-- 成功資料 -->
					<div class="tab-pane table-responsive" id="successData" role="tabpanel" aria-labelledby="successData-tab">
			            <tags:form-row>
			                <label class="col-form-label text-left">成功總件數:</label>
<%--			                <label class="col-form-label text-right"><fmt:formatNumber type="number" maxFractionDigits="3" value="${file3.s_total_cnt}" /></label>--%>
			                <label class="col-form-label text-left">成功總金額:</label>
<%--			                <label class="col-form-label text-right"><fmt:formatNumber type="number" maxFractionDigits="3" value="${file3.s_total_amt}" /></label>--%>
			            </tags:form-row>
						<tags:form-row></tags:form-row>
						<table class="table" id="successDataTab">
							<thead>
							<tr>
								<th style="vertical-align:middle;">行號</th>
								<th style="vertical-align:middle;">入帳日期</th>
								<th style="vertical-align:middle;">受理編號</th>
								<th style="vertical-align:middle;">序</th>
								<th style="vertical-align:middle;">入帳帳號</th>
								<th style="vertical-align:middle;">退匯金額</th>
								<th style="vertical-align:middle;">原因</th>
<%--
							</tr>
							</thead>
							<tbody>
<%--							<c:forEach items="${file2List}" var="data" varStatus="status">--%>
<%--			             			<c:if test="${data.reject_code == '00' }">--%>
<%--								        <tr>--%>
<%--											<td class="text-left"><c:out value="${data.line }"/></td>--%>
<%--											<td class="text-left"><func:minguo value="${data.bank_trans_date }"/></td>--%>
<%--											<td class="text-left"><c:out value="${data.apno }"/></td>--%>
<%--											<td class="text-left"><c:out value="${data.idx }"/></td>--%>
<%--											<td class="text-left"><c:out value="${data.account_no1 }${data.account_no2 }${data.account_no3 }"/></td>--%>
<%--											<td class="text-right"><fmt:formatNumber type="number" maxFractionDigits="3" value="${data.cash_amt}" /></td>--%>
<%--											<td class="text-left"><c:out value="${data.reject_code }"/></td>--%>
<%--											<td class="text-left"><c:out value="${data.id_no }"/></td>--%>
<%--											<td class="text-left"><c:out value="${data.per_unit_name }"/></td>--%>
<%--										</tr>--%>
<%--							        </c:if>--%>
<%--							    </c:forEach>--%>
							</tbody>
						</table>
					</div>

					<!-- 退匯資料 -->
					<div class="tab-pane table-responsive" id="returnData" role="tabpanel" aria-labelledby="returnData-tab">
			            <tags:form-row>
			                <label class="col-form-label text-left">退匯總件數:</label>
<%--			                <label class="col-form-label text-right"><fmt:formatNumber type="number" maxFractionDigits="3" value="${file3.f_total_cnt}" /></label>--%>
			                <label class="col-form-label text-left">退匯總金額:</label>
<%--			                <label class="col-form-label text-right"><fmt:formatNumber type="number" maxFractionDigits="3" value="${file3.f_total_amt}" /></label>--%>
			            </tags:form-row>
						<tags:form-row></tags:form-row>
						<table class="table" id="returnDataTab">
							<thead>
							<tr>
								<th style="vertical-align:middle;">行號</th>
								<th style="vertical-align:middle;">入帳日期</th>
								<th style="vertical-align:middle;">受理編號</th>
								<th style="vertical-align:middle;">序</th>
								<th style="vertical-align:middle;">入帳帳號</th>
								<th style="vertical-align:middle;">退匯金額</th>
								<th style="vertical-align:middle;">原因</th>
								<th style="vertical-align:middle;">說明</th>
<%--								<th style="vertical-align:middle;"><c:out value="${caseData.column1 }"/></th>--%>
<%--								<th style="vertical-align:middle;"><c:out value="${caseData.column2 }"/></th>--%>
							</tr>
							</thead>
							<tbody>
<%--							<c:forEach items="${file2List}" var="data" varStatus="status">--%>
<%--			             			<c:if test="${data.reject_code != '00' }">--%>
<%--								        <tr>--%>
<%--											<td class="text-left"><c:out value="${data.line }"/></td>--%>
<%--											<td class="text-left"><func:minguo value="${data.bank_trans_date }"/></td>--%>
<%--											<td class="text-left"><c:out value="${data.apno }"/></td>--%>
<%--											<td class="text-left"><c:out value="${data.idx }"/></td>--%>
<%--											<td class="text-left"><c:out value="${data.account_no1 }${data.account_no2 }${data.account_no3 }"/></td>--%>
<%--											<td class="text-right"><fmt:formatNumber type="number" maxFractionDigits="3" value="${data.cash_amt}" /></td>--%>
<%--											<td class="text-left"><c:out value="${data.reject_code }"/></td>--%>
<%--											<td class="text-left"><c:out value="${data.reject_code_nm }"/></td>--%>
<%--											<td class="text-left"><c:out value="${data.id_no }"/></td>--%>
<%--											<td class="text-left"><c:out value="${data.per_unit_name }"/></td>--%>
<%--										</tr>--%>
<%--							        </c:if>--%>
<%--						        </c:forEach>--%>
							</tbody>
						</table>
					</div>
				</div>

<%--	            <form:hidden id="inskd" name="inskd" path="inskd"/>--%>
<%--	            <form:hidden id="inskdNm" name="inskdNm" path="inskdNm"/>--%>
<%--	            <form:hidden id="fileDate" name="fileDate" path="fileDate"/>--%>
<%--	            <form:hidden id="bliAccountCode" name="bliAccountCode" path="bliAccountCode"/>--%>
<%--	            <form:hidden id="jobItem" name="jobItem" path="jobItem"/>--%>
<%--	            <form:hidden id="jobItemNm" name="jobItemNm" path="jobItemNm"/>--%>
<%--	            <form:hidden id="queryPaydate" name="queryPaydate" path="queryPaydate"/>--%>
<%--	            <form:hidden id="queryFileName" name="queryFileName" path="queryFileName"/>--%>
	    	</form:form>
	    </tags:fieldset>
    </jsp:attribute>

<jsp:attribute name="footers">
<script>
	var config = getDataTablesConfig(null, null, 100);
	var table = $(' #allTab, #successDataTab, #returnDataTab').DataTable(config);

	$(function() {
			controlBtn();
            $('#btnInster').click(function() {
           		$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_carInster.action" />').submit();
            });

			$('#btnBack').click(function() {
				$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_enter.action" />').submit();
			});

			$('#btnDelete').click(function() {
				showConfirm('確定要刪除資料？', () => {
					$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_delete.action" />').submit();
				});
			});

			$('#btnUpdate').click(function() {
				showConfirm('確定要修改資料？', () => {
					$('#eip07w010Form').attr('action', '<c:url value="/Eip07w010_update.action" />').submit();
				});
			});



			function controlBtn(){
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