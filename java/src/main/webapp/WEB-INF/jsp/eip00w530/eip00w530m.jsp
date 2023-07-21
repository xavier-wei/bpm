<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w530Controller).CASE_KEY" />
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
<tags:fieldset legend="意見調查">
    <div id="tabLinks" class="row">
        <div class="col-6 col-md">
            <ul class="nav nav-tabs text-center mt-1">
                <li class="nav-item"><a id="tab1" class="nav-link" href="#">列表</a></li>
                <li class="nav-item"><a id="tab2" class="nav-link" href="#">歷史區</a></li>
            </ul>
        </div>
    </div>
    <form:form id="eip00w530Form" name="eip00w530Form" modelAttribute="${caseKey}" method="POST">
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
                        <c:forEach items="${caseData.osList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle"><c:out value="${status.index+1}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.topicname}" /></td>
                                <td class="text-center align-middle">
                                    <tags:button cssClass="btnWrite" data-formno="${item.osformno}" data-completed ="${item.iscompleted}">填寫</tags:button>
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
                        <c:forEach items="${caseData.hisList}" var="item" varStatus="status">
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
        </tags:form-row>
        <form:hidden path="osformno"/>
        <form:hidden path="promptmsg"/>
    </form:form>
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

    $('.btnWrite,.btnHisWrite').click(function(e) {
        e.preventDefault();
        $('#promptmsg').val('');
        $('#osformno').val($(this).data('formno'));
        $('#eip00w530Form').attr('action', '<c:url value="/Eip00w530_write.action" />').submit();
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
        if ($(this).data('completed')==='Y') {
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