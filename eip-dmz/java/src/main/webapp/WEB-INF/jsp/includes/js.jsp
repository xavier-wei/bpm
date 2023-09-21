<script src='<c:url value="/js/jquery.min.js"/>'></script>
<script src='<c:url value="/js/noty.min.js"/>'></script>
<script src='<c:url value="/js/datatables.min.js"/>'></script>

<script src='<c:url value="/js/popper.min.js"/>'></script>
<script src='<c:url value="/js/bootstrap.min.js"/>'></script>
<script src='<c:url value="/js/bootstrap-select.min.js"/>'></script>

<script src='<c:url value="/js/stomp.min.js"/>'></script>
<script src='<c:url value="/js/function.js"/>'></script>
<script src='<c:url value="/js/onload.js"/>'></script>

<script src='<c:url value="/js/pagination.input.js"/>'></script>
<script src='<c:url value="/js/jquery.iisi.expand.js"/>'></script>

<script src='<c:url value="/js/jquery.ui/jquery-ui.js"/>'></script>
<script src='<c:url value="/js/dynatree/jquery.dynatree.js"/>'></script>

<script src='<c:url value="/js/bootstrap-datepicker.js"/>'></script>
<script src='<c:url value="/js/bootstrap-datepicker-zh-TW.min.js"/>'></script>

<script src='https://pcictw.pcc.gov.tw/WebIme/cmexwebime.js'></script>
<script src="https://pcictw.pcc.gov.tw/WebFont/cmexwebfont.js"></script>
<%--websocket--%>
<script>
    $(function () {
        let stompClient = null;
        let socket = null;
        try {
            socket = new WebSocket((window.location.origin + '<c:url value="/eip-websocket" />').replace('http:', 'ws:').replace('https:', 'wss:'));
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                stompClient.subscribe('/user/queue/unlock', function (payload) {
                    if (payload != null) {
                        let msg = JSON.parse(payload.body).message;
                        if (msg === "Unlock") {
                            unlockAllButtons();
                        }
                    }
                });
            });
            _socket = socket;
        } catch (e) {
            console.log(e);
        }

        $(".dateTW").datepicker({
            weekStart: 1,
            daysOfWeekHighlighted: "6,0",
            autoclose: true,
            todayHighlight: true,
            format : 'yyyy/mm/dd',
            language : 'zh-TW'
        }).on('show', function(ev) {
            $(this).val() || $(this).trigger('change');
        }).on('keyup', function(e) {
            var charCode = (e.which) ? e.which : e.keyCode;
            // 輸入數字
            if ((charCode >= 48 && charCode <= 57) || (charCode >= 96 && charCode <= 105)) {
                convertDate($(this), false);
            }
        }).on('blur', function(e) {
            convertDate($(this), true);
        });

        $(".dateTW").each(function() {
            var $each = $(this);
            if ($each.attr('readonly')) {
                $each.data('datepicker')._detachEvents();
            }
        });
    });

    /**
     * 將點選的日期轉換為民國日期
     *
     * @param inputDate 輸入日期
     * @param isBlur
     * @returns
     */
    function convertDate(inputDate, isBlur){
        var inputVal = inputDate.val();
        var dateLen = inputVal.length;
        var year = "";
        var month = "";
        var day = "";

        if(isBlur){
            if(dateLen > 0){
                year = inputVal.substring(0, dateLen - 4);
                month = leftPad(inputVal.substring(dateLen - 4, dateLen - 2), 2, '0');
                day = leftPad(inputVal.substring(dateLen - 2), 2, '0');

                if(leftPad(year, 3, '0') != year){
                    // 年度有補0，重新設置日期
                    inputDate.datepicker('update',  (parseInt(year) + 1911) + '/' + month + '/' + day);
                }

                inputDate.val(leftPad(year, 3, '0') + month + day);
                //console.log('blur:' + leftPad(year, 3, '0') + month + day);
            }
        } else {
            year = inputVal.substring(0, dateLen - 4);
            month = inputVal.substring(dateLen - 4, dateLen - 2);
            day = inputVal.substring(dateLen - 2);

            if(dateLen == 7){
                // 資料長度為7時，重置日期
                inputDate.datepicker('update',  (parseInt(year) + 1911) + '/' + month + '/' + day);
            }
            inputDate.val(inputVal);
        }
    }
</script>

<%--key binding--%>
<script>
    $(function () {
        jQuery('body').iisiCommonHandler();
    });
</script>

<%-- 全字庫輸入法 --%>
<script>
    let webImeLoaded = false;

    function hardmode(id, obj) {
        if (!webImeLoaded) {
            webImeLoaded = true;
            loadWebImeScript(() => {
                createWebIme('.web-ime-load', {showWordType: false, onlyExt: false});
            });
        }

        let $input = $('#' + id);
        if ($input.prop('disabled')) {
            $input.val($('.web-ime-' + id).val());
        } else {
            $('.web-ime-' + id).val($input.val());
        }
        let $_input = $('#' + '_' + id);
        if ($input.is(':visible')) {
            $input.hide().prop('disabled', true);
            $_input.show().prop('disabled', false);
            $(obj).text('關閉').prev('img').show();
        } else {
            $input.show().prop('disabled', false);
            $_input.hide().prop('disabled', true);
            $(obj).text('難字').prev('img').hide();
        }
    }

    $(function () {
        $('.web-ime').each(function () {
            let id = $(this).attr('id');
            let $newInput = $(this).clone().attr('id', '_' + id).addClass('web-ime-' + id).css('display', 'none').addClass('web-ime-load').prop('disabled', true);
            $(this).after($newInput);
            let $button = $('<button type="button" class="btn btn-outline-be" onclick="hardmode(\`' + id + '\`, this)">難字</button>').attr('id', '_' + id + '_button');
            $($newInput).after($button);
        });
    });
</script>
