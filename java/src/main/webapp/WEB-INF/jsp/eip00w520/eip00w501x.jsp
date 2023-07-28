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
            <thead>
                <tr>
                    <td class="text-center align-middle"><c:out value="標題"/>
                    <td class="text-center align-middle"><c:out value="題目"/>
                    <td class="text-center align-middle"><c:out value="選項"/>
                    <td class="text-center align-middle"><c:out value="得票數"/>
                    <td class="text-center align-middle"><c:out value="得票率"/>
                <tr>
            </thead>
            <tbody>
                <c:set var="prevSectitleseq" value="" />
                <c:set var="prevTopic" value="" />
                <c:forEach items="${caseData.reviews}" var="item" varStatus="status">
                    <c:set var="currSectitleseq" value="${item.sectitleseq}" />
                    <c:set var="currTopic" value="${item.topic}" />
                    <c:choose>
                    <%--部分標題相同--%>
                    <c:when test="${currSectitleseq ne prevSectitleseq and item.no ne '999'}">
                        <c:set var="prevSectitleseq" value="${currSectitleseq}" />
                        <c:set var="prevTopic" value="${currTopic}" />
                        <tr>
                            <c:set var="rowspankey" value="${item.sectitleseq}" />
                            <td rowspan="${caseData.titleRowspanMap[rowspankey]}" class="text-left align-middle first-td"><c:out value="${item.sectitle}"/></td>
                            <c:set var="quesrowspankey" value="${item.topic}" />
                            <td rowspan="${caseData.quesRowspanMap[quesrowspankey]}" class="text-left align-middle sec-td"><c:out value="${item.topic}"/></td>
                            <td class="text-left align-middle"><c:out value="${item.itemname}" /></td>
                            <c:set var="key" value="${item.no}" />
                            <td class="text-right align-middle">${caseData.multipleDataMap[key].count}</td>
                            <td class="text-right align-middle">${caseData.multipleDataMap[key].rate}</td>
                        </tr>
                    </c:when>
                    <c:when test="${currSectitleseq ne prevSectitleseq and item.no eq '999'}">
                        <c:set var="prevSectitleseq" value="${currSectitleseq}" />
                        <c:set var="prevTopic" value="${currTopic}" />
                        <c:set var="rowspankey" value="${item.sectitleseq}" />
                        <c:set var="qseqnokey" value="${item.qseqno}" />
                        <c:set var="textList" value="${caseData.textUiMap[qseqnokey]}" />
                        <c:if test="${empty caseData.qseqnoTextMap[qseqnokey]}">
                            <c:forEach items="${caseData.textUiMap[qseqnokey]}" var="textui" varStatus="status">
                                <tr>
                                    <c:if test="${status.index eq 0}">
                                        <td rowspan="${caseData.titleRowspanMap[rowspankey]}" class="text-left align-middle first-td"><c:out value="${item.sectitle}"/></td>
                                        <td rowspan="${fn:length(textList)}" class="text-left align-middle sec-td"><c:out value="${item.itemname}"/></td>
                                    </c:if>
                                    <td class="text-left align-middle">${textui}</td>
                                    <td class="text-right align-middle">${caseData.textDataMap[textui].count}</td>
                                    <td class="text-right align-middle">${caseData.textDataMap[textui].rate}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${not empty caseData.qseqnoTextMap[qseqnokey]}">
                            <c:set var="textui" value="${caseData.qseqnoTextMap[qseqnokey]}" />
                            <tr>
                                <td rowspan="${caseData.titleRowspanMap[rowspankey]}" class="text-left align-middle first-td"><c:out value="${item.sectitle}"/></td>
                                <td class="text-left align-middle sec-td"><c:out value="${item.itemname}"/></td>
                                <td class="text-left align-middle">${textui}</td>
                                <td class="text-right align-middle">${empty caseData.textDataMap[textui].count ? '0' : caseData.textDataMap[textui].count}</td>
                                <td class="text-right align-middle">${empty caseData.textDataMap[textui].rate ? '0%' : caseData.textDataMap[textui].rate}</td>
                            </tr>
                        </c:if>
                    </c:when>
                    <%--部分標題相同，但題目不同--%>
                    <c:when test="${currSectitleseq eq prevSectitleseq and currTopic ne prevTopic and item.no ne '999'}">
                        <c:set var="prevTopic" value="${currTopic}" />
                        <tr>
                            <c:set var="quesrowspankey" value="${item.topic}" />
                            <td rowspan="${caseData.quesRowspanMap[quesrowspankey]}" class="text-left align-middle sec-td"><c:out value="${item.topic}"/></td>
                            <td class="text-left align-middle"><c:out value="${item.itemname}" /></td>
                            <c:set var="key" value="${item.no}" />
                            <td class="text-right align-middle">${caseData.multipleDataMap[key].count}</td>
                            <td class="text-right align-middle">${caseData.multipleDataMap[key].rate}</td>
                        </tr>
                    </c:when>
                    <c:when test="${currSectitleseq eq prevSectitleseq and currTopic ne prevTopic and item.no eq '999'}">
                        <c:set var="prevTopic" value="${currTopic}" />
                        <c:set var="rowspankey" value="${item.sectitleseq}" />
                        <c:set var="qseqnokey" value="${item.qseqno}" />
                        <c:set var="textList" value="${caseData.textUiMap[qseqnokey]}" />
                        <c:if test="${empty caseData.qseqnoTextMap[qseqnokey]}">
                            <c:forEach items="${caseData.textUiMap[qseqnokey]}" var="textui" varStatus="status">
                                <tr>
                                    <c:if test="${status.index eq 0}">
                                        <td rowspan="${fn:length(textList)}" class="text-left align-middle sec-td"><c:out value="${item.itemname}"/></td>
                                    </c:if>
                                    <td class="text-left align-middle">${textui}</td>
                                    <td class="text-right align-middle">${caseData.textDataMap[textui].count}</td>
                                    <td class="text-right align-middle">${caseData.textDataMap[textui].rate}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${not empty caseData.qseqnoTextMap[qseqnokey]}">
                            <c:set var="textui" value="${caseData.qseqnoTextMap[qseqnokey]}" />
                            <tr>
                                <td class="text-left align-middle sec-td"><c:out value="${item.itemname}"/></td>
                                <td class="text-left align-middle">${textui}</td>
                                <td class="text-right align-middle">${empty caseData.textDataMap[textui].count ? '0' : caseData.textDataMap[textui].count}</td>
                                <td class="text-right align-middle">${empty caseData.textDataMap[textui].rate ? '0%' : caseData.textDataMap[textui].rate}</td>
                            </tr>
                        </c:if>
                    </c:when>
                    <%--部分標題相同，題目也相同--%>
                    <c:when test="${currSectitleseq eq prevSectitleseq and currTopic eq prevTopic}">
                        <tr>
                            <td class="text-left align-middle"><c:out value="${item.itemname}" /></td>
                            <c:set var="key" value="${item.no}" />
                            <td class="text-right align-middle">${caseData.multipleDataMap[key].count}</td>
                            <td class="text-right align-middle">${caseData.multipleDataMap[key].rate}</td>
                        </tr>
                    </c:when>
                    </c:choose>
                </c:forEach>
            </tbody>
        </table>
    </div>
</tags:form-row>