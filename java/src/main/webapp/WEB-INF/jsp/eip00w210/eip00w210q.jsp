<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w210Controller).CASE_KEY"/>
<c:set var="caseData" value="${sessionScope[caseKey]}"/>
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
    <tags:button id="btnSave">
        確認<i class="fas fa-user-check"></i>
    </tags:button>
</jsp:attribute>
    <jsp:attribute name="contents">
    <tags:fieldset legend="設定">
		<form:form id="eip00w210Form" name="eip00w210Form" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
                 <form:label path="level" cssClass="col-form-label">網站導覽作業顯示層階：</form:label>
                <div class="col-12 col-md-6 m-2">
                    <form:select path="level" items="${caseData.levelList}" itemLabel="left" itemValue="right"/>
                </div>
            </tags:form-row>
           
        </form:form>
    </tags:fieldset>
</jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function () {
        $('#btnSave').click(function () {
            $('#eip00w210Form').attr('action', '<c:url value="/Eip00w210_save.action" />').submit();
        });
    });
</script>
</jsp:attribute>
</tags:layout>