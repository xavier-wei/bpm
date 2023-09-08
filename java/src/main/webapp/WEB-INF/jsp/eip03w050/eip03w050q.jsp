<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 重要列管事項_件數統計 --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip03w050Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="contents">
        <form:form id="eip03w050Form" modelAttribute="${caseKey}">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th class="text-center">項次</th>
                        <th class="text-center">處室</th>
                        <th class="text-center">案件數</th>
                        <th class="text-center">已結案</th>
                        <th class="text-center">未結案</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${caseData.eip03w040CaseList }" var="item" varStatus="status">
                            <tr>
                                <td>${status.index+1 }</td>
                                <td class="text-left">
                                    <c:out value="${item.dept_name }" />
                                </td>
                                <td class="text-right cnt">
                                    <c:out value="${item.cnt }" />
                                </td>
                                <c:choose>
                                    <c:when test="${item.cnt_cls > 0 && item.sameDept == 'true'}">
                                        <td class="text-right cnt_cls"><a class="clicked" href="#" data-obj="${item.trkObj}" data-name="${item.dept_name}" data-status="closed"><c:out value="${item.cnt_cls }" /></a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="text-right "><c:out value="${item.cnt_cls }" /></td>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${item.cnt_opn > 0 && item.sameDept == 'true'}">
                                        <td class="text-right cnt_opn"><a class="clicked" href="#" data-obj="${item.trkObj}" data-name="${item.dept_name}" data-status="unclosed"><c:out value="${item.cnt_opn }" /></a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="text-right"><c:out value="${item.cnt_opn }" /></td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                    </c:forEach>
                        <tr>
                            <td></td>
                            <td class="text-center"><c:out value="合計" /></td>
                            <td class="text-right vSum" ></td>
                            <td class="text-right vSum" ></td>
                            <td class="text-right vSum" ></td>
                        </tr>

                    </tbody>
                </table>
            </div>
            <form:hidden path="trkObj" />
            <form:hidden path="dept_name" />
            <form:hidden path="status" />
        </form:form>
    </jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function(){

        $('.vSum').each(function (i) {
            var dataRowlength = ($('.table tr').length) - 2;
            var total = 0;
            for(var j = 1 ; j < dataRowlength ; j++){
                var test =  parseInt($('.table tr:eq('+(j)+') td:eq(' + (i+2) +')').text().trim(), 10);
                total += test;
            }
            $(this).text(total);
            i++;
        })


        $('.clicked').css('text-decoration', 'underline').click(function(e){
            e.preventDefault();
            $('#trkObj').val($(this).data('obj'));
            $('#dept_name').val($(this).data('name'));
            $('#status').val($(this).data('status'));
            $('#eip03w050Form').attr('action', '<c:url value="/Eip03w050_queryIssue.action" />').submit();
        });
    })
</script>
</jsp:attribute>
</tags:layout>