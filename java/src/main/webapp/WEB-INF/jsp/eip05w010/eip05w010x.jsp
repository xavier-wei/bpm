<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip05w010Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
    </style>
</jsp:attribute>
<jsp:attribute name="buttons">
<!-- 明細頁 -->
    <tags:button id="btnSave">
        儲存<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnReply">
        返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
<tags:fieldset legend="${caseData.mode eq 'A' ? '新增' : '修改'}分類">
<form:form id="eip05w010Form" modelAttribute="${caseKey}">
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="oscname">分類名稱：</form:label>
            <form:input path="oscname" cssClass="form-control d-inline-block" placeholder="最長10字" maxlength="10"/>
        </div>
    </tags:form-row>
    <tags:form-note>
    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
    </tags:form-note>
    <form:hidden path="mode"/>
    <form:hidden path="osccode"/>
</form:form>
</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    $('#btnSave').click(function(){
        $('#eip05w010Form').attr('action', '<c:url value="/Eip05w010_saveOrModify.action" />').submit();
    })
    $('#btnReply').click(function(){
        $('#eip05w010Form').attr('action', '<c:url value="/Eip05w010_enter.action" />').submit();
    })
})
</script>
</jsp:attribute>
</tags:layout>