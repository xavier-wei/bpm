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

<script src='<c:url value="/js/Sortable.min.js"/>'></script>

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

        $(".dateTW").each((i, e) => {
            $(e).prop('autocomplete','off');
            let clone = $(e).clone();
            clone.removeClass('dateTW').attr('type', 'hidden').insertAfter($(e));
            $(e).data('real',$(e).prop('id')).prop('name', $(e).prop('name') + '_OUTSIDE').prop('id', $(e).prop('id') + '_OUTSIDE');
        });

        let $dateTW = $(".dateTW"); //上面那個不要替換
        $dateTW.datepicker({
            weekStart: 1,
            daysOfWeekHighlighted: "6,0",
            autoclose: true,
            todayHighlight: true,
            format: 'yyyy/mm/dd',
            language: 'zh-TW'
        }).on('show', function (ev) {
            $(this).val() || $(this).trigger('change');
        }).on('keyup', function (e) {
            const charCode = (e.which) ? e.which : e.keyCode;
            // 輸入數字
            if ((charCode >= 48 && charCode <= 57) || (charCode >= 96 && charCode <= 105)) {
                convertDate($(this));
            }
            $('#'+$(this).data('real')).val($(this).val().replaceAll('/', ''));
        }).on('hide',function (e) {
            //裡面做了轉換
            $('#'+$(this).data('real')).val($(this).val().replaceAll('/', ''));
        });

        $dateTW.each(function () {
            const $each = $(this);
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
    function convertDate(inputDate) {
        let inputVal = inputDate.val();
        let year = "";
        let month = "";
        let day = "";

        let orgInputVal = inputVal;
        inputVal = inputVal.replaceAll('/', '')
        let dateLen = inputVal.length;
        if (dateLen === 7) {
            year = inputVal.substring(0, dateLen - 4);
            month = inputVal.substring(dateLen - 4, dateLen - 2);
            day = inputVal.substring(dateLen - 2);
            // 資料長度為7時，重置日期
            inputDate.datepicker('update', (parseInt(year) + 1911) + '/' + month + '/' + day);
            inputDate.val(year + '/' + month + '/' + day);
        } else {
            inputDate.val(orgInputVal);
        }
    }
</script>

<%--key binding--%>
<script>
    $(function () {
        jQuery('body').iisiCommonHandler();
    });
</script>

<%-- 超過50個字以"..."取代，滑鼠移過去會顯示完整字串 --%>
<script>
    $(function () {
        var len = 51;
        $(".ellipsisStr").each(function (i) {
            if ($(this).text().trim().length > len) {
                $(this).attr("title", $(this).text().trim());
                var text = $(this).text().trim().substring(0, len - 1) + "...";
                $(this).text(text);
                console.log(text);
            }
        });
    });
</script>