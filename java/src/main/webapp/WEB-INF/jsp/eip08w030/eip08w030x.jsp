<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w030Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnSubmit">
    	複核<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset  legend="查詢結果">
		<form:form id="eip08w030Form" name="eip08w030Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<div class="col-4 col-md-4">申請日期(起)：<func:minguo value="${caseData.applydateStart}"  pattern="yyy/MM/dd"/></div>
            	<div class="col-4 col-md-4">申請日期(迄)：<func:minguo value="${caseData.applydateEnd}"  pattern="yyy/MM/dd"/></div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-4 col-md-4">複核選項：
            	<label><input type="radio" name="agree"  value="agree">同意</label>
            	<label><input type="radio" name="agree"  value="disagree">不同意</label>
            	</div>
            </tags:form-row>
            <tags:form-row>
          	 <div class="table-responsive mt-2">	 
            	    <table id="qryListTable" class="table">
	            	    <thead data-orderable="true">
	            	    <tr>
                            <th style="width: 10%">全選<input type="checkbox" id="dataListTabcheckAllP" name="dataListTabcheckAllP"></th>
                            <th style="width: 10%">領物單號</th>
                            <th style="width: 10%">申請人</th>
                            <th style="width: 50%">申請單位</th>
                            <th style="width: 10%">明細</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${caseData.dataList}" var="item" varStatus="status">
                        <tr>
                        	<td><form:checkbox path="dataList[${status.index}].Check"  value="applynos" cssClass="checkedgreen"/></td>
                        	<td>PL<c:out value="${item.applyno}"/></td>
                        	<td><c:out value="${item.apply_user}"/></td>
                        	<td class="text-left"><c:out value="${item.apply_dept}"/></td>
                        	<td>
	                        	<tags:button cssClass="btnDetail" value="${item.applyno}">
									明細
								</tags:button>
                        	</td>
                        	</tr>
                        </c:forEach>
                        </tbody>
                        <form:hidden path="applyno"/>
                    </table>
                    </div>
            </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

		let config = getDataTablesConfig();
		var table = $("#qryListTable").DataTable(config);

        $(function() {
        	$('#selectFile').click(function(e){
        		$('input[name="agree"]')[0].checked = true;
        	});
            
            $('#btnReturn').click(function(){
           		$('#eip08w030Form').attr('action', '<c:url value="/Eip08w030_enter.action" />').submit();
            });
            
            
            $('.btnDetail').click(function(){
            	$('#applyno').val($(this).val());
           		$('#eip08w030Form').attr('action', '<c:url value="/Eip08w030_detail.action" />').submit();
            });
            
            $('#btnSubmit').click(function(){
            	$('#eip08w030Form').attr('action', '<c:url value="/Eip08w030_update.action" />').submit();
            });
            
            //全選的切換
            var flag = false;
            $("#dataListTabcheckAllP").click(function(){
            	$("input[name^='dataList']:checkbox").each(function(){
            		$(this).prop('checked',!flag);
            	});
                flag = !flag;
            });
         });

</script>
</jsp:attribute>
</tags:layout>