<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w420Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        .btn {
            vertical-align: baseline;
        }
    </style>
</jsp:attribute>
<jsp:attribute name="buttons">
<!-- 明細頁 -->
    <tags:button id="btnSave">
        儲存<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnReply">
        返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

    <jsp:attribute name="contents">
    <tags:fieldset legend="時數認證">
    <form:form id="eip00w420Form" name="eip00w420Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="col-md-3">
                <form:label cssClass="col-form-label" path="cKeyword">關鍵字：</form:label>
                <form:input path="cKeyword" cssClass="form-control d-inline-block" size="16" maxlength="50"/>
            </div>
            <div class="col-md-1">
                <tags:button id="btnSearch">
                    查詢<i class="fas fa-search"></i>
                </tags:button>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="table-responsive">
                <table class="table" id="tb1">
                    <thead data-orderable="true">
                        <tr>
                            <th class="text-center align-middle" rowspan="2">項次</th>
                            <th class="text-center align-middle" rowspan="2">姓名</th>
                            <th class="text-center align-middle" rowspan="2">聯絡電話</th>
                            <th class="text-center align-middle" rowspan="2">E-mail</th>
                            <th class="text-center align-middle" rowspan="2">認證時數</th>
                            <th class="text-center align-middle" colspan="${caseData.certihoursList.size()+1}">出勤狀況</th>
                        </tr>
                        <tr>
                            <c:forEach items="${caseData.certihoursList}" var="item" varStatus="status">
                                <th class="text-center align-middle"><input id="selectAllCol${status.index+1}" type="checkbox"/><label class="d-inline" for="selectAllCol${status.index+1}">全選</label></th>
                            </c:forEach>
                                <th class="text-center align-middle"><input type="checkbox" id="selectAll"/><label class="d-inline" for="selectAll">全選</label></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.cerList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle">
                                    <c:out value="${status.index+1}" />
                                    <form:hidden path="cerList[${status.index}].seqno"/>
                                </td>
                                <td class="text-left align-middle"><c:out value="${item.regisname}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.regisphone}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.regisemail}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.certihours}" /></td>
                                <c:forEach items="${caseData.certihoursList}" var="inneritem" varStatus="innerstatus">
                                    <td class="text-center align-middle tdcheck">
                                        <form:checkbox path="cerList[${status.index}].checkList" value="${inneritem}"/>
                                        <label class="d-inline"><c:out value="${inneritem}"/></label>
                                    </td>
                                </c:forEach>
                                <td class="text-center align-middle"><form:checkbox path="cerList[${status.index}].selAllmk" value="Y"/>
                                    <label class="d-inline" for="cerList${status.index}.selAllmk1">全部</label>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
        <form:hidden path="orformno"/>
        <form:hidden path="mode"/>
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

    $('#btnSave').click(function(e){
        e.preventDefault();
        $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_certifiedConfirm.action" />').submit();
    })
    $('#btnReply').click(function(e){
        e.preventDefault();
        $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_enter.action" />').submit();
    })
    $('#btnSearch').click(function(e){
        e.preventDefault();
        $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_certified.action" />').submit();
    })

    $("[id^='selectAllCol']").click(function() {
        let last = $(this).attr('id').charAt($(this).attr('id').length - 1);
        let isChecked = $(this).is(":checked");
        let selector = '[id$="checkList' + last + '"]';
        if (isChecked) {
            $(selector).prop("checked", true);
        } else {
            $(selector).prop("checked", false);
        }
    });

    $('#selectAll').click(function(){
        let isChecked = $(this).is(":checked");
        let selector = '[id$="selAllmk1"]';
        if (isChecked) {
            $(selector).prop("checked", true);
        } else {
            $(selector).prop("checked", false);
        }
    });

    $("[id*='checkList']").click(function(){
        let last = $(this).attr('id').charAt($(this).attr('id').length - 1);
        let selector = "[id^='selectAllCol" + last + "']"
        if ($('[id$="checkList' + last + '"]:checked').length === $('[id$="checkList' + last + '"]').length) {
            $(selector).prop("checked", true);
        } else {
            $(selector).prop("checked", false);
        }
    });

    // 初始化
    $('.tdcheck label').each(function (){
        let str = $(this).text();
        let str1 = str.charAt(0) === 'P' ? '實體' : '數位';
        let str2 = str.charAt(1) === 'M' ? '上午' : '下午';
        let str3 = str.substring(2);
        $(this).text(str3 + '時' + '(' + str1 + str2 + ')');
        $(this).attr('for',$(this).prev().prev().attr("id"));
    });

    $("[id^='selectAllCol']").each(function (i,e) {
        if ($('[id$="checkList' + (i+1) + '"]:checked').length === $('[id$="checkList' + (i+1) + '"]').length) {
            let selector = "[id^='selectAllCol" + (i+1) + "']"
            $(selector).prop("checked", true);
        }
    });

})
</script>
</jsp:attribute>
</tags:layout>