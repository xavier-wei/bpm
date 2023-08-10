<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.framework.controllers.LoginController).CASE_KEY"/>
<c:set var="caseData" value="${requestScope[caseKey]}"/>
<tags:layout pgcode="HOME">
    <jsp:attribute name="heads">
    <link rel="stylesheet" href="<c:url value='/css/18698.css' />"/>
        <style>
            table.dataTable.no-footer {
                border-bottom: 1px solid #111;
                margin: 0;
            }

            .dataTables_wrapper label {
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
                <div  class="box" draggable="true">
                    <div id="tableauContainer" class="row"></div>
                </div>

                <!-- <div class="box" draggable="true">
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
                     
                </div> -->
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
                                                                <th class="text-center">路徑</th>
                                                                <th class="text-center">操作區</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

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
        </div>
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
        
        <form:form id="indexForm" modelAttribute="${caseKey}" method="POST">
            <form:hidden path="fseq"/>
            <form:hidden path="seq"/>
            <form:hidden path="key"/>
            <form:hidden path="subject"/>
        </form:form>
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
                        url: "<c:url value='/Common_saveSetting.action'/>",
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
                let msgListTableConfig = getDataTablesConfig();
                msgListTableConfig.searching = true;
                msgListTableConfig.dom = "<'d-flex text-nowrap'f<'d-flex ml-auto mr-0 mt-auto pagination text-wrap'pli>>tr";
                msgListTableConfig.lengthChange = true;
                msgListTableConfig.lengthMenu = [[5, 25, 50, -1], [5, 25, 50, '全部']];
                msgListTableConfig.sNoFooter = '';
                msgListTableConfig.deferRender = true;
                msgListTableConfig.fnServerParams = (data) => {
                    data['order'].forEach(function (items, index) {
                        data['order'][index]['column'] = data['columns'][items.column]['data'];
                    });
                };
                msgListTableConfig.processing = true;
                msgListTableConfig.ajax = {
                    url: '<c:url value="/Common_getMsgdata.action"/>',
                    type: 'POST',
                    contentType: 'application/json',
                    data: function (d) {
                        return JSON.stringify(d);
                    }
                };
                msgListTableConfig.columns = [
                    {data: '', render: (d, t, r, m) => m.row + m.settings._iDisplayStart + 1},
                    {data: 'subject'},
                    {data: 'msgtype'},
                    {data: 'releasedt'},
                    {data: 'contactunit'},
                    {
                        data: 'fseq', createdCell: (t, d) => {
                            $(t).data('fseq', d);
                        }, render: (d, t, r, m) => {
                            return '<button type=\"button\" class=\"btn btn-outline-be btnDetailMsg \"> <span class=\"btn-txt\">明細<i class=\"fas fa-list-alt\"><\/i><\/span> <\/button>';
                        }, orderable: false
                    }
                ];
                msgListTableConfig.serverSide = true;

                let downloadListTableConfig = $.extend(true, {}, msgListTableConfig);
                downloadListTableConfig.ajax.url = '<c:url value="/Common_getDownloaddata.action"/>';
                downloadListTableConfig.columns = [
                    {data: '', render: (d, t, r, m) => m.row + m.settings._iDisplayStart + 1},
                    {data: 'subject'},
                    {data: 'upddt'},
                    {data: 'contactunit'},
                    {
                        data: 'fseq', createdCell: (t, d) => {
                            $(t).data('fseq', d);
                        }, render: (d, t, r, m) => {
                            return '<button type=\"button\" class=\"btn btn-outline-be btnDetailMsg \"> <span class=\"btn-txt\">明細<i class=\"fas fa-list-alt\"><\/i><\/span> <\/button>';
                        }, orderable: false
                    }
                ];

                $('#msgListTable').DataTable(msgListTableConfig);
                $('#downloadListTable').DataTable(downloadListTableConfig);

                //公告-明細
                $(document).on('click', '.btnDetailMsg', function() {
                    $('#fseq').val($(this).parent('td').data('fseq'));
                    $.ajax({
                        type: "POST",
                        url: '<c:url value="/Common_Eip01w030GetDetail.action" />',
                        data: {
                            'fseq': $(this).parent('td').data('fseq')
                        },
                        timeout: 100000,
                        success: function(data) {
                            if (data == '') {
                                showAlert('查無資料!');
                            } else {
                                var str = '';
                                var count = Object.keys(data.file).length;
                                if (count == 1) {
                                    var key = Object.keys(data.file);
                                    str +=
                                        '附加檔案：<a href="javascript:;" class="alink" id=' +
                                        key + '>' +
                                        data.file[key] + '</a>';
                                } else if (count == 0) {
                                    str += '附加檔案：';
                                } else {
                                    str +=
                                        '<div style="display: flex;">' +
                                        '<div style="flex: none;">附加檔案：</div><div>';
                                    $.each(data.file, function(key, value) {
                                        str +=
                                            '<div class="d-inline-flex mr-3">' +
                                            '<input type="checkbox" id="' +
                                            key +
                                            '" name="filelist" checked><a href="javascript:;" class="alink" id=' +
                                            key + '>' + value + '</a></div>';
                                    });
                                    str +=
                                        '</div></div>' +
                                        '<button type="button" class="btn btn-outline-be btn-sm mr-1" ' +
                                        'style="margin-left: 80px;" id="zipDownload">下載</button>';
                                }
                                $('#subject').val(data.subject);
                                $('.modal-title').html('公告事項－' + data.msgtype);
                                $('.modal-body').html(
                                    '主　　題：' + data.subject +
                                    '<br>訊息文字：' + data.mcontent +
                                    '<br>發布單位：' + data.contactunit +
                                    '<br>' + str +
                                    '<br>更新日期：' + data.upddt +
                                    '<br>聯絡人　：' + data.contactperson +
                                    '<br>聯絡電話：' + data.contacttel);
                                $('#popModal').modal('show');
                            }
                        },
                        error: function(e) {
                            showAlert("取得資料發生錯誤");
                        }
                    });
                });


                // 檔案下載按鈕
                $(document).on('click', '#zipDownload', function (e) {
                    var checkedList = $('input:checkbox[name="filelist"]:checked');
                    if (checkedList.length) {
                        var idArray = [];
                        checkedList.each(function () {
                            idArray.push(this.id);
                        });
                        $('#seq').val(idArray.join(','));
                        $('#indexForm').attr('action', '<c:url value="/Common_eip01w030GetFile.action" />')
                            .submit();
                    } else {
                        showAlert("必須勾選檔案");
                    }
                });

                // 檔案下載連結
                $(document).on('click', '.alink', function (e) {
                    $('#seq').val($(this).attr('id'));
                    $('#indexForm').attr('action', '<c:url value="/Common_eip01w030GetFile.action" />')
                        .submit();
                });

            });



            $(function() {
                getUserData();
                getTicket()
            });


            // 假設後端資料
            let backendResponse = [{
                    dashboardFigId: 1,
                    imageBase64String: "././images/tableau/BID_01_01_20230717.png",
                    tableauUrl: "http://223.200.84.115/#/views/__2023071701/_?:iid=4"
                },
                {
                    dashboardFigId: 2,
                    imageBase64String: "././images/tableau/BID_02_01_20230717.png",
                    tableauUrl: "http://223.200.84.115/#/views/__2023071701_16895610210960/_?:iid=1"
                },
            ];


            function openTableau(type) {
                // 顯示確認視窗
                const isConfirmed = confirm('將開啟Tableau視窗，確認繼續嗎？');
                if (isConfirmed) {
                    //美新開一個url都要再拿一次ticket，不然ticket會失效
                    getTicket();
                    const dashboardFigId = type; 
                    const foundImage = backendResponse.find(item => item.dashboardFigId === dashboardFigId);
                    if (foundImage) {
                        foundImage.tableauUrl = foundImage.tableauUrl.replace("#","/trusted/"+ticket)
                        console.log(foundImage.tableauUrl)
                        window.open(foundImage.tableauUrl, "_blank");                       
                    } else {
                        alert('找不到對應的儀錶板網址');
                    }
                }
            }


            //取得userId
            function getUserData() {
                $.ajax({
                    url: '<c:url value="/get-tableau-data" />',
                    type: 'POST',
                    async: true,
                    timeout: 100000,
                    success: function(response) {
                        console.log(response);
                        if (response) {
                            backendResponse = response
                            //動態生成tableau div
                            const tableauContainer = document.getElementById("tableauContainer");
                            backendResponse.forEach((imageData) => {
                                console.log("imageData",imageData)
                                const tableauElement = createTableauElement(imageData);
                                tableauContainer.appendChild(tableauElement);
                            });
                        }
                    },
                    error: function(error) {
                        console.error(error);
                    }
                });
            }



            // 動態產生tableau div
            function createTableauElement(imageData) {
                const tableauDiv = document.createElement("div");
                tableauDiv.classList.add("col-md-4", "tableau_btn");
                tableauDiv.id = "tableau_btn_" + imageData.dashboardFigId;

                const topDiv = document.createElement("div");
                topDiv.classList.add("top", "pic-scale-up");

                const link = document.createElement("a");
                link.href = "#";
                link.onclick = function() {
                    openTableau(imageData.dashboardFigId);
                };

                const imgDiv = document.createElement("div");
                imgDiv.classList.add("d-inline-block", "align-middle", "text-center");
                const img = document.createElement("img");
                const base64String = imageData.imageBase64String;
                img.src = "data:image/png;base64," + base64String;
                img.style.borderRadius = "10px";
                img.style.maxWidth = "100%";
                img.style.maxHeight = "100%";
                img.alt = "儀錶板";


                imgDiv.appendChild(img);
                link.appendChild(imgDiv);
                topDiv.appendChild(link);
                tableauDiv.appendChild(topDiv);
                return tableauDiv;
            }


            let ticket;
            //獲取tableau授權碼，不用再二次登入
            function getTicket() {
             $.ajax({
                    url: '<c:url value="/get-ticket" />',
                    type: 'POST',
                    async: true,
                    timeout: 100000,
                    success: function(response) {
                        if (response) {
                            ticket = response.ticket;  
                            console.log("ticket",ticket);
                        }
                    },
                    error: function(error) {
                        console.error(error);
                    }
                });
            }

          function arrayBufferToBase64(buffer) {
            console.log("buffer", buffer); // 檢查 buffer 是否有值
            const bytes = new Uint8Array(buffer);
            console.log("bytes", bytes); // 檢查 bytes 是否有值
            let binary = '';
            for (let i = 0; i < bytes.length; i++) {
                binary += String.fromCharCode(bytes[i]);
            }
            return window.btoa(binary);
        }

        </script>
    </jsp:attribute>
</tags:layout>