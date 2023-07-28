<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 重要列管事項_填報辦理進度 --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip03w020Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="buttons">
        <tags:button id="btnQuery">查詢<i class="fas fa-search"></i></tags:button>
        <tags:button id="btnClear">清除<i class="fas fa-eraser"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <tags:fieldset>
            <form:form id="eip03w020Form" name="eip03w020Form" modelAttribute="${caseKey}" method="POST">
                <tags:form-row>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label" path="trkID">列管編號：</form:label>
                        <form:input path="trkID" cssClass="form-control " size="9"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-4 d-flex">
                        <form:label cssClass="col-form-label" path="trkCont">內容：</form:label>
                        <form:input path="trkCont" cssClass="form-control " size="50"  maxlength="100"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-4 d-flex">
                        <form:label cssClass="col-form-label" path="allStDt">全案列管日期：</form:label>
                        <form:input path="allStDtSt" cssClass="form-control num_only" size="13" placeholder="開始日期(民國年)" maxlength="7"/>
                        <span class="input-group-text px-1">~</span>
                        <form:input path="allStDtEnd" cssClass="form-control num_only" size="13" placeholder="結束日期(民國年)" maxlength="7"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label" path="trkSts">列管狀態：</form:label>
                        <form:select path="trkSts" cssClass="form-control selector">
                            <form:option name="" value="">全部</form:option>
                            <form:options items="${caseData.trkStsCombobox}" />
                        </form:select>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label" path="prcSts">處理狀態：</form:label>
                        <form:select path="prcSts" cssClass="form-control selector">
                            <form:option name="" value="">全部</form:option>
                            <form:options items="${caseData.prcStsCombobox}" />
                        </form:select>
                    </div>
                </tags:form-row>
                <form:hidden path="selectedTrkID"/>
                <tags:form-note>
                    <tags:form-note-item>可擇一輸入再點選[查詢]</tags:form-note-item>
                </tags:form-note>
            </form:form>
        </tags:fieldset>
        <fieldset id="resultBox">
            <legend>查詢結果</legend>
                <div class="table-responsive">
                    <table id="dataTable" class="table">
                        <thead data-orderable="true">
                        <tr>
                            <th style="width: 10%" >列管編號</th>
                            <th style="width: 30%" >內容</th>
                            <th style="width: 10%" >全案列管日期</th>
                            <th style="width: 8%" >列管狀態</th>
                            <th style="width: 10%" >列管對象數</th>
                            <th style="width: 8%" >待處理數</th>
                            <th style="width: 8%" >待解列數</th>
                            <th style="width: 8%" >已解列數</th>
                            <th data-orderable="false">操作區</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${caseData.keepTrkMstList}" var="item" varStatus="status">
                                <tr class="text-left">
                                    <td>
                                        <c:out value='${item.trkID}'/>
                                    </td>
                                    <td>
                                        <c:out value='${item.trkCont}' escapeXml="false"/>
                                    </td>
                                    <td>
                                        <func:minguo value="${item.allStDt}"/>
                                    </td>
                                    <td>
                                        <c:out value='${item.trkSts}'/>
                                    </td>
                                    <td class="text-right">
                                        <c:out value='${item.cnt_all}'/> <%--列管對象數--%>
                                    </td>
                                    <td class="text-right">
                                        <c:choose>
                                            <c:when test="${item.nowStat == '1'}">
                                                <c:out value='【${item.cnt_doing}】' /> <%--待處理--%>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value='${item.cnt_doing}' /> <%--待處理--%>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-right">
                                        <c:choose>
                                            <c:when test="${item.nowStat == '2'}">
                                                <c:out value='【${item.cnt_wait}】' /> <%--待解列--%>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value='${item.cnt_wait}' /> <%--待解列--%>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-right">
                                        <c:choose>
                                            <c:when test="${item.nowStat == '3'}">
                                                <c:out value='【${item.cnt_done}】' />  <%--已解列--%>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value='${item.cnt_done}' />  <%--已解列--%>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center" >
                                        <tags:button id="btnDetail" class="btn btn-outline-be btn-sm btnDetail" data-selected="${item.trkID}" >明細</tags:button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
        </fieldset>
    </jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function(){
        // table render config
        var columnDefs = [ {
            "orderable" : true,
        }];
        $("#dataTable").DataTable(getDataTablesConfig(columnDefs));

        //btnClear 清除
        $('#btnClear').click(function(e) {
            e.preventDefault();
            $('#trkID').val('');
            $('#trkCont').val('');
            $('#allStDtSt').val('');
            $('#allStDtEnd').val("");
            $('#trkSts').val("");
            $('#prcSts').val("");
        });

        //btnQuery
        $('#btnQuery').click(function(e) {
            e.preventDefault();
            $('#eip03w020Form').attr('action', 'Eip03w020_queryKeepTrk.action').submit();
        });

        $('#dataTable').on('click', '.btnDetail', function (e) {
            e.preventDefault()
            $('input[id="selectedTrkID"]').val($(this).data('selected'));
            $('#eip03w020Form').attr('action', 'Eip03w020_queryKeepTrkForDetail.action').submit();
        });

        //btnApplyForm
        // $('.btnApplyForm').click(function (e){
        //     e.preventDefault();
        //     $('input[id="meetingId"]').val($(this).data('meetingid'));
        //     $('#eip06w011Form').attr('action', 'Eip06w010_getApplyForm.action').submit();
        // })

        // $('#dataTable').on('click', '.btnApplyForm', function (e) {
        //         e.preventDefault();
        //         $('input[id="meetingId"]').val($(this).data('meetingid'));
        //         $('#eip06w011Form').attr('action', 'Eip06w010_getApplyForm.action').submit();
        // });

        //btnEdit
        // $('.btnEdit').click(function (e){
        //     e.preventDefault();
        //     $('input[id="meetingId"]').val($(this).data('meetingid'));
        //     $('input[id="operation"]').val("update");
        //     alert($(this).data('meetingid'));
        //     $('#eip06w011Form').attr('action', 'Eip06w010_queryMeetingForUpdate.action').submit();
        // })

        // $('#dataTable').on('click', '.btnEdit', function (e) {
        //         e.preventDefault();
        //         $('input[id="meetingId"]').val($(this).data('meetingid'));
        //         $('input[id="operation"]').val("update");
        //         $('#eip06w011Form').attr('action', 'Eip06w010_queryMeetingForUpdate.action').submit();
        // });

        //btnEdit
        // $('.btnDetail').click(function (e){
        //     e.preventDefault();
        //     $('input[id="meetingId"]').val($(this).data('meetingid'));
        //     $('input[id="operation"]').val("update");
        //     $('#eip06w011Form').attr('action', 'Eip06w010_queryMeetingForDetail.action').submit();
        // })

        //btnCancel
        <%--$('.btnCancel').click(function (e){--%>
        <%--    e.preventDefault();--%>
        <%--    // showAlert("確定要刪除此會議?")--%>
        <%--    $('input[id="meetingId"]').val($(this).data('meetingid'));--%>
        <%--    $('input[id="operation"]').val("update");--%>
        <%--    showConfirm("確定要刪除此會議?", function() {--%>
        <%--        $('#eip06w011Form')--%>
        <%--            .attr('action', '<c:url value="/Eip06w010_deleteMeeting.action" />')--%>
        <%--            .submit();--%>
        <%--    });--%>
        <%--})--%>
        <%--$('#dataTable').on('click', '.btnCancel', function (e) {--%>
        <%--        e.preventDefault();--%>
        <%--        // showAlert("確定要刪除此會議?")--%>
        <%--        $('input[id="meetingId"]').val($(this).data('meetingid'));--%>
        <%--        $('input[id="operation"]').val("update");--%>
        <%--        showConfirm("確定要刪除此會議?", function() {--%>
        <%--            $('#eip06w011Form')--%>
        <%--                .attr('action', '<c:url value="/Eip06w010_deleteMeeting.action" />')--%>
        <%--                .submit();--%>
        <%--        });--%>
        <%--});--%>

    })
</script>
</jsp:attribute>
</tags:layout>