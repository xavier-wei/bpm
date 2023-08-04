<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w420Controller).CASE_KEY" />
<spring:eval var="modifyKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w420Controller).MODIFY_CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<c:set var="modifyData" value="${requestScope[modifyKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        .col-form-label {
            min-width: 150px;
        }
        .btn {
            vertical-align: baseline;
        }
        .hidden-span {
            display: none;
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
<tags:fieldset legend="${caseData.mode eq 'A' ? '新增' : '修改'}線上報名">
<form:form id="eip00w420Form" modelAttribute="${modifyKey}" enctype="multipart/form-data" method="post">
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="orccode">線上報名類別：</form:label>
            <form:select path="orccode" cssClass="form-control d-inline-block" multiple="false">
                <form:option value="">請選擇</form:option>
                <form:options items="${caseData.orccodeCombobox}" />
            </form:select>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="courseclacode">課程類別代碼：</form:label>
            <form:input path="courseclacode" cssClass="form-control d-inline-block num_only" size="5" maxlength="4"/>(最長4字)
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="coursecode">課程代碼：</form:label>
            <form:input path="coursecode" cssClass="form-control d-inline-block num_eng_only" size="20" maxlength="20"/>(最長20字)
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="classcode">班別代碼：</form:label>
            <form:input path="classcode" cssClass="form-control d-inline-block num_eng_only" size="10" maxlength="10"/>(最長10字)
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="period">期別：</form:label>
            <form:input path="period" cssClass="form-control d-inline-block num_only" size="4" maxlength="3"/>(最長3字)
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="topicname">名稱：</form:label>
            <form:input path="topicname" cssClass="form-control d-inline-block" size="50" maxlength="50"/>(最長50字)
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="regisfmdt">報名開始時間：</form:label>
            <form:input path="regisfmdt" cssClass="form-control d-inline-block dateTW date" size="10" maxlength="7"/>
            <form:input path="regisfmdtHour" cssClass="form-control d-inline-block num_only padL" size="3" maxlength="2"/>時
            <form:input path="regisfmdtMinute" cssClass="form-control d-inline-block num_only padL" size="3" maxlength="2"/>分
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="regisendt">報名結束時間：</form:label>
            <form:input path="regisendt" cssClass="form-control d-inline-block dateTW date" size="10" maxlength="7"/>
            <form:input path="regisendtHour" cssClass="form-control d-inline-block num_only padL" size="3" maxlength="2"/>時
            <form:input path="regisendtMinute" cssClass="form-control d-inline-block num_only padL" size="3" maxlength="2"/>分
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="organizer">主辦單位：</form:label>
            <form:input path="organizer" cssClass="form-control d-inline-block" size="50" maxlength="50"/>(最長50字)
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="contacter">聯絡人：</form:label>
            <form:input path="contacter" cssClass="form-control d-inline-block" size="20" maxlength="6"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="contactnum">聯絡電話：</form:label>
            <form:input path="contactnum" cssClass="form-control d-inline-block tel" size="20" maxlength="20"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="fax">傳真：</form:label>
            <form:input path="fax" cssClass="form-control d-inline-block tel" size="20" maxlength="20"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="email">E-mail：</form:label>
            <form:input path="email" cssClass="form-control d-inline-block mail " size="50" maxlength="100"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="addres">地點：</form:label>
            <form:input path="addres" cssClass="form-control d-inline-block" size="50" maxlength="50"/>(最長50字)
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="country">上課縣市：</form:label>
            <form:input path="country" cssClass="form-control d-inline-block num_only" size="10" maxlength="2"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="profmdt">辦理開始時間：</form:label>
            <form:input path="profmdt" cssClass="form-control d-inline-block dateTW date" size="10" maxlength="7"/>
            <form:input path="profmdtHour" cssClass="form-control d-inline-block num_only padL" size="3" maxlength="2"/>時
            <form:input path="profmdtMinute" cssClass="form-control d-inline-block num_only padL" size="3" maxlength="2"/>分
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="proendt">辦理結束時間：</form:label>
            <form:input path="proendt" cssClass="form-control d-inline-block dateTW date" size="10" maxlength="7"/>
            <form:input path="proendtHour" cssClass="form-control d-inline-block num_only padL" size="3" maxlength="2"/>時
            <form:input path="proendtMinute" cssClass="form-control d-inline-block num_only padL" size="3" maxlength="2"/>分
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="acceptappnum">接受報名人數：</form:label>
            <form:input path="acceptappnum" cssClass="form-control d-inline-block num_only" size="4" maxlength="3"/>人
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="allowappnum">允許報名人數：</form:label>
            <form:input path="allowappnum" cssClass="form-control d-inline-block num_only" size="4" maxlength="3"/>人
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="allowappway">允許報名方式：</form:label>
            <label id="chk1" class="mb-0">
                <div class="form-check-inline">
                    <form:checkbox path="allowappway" cssClass="form-check" value="I"/>網路
                </div>
            </label>
            <label id="chk2" class="mb-0">
                <div class="form-check-inline">
                    <form:checkbox path="allowappway" cssClass="form-check" value="E"/>E-mail
                </div>
            </label>
            <label id="chk3" class="mb-0">
                <div class="form-check-inline">
                    <form:checkbox path="allowappway" cssClass="form-check" value="F"/>傳真
                </div>
            </label>
            <label id="chk4" class="mb-0">
                <div class="form-check-inline">
                    <form:checkbox path="allowappway" cssClass="form-check" value="T"/>電話
                </div>
            </label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="fee">費用：</form:label>
            <form:input path="fee" cssClass="form-control d-inline-block num_only" size="7" maxlength="7"/>元
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="account">報名繳費帳號：</form:label>
            <form:input path="account" cssClass="form-control d-inline-block tel" size="30" maxlength="30"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="ismeals">提供餐點：</form:label>
            <label class="mb-0">
                <form:radiobutton path="ismeals" value="Y" cssClass="mr-1"/>
                <span class="font-weight-bold mr-2">是</span>
            </label>
            <label class="mb-0">
                <form:radiobutton path="ismeals" value="N" cssClass="mr-1"/>
                <span class="font-weight-bold mr-2">否</span>
            </label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label star" path="classhours">上課時數：</form:label>
            <form:input path="classhours" cssClass="form-control d-inline-block num_only" size="3" maxlength="3"/>
            <form:select path="classhoursUnit" cssClass="form-control d-inline-block" multiple="false">
                <form:option value="H">小時</form:option>
                <form:option value="D">天</form:option>
                <form:option value="C">學分</form:option>
            </form:select>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="certihours">認證時數：</form:label>
            <form:select path="certihoursType1" cssClass="form-control d-inline-block" multiple="false">
                <form:option value="P">實體</form:option>
                <form:option value="D">數位</form:option>
            </form:select>
            <form:select path="certihoursType2" cssClass="form-control d-inline-block" multiple="false">
                <form:option value="M">上午</form:option>
                <form:option value="A">下午</form:option>
            </form:select>
            <form:input path="certihoursType3" cssClass="form-control d-inline-block num_only" size="3" maxlength="2"/>
            <tags:button id="btnAdd">新增</tags:button>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="certihours"></form:label>
            <span id="certihoursText"></span>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="lecturercode">講師代號：</form:label>
            <form:input path="lecturercode" cssClass="form-control d-inline-block num_eng_only" size="30" maxlength="30"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-6">
            <form:label cssClass="col-form-label" path="subject">主旨：</form:label>
            <form:input path="subject" cssClass="form-control d-inline-block" size="30" maxlength="33"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12 d-flex">
            <form:label cssClass="col-form-label mr-1" path="passmsg">報名審核通過Email<br>之通知訊息：</form:label>
            <form:textarea path="passmsg" cssClass="form-control col-md-6" rows="5" maxlength="333"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12 d-flex">
            <form:label cssClass="col-form-label" path="rejectmst">報名審核不通過Email<br>之通知訊息：</form:label>
            <form:textarea path="rejectmst" cssClass="form-control col-md-6" rows="5" maxlength="333"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="regisqual">報名資格：</form:label>
            <label class="font-weight-bold"><input type="checkbox" id="selectAll">會內</label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="regisqual"></form:label>
            <label class="font-weight-bold">單位</label>
        </div>
        <c:forEach items="${caseData.regisqualCheckboxU}" var="item" varStatus="status">
                <c:if test="${status.index % 5 == 0}">
                    <div class="col-md-12">
                    <form:label cssClass="col-form-label" path="regisqual"></form:label>
                </c:if>
                <label class="mb-0">
                    <div class="form-check-inline">
                        <form:checkbox path="regisqual" cssClass="form-check" value="${item.key}"/>${item.value}
                    </div>
                </label>
                <c:if test="${status.index % 5 == 4 || status.last}">
                    </div>
                </c:if>
        </c:forEach>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="regisqual"></form:label>
            <label class="font-weight-bold">職員</label>
        </div>
        <c:forEach items="${caseData.regisqualCheckboxE1}" var="item" varStatus="status">
                <c:if test="${status.index % 7 == 0}">
                    <div class="col-md-12">
                    <form:label cssClass="col-form-label" path="regisqual"></form:label>
                </c:if>
                <label class="mb-0">
                    <div class="form-check-inline">
                        <form:checkbox path="regisqual" cssClass="form-check" value="${item.key}"/>${item.value}
                    </div>
                </label>
                <c:if test="${status.index % 7 == 6 || status.last}">
                    </div>
                </c:if>
        </c:forEach>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="regisqual"></form:label>
            <label class="font-weight-bold">聘僱人員</label>
        </div>
        <c:forEach items="${caseData.regisqualCheckboxE2}" var="item" varStatus="status">
                <c:if test="${status.index % 7 == 0}">
                    <div class="col-md-12">
                    <form:label cssClass="col-form-label" path="regisqual"></form:label>
                </c:if>
                <label class="mb-0">
                    <div class="form-check-inline">
                        <form:checkbox path="regisqual" cssClass="form-check" value="${item.key}"/>${item.value}
                    </div>
                </label>
                <c:if test="${status.index % 7 == 6 || status.last}">
                    </div>
                </c:if>
        </c:forEach>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="regisqual"></form:label>
            <label class="font-weight-bold">工友</label>
        </div>
        <c:forEach items="${caseData.regisqualCheckboxE3}" var="item" varStatus="status">
                <c:if test="${status.index % 7 == 0}">
                    <div class="col-md-12">
                    <form:label cssClass="col-form-label" path="regisqual"></form:label>
                </c:if>
                <label class="mb-0">
                    <div class="form-check-inline">
                        <form:checkbox path="regisqual" cssClass="form-check" value="${item.key}"/>${item.value}
                    </div>
                </label>
                <c:if test="${status.index % 7 == 6 || status.last}">
                    </div>
                </c:if>
        </c:forEach>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="regisqual"></form:label>
            <label class="font-weight-bold">勞基法人員</label>
        </div>
        <c:forEach items="${caseData.regisqualCheckboxE4}" var="item" varStatus="status">
                <c:if test="${status.index % 7 == 0}">
                    <div class="col-md-12">
                    <form:label cssClass="col-form-label" path="regisqual"></form:label>
                </c:if>
                <label class="mb-0">
                    <div class="form-check-inline">
                        <form:checkbox path="regisqual" cssClass="form-check" value="${item.key}"/>${item.value}
                    </div>
                </label>
                <c:if test="${status.index % 7 == 6 || status.last}">
                    </div>
                </c:if>
        </c:forEach>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12 d-flex">
            <form:label cssClass="col-form-label mr-1" path="topicdesc">內容說明：</form:label>
            <form:textarea path="topicdesc" cssClass="form-control col-md-6" rows="5" maxlength="1333"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12 d-flex">
            <form:label cssClass="col-form-label mr-1" path="remark">備註：</form:label>
            <form:textarea path="remark" cssClass="form-control col-md-6" rows="5" maxlength="1333"/>
        </div>
    </tags:form-row>
    <div id="fileRow">
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="files">附加檔案：</form:label>
            <form:input path="files" type="file"/>
            <tags:button id="btnAddUpload">增加檔案</tags:button>
            <tags:button id="btnDelUpload">刪除檔案</tags:button>
        </div>
    </tags:form-row>
    </div>
    <tags:form-row>
        <form:label cssClass="col-form-label mr-1" path="files"></form:label>

    </tags:form-row>
    <tags:form-note>
    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
    </tags:form-note>
    <form:hidden path="mode"/>
    <form:hidden path="certihours"/>
    <form:hidden path="orformno"/>
</form:form>
</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    var chvArray = new Array();
    var chsArray = new Array();
    $('#btnSave').click(function(e){
        e.preventDefault();
        $('#certihours').val(chvArray.join(','));
        $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_confirm.action" />').submit();
    })
    $('#btnReply').click(function(e){
        e.preventDefault();
        $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_enter.action" />').submit();
    })

    //組合字串供畫面顯示
    function composite () {
        $('#certihoursText').empty();
        chsArray = new Array();
        $.each(chvArray,function (i,e){
            let str1 = e.charAt(0) === 'P' ? '實體' : '數位';
            let str2 = e.charAt(1) === 'M' ? '上午' : '下午';
            let str3 = e.substring(2);
            chsArray.push('<lable>' + str3 + '時' + '(' + str1 + str2 + ')'
                + '<span style="display: none;">'+ e +'</span>'
                + '<span style="color: red;cursor: pointer;">X</span></lable>');

        })
        // showAlert(chsArray.join('、'));
        $('#certihoursText').append(chsArray.join('、'));
    }

    //新增認證時數
    $('#btnAdd').click(function () {
        if (!$('#certihoursType3').val()) {
            showAlert("認證時數不得為空", 'certihoursType3');
        } else {
            let chvalue = $('#certihoursType1').val() + $('#certihoursType2').val() + $('#certihoursType3').val();
            chvArray.push(chvalue);
            // $('#certihoursText').append(chstr);
            composite();
        }
    })

    //刪除認證時數
    $('#certihoursText').on('click', 'span', function() {
        var index = $.inArray($(this).prev().text(), chvArray);
        if (index !== -1) {
            chvArray.splice(index, 1);
        }
        composite();
        // alert(chvArray.join('、'));
    });

    //全選
    $("#selectAll").click(function() {
        if ($("#selectAll").is(":checked")) {
            $("input[name='regisqual']").each(function () {
                $(this).prop("checked", true);
            });
        } else {
            $("input[name='regisqual']").each(function () {
                $(this).prop("checked", false);
            });
        }
    });

    $("input[name='regisqual']").click(function() {
        var numberOfChecked = $("input[name='regisqual']:checked").length;
        var allcheckbox = $("input[name='regisqual']").length;
        if (numberOfChecked === allcheckbox) {
            $("#selectAll").prop("checked",true);
        }else {
            $("#selectAll").prop("checked",false);
        }
    });

    //增加檔案
    $("#btnAddUpload").click(function() {
        let addfile = '<div class="form-row py-1 no-gutters">'+
            '<div class="col-md-6">' +
            '<label for="files" class="col-form-label"></label> ' +
            '<input id="files" name="files" type="file" value>' +
            '</div>'+
            '</div>';
        $("#fileRow").append(addfile);
    });

    //增加檔案
    $("#btnDelUpload").click(function() {
        if ($("#fileRow").find(".form-row").length > 1) {
            $("#fileRow").find(".form-row:last").remove();
        } else {
            $("#files").val("");
        }
    });

    //初始化
    if ($('#certihours').val() != '') {
        chvArray = $.map($('#certihours').val().split(','), function(value) {
            return value;
        });
        composite();
    }

    if ($("input[name='regisqual']:checked").length === $("input[name='regisqual']").length) {
        $("#selectAll").prop("checked",true);
    }else {
        $("#selectAll").prop("checked",false);
    }
})
</script>
</jsp:attribute>
</tags:layout>