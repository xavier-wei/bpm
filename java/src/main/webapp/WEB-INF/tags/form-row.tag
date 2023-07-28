<%@ attribute name="id" %>
<%@ tag language="java" pageEncoding="UTF-8" description="表單行佈局標籤" %>
<%@ tag import="java.util.*" import="org.springframework.web.util.HtmlUtils" import="tw.gov.pcc.common.util.TagUtil"%>

<%@ tag dynamic-attributes="dynamicAttributesVar" %>

<%@ attribute name="cssClass" rtexprvalue="true" required="false"%>

<div class="form-row py-1 no-gutters <%=HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("cssClass"), "")) %>" <%=TagUtil.getAttributes((Map<String, Object>) jspContext.getAttribute("dynamicAttributesVar"))%>>
	<jsp:doBody/>
</div>


