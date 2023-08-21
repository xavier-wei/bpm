<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w010Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
    <tags:button id="btnInsert">
    	新增<i class="fas fa-user-plus"></i>
    </tags:button>          
    <tags:button id="btnEdit">
    	修改<i class="fas fa-edit"></i>
    </tags:button>
    <tags:button id="btnDelete">
    	刪除<i class="fas fa-trash"></i>
    </tags:button>
	<tags:button id="btnReturnMain">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset  legend="明細查詢結果">
		<form:form id="eip08w011Form" name="eip08w011Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
                
                 <form:label path="mainkindno" cssClass="col-form-label">品名大類代號：</form:label>
            	 <div class="col-6 col-md form-inline">
					<c:out value="${caseData.mainkindno}"/>
                 </div>            
				 <form:label path="mainkindname" cssClass="col-form-label">品名大類：</form:label>
                 <div class="col-6 col-md form-inline">
					<c:out value="${caseData.mainkindname}"/>
                 </div>
            </tags:form-row>
            <tags:form-row>
            	 <form:label path="addDetailItemno" cssClass="col-form-label">品名代號：</form:label>
            	 <div class="col-6 col-md form-inline">
					<form:input path="addDetailItemno" cssClass="form-control eng_num_only upCase" size="3" maxlength="3"/>
                 </div>            
				 <form:label path="addDetailItemname" cssClass="col-form-label">品名：</form:label>
                 <div class="col-6 col-md form-inline">
					<form:input path="addDetailItemname" cssClass="form-control" size="20" maxlength="20"/>
                 </div>
            </tags:form-row>
            <tags:form-row>
				<div class="table-responsive">	           
					<table class="table" id="listTable">
						<thead data-orderable="true">
							<tr>
								<th class="text-center" width=5%></th>
								<th class="text-center" width=10%>品名<br>代號</th>
								<th class="text-center" width=25%>品名</th>
								<th class="text-center" width=10%>目前<br>數量</th>
								<th class="text-center" width=10%>進貨<br>數量</th>
								<th class="text-center" width=10%>退貨<br>數量</th>
								<th class="text-center" width=10%>實際<br>存貨<br>數量</th>
								<th class="text-center" width=10%>預扣<br>數量</th>
								<th class="text-center" width=10%>帳面<br>存貨<br>數量</th>
							</tr>
						</thead>
					    <tbody>
					    	<c:forEach items="${caseData.resultKindList}" var="data" varStatus="status">
						        <tr>
						        	<td class="text-center"><form:checkbox path="resultKindList[${status.index}].checkbox"  cssClass="checkedgreen"/></td>
						        	<td class="text-center"><c:out value="${data.itemno}"/></td>
						        	<td class="text-center"><c:out value="${data.itemname}"/></td>
						            <td class="text-center"><c:out value="${data.final_cnt}"/></td>
						            <td class="text-center">
						            	<form:input path="resultKindList[${status.index}].p_cnt" cssClass="form-control num_only" size="5" maxlength="5"/>
						            </td>
						        	<td class="text-center">
						        		<form:input path="resultKindList[${status.index}].r_cnt" cssClass="form-control num_only" size="5" maxlength="5"/>
						        	</td>
						        	<td class="text-center"><c:out value="${data.final_cnt}"/></td>
						            <td class="text-center"><c:out value="${data.withhold_cnt}"/></td>
						            <td class="text-center"><c:out value="${data.book_cnt}"/></td>
						        </tr>
					        </c:forEach>
						</tbody> 
					</table>  
				</div>
            </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
	$(function(){
		var config = getDataTablesConfig(null,null,10);
		var table = $('#listTable').DataTable(config);
		
		$('#btnEdit').click(function(){
			$('#eip08w011Form').attr('action', '<c:url value="/Eip08w010_editDetail.action" />').submit();
		})
		$('#btnInsert').click(function(){
			$('#eip08w011Form').attr('action', '<c:url value="/Eip08w010_addDetail.action" />').submit();
		})
		$('#btnDelete').click(function(){
			$('#eip08w011Form').attr('action', '<c:url value="/Eip08w010_deleteDetail.action" />').submit();
		})
		$('#btnReturnMain').click(function(){
			$('#eip08w011Form').attr('action', '<c:url value="/Eip08w010_enter.action" />').submit();
		})
 	})

</script>
</jsp:attribute>
</tags:layout>