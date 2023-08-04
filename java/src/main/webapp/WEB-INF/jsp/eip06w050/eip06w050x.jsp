<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip06w050Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        .btn {
            border-color: #0c0c0c;
        }
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
<tags:fieldset legend="參數${caseData.mode eq 'A' ? '新增' : '修改'}">
<form:form id="eip06w050Form" modelAttribute="${caseKey}">
    <tags:form-row>
        <c:choose>
            <c:when test="${caseData.mode eq 'A'}">
                <form:label cssClass="col-form-label star" path="itemTyp">類別：</form:label>
            </c:when>
            <c:when test="${caseData.mode eq 'U'}">
                <form:label cssClass="col-form-label " path="itemTyp">類別：</form:label>
            </c:when>
        </c:choose>
        <div class="col-md-6">
            <form:select path="itemTyp" cssClass="form-control selector">
            <form:option value="">請選擇</form:option>
                <form:options items="${caseData.itemTypMap}"/>
            </form:select>
        </div>
    </tags:form-row>
    <tags:form-row>
        <c:choose>
            <c:when test="${caseData.mode eq 'A'}">
                <form:label id="itemIdLabel" cssClass="col-form-label star" path="itemId">編號：</form:label>
            </c:when>
            <c:when test="${caseData.mode eq 'U'}">
                <form:label id="itemIdLabel" cssClass="col-form-label" path="itemId">編號：</form:label>
            </c:when>
        </c:choose>
        <div class="col-md-6">
            <form:input path="itemId" cssClass="form-control d-inline-block num_only" maxlength="10"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <form:label cssClass="col-form-label star" path="itemName">名稱：</form:label>
        <div class="col-md-6">
            <form:input path="itemName" cssClass="form-control d-inline-block" maxlength="10"/>
        </div>
    </tags:form-row>

    <tags:form-row id="qtyRow">
        <form:label id="qtyLabel" cssClass="col-form-label star" path="qty">數量：</form:label>
        <div class="col-md-6">
            <form:input path="qty" cssClass="form-control d-inline-block num_only" maxlength="2"/>
        </div>
    </tags:form-row>
    <form:hidden path="mode"/>
    <tags:form-note>
    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
    </tags:form-note>

</form:form>
</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    $('#btnSave').click(function(){
        //修改送出時需解除disabled，才可以送出
        if($('#mode').val() =='U'){
            $('#itemId').prop('disabled', false);
            $('#itemTyp').prop('disabled', false);
        }
        $('#eip06w050Form').attr('action', '<c:url value="/Eip06w050_saveMeetingCodeModify.action" />').submit();
    })
    $('#btnReply').click(function(){
        $('#eip06w050Form').attr('action', '<c:url value="/Eip06w050_enter.action" />').submit();
    })



    $('#itemTyp').change(function(){
        cheitemTyp();
    });
    function cheitemTyp(){
        //類別(itemTyp)為物品(A)，隱藏數量相關欄位(qty)
        if($('#itemTyp').val()=='A' ){
            $('#qtyRow').hide();
        }else{
            $('#qtyRow').show();
        }

        //類別(itemTyp)為會議室(F)或(FX)，數量改為人數(qty)
        var itemTypValue = $('#itemTyp').val();
        if(itemTypValue =='F'||itemTypValue =='FX'){
            $('#qtyLabel').text('人數：')
        }else{
            $('#qtyLabel').text('數量：');
        }

        //類別新增時A：餐點  B：物品 F：會議室
        if (itemTypValue == 'A') {
            $('#itemIdLabel').text('編號：A-');
        } else if (itemTypValue == 'B') {
            $('#itemIdLabel').text('編號：B-');
        } else if (itemTypValue == 'F') {
            $('#itemIdLabel').text('編號：F-');
        }
    }
    cheitemTyp();

    //修改時編號(itemId)不可修改
    if($('#mode').val() =='U'){
        $('#itemId').prop('disabled', true);
        $('#itemTyp').prop('disabled', true);
        $('#itemIdLabel').text('編號：');
    }
})
</script>
</jsp:attribute>
</tags:layout>