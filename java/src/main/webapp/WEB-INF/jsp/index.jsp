<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.SettingController).SETTING"/>
<c:set var="caseData" value="${requestScope[caseKey]}"/>
<tags:layout pgcode="INDEX">
    <jsp:attribute name="heads">
    <link rel="stylesheet" href="<c:url value='/css/18698.css' />"/>
    </jsp:attribute>
    <jsp:attribute name="contents">
                        <c:set var="listOrder" value="${sessionScope[caseKey].entryListOrder}"/>
        <c:choose>
            <c:when test="${empty sessionScope[caseKey].entryListOrder}">
                <c:set var="listOrder" value="drag2,drag3,drag4"/>
            </c:when>
            <c:otherwise>
                <c:set var="listOrder" value="${sessionScope[caseKey].entryListOrder}"/>
            </c:otherwise>
        </c:choose>
        <div class="container2">
        <c:forEach var="item" items="${fn:split(listOrder,',')}">
            <c:choose>
                <c:when test="${item eq 'drag2'}">
            <div class="box" draggable="true">
                <section id="drag2" class="dragtag"></section>
                <nav class="nav pt-4 navbar-expand">
                    <div id="nav-tab4" role="tablist" class="nav nav-tabs container-fluid">
                        <button id="nav-infrom-tab4" data-toggle="tab" data-target="#nav-infrom" role="tab"
                                aria-controls="nav-infrom" aria-selected="true" type="button"
                                class="btn nav-link btn-secondary active w-100">
                            個人儀表板
                        </button>
                    </div>
                </nav>
                <!-- 這裡預計要放其他團隊的IFRAME內容 -->
            </div>                        
                </c:when>
                <c:when test="${item eq 'drag3'}">
            <div class="box" draggable="true">
                <section id="drag3" class="dragtag"></section>
                <nav class="nav pt-4 navbar-expand">
                    <div id="nav-tab3" role="tablist" class="nav nav-tabs container-fluid">
                        <button id="nav-infrom-tab3" data-toggle="tab" data-target="#nav-infrom" role="tab"
                                aria-controls="nav-infrom" aria-selected="true" type="button"
                                class="btn nav-link btn-secondary active">
                            公告事項
                        </button>
                        <button id="nav-download-tab" data-toggle="tab" data-target="#nav-download" role="tab"
                                aria-controls="nav-download" aria-selected="false" type="button"
                                class="btn nav-link btn-secondary">下載專區
                        </button>
                    </div>
                </nav>
                <div id="nav-tabContent" class="tab-content">
                    <div id="nav-infrom" role="tabpanel" aria-labelledby="nav-infrom-tab"
                         class="tab-pane fade active show">
                        <section class="container pt-2 pl-0 pr-0">
                            <div class="card">
                                <div class="card-body p-0">
                                    <div id="collapse-5" class="px-1 py-1 collapse show">
                                        <div class="card test-table table-sm table-hover">
                                            <div class="card-body m-0 p-0">

                                                <div class="table-responsive">
                                                    <table class="table" id="listTable">
                                                        <thead data-orderable="true">
                                                            <tr>
                                                                <th class="text-center">序號</th>
                                                                <th class="text-center">主題</th>
                                                                <th class="text-center">類別</th>
                                                                <th class="text-center">發布時間</th>
                                                                <th class="text-center">發布單位</th>
                                                                <th class="text-center">操作區</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
					    	<c:forEach items="${msgdata}" var="item" varStatus="status">
						        <tr>
                                    <td class="text-left"><c:out value="${status.index + 1}"/></td>
                                    <td class="text-left"><c:out value="${item.subject}"/></td>
                                    <td class="text-left"><c:out value="${item.msgtype}"/></td>
                                    <td class="text-left"><func:minguo value="${item.releasedt}"/></td>
                                    <td class="text-left"><c:out value="${item.contactunit}"/></td>
                                    <td class="text-center" data-fseq="${item.fseq}">
                                        <tags:button id="btnDetail">明細<i class="fas fa-list-alt"></i></tags:button>
                                    </td>
                                </tr>
					        </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                    <div id="nav-download" role="tabpanel" aria-labelledby="nav-download-tab" class="tab-pane fade">

                    </div>
                </div>
            </div>                        
                </c:when>
                <c:when test="${item eq 'drag4'}">
            <div class="box" draggable="true">
                <section id="drag4" class="dragtag"></section>
                <nav class="nav pt-4 navbar-expand">
                    <div id="nav-tab2" role="tablist" class="nav nav-tabs container-fluid">
                        <button id="nav-infrom-tab2" data-toggle="tab" data-target="#nav-infrom" role="tab"
                                aria-controls="nav-infrom" aria-selected="true" type="button"
                                class="btn nav-link btn-secondary active w-100">
                            常用系統及網站
                        </button>
                    </div>
                </nav>
                <div class="in">
                    <div class="ct">
                        <div class="in">
                            <div class="group base-wrapper" data-index="3" data-type="3" data-child="3">

                            </div>
                        </div>
                    </div>
                </div>
            </div>                    
                </c:when>
                <c:otherwise>
                    
                </c:otherwise>
            </c:choose>
        </c:forEach>
                <div id="popModal" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">公告事項</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
    </jsp:attribute>
    <jsp:attribute name="footers">
        <script type="text/javascript">

            document.addEventListener('DOMContentLoaded', (event) => {

                var dragSrcEl = null;

                function handleDragStart(e) {
                    this.style.opacity = '0.4';

                    dragSrcEl = this;

                    e.dataTransfer.effectAllowed = 'move';
                    e.dataTransfer.setData('text/html', this.innerHTML);
                }

                function handleDragOver(e) {
                    if (e.preventDefault) {
                        e.preventDefault();
                    }

                    e.dataTransfer.dropEffect = 'move';

                    return false;
                }

                function handleDragEnter(e) {
                    this.classList.add('over');
                }

                function handleDragLeave(e) {
                    if (e.currentTarget.contains(e.relatedTarget)) {
                        return;
                    }
                    this.classList.remove('over');
                }

                function handleDrop(e) {
                    if (e.stopPropagation) {
                        e.stopPropagation(); // stops the browser from redirecting.
                    }

                    if (dragSrcEl != this) {
                        dragSrcEl.innerHTML = this.innerHTML;
                        this.innerHTML = e.dataTransfer.getData('text/html');
                    }

                    return false;
                }

                function handleDragEnd(e) {
                    this.style.opacity = '1';

                    items.forEach(function (item) {
                        item.classList.remove('over');
                    });

                    let data = {};
                    data.entryListOrder = $('.dragtag').map(function () {
                        return this.id;
                    }).get().join(',');

                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: 'Common_saveSetting.action',
                        data: JSON.stringify(data),
                        dataType: 'json',
                        timeout: 100000,
                        success: function (data) {
                        }
                    });
                }


                let items = document.querySelectorAll('.container2 .box');
                items.forEach(function (item) {
                    item.addEventListener('dragstart', handleDragStart, false);
                    item.addEventListener('dragenter', handleDragEnter, false);
                    item.addEventListener('dragover', handleDragOver, false);
                    item.addEventListener('dragleave', handleDragLeave, false);
                    item.addEventListener('drop', handleDrop, false);
                    item.addEventListener('dragend', handleDragEnd, false);
                });
            })
            $(function () {
                let config = getDataTablesConfig();
                config.searching = true;
                config.dom = "<'pagination'pli>tf";
                config.lengthChange = true;
                config.lengthMenu = [[5, 25, 50, -1], [5, 25, 50, '全部']];
                let table = $('#listTable').DataTable(config);


                $('#btnDetail').click(function () {
                    $.ajax({
                        type: "POST",
                        url: '<c:url value="/Eip01w030_getDetail.action" />',
                        data: {
                            'fseq': $(this).parent('td').data('fseq')
                        },
                        timeout: 100000,
                        success: function (data) {
                            if (data === '') {
                                showAlert('查無資料!');
                            } else {
                                let str = '';
                                $.each(data.file, function (i, e) {
                                    str +=
                                        '<a href="javascript:;" class="alink" id=' +
                                        i + '>' +
                                        e + '</a>' + '　';
                                });
                                $('.modal-body').html('公告事項：' + data.msgtype +
                                    '<br>發佈單位：' + data.contactunit +
                                    '<br>主　　題：' + data.subject +
                                    '<br>　　　　　' + data.mcontent +
                                    '<br>附加檔案：' + str +
                                    '<br>更新日期：' + data.upddt +
                                    '<br>聯絡人　：' + data.contactperson +
                                    '<br>聯絡電話：' + data.contacttel);
                                $('#popModal').modal('show');
                            }
                        },
                        error: function (e) {
                            showAlert("取得資料發生錯誤");
                        }
                    });
                });
            });
        </script>
    </jsp:attribute>
</tags:layout>