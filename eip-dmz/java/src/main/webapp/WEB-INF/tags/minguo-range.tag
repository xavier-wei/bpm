<%@ tag language="java" pageEncoding="UTF-8" description="民國日期範圍佈局標籤" %>
<%@ attribute name="start" rtexprvalue="true" required="false"%>
<%@ attribute name="end" rtexprvalue="true" required="false"%>
<%@ attribute name="pattern" rtexprvalue="true" required="false"%>
<%@ attribute name="delimiter" rtexprvalue="true" required="false"%>

<%@ include file="/WEB-INF/jsp/includes/include.jsp"%>

<func:minguo value="${start}"/>
<c:if test="${not empty start and not empty end}">
<c:out value="${empty delimiter ? '~': delimiter}" />
</c:if>
<func:minguo value="${end}"/>