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
    <tags:fieldset legend="報名審核">
    <form:form id="eip00w420Form" name="eip00w420Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="col-lg-12 col-xl-11">
                <form:label cssClass="col-form-label" path="vKeyword">關鍵字：</form:label>
                <form:input path="vKeyword" cssClass="form-control d-inline-block" size="16" maxlength="50"/>
                <form:label cssClass="col-form-label" path="vStatus">狀態：</form:label>
                <form:select path="vStatus" cssClass="form-control d-inline-block" multiple="false">
                    <form:option value="">請選擇</form:option>
                    <form:options items="${caseData.regstatusCombobox}" />
                </form:select>
            </div>
            <div class="col-lg-12 col-xl-1">
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
                            <th class="text-center align-middle" rowspan="2">公司全銜</th>
                            <th class="text-center align-middle" rowspan="2">報名資格</th>
                            <th class="text-center align-middle" colspan="2">是否通過審核</th>
                            <th class="text-center align-middle" colspan="3">是否繳費</th>
                            <th class="text-center align-middle" colspan="2">是否通知報名者</th>
                        </tr>
                        <tr>
                            <th class="text-center align-middle"><input type="checkbox" id="selectAll1"/><label class="d-inline" for="selectAll1">全選</label></th>
                            <th class="text-center align-middle"><input type="checkbox" id="selectAll2"/><label class="d-inline" for="selectAll2">全選</label></th>
                            <th class="text-center align-middle"><input type="checkbox" id="selectAll3"/><label class="d-inline" for="selectAll3">全選</label></th>
                            <th class="text-center align-middle"><input type="checkbox" id="selectAll4"/><label class="d-inline" for="selectAll4">全選</label></th>
                            <th class="text-center align-middle"><input type="checkbox" id="selectAll5"/><label class="d-inline" for="selectAll5">全選</label></th>
                            <th class="text-center align-middle"><input type="checkbox" id="selectAll6"/><label class="d-inline" for="selectAll6">全選</label></th>
                            <th class="text-center align-middle"><input type="checkbox" id="selectAll7"/><label class="d-inline" for="selectAll7">全選</label></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.verList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle">
                                    <c:out value="${status.index+1}" />
                                    <form:hidden path="verList[${status.index}].seqno"/>
                                </td>
                                <td class="text-left align-middle"><c:out value="${item.regisname}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.regisphone}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.regisemail}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.company}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.dept}" /></td>
                                <c:if test="${item.isPass eq 'D'}">
                                    <td class="text-center align-middle">-</td>
                                    <td class="text-center align-middle">-</td>
                                    <td class="text-center align-middle">-</td>
                                    <td class="text-center align-middle">-</td>
                                    <td class="text-center align-middle">-</td>
                                    <td class="text-center align-middle">-</td>
                                    <td class="text-center align-middle">-</td>
                                </c:if>
                                <c:if test="${item.isPass ne 'D'}">
                                    <td class="text-center align-middle">
                                        <label class="mb-0">
                                            <form:radiobutton path="verList[${status.index}].isPass" data-ispass="${item.isPass}" value="Y" cssClass="mr-1"/>
                                            <span class="font-weight-bold mr-2">是</span>
                                        </label>
                                    </td>
                                    <td class="text-center align-middle">
                                        <label class="mb-0">
                                            <form:radiobutton path="verList[${status.index}].isPass" data-ispass="${item.isPass}" value="N" cssClass="mr-1"/>
                                            <span class="font-weight-bold mr-2">否</span>
                                        </label>
                                    </td>
                                    <td class="text-center align-middle">
                                        <label class="mb-0">
                                            <form:radiobutton path="verList[${status.index}].isPay" data-ispass="${item.isPass}" value="Y" cssClass="mr-1"/>
                                            <span class="font-weight-bold mr-2">是</span>
                                        </label>
                                    </td>
                                    <td class="text-center align-middle">
                                        <label class="mb-0">
                                            <form:radiobutton path="verList[${status.index}].isPay" data-ispass="${item.isPass}" value="N" cssClass="mr-1"/>
                                            <span class="font-weight-bold mr-2">否</span>
                                        </label>
                                    </td>
                                    <td class="text-center align-middle">
                                        <label class="mb-0">
                                            <form:radiobutton path="verList[${status.index}].isPay" data-ispass="${item.isPass}" value="F" cssClass="mr-1"/>
                                            <span class="font-weight-bold mr-2">免費</span>
                                        </label>
                                    </td>
                                    <td class="text-center align-middle">
                                        <label class="mb-0">
                                            <form:radiobutton path="verList[${status.index}].isNotify" data-ispass="${item.isPass}" value="Y" cssClass="mr-1"/>
                                            <span class="font-weight-bold mr-2">是</span>
                                        </label>
                                    </td>
                                    <td class="text-center align-middle">
                                        <label class="mb-0">
                                            <form:radiobutton path="verList[${status.index}].isNotify" data-ispass="${item.isPass}" value="N" cssClass="mr-1"/>
                                            <span class="font-weight-bold mr-2">否</span>
                                        </label>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
        <form:hidden path="orformno"/>
        <form:hidden path="mode"/>
    </form:form>
    <tags:form-note>
    <tags:form-note-item>倘報名者取消報名，不開放操作選取。</tags:form-note-item>
    </tags:form-note>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    // dataTable 設定
    let config = getDataTablesConfig();
    config.ordering = false;
    config.pageLength = 20;
    $('#tb1').DataTable(config);

    $('#btnSave').click(function(e){
        e.preventDefault();
        $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_verifyConfirm.action" />').submit();
    })
    $('#btnReply').click(function(e){
        e.preventDefault();
        $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_enter.action" />').submit();
    })
    $('#btnSearch').click(function(e){
        e.preventDefault();
        $('#mode').val('S');
        $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_verify.action" />').submit();
    })

    //全選
    $("[id^='selectAll']").click(function() {
        let last = $(this).attr('id').charAt($(this).attr('id').length - 1);
        let isChecked = $(this).is(":checked");
        if (isChecked) {
            if (last === '1') {
                $("input[name$='isPass'][value='Y']").prop("checked",true);
            } else if (last === '2') {
                $("input[name$='isPass'][value='N']").prop("checked",true);
            } else if (last === '3') {
                $("input[name$='isPay'][value='Y']").prop("checked",true);
            } else if (last === '4') {
                $("input[name$='isPay'][value='N']").prop("checked",true);
            } else if (last === '5') {
                $("input[name$='isPay'][value='F']").prop("checked",true);
            } else if (last === '6') {
                $("input[name$='isNotify'][value='Y']").prop("checked",true);
            } else if (last === '7') {
                $("input[name$='isNotify'][value='N']").prop("checked",true);
            }
        }

    });

    //初始化
    if ($("input[name$='isPass'][value='Y']:checked").length === $("input[name$='isPass'][value='Y']").length) {
        $("#selectAll1").prop("checked",true);
    } else if ($("input[name$='isPass'][value='N']:checked").length === $("input[name$='isPass'][value='N']").length){
        $("#selectAll2").prop("checked",true);
    }
    if ($("input[name$='isPay'][value='Y']:checked").length === $("input[name$='isPay'][value='Y']").length){
        $("#selectAll3").prop("checked",true);
    } else if ($("input[name$='isPay'][value='N']:checked").length === $("input[name$='isPay'][value='N']").length){
        $("#selectAll4").prop("checked",true);
    } else if ($("input[name$='isPay'][value='F']:checked").length === $("input[name$='isPay'][value='F']").length){
        $("#selectAll5").prop("checked",true);
    }
    if ($("input[name$='isNotify'][value='Y']:checked").length === $("input[name$='isNotify'][value='Y']").length){
        $("#selectAll6").prop("checked",true);
    } else if ($("input[name$='isNotify'][value='N']:checked").length === $("input[name$='isNotify'][value='N']").length){
        $("#selectAll7").prop("checked",true);
    }

    if($("td").length === 1) {
        $("[id^='selectAll']").prop("checked",false);
    }

})
</script>
</jsp:attribute>
</tags:layout>