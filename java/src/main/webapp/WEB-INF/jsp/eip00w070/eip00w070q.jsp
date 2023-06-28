<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w070Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">

	<tags:button id="btnSearch">
    	查詢<i class="fas fa-search"></i>
    </tags:button>   
    <tags:button id="btnInsert">
    	新增角色<i class="fas fa-user-plus"></i>
    </tags:button>          
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip00w070Form" name="eip00w070Form" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
		      <form:label path="role_id" cssClass="col-form-label">角色代號：</form:label>
              <div class="col-6 col-md form-inline">
                  <form:input path="role_id" cssClass="form-control eng_num_only" size="20" maxlength="20"/>
              </div>
              <form:hidden path="role_desc" name="role_desc" cssClass="form-control inputtext"/>
            </tags:form-row>
            
        </form:form>
    </tags:fieldset>
    <tags:form-row>
		<div class="table-responsive">	           
			<table class="table" id="listTable">
				<thead data-orderable="true">
					<tr>
						<th class="text-center">系統代號</th>
						<th class="text-center">角色代號</th>
						<th class="text-center">角色說明</th>
						<th class="text-center"></th>
					</tr>
				</thead>
			    <tbody>
			    	<c:forEach items="${caseData.rolesList}" var="data" varStatus="status">
				        <tr>
				        	<td class="text-left"><c:out value="${data.sys_id}"/></td>
				        	<td class="text-left"><c:out value="${data.role_id}"/></td>
				        	<td class="text-left"><c:out value="${data.role_desc}"/></td>
				            <td class="text-right">
				            	 <tags:button cssClass="btnEdit"
                                            onclick="editMenu('${data.role_id}','${data.role_desc}')">編輯<i class="fas fa-edit"></i></tags:button>
				            	 <tags:button cssClass="btnEdit"
                                            onclick="editMember('${data.role_id}','${data.role_desc}')">隸屬人員<i class="fas fa-search"></i></tags:button>
				            </th>
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
           	
            $('#btnInsert').click(function() {
           		$('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_add.action" />').submit();
            });
            
            $('#btnSearch').click(function() {
           		$('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_query.action" />').submit();
            });
        });
        
        function editMenu(roleid,roledesc) {
            $('#role_id').val(roleid);
            $('#role_desc').val(roledesc);
            $('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_editMenu.action" />').submit();
        }
        function editMember(roleid,roledesc) {
            $('#role_id').val(roleid);
            $('#role_desc').val(roledesc);
            $('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_editMember.action" />').submit();
        }
</script>
</jsp:attribute>
</tags:layout>