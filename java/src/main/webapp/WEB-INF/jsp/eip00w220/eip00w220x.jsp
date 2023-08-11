<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--@elvariable id="innerMenu" type="tw.gov.pcc.common.domain.MenuItem"--%>
<%--@elvariable id="level" type="java.lang.String"--%>
<c:if test="${empty innerMenu.sub}">
    <!-- 生成最後層級的菜單項目 -->
    <c:if test="${innerMenu.levelNo eq '2'}">
        <div class="pl-0 pr-9 container">
            <div class="col-12 p-0 mb-3 col">
                <div class="card">
                    <div class="card-header py-1 px-3">
                        <h4 class="m-0">${innerMenu.functionName}</h4>
                    </div>
                    <div class="card-body p-3">
                        <div class="d-flex flex-wrap col-12 p-0 col">
                            <a href="<c:url value="${innerMenu.url}"/>"
                               class="px-0 pt-2 pb-2 col col-4">${innerMenu.functionName}</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${innerMenu.levelNo ne '2'}">
        <a href="<c:url value="${innerMenu.url}"/>"
           class="px-0 pt-2 pb-2 col col-4">${innerMenu.functionName}</a>
    </c:if>
</c:if>
<c:if test="${not empty innerMenu.sub}">
    <!-- 生成非最後層級的菜單項目 -->
    <div class="pl-0 pr-9 container">
        <div class="col-12 p-0 mb-3 col">
            <div class="card">
                <div class="card-header py-1 px-3">
                    <h4 class="m-0">${innerMenu.functionName}</h4>
                </div>
                <c:if test="${level > 1}">
                    <div class="card-body p-3">
                        <div class="d-flex flex-wrap col-12 p-0 col">
                            <c:forEach var="subItem" items="${innerMenu.sub}">
                                <c:set var="innerMenu" value="${subItem}" scope="request"/>
                                <c:set var="level" value="${level}" scope="request"/>
                                <c:import url="/WEB-INF/jsp/eip00w220/eip00w220x.jsp"/>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</c:if>
