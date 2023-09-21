<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.SettingController).SETTING"/>
<c:set var="caseData" value="${requestScope[caseKey]}"/>
<tags:layout pgcode="HOME">
    <jsp:attribute name="heads">
    <link rel="stylesheet" href="<c:url value='/css/18698.css' />"/>
        <style>
            
        </style>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <div class="container">
       
        </div>
    </jsp:attribute>
    <jsp:attribute name="footers">
        <script type="text/javascript">
            
        </script>
    </jsp:attribute>
</tags:layout>