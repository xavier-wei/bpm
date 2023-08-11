<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">    
    <tags:button id="btnEdit">
    	更新<i class="fas fa-edit"></i>
    </tags:button>
        <tags:button id="btnBack">
    	取消<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="帳號編輯">
		<form:form id="eip00w020Form" name="eip00w020Form" modelAttribute="${caseKey}" method="POST">
			<form:hidden path="delRoleId" name="delRoleId" cssClass="form-control inputtext"/>
            <tags:form-row>
                    <form:label cssClass="col-form-label" path="users.user_id">使用者代號：</form:label>
           			<div class="col-12 col-md form-inline">
           				<c:out value="${caseData.users.user_id}"/>
                    </div>
            </tags:form-row>
			<tags:form-row>
                  	<c:if test="${caseData.users.from_hr == 'Y'}">
                  			<form:label cssClass="col-form-label" path="dept_id">部門代號：</form:label>
	           			<div class="col-12 col-md form-inline">
	           				<c:out value="${caseData.users.dept_id}"/>-<func:code codekind = 'DEPT' codeno = '${caseData.users.dept_id}' />
	                    </div>
                  	</c:if>
                  	<c:if test="${caseData.users.from_hr != 'Y'}">
	                    <form:label cssClass="col-form-label" path="dept_id">部門代號：</form:label>
	                    <div class="col-12 col-md form-inline">
				        <form:select path="dept_id" cssClass="form-control">
							<form:option value="">請選擇</form:option>
								<c:forEach items="${caseData.deptList}" var="item" varStatus="status">
									<form:option value="${item.codeno}">${item.codeno}-${item.codename}</form:option>
								</c:forEach>
						</form:select>
						</div>
                  	</c:if>
            </tags:form-row>
            <tags:form-row>
	            	
		            	<c:if test="${caseData.users.from_hr == 'Y'}">
		            		<form:label cssClass="col-form-label" path="dept_id">中文姓名：</form:label>
		           			<div class="col-12 col-md form-inline">
		           				<c:out value="${caseData.user_name}"/>
		                    </div>
		           		</c:if>
		           		<c:if test="${caseData.users.from_hr != 'Y'}">
							<form:label cssClass="col-form-label" path="user_name">中文姓名：</form:label>
							<div class="col-12 col-md form-inline">
		                    <form:input path="user_name" cssClass="form-control" size="30" value="${caseData.user_name}"/>
		                    </div>
		           		</c:if>
			</tags:form-row>      
			<tags:form-row>
	                <div class="col-12 col-md form-inline">
	                   	<tags:text-item label="英文姓名"><c:out value=""/></tags:text-item>
	                </div>
            </tags:form-row>
            <tags:form-row>
            	   <form:label cssClass="col-form-label" path="dept_id">電子郵件：</form:label>
                   <div class="col-12 col-md form-inline">
                        <form:input path="email" cssClass="form-control" size="50" value="${caseData.email}"/>
                   </div>
            </tags:form-row>
            <tags:form-row>
				<c:if test="${caseData.users.from_hr == 'Y'}">
                    <form:label cssClass="col-form-label" path="emp_id">員工編號：</form:label>
           			<div class="col-12 col-md form-inline">
                     	<c:out value="${caseData.users.emp_id}"/>
                    </div>
           		</c:if>
           		<c:if test="${caseData.users.from_hr != 'Y'}">
           			<form:label cssClass="col-form-label" path="emp_id">員工編號：</form:label>
           			<div class="col-12 col-md form-inline">
                     	<form:input path="emp_id" cssClass="form-control" size="30" value="${caseData.emp_id}"/>
                    </div>
           		</c:if>
            </tags:form-row>   
            <tags:form-row>
				<c:if test="${caseData.users.from_hr == 'Y'}">
                    <form:label cssClass="col-form-label" path="users.tel1">連絡電話：</form:label>
           			<div class="col-12 col-md form-inline">
                     	<c:out value="${caseData.users.tel1}"/>
                    </div>
           		</c:if>
           		<c:if test="${caseData.users.from_hr != 'Y'}">
                   	<form:label cssClass="col-form-label" path="dept_id">連絡電話：</form:label>
           			<div class="col-12 col-md form-inline">
                     	<form:input path="tel1" cssClass="form-control" size="30" value="${caseData.tel1}"/>
                    </div>
           		</c:if>
            </tags:form-row>      
			<tags:form-row>
				<c:if test="${caseData.users.from_hr == 'Y'}">
                    <form:label cssClass="col-form-label" path="users.tel2">連絡電話：</form:label>
           			<div class="col-12 col-md form-inline">
                     	<c:out value="${caseData.users.tel2}"/>
                    </div>
           		</c:if>
           		<c:if test="${caseData.users.from_hr != 'Y'}">
           			<form:label cssClass="col-form-label" path="tel2">分機：</form:label>
                    <div class="col-12 col-md form-inline">
                     	<form:input path="tel2" cssClass="form-control" size="30" value="${caseData.tel2}"/>
                    </div>
           		</c:if>
		    </tags:form-row>
            <tags:form-row>
            	   <form:label cssClass="col-form-label" path="line_token">LINE TOKEN：</form:label>
            	   <div class="col-12 col-md form-inline">
                        <form:input path="line_token" cssClass="form-control" size="50" value="${caseData.line_token}"/>
                   </div>
            </tags:form-row>
            <tags:form-row>
            		<form:label cssClass="col-form-label star" path="userStatus">使用者狀態：</form:label>
            		<div class="col-12 col-md form-inline">
	                	<form:radiobutton path="userStatus" label="啟用" value="Y" />
	                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    <form:radiobutton path="userStatus" label="停用" value="N" />
	                </div>
            </tags:form-row>
            <tags:form-row>
				<c:if test="${caseData.users.from_hr == 'Y'}">
                   <form:label cssClass="col-form-label" path="users.title_id">職稱代號：</form:label>
            	   <div class="col-12 col-md form-inline">
                     	<c:out value="${caseData.users.title_id}"/>-<func:code codekind = 'TITLE' codeno = '${caseData.users.title_id}' />
                   </div>
           		</c:if>
           		<c:if test="${caseData.users.from_hr != 'Y'}">
           			<form:label cssClass="col-form-label" path="title_id">職稱代號：</form:label>
           			<div class="col-12 col-md form-inline">
				        <form:select path="title_id" cssClass="form-control">
							<form:option value="">請選擇</form:option>
								<c:forEach items="${caseData.titleidList}" var="item" varStatus="status">
									<form:option value="${item.codeno}">${item.codeno}-${item.codename}</form:option>
								</c:forEach>
						</form:select>
					</div>
           		</c:if>
            </tags:form-row>
		    <tags:form-row>
			<div class="table-responsive">	           
				<table class="table" id="listTable">
					<thead data-orderable="true">
						<tr>
							<th class="text-center" style="width: 10%">系統代號</th>
							<th class="text-center" style="width: 20%">角色代號</th>
							<th class="text-center" style="width: 50%">角色說明</th>
							<th class="text-center" style="width: 30%"></th>
						</tr>
					</thead>
				    <tbody>
				    	<c:forEach items="${caseData.userRolesList}" var="data" varStatus="status">
					        <tr>
					        	<td class="text-left"><c:out value="${data.sys_id}"/></td>
					        	<td class="text-left"><c:out value="${data.role_id}"/></td>
					        	<td class="text-left"><c:out value="${data.role_desc}"/></td>
					            <td class="text-right">
					            	 <tags:button cssClass="btnEdit"
		                                           onclick="delRole('${data.role_id}')">刪除帳戶角色<i class="fas fa-trash"></i></tags:button>
					            </td>
					        </tr>
				        </c:forEach>

					</tbody> 
				</table>  
			</div>
		   </tags:form-row>
		   <tags:form-row>
			<div class="table-responsive">	           
				<table class="table">
					<thead data-orderable="true">
						<tr>
							<th class="text-center" style="width: 70%">新增角色代號</th>
							<th class="text-center" style="width: 30%"></th>
						</tr>
					</thead>
				    <tbody>
					    <tr>
				        	<td class="text-center">							  
				          		<div class="col-12 col-md form-inline">
							        <form:select path="addRoleid" cssClass="form-control">
										<form:option value="">請選擇</form:option>
											<c:forEach items="${caseData.rolesList}" var="item" varStatus="status">
												<form:option value="${item.role_id}">${item.role_id}-${item.role_desc}</form:option>
											</c:forEach>
									</form:select>
								</div>
				        	</td>
				            <td class="text-right">
				            	 <tags:button cssClass="btnEdit"
	                                           onclick="addRole()">新增<i class="fas fa-edit"></i></tags:button>
				            </td>
					    </tr>
					</tbody> 
				</table>  
			</div>
		   </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
	$(document).ready(function(){
 		var selectedTtileidValue = '<c:out value="${caseData.title_id}"/>';
 		var selectedDeptidValue = '<c:out value="${caseData.dept_id}"/>';
		$('#title_id').val(selectedTtileidValue);
		$('#dept_id').val(selectedDeptidValue);
	})
	
	$(function() {
    	var config = getDataTablesConfig();
        var table = $('#listTable').DataTable(config);
		
		$('#btnBack').click(function(){
			$('#eip00w020Form').attr('action', '<c:url value="/Eip00w020_enter.action" />').submit();
		})
		$('#btnEdit').click(function(){
			$('#eip00w020Form').attr('action', '<c:url value="/Eip00w020_update.action" />').submit();
		})
	})
	
    function delRole(delid) {
        $('#delRoleId').val(delid);
        $('#eip00w020Form').attr('action', '<c:url value="/Eip00w020_delUserRole.action" />').submit();
    }
	
    function addRole() {
        $('#eip00w020Form').attr('action', '<c:url value="/Eip00w020_addUserRole.action" />').submit();
    }


</script>
</jsp:attribute>
</tags:layout>