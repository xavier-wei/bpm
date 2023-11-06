<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip06w050Controller).CASE_KEY" />
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
        <tags:button id="btnSearch">
    	查詢<i class="fas fa-search"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="查詢條件">
    <form:form id="eip06w050Form" name="eip06w050Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <form:label cssClass="col-form-label " path="itemTyp">類別：</form:label>
            <form:select path="itemTyp" cssClass="form-control selector">
                <form:option value="">請選擇</form:option>
                <form:options items="${caseData.itemTypMap}"/>
            </form:select>
        </tags:form-row>

        <tags:form-row>
            <div class="table-responsive">
                <table class="table" id="tb1">
                    <thead data-orderable="true">
                    <tr>
                        <th style="width: 20%">類別</th>
                        <th style="width: 20%">編號</th>
                        <th style="width: 20%">名稱</th>
                        <th style="width: 20%">數量</th>
                        <th style="width: 20%">操作區</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.meetingCodeCaseList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle">
                                      <c:choose>
                                        <c:when test="${item.itemTyp eq 'A'}">會議餐點</c:when>
                                        <c:when test="${item.itemTyp eq 'B'}">會議物品</c:when>
                                        <c:when test="${item.itemTyp eq 'D'}">預約天數</c:when>
                                        <c:when test="${item.itemTyp eq 'F'}">會議室</c:when>
                                        <c:when test="${item.itemTyp eq 'FX'}">會議室</c:when>
                                      </c:choose>
                                </td>
                                <td class="text-center align-middle"><c:out value="${item.itemId}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.itemName}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.qty}" /></td>
                                <td data-itemid="${item.itemId}" class="text-center align-middle">
                                    <tags:button name="modifyButton">修改</tags:button>
                                    <tags:button name="deleteButton">刪除</tags:button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
        <form:hidden path="mode"/>
        <form:hidden path="itemId"/>
        <tags:form-note>
            <tags:form-note-item>參數已使用，將無法刪除。</tags:form-note-item>
            <tags:form-note-item>查詢時，類別為必填。</tags:form-note-item>
        </tags:form-note>
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
        $('#mode').val('A');
		$('#eip06w050Form').attr('action', '<c:url value="/Eip06w050_selectAction.action" />').submit();
	})

    $('#btnSearch').click(function(e){
        e.preventDefault();
        $('#mode').val('Q');
        $('#eip06w050Form').attr('action', '<c:url value="/Eip06w050_query.action" />').submit();
    })

    $('#tb1 button[name=deleteButton]').click(function(e) {
        e.preventDefault();
        var url = '<c:url value="/Eip06w050_showAlert.action" />';
        var itemId = $(this).parent().data('itemid');
        var data = { itemId: itemId };

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: url,
            data: JSON.stringify(data),
            dataType: 'json',
            timeout: 100000
        }).done(function(data) {
            if (data.itemIdIsUse === "N") {
                showAlert("使用中，無法刪除");
                $('#footer .prog.infoword').text('')
                return;
            } else {
                showConfirm('確定刪除嗎?', () => {
                    $('#itemId').val(itemId);
                    $('#eip06w050Form').attr('action', '<c:url value="/Eip06w050_delete.action" />').submit();
                });
            }
        });
    });


    $('#tb1 button[name=modifyButton]').click(function(e){
        e.preventDefault();
        $('#mode').val('U');
        $('#itemId').val($(this).parent().data('itemid'));
        $('#eip06w050Form').attr('action', '<c:url value="/Eip06w050_modify.action" />').submit();
    });
})
</script>
</jsp:attribute>
</tags:layout>