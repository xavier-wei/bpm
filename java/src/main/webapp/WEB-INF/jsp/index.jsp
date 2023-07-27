<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.SettingController).SETTING"/>
<c:set var="caseData" value="${requestScope[caseKey]}"/>
<tags:layout pgcode="HOME">
    <jsp:attribute name="heads">
    <link rel="stylesheet" href="<c:url value='/css/18698.css' />"/>
        <style>
            table.dataTable.no-footer {
                border-bottom: 1px solid #111;
                margin: 0;
            }

            .dataTables_filter label {
                margin: 0;
            }

            .pic-scale-up {
                position: relative; /* 設定定位上下文，使 z-index 生效 */
                z-index: 1; /* 設定 z-index 值為 1，放在其他元素之上 */
            }

            .pic-scale-up:hover {
                transform: translate(100%, 50%) scale(3, 3);
                transition: transform 0.25s ease;
                z-index: 2; /* 懸停時，將 z-index 值提高到 2，使其放在最外層 */
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="contents">
                        <c:set var="listOrder" value="${sessionScope[caseKey].entryListOrder}"/>
        <c:choose>
            <c:when test="${empty sessionScope[caseKey].entryListOrder }">
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
                        <button id="nav-inform-tab4" data-toggle="tab" data-target="#nav-inform" role="tab"
                                aria-controls="nav-inform" aria-selected="true" type="button"
                                class="btn nav-link btn-secondary active w-100">
                            個人儀表板
                        </button>
                    </div>
                </nav>
                <div class="box" draggable="true">
                    <div class="row">
                        <div class="col-md-2 tableau_btn">
                             <div class="top pic-scale-up">
                                    <a href="#" onclick="openTableau(1)">
                                        <div class="d-inline-block align-middle text-center">
                                            <img src="././images/tableau/BID_01_01_20230717.png" style="border-radius: 10px; max-width:100%; max-height:100%;" alt="Responsive image" />
                                        </div>
                                    </a>
                            </div>
                        </div>
                          <div class="col-md-2 tableau_btn">
                            <div class="top pic-scale-up">
                                   <a href="#" onclick="openTableau(2)">
                                    <div class="d-inline-block align-middle text-center">
                                        <img src="././images/tableau/BID_02_01_20230717.png"  style="border-radius: 10px; max-width:100%; max-height:100%; " alt="Responsive image" />
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                     
                </div>
            </div>                        
                </c:when>
                <c:when test="${item eq 'drag3'}">
            <div class="box" draggable="true">
                <section id="drag3" class="dragtag"></section>
                <nav class="nav pt-4 navbar-expand">
                    <div id="nav-tab3" role="tablist" class="nav nav-tabs container-fluid">
                        <button id="nav-inform-tab3" data-toggle="tab" data-target="#nav-inform" role="tab"
                                aria-controls="nav-inform" aria-selected="true" type="button"
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
                    <div id="nav-inform" role="tabpanel" aria-labelledby="nav-inform-tab"
                         class="tab-pane fade active show">
                        <section class="container pt-2 pl-0 pr-0">
                            <div class="card">
                                <div class="card-body p-0">
                                    <div class="px-1 py-1 collapse show">
                                        <div class="card test-table table-sm table-hover">
                                            <div class="card-body m-0 p-0">

                                                <div class="table-responsive">
                                                    <table class="table" id="msgListTable">
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
					    	<c:forEach items="${msgdata}" var="msg" varStatus="status">
						        <tr>
                                    <td class="text-left"><c:out value="${status.index + 1}"/></td>
                                    <td class="text-left"><c:out value="${msg.subject}"/></td>
                                    <td class="text-left"><c:out value="${msg.msgtype}"/></td>
                                    <td class="text-left"><func:minguo value="${msg.releasedt}"/></td>
                                    <td class="text-left"><c:out value="${msg.contactunit}"/></td>
                                    <td class="text-center" data-fseq="${msg.fseq}">
                                        <tags:button
                                                cssClass="btnDetailMsg">明細<i class="fas fa-list-alt"></i></tags:button>
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
                        <section class="container pt-2 pl-0 pr-0">
                            <div class="card">
                                <div class="card-body p-0">
                                    <div class="px-1 py-1 collapse show">
                                        <div class="card test-table table-sm table-hover">
                                            <div class="card-body m-0 p-0">
                                                <div class="table-responsive">
                                                    <table class="table" id="downloadListTable">
                                                        <thead data-orderable="true">
                                                            <tr>
                                                                <th class="text-center">序號</th>
                                                                <th class="text-center">主題</th>
                                                                <th class="text-center">更新日期</th>
                                                                <th class="text-center">操作區</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
					    	<c:forEach items="${downloaddata}" var="download" varStatus="status">
						        <tr>
                                    <td class="text-left"><c:out value="${status.index + 1}"/></td>
                                    <td class="text-left"><c:out value="${download.subject}"/></td>
                                    <td class="text-left"><c:out value="${download.upddt}"/></td>
                                    <td class="text-center" data-fseq="${download.fseq}">
                                        <tags:button
                                                cssClass="btnDetailDownload">明細<i class="fas fa-list-alt"></i></tags:button>
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
                </div>
            </div>
                </c:when>
                <c:when test="${item eq 'drag4'}">
            <div class="box" draggable="true">
                <section id="drag4" class="dragtag"></section>
                <nav class="nav pt-4 navbar-expand">
                    <div id="nav-tab2" role="tablist" class="nav nav-tabs container-fluid">
                        <button id="nav-inform-tab2" data-toggle="tab" data-target="#nav-inform" role="tab"
                                aria-controls="nav-inform" aria-selected="true" type="button"
                                class="btn nav-link btn-secondary active w-100">
                            常用系統及網站
                        </button>
                    </div>
                </nav>
                <div class="row row-cols-2" data-index="3" data-type="3" data-child="3">
                    <c:forEach items="${sys_site}" var="item">
                    <div>
                        <a href="${item.codename}" target="_blank" title="開啟新視窗"><c:out
                                value="${item.scodekind}"/></a>
                    </div>
                    </c:forEach>
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
            $(function () {
                let dragSrcEl = null;

                function handleDragStart(e) {
                    $(this).css('opacity', '0.4');
                    dragSrcEl = this;
                    e.originalEvent.dataTransfer.effectAllowed = 'move';
                }

                function handleDragOver(e) {
                    if (e.preventDefault) {
                        e.preventDefault();
                    }

                    e.originalEvent.dataTransfer.dropEffect = 'move';

                    return false;
                }

                function handleDragEnter() {
                    $(this).addClass('over');
                }

                function handleDragLeave(e) {
                    if ($.contains(e.currentTarget, e.relatedTarget)) {
                        return;
                    }
                    $(this).removeClass('over');
                }

                function handleDrop(e) {
                    if (e.stopPropagation) {
                        e.stopPropagation();
                    }

                    if (dragSrcEl !== this) {
                        let thisCopy = $(this).children().detach();
                        let dragSrcElCopy = $(dragSrcEl).children().detach();

                        $(this).append(dragSrcElCopy);
                        $(dragSrcEl).append(thisCopy);
                    }

                    return false;
                }

                function handleDragEnd() {
                    $('.box').css('opacity', '1').removeClass('over');

                    let data = {};
                    data.entryListOrder = $('.dragtag').map(function () {
                        return this.id;
                    }).get().join(',');

                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "<c:url value='Common_saveSetting.action'/>",
                        data: JSON.stringify(data),
                        dataType: 'json',
                        timeout: 100000,
                        success: function (data) {
                        }
                    });
                }

                $('.box').on('dragstart', handleDragStart)
                    .on('dragenter', handleDragEnter)
                    .on('dragover', handleDragOver)
                    .on('dragleave', handleDragLeave)
                    .on('drop', handleDrop)
                    .on('dragend', handleDragEnd);
            });

            $(function () {
                let config = getDataTablesConfig();
                config.searching = true;
                config.dom = "<'pagination'pli>tf";
                config.lengthChange = true;
                config.lengthMenu = [[5, 25, 50, -1], [5, 25, 50, '全部']];
                config.sNoFooter = '';
                $('#msgListTable').DataTable(config);
                $('#downloadListTable').DataTable(config);


                $('.btnDetailMsg').click(function () {
                    $.ajax({
                        type: "POST",
                        url: '<c:url value="/Eip01w030_getDetail.action" />',
                        async: true,
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
                        error: function (xhr, ajaxOptions, thrownError) {
                            showAlert("網路發生錯誤");
                            console.log(xhr.status + " " + thrownError);
                        }
                    });
                });

                $('.btnDetailDownload').click(function () {
                    $.ajax({
                        type: "POST",
                        url: '<c:url value="/Eip01w040_detail.action" />',
                        async: true,
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
                        error: function (xhr, ajaxOptions, thrownError) {
                            showAlert("網路發生錯誤");
                            console.log(xhr.status + " " + thrownError);
                        }
                    });
                });
            });


            function openTableau(type) {
                    // 顯示確認視窗
                    const isConfirmed = confirm('將開啟Tableau視窗，確認繼續嗎？');
                    if (isConfirmed) {
                        if(type===1){
                        window.open("http://223.200.84.115/#/views/__2023071701/_?:iid=4", "_blank");

                        }
                        else if (type===2){
                        window.open("http://223.200.84.115/#/views/__2023071701_16895610210960/_?:iid=1", "_blank");
                     }
                    }
            }



        $(function() {
            getUserData();
        });


        //取得userId
        function getUserData() {
            $.ajax({
                url: '<c:url value="/getUserData" />',
                type: 'POST',
                async: true,
                timeout: 100000,
                success: function(response) {
                    console.log(response);
                },
                error: function(error) {
                    console.error(error);
                }
            });
        }



          


        </script>
    </jsp:attribute>
</tags:layout>