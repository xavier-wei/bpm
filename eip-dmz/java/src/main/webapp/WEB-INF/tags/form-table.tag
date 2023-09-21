<%@ tag language="java" pageEncoding="UTF-8" description="表單表格佈局標籤" %>
<%@ tag import="java.util.Objects" import="org.springframework.web.util.HtmlUtils"%>

<%@ attribute name="cssClass" rtexprvalue="true" required="false"%>

<div class="form-table <%=HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("cssClass"), "")) %>">
	<jsp:doBody/>
</div>