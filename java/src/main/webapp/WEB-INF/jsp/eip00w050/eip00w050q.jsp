<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w050Controller).CASE_KEY"/>
<c:set var="caseData" value="${requestScope[caseKey]}"/>
<tags:layout>
<jsp:attribute name="buttons">
    	<tags:button id="btnNext">下一步<i class="fas fa-play"></i></tags:button>
</jsp:attribute>
    <jsp:attribute name="contents">
    <tags:fieldset legend="查詢條件">
        <form:form id="eip00w050Form" modelAttribute="${caseKey}">
            <tags:form-row>
                <form:label cssClass="col-form-label" path="sys_id">請先挑選系統：</form:label>
                <div class="col-12 col-md m-2">
                    <form:select path="sys_id">
                        <form:options items="${systems}" itemLabel="sys_name"
                                      itemValue="sys_id"/>
                    </form:select>
                </div>
            </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
    <jsp:attribute name="footers">
<script type="text/javascript">
    $(function () {
        $('#btnNext').click(function () {
            $('#eip00w050Form').attr('action', '<c:url value="/Eip00w050_list.action"/>').submit();
        });
    });
</script>
</jsp:attribute>
</tags:layout>