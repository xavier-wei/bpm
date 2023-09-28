<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- !!!!開發除錯用!!!! --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.framework.controllers.ConsoleController).CASE_KEY"/>
<c:set var="caseData" value="${requestScope[caseKey]}"/>
<tags:layout>
    <jsp:attribute name="buttons">
    <!-- 選擇頁 -->
        <tags:button id="btnPrint">執行<i class="fas fa-cog"></i></tags:button>
        <tags:button id="btnDownload">LOG<i class="fas fa-file"></i></tags:button>
        <tags:button id="btnClear">清除<i class="fas fa-eraser"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <tags:fieldset>
            <form:form id="form" name="form" modelAttribute="${caseKey}" method="POST">
                <tags:form-row>
                    <div class="col-12 form-inline">
                        <form:label cssClass="col-form-label star" path="input_text">input：</form:label>
                        <form:textarea cssClass="col-12 form-control" path="input_text" maxlength="9999"
                                       rows="10"></form:textarea>
                    </div>
                </tags:form-row>
            </form:form>
        </tags:fieldset>
        <tags:fieldset legend="執行結果">
    <c:if test="${not empty error}">
        <p style="color: red;">${fn:escapeXml(error)}</p>
    </c:if>
                     
    <c:if test="${not empty updateResult}">
        <p style="color: blue;">${fn:escapeXml(updateResult)}</p>
    </c:if>

    <c:if test="${results != null}">
        <h1>查詢結果：</h1>
    </c:if>
    <c:if test="${not empty results}">
        <table class="table" >
            <thead>
            <tr>
                    <c:forEach items="${results[0].keySet()}" var="column">
                        <th>${column}</th>
                    </c:forEach>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${results}" var="row">
                    <tr>
                        <c:forEach items="${row.values()}" var="value">
                            <td>${value}</td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
        </tags:fieldset>
    </jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function () {

        //btnClear 清除
        $('#btnClear').click(function (e) {
            e.preventDefault();
            $('#input_text').val("");
            $('#form').attr('action', 'Common_console.action').submit();
        });

        //btnClear 清除
        $('#btnDownload').click(function (e) {
            e.preventDefault();
            $('#form').attr('action', 'Common_downloadlog.action').submit();
        });

        //btnClear 清除
        $('#btnPrint').click(function (e) {
            e.preventDefault();
            $('#form').attr('action', 'Common_console.action').submit();
        });
    })
</script>
</jsp:attribute>
</tags:layout>