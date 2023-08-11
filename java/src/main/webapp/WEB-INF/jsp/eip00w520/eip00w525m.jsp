<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w520Controller).CASE_KEY" />
<spring:eval var="optionCaseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w520Controller).OPTION_CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<c:set var="optionData" value="${requestScope[optionCaseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        .col-form-label {
            min-width: 140px;
        }
    </style>
</jsp:attribute>
    <jsp:attribute name="buttons">
    <tags:button id="btnInsert">
        新增<i class="fas fa-user-plus"></i>
    </tags:button>
    <tags:button id="btnUpdate">
        修改<i class="fas fa-user-edit"></i>
    </tags:button>
    <tags:button id="btnDelete">
        刪除<i class="fas fa-trash-alt"></i>
    </tags:button>
    <tags:button id="btnReply">
        返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
<tags:fieldset legend="${caseData.mode eq 'A' ? '新增' : '修改'}部分列表選項(子表格)">
<form:form id="eip00w520Form" modelAttribute="${optionCaseKey}">
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="topicname">主題名稱：</form:label>
            <c:out value="${optionData.topicname}"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="sectitleseq">部分排序：</form:label>
            <c:out value="${optionData.sectitleseq}"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="sectitle">部分標題：</form:label>
            <c:out value="${optionData.sectitle}"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="topicseq">題目排序：</form:label>
            <c:out value="${optionData.topicseq}"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="topic">題目：</form:label>
            <c:out value="${optionData.topic}"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6 d-flex">
            <form:label cssClass="col-form-label star" path="itemseq">選項排序：</form:label>
            <form:input path="itemseq" cssClass="form-control d-inline-block num_only" size="3" maxlength="2"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6 d-flex">
            <form:label cssClass="col-form-label star" path="itemdesc">選項說明：</form:label>
            <form:input path="itemdesc" cssClass="form-control d-inline-block" size="20" maxlength="33"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="isaddtext">是否增加文字框：</form:label>
            <label>
                <form:radiobutton path="isaddtext" value="Y"/>
                <span class="font-weight-bold">是</span>
            </label>
            <label>
                <form:radiobutton path="isaddtext" value="N"/>
                <span class="font-weight-bold">否</span>
            </label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="table-responsive">
            <table class="table" id="tb1">
                <thead data-orderable="true">
                <tr>
                    <th data-orderable="false" class="text-center align-middle"><input type="checkbox" id="selectAll"/><label for="selectAll" class="d-inline">全選</label></th>
                    <th class="text-center align-middle">排序</th>
                    <th class="text-center align-middle">選項說明</th>
                    <th class="text-center align-middle">是否增加文字框</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${caseData.optionList}" var="item" varStatus="status">
                    <tr class="checktr">
                        <td class="text-center align-middle"><form:checkbox path="iseqnoList[${status.index}]"  value="${item.iseqno}"/></td>
                        <td class="text-center align-middle"><c:out value="${item.itemseq}" /></td>
                        <td class="text-left align-middle"><c:out value="${item.itemdesc}" /></td>
                        <td class="text-left align-middle"><c:out value="${item.isaddtext}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </tags:form-row>
    <form:hidden path="osformno"/>
    <form:hidden path="qseqno"/>
    <form:hidden path="topicname"/>
    <form:hidden path="sectitleseq"/>
    <form:hidden path="sectitle"/>
    <form:hidden path="topicseq"/>
    <form:hidden path="topic"/>
    <form:hidden path="iseqno"/>
    <form:hidden path="mode"/>
</form:form>
<tags:form-note>
<tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
<tags:form-note-item>修改僅限選擇一筆。</tags:form-note-item>
</tags:form-note>
</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    // dataTable 設定
    let config = getDataTablesConfig();
    config.paging = false;
    $('#tb1').DataTable(config);
    $('#btnInsert').click(function(e){
        e.preventDefault();
        $('#mode').val('A');
        $('#eip00w520Form').attr('action', '<c:url value="/Eip00w520_optionInsertUpdate.action" />').submit();
    })
    $('#btnUpdate').click(function(e){
        e.preventDefault();
        $('#iseqno').val($("input[name^='iseqnoList']:checked").val());
        $('#mode').val('U');
        $('#eip00w520Form').attr('action', '<c:url value="/Eip00w520_optionInsertUpdate.action" />').submit();
    })
    $('#btnDelete').click(function(e){
        e.preventDefault();
        if($("input[name^='iseqnoList']:checked").length == 0) {
            showAlert("請至少選擇一項");
            return;
        }
        showConfirm('確定刪除嗎？',()=>{
            $('#eip00w520Form').attr('action', '<c:url value="/Eip00w520_optionDelete.action" />').submit();
        });
    })
    $('#btnReply').click(function(e){
        e.preventDefault();
        $('#eip00w520Form').attr('action', '<c:url value="/Eip00w520_questionList.action" />').submit();
    })

    //全選
    $("#selectAll").click(function() {
        if ($("#selectAll").is(":checked")) {
            $("input[name^='iseqnoList']").each(function () {
                $(this).prop("checked", true);
            });
        } else {
            $("input[name^='iseqnoList']").each(function () {
                $(this).prop("checked", false);
            });
        }
        changeButtonStatus();
    });

    $("input[name^='iseqnoList']").click(function() {
        var numberOfChecked = $("input[name^='iseqnoList']:checked").length;
        var allcheckbox = $("input[name^='iseqnoList']").length;
        if (numberOfChecked === allcheckbox) {
            $("#selectAll").prop("checked",true);
        }else {
            $("#selectAll").prop("checked",false);
        }
        changeButtonStatus();
        if ($(this).is(':checked')) {
            $('#itemseq').val($(this).parent().parent().children().eq(1).text());
            $('#itemdesc').val($(this).parent().parent().children().eq(2).text());
            let isaddtext = $(this).parent().parent().children().eq(3).text();
            if (isaddtext === 'Y') {
                $("input[name$='isaddtext'][value='Y']").prop("checked",true);
            } else if (isaddtext === 'N') {
                $("input[name$='isaddtext'][value='N']").prop("checked", true);
            }
        } else {
            $('#itemseq').val('');
            $('#itemdesc').val('');
            $("input[name$='isaddtext']").prop("checked",false);
        }

    });

    // $('.checktr').click(function() {
    //     $('#iseqno').val($(this).children('td').eq(0).children().val());
    //     $('#itemseq').val($(this).children('td').eq(1).text());
    //     $('#itemdesc').val($(this).children('td').eq(2).text());
    //     let isaddtext = $(this).children('td').eq(3).text();
    //     if (isaddtext === 'Y') {
    //         $("input[name$='isaddtext'][value='Y']").prop("checked",true);
    //     } else if (isaddtext === 'N') {
    //         $("input[name$='isaddtext'][value='N']").prop("checked", true);
    //     }
    // });

    function changeButtonStatus() {
        let checkedNumber = $("input[name^='iseqnoList']:checked").length;
        // alert(checkedNumber);
        if(checkedNumber === 1) {
            $('#btnUpdate').prop('disabled', false);
            $('#btnDelete').prop('disabled', false);
        } else if (checkedNumber > 1) {
            $('#btnUpdate').prop('disabled', true);
            $('#btnDelete').prop('disabled', false);
        } else {
            $('#btnUpdate').prop('disabled',true);
            $('#btnDelete').prop('disabled',true);
        }
    }

    // 初始化
    $('#btnUpdate').prop('disabled',true);
    $('#btnDelete').prop('disabled',true);
    changeButtonStatus();
})
</script>
</jsp:attribute>
</tags:layout>