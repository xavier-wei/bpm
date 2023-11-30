<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.msg.controllers.Eip01w050Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="contents">
        <tags:fieldset legend="輿情專區">
            <form:form id="eip01w050Form" modelAttribute="${caseKey}">
                <div class="table-responsive mt-2">
                    <table class="table" id="qryListTable">
                        <thead>
                            <tr>
                                <th class="text-center">序號</th>
                                <th class="text-center">主題</th>
                                <th class="text-center">更新日期</th>
                                <th class="text-center">操作區</th>
                            </tr>
                        </thead>
                        <c:if test="${not empty caseData.qryList }">
                            <tbody>
                                <c:forEach items="${caseData.qryList}" var="item" varStatus="status">
                                    <tr data-seq="${status.index + 1 }">
                                        <td class="text-center">
                                            <c:out value="${status.index + 1 }" />
                                        </td>
                                        <td class="text-left">
                                            <c:out value="${item.subject}" />
                                        </td>
                                        <td class="text-center">
                                            <c:out value="${item.upddt}" />
                                        </td>
                                        <td class="text-center" data-fseq="${item.fseq}">
                                            <tags:button id="btnDetail">明細</tags:button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </c:if>
                    </table>
                </div>
                <form:hidden path="fseq" />
                <form:hidden path="seq" />
                <form:hidden path="subject" />
            </form:form>
        </tags:fieldset>

        <div id="popModal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">輿情專區</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">關閉</button>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>

    <jsp:attribute name="footers">
        <script type="text/javascript">
            $(function() {
                // 明細
                $('#qryListTable #btnDetail').click(function() {
                    let url = '<c:url value="/Eip01w050_getDetail.action" />';
                    let fseq = $(this).parent('td').data('fseq');
                    $('#fseq').val(fseq);
                    getDetail('5', url, fseq);
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
                    $('#eip01w050Form').attr('action', '<c:url value="/Eip01w050_getFile.action" />')
                        .submit();
                }
            });
            // 檔案下載連結
            $(document).on('click', '.alink', function(e) {
                $('#seq').val($(this).attr('id'));
                $('#eip01w050Form').attr('action', '<c:url value="/Eip01w050_getFile.action" />')
                    .submit();
            });
        </script>
    </jsp:attribute>
</tags:layout>
<%@ include file="/WEB-INF/jsp/eip01w010/eip01w010x.jsp" %>