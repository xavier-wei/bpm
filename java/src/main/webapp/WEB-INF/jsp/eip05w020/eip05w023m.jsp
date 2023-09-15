<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip05w020Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
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
    <tags:button id="btnReply">
        返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="意見調查部分列表">
    <form:form id="eip05w020Form" name="eip05w020Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="table-responsive">
                <table class="table" id="tb1">
                    <thead data-orderable="true">
                    <tr>
                        <th data-orderable="false" class="text-center align-middle"><input type="checkbox" id="selectAll"/><label for="selectAll" class="d-inline">全選</label></th>
                        <th class="text-center align-middle">項次</th>
                        <th class="text-center align-middle">部分標題</th>
                        <th class="text-center align-middle">子部分</th>
                        <th class="text-center align-middle">題目</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.partList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle"><form:checkbox path="sectitleseqList[${status.index}]"  value="${item.sectitleseq}"/></td>
                                <td class="text-center align-middle"><c:out value="${status.index+1}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.sectitle}" /></td>
                                <td data-qseqno="${item.qseqno}" data-sectitle="${item.sectitle}" data-sectitleseq="${item.sectitleseq}" class="text-center align-middle">
                                    <tags:button cssClass="subpart">子部分</tags:button>
                                </td>
                                <td data-qseqno="${item.qseqno}" class="text-center align-middle">
                                    <tags:button cssClass="modify">修改</tags:button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
        <form:hidden path="mode"/>
        <form:hidden path="topicname"/>
        <form:hidden path="sectitle"/>
        <form:hidden path="sectitleseq"/>
        <form:hidden path="osformno"/>
        <form:hidden path="qseqno"/>
    </form:form>
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
		$('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_selectPartInsertUpdate.action" />').submit();
	})
    $('#btnDelete').click(function(e){
        e.preventDefault();
        if($("input[name^='sectitleseqList']:checked").length == 0) {
            showAlert("請至少選擇一項");
            return;
        }
        showConfirm('確定刪除嗎？',()=>{
            $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_partDelete.action" />').submit();
        });
    })
    $('#btnReply').click(function(e){
        e.preventDefault();
        $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_enter.action" />').submit();
    })
    $('#tb1 button.subpart').click(function(e){
        e.preventDefault();
        $('#qseqno').val($(this).parent().data('qseqno'));
        $('#sectitle').val($(this).parent().data('sectitle'));
        $('#sectitleseq').val($(this).parent().data('sectitleseq'));
        $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_questionList.action" />').submit();
    });
    $('#tb1 button.modify').click(function(e){
        e.preventDefault();
        $('#mode').val('U');
        $('#qseqno').val($(this).parent().data('qseqno'));
        $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_selectPartInsertUpdate.action" />').submit();
    });
    //全選
    $("#selectAll").click(function() {
        if ($("#selectAll").is(":checked")) {
            $("input[name^='sectitleseqList']").each(function () {
                $(this).prop("checked", true);
            });
        } else {
            $("input[name^='sectitleseqList']").each(function () {
                $(this).prop("checked", false);
            });
        }
    });
    $("input[name^='sectitleseqList']").click(function() {
        var numberOfChecked = $("input[name^='sectitleseqList']:checked").length;
        var allcheckbox = $("input[name^='sectitleseqList']").length;
        if (numberOfChecked === allcheckbox) {
            $("#selectAll").prop("checked",true);
        }else {
            $("#selectAll").prop("checked",false);
        }
    });
})
</script>
</jsp:attribute>
</tags:layout>