<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ tag import="java.util.*" import="org.springframework.web.util.HtmlUtils" import="tw.gov.pcc.common.util.TagUtil" %>
<%@ tag dynamic-attributes="dynamicAttributesVar" %>

<%@ attribute name="cssClass" rtexprvalue="true" required="false" %>
<%@ attribute name="items" type="java.util.Collection" rtexprvalue="true" required="true" %>

<c:forEach var="item" items="${items}">
    <c:choose>
        <c:when test="${item.url == '#'}">
            <div class="list-group-item list-group-item-action">
                <h5><c:out value="${item.functionName}"/></h5>
                <c:if test="${not empty item.sub}">
                    <tags:menu items="${item.sub}"></tags:menu>
                </c:if>
            </div>
        </c:when>
        <c:otherwise>
            <a href="<c:url value="${item.url}"/>"
               class="list-group-item list-group-item-action">${item.functionName}</a>
        </c:otherwise>
    </c:choose>
</c:forEach>
