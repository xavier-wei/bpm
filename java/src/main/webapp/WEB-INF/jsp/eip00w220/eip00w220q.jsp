<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w220Controller).CASE_KEY"/>
<c:set var="caseData" value="${requestScope[caseKey]}"/>
<tags:layout pgcode="SITEMAP">
    <jsp:attribute name="heads">
        <style>
            .card-header {
                background-color: #1aa4b7;
                color: #FFFFFF;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="contents">
        ${caseData.rawResult}
    </jsp:attribute>
    <jsp:attribute name="footers">
        <script type="text/javascript">
            $(function () {
            });
        </script>
    </jsp:attribute>
</tags:layout>