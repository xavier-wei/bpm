<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<tags:bpmLayout>
    <jsp:attribute name="contents">
            <iframe style="width: 100%;height: 100%" src="${bpmPath}"></iframe>
    </jsp:attribute>
</tags:bpmLayout>
