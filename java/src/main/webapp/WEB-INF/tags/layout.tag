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
<html>
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
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-datepicker.css"/>"/>

    <script type="text/javascript">
        if (navigator.userAgent.indexOf("MSIE") > 0 || navigator.userAgent.indexOf("Trident") > 0) {
            alert('本網站不支援IE，請使用EDGE或CHROME瀏覽器。')
        }
    </script>

    <style>
        .flash {
            animation-name: flash-animation;
            animation-duration: 0.5s;
        }

        @keyframes flash-animation {
            0% {
                background-color: lightskyblue;
            }
            100.0% {
                background-color: white;
            }
        }
    </style>

    <jsp:invoke fragment="heads"/>
</head>

<body>
<div class="web frameDiv">
    <div class="nav bg">
        <a href="<c:url value='/LoginForward.action' />" class="clickDiv" title="返回首頁"></a>
        <div class="sys_info">
            <div class="row flex-row-reverse m-auto" id="title_status">
                <div class="col-md-2 top_3 sitemap">
                    <a href="<c:url value='/Common_sitemap.action' />">
                        <img src="./images/top_icon2.png" alt="" class="d-inline-block align-middle">
                        <div class="d-inline-block align-middle text-center">
                            <span class="title_04">網站導覽</span>
                        </div>
                    </a>
                </div>
				<div id="eip0aw011Case" class="col-md-3 top_3 title_status">
					<a href="${eip0aw011Case.apiResult.click_url}" target="_blank" style="text-decoration:none;"
					   class="title_05">
						<img src="./images/top_icon4.png" alt="" class="d-inline-block align-middle">
						<div class="d-inline-block align-middle text-center">
							<div class="title_01">待處理公文</div>
							<span class="title_02">${eip0aw011Case.apiResult.cnt}</span>
							<span class="title_03">件</span>
						</div>
					</a>
				</div>
				<div id="eip0aw010Case" class="col-md-3 top_3 title_status">
					<a href="${eip0aw010Case.apiResult.click_url}" target="_blank" style="text-decoration:none;"
					   class="title_05">
						<img src="./images/top_icon1.png" alt="" class="d-inline-block align-middle">
						<div class="d-inline-block align-middle text-center">
							<div class="title_01">待簽核件數</div>
							<span class="title_02">${eip0aw010Case.apiResult.cnt}</span>
							<span class="title_03">件</span>
						</div>
					</a>
				</div>
                <div class="col-md-3 top_3 bpm_title_status">
                    <a href="<c:url value='/Bpm01w030_enter.action' />">
                        <img src="./images/top_icon3.png" alt="" class="d-inline-block align-middle">
                        <div class="d-inline-block align-middle text-center">
                            <div class="bpm_title_01">待處理表單</div>
                            <span class="bpm_title_02" id="bpm_pending"></span>
                            <span class="bpm_title_03">件</span>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <div class="sys_info">
            <div>登入帳號：<c:out value="${frameworkUserData.userId}-${frameworkUserData.userName}"/></div>
            <div> 單位：<c:out value="${frameworkUserData.deptName}"/></div>
            <div> 登入日期：<c:out value="${frameworkUserData.loginDateString}"/></div>
            <div>登入時間：<c:out value="${frameworkUserData.loginTimeString}"/></div>
        </div>
    </div>
    <div class="wrapper">
        <div class="content">
            <c:if test="${not fn:contains(requestScope['javax.servlet.forward.servlet_path'],'_') && sessionScope['_setting'] != null}">
                <c:set target="${sessionScope['_setting']}" property="closed" value="Y"/>
            </c:if>
            <div id="sidenav"
                 class="sidenav <c:if test="${sessionScope['_setting'].closed eq 'N'}">activeBar</c:if> d-none">
                <div id="tree" class="tree">
                    <ul id="menu" class="root">
                        <li>
                            <span class="caret caret-down caretRoot">行政支援系統(EIP)</span>
                            <tags:menu items="${frameworkUserData.menu.menuItemList}"
                                       cssClass="nested active"></tags:menu>
                        </li>
                    </ul>
                </div>
            </div>
            <div id="sidenavButton" class="sidenav-button"><i id="sidenavButtonI" class="fa fa-angle-left"></i>
            </div>

            <div class="page">
                <div id="main" class="main d-none">
                    <div class="fieldset">
                        <div class="legend">
                            <div>
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
                                                行政支援系統</a></li>

                                        <%
                                            String pageName = FilenameUtils.getBaseName(StringUtils.upperCase(StringUtils.defaultString(application.getRealPath(request.getServletPath()))));
                                            pageName = StringUtils.defaultIfBlank((String) jspContext.getAttribute("pgcode"), pageName);
                                        %>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                        <div class="timeword">
                            網頁下載時間：<func:nowDateTime/>
                        </div>
                    </div>
                    <c:if test="${not empty buttons}">
                        <div class="formButtonDiv">
                            <jsp:invoke fragment="buttons"/>
                        </div>
                    </c:if>
                    <jsp:invoke fragment="contents"/>
                </div>

                <div id="msgarea" class="infoarea TframeDiv" style="width:100%;display:flex">
                    <div id="footer" class="footer">
                        <div class="ifon infotitle"><i class="fas fa-caret-down"></i><b>【訊息區】</b></div>
                        <div id="sysyemMessage" class="prog infoword"><sys:systemMessage/></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%@ include file="/WEB-INF/jsp/includes/js.jsp" %>

