<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w010Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
    <tags:button id="btnInsert">
    	新增<i class="fas fa-user-plus"></i>
    </tags:button>          
    <tags:button id="btnDelete">
    	刪除<i class="fas fa-trash-alt"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="設定條件">
		<form:form id="eip00w010Form" name="eip00w010Form" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
                 <form:label path="user_id" cssClass="col-form-label">員工編號：</form:label>
                    <div class="col-6 col-md form-inline">
                        <form:input path="user_id" cssClass="form-control eng_num_only" size="20" maxlength="20"/>
                    </div>
            </tags:form-row>
			<tags:form-row>
				<div class="table-responsive">	           
					<table class="table" id="listTable">
						<thead data-orderable="true">
							<tr>
								<th class="text-center"></th>
								<th class="text-center">員工編號</th>
								<th class="text-center">姓名</th>
								<th class="text-center">電子信箱</th>
								<th class="text-center">分機</th>
							</tr>
						</thead>
					    <tbody>
					    	<c:forEach items="${caseData.userList}" var="data" varStatus="status">
						        <tr>
						        	<td class="text-center">
						        		<form:checkbox path="selectedUserid" value="${data.user_id}"/>
						        	</td>
						        	<td class="text-left"><c:out value="${data.user_id}"/></td>
						        	<td class="text-left"><c:out value="${data.user_name}"/></td>
						        	<td class="text-left"><c:out value="${data.email}"/></td>
						            <td class="text-left"><c:out value="${data.tel2}"/></td>
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
        $(function() {
        	var config = getDataTablesConfig();
            var table = $('#listTable').DataTable(config);
           	
            $('#btnInsert').click(function() {
           		$('#eip00w010Form').attr('action', '<c:url value="/Eip00w010_add.action" />').submit();
            });
            
            $('#btnDelete').click(function() {	
                showConfirm('確定要刪除資料？', () => {
                	$('#eip00w010Form').attr('action', '<c:url value="/Eip00w010_delete.action" />').submit();
                });
            });
            
         });
</script>
</jsp:attribute>
</tags:layout>