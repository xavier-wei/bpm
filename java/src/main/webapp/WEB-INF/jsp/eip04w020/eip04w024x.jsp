<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip04w020Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
    </style>
</jsp:attribute>
<jsp:attribute name="buttons">
<tags:button id="btnReply">
    返回<i class="fas fa-reply"></i>
</tags:button>
</jsp:attribute>
    <jsp:attribute name="contents">
    <tags:fieldset legend="修改歷程">
    <form:form id="eip04w020Form" name="eip04w020Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="table-responsive">
                <table class="table" id="tb1">
                    <thead data-orderable="true">
                    <tr>
                        <th class="text-center align-middle">項次</th>
                        <th class="text-center align-middle">異動日期時間</th>
                        <th class="text-center align-middle">變更動作</th>
                        <th class="text-center align-middle">操作者</th>
                        <th class="text-center align-middle">操作者當時所屬部門</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${caseData.hisList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle"><c:out value="${status.index+1}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.uppdt}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.chgtype eq 'U' ? '更新' : '新增'}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.operator}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.operatorDept}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
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
    $('#btnReply').click(function(e){
        e.preventDefault();
        $('#eip04w020Form').attr('action', '<c:url value="/Eip04w020_enter.action" />').submit();
    })
})
</script>
</jsp:attribute>
</tags:layout>