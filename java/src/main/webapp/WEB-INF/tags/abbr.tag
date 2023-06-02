<%@ tag language="java" pageEncoding="UTF-8" description="文字縮寫標籤" %>
<%@ tag import="java.util.*" import="org.springframework.web.util.HtmlUtils" import="tw.gov.pcc.common.util.TagUtil"%>

<%@ attribute name="length" rtexprvalue="true" required="true"%>
<%@ attribute name="value" rtexprvalue="true" required="false"%>

<%@ include file="/WEB-INF/jsp/includes/include.jsp"%>

<c:choose>
    <c:when test="${fn:length(value) > length}">
    	<span class="tip" data-tooltip="<%=HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("value"), "")) %>"><c:out value="${fn:substring(value, 0, length)}"/>...</span>
    </c:when>
    <c:otherwise>
    	<c:out value="${value}"></c:out>
    </c:otherwise>
</c:choose>