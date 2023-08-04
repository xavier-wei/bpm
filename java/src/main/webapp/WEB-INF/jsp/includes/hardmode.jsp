<script src='https://pcictw.pcc.gov.tw/WebIme/cmexwebime.js'></script>
<script src="https://pcictw.pcc.gov.tw/WebFont/cmexwebfont.js"></script>

<%--EIP 使用的全字庫輸入法 前置作業--%>
<%--目前好像用不到，先暫留--%>
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
