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
                top.location = self.location;
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
            #menu{
                color: #3267c3;
            }
            #menu:active, #menu:focus{
                background-color: lightblue;
                color: white;
            }
        </style>
        <jsp:invoke fragment="heads"/>
    </head>

    <body>
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
                                    <span class="mt-1 mr-auto">
                                        <i id="menu" class="fas fa-bars fa-2x fa-border" data-toggle="modal" data-target="#menuModal"></i>
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
                            <tags:menu items="${frameworkUserData.menu.menuItemList}"
                                       cssClass="nested active"></tags:menu>
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