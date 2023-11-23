<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.msg.controllers.Eip01w060Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="contents">
        <tags:fieldset legend="單位簡介">
            <form:form id="eip01w060Form" modelAttribute="${caseKey}">
                <tags:form-row>
                    <div class="col-12 col-md">
                        <form:select path="dept" cssClass="form-control ml-4" style="width:30%;">
                            <form:options items="${caseData.depts}" />
                        </form:select>
                    </div>
                </tags:form-row>
                <form:hidden path="fseq" />
                <form:hidden path="seq" />
                <form:hidden path="subject" />
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
                        url: '<c:url value="/Eip01w060_getDetail.action" />',
                        data: {
                            'contactunit': $('#dept').val()
                        },
                        timeout: 100000,
                        success: function(${"data"}) {
                            if (data == '') {
                                showAlert('查無資料!');
                            } else {
                                var msgcontent = '';
                                var fileList = '';
                                var title = !$('select[name="dept"] option').length ? '' :
                                    $('select[name="dept"] :selected').html();
                                var count = Object.keys(data.files).length;
                                if (count == 1) {
                                    var key = Object.keys(data.files);
                                    fileList +=
                                        '附檔下載：<a href="javascript:;" class="alink" id=' +
                                        key + '>' +
                                        data.files[key] + '</a>';
                                } else if (count == 0) {
                                    fileList += '附檔下載：';
                                } else {
                                    fileList +=
                                        '<div style="display: flex;">' +
                                        '<div style="flex: none;">附檔下載：</div><div>';
                                    $.each(data.files, function(key, value) {
                                        fileList +=
                                            '<div class="d-inline-flex mr-3">' +
                                            '<input type="checkbox" id="' +
                                            key +
                                            '" name="filelist"><a href="javascript:;" class="alink" id=' +
                                            key + '>' + value + '</a></div>';
                                    });
                                    fileList +=
                                        '</div></div>' +
                                        '<button type="button" class="btn btn-outline-be btn-sm mr-1" ' +
                                        'style="margin-left: 80px;" id="zipDownload">下載</button>';
                                }
                                msgcontent += title + '<br>' +
                                    ((data.pagetype === 'A') ? data.mcontent : '<a href="' +
                                        data.mcontent + '" target="_blank">' + data
                                        .mcontent + '</a>') + '<br>' + fileList;
                                $('.msg').html('');
                                $('.msg').html(msgcontent);
                                $('#fseq').val(data.fseq);
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
            // 檔案下載按鈕
            $(document).on('click', '#zipDownload', function(e) {
                var checkedList = $('input:checkbox[name="filelist"]:checked');
                if (checkedList.length) {
                    var idArray = [];
                    checkedList.each(function() {
                        idArray.push(this.id);
                    });
                    $('#seq').val(idArray.join(','));
                    $('#eip01w060Form').attr('action', '<c:url value="/Eip01w060_getFile.action" />')
                        .submit();
                }
            });
            // 檔案下載連結
            $(document).on('click', '.alink', function(e) {
                $('#seq').val($(this).attr('id'));
                $('#eip01w060Form').attr('action', '<c:url value="/Eip01w060_getFile.action" />')
                    .submit();
            });
        </script>
    </jsp:attribute>
</tags:layout>