<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.msg.controllers.Eip01w040Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="heads">
        <style>
            .col-form-label {
                min-width: 100px;
            }

            #btnQuery {
                height: 90%;
                margin-top: 2.5px;
                padding-top: 6px;
                padding-bottom: 6px;
                padding-left: 8px;
                padding-right: 8px;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <tags:fieldset legend="查詢條件">
            <form:form id="eip01w040Form" modelAttribute="${caseKey}">
                <div id="dynaTree">
                    <ul>
                        <c:forEach var="items" items="${items}">
                            <c:out value="${items}" escapeXml="false" />
                        </c:forEach>
                    </ul>
                </div>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="keyword">關鍵字搜尋：</form:label>
                    <div class="col-12 col-md d-flex">
                        <form:input path="keyword" cssClass="form-control" size="40" maxlength="800" />
                        <tags:button cssClass="ml-2" id="btnQuery">搜尋<i class="fas fa-search"></i></tags:button>
                    </div>
                </tags:form-row>

                <div class="table-responsive mt-4">
                    <table class="table" id="qryListTable">
                        <thead>
                            <tr>
                                <th class="text-center">序號</th>
                                <th class="text-center">主題</th>
                                <th class="text-center">更新日期</th>
                                <th data-orderable="false" class="text-center">操作區</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${not empty caseData.qryList }">
                                <c:forEach items="${caseData.qryList}" var="item" varStatus="status">
                                    <tr data-seq="${status.index + 1 }">
                                        <td class="text-center">
                                            <c:out value="${status.index + 1 }" />
                                        </td>
                                        <td class="text-left">
                                            <c:out value="${item.subject}" />
                                            <form:hidden path="qryList[${status.index}].subject" />
                                        </td>
                                        <td class="text-center">
                                            <c:out value="${item.upddt}" />
                                            <form:hidden path="qryList[${status.index}].upddt" />
                                        </td>
                                        <td class="text-center" data-fseq="${item.fseq}">
                                            <tags:button id="btnDetail">明細<i class="fas fa-list-alt"></i></tags:button>
                                            <form:hidden path="qryList[${status.index}].fseq" />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                    </table>
                </div>
                <form:hidden path="fseq" />
                <form:hidden path="seq" />
                <form:hidden path="key" />
                <form:hidden path="subject" />
            </form:form>
        </tags:fieldset>

        <div id="popModal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">下載專區</h5>
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
            $(function() {
                let config = getDataTablesConfig();
                //config.searching = true;
                //config.dom = "<'pagination'pli>tf"; // or <input type="search" id="keyword">
                //config.orderable = true;
                //config.lengthChange = true;
                //config.pageLength = 5; // default 5
                var table = $("#qryListTable").DataTable(config);
                $("#dynaTree").dynatree({
                    selectMode: 1, //1:single, 2:multi, 3:multi-hier
                    minExpandLevel: 1,
                    persist: false,
                    autoCollapse: false,
                    activeVisible: true,
                    cookie: false,
                    onActivate: function(node) {
                        // 路徑查詢
                        $('#key').val(node.data.key);
                        $('#eip01w040Form').attr('action',
                                '<c:url value="/Eip01w040_path.action" />')
                            .submit();
                    }
                });
                // 關鍵字查詢
                $('input#keyword').on('keyup', function(e) {
                    if (e.key === 'Enter' || e.keyCode === 13) {
                        $('#eip01w040Form').attr('action',
                                '<c:url value="/Eip01w040_keyword.action" />')
                            .submit();
                    }
                    //table
                    //.columns(1)
                    //.search(this.value)
                    //.draw();
                });
                $('#btnQuery').click(function() {
                    $('#eip01w040Form').attr('action', '<c:url value="/Eip01w040_keyword.action" />')
                        .submit();
                });
                // 明細
                $('#qryListTable #btnDetail').click(function() {
                    $('#fseq').val($(this).parent('td').data('fseq'));
                    $.ajax({
                        type: "POST",
                        url: '<c:url value="/Eip01w040_detail.action" />',
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
                                } else {
                                    str +=
                                        '<div style="display: flex;">' +
                                        '<div style="flex: none;">附加檔案：</div><div>';
                                    $.each(data.file, function(key, value) {
                                        str +=
                                            '<div class="d-inline-flex mr-3">' +
                                            '<input type="checkbox" id="' +
                                            key +
                                            '" name="filelist"><a href="javascript:;" class="alink" id=' +
                                            key + '>' + value + '</a></div>';
                                    });
                                    str +=
                                        '</div></div>' +
                                        '<button type="button" class="btn btn-outline-be btn-sm mr-1" ' +
                                        'style="margin-left: 80px;" id="zipDownload">下載</button>';
                                }
                                $('#subject').val(data.subject);
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
            });
            // 檔案下載按鈕
            $(document).on('click', '#zipDownload', function(e) {
                var checkedList = $('input:checkbox[name="filelist"]:checked');
                if (checkedList.length) {
                    var idArray = [];
                    checkedList.each(function() {
                        idArray.push(this.id);
                    });
                    $('#seq').val(idArray.join(','));
                    $('#eip01w040Form').attr('action', '<c:url value="/Eip01w040_getFile.action" />')
                        .submit();
                }
            });
            // 檔案下載連結
            $(document).on('click', '.alink', function(e) {
                $('#seq').val($(this).attr('id'));
                $('#eip01w040Form').attr('action', '<c:url value="/Eip01w040_getFile.action" />')
                    .submit();
            });
        </script>
    </jsp:attribute>
</tags:layout>