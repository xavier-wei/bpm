<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag language="java" pageEncoding="UTF-8" description="上傳檔案選取標籤" %>
<%@ tag import="java.util.Objects" import="org.springframework.web.util.HtmlUtils"%>

<%@ attribute name="name" rtexprvalue="true" required="true"%>
<%@ attribute name="id" rtexprvalue="true" required="false"%>
<%@ attribute name="size" rtexprvalue="true" required="false"%>
<%@ attribute name="cssClass" rtexprvalue="true" required="false"%>
<%@ attribute name="multiple" rtexprvalue="true" required="false"%>
<%@ attribute name="accept" rtexprvalue="true" required="false"%>

<%
	String cssClass = jspContext.getAttribute("cssClass") == null ? "" : HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("cssClass"), ""));
%>
<<%="input"%> type="file"
<%=jspContext.getAttribute("id") == null ? "" : "id=\"" + HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("id"), "")) + "\"" %>
name="<%=HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("name"), "")) %>"
<%=jspContext.getAttribute("size") == null ? "" : "size=\"" + HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("size"), "")) + "\"" %>
<%=jspContext.getAttribute("cssClass") == null ? "" : "class=\"" + HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("cssClass"), "")) + "\"" %>
<%=jspContext.getAttribute("multiple") == null ? "" : "multiple" %>
<%=jspContext.getAttribute("accept") == null ? "" : "accept=\"" + HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("accept"), "")) + "\"" %>
/>