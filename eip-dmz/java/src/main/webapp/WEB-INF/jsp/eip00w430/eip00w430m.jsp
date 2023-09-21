<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w430Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        td .btn {
            vertical-align: baseline;
        }
        .row {
            margin-left: -15px;
            margin-right: -15px;
        }
        .nav-link{
            border-radius: 8px 8px 0px 0px;
            padding: 4px;
            background-color: #8fd4ce;
            background-image: linear-gradient(to bottom, #c0dad5, #c0dad5);
            border: 1px solid #107db0;
            /*box-shadow: inset 0 1px 0 #eeeeee, inset 0 -1px 0 #eeeeee, inset 0 0 0 1px #eeeeee, 0 2px 4px rgba(0, 0, 0, 0.2);*/
            color: white;
        }

        .nav-link.font-weight-bold.active {
            border-radius: 8px 8px 0px 0px;
            padding: 4px;
            background-color: #ffffff;
            background-image: linear-gradient(to bottom, #fdd9a1, #fdd9a1);
            border: 1px solid #b2784e;
            box-shadow: inset 0 1px 0 #fdd9a1, inset 0 -1px 0 #fdd9a1, inset 0 0 0 1px #fdd9a1, 0 2px 4px rgba(0, 0, 0, 0.2);
            color: white;
        }
</style>
</jsp:attribute>
<jsp:attribute name="contents">
<tags:fieldset legend="線上報名">
    <div id="tabLinks" class="row">
        <div class="col-6 col-md">
            <ul class="nav nav-tabs text-center mt-1">
                <li class="nav-item"><a id="tab1" class="nav-link" href="#">報名</a></li>
                <li class="nav-item"><a id="tab2" class="nav-link" href="#">歷史區</a></li>
            </ul>
        </div>
    </div>
    <form:form id="eip00w430Form" name="eip00w430Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="table-responsive" id="div1">
                <table class="table" id="tb1">
                    <thead data-orderable="true">
                    <tr>
                        <th class="text-center align-middle">項次</th>
                        <th class="text-center align-middle">主題</th>
                        <th class="text-center align-middle">報名結果</th>
                        <th class="text-center align-middle">操作區</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.regList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle"><c:out value="${status.index+1}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.topicname}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.statusVal}" /></td>
                                <td class="text-center align-middle">
                                    <tags:button cssClass="btnContent" data-formno="${item.orformno}">內容</tags:button>
                                    <tags:button cssClass="btnReg" data-formno="${item.orformno}" data-status="${item.status}" data-full="${item.isfull}" data-result="${item.regresultVal}">報名</tags:button>
                                    <tags:button cssClass="btnCancel" data-formno="${item.orformno}" data-status="${item.status}" data-result="${item.regresultVal}">取消報名</tags:button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="table-responsive" id="div2">
                <table class="table" id="tb2">
                    <thead data-orderable="true">
                    <tr>
                        <th class="text-center align-middle">項次</th>
                        <th class="text-center align-middle">主題</th>
                        <th class="text-center align-middle">操作區</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.reghisList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle"><c:out value="${status.index+1}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.topicname}" /></td>
                                <td class="text-center align-middle">
                                    <tags:button cssClass="btnHisContent" data-formno="${item.orformno}">內容</tags:button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
        <form:hidden path="orformno"/>
    </form:form>
    <tags:form-note>
    <tags:form-note-item>審核後欲取消報名請來電通知聯絡人。</tags:form-note-item>
    </tags:form-note>
</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    // dataTable 設定
    let config = getDataTablesConfig();
    config.pageLength = 20;
    $('#tb1').DataTable(config);
    $('#tb2').DataTable(config);

    $('.nav-item').click(function(){
        $('.nav-link').removeClass('font-weight-bold active');
        $(this).find('a').addClass('font-weight-bold active');
        let clickId = $(this).find('a').attr("id").substring(3);
        if (clickId === '1') {
            $('#div1').show();
            $('#div2').hide();
        } else {
            $('#div2').show();
            $('#div1').hide();
        }
    });

    //初始化
    $('#tab1').trigger("click");

    $('.btnContent,.btnHisContent').click(function(e) {
        e.preventDefault();
        $('#orformno').val($(this).data('formno'));
        $('#eip00w430Form').attr('action', '<c:url value="/Eip00w430_content.action" />').submit();
    });

    $('.btnReg').click(function(e) {
        e.preventDefault();
        $('#orformno').val($(this).data('formno'));
        $('#eip00w430Form').attr('action', '<c:url value="/Eip00w430_register.action" />').submit();
    });

    $('.btnCancel').click(function(e) {
        e.preventDefault();
        $('#orformno').val($(this).data('formno'));
        $('#eip00w430Form').attr('action', '<c:url value="/Eip00w430_cancel.action" />').submit();
    });

    //初始化
    $('.btnReg').each(function (){
        if ($(this).data('status')==='P' || ["Y","N","E"].includes($(this).data('result')) || $(this).data('full')===true) {
            $(this).prop('disabled',true);
        } else {
            $(this).prop('disabled',false);
        }
    });

    $('.btnCancel').each(function (){
        if ($(this).data('result')==='E') {
            $(this).prop('disabled',false);
        } else {
            $(this).prop('disabled',true);
        }
    });
})
</script>
</jsp:attribute>
</tags:layout>