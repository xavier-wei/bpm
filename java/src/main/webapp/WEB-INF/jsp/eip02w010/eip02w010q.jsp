<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip02w010Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="heads">
        <style>
            .ads {
                display: block;
                position: absolute;
                bottom: 2px;
                left: 8rem;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="buttons">
        <tags:button id="btnQuery">
            查詢<i class="fas fa-search"></i>
        </tags:button>
        <tags:button id="btnCopy">
            批次複製電子信箱<i class="fas fa-copy"></i>
        </tags:button>
        <tags:button id="btnClear">
            清除<i class="fas fa-eraser"></i>
        </tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <form:form id="eip02w010Form" modelAttribute="${caseKey}">
            <tags:fieldset legend="查詢條件">
                    <tags:form-row>
                        <form:label cssClass="col-form-label" path="dept_id">部門：</form:label>
                        <div class="col-12 col-md">
                            <form:select path="dept_id" cssClass="form-control">
                                <option value="" selected>請選擇</option>
                                <c:forEach var="item" items="${caseData.dept}" varStatus="status">
                                    <form:option value="${item.codeno}">
                                        <c:out value="${item.codename}" />
                                    </form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="user_name">員工姓名：</form:label>
                    <div class="col-12 col-md">
                        <form:input path="user_name" cssClass="form-control" size="10" maxlength="20" />
                        <span class="ads"><a href="javascript:;">進階查詢</a></span>
                    </div>
                </tags:form-row>
                <div id="condition">
                    <tags:form-row>
                        <form:label cssClass="col-form-label" path="titlename">職稱：</form:label>
                        <div class="col-12 col-md">
                            <form:select path="titlename" cssClass="form-control">
                                <option value="" selected>請選擇</option>
                                <c:forEach var="item" items="${caseData.title}" varStatus="status">
                                    <form:option value="${item.codeno}">
                                        <c:out value="${item.codename}" />
                                    </form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </tags:form-row>
                    <tags:form-row>
                        <form:label cssClass="col-form-label" path="user_id">員工編號：</form:label>
                        <div class="col-12 col-md">
                            <form:input path="user_id" cssClass="form-control num_eng_only" size="10" maxlength="10" />
                        </div>
                    </tags:form-row>
                    <tags:form-row>
                        <form:label cssClass="col-form-label" path="user_ename">英文姓名：</form:label>
                        <div class="col-12 col-md">
                            <form:input path="user_ename" cssClass="form-control num_eng_only" size="10"
                                maxlength="30" />
                        </div>
                    </tags:form-row>
                    <tags:form-row>
                        <form:label cssClass="col-form-label" path="email">電子郵件信箱：</form:label>
                        <div class="col-12 col-md">
                            <form:input path="email" cssClass="form-control num_eng_only" size="10" maxlength="30" />
                        </div>
                    </tags:form-row>
                </div>
            </tags:fieldset>
            <div class="table-responsive mt-4">${caseData.groupStr }
                <table class="table" id="qryListTable">
                    <thead>
                        <tr>
                            <th class="text-center" data-orderable="false" >
                                <form:checkbox path="checkAll" />全選
                            </th>
                            <th class="text-center">員工編號</th>
                            <th class="text-center">姓名</th>
                            <th class="text-center">英文名</th>
                            <th class="text-center">部門</th>
                            <th class="text-center">職稱</th>
                            <th class="text-center">電子郵件信箱</th>
                            <th class="text-center">連絡電話</th>
                            <th class="text-center">分機</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.qryList}" var="item" varStatus="status">
                            <tr data-seq="${status.index + 1 }">
                                <td class="text-center">
                                    <input type="checkbox" value="${status.index + 1 }">
                                </td>
                                <td class="text-left">
                                    <c:out value="${item.user_id}" />
                                </td>
                                <td class="text-left">
                                    <c:out value="${item.user_name}" />
                                </td>
                                <td class="text-left">
                                    <c:out value="${item.ename}" />
                                </td>
                                <td class="text-left">
                                    <c:out value="${item.dept_name}" />
                                </td>
                                <td class="text-left">
                                    <c:out value="${item.titlename}" />
                                </td>
                                <td class="text-left">
                                    <c:out value="${item.email}" />
                                </td>
                                <td class="text-left">
                                    <c:out value="${item.tel1}" />
                                </td>
                                <td class="text-left">
                                    <c:out value="${item.tel2}" />
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <form:hidden path="on_off" />
        </form:form>
    </jsp:attribute>
    <jsp:attribute name="footers">
        <script type="text/javascript">
            $(function() {
                let config = getDataTablesConfig();
                var table = $("#qryListTable").DataTable(config);
                // 控制進階查詢
                var onOff = Boolean('<c:out value="${caseData.on_off }"/>');
                onOff ? $('#condition').show() : $('#condition').hide();
                $('.ads').click(function() {
                    onOff = !onOff;
                    $('#condition').slideToggle();
                });
                // checkbox 控制
                var $checkAll = $('input[name="checkAll"]');
                var $checks = $('#qryListTable > tbody input:checkbox:not(:disabled)');
                $checkAll.prop('checked', false);
                $checkAll.change(function(e) {
                    $checks.prop("checked", $(this).prop("checked"));
                });
                $checks.change(function(e) {
                    $checkAll.prop("checked", !$checks.is(':not(:checked)'));
                });
                // 查詢
                $('#btnQuery').click(function() {
                    $('#on_off').val(onOff ? ' ' : null); // 字串:true;null:false
                    $('#eip02w010Form').attr('action', '<c:url value="/Eip02w010_query.action" />')
                        .submit();
                });
                // 複製
                $('#btnCopy').click(function() {
                    var copyStr = '';
                    $('#qryListTable > tbody input:checkbox:checked').each(function(){ // tr:not(:hidden)
                        var name = $.trim($(this).closest('tr').find('td:eq(2)').text());
                        var email = $.trim($(this).closest('tr').find('td:eq(6)').text());
                        copyStr += name+'<'+email+'>;';
                    });
                    if (copyStr !== '') {
                        copyStr = copyStr.substring(0, copyStr.length - 1);
                        showMessageByMessageType('複製成功');
                        copyToClipboard(copyStr); // '${caseData.copyStr }'
                    }
                });
                // 清除
                $('#btnClear').click(function() {
//                     $('input[type="text"]').val('');
//                     $('#dept_id option:eq(0), #titlename option:eq(0)').prop('selected', true);
                    $('#eip02w010Form').attr('action', '<c:url value="/Eip02w010_enter.action" />')
                    .submit();
                });
                const unsecuredCopyToClipboard = (text) => {
                    const textArea = document.createElement("textarea");
                    textArea.value = text;
                    document.body.appendChild(textArea);
                    textArea.focus();
                    textArea.select();
                    try {
                        document.execCommand('copy')
                    } catch (err) {
                        console.error('Unable to copy to clipboard', err)
                    }
                    document.body.removeChild(textArea)
                };
                const copyToClipboard = (content) => {
                    if (window.isSecureContext && navigator.clipboard) {
                        navigator.clipboard.writeText(content); // https or localhost
                    } else {
                        unsecuredCopyToClipboard(content); // http
                    }
                };
            });
        </script>
    </jsp:attribute>
</tags:layout>