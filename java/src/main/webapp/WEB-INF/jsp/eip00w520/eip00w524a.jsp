<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w520Controller).CASE_KEY" />
<spring:eval var="questionCaseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w520Controller).QUESTION_CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<c:set var="questionData" value="${requestScope[questionCaseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
    </style>
</jsp:attribute>
<jsp:attribute name="buttons">
    <tags:button id="btnSave">
        儲存<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnReply">
        返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
<tags:fieldset legend="${caseData.mode eq 'A' ? '新增' : '修改'}部分列表題目(母表格)">
<form:form id="eip00w520Form" modelAttribute="${questionCaseKey}">
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="topicname">主題名稱：</form:label>
            <c:out value="${questionData.topicname}"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="sectitleseq">部分排序：</form:label>
            <c:out value="${questionData.sectitleseq}"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="sectitle">部分標題：</form:label>
            <c:out value="${questionData.sectitle}"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6 d-flex">
            <form:label cssClass="col-form-label star" path="topicseq">題目排序：</form:label>
            <form:input path="topicseq" cssClass="form-control d-inline-block num_only" size="3" maxlength="2"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6 d-flex">
            <form:label cssClass="col-form-label star" path="topic">題目：</form:label>
            <form:input path="topic" cssClass="form-control d-inline-block" size="20" maxlength="33"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="optiontype">選項：</form:label>
            <label>
                <form:radiobutton path="optiontype" value="S"/>
                <span class="font-weight-bold">單選</span>
            </label>
            <label>
                <form:radiobutton path="optiontype" value="M"/>
                <span class="font-weight-bold">多選</span>
            </label>
            <label>
                <form:radiobutton path="optiontype" value="T"/>
                <span class="font-weight-bold">文字框</span>
            </label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="isrequired">是否必填：</form:label>
            <label>
                <form:radiobutton path="isrequired" value="Y"/>
                <span class="font-weight-bold">是</span>
            </label>
            <label>
                <form:radiobutton path="isrequired" value="N"/>
                <span class="font-weight-bold">否</span>
            </label>
        </div>
    </tags:form-row>
    <form:hidden path="osformno"/>
    <form:hidden path="qseqno"/>
    <form:hidden path="topicname"/>
    <form:hidden path="sectitleseq"/>
    <form:hidden path="sectitle"/>
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
    $('#btnSave').click(function(){
        $('#eip00w520Form').attr('action', '<c:url value="/Eip00w520_questionConfirm.action" />').submit();
    })
    $('#btnReply').click(function(){
        $('#eip00w520Form').attr('action', '<c:url value="/Eip00w520_questionList.action" />').submit();
    })
})
</script>
</jsp:attribute>
</tags:layout>