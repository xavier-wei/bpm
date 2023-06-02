<%@ page import="tw.gov.pcc.eip.util.ExceptionUtility" %>
<%@ page import="tw.gov.pcc.eip.util.ObjectUtility" %>
<%@ page language="java" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%
    org.slf4j.LoggerFactory.getLogger(Exception.class)
            .error("{}\r\n{}", ObjectUtility.normalizeObject(exception.getMessage()), ObjectUtility.normalizeObject(ExceptionUtility.getStackTrace(exception))); //將log導回log4j
%>
<c:redirect url="/html/error.html"/>