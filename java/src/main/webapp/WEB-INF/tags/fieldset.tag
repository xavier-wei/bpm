	<%@ tag language="java" pageEncoding="UTF-8" description="HTML fieldset佈局標籤" %>
<%@ tag import="java.util.Objects" import="org.springframework.web.util.HtmlUtils"%>

<%@ attribute name="legend" rtexprvalue="true" required="false"%>

<fieldset>
   	<legend><%=HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("legend"), "查詢條件")) %></legend>
   	<jsp:doBody/>
</fieldset>