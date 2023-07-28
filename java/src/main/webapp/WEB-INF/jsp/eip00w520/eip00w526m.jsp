<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w520Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        #tb1 tr td{
            background-color: white;
            height: 50px;
        }
        #tb1 tr .first-td,.sec-td{
            background-color: white;
            min-width: 100px;
        }
        #tb1 tr td.star:before{
            content: '*';
            color: red;
        }
        .underline-input {
            border: none;
            border-bottom: 1px solid #ced4da;
            border-radius: 0;
            box-shadow: none;
        }
        .form-check-inline {
            margin-right: 0rem;
        }
        table {
            border-collapse: separate;
        }
        table tr td label {
            display: inline;
        }
    </style>
</jsp:attribute>
    <jsp:attribute name="buttons">
    <tags:button id="btnExport">
        匯出<i class="fas fa-download"></i>
    </tags:button>
    <tags:button id="btnReview">
        檢視<i class="fas fa-search"></i>
    </tags:button>
    <tags:button id="btnSelectAll">
        全選<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnClear">
        清除<i class="fas fas fa-eraser"></i>
    </tags:button>
    <tags:button id="btnReply">
        返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
<tags:fieldset legend="意見調查預覽畫面">
<form:form id="eip00w520Form" modelAttribute="${caseKey}">
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
                <c:forEach items="${caseData.contents}" var="item" varStatus="status">
                        <tr>
                            <c:if test="${currSectitle ne item.sectitle}">
                                <td rowspan="${item.rowspan}" class="text-left align-middle first-td"><c:out value="${partIndex}" />.<c:out value="${item.sectitle}" /></td>
                                <c:set var="partIndex" value="${partIndex+1}" />
                                <c:set var="quesIndex" value="1" />
                            </c:if>
                            <td class="text-left align-middle sec-td"><c:out value="${quesIndex}" />.<c:out value="${item.topic}" /></td>
                            <td class="text-left align-middle">
                                <c:forEach items="${item.optionList}" var="opts" varStatus="optStatus">
                                    <c:choose>
                                        <c:when test="${opts.isText eq 'Y'}">
                                            <label class="mb-0">
                                                <div class="form-check-inline">
                                                    <form:checkbox path="reviewTextcontents[${status.index}].qseqno" cssClass="form-check" value="${opts.qseqno}"/>
                                                </div>
                                            </label>
                                            <form:input path="reviewTextcontents[${status.index}].text" cssClass="form-control d-inline-block fullCase" size="30" maxlength="100"/>
<%--                                            <form:hidden path="reviewTextcontents[${status.index}].qseqno" value="${opts.qseqno}"/>--%>
                                        </c:when>
                                        <c:otherwise>
                                            <label class="mb-0">
                                                <div class="form-check-inline">
                                                    <form:checkbox path="reviewMultiplecontents" cssClass="form-check" value="${opts.iseqno}"/>${opts.itemdesc}
                                                </div>
                                            </label>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </td>
                            <c:set var="currSectitle" value="${item.sectitle}" />
                            <c:set var="quesIndex" value="${quesIndex+1}" />
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </tags:form-row>
    <form:hidden path="osformno"/>
    <form:hidden path="mode"/>
</form:form>
<tags:form-note>
<tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
</tags:form-note>
</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    // dataTable 設定
    $('#btnReview').click(function(e){
        e.preventDefault();
        if($('input[type="checkbox"]:checked').length === 0) {
            showAlert("請至少勾選一項");
            return;
        }
        $('#eip00w520Form').attr('action', '<c:url value="/Eip00w520_writeContent.action" />').submit();
    })
    $('#btnSelectAll').click(function(e){
        e.preventDefault();
        $('input[type="checkbox"]').prop('checked', true);
    })
    $('#btnClear').click(function(e){
        e.preventDefault();
        $('input[type="text"]').val('');
        $('input[type="radio"], input[type="checkbox"]').prop('checked', false);
    })
    $('#btnReply').click(function(e){
        e.preventDefault();
        $('#eip00w520Form').attr('action', '<c:url value="/Eip00w520_enter.action" />').submit();
    })

})
</script>
</jsp:attribute>
</tags:layout>