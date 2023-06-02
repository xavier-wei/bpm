<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql"     uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x"       uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acl"     uri="https://www.pcc.gov.tw/common-acl" %>
<%@ taglib prefix="func"    uri="https://www.pcc.gov.tw/common-func" %>
<%@ taglib prefix="sys"     uri="https://www.pcc.gov.tw/system-func" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<spring:eval var="frameworkUserKey" expression="T(tw.gov.pcc.common.helper.UserSessionHelper).FRAMEWORK_USER_INFO" />
<c:set var="frameworkUserData" value="${sessionScope[frameworkUserKey]}" />

<spring:eval var="userKey" expression="T(tw.gov.pcc.common.helper.UserSessionHelper).USER_INFO" />
<c:set var="userData" value="${sessionScope[userKey]}" />

