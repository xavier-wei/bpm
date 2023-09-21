<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w430Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        td .btn {
            vertical-align: baseline;
        }
</style>
</jsp:attribute>
<jsp:attribute name="buttons">
    <tags:button id="btnReply">
        返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>
<jsp:attribute name="contents">
<tags:fieldset legend="內容">
    <form:form id="eip00w430Form" name="eip00w430Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="報名資格"><c:out value="${caseData.regContent.regisqual}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="課程代碼"><c:out value="${caseData.regContent.coursecode}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="班別代碼"><c:out value="${caseData.regContent.classcode}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="名稱"><c:out value="${caseData.regContent.topicname}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="地點"><c:out value="${caseData.regContent.address}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="報名期間"><c:out value="${caseData.regContent.regisdt}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="活動辦理時間"><c:out value="${caseData.regContent.prodt}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="課程時數"><c:out value="${caseData.regContent.classhours}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="允許報名人數"><c:out value="${caseData.regContent.allowappnum}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="已報名人數"><c:out value="${caseData.regContent.actualappnum}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="內容"><c:out value="${caseData.regContent.topicdesc}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="主辦單位"><c:out value="${caseData.regContent.organizer}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="聯絡人"><c:out value="${caseData.regContent.contacter}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="聯絡電話"><c:out value="${caseData.regContent.contactnum}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="聯絡傳真"><c:out value="${caseData.regContent.fax}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="E-mail"><c:out value="${caseData.regContent.email}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="允許報名方式"><c:out value="${caseData.regContent.allowappway}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="提供餐點"><c:out value="${caseData.regContent.ismeals eq 'Y' ? '是' : '否'}"/></tags:text-item>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="col-12 col-md-12">
                <tags:text-item label="附件下載">${caseData.regContent.files}</tags:text-item>
            </div>
        </tags:form-row>
        <form:hidden path="orformno"/>
        <form:hidden path="filename"/>
    </form:form>
</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    $('#btnReply').click(function(e){
        e.preventDefault();
        $('#eip00w430Form').attr('action', '<c:url value="/Eip00w430_enter.action" />').submit();
    })

    $('#eip00w430Form a').click(function(e){
        e.preventDefault();
        $('#filename').val($(this).text());
        $('#eip00w430Form').attr('action', '<c:url value="/Eip00w430_download.action" />').submit();
    })
})
</script>
</jsp:attribute>
</tags:layout>