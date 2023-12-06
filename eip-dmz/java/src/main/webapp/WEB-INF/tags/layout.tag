<%@ tag import="org.apache.commons.io.FilenameUtils" %>
<%@ tag import="org.apache.commons.lang3.StringUtils" %>
<%@ tag import="org.springframework.web.util.HtmlUtils" %>
<%@ tag language="java" pageEncoding="UTF-8" description="HTML佈局標籤" %>
<%@ attribute name="heads" fragment="true" required="false" %>
<%@ attribute name="buttons" fragment="true" required="false" %>
<%@ attribute name="contents" fragment="true" required="false" %>
<%@ attribute name="footers" fragment="true" required="false" %>

<%@ attribute name="title" rtexprvalue="true" required="false" %>
<%@ attribute name="pgcode" rtexprvalue="true" required="false" %>

<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<!DOCTYPE html>
<html lang="zh">
<head>
    <style id="antiClickjack">
        body {
            display: none !important;
        }
    </style>
    <script type="text/javascript">
        if (self === top) {
            var antiClickjack = document.getElementById("antiClickjack");
            antiClickjack.parentNode.removeChild(antiClickjack);
        } else {
            ${"top.location = self.location;"}
        }
    </script>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="utf-8">
    <title><spring:message code="title.system.name"/><func:serverInfo/></title>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <link rel="stylesheet" href="<c:url value='/css/web-fonts-with-css/css/fontawesome-all.css' />"/>
    <link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" href="<c:url value='/css/bootstrap-select.min.css' />"/>
    <link rel="stylesheet" href="<c:url value='/css/rowGroup.dataTables.min.css' />"/>
    <link rel="stylesheet" href="<c:url value='/css/buttons.dataTables.min.css' />"/>
    <link rel="stylesheet" href="<c:url value='/css/noty.css' />"/>

    <link rel="stylesheet" href="<c:url value='/css/tag.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/css/default.css' />"/>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/css/framework.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/css/print.css'/>" media="print"/>
    <link rel="stylesheet" href="<c:url value="/js/dynatree/skin/ui.dynatree.css"/>">
    <script type="text/javascript">
        if (navigator.userAgent.indexOf("MSIE") > 0 || navigator.userAgent.indexOf("Trident") > 0) {
            alert('本網站不支援IE，請使用EDGE或CHROME瀏覽器。')
        }
    </script>
    <style>
        #menu {
            color: #3267c3;
        }

        #menu:active, #menu:focus {
            background-color: lightblue;
            color: white;
        }
    </style>
    <jsp:invoke fragment="heads"/>
</head>

<body>
<div id="tree" class="d-none tree">
    <ul id="menuOrg" class="root">
        <li>
            <span class="caret caret-down caretRoot">行政支援系統(EIP)</span>
            <tags:menu items="${frameworkUserData.menu.menuItemList}"
                       cssClass="nested active"></tags:menu>
        </li>
    </ul>
</div>
<div class="web frameDiv">
    <div class="nav">
        <img src="<c:url value="/images/background.png?v=1"/>" alt="" class="bg"/>
        <span class="ml-auto mr-1 mt-auto">登入帳號：<c:out value="${frameworkUserData.userId}"/></span>
    </div>
    <div class="wrapper">
        <div class="content">
            <c:if test="${not fn:contains(requestScope['javax.servlet.forward.servlet_path'],'_') && sessionScope['_setting'] != null}">
                <c:set target="${sessionScope['_setting']}" property="closed" value="Y"/>
            </c:if>

            <div class="page">
                <div id="main" class="main">
                    <div class="formButtonDiv mt-1">
                        <span class="mt-1 mr-auto d-flex">
                            <i id="menu" class="fas fa-bars fa-2x fa-border" data-toggle="modal"
                               data-target="#menuModal"></i>
                            <nav aria-label="breadcrumb">
                                <ol class="breadcrumb p-1 breadcrumb2 m-0" id="menuPath">
                                    <li class="breadcrumb-item align-middle">
                                        <a href="<c:url value='/LoginForward.action' />">
                                            <svg aria-hidden="true" focusable="false" data-prefix="fas"
                                                 data-icon="home" role="img"
                                                 xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512"
                                                 class="svg-inline--fa fa-home fa-w-18">
                                                <path fill="currentColor"
                                                      d="M280.37 148.26L96 300.11V464a16 16 0 0 0 16 16l112.06-.29a16 16 0 0 0 15.92-16V368a16 16 0 0 1 16-16h64a16 16 0 0 1 16 16v95.64a16 16 0 0 0 16 16.05L464 480a16 16 0 0 0 16-16V300L295.67 148.26a12.19 12.19 0 0 0-15.3 0zM571.6 251.47L488 182.56V44.05a12 12 0 0 0-12-12h-56a12 12 0 0 0-12 12v72.61L318.47 43a48 48 0 0 0-61 0L4.34 251.47a12 12 0 0 0-1.6 16.9l25.5 31A12 12 0 0 0 45.15 301l235.22-193.74a12.19 12.19 0 0 1 15.3 0L530.9 301a12 12 0 0 0 16.9-1.6l25.5-31a12 12 0 0 0-1.7-16.93z"
                                                      class=""></path>
                                            </svg>
                                            行政支援系統(DMZ)</a></li>

                                    <%
                                        String pageName = FilenameUtils.getBaseName(StringUtils.upperCase(StringUtils.defaultString(application.getRealPath(request.getServletPath()))));
                                        pageName = StringUtils.defaultIfBlank((String) jspContext.getAttribute("pgcode"), pageName);
                                    %>
                                </ol>
                            </nav>
                        </span>
                        <span class="ml-auto">
                                    <c:if test="${not empty buttons}">
                                        <jsp:invoke fragment="buttons"/>
                                    </c:if>
                        </span>
                    </div>
                    <jsp:invoke fragment="contents"/>
                </div>

                <div id="msgarea" class="fixed-bottom">
                    <div id="footer" class="footer">
                        <div class="alert alert-info mb-0 p-0 prog infotitle" role="alert">
                            <sys:systemMessage/></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="menuModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">功能選單</h5>
            </div>
            <div class="modal-body">
                <div class="list-group">
                    <tags:menuDMZ items="${frameworkUserData.menu.menuItemList}"
                                  cssClass="nested active"></tags:menuDMZ>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">關閉</button>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/includes/js.jsp" %>

