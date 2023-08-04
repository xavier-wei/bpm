<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 各類參數維護作業：決行層級參數檔 --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip06w010Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="buttons">
        <tags:button id="btnQuery">查詢<i class="fas fa-search"></i></tags:button>
        <tags:button id="btnClear">清除<i class="fas fa-eraser"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <tags:fieldset>
            <form:form id="eip06w010Form" name="eip06w010Form" modelAttribute="${caseKey}" method="POST">
                <tags:form-row>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label" path="meetingName">會議名稱：</form:label>
                        <form:input path="meetingName" cssClass="form-control" size="21"/>
                    </div>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label" path="chairman">主持人：</form:label>
                        <form:input path="chairman" cssClass="form-control" size="9"/>
                    </div>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label" path="organizerId">申請人：</form:label>
                        <form:input path="organizerId" cssClass="form-control" size="9"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-4 d-flex">
                        <form:label cssClass="col-form-label" path="meetingdtBegin">會議日期：</form:label>
                        <form:input path="meetingdtBegin" cssClass="form-control num_only" size="13" placeholder="開始日期(民國年)" maxlength="7"/>
                        <span class="input-group-text px-1">~</span>
                        <form:input path="meetingdtEnd" cssClass="form-control num_only" size="13" placeholder="結束日期(民國年)" maxlength="7"/>
                    </div>
                    <div class="col-md-4 d-flex">
                        <form:label cssClass="col-form-label" path="meetingBegin">會議時間：</form:label>
                        <form:select path="meetingBegin" cssClass="form-control selector">
                            <form:option value="">開始時間</form:option>
                        </form:select>
                        <span class="input-group-text px-1">~</span>
                        <form:select path="meetingEnd" cssClass="form-control selector">
                            <form:option value="">結束時間</form:option>
                        </form:select>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label" path="roomId">場地：</form:label>
                        <form:select path="roomId" cssClass="form-control selector">
                            <form:option name="" value="">請選擇場地</form:option>
                            <c:forEach items="${caseData.roomIdList}" var="item">
                                <form:option name="${item.itemId }" value="${item.itemId }">${item.itemId } - ${item.itemName }</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
                <form:hidden path="admin" value="false"/>
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
                            <th style="width: 2%" >序號</th>
                            <th style="width: 15%" >會議名稱</th>
                            <th style="width: 10%" >主持人</th>
                            <th style="width: 8%" >會議日期</th>
                            <th style="width: 10%" >會議時間</th>
                            <th style="width: 8%" >場地</th>
                            <th style="width: 12%" >場地名稱</th>
                            <th style="width: 8%" >申請人</th>
                            <th data-orderable="false"></th>
<%--                            <th style="display: none"></th>--%>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${caseData.resultList}" var="item" varStatus="status">
                                <tr class="text-left">
                                    <td class="text-center">
                                        <c:out value='${status.count}'/>
                                    </td>
                                    <td>
                                        <c:out value='${item.meetingName}'/>
                                    </td>
                                    <td>
                                        <c:out value='${item.chairman}'/>
                                    </td>
                                    <td>
                                        <func:minguo value="${item.meetingdt}"/>
                                    </td>
                                    <td>
                                        <c:out value='${item.meetingBegin} ~ ${item.meetingEnd}'/>
                                    </td>
                                    <td>
                                        <c:out value='${item.roomId}'/>
                                    </td>
                                    <td>
                                        <c:out value='${item.itemName}' />
                                    </td>
                                    <td>
                                        <c:out value='${item.organizerId}' />
                                    </td>
                                    <td class="text-left" >
                                        <c:choose>
<%--                                            無訂餐隱藏--%>
                                            <c:when test="${item.orderFood == true}">
                                                <tags:button id="btnApplyForm" class="btn btn-outline-be btn-sm btnApplyForm" data-meetingId="${item.meetingId}"  >申請書</tags:button>
                                            </c:when>
                                        </c:choose>
                                        <c:choose>
