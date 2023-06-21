<%@ tag language="java" pageEncoding="UTF-8" description="按鈕佈局標籤" %>
<%@ tag import="java.util.*" import="org.springframework.web.util.HtmlUtils" import="tw.gov.pcc.common.util.TagUtil"%>
<%@ tag dynamic-attributes="dynamicAttributesVar" %>

<%@ attribute name="cssClass" rtexprvalue="true" required="false"%>

<button <%=TagUtil.getAttributes((Map<String, Object>) jspContext.getAttribute("dynamicAttributesVar"))%>
	type="button" class="btn btn-outline-be <%=HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("cssClass"), "")) %>">
	<span class="btn-txt"><jsp:doBody/></span>
</button>