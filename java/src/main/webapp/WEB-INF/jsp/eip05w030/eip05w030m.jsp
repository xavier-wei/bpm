<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKeyA" expression="T(tw.gov.pcc.eip.common.controllers.Eip05w030Controller).CASE_KEY" />
<c:set var="caseDataA" value="${requestScope[caseKeyA]}" />
<spring:eval var="caseKeyB" expression="T(tw.gov.pcc.eip.common.controllers.Eip05w020Controller).CASE_KEY" />
<c:set var="caseDataB" value="${requestScope[caseKeyB]}" />
<tags:layout>
<jsp:attribute name="heads">
    <link rel="stylesheet" href="<c:url value='/css/18698.css' />"/>
    <style>
        td .btn {
            vertical-align: baseline;
        }
        .row {
            margin-left: -15px;
            margin-right: -15px;
        }
        .nav-tabs .nav-link {
            width: 10%
        }
</style>
</jsp:attribute>
<jsp:attribute name="contents">
<tags:fieldset legend="意見調查">
    <nav class="nav pt-4 navbar-expand">
        <div id="nav-tab3" role="tablist" class="nav nav-tabs container-fluid">
            <button id="tab1" data-toggle="tab" role="tab"
                    aria-controls="nav-inform" aria-selected="true" type="button"
                    class="btn nav-link btn-secondary active">列表
            </button>
            <button id="tab2" data-toggle="tab" role="tab"
                    aria-controls="nav-download" aria-selected="false" type="button"
                    class="btn nav-link btn-secondary">歷史區
            </button>
        </div>
    </nav>
    <tags:form-row>
        <div class="table-responsive" id="div1">
            <table class="table" id="tb1">
                <thead data-orderable="true">
                <tr>
                    <th class="text-center align-middle">項次</th>
                    <th class="text-center align-middle">主題</th>
                    <th colspan="2" class="text-center align-middle">操作區</th>
                    <th style="display: none"></th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${caseDataA.osList}" var="item" varStatus="status">
                        <tr>
                            <td class="text-center align-middle"><c:out value="${status.index+1}" /></td>
                            <td class="text-left align-middle"><c:out value="${item.topicname}" /></td>
                            <td class="text-center align-middle">
                                <tags:button cssClass="btnWrite" data-formno="${item.osformno}" data-completed="${item.iscompleted}" data-status="${item.status}">填寫</tags:button>
                            </td>
                            <td class="text-center align-middle">
                                <tags:button cssClass="btnResult" data-formno="${item.osformno}" data-disstatic="${item.isdisstatic}">結果</tags:button>
                            </td>
                            <td style="display: none"></td>
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
                    <c:forEach items="${caseDataA.hisList}" var="item" varStatus="status">
                        <tr>
                            <td class="text-center align-middle"><c:out value="${status.index+1}" /></td>
                            <td class="text-left align-middle"><c:out value="${item.topicname}" /></td>
                            <td class="text-center align-middle">
                                <tags:button cssClass="btnHisResult" data-formno="${item.osformno}" data-disstatic="${item.isdisstatic}">結果</tags:button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <form:form id="eip05w020Form" name="eip05w020Form" modelAttribute="${caseKeyA}" method="POST">
        <form:hidden path="osformno"/>
        <form:hidden path="promptmsg"/>
        </form:form>
    </tags:form-row>


</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    //dataTable 設定
    let config = getDataTablesConfig();
    config.pageLength = 20;
    $('#tb1').DataTable(config);
    $('#tb2').DataTable(config);

    $('.nav-link').click(function(){
        let clickId = $(this).attr("id").substring(3);
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

    $('.btnWrite').click(function(e) {
        e.preventDefault();
        $('#promptmsg').val('');
        $('#osformno').val($(this).data('formno'));
        $('#eip05w020Form').attr('action', '<c:url value="/Eip05w030_write.action" />').submit();
    });

    $('.btnResult,.btnHisResult').click(function(e) {
        e.preventDefault();
        $('#promptmsg').val('');
        $('#osformno').val($(this).data('formno'));
        $('#eip05w020Form').attr('action', '<c:url value="/Eip05w030_result.action" />').submit();
    });
    
    //初始化
    $('.btnResult,.btnHisResult').each(function (){
        if ($(this).data('disstatic')==='N') {
            $(this).prop('disabled',true);
        } else {
            $(this).prop('disabled',false);
        }
    });

    $('.btnWrite').each(function (){
        if ($(this).data('completed')==='Y' || $(this).data('status')!=='I') {
            $(this).prop('disabled',true);
        } else {
            $(this).prop('disabled',false);
        }
    });

    if ($('#promptmsg').val()) {
        showAlert($('#promptmsg').val());
    }

})
</script>
</jsp:attribute>
</tags:layout>