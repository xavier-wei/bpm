<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 會議室管理_部分啟用查詢作業 --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip06w060Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        .button-spacing {
            margin-right: -10px;
        }
    </style>
</jsp:attribute>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
    <tags:button id="btnInsert">
        新增<i class="fas fa-user-plus"></i>
    </tags:button>
    <tags:button id="btnReply">
        返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="查詢條件">
    <tags:form-row>
        <tags:text-item label="編號" ><c:out value="${caseData.itemId}"/></tags:text-item>
        <tags:text-item label="名稱"><c:out value="${caseData.itemName}"/></tags:text-item>
    </tags:form-row>
    <form:form id="eip06w060Form" name="eip06w060Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="table-responsive">
                <table class="table" id="tb1">
                    <thead data-orderable="true">
                    <tr>
                        <th style="width: 5%">序號</th>
                        <th style="width: 15%">啟用日期</th>
                        <th style="width: 15%">開始時間</th>
                        <th style="width: 15%">結束時間</th>
                        <th style="width: 15%">操作區</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.roomIsableCaseList}" var="item" varStatus="status" >
                            <tr>
                                <td class="text-center align-middle">${status.count}</td>
                                <td class="text-left align-middle"><c:out value="${item.isableDate}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.meetingBegin}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.meetingEnd}" /></td>
                                <td data-itemid="${item.itemId}"
                                    data-itemno="${item.itemNo}" class="text-center align-middle">
                                    <tags:button name="deleteButton" onclick="deleteItem(${item.itemNo})">刪除</tags:button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
        <form:hidden path="itemId"/>
        <form:hidden path="itemNo"/>
        <form:hidden path="itemName"/>
    </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">

<script>
$(function(){
    // dataTable 設定
    let config = getDataTablesConfig();
    $('#tb1').DataTable(config);
    $('#btnInsert').click(function(e){
        e.preventDefault();
        $('#eip06w060Form').attr('action', '<c:url value="/Eip06w060_inster.action" />').submit();
    })

    $('#btnReply').click(function(e){
        e.preventDefault();
        $('#eip06w060Form').attr('action', '<c:url value="/Eip06w060_enter.action" />').submit();
    })

    function deleteItem(itemno){
        showConfirm('確定刪除嗎?',()=>{
            $('#itemNo').val(itemno);
            $('#eip06w060Form').attr('action', '<c:url value="/Eip06w060_deleteSingle.action" />').submit();
        });
    }

    $('#tb1 button[name=deleteButton]').click(function (e) {
        e.preventDefault();
        var itemno =  $(this).parent().data('itemno');
        deleteItem(itemno);
    });
})
</script>
</jsp:attribute>
</tags:layout>