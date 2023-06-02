<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.SettingController).SETTING"/>
<c:set var="caseData" value="${requestScope[caseKey]}"/>
<tags:layout pgcode="INDEX">
    <jsp:attribute name="heads">
    <link rel="stylesheet" href="<c:url value='/css/18698.css' />"/>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <c:choose>
            <c:when test="${empty sessionScope[caseKey].entryListOrder}">
                <c:set var="listOrder" value="drag1,drag2,drag3,drag4"/>
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
                <div class="pc">
                    <div class="row flex-row-reverse">
                        <div class="col-md-3">
                            <div class="top_4">
                                <a href="javascript:confirm('將開啟公文系統視窗')" style="text-decoration:none;">
                                    <div class="d-inline-block align-middle text-center">
                                        <div class="title_01">XXX數量</div>
                                        <span class="title_02">1</span>
                                        <span class="title_03">件</span>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="top_4">
                                <a href="javascript:confirm('將開啟差勤系統視窗')" style="text-decoration:none;">
                                    <div class="d-inline-block align-middle text-center">
                                        <div class="title_01">XXX數量</div>
                                        <span class="title_02">1</span>
                                        <span class="title_03">件</span>
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
                        <section class="container pt-2">
                            <div class="card">
                                <div class="card-body">
                                    <div id="collapse-5" class="px-1 py-1 collapse show">
                                        <div class="card test-table table-sm table-hover">
                                            <div class="card-body m-0 p-0">

                                                <div class="table-responsive">
                                                    <table class="table" id="listTable">
                                                        <thead data-orderable="true">
                                                            <tr>
                                                                <th class="text-center">序號</th>
                                                                <th class="text-center">公告日期</th>
                                                                <th class="text-center">類別</th>
                                                                <th class="text-center">內文</th>
                                                                <th class="text-center">公告單位</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
					    	<c:forEach items="${announce}" var="data" varStatus="status">
						        <tr>
                                    <td class="text-left"><c:out value="${data.sno}"/></td>
                                    <td class="text-left"><func:minguo value="${data.date}"/></td>
                                    <td class="text-left"><c:out value="${data.kind}"/></td>
                                    <td class="text-left"><a href="<c:out value="data.url" />"><c:out
                                            value="${data.context}"/></a></td>
                                    <td class="text-left"><c:out value="${data.dept}"/></td>
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
                config.searching=true;
                config.dom = "<'pagination'pli>tf";
                config.lengthChange=true;
                config.lengthMenu=[[5,25,50,-1],[5,25,50,'全部']];
                let table = $('#listTable').DataTable(config);
            });
        </script>
    </jsp:attribute>
</tags:layout>