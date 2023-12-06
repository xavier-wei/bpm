<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags"   tagdir="/WEB-INF/tags" %>

<%@ tag import="java.util.*" import="org.springframework.web.util.HtmlUtils" import="tw.gov.pcc.common.util.TagUtil"%>
<%@ tag dynamic-attributes="dynamicAttributesVar" %>

<%@ attribute name="cssClass" rtexprvalue="true" required="false" %>
<%@ attribute name="items" type="java.util.Collection" rtexprvalue="true" required="true" %>

<ul class="<%=HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("cssClass"), "")) %>" <%=TagUtil.getAttributes((Map<String, Object>) jspContext.getAttribute("dynamicAttributesVar"))%>>
	<c:forEach var="item" items="${items}">
		<li>
			<c:choose>
				<c:when test="${item.url == '#'}">
					<span class="caret"><c:out value="${item.functionName}"/></span>
					<c:if test="${not empty item.sub}">
						<tags:menu items="${item.sub}" cssClass="nested"></tags:menu>
					</c:if>
				</c:when>
				<c:otherwise>
					<a href='<c:url value="${item.url}" />'><c:out value="${item.functionName}"/></a>
				</c:otherwise>
			</c:choose>
		</li>
	</c:forEach>
</ul>