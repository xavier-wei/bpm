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
    <tags:button id="btnInsert">
        送出<i class="fas fa-paper-plane"></i>
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
    <%@ include file="/WEB-INF/jsp/eip00w520/eip00w500x.jsp" %>
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
    $('#btnInsert').click(function(e){
        e.preventDefault();
        $('#eip00w520Form').attr('action', '<c:url value="/Eip00w520_previewTest.action" />').submit();
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

    if ($('#mode').val()==='P') {
        $('#btnInsert').prop('disabled',true);
    }
})
</script>
</jsp:attribute>
</tags:layout>