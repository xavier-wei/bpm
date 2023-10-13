<script>
    function getDetail(no, url, fseq) {
        $.ajax({
            type: "POST",
            url: url,
            data: {
                'fseq': fseq
            },
            timeout: 100000,
            success: function(data) {
                if (data == '') {
                    showAlert('查無資料!');
                } else {
                    var fileList = '';
                    var bodyContent = '';
                    var count = Object.keys(data.file).length;
                    if (count == 1) {
                        var key = Object.keys(data.file);
                        fileList +=
                            '附加檔案：<a href="javascript:;" class="alink" id=' +
                            key + '>' +
                            data.file[key] + '</a>';
                    } else if (count == 0) {
                        fileList += '附加檔案：';
                    } else {
                        fileList +=
                            '<div style="display: flex;">' +
                            '<div style="flex: none;">附加檔案：</div><div>';
                        $.each(data.file, function(key, value) {
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
                    if (data.pagetype === 'A') {
                        bodyContent += '主　　題：' + data.subject;
                        bodyContent += '<br>訊息文字：' + data.mcontent;
                    } else {
                        bodyContent += '連結標題：' + data.subject;
                        bodyContent += '<br>連結網址：<a href="' + data.mcontent + '" target="_blank">' + data
                            .mcontent + '</a>';
                    }
                    bodyContent += '<br>發布單位：' + data.contactunit;
                    bodyContent += '<br>' + fileList;
                    bodyContent += '<br>更新日期：' + data.upddt;
                    bodyContent += '<br>聯絡人　：' + data.contactperson;
                    bodyContent += '<br>聯絡電話：' + data.contacttel;
                    $('#subject').val(data.subject);
                    if (no === '3') {
                        $('.modal-title').html('公告事項－' + data.msgtype);
                    }
                    $('.modal-body').html(bodyContent);
                    $('#popModal').modal('show');
                }
            },
            error: function(e) {
                showAlert("取得資料發生錯誤");
            }
        });
    }
</script>