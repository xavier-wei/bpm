<%@ tag language="java" pageEncoding="UTF-8" description="tab標籤" %>
<%@ tag import="java.util.*" import="org.springframework.web.util.HtmlUtils" import="tw.gov.pcc.common.util.TagUtil"%>

<%@ attribute name="id" rtexprvalue="true" required="false"%>
<%@ attribute name="tabsSourceKey" rtexprvalue="true" required="false"%>
<%@ attribute name="currentTabIndx" rtexprvalue="true" required="false"%>

<%@ include file="/WEB-INF/jsp/includes/include.jsp"%>

<div id="<%=HtmlUtils.htmlEscape(Objects.toString(jspContext.getAttribute("id"), "tabsLink")) %>" class="row mb-4 ml-1">
	<div class="col-6 col-md">
		<c:forEach items='<%= session.getAttribute(Objects.toString(jspContext.getAttribute("tabsSourceKey")))%>' var="tabs" varStatus="status">
			<c:set var="tabCssClass" value=""/>
			<c:if test="${status.last}">
				<c:set var="tabCssClass" value="nav-tabs"/>
			</c:if>
			<ul class="nav text-center ${tabCssClass } mt-1">
				<c:forEach items='${tabs}' var="tab" varStatus="status">
					<c:set var="activeCssClass" value=""/>
					<c:set var="cursorStyle" value="cursor: pointer"/>
					<c:if test="${currentTabIndx eq tab.order}">
						<c:set var="activeCssClass" value="font-weight-bold active"/>
						<c:set var="cursorStyle" value=""/>
					</c:if>
					<li class="nav-item mr-1"><a class="${activeCssClass} nav-link px-3" style="${cursorStyle}" data-action="<c:url value='${tab.linkAction}'/>"  data-current-tab-indx="${currentTabIndx}" data-this-indx="${tab.order }">${tab.tabName }</a></li>
				</c:forEach>
			</ul>
		</c:forEach>
	</div>
</div>