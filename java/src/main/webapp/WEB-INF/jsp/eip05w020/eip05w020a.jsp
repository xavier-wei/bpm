<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip05w020Controller).CASE_KEY" />
<spring:eval var="themeKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip05w020Controller).THEME_CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<c:set var="themeData" value="${requestScope[themeKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
        .col-form-label {
            min-width: 250px;
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
<tags:fieldset legend="${caseData.mode eq 'A' ? '新增' : '修改'}意見調查主題">
<form:form id="eip05w020Form" modelAttribute="${themeKey}" >
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="osccode">意見調查類別：</form:label>
            <form:select path="osccode" cssClass="form-control d-inline-block">
                <form:option value="">請選擇</form:option>
                <form:options items="${caseData.osccodeCombobox}" />
            </form:select>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="topicname">主題名稱：</form:label>
            <form:input path="topicname" cssClass="form-control d-inline-block" placeholder="最長100字" size="40" maxlength="100"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="topicname">開始時間：</form:label>
            <form:input path="osfmdt" cssClass="form-control d-inline-block dateTW cdate" placeholder="日期" maxlength="9" size="10"/>
            <form:input path="osfmdtHour" cssClass="form-control d-inline-block num_only padL" placeholder="時" size="3" maxlength="2"/>：
            <form:input path="osfmdtMinute" cssClass="form-control d-inline-block num_only padL" placeholder="分" size="3" maxlength="2"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="topicname">結束時間：</form:label>
            <form:input path="osendt" cssClass="form-control d-inline-block dateTW cdate" placeholder="日期" maxlength="9" size="10"/>
            <form:input path="osendtHour" cssClass="form-control d-inline-block num_only padL" placeholder="時" size="3" maxlength="2"/>：
            <form:input path="osendtMinute" cssClass="form-control d-inline-block num_only padL" placeholder="分" size="3" maxlength="2"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12 d-flex">
            <form:label cssClass="col-form-label mr-1 star" path="topicdesc">說明：</form:label>
            <form:textarea path="topicdesc" cssClass="form-control col-md-6" rows="5" maxlength="1333"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="organizer">主辦單位：</form:label>
            <form:input path="organizer" cssClass="form-control d-inline-block" placeholder="最長25字" size="40" maxlength="25"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12 d-flex">
            <form:label cssClass="col-form-label mr-1 star" path="promptmsg">使用者填寫後送出之提示訊息：</form:label>
            <form:textarea path="promptmsg" cssClass="form-control col-md-6" rows="5" maxlength="1333"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="isdisstatic">是否立即呈現投票統計結果：</form:label>
            <label>
                <form:radiobutton path="isdisstatic" value="Y"/>
                <span class="font-weight-bold">是</span>
            </label>
            <label>
                <form:radiobutton path="isdisstatic" value="N"/>
                <span class="font-weight-bold">否</span>
            </label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="isdisstatic">是否限制一次作答：</form:label>
            <label>
                <form:radiobutton path="islimitone" value="Y"/>
                <span class="font-weight-bold">是</span>
            </label>
            <label>
                <form:radiobutton path="islimitone" value="N"/>
                <span class="font-weight-bold">否</span>
            </label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="isanonymous">是否匿名投票：</form:label>
            <label>
                <form:radiobutton path="isanonymous" value="Y"/>
                <span class="font-weight-bold">是</span>
            </label>
            <label>
                <form:radiobutton path="isanonymous" value="N"/>
                <span class="font-weight-bold">否</span>
            </label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label star" path="limitvote">投票對象限制：</form:label>
            <label class="font-weight-bold"><input type="checkbox" id="selectAll">會內</label>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="limitvote"></form:label>
            <label class="font-weight-bold">單位</label>
        </div>
        <c:forEach items="${caseData.limitvoteCheckboxU}" var="item" varStatus="status">
                <c:if test="${status.index % 5 == 0}">
                    <div class="col-md-12">
                    <form:label cssClass="col-form-label" path="limitvote"></form:label>
                </c:if>
                <label class="mb-0">
                    <div class="form-check-inline">
                        <form:checkbox path="limitvote" cssClass="form-check" value="${item.key}"/>${item.value}
                    </div>
                </label>
                <c:if test="${status.index % 5 == 4 || status.last}">
                    </div>
                </c:if>
        </c:forEach>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="limitvote"></form:label>
            <label class="font-weight-bold">職員</label>
        </div>
        <c:forEach items="${caseData.limitvoteCheckboxE1}" var="item" varStatus="status">
                <c:if test="${status.index % 7 == 0}">
                    <div class="col-md-12">
                    <form:label cssClass="col-form-label" path="limitvote"></form:label>
                </c:if>
                <label class="mb-0">
                    <div class="form-check-inline">
                        <form:checkbox path="limitvote" cssClass="form-check" value="${item.key}"/>${item.value}
                    </div>
                </label>
                <c:if test="${status.index % 7 == 6 || status.last}">
                    </div>
                </c:if>
        </c:forEach>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="limitvote"></form:label>
            <label class="font-weight-bold">聘僱人員</label>
        </div>
        <c:forEach items="${caseData.limitvoteCheckboxE2}" var="item" varStatus="status">
                <c:if test="${status.index % 7 == 0}">
                    <div class="col-md-12">
                    <form:label cssClass="col-form-label" path="limitvote"></form:label>
                </c:if>
                <label class="mb-0">
                    <div class="form-check-inline">
                        <form:checkbox path="limitvote" cssClass="form-check" value="${item.key}"/>${item.value}
                    </div>
                </label>
                <c:if test="${status.index % 7 == 6 || status.last}">
                    </div>
                </c:if>
        </c:forEach>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="limitvote"></form:label>
            <label class="font-weight-bold">工友</label>
        </div>
        <c:forEach items="${caseData.limitvoteCheckboxE3}" var="item" varStatus="status">
                <c:if test="${status.index % 7 == 0}">
                    <div class="col-md-12">
                    <form:label cssClass="col-form-label" path="limitvote"></form:label>
                </c:if>
                <label class="mb-0">
                    <div class="form-check-inline">
                        <form:checkbox path="limitvote" cssClass="form-check" value="${item.key}"/>${item.value}
                    </div>
                </label>
                <c:if test="${status.index % 7 == 6 || status.last}">
                    </div>
                </c:if>
        </c:forEach>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="limitvote"></form:label>
            <label class="font-weight-bold">勞基法人員</label>
        </div>
        <c:forEach items="${caseData.limitvoteCheckboxE4}" var="item" varStatus="status">
                <c:if test="${status.index % 7 == 0}">
                    <div class="col-md-12">
                    <form:label cssClass="col-form-label" path="limitvote"></form:label>
                </c:if>
                <label class="mb-0">
                    <div class="form-check-inline">
                        <form:checkbox path="limitvote" cssClass="form-check" value="${item.key}"/>${item.value}
                    </div>
                </label>
                <c:if test="${status.index % 7 == 6 || status.last}">
                    </div>
                </c:if>
        </c:forEach>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="mailsubject">調查通知信件主旨：</form:label>
            <form:input path="mailsubject" cssClass="form-control d-inline-block" size="40" maxlength="33"/>
        </div>
    </tags:form-row>
    <tags:form-row>
        <div class="col-md-12 d-flex">
            <form:label cssClass="col-form-label mr-1" path="mailmsg">調查通知信件內容：</form:label>
            <form:textarea path="mailmsg" cssClass="form-control col-md-6" rows="5" maxlength="1333"/>
        </div>
    </tags:form-row>
    <c:if test="${caseData.mode eq 'U'}">
        <tags:form-row>
            <div class="col-md-12">
                <form:label cssClass="col-form-label" path="status">狀態：</form:label>
                <c:out value="${themeData.status}"/>
            </div>
        </tags:form-row>
        <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="creuser">建立人員/時間：</form:label>
            <c:out value="${themeData.creuser}"/>&nbsp;&nbsp;<c:out value="${themeData.credt}"/>
        </div>
        </tags:form-row>
        <tags:form-row>
        <div class="col-md-12">
            <form:label cssClass="col-form-label" path="upduser">更新人員/時間：</form:label>
            <c:out value="${themeData.upduser}"/>&nbsp;&nbsp;<c:out value="${themeData.upddt}"/>
        </div>
        </tags:form-row>
    </c:if>
    <tags:form-note>
    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
    </tags:form-note>
    <form:hidden path="mode"/>
    <form:hidden path="osformno"/>
    <form:hidden path="status"/>
    <form:hidden path="creuser"/>
    <form:hidden path="credt"/>
    <form:hidden path="upduser"/>
    <form:hidden path="upddt"/>
</form:form>
</tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    $('#btnSave').click(function(e){
        e.preventDefault();
        $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_confirm.action" />').submit();
    })
    $('#btnReply').click(function(e){
        e.preventDefault();
        $('#eip05w020Form').attr('action', '<c:url value="/Eip05w020_enter.action" />').submit();
    })

    //全選
    $("#selectAll").click(function() {
        if ($("#selectAll").is(":checked")) {
            $("input[name='limitvote']").each(function () {
                $(this).prop("checked", true);
            });
        } else {
            $("input[name='limitvote']").each(function () {
                $(this).prop("checked", false);
            });
        }
    });

    $("input[name='limitvote']").click(function() {
        var numberOfChecked = $("input[name='limitvote']:checked").length;
        var allcheckbox = $("input[name='limitvote']").length;
        if (numberOfChecked === allcheckbox) {
            $("#selectAll").prop("checked",true);
        }else {
            $("#selectAll").prop("checked",false);
        }
    });

    if ($("input[name='limitvote']:checked").length === $("input[name='limitvote']").length) {
        $("#selectAll").prop("checked",true);
    }else {
        $("#selectAll").prop("checked",false);
    }
})
</script>
</jsp:attribute>
</tags:layout>