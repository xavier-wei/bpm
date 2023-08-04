<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 會議室管理_主頁 --%>
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
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="查詢條件">
    <form:form id="eip06w060Form" name="eip06w060Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="table-responsive">
                <table class="table" id="tb1">
                    <thead data-orderable="true">
                    <tr>
<%--                        <th style="width: 15%">類別</th>--%>
                        <th style="width: 15%">編號</th>
                        <th style="width: 15%">名稱</th>
                        <th style="width: 15%">數量</th>
                        <th style="width: 20%">目前狀態</th>
                        <th style="width: 20%">操作區</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.meetingCodeCaseList}" var="item" varStatus="status">
                            <tr>
<%--                                <td class="text-center align-middle">--%>
<%--                                      <c:choose>--%>
<%--                                        <c:when test="${item.itemTyp eq 'F'}">會議室</c:when>--%>
<%--                                        <c:when test="${item.itemTyp eq 'FX'}">會議室</c:when>--%>
<%--                                      </c:choose>--%>
<%--                                </td>--%>
                                <td class="text-center align-middle"><c:out value="${item.itemId}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.itemName}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.qty}" /></td>
                                <td class="text-center align-middle">
                                    <c:choose>
                                        <c:when test="${item.itemTyp eq 'F'}">啟用</c:when>
                                        <c:when test="${item.itemTyp eq 'FX'}">禁用</c:when>
                                    </c:choose>
<%--                                    <input type="radio" name="itemTyp${status.index}" value="F" id="itemTypF${status.index}" <c:if test="${item.itemTyp == 'F'}">checked</c:if> class="button-spacing " onclick="return false;" readonly>--%>
<%--                                    <label for="itemTypF${status.index}">啟用</label>--%>
<%--                                    <label >   </label>--%>
<%--                                    <input type="radio" name="itemTyp${status.index}" value="FX" id="itemTypFX${status.index}" <c:if test="${item.itemTyp == 'FX'}">checked</c:if> class="button-spacing " onclick="return false;" readonly>--%>
<%--                                    <label for="itemTypFX${status.index}">禁用</label>--%>
                                </td>
                                <td data-itemid="${item.itemId}"
                                    data-itemname="${item.itemName}"
                                    class="text-left align-middle">
                                    <tags:button name="roomisable">
                                      <c:choose>
                                        <c:when test="${item.itemTyp == 'F'}">禁用</c:when>
                                        <c:when test="${item.itemTyp == 'FX'}">啟用</c:when>
                                      </c:choose>
                                    </tags:button>
                                    <c:if test="${item.itemTyp == 'FX'}">
                                      <tags:button name="partisable">部分啟用</tags:button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
        <form:hidden path="itemId"/>
        <form:hidden path="itemName"/>
        <tags:form-note>
            <tags:form-note-item>「目前狀態」為「禁用」時，點選啟用，將清空部分啟用內容。</tags:form-note-item>
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

    $('#tb1 button[name=roomisable]').click(function(e){
        e.preventDefault();
        $('#itemId').val($(this).parent().data('itemid'));
        $('#eip06w060Form').attr('action', '<c:url value="/Eip06w060_roomIsable.action" />').submit();
    });

    $('#tb1 button[name=partisable]').click(function(e){
        e.preventDefault();
        $('#itemId').val($(this).parent().data('itemid'));
        $('#itemName').val($(this).parent().data('itemname'));
        $('#eip06w060Form').attr('action', '<c:url value="/Eip06w060_partisable.action" />').submit();
    });
})
</script>
</jsp:attribute>
</tags:layout>