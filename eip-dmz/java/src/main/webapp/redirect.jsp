<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<c:if test="${not empty sessionScope.redirectUrl}">
    <c:redirect url="${sessionScope.redirectUrl}"/>
    <c:remove var="redirectUrl" scope="session"/>
</c:if>
</body>
</html>
