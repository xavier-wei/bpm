<script src='<c:url value="/js/jquery.min.js"/>'></script>
<script src='<c:url value="/js/noty.min.js"/>'></script>
<script src='<c:url value="/js/datatables.min.js"/>?a=<%=tw.gov.pcc.eip.util.DateUtility.getNowChineseDateTime(true)%>'></script>

<script src='<c:url value="/js/bootstrap.min.js"/>'></script>

<script src='<c:url value="/js/stomp.min.js"/>'></script>
<script src='<c:url value="/js/function.js"/>?a=<%=tw.gov.pcc.eip.util.DateUtility.getNowChineseDateTime(true)%>'></script>
<script src='<c:url value="/js/onload.js"/>?a=<%=tw.gov.pcc.eip.util.DateUtility.getNowChineseDateTime(true)%>'></script>

<script src='<c:url value="/js/pagination.input.js"/>'></script>
<script src='<c:url value="/js/jquery.iisi.expand.js"/>?a=<%=tw.gov.pcc.eip.util.DateUtility.getNowChineseDateTime(true)%>'></script>

<script src='<c:url value="/js/jquery.ui/jquery-ui.js"/>?a=<%=tw.gov.pcc.eip.util.DateUtility.getNowChineseDateTime(true)%>'></script>
<script src='<c:url value="/js/dynatree/jquery.dynatree.js"/>?a=<%=tw.gov.pcc.eip.util.DateUtility.getNowChineseDateTime(true)%>'></script>

<script src='<c:url value="/js/bootstrap-datepicker.js"/>'></script>
<script src='<c:url value="/js/bootstrap-datepicker-zh-TW.min.js"/>'></script>
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


<%--float button--%>
<script>
    function reposIEButton(e) {
        var offset = e.data.offset;
        var nav = e.data.nav;
        var tmp = e.data.tmp;
        var navParent = e.data.navParent;

        var nheight = nav.height();
        var pstop = navParent.scrollTop();
        var crossTop = pstop + nheight;
        var diff = navParent.width() - nav.width() + parseInt(nav.css('margin-right').replace('px', ''), 10) - parseInt(navParent.css('padding-right').replace('px', ''), 10);

        if (crossTop > offset.top && nav.css('position') !== 'absolute') {
            nav.css('position', 'absolute');
            nav.css('right', diff + 'px');
            tmp.show();
        } else if (crossTop <= offset.top && (nav.css('position') === 'absolute' || tmp.is(":visible"))) {
            tmp.hide();
            nav.css('position', '');
            nav.css('right', '');
        }
    }


    <%-- for ie --%>
    $(function () {
        try {
            var nu = navigator.userAgent;
            var is_ie = nu.indexOf("MSIE ") > -1 || nu.indexOf("Trident/") > -1;
            if (is_ie && $('.formButtonDiv').length > 0) {
                var nav = $('.formButtonDiv:eq(0)');
                var navParent = nav.parent()
                var offset = navParent.offset();

                var obj = {
                    offset: offset,
                    nav: nav,
                    navParent: navParent,
                    tmp: $('<div id="_tmp"></div>').hide()
                };
                $(obj.tmp).height(nav.height()).css('margin-top', nav.css('margin-top')).insertAfter(nav);

                navParent.on('scroll', null, obj, reposIEButton);
            }
        } catch (e) {
            console.log('Floating Button error');
        }
    });
</script>
