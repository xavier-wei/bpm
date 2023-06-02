<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w030Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
    <tags:button id="btnInsert">
    	新增<i class="fas fa-user-plus"></i>
    </tags:button>          
    <tags:button id="btnSearch">
    	查詢<i class="fas fa-search"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip00w030Form" name="eip00w030Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
                 <form:label path="sys_id" cssClass="col-form-label">系統代號：</form:label>
                    <div class="col-6 col-md form-inline">
                        <form:input path="sys_id" cssClass="form-control eng_num_only" size="19" maxlength="18"/>
                    </div>
            </tags:form-row>
            <tags:form-row>
				<div class="table-responsive">	           
					<table class="table" id="listTable">
						<thead data-orderable="true">
							<tr>
								<th class="text-center">系統代號</th>
								<th class="text-center">系統名稱</th>
								<th class="text-center">首頁網址</th>
								<th class="text-center">項目代號</th>
								<th class="text-center">序號</th>
								<th class="text-center"></th>
							</tr>
						</thead>
					    <tbody>
					    	<c:forEach items="${caseData.systemLits}" var="data" varStatus="status">
						        <tr>
						        	<td class="text-left"><c:out value="${data.sys_id}"/></td>
						        	<td class="text-left"><c:out value="${data.sys_name}"/></td>
						        	<td class="text-right"><c:out value="${data.url}"/></td>
						            <td class="text-left"><c:out value="${data.item_id}"/></td>
						            <td class="text-left"><c:out value="${data.sort_order}"/></td>
						            <td class="text-center">
							            <tags:button cssClass="btnEdit"
	                                             onclick="doEdit('${data.sys_id}')">修改<i class="fas fa-edit"></i></tags:button>
						            </td>
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
		var config = getDataTablesConfig();
		var table = $('#listTable').DataTable(config);
		
		$('#btnSearch').click(function(){
			$('#eip00w030Form').attr('action', '<c:url value="/Eip00w030_query.action" />').submit();
		})
		$('#btnInsert').click(function(){
			$('#eip00w030Form').attr('action', '<c:url value="/Eip00w030_add.action" />').submit();
		})
	})
	function doEdit(sys_id) {
        $('#sys_id').val(sys_id);
        $('#eip00w030Form').attr('action', '<c:url value="/Eip00w030_edit.action" />').submit();
    }
</script>
</jsp:attribute>
</tags:layout>