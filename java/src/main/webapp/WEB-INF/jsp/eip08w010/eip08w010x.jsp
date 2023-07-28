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
    <tags:fieldset>
		<form:form id="eip08w010Form" name="eip08w010Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
                 <form:label path="addMainItemname" cssClass="col-form-label">品名大類：</form:label>
                    <div class="col-6 col-md form-inline">
                        <form:input path="addMainItemname" cssClass="form-control" size="19" maxlength="18"/>
                    </div>
            </tags:form-row>
            <tags:form-row>
                 <form:label path="addMainItemno" cssClass="col-form-label">品名大類代號：</form:label>
                    <div class="col-6 col-md form-inline">
                        <form:input path="addMainItemno" cssClass="form-control eng_num_only upCase" size="1" maxlength="1"/>
                    </div>
            </tags:form-row>
            <tags:form-row>
				<div class="table-responsive">	           
					<table class="table" id="listTable">
						<thead data-orderable="true">
							<tr>
								<th class="text-center" width=5%></th>
								<th class="text-center" width=10%>序號</th>
								<th class="text-left" width=20%>品名大類代號</th>
								<th class="text-left" width=50%>品名大類</th>
								<th class="text-center" width=15%></th>
							</tr>
						</thead>
					    <tbody>
					    	<c:forEach items="${caseData.resultKindList}" var="data" varStatus="status">
						        <tr>
						        	<td class="text-center"><form:checkbox path="resultKindList[${status.index}].checkbox"  cssClass="checkedgreen"/></td>
						        	<td class="text-center"><c:out value="${status.index+1}"/></td>
						        	<td class="text-left"><c:out value="${data.itemno}"/></td>
									<td class="text-left">
										<form:input path="resultKindList[${status.index}].itemname" cssClass="form-control" size="30" maxlength="10"/>
						            </td>
						            <td class="text-center">
							            <tags:button cssClass="btnEdit"
 	                                             onclick="detail('${data.itemno}','${data.itemname}')">明細<i class="fas fa-edit"></i></tags:button>
						            </td>
						        </tr>
					        </c:forEach>
						</tbody> 
					</table>  
				</div>
				<form:hidden path = 'mainkindno' />
				<form:hidden path = 'mainkindname' />
            </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
	$(function(){
		var config = getDataTablesConfig();
		var table = $('#listTable').DataTable(config);
		
		$('#btnInsert').click(function(){
			$('#eip08w010Form').attr('action', '<c:url value="/Eip08w010_add.action" />').submit();
		})
		$('#btnDelete').click(function(){
			showConfirm('其品名大類下的資料會全部刪除，是否確認刪除此品名大類?',()=>{
				$('#eip08w010Form').attr('action', '<c:url value="/Eip08w010_deleteMain.action" />').submit();
            });
		})
		$('#btnEdit').click(function(){
			$('#eip08w010Form').attr('action', '<c:url value="/Eip08w010_editMain.action" />').submit();
		})
		$('#btnReturnMain').click(function(){
			$('#eip08w010Form').attr('action', '<c:url value="/Eip08w010_enter.action" />').submit();
		})
		
 	})
	function detail(mainkindno,mainkindname) {
        $('#mainkindno').val(mainkindno);
        $('#mainkindname').val(mainkindname);
        $('#eip08w010Form').attr('action', '<c:url value="/Eip08w010_detailQuery.action" />').submit();
    }
</script>
</jsp:attribute>
</tags:layout>