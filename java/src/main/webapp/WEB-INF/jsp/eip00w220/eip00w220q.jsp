<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey"
             expression="T(tw.gov.pcc.eip.adm.controllers.Eip00w220Controller).CASE_KEY"/>
<c:set var="caseData" value="${requestScope[caseKey]}"/>
<tags:layout pgcode="SITEMAP">
    <jsp:attribute name="heads">
        <style>
          /*recursive call used*/
          .card-header {
            background-color: #1aa4b7;
            color: #FFFFFF;
          }
        </style>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <c:forEach items="${frameworkUserData.menu.menuItemList}" var="menu">
            <c:set var="innerMenu" value="${menu}" scope="request"/>
            <c:set var="level" value="${caseData.level}" scope="request"/>
            <c:import url="/WEB-INF/jsp/eip00w220/eip00w220x.jsp"/>
        </c:forEach>
    </jsp:attribute>
    <jsp:attribute name="footers">
    </jsp:attribute>
</tags:layout>