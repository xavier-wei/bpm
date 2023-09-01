<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 會議室活動報表列印作業 --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip06w040Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="buttons">
    <!-- 選擇頁 -->
        <tags:button id="btnPrint">列印<i class="fas fa-cog"></i></tags:button>
        <tags:button id="btnClear">清除<i class="fas fa-eraser"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <tags:fieldset>
            <form:form id="eip06w040Form" name="eip06w040Form" modelAttribute="${caseKey}" method="POST">
                <tags:form-row>
                    <div class="col-md-3 d-flex">
                        <form:label cssClass="col-form-label star" path="meetingdt">會議日期：</form:label>
<%--                        <form:input path="meetingdt" cssClass="form-control" size="9"/>--%>
                        <form:input id="meetingdt"  name="meetingdt"  path="meetingdt" cssClass="form-control num_only ml-3 dateTW" size="9" maxlength="9" />
                    </div>
                </tags:form-row>
                 <tags:form-note>
                    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
                </tags:form-note>
            </form:form>
        </tags:fieldset>
    </jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function(){

        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1; //January is 0!
        var yyyy = today.getFullYear() - 1911;
        if (dd < 10) {
            dd = '0' + dd;
        }
        if (mm < 10) {
            mm = '0' + mm;
        }
        today = yyyy  + mm + dd;
        $('#meetingdt').val(today);

        //btnClear 清除
        $('#btnClear').click(function(e) {
            e.preventDefault();
            $('#meetingdt').val("");
        });

        //btnClear 清除
        $('#btnPrint').click(function(e) {
            e.preventDefault();
            $('#eip06w040Form').attr('action', 'Eip06w040_print.action').submit();
        });
    })
</script>
</jsp:attribute>
</tags:layout>