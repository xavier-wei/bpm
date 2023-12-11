<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w300Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	<tags:button id="btnSearch">
    	查詢<i class="fas fa-search"></i>
    </tags:button>
    <tags:button id="btnInsert">
    	新增<i class="fas fa-user-plus"></i>
    </tags:button>     
    <tags:button id="btnClear">
        清除<i class="fas fa-eraser"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip00w300Form" name="eip00w300Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
                 <form:label path="user_id" cssClass="col-form-label">員工編號：</form:label>
                    <div class="col-6 col-md form-inline">
                        <form:input path="user_id" cssClass="form-control eng_num_only" size="19" maxlength="18"/>
                    </div>
            </tags:form-row>
            <tags:form-row>
                 <form:label path="dept_id" cssClass="col-form-label">部門代號：</form:label>
                    <div class="col-6 col-md form-inline">
                        <form:input path="dept_id" cssClass="form-control eng_num_only" size="19" maxlength="18"/>
                    </div>
            </tags:form-row>
        </form:form>
    </tags:fieldset>
    <tags:form-row>
		<div class="table-responsive">	           
			<table class="table" id="listTable">
				<thead data-orderable="true">
					<tr>
						<th class="text-center">員工編號</th>
						<th class="text-center">員工姓名</th>
						<th class="text-center">授權審核部門代號</th>
						<th class="text-center">部門名稱</th>
						<th class="text-center"></th>
					</tr>
				</thead>
			    <tbody>
			    	<c:forEach items="${caseData.user_auth_deptList}" var="data" varStatus="status">
				        <tr>
				        	<td class="text-left"><c:out value="${data.user_id}"/></td>
				        	<td class="text-left"><c:out value="${data.user_name}"/></td>
				        	<td class="text-left"><c:out value="${data.dept_id}"/></td>
				        	<td class="text-left"><c:out value="${data.dept_name}"/></td>

				            <td class="text-center">
			            		<tags:button cssClass="btnEdit" 
                                    onclick="doDelete('${data.user_id}','${data.dept_id}')">刪除</tags:button>
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
	$(function(){
		var config = getDataTablesConfig();
		var table = $('#listTable').DataTable(config);
		
		$('#btnSearch').click(function(){
			$('#eip00w300Form').attr('action', '<c:url value="/Eip00w300_query.action" />').submit();
		})
		$('#btnInsert').click(function(){
			$('#eip00w300Form').attr('action', '<c:url value="/Eip00w300_add.action" />').submit();
		})
	})
	function doDelete(user_id, dept_id) {
	    showConfirm('確定要刪除資料？', () => {
            $('#user_id').val(user_id);
            $('#dept_id').val(dept_id);
            $('#eip00w300Form').attr('action', '<c:url value="/Eip00w300_delete.action" />').submit();
        });

    }

    // 清除
    $('#btnClear').click(function(e) {
       $('#user_id').val('');
       $('#dept_id').val('');
       $('#eip00w300Form').attr('action', '<c:url value="/Eip00w300_enter.action" />').submit();
       $('#listTable').val('');
    });

</script>
</jsp:attribute>
</tags:layout>