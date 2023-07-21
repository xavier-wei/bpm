<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.msg.controllers.Eip01w020Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="buttons">
        <tags:button id="btnCount">
            統計<i class="fas fa-calculator"></i>
        </tags:button>
        <tags:button id="btnClear">
            清除<i class="fas fa-eraser"></i>
        </tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <form:form id="eip01w020Form" modelAttribute="${caseKey}">
            <tags:fieldset legend="查詢條件">
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="msgtype">分類名稱：</form:label>
                    <div class="col-12 col-md">
                        <form:select path="msgtype" cssClass="form-control">
                            <option value="" selected>請選擇</option>
                            <c:forEach var="item" items="${caseData.msgtypes}" varStatus="status">
                                <form:option value="${item.codeno}">
                                    <c:out value="${item.codename}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="subject">主旨關鍵字：</form:label>
                    <div class="col-12 col-md">
                        <form:input path="subject" cssClass="form-control" size="40" maxlength="800" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="contactunit">聯絡單位：</form:label>
                    <div class="col-12 col-md">
                        <form:select path="contactunit" cssClass="form-control">
                            <option value="" selected>請選擇</option>
                            <c:forEach var="item" items="${caseData.contactunits}" varStatus="status">
                                <form:option value="${item.codeno}">
                                    <c:out value="${item.codename}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="creatid">建立人員：</form:label>
                    <div class="col-12 col-md">
                        <form:select path="creatid" cssClass="form-control">
                            <option value="" selected>請選擇</option>
                            <c:forEach var="item" items="${caseData.creators}" varStatus="status">
                                <form:option value="${item.codeno}">
                                    <c:out value="${item.codename}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <form:label cssClass="col-form-label" path="updid">更新人員：</form:label>
                    <div class="col-12 col-md">
                        <form:select path="updid" cssClass="form-control">
                            <option value="" selected>請選擇</option>
                            <c:forEach var="item" items="${caseData.updaters}" varStatus="status">
                                <form:option value="${item.codeno}">
                                    <c:out value="${item.codename}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </tags:form-row>
            </tags:fieldset>
            <c:if test="${not empty caseData.qryList }">
                <div class="table-responsive mt-4">
                    <table class="table" id="qryListTable">
                        <thead>
                            <tr>
                                <th class="text-center">項次</th>
                                <th class="text-center">聯絡單位</th>
                                <th class="text-center">文章篇數</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${caseData.qryList}" var="item" varStatus="status">
                                <tr data-seq="${status.index + 1 }">
                                    <td class="text-center">
                                        ${status.index + 1}
                                    </td>
                                    <td class="text-center">
                                        <c:out value="${item.deptname}" />
                                        <form:hidden path="qryList[${status.index}].deptname" />
                                    </td>
                                    <td class="text-center">
                                        <c:out value="${item.count}" />
                                        <form:hidden path="qryList[${status.index}].count" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </form:form>
    </jsp:attribute>

    <jsp:attribute name="footers">
        <script type="text/javascript">
            $(function() {
                // 統計
                $('#btnCount').click(function() {
                    $('#eip01w020Form').attr('action', '<c:url value="/Eip01w020_count.action" />')
                        .submit();
                });
                // 清除
                $('#btnClear').click(function() {
                    $('#eip01w020Form').attr('action', '<c:url value="/Eip01w020_enter.action" />')
                        .submit();
                });
            });
        </script>
    </jsp:attribute>
</tags:layout>