<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip05w020Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        td .btn {
            vertical-align: baseline;
        }
        .btntd2 {
            min-width: 72px;
        }
        .btntd4 {
            min-width: 105px;
        }
        .dateCol {
            min-width: 150px;
        }
    </style>
</jsp:attribute>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
    <tags:button id="btnInsert">
        新增<i class="fas fa-user-plus"></i>
    </tags:button>          
    <tags:button id="btnDelete">
        刪除<i class="fas fa-trash-alt"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="意見調查主題列表">
    <form:form id="eip05w020Form" name="eip05w020Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="table-responsive">
                <table class="table" id="tb1">
                    <thead data-orderable="true">
                        <tr>
                            <th data-orderable="false" class="text-center align-middle"><input type="checkbox" id="selectAll"/><label class="d-inline" for="selectAll">全選</label></th>
                            <th class="text-center align-middle">項次</th>
                            <th class="text-center align-middle">主題名稱</th>
                            <th class="text-center align-middle dateCol">投票<br>開始/結束日期</th>
                            <th class="text-center align-middle">狀態</th>
                            <th class="text-center align-middle" data-orderable="false">部分列表</th>
                            <th class="text-center align-middle" colspan="3">填寫內容</th>
                            <th class="text-center align-middle" colspan="4">操作區</th>
                            <th class="text-center align-middle" colspan="2">統計表</th>
                            <th style="display: none"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.osList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle"><form:checkbox path="osformnoList" data-status="${item.statusVal}" data-anonymous="${item.isanonymous}" data-topicname="${item.topicname}" value="${item.osformno}"/></td>
                                <td class="text-center align-middle"><c:out value="${status.index+1}"/></td>
                                <td class="text-left align-middle"><c:out value="${item.topicname}" escapeXml="false"/></td>
                                <td class="text-center align-middle"><c:out value="${item.osfmdt}"/>~<br><c:out value="${item.osendt}" />&nbsp;&nbsp;</td>
                                <td class="text-center align-middle btntd2"><c:out value="${item.status}"/></td>
                                <td class="text-center align-middle btntd4"><tags:button cssClass="groupN groupP" data-action="6">部分列表</tags:button></td>
                                <td class="text-center align-middle btntd2"><tags:button cssClass="groupI groupT groupC anonymous" data-action="3">匯出</tags:button></td>
                                <td class="text-center align-middle btntd2"><tags:button cssClass="groupI groupT groupC anonymous" data-action="4">查詢</tags:button></td>
                                <td class="text-center align-middle btntd2"><tags:button cssClass="groupN groupP groupI groupT groupC" data-action="5">預覽</tags:button></td>
                                <td class="text-center align-middle btntd2"><tags:button cssClass="groupN groupP groupI groupT groupC" data-action="7">複製</tags:button></td>
                                <td class="text-center align-middle btntd2"><tags:button cssClass="groupN groupP" data-action="8">修改</tags:button></td>
                                <td class="text-center align-middle btntd2"><tags:button cssClass="groupN" data-action="9">上架</tags:button></td>
                                <td class="text-center align-middle btntd2"><tags:button cssClass="groupP groupI" data-action="10">下架</tags:button></td>
                                <td class="text-center align-middle btntd2"><tags:button cssClass="groupI groupC" data-action="1">檢視</tags:button></td>
                                <td class="text-center align-middle btntd2"><tags:button cssClass="groupI groupC" data-action="2">匯出</tags:button></td>
                                <td style="display: none"></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
        <form:hidden path="mode"/>
        <form:hidden path="osformno"/>
        <form:hidden path="topicname"/>
    </form:form>
    <tags:form-note>
    <tags:form-note-item>功能是否開放根據活動狀態決定。</tags:form-note-item>
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

    $('#btnInsert').click(function(e){
        e.preventDefault();
        $('#mode').val('A');
        $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_selectInsertUpdate.action" />').submit();
    });

    $('#btnDelete').click(function(e){
        e.preventDefault();
        if($("input[name^='osformnoList']:checked").length == 0) {
            showAlert("請至少勾選一項");
            return;
        }
        let isAllowDel = true;
        $("input[name^='osformnoList']:checked").each(function () {
            let status = $(this).data('status');
            if ($.inArray(status, ["N"]) < 0){
                isAllowDel = false;
            }
        });
        if (!isAllowDel) {
            showAlert("所選主題需為「已建檔」，才可進行刪除！");
            return;
        }
        showConfirm('確定刪除嗎？',()=>{
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_delete.action" />').submit();
        });
    })

    $('#tb1 button').click(function(e){
        e.preventDefault();
        let action = $(this).data('action');
            $('#osformno').val($(this).parent().siblings().eq(0).find(':first-child').val());
        if (action === 1) {
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_review.action" />').submit();
        } else if (action === 2) {
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_printStatistics.action" />').submit();
        } else if (action === 3) {
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_printWriteContent.action" />').submit();
        } else if (action === 4) {
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_contentQuery.action" />').submit();
        } else if (action === 5) {
            $('#mode').val('P');
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_preview.action" />').submit();
        } else if (action === 6) {
            $('#topicname').val($(this).parent().siblings().eq(0).find(':first-child').data('topicname'));
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_partList.action" />').submit();
        } else if (action === 7) {
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_copy.action" />').submit();
        } else if (action === 8) {
            $('#mode').val('U');
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_selectInsertUpdate.action" />').submit();
        } else if (action === 9) {
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_put.action" />').submit();
        } else if (action === 10) {
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_off.action" />').submit();
        }
    });

    //全選
    $("#selectAll").click(function() {
        if ($("#selectAll").is(":checked")) {
            $("input[name^='osformnoList']").each(function () {
                $(this).prop("checked", true);
            });
        } else {
            $("input[name^='osformnoList']").each(function () {
                $(this).prop("checked", false);
            });
        }
    });

    $("input[name^='osformnoList']").click(function() {
        var numberOfChecked = $("input[name^='osformnoList']:checked").length;
        var allcheckbox = $("input[name^='osformnoList']").length;
        if (numberOfChecked === allcheckbox) {
            $("#selectAll").prop("checked",true);
        }else {
            $("#selectAll").prop("checked",false);
        }
    });

    $("#tb1 tbody tr").each(function() {
        var status = $(this).find("input[name^='osformnoList']").data('status');
        var anonymous = $(this).find("input[name^='osformnoList']").data('anonymous');
        var buttons = $(this).find(".btn");
        buttons.prop("disabled", true);
        if (status === 'N') {
            buttons = $(this).find(".groupN");
            buttons.prop("disabled", false);
        } else if (status === 'P') {
            buttons = $(this).find(".groupP");
            buttons.prop("disabled", false);
        } else if (status === 'I') {
            buttons = $(this).find(".groupI");
            buttons.prop("disabled", false);
        } else if (status === 'T') {
            buttons = $(this).find(".groupT");
            buttons.prop("disabled", false);
        } else if (status === 'C') {
            buttons = $(this).find(".groupC");
            buttons.prop("disabled", false);
        }
        if (anonymous === 'Y') {
            buttons = $(this).find(".anonymous");
            buttons.prop("disabled", true);
        }
    });

})
</script>
</jsp:attribute>
</tags:layout>