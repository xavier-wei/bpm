<tags:form-row>
    <div class="col-md-6">
        <form:label cssClass="col-form-label" path="themeCase.topicname">主題：</form:label>
        <c:out value="${caseData.themeCase.topicname}"/>
    </div>
</tags:form-row>
<tags:form-row>
    <div class="col-md-6">
        <form:label cssClass="col-form-label" path="themeCase.osfmdt">填寫時間：</form:label>
        <c:out value="${caseData.themeCase.fullosfmdt}"/> ~ <c:out value="${caseData.themeCase.fullosendt}"/>
    </div>
</tags:form-row>
<tags:form-row>
    <div class="table-responsive">
        <table class="table" id="tb1">
            <tbody>
                <c:set var="partIndex" value="1" />
                <c:set var="quesIndex" value="1" />
                <c:forEach items="${caseData.previews}" var="item" varStatus="status">
                    <tr>
                        <c:if test="${currSectitle ne item.sectitle}">
                            <td rowspan="${item.rowspan}" class="text-left align-middle first-td"><c:out value="${partIndex}" />.<c:out value="${item.sectitle}" /></td>
                            <c:set var="partIndex" value="${partIndex+1}" />
                            <c:set var="quesIndex" value="1" />
                        </c:if>
                        <td class="text-left align-middle sec-td ${item.isrequired eq 'Y' ? 'star' : ''}"><c:out value="${quesIndex}" />.<c:out value="${item.topic}" /></td>
                        <td class="text-left align-middle">
                            <c:forEach items="${item.optionList}" var="opts" varStatus="optStatus">
                                <c:choose>
                                    <c:when test="${opts.isText eq 'Y'}">
                                        <form:input path="wricontent[${status.index}].t" cssClass="form-control d-inline-block fullCase trimS" size="30" maxlength="100"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${item.optiontype eq 'S'}">
                                            <label>
                                                <form:radiobutton path="wricontent[${status.index}].os[0].v" data-n="${item.qseqno}" value="${opts.iseqno}"/>
                                                <span><c:out value="${opts.itemdesc}" /></span>
                                            </label>
                                            <c:if test="${opts.isaddtext eq 'Y'}">
                                                <form:input path="wricontent[${status.index}].os[${optStatus.index}].t" cssClass="form-control d-inline-block fullCase underline-input trimS" size="8" maxlength="10"/>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${item.optiontype eq 'M'}">
                                            <label class="mb-0">
                                                <div class="form-check-inline">
                                                    <form:checkbox path="wricontent[${status.index}].os[${optStatus.index}].v" cssClass="form-check" value="${opts.iseqno}"/>${opts.itemdesc}
                                                </div>
                                            </label>
                                            <c:if test="${opts.isaddtext eq 'Y'}">
                                                <form:input path="wricontent[${status.index}].os[${optStatus.index}].t" cssClass="form-control d-inline-block fullCase underline-input trimS" size="8" maxlength="10"/>
                                            </c:if>
                                            <form:hidden path="wricontent[${status.index}].os[${optStatus.index}].q" value="${item.qseqno}"/>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <form:hidden path="wricontent[${status.index}].isrequired" value="${item.isrequired}" />
                            <form:hidden path="wricontent[${status.index}].optiontype" value="${item.optiontype}" />
                            <form:hidden path="wricontent[${status.index}].n" value="${item.qseqno}"/>
                        </td>
                        <c:set var="currSectitle" value="${item.sectitle}" />
                        <c:set var="quesIndex" value="${quesIndex+1}" />
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</tags:form-row>