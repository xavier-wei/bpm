<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eipib0w050Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 修改頁 -->
    <tags:button id="btnArchive">
                        存檔<i class="fas fa-user-check"></i>
    </tags:button>          
    <tags:button id="btnClear">
                        清除<i class="fas fa-eraser"></i>
    </tags:button>
    <tags:button id="btnReply">
                       返回<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
<form:form id="bfib0w050Form" modelAttribute="${caseKey}">
    <tags:form-row>
    <form:label cssClass="col-form-label" path="codekind">主代號類別：</form:label>
        <div class="col-12 col-md">
            <form:input path="afcodekind" name="codekind" value="${caseData.codekind}" cssClass="form-control"/>
            <form:hidden path="codekind" name="codekind" cssClass="form-control"/>
        </div>
    </tags:form-row>
    <tags:form-row>
    <form:label cssClass="col-form-label" path="codeno">主代號：</form:label>
        <div class="col-12 col-md">
            <form:input path="afcodeno" name="codeno" value="${caseData.codeno}" cssClass="form-control inputtext"/>
            <form:hidden path="codeno" name="codeno" cssClass="form-control inputtext"/>
        </div>
    </tags:form-row>
    <tags:form-row>
    <form:label cssClass="col-form-label" path="codename">主代號名稱：</form:label>
        <div class="col-12 col-md">
            <form:input path="codename" name="codename" value="${caseData.codename}" size="40" cssClass="form-control inputtext"/>
        </div>
    </tags:form-row>
    <tags:form-row>
    <form:label cssClass="col-form-label" path="scodekind">副代號類別：</form:label>
        <div class="col-12 col-md">
            <form:input path="scodekind" name="scodekind" value="${caseData.scodekind}" cssClass="form-control inputtext"/>
        </div>
    </tags:form-row>
    <tags:form-row>
    <form:label cssClass="col-form-label" path="scodeno">副代號：</form:label>
        <div class="col-12 col-md">
            <form:input path="scodeno" name="scodeno" value="${caseData.scodeno}" cssClass="form-control inputtext"/>
        </div>
    </tags:form-row>
    <tags:form-row>
    <form:label cssClass="col-form-label" path="remark">說明：</form:label>
        <div class="col-12 col-md">
            <form:input path="remark" name="remark" value="${caseData.remark}" cssClass="form-control inputtext"/>
        </div>
    </tags:form-row>
<form:hidden path="doUpdate" value="true"/>
</form:form>

</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
	//返回
    $('#btnReply').click(function(){
    	$('.inputtext').val("");
        $('#bfib0w050Form').attr('action', '<c:url value="/Code_query.action" />').submit();
    })
    //清除
    $('#btnClear').click(function(){
    	$('.inputtext').val("");
    })
    //修改
    $('#btnArchive').click(function(){
    	$('#bfib0w050Form').attr('action', '<c:url value="/Code_update.action" />').submit();
    })
})
</script>
</jsp:attribute>
</tags:layout>