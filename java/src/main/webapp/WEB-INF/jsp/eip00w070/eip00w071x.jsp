<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w070Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">    
    <tags:button id="btnEdit">
    	儲存<i class="fas fa-edit"></i>
    </tags:button>
        <tags:button id="btnBack">
    	取消<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="隸屬人員">
    	<form:form id="eip00w070Form" name="eip00w070Form" modelAttribute="${caseKey}" method="POST">
    	<div class="col-12 d-flex">
            <div class="col-6 col-md-6">
					<tags:form-row>
						<tags:text-item label="角色代號"><c:out value="${caseData.role_id}"/></tags:text-item>
	                </tags:form-row>
	                <tags:form-row>
						<tags:text-item label="角色說明"><c:out value="${caseData.role_desc}"/></tags:text-item>
	                </tags:form-row>
        	</div>
        	<div class="col-6 col-md-6">
        		<div>
        			<form:select path="dept" cssClass="form-control">
        				<option value="all">全部</option>
        				<c:forEach items="${caseData.deptsList}" var="data" varStatus="status">
        					<form:option value="${data.dept_id}"><c:out value="${data.dept_name}"/></form:option>
        				</c:forEach>
        			</form:select>
        		</div>
				<div class="table-responsive">	           
					<table class="table" id="listTable">
						<thead data-orderable="true">
							<tr>
								<th class="text-center"></th>
								<th class="text-center">姓名</th>
								<th class="text-center">員工代號</th>
								<th class="text-center">部門代號</th>
							</tr>
						</thead>
					    <tbody>
					    	<c:forEach items="${caseData.usersList}" var="data" varStatus="status">
						        <tr data-dept = "${data.dept_id}">
						            <td>
						            	<form:checkbox path="usersList[${status.index}].checkbox" value="${data.checkbox}"/> 
						            </td>
						        	<td class="text-left"><c:out value="${data.user_name}"/></td>
						        	<td class="text-left"><c:out value="${data.user_id}"/></td>
						        	<td class="text-left"><c:out value="${data.dept_cname}"/></td>
						        </tr>
					        </c:forEach>
						</tbody> 
					</table>  
				</div>
		 	</div>
		</div>
		</form:form>	
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>

    $(function(){            
		$('#btnBack').click(function(){
			$('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_enter.action" />').submit();
		})
		$('#btnEdit').click(function(){
 			$('#eip00w070Form').attr('action', '<c:url value="/Eip00w070_updMember.action" />').submit();
		})
	})
	
	$(document).ready(function() {
	    $('#dept').on('change', function() {
            var selectedValue = $(this).val();
            if(selectedValue == 'all'){
                $('tbody tr').show();
            }else{
                $('tbody tr').hide();
                $('tr[data-dept="' + selectedValue + '"]').show();
            }
	    });
	});
</script>
</jsp:attribute>
</tags:layout>