<%--                                            申請人 || 管理者 才有編輯權限--%>
                                            <c:when test="${item.editable == true}">
                                                <tags:button id="btnEdit" class="btn btn-outline-be btn-sm btnEdit" data-meetingid="${item.meetingId}"  >編輯</tags:button>
                                                <tags:button id="btnCancel" class="btn btn-outline-be btn-sm btnCancel" data-meetingid="${item.meetingId}"  >取消</tags:button>
                                            </c:when>
                                            <c:otherwise>
                                                <tags:button id="btnDetail" class="btn btn-outline-be btn-sm btnDetail" data-meetingid="${item.meetingId}"  >明細</tags:button>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
<%--                <form:hidden path="seq"/>--%>
            <form:form id="eip06w011Form" name="eip06w011Form" modelAttribute="${caseKey}" method="POST">
                <form:hidden path="meetingId"/>
                <form:hidden path="operation"/>
                <form:hidden path="admin" value="false"/>
            </form:form >
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

        //會議時間
        const timeStart = $('#meetingBegin');
        const timeEnd = $('#meetingEnd');
        for (let i = 0; i < 24; i++) {
            for (let j = 0; j < 60; j += 30) {
                const time = i.toString().padStart(2,"0")+j.toString().padStart(2,"0");
                timeStart.append('<option value="' + time + '">' + time + '</option>');
                timeEnd.append('<option value="' + time + '">' + time + '</option>');
            }
        }

        //btnQuery
        $('#btnQuery').click(function(e) {
            e.preventDefault();
            $('#eip06w010Form').attr('action', 'Eip06w010_queryAllMeeting.action').submit();
        });

        //btnClear 清除
        $('#btnClear').click(function(e) {
            e.preventDefault();
            $('#meetingName').val('');
            $('#chairman').val('');
            $('#organizerId').val('');
            $('#meetingdtBegin').val("");
            $('#meetingdtEnd').val("");
            $('#meetingBegin').val("");
            $('#meetingEnd').val("");
            $('#roomId').val("");
        });

        //btnApplyForm
        // $('.btnApplyForm').click(function (e){
        //     e.preventDefault();
        //     $('input[id="meetingId"]').val($(this).data('meetingid'));
        //     $('#eip06w011Form').attr('action', 'Eip06w010_getApplyForm.action').submit();
        // })
        $('#dataTable').on('click', '.btnApplyForm', function (e) {
                e.preventDefault();
                $('input[id="meetingId"]').val($(this).data('meetingid'));
                $('#eip06w011Form').attr('action', 'Eip06w010_getApplyForm.action').submit();
        });

        //btnEdit
        // $('.btnEdit').click(function (e){
        //     e.preventDefault();
        //     $('input[id="meetingId"]').val($(this).data('meetingid'));
        //     $('input[id="operation"]').val("update");
        //     alert($(this).data('meetingid'));
        //     $('#eip06w011Form').attr('action', 'Eip06w010_queryMeetingForUpdate.action').submit();
        // })
        $('#dataTable').on('click', '.btnEdit', function (e) {
                e.preventDefault();
                $('input[id="meetingId"]').val($(this).data('meetingid'));
                $('input[id="operation"]').val("update");
                $('#eip06w011Form').attr('action', 'Eip06w010_queryMeetingForUpdate.action').submit();
        });

        //btnEdit
        // $('.btnDetail').click(function (e){
        //     e.preventDefault();
        //     $('input[id="meetingId"]').val($(this).data('meetingid'));
        //     $('input[id="operation"]').val("update");
        //     $('#eip06w011Form').attr('action', 'Eip06w010_queryMeetingForDetail.action').submit();
        // })
        $('#dataTable').on('click', '.btnDetail', function (e) {
                e.preventDefault();
                $('input[id="meetingId"]').val($(this).data('meetingid'));
                $('input[id="operation"]').val("update");
                $('#eip06w011Form').attr('action', 'Eip06w010_queryMeetingForDetail.action').submit();
        });

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
        $('#dataTable').on('click', '.btnCancel', function (e) {
                e.preventDefault();
                // showAlert("確定要刪除此會議?")
                $('input[id="meetingId"]').val($(this).data('meetingid'));
                $('input[id="operation"]').val("update");
                showConfirm("確定要刪除此會議?", function() {
                    $('#eip06w011Form')
                        .attr('action', '<c:url value="/Eip06w010_deleteMeeting.action" />')
                        .submit();
                });
        });

    })
</script>
</jsp:attribute>
</tags:layout>