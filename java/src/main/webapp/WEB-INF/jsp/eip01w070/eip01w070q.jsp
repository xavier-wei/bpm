<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.msg.controllers.Eip01w070Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="contents">
        <tags:fieldset legend="業務資訊">
            <form:form id="eip01w070Form" modelAttribute="${caseKey}">
                <tags:form-row>
                    <div class="col-12 col-md">
                        <form:select path="dept" cssClass="form-control ml-4" style="width:30%;">
                            <form:options items="${caseData.depts}" />
                        </form:select>
                    </div>
                </tags:form-row>
                <form:hidden path="fseq"/>
                <form:hidden path="seq"/>
                <form:hidden path="subject"/>
                <br>
                <div class="msg">

                </div>
            </form:form>
        </tags:fieldset>
    </jsp:attribute>

    <jsp:attribute name="footers">
        <script type="text/javascript">
            $(function() {
                // 明細
                $('#dept').change(function() {
                    $.ajax({
                        type: "POST",
                        url: '<c:url value="/Eip01w070_getDetail.action" />',
                        data: {
                            'contactunit': $('#dept').val()
                        },
                        timeout: 100000,
                        success: function(data) {
                            if (data == '') {
                                showAlert('查無資料!');
                            } else {
                                var title = '';
                                var msg = '';
                                var str = '';
                                title = !$('select[name="dept"] option').length ? '' : $('select[name="dept"] :selected').html();
                                $.each(data.msgs, function(i, e) { // 串接內容
                                    msg += '　　' + (i + 1) + '.' + e + '<br>';
                                });
                                $.each(data.files, function(i, e) { // 串接檔名
                                    str +=
                                        '<a href="javascript:;" class="alink" id=' +
                                        i + '>' +
                                        e + '</a>' + '　';
                                });
                                $('.msg').html('');
                                $('.msg').html(title +
                                    '<br>' + msg +
                                    '<br>附檔下載：' + str);
                                $('#subject').val(data.subject);
                            }
                        },
                        error: function(e) {
                            showAlert("取得資料發生錯誤");
                        }
                    });
                });
                $('#dept').trigger('change');
            });
            // 檔案下載連結
            $(document).on('click', '.alink', function(e) {
                var id = $(this).attr('id').split('_');
                $('#fseq').val(id[0]);
                $('#seq').val(id[1]);
                $('#eip01w070Form').attr('action', '<c:url value="/Eip01w070_getFile.action" />')
                    .submit();
            });
        </script>
    </jsp:attribute>
</tags:layout>