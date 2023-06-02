<%@ tag language="java" pageEncoding="UTF-8" description="文字欄位佈局標籤"%>
<%@ tag import="java.util.Objects" import="org.springframework.web.util.HtmlUtils"%>

<%@ attribute name="label" rtexprvalue="true" required="false"%>
<%@ attribute name="labelClass" rtexprvalue="true" required="false"%>

<%@ include file="/WEB-INF/jsp/includes/include.jsp"%>

<div class="d-flex no-gutters">
    <div class="col-label <%=HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("labelClass"), "")) %>"><c:out value="${label}"></c:out>：</div>
    <div class="col-value">
    <jsp:doBody />  
    </div>
</div>