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
            <thead data-orderable="true">
            <tr>
                <c:forEach items="${caseData.writeContentTitle}" var="title" varStatus="status">
                <td class="text-center align-middle">${title}</td>
                </c:forEach>
            <tr>
            </thead>
            <tbody>
                <c:forEach items="${caseData.writeContentData}" var="data" varStatus="datastatus">
                    <tr>
                        <c:forEach items="${data}" var="col" varStatus="colstatus">
                            <c:if test="${colstatus.index eq 0}">
                                <td class="text-center align-middle">${datastatus.index+1}</td>
                            </c:if>
                            <td class="text-left align-middle">${col}</td>
                        </c:forEach>
                    <tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</tags:form-row>