<script type="text/javascript">
    let entryPath;
    $(function () {
        // menu open close function
        $("#tree").on("click", ".caret", function () {
            if ($(this).hasClass('caretRoot') && !$(this).hasClass('caret-down')) {
                $('.caret')
                    .addClass("caret-down")
                    .siblings(".nested")
                    .addClass("active");
            } else {
                $(this)
                    .toggleClass("caret-down")
                    .siblings(".nested")
                    .toggleClass("active");
            }
        });

        entryPath = '<c:url value="${requestScope['javax.servlet.forward.servlet_path']}" />';
        if (entryPath.indexOf(".action") > -1) {
            let funcPath = entryPath.substring(entryPath.lastIndexOf("/"));
            if (funcPath.indexOf("_") > -1) {
                entryPath = entryPath.substring(0, entryPath.lastIndexOf("_") + 1);
            }
            const menus = $("#menu a[href^='" + entryPath + "']").parents('li');
            $(menus).children(".caret:not(.caret-down)").click();
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
        $('#main').removeClass('d-none');
        $('#sidenav').removeClass('d-none');
        inputFieldFocus();
        showAlert('<sys:systemAlertMessage />');
        const validationResult = '<sys:validationMessage />'.split('|@|@|');
        showAlert(validationResult[1], validationResult[0]);
        showConfirms('<sys:confirmMessage />');

        let $sidenav = $("#sidenav");
        $sidenav.addClass("animeTransition");
        $("#sidenavButton").click(function () {
            $sidenav.toggleClass("activeBar");

            let data = {};
            data.closed = $sidenav.hasClass("activeBar") ? "N" : "Y";

            let $sidenavButtonI = $('#sidenavButtonI');
            if (data.closed === "Y") {
                $sidenavButtonI.addClass("fa-angle-left").removeClass("fa-angle-right");
            } else {
                $sidenavButtonI.addClass("fa-angle-right").removeClass("fa-angle-left");
            }

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '<c:url value="/Common_saveSetting.action"/>',
                data: JSON.stringify(data),
                dataType: 'json',
                timeout: 100000,
                success: function (data) {
                }
            });

        });

        $(".infotitle").click(function () {
            $("#msgarea").toggleClass("newheight");
        });
        if ($('#footer .prog.infoword').text().trim().length > 0) {
            $(".infotitle").click();
        }
        $('input:checkbox:enabled[data-checkall]').on('click', checkall);


        reloadEip0aw010Case();
        reloadEip0aw011Case();
    });

	 function reloadEip0aw010Case() {
		reloadCase($('#eip0aw010Case'),'<c:url value="/Common_getEip0aw010Case.action"/>');
	}
	
	function reloadEip0aw011Case() {
		reloadCase($('#eip0aw011Case'),'<c:url value="/Common_getEip0aw011Case.action"/>');
	}

	function reloadCase(obj, url) {
		let interval = '<c:out value="${eip0aw010Case.interval}"/>';
		$.ajax({
			url: url,
			type: "POST",
			dataType: "json",
			cache: false,
			data: {'p':new Date().getTime()},
			contentType: "application/json",
			success: function (data) {
				$(obj).find('.title_02').text(data.apiResult.cnt);
			},
			error: function (xhr, ajaxOptions, thrownError) {
				console.log(xhr.status + " " + thrownError);
			},
			complete: function (xhr, sts) {
				if (!$.isNumeric(interval) || interval < 10) {
					interval = 10;
				}
				setTimeout(() => reloadCase(obj, url), interval * 1000);
			}
		});
	}
   
    getPendingBpm()
    function getPendingBpm() {
        // $('#bpm_pending').text(10);
        $.ajax({
            url: '<c:url value="/Common_getPendingBpm.action"/>',
            type: "GET",
            dataType: "json",
            cache: false,
            data: {'p': new Date().getTime()},
            contentType: "application/json",
            success: function (data) {
                $('#bpm_pending').text(data);
                console.log(data);
                console.log('成功拉');

            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log('出事拉');
                console.log(xhr.status + " " + thrownError);
            }
        });
    }

</script>
<jsp:invoke fragment="footers"/>
</body>
</html>