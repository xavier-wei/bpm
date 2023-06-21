<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.msg.controllers.Eip01w030Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="buttons">
        <tags:button id="btnQuery">
            查詢<i class="fas fa-search"></i>
        </tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <tags:fieldset legend="查詢條件">
            <form:form id="eip01w030Form" modelAttribute="${caseKey}">
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="msgtype">類別：</form:label>
                    <div class="col-12 col-md">
                        <form:select path="msgtype" cssClass="form-control">
                            <option value="" selected disabled hidden>請選擇</option>
                            <c:forEach var="item" items="${caseData.optList}" varStatus="status">
                                <form:option value="${item.codeno}">
                                    <c:out value="${item.codename}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="theme">主題：</form:label>
                    <div class="col-12 col-md">
                        <form:input path="theme" cssClass="form-control" size="40" maxlength="800" />
                    </div>
                </tags:form-row>
                <tags:form-note>
                    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
                </tags:form-note>
                <c:if test="${not empty caseData.qryList }">
                    <div class="table-responsive mt-4">
                        <table class="table" id="qryListTable">
                            <thead>
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
                                            <c:out value="${item.msgtype}" />
                                            <form:hidden path="qryList[${status.index}].msgtype" />
                                        </td>
                                        <td class="text-center">
                                            <func:minguo value="${item.releasedt}" /><br>
                                            <form:hidden path="qryList[${status.index}].releasedt" />
                                        </td>
                                        <td class="text-center">
                                            <c:out value="${item.contactunit}" />
                                            <form:hidden path="qryList[${status.index}].contactunit" />
                                        </td>
                                        <td class="text-center" data-fseq="${item.fseq}">
                                            <tags:button id="btnDetail">明細<i class="fas fa-list-alt"></i></tags:button>
                                            <form:hidden path="qryList[${status.index}].fseq" />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <form:hidden path="fseq" />
                <form:hidden path="seq" />
                <form:hidden path="filename" />
            </form:form>
        </tags:fieldset>

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
            $(function() {
                // 查詢
                $('#btnQuery').click(function() {
                    $('#eip01w030Form').attr('action', '<c:url value="/Eip01w030_query.action" />')
                        .submit();
                });
                // 明細
                $('#qryListTable #btnDetail').click(function() {
                    $('#fseq').val($(this).parent('td').data('fseq'));
                    $.ajax({
                        type: "POST",
                        url: '<c:url value="/Eip01w030_getDetail.action" />',
                        data: {
                            'fseq': $(this).parent('td').data('fseq')
                        },
                        timeout: 100000,
                        success: function(data) {
                            if (data == '') {
                                showAlert('查無資料!');
                            } else {
                                var str = '';
                                $.each(data.file, function(i, e) {
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
                        error: function(e) {
                            showAlert("取得資料發生錯誤");
                        }
                    });
                });
            });
            // 檔案下載連結
            $(document).on('click', '.alink', function(e) {
                $('#seq').val($(this).attr('id'));
                $('#filename').val($(this).html());
                $('#eip01w030Form').attr('action', '<c:url value="/Eip01w030_getFile.action" />')
                    .submit();
            });
        </script>
    </jsp:attribute>
</tags:layout>