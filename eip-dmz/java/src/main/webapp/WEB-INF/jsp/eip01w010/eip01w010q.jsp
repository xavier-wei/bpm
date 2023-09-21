<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.msg.controllers.Eip01w010Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="buttons">
        <c:choose>
            <c:when test="${(caseData.mode == 'Q' || caseData.mode == 'D' ) && not empty caseData.queryList }">
                <tags:button id="btnUpload">
                    上稿<i class="fas fa-paper-plane"></i>
                </tags:button>
                <tags:button id="btnDelete">
                    刪除<i class="fas fa-trash-alt"></i>
                </tags:button>
                <tags:button id="btnBack">
                    返回<i class="fas fa-reply"></i>
                </tags:button>
            </c:when>
            <c:otherwise>
                <tags:button id="btnInsert">
                    新增<i class="fas fa-user-plus"></i>
                </tags:button>
                <tags:button id="btnQuery">
                    查詢<i class="fas fa-search"></i>
                </tags:button>
                <tags:button id="btnClear">
                    清除<i class="fas fa-eraser"></i>
                </tags:button>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <form:form id="eip01w010Form" modelAttribute="${caseKey}">
            <tags:fieldset legend="查詢條件">
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="p1id">建立人員：</form:label>
                    <div class="col-12 col-md">
                        <form:input path="p1id" cssClass="form-control num_eng_only" size="10" maxlength="10" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="p1page">頁面型態：</form:label>
                    <div class="col-12 col-md">
                        <form:select path="p1page" cssClass="form-control">
                            <option value="" selected disabled hidden>請選擇</option>
                            <c:forEach var="item" items="${caseData.pagetypes}" varStatus="status">
                                <form:option value="${item.codeno}">
                                    <c:out value="${item.codename}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="p1title">主旨：</form:label>
                    <div class="col-12 col-md">
                        <form:input path="p1title" cssClass="form-control" size="40" maxlength="800" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="status">狀態：</form:label>
                    <div class="col-12 col-md">
                        <form:select path="status" cssClass="form-control">
                            <c:forEach var="item" items="${caseData.statuses1}" varStatus="status">
                                <form:option value="${item.codeno}">
                                    <c:out value="${item.codename}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
                <form:hidden path="mode" />
                <form:hidden path="fseq" />
                <form:hidden path="keep" />
            </tags:fieldset>
            <c:if test="${(caseData.mode == 'Q' || caseData.mode == 'D' ) && not empty caseData.queryList }">
                <tags:fieldset legend="查詢結果">
                    <div class="table-responsive mt-4">
                        <table class="table" id="querylistTable">
                            <thead>
                                <tr>
                                    <th data-orderable="false">
                                        <form:checkbox path="checkAll" />全選
                                    </th>
                                    <th class="text-center">項次</th>
                                    <th class="text-center">頁面型態</th>
                                    <th class="text-center">主旨/連結網址</th>
                                    <th class="text-center">前台是否顯示</th>
                                    <th class="text-center">上架時間<br>下架時間</th>
                                    <th class="text-center">狀態</th>
                                    <th class="text-center">明細</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${caseData.queryList}" var="item" varStatus="status">
                                    <tr data-seq="${status.index + 1 }">
                                        <td class="text-center">
                                            <c:if test="${item.status != '4' }">
                                                <form:checkbox path="queryList[${status.index}].check"
                                                    value="${item.check }" cssClass="checkedgreen" />
                                            </c:if>
                                        </td>
                                        <td class="text-center">
                                            <c:out value="${item.fseq}" />
                                            <form:hidden path="queryList[${status.index}].fseq" />
                                        </td>
                                        <td class="text-center">
                                            <c:out value="${item.pagetype}" />
                                            <form:hidden path="queryList[${status.index}].pagetype" />
                                        </td>
                                        <td class="text-left">
                                            <c:out value="${item.subject}" />
                                            <form:hidden path="queryList[${status.index}].subject" />
                                        </td>
                                        <td class="text-center">
                                            <c:out value="${item.isfront}" />
                                            <form:hidden path="queryList[${status.index}].isfront" />
                                        </td>
                                        <td class="text-center">
                                            <func:minguo value="${item.releasedt}" /><br>
                                            <func:minguo value="${item.offtime}" />
                                            <form:hidden path="queryList[${status.index}].releasedt" />
                                            <form:hidden path="queryList[${status.index}].offtime" />
                                        </td>
                                        <td class="text-center">
                                            <c:out value="${item.statusText}" />
                                            <form:hidden path="queryList[${status.index}].status" />
                                        </td>
                                        <td class="text-center" data-fseq="${item.fseq}">
                                            <tags:button id="btnDetail">明細<i class="fas fa-list-alt"></i></tags:button>
                                            <tags:button id="btnPreview">預覽<i class="fas fa-window-restore"></i>
                                            </tags:button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </tags:fieldset>
            </c:if>
        </form:form>
        <div id="popModal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title"></h5>
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
                // 新增
                $('#btnInsert').click(function() {
                    $('#mode').val('I');
                    $('#eip01w010Form').attr('action', '<c:url value="/Eip01w010_edit.action" />')
                        .submit();
                });
                // 查詢
                $('#btnQuery').click(function() {
                    $('#mode').val('Q');
                    $('#eip01w010Form').attr('action', '<c:url value="/Eip01w010_queryList.action" />')
                        .submit();
                });
                // 清除
                $('#btnClear').click(function() {
                    $('input[type="text"]').val('');
                    $('#mode').val('');
                });
                // 明細
                $('#querylistTable #btnDetail').click(function() {
                    $('#mode').val('D');
                    $('#fseq').val($(this).parent('td').data('fseq'));
                    $('#eip01w010Form').attr('action', '<c:url value="/Eip01w010_query.action" />')
                        .submit();
                });
                // 預覽
                $('#querylistTable #btnPreview').click(function() {
                    $('#fseq').val($(this).parent('td').data('fseq'));
                    $.ajax({
                        type: "POST",
                        url: '<c:url value="/Eip01w010_getDetail.action" />',
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
//                                     str += '<button type="button" class="btn btn-outline-info mr-3 mb-2" id='+i+'>'+e+'</button>';
                                });
                                $('.modal-title').html(data.attributype);
                                $('.modal-body').html(
                                    '主　　題：' + data.subject +
                                    '<br>訊息文字：' + data.mcontent +
                                    '<br>發布單位：' + data.contactunit +
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
                // 上稿
                $('#btnUpload').click(function() {
                    $('#eip01w010Form').attr('action', '<c:url value="/Eip01w010_btnup.action" />')
                        .submit();
                });
                // 刪除
                $('#btnDelete').click(function() {
                    if ($('input:checkbox').length > 1 && $('input:checkbox:checked').length > 0) { // 有顯示checkbox且至少勾選一個
                        showConfirm('確定要刪除資料？', () => {
                            $('#eip01w010Form').attr('action',
                                    '<c:url value="/Eip01w010_btndel.action" />')
                                .submit();
                        });
                    } else {
                        $('#eip01w010Form').attr('action', '<c:url value="/Eip01w010_btndel.action" />')
                            .submit();
                    }
                });
                // 返回
                $('#btnBack').click(function() {
                    $('#mode').val('');
                    $('#eip01w010Form').attr('action', '<c:url value="/Eip01w010_enter.action" />')
                        .submit();
                });
                // checkbox 控制
                var $checkAll = $('input[name="checkAll"]');
                var $checks = $('#querylistTable > tbody input:checkbox');
                $checkAll.change(function(e) {
                    $checks.prop("checked", $(this).prop("checked"));
                });
                $checks.change(function(e) {
                    $checkAll.prop("checked", !$checks.is(':not(:checked)'));
                });
            });
        </script>
    </jsp:attribute>
</tags:layout>