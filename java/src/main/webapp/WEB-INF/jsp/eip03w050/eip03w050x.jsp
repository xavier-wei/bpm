<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 重要列管事項_件數統計 --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip03w050Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="buttons">
        <tags:button id="btnPrint">匯出EXCEL <i class="fas fa-file-excel"></i></tags:button>
	    <tags:button id="btnBack">返回<i class="fas fa-reply"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <form:form id="eip03w050Form" modelAttribute="${caseKey}">
                   <tags:form-row>
                        <div class="col-md-3">
                            <form:label cssClass="col-form-label" path="dept_name">處室：</form:label>
                            <c:out value="${caseData.dept_name}" />
                        </div>
                        <div class="col-md-3">
                            <form:label cssClass="col-form-label" path="status">狀態：</form:label>
                            <c:choose>
                                <c:when test="${caseData.status == 'closed'}">
                                    <c:out value="已結案" />
                                </c:when>
                                <c:otherwise>
                                    <c:out value="未結案" />
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </tags:form-row>
            <div class="table-responsive">
                <table id="dataTable" class="table">
                    <thead data-orderable="true">
                    <tr>
                        <th class="text-center">序號</th>
                        <th class="text-center">列管編號</th>
                        <th class="text-center">交辦來源</th>
                        <th class="text-center">列管事項</th>
                        <th class="text-center">處理狀態</th>
                        <th class="text-center">辦理情形</th>
                        <th class="text-center">列管迄日</th>
                        <th class="text-center">解列日期</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${caseData.eip03w040CaseDetailList }" var="item" varStatus="status">
                            <tr>
                                <td>${status.index+1 }</td>
                                <td class="text-center">
                                    <c:out value="${item.trkId }" />
                                </td>
                                <td class="text-center">
                                    <c:out value="${item.trkFrom }" />
                                </td>
                                <td class="text-left">
                                    <c:out value="${item.trkCont }"  escapeXml="false"/>
                                </td>
                                <td class="text-left">
                                    <c:out value="${item.prcSts }" />
                                </td>
                                <td class="text-left">
                                    <c:out value="${item.rptCont }"  escapeXml="false"/>
                                </td>
                                <td class="text-left">
                                    <func:minguo value="${item.endDt}"/>
                                </td>
                                <td class="text-left">
<%--                                    解列日期：固定文字，若KeepTrkDtl.SupAgree為Y，則KeepTrkDtl.SupDt，否則為空。--%>
                                    <c:choose>
                                        <c:when test="${item.supAgree == 'Y'}">
                                            <func:minguo value="${item.fmtSupDt }" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="" />
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <form:hidden path="trkObj" val="${caseData.trkObj}" />
            <form:hidden path="status" val="${caseData.status}"/>
        </form:form>
    </jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function(){
        // table render config
        var columnDefs = [ {
            "orderable" : true,
        }];
        $("#dataTable").DataTable(getDataTablesConfig(columnDefs));

        //返回btnBack
        $('#btnBack').click(function(e) {
            e.preventDefault();
            $('#eip03w050Form').attr('action', 'Eip03w050_enter.action').submit();
        });

        $('#btnPrint').click(function(e) {
            e.preventDefault();
            $('#eip03w050Form').attr('action', 'Eip03w050_print.action').submit();
        });
    })
</script>
</jsp:attribute>
</tags:layout>