<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
    <tags:button id="btnSearch">
    	查詢<i class="fas fa-search"></i>
    </tags:button>         

</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip00w020Form" name="eip00w020Form" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
				<div class="col-md d-flex align-items-center">
					<form:label path="search_id" cssClass="col-form-label">員工編號或姓名：</form:label>
				 	<form:input path="search_id" cssClass="form-control eng_num_only" size="20" maxlength="20"/>
				</div>
            </tags:form-row>
            <tags:form-row>
            	<div class="col-md d-flex align-items-center">
            	<form:label path="dept_name" cssClass="col-form-label">部門名稱：</form:label>
            		<form:input path="dept_name" cssClass="form-control eng_num_only" size="20" maxlength="20"/>
            	</div>
            </tags:form-row>
            <form:hidden path="user_id"/>
            <form:hidden path="dept_id"/>
        </form:form>
    </tags:fieldset>
    <tags:form-row>
				<div class="table-responsive">	           
					<table class="table" id="listTable">
						<thead data-orderable="true">
							<tr>
								<th class="text-center">使用者代號</th>
								<th class="text-center">姓名</th>
								<th class="text-center">電子信箱</th>
								<th class="text-center">分機</th>
								<th class="text-center">部門代號</th>
								<th class="text-center">部門名稱</th>
								<th class="text-center"></th>
							</tr>
						</thead>
					    <tbody>
					    	<c:forEach items="${caseData.userList}" var="data" varStatus="status">
						        <tr>
						        	<td class="text-left"><c:out value="${data.user_id}"/></td>
						        	<td class="text-left"><c:out value="${data.user_name}"/></td>
						        	<td class="text-left"><c:out value="${data.email}"/></td>
						            <td class="text-left"><c:out value="${data.tel2}"/></td>
						            <td class="text-left"><c:out value="${data.dept_id}"/></td>
						            <td class="text-left"><c:out value="${data.dept_cname}"/></td>
						            <td class="text-center">
							            <tags:button cssClass="btnEdit"
	                                             onclick="doEdit('${data.user_id}','${data.dept_id}')">修改</i></tags:button>
						            </td>
						        </tr>
					        </c:forEach>
						</tbody> 
					</table>  
				</div>
     </tags:form-row>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
   $(function() {
   	   var config = getDataTablesConfig();
       var table = $('#listTable').DataTable(config);
      	
       $('#btnSearch').click(function() {
      		$('#eip00w020Form').attr('action', '<c:url value="/Eip00w020_query.action" />').submit();
       });
       
    });
    function doEdit(userid,deptid) {
       $('#user_id').val(userid);
       $('#dept_id').val(deptid);
       $('#eip00w020Form').attr('action', '<c:url value="/Eip00w020_edit.action" />').submit();
   }
</script>
</jsp:attribute>
</tags:layout>