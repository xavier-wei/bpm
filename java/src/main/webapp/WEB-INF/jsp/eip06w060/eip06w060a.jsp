<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 會議室管理_新增作業 --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip06w060Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        .btn {
            border-color: #0c0c0c;
        }
    </style>
</jsp:attribute>
<jsp:attribute name="buttons">
<!-- 新增頁 -->
    <tags:button id="btnSave">
        送出<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnReply">
        返回<i class="fas fa-reply"></i>
    </tags:button>

</jsp:attribute>

<jsp:attribute name="contents">
<tags:fieldset legend="會議室禁用">
<form:form id="eip06w060Form" modelAttribute="${caseKey}">
                 <tags:form-row>
                 <div class="col-md d-flex">
                    <form:label cssClass="col-form-label" path="itemId">編號：</form:label>
                    <span class="pt-2">${caseData.itemId}&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <form:label cssClass="col-form-label" path="itemId">名稱：</form:label>
                    <span class="pt-2">${caseData.itemName}</span>
                 </div>
                 </tags:form-row>

                <tags:form-row>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label star" path="isableDate">日期：</form:label>
                        <form:select path="repeat" cssClass="form-control selector mr-5">
                            <form:option value="false">不重複</form:option>
                            <form:option value="true">自訂</form:option>
                        </form:select>
                        <span class="pt-2">每</span>
                        <form:select path="dateWeekMonth" cssClass="form-control selector mx-1" disabled="true">
                            <form:option value="date">日</form:option>
                            <form:option value="week">週</form:option>
                            <form:option value="month">月</form:option>
                        </form:select>
                        <span class="pt-2 ml-3">第</span>
                        <form:select path="week" cssClass="form-control selector mx-1" disabled="true">
                            <form:option value="1">一</form:option>
                            <form:option value="2">二</form:option>
                            <form:option value="3">三</form:option>
                            <form:option value="4">四</form:option>
                            <form:option value="5">五</form:option>
                        </form:select>
                        <span class="pt-2">個</span>
                        <span class="pt-2 ml-3">星期</span>
                        <form:select path="day" cssClass="form-control selector mx-1" disabled="true">
                            <form:option value="1">一</form:option>
                            <form:option value="2">二</form:option>
                            <form:option value="3">三</form:option>
                            <form:option value="4">四</form:option>
                            <form:option value="5">五</form:option>
                            <form:option value="6">六</form:option>
                            <form:option value="7">日</form:option>
                        </form:select>
                        <form:input path="periodStart" cssClass="form-control num_only dateTW" size="8" maxlength="7" />
                        <span class="pt-2 mx-1">~</span>
                        <form:input path="periodEnd" cssClass="form-control num_only dateTW" size="8" maxlength="7" />
                    </div>
                </tags:form-row>

                <tags:form-row>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label star" path="meetingBegin">禁用時間：</form:label>
                        <form:select path="meetingBegin" cssClass="form-control selector">
                            <form:option value="">開始時間</form:option>
                            <c:forEach items="${caseData.timeBeginMap}" var="opts" varStatus="optStatus">
                                <option value="${opts.key}">${opts.value.substring(0,2)}:${opts.value.substring(2)}</option>
                            </c:forEach>
                        </form:select>
                        <span class="input-group-text">~</span>
                        <form:select path="meetingEnd" cssClass="form-control selector">
                            <form:option value="">結束時間</form:option>
                            <c:forEach items="${caseData.timeEndMap}" var="opts" varStatus="optStatus">
                                <option value="${opts.key}">${opts.value.substring(0,2)}:${opts.value.substring(2)}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
    <tags:form-note>
    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
    </tags:form-note>
    <form:hidden path="itemId"/>
    <form:hidden path="itemName"/>
</form:form>
</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
    $(function() {
        //設定今天日期前無法選擇
        var maxDate = new Date();
        maxDate.setDate(maxDate.getDate())
        let $dateTW = $(".dateTW");
        $dateTW.datepicker("setStartDate", new Date());
    })

        $('#btnSave').click(function () {
            // 送出時需解除disabled為false，才有作用
            $('#periodEnd').prop('disabled', false);
            $('#eip06w060Form').attr('action', '<c:url value="/Eip06w060_save.action" />').submit();
        })

        $('#btnReply').click(function () {
            $('#eip06w060Form').attr('action', '<c:url value="/Eip06w060_partisable.action" />').submit();
        })

        $('#repeat, #dateWeekMonth').change(function () {
            let repeat = $('#repeat').val();
            let dateWeekMonth = $('#dateWeekMonth').val();

            if (repeat === 'false') {
                $('#dateWeekMonth').prop('disabled', true);
                $('#week').prop('disabled', true);
                $('#day').prop('disabled', true);
                $('#periodEnd_OUTSIDE').prop('disabled', true);
            } else {
                $('#dateWeekMonth').prop('disabled', false);
                if (dateWeekMonth === 'date') {
                    console.log(repeat, dateWeekMonth);
                    $('#week').prop('disabled', true);
                    $('#day').prop('disabled', true);
                    $('#periodEnd_OUTSIDE').prop('disabled', false);
                } else if (dateWeekMonth === 'week') {
                    $('#week').prop('disabled', true);
                    $('#day').prop('disabled', false);
                    $('#periodEnd_OUTSIDE').prop('disabled', false);
                } else if (dateWeekMonth === 'month') {
                    $('#week').prop('disabled', false);
                    $('#day').prop('disabled', false);
                    $('#periodEnd_OUTSIDE').prop('disabled', false);
                }
            }
        })

        let repeat = $('#repeat').val();
        let dateWeekMonth = $('#dateWeekMonth').val();
        if (repeat === 'false') {
            $('#dateWeekMonth').prop('disabled', true);
            $('#week').prop('disabled', true);
            $('#day').prop('disabled', true);
            // 如使用dateTW樣式，disabled效果呈現，#XXX_OUTSIDE，作用
            $('#periodEnd_OUTSIDE').prop('disabled', true);
            $('#periodEnd').prop('disabled', true);
        } else {
            $('#dateWeekMonth').prop('disabled', false);
            if (dateWeekMonth === 'date') {
                $('#week').prop('disabled', true);
                $('#day').prop('disabled', true);
                $('#periodEnd_OUTSIDE').prop('disabled', false);
            } else if (dateWeekMonth === 'week') {
                $('#week').prop('disabled', true);
                $('#day').prop('disabled', false);
                $('#periodEnd_OUTSIDE').prop('disabled', false);
            } else if (dateWeekMonth === 'month') {
                $('#week').prop('disabled', false);
                $('#day').prop('disabled', false);
                $('#periodEnd_OUTSIDE').prop('disabled', false);
            }
        }
</script>
</jsp:attribute>
</tags:layout>