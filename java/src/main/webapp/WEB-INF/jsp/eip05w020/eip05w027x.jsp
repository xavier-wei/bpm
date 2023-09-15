<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip05w020Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>

    </style>
</jsp:attribute>
    <jsp:attribute name="buttons">
    <tags:button id="btnReply">
        返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
<form:form id="eip05w020Form" modelAttribute="${caseKey}">
    <tags:fieldset legend="意見調查填寫內容">
    <%@ include file="/WEB-INF/jsp/eip05w020/eip05w002x.jsp" %>
    <form:hidden path="osformno"/>
    </tags:fieldset>
</form:form>


</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    $('#btnReply').click(function(e){
        e.preventDefault();
        $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_contentQuery.action" />').submit();
    })
})
</script>
</jsp:attribute>
</tags:layout>