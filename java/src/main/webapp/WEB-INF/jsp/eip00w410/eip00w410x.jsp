<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w410Controller).CASE_KEY" />
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
<form:form id="eip00w410Form" modelAttribute="${caseKey}">
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="orccode">分類名稱：</form:label>
            <form:input path="orcname" cssClass="form-control d-inline-block" maxlength="10"/>(最長10字)
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="iscourse">是否為課程：</form:label>
            <label class="mb-0">
                <form:radiobutton path="iscourse" value="Y" cssClass="mr-1"/>
                <span>是</span>
            </label>
            <label class="mb-0">
                <form:radiobutton path="iscourse" value="N" cssClass="mr-1"/>
                <span>否</span>
            </label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="signform">簽到表格式：</form:label>
            <label class="mb-0">
                <form:radiobutton path="signform" value="A" cssClass="mr-1"/>
                <span>活動</span>
            </label>
            <label class="mb-0">
                <form:radiobutton path="signform" value="C" cssClass="mr-1"/>
                <span>課程</span>
            </label>
            <label class="mb-0">
                <form:radiobutton path="signform" value="M" cssClass="mr-1"/>
                <span>會議</span>
            </label>
        </div>
    </tags:form-row>
    <tags:form-note>
    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
    </tags:form-note>
    <form:hidden path="mode"/>
    <form:hidden path="orccode"/>
</form:form>
</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    $('#btnSave').click(function(){
        $('#eip00w410Form').attr('action', '<c:url value="/Eip00w410_saveOrModify.action" />').submit();
    })
    $('#btnReply').click(function(){
        $('#eip00w410Form').attr('action', '<c:url value="/Eip00w410_enter.action" />').submit();
    })
})
</script>
</jsp:attribute>
</tags:layout>