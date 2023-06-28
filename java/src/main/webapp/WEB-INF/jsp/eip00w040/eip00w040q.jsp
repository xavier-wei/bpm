<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w040Controller).CASE_KEY" />
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
		<form:form id="eip00w040Form" name="eip00w040Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
                 <form:label path="dept_id" cssClass="col-form-label">部門代號：</form:label>
                    <div class="col-6 col-md form-inline">
                        <form:input path="dept_id" cssClass="form-control eng_num_only" size="19" maxlength="18"/>
                    </div>
            </tags:form-row>
            <tags:form-row>
				<div class="table-responsive">	           
					<table class="table" id="listTable">
						<thead data-orderable="true">
							<tr>
								<th class="text-center">部門代號</th>
								<th class="text-center">部門名稱</th>
								<th class="text-center">部門說明</th>
								<th class="text-center"></th>
							</tr>
						</thead>
					    <tbody>
					    	<c:forEach items="${caseData.deptList}" var="data" varStatus="status">
						        <tr>
						        	<td class="text-left"><c:out value="${data.dept_id}"/></td>
						        	<td class="text-left"><c:out value="${data.dept_name}"/></td>
						        	<td class="text-right"><c:out value="${data.dept_desc}"/></td>

						            <td class="text-center">
						            		<tags:button cssClass="btnEdit"
 	                                             onclick="detail('${data.dept_id}')">查詢<i class="fas fa-edit"></i></tags:button>  
 						            	<c:if test="${data.fromhr != 'Y'}"> 
 						            		<tags:button cssClass="btnEdit" 
 	                                             onclick="doEdit('${data.dept_id}')">修改<i class="fas fa-edit"></i></tags:button>  
 						            	</c:if> 
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
			$('#eip00w040Form').attr('action', '<c:url value="/Eip00w040_query.action" />').submit();
		})
		$('#btnInsert').click(function(){
			$('#eip00w040Form').attr('action', '<c:url value="/Eip00w040_add.action" />').submit();
		})
	})
	function doEdit(dept_id) {
        $('#dept_id').val(dept_id);
        $('#eip00w040Form').attr('action', '<c:url value="/Eip00w040_edit.action" />').submit();
    }
	function detail(dept_id) {
        $('#dept_id').val(dept_id);
        $('#eip00w040Form').attr('action', '<c:url value="/Eip00w040_detail.action" />').submit();
    }
</script>
</jsp:attribute>
</tags:layout>