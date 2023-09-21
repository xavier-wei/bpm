<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w520Controller).CASE_KEY" />
<spring:eval var="partCaseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w520Controller).PART_CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<c:set var="partData" value="${requestScope[partCaseKey]}" />
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
<tags:fieldset legend="${caseData.mode eq 'A' ? '新增' : '修改'}部分標題">
<form:form id="eip00w520Form" modelAttribute="${partCaseKey}">
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="topicname">主題名稱：</form:label>
            <c:out value="${partData.topicname}"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="sectitleseq">部分排序：</form:label>
            <form:input path="sectitleseq" cssClass="form-control d-inline-block num_only" size="3" maxlength="2"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6 d-flex">
            <form:label cssClass="col-form-label star mr-1" path="sectitle">部分標題：</form:label>
            <form:textarea path="sectitle" cssClass="form-control col-md-6" rows="3" maxlength="100"/>
        </div>
    </tags:form-row>
    <form:hidden path="osformno"/>
    <form:hidden path="qseqno"/>
    <form:hidden path="topicname"/>
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
        $('#eip00w520Form').attr('action', '<c:url value="/Eip00w520_partConfirm.action" />').submit();
    })
    $('#btnReply').click(function(){
        $('#eip00w520Form').attr('action', '<c:url value="/Eip00w520_partList.action" />').submit();
    })
})
</script>
</jsp:attribute>
</tags:layout>