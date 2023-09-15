<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip04w020Controller).CASE_KEY" />
<spring:eval var="manualKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip04w020Controller).MANUAL_CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<c:set var="manualData" value="${requestScope[manualKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        .col-form-label {
            min-width: 190px;
        }
        .btn {
            vertical-align: baseline;
        }
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
<tags:fieldset legend="人工報名">
<form:form id="eip04w020Form" modelAttribute="${manualKey}" enctype="multipart/form-data" method="post">
    <tags:form-row>
         <div class="col-md-6">
             <form:label cssClass="col-form-label" path="topicname">主題名稱：</form:label>
             <c:out value="${caseData.topicname}"/>
         </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="regType">新增方式：</form:label>
            <label class="mb-0">
                <form:radiobutton path="regType" value="S" checked="true" />
                <span>單筆報名</span>
            </label>
            <label class="mb-0">
                <form:radiobutton path="regType" value="B"/>
                <span>批次匯入報名檔案</span>
            </label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="regisway">報名方式：</form:label>
            <label class="mb-0">
                <form:radiobutton path="regisway" value="I"/>
                <span>網路</span>
            </label>
            <label class="mb-0">
                <form:radiobutton path="regisway" value="E"/>
                <span>E-mail</span>
            </label>
            <label class="mb-0">
                <form:radiobutton path="regisway" value="F"/>
                <span>傳真</span>
            </label>
        </div>
    </tags:form-row>
    <div id="groupA">
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="adaccount">AD帳號：</form:label>
            <form:input path="adaccount" cssClass="form-control d-inline-block num_eng_only" size="20" maxlength="20"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="regisname">姓名：</form:label>
            <form:input path="regisname" cssClass="form-control d-inline-block trimS" size="20" maxlength="33"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="regisidn">身分證字號/護照號碼：</form:label>
            <form:input path="regisidn" cssClass="form-control d-inline-block idn_passport" size="12" maxlength="10"/>(護照號碼開頭請加上#字號)
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="regissex">性別：</form:label>
            <label class="mb-0">
                <form:radiobutton path="regissex" value="G"/>
                <span>男</span>
            </label>
            <label class="mb-0">
                <form:radiobutton path="regissex" value="F"/>
                <span>女</span>
            </label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="regisbrth">生日：</form:label>
            <form:input path="regisbrth" cssClass="form-control d-inline-block date dateTW" size="12" maxlength="7"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-10">
            <form:label cssClass="col-form-label star" path="regisemail">E-mail：</form:label>
            <form:input path="regisemail" cssClass="form-control d-inline-block mail" size="50" maxlength="100"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="regisphone">聯絡電話：</form:label>
            <form:input path="regisphone" cssClass="form-control d-inline-block tel trimS" size="12" maxlength="20"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="jogtitle">職稱：</form:label>
            <form:input path="jogtitle" cssClass="form-control d-inline-block trimS" size="20" maxlength="33"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="degreen">學位學分：</form:label>
            <form:select path="degreen" cssClass="form-control d-inline-block">
                <form:option value="">請選擇</form:option>
                <form:options items="${caseData.degreenCombobox}" />
            </form:select>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-10">
            <form:label cssClass="col-form-label star" path="dept">部門：</form:label>
            <form:input path="dept" cssClass="form-control d-inline-block trimS " size="50" maxlength="33"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-10">
            <form:label cssClass="col-form-label" path="regisaddres">聯絡地址：</form:label>
            <form:input path="regisaddres" cssClass="form-control d-inline-block" size="60" maxlength="66"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="mealstatus">用餐狀況：</form:label>
            <label class="mb-0">
                <form:radiobutton path="mealstatus" value="N"/>
                <span>不用餐</span>
            </label>
            <label class="mb-0">
                <form:radiobutton path="mealstatus" value="M"/>
                <span>用餐-葷</span>
            </label>
            <label class="mb-0">
                <form:radiobutton path="mealstatus" value="V"/>
                <span>用餐-素</span>
            </label>
        </div>
    </tags:form-row>
    </div>
    <div id="groupB">
    <tags:form-row>
        <form:label cssClass="col-form-label" path="file">檔案上傳：</form:label>
        <tags:button id="btnDownload" cssClass="btn-sm">
            下載範本
        </tags:button>
    </tags:form-row>
    <tags:form-row>
        <form:label cssClass="col-form-label" path="file"></form:label>
        <form:input path="file" type="file"/>
    </tags:form-row>
    </div>
    <tags:form-note>
    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
    </tags:form-note>
    <form:hidden path="orformno"/>
    <form:hidden path="topicname"/>
    <form:hidden path="mode"/>
</form:form>
</tags:fieldset>
</jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function(){
        $('#btnSave').click(function(e){
            e.preventDefault();
            let regType = $('input[name="regType"]:checked').val();
            if(regType==='S') {
                $('#eip04w020Form').attr('action', '<c:url value="/Eip04w020_manualRegSingle.action" />').submit();
            } else if (regType==='B'){
                if ($.trim($('#file').val()).length === 0) {
                    showAlert("「檔案上傳」為必填");
                } else {
                    $('#eip04w020Form').attr('action', '<c:url value="/Eip04w020_manualRegBatch.action" />').submit();
                }
            }
        })
        $('#btnDownload').click(function(e){
            e.preventDefault();
            $('#eip04w020Form').attr('action', '<c:url value="/Eip04w020_downloadExample.action" />').submit();
        })
        $('#btnReply').click(function(e){
            e.preventDefault();
            $('#eip04w020Form').attr('action', '<c:url value="/Eip04w020_enter.action" />').submit();
        })

        $('#adaccount').change(function (){
            if($(this).val()) {
                $.ajax({
                    type : "POST",
                    url : '<c:url value='/Eip04w020_getInfo.action'/>',
                    data: {
                    'adaccount': $(this).val()
                    },
                    timeout : 100000,
                    success : function(data) {
                        $('#regisname').val(data.name);
                        $('#regisemail').val(data.email);
                        $('#regisphone').val(data.tel);
                        $('#jogtitle').val(data.title);
                        $('#dept').val(data.dept);
                    },
                    error : function(e) {
                        showAlert("取得基本資料失敗");
                    }
                });
            }
        })

        function changeRegType(regType){
            if(regType==='S') {
                $('#groupA').show();
                $('#groupB').hide();
            } else if (regType==='B'){
                $('#groupA').hide();
                $('#groupB').show();
            }
        }

        $('input[name="regType"]').change(function(){
            changeRegType($(this).val());
        });

        //初始化
        $('#groupB').hide();
        changeRegType($('input[name="regType"]:checked').val());
    })
</script>
</jsp:attribute>
</tags:layout>