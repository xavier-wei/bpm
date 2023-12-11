<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w300Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
    <tags:button id="btnAdd">
    	新增<i class="fas fa-user-plus"></i>
    </tags:button>          
    <tags:button id="btnBack">
    	取消<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>
<jsp:attribute name="contents">
    <tags:fieldset legend="授權審核部門新增">
		<form:form id="eip00w300Form" name="eip00w300Form" modelAttribute="${caseKey}" method="POST">
                <tags:form-row>
                    <div class="col-12 col-md form-inline">
                        <form:label cssClass="col-form-label star" path="unitOption">指定人員單位：</form:label>
                        <form:select path="unitOption" cssClass="form-control">
                            <c:forEach var="item" items="${caseData.unitOptions}" varStatus="status">
                                <form:option value="${item.codeno}">
                                    <c:out value="${item.codename}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                        <form:label cssClass="col-form-label star" path="user_id">指定人員：</form:label>
                        <form:select path="user_id" cssClass="form-control">
                        </form:select>
                    </div>
                </tags:form-row>

            <tags:form-row>
                <form:label cssClass="col-form-label star" path="dept_id">授權審核部門代號：</form:label>
                <div class="col-12 col-md form-inline">
			        <form:select path="dept_id" cssClass="form-control">
						<form:option value="">請選擇</form:option>
							<c:forEach items="${caseData.deptList}" var="item" varStatus="status">
								<form:option value="${item.dept_id}">${item.dept_id}-${item.dept_name}</form:option>
							</c:forEach>
					</form:select>
				</div>
            </tags:form-row>

        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){

                function getUsersOptions(deptId, userId) {
                    $.ajax({
                        type: "POST",
                        url: '<c:url value="/Eip00w300_getUsersData.action" />',
                        data: {
                            'deptid': deptId
                        },
                        timeout: 100000,
                        success: function(data) {
                            if (data != null) {
                                $.each(data, function(i, e) {
                                    $('#user_id').append("<option value='" + i +
                                        "'>" + e + "</option>");
                                });
                            }
                            if (userId !== '') {
                                $("#user_id option[value=" + userId + "]").prop(
                                    'selected', 'selected');
                            }
                        },
                        error: function(e) {
                            showAlert("取得資料發生錯誤");
                        }
                    });
                }

                $('#unitOption').on('change', function(){
                    var selectValue = $(this).val();
                    $('#user_id').empty();
                    getUsersOptions(selectValue);
                });
                var unitOption = '<c:out value="${caseData.unitOption}" />';
                var user_id = '<c:out value="${caseData.user_id}" />';
                getUsersOptions(unitOption, user_id); // 取得使用者選單(聯絡單位, 聯絡人)

	$('#btnAdd').click(function(){
		$('#eip00w300Form').attr('action', '<c:url value="/Eip00w300_insert.action" />').submit();
	})
	$('#btnBack').click(function(){
		$('#eip00w300Form').attr('action', '<c:url value="/Eip00w300_back.action" />').submit();
	})

})
</script>
</jsp:attribute>
</tags:layout>