<script type="text/javascript">
    let entryPath;
    $(function () {
        entryPath = '<c:url value="${requestScope['javax.servlet.forward.servlet_path']}" />';
        if (entryPath.indexOf(".action") > -1) {
            let funcPath = entryPath.substring(entryPath.lastIndexOf("/"));
            if (funcPath.indexOf("_") > -1) {
                entryPath = entryPath.substring(0, entryPath.lastIndexOf("_") + 1);
            }
            const menus = $("#menuOrg a[href^='" + entryPath + "']").parents('li');
            // $(menus).children(".caret:not(.caret-down)").click();
            const names = $.map(menus, function (item) {
                let children = $(item).children("a,span");
                nameurl = {};
                nameurl.text = children.text();
                if (children.attr('href')) {
                    nameurl.url = children.attr('href');
                } else {
                    nameurl.url = '';
                }
                return nameurl;
            });
            $('#menuPath').append($.map(names, function (item) {
                if (item.url) {
                    return '<li class=\"breadcrumb-item align-middle p-0 \"> <a href=\"' + item.url + '\"> <svg aria-hidden=\"true\" focusable=\"false\" data-prefix=\"fas\" data-icon=\"tasks\" role=\"img\" xmlns=\"http:\/\/www.w3.org\/2000\/svg\" viewBox=\"0 0 512 512\" class=\"svg-inline--fa fa-tasks fa-w-16\">  <path fill=\"currentColor\" d=\"M139.61 35.5a12 12 0 0 0-17 0L58.93 98.81l-22.7-22.12a12 12 0 0 0-17 0L3.53 92.41a12 12 0 0 0 0 17l47.59 47.4a12.78 12.78 0 0 0 17.61 0l15.59-15.62L156.52 69a12.09 12.09 0 0 0 .09-17zm0 159.19a12 12 0 0 0-17 0l-63.68 63.72-22.7-22.1a12 12 0 0 0-17 0L3.53 252a12 12 0 0 0 0 17L51 316.5a12.77 12.77 0 0 0 17.6 0l15.7-15.69 72.2-72.22a12 12 0 0 0 .09-16.9zM64 368c-26.49 0-48.59 21.5-48.59 48S37.53 464 64 464a48 48 0 0 0 0-96zm432 16H208a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16h288a16 16 0 0 0 16-16v-32a16 16 0 0 0-16-16zm0-320H208a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16h288a16 16 0 0 0 16-16V80a16 16 0 0 0-16-16zm0 160H208a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16h288a16 16 0 0 0 16-16v-32a16 16 0 0 0-16-16z\" class=\"\"><\/path> <\/svg>' + item.text + ' <\/a><\/li>';
                } else {
                    return '<li class=\"breadcrumb-item align-middle p-0 \"> ' + item.text + ' <\/li>';
                }
            }).reverse().slice(1).join('')).find('li:last a').append('<span id="pageTitle"><%= HtmlUtils.htmlEscape(pageName) %></span>');
            if (names.length && !$('#pageTitle').text()) {
                $('#pageTitle').text(names[0]);
            }
        }

        inputFieldFocus();
        showAlert('<sys:systemAlertMessage />');
        const validationResult = '<sys:validationMessage />'.split('|@|@|');
        showAlert(validationResult[1], validationResult[0]);
        showConfirms('<sys:confirmMessage />');

        $(".infotitle").click(function () {
            $("#msgarea").toggleClass("d-none");
        });
        $('input:checkbox:enabled[data-checkall]').on('click', checkall);
    });
</script>
<jsp:invoke fragment="footers"/>
</body>
</html>