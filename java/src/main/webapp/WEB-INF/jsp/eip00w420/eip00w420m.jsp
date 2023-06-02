<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w420Controller).CASE_KEY" />
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
<!-- 選擇頁 -->
    <tags:button id="btnInsert">
        新增<i class="fas fa-user-plus"></i>
    </tags:button>          
    <tags:button id="btnDelete">
        刪除<i class="fas fa-trash-alt"></i>
    </tags:button>
    <tags:button id="btnPut">
        上架<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnDisabled">
        停辦<i class="fas fa-user-times"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="查詢條件">
    <form:form id="eip00w420Form" name="eip00w420Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="col-md-3">
                <form:label cssClass="col-form-label" path="qKeyword">關鍵字：</form:label>
                <form:input path="qKeyword" cssClass="form-control d-inline-block" size="16" maxlength="50"/>
            </div>
            <div class="col-md-3">
                <form:label cssClass="col-form-label" path="qStatus">活動狀態：</form:label>
                <form:select path="qStatus" cssClass="form-control d-inline-block" multiple="false">
                    <form:option value="">請選擇</form:option>
                    <form:options items="${caseData.statusCombobox}" />
                </form:select>
            </div>
            <div class="col-md-5">
                <form:label cssClass="col-form-label" path="qStartYear">活動區間：</form:label>
                <form:input path="qStartYear" cssClass="form-control d-inline-block padL" size="4" maxlength="3"/>年
                <form:input path="qStartMonth" cssClass="form-control d-inline-block padL" size="3" maxlength="2"/>月
                <span class="">~</span>
                <form:input path="qEndYear" cssClass="form-control d-inline-block padL" size="4" maxlength="3"/>年
                <form:input path="qEndMonth" cssClass="form-control d-inline-block padL" size="3" maxlength="2"/>月
            </div>
            <div class="col-md-1">
                <tags:button id="btnSearch">
                    查詢<i class="fas fa-search"></i>
                </tags:button>
            </div>
        </tags:form-row>
        <tags:form-row>
            <div class="table-responsive">
                <table class="table" id="tb1">
                    <thead data-orderable="true">
                    <tr>
                        <th data-orderable="false" class="text-center align-middle"><input type="checkbox" id="selectAll"/><label class="d-inline" for="selectAll">全選</label></th>
                        <th class="text-center align-middle">項次</th>
                        <th class="text-center align-middle">分類名稱</th>
                        <th class="text-center align-middle">主題名稱</th>
                        <th class="text-center align-middle">狀態</th>
                        <th class="text-center align-middle">接受報名人數</th>
                        <th class="text-center align-middle">已報名人數</th>
                        <th class="text-center align-middle">通過審核人數</th>
                        <th class="text-center align-middle">報名<br>開始/結束日期</th>
                        <th data-orderable="false" class="text-center align-middle">操作區</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.orList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle"><form:checkbox path="orformnoList[${status.index}]" data-status="${item.statusVal}" value="${item.orformno}"/></td>
                                <td class="text-center align-middle"><c:out value="${status.index+1}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.orcname}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.topicname}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.status}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.acceptappnum}" /></td>
                                <td class="text-center align-middle"><abbr title="${item.actualappnumAbbr}"><c:out value="${item.actualappnum}" /></abbr></td>
                                <td class="text-center align-middle"><abbr title="${item.passnumAbbr}"><c:out value="${item.passnum}" /></abbr></td>
                                <td class="text-center align-middle"><c:out value="${item.regisfmdt}" /><br><c:out value="${item.regisendt}" /></td>
                                <td class="text-center align-middle">
                                    <form:select path="selectAction" cssClass="form-control d-inline-block" data-orformno="${item.orformno}" data-status="${item.statusVal}" cssStyle="max-width: 145px;" multiple="false">
                                        <form:option value="M">人工報名</form:option>
                                        <form:option value="V">報名審核</form:option>
                                        <form:option value="A">時數認證</form:option>
                                        <form:option value="H">修改歷程</form:option>
                                        <form:option value="C">複製</form:option>
                                        <form:option value="U">修改</form:option>
                                        <form:option value="P">簽到簿列印</form:option>
                                        <form:option value="E1">匯出學習時數</form:option>
                                        <form:option value="E2">匯出報名資料</form:option>
                                    </form:select>
                                    <tags:button id="btnExcute">執行</tags:button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
        <form:hidden path="mode"/>
        <form:hidden path="orformno"/>
    </form:form>
    <tags:form-note>
    <tags:form-note-item>操作區選單允許執行與否根據活動狀態決定。</tags:form-note-item>
    </tags:form-note>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    // dataTable 設定
    let config = getDataTablesConfig();
    config.pageLength = 20;
    $('#tb1').DataTable(config);
	$('#btnSearch').click(function(e){
        e.preventDefault();
		$('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_advQuery.action" />').submit();
	})
    $('#btnInsert').click(function(e){
        e.preventDefault();
        $('#mode').val('A');
        $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_selectInsertUpdate.action" />').submit();
    })

    $('#btnPut').click(function(e){
        e.preventDefault();
        if($("input[name^='orformnoList']:checked").length == 0) {
            showAlert("請至少勾選一項");
            return;
        }
        let isAllowPut = true;
        $("input[name^='orformnoList']:checked").each(function () {
            let status = $(this).data('status');
            if ($.inArray(status, ["N"]) < 0){
                isAllowPut = false;
            }
        });
        if (!isAllowPut) {
            showAlert("所選活動需為「已建檔」，才可上架！");
            return;
        }
        $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_put.action" />').submit();
    })

    $('#btnDelete').click(function(e){
        e.preventDefault();
        if($("input[name^='orformnoList']:checked").length == 0) {
            showAlert("請至少勾選一項");
            return;
        }
        let isAllowDel = true;
        $("input[name^='orformnoList']:checked").each(function () {
            let status = $(this).data('status');
            if ($.inArray(status, ["N","P"]) < 0){
                isAllowDel = false;
            }
        });
        if (!isAllowDel) {
            showAlert("所選活動需為「已建檔」或「上架中」，才可進行刪除！");
            return;
        }
        showConfirm('確定刪除嗎？',()=>{
            $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_delete.action" />').submit();
        });
    })

    $('#btnDisabled').click(function(e){
        e.preventDefault();
        if($("input[name^='orformnoList']:checked").length == 0) {
            showAlert("請至少勾選一項");
            return;
        }
        let isAllowDis = true;
        $("input[name^='orformnoList']:checked").each(function () {
            let status = $(this).data('status');
            if ($.inArray(status, ["A","I"]) < 0){
                isAllowDis = false;
            }
        });
        if (!isAllowDis) {
            showAlert("所選活動需為「報名中」或「進行中」，才可停辦！");
            return;
        }
        showConfirm('確定停辦嗎？',()=>{
            $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_disabled.action" />').submit();
        });
    })

    //全選
    $("#selectAll").click(function() {
        if ($("#selectAll").is(":checked")) {
            $("input[name^='orformnoList']").each(function () {
                $(this).prop("checked", true);
            });
        } else {
            $("input[name^='orformnoList']").each(function () {
                $(this).prop("checked", false);
            });
        }
    });

    $("input[name^='orformnoList']").click(function() {
        var numberOfChecked = $("input[name^='orformnoList']:checked").length;
        var allcheckbox = $("input[name^='orformnoList']").length;
        if (numberOfChecked === allcheckbox) {
            $("#selectAll").prop("checked",true);
        }else {
            $("#selectAll").prop("checked",false);
        }
    });

    $('#tb1 button').click(function(e){
        e.preventDefault();
        $('#mode').val($(this).prev().val());
        $('#orformno').val($(this).prev().data('orformno'));
        if ($(this).prev().val() === 'U') {
            $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_selectInsertUpdate.action" />').submit();
        } else if ($(this).prev().val() === 'C') {
            $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_copy.action" />').submit();
        } else if ($(this).prev().val() === 'H') {
            $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_history.action" />').submit();
        } else if ($(this).prev().val() === 'V') {
            $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_verify.action" />').submit();
        } else if ($(this).prev().val() === 'A') {
            $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_certified.action" />').submit();
        } else if ($(this).prev().val() === 'P') {
            $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_printSigninList.action" />').submit();
        } else if ($(this).prev().val() === 'E1') {
            $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_printCSV.action" />').submit();
        } else if ($(this).prev().val() === 'E2') {
            $('#eip00w420Form').attr('action', '<c:url value="/Eip00w420_printExcel.action" />').submit();
        }
    });

    $("#tb1 select").change(function () {
        changeSelectAction($(this));
    });
    
    function changeSelectAction(el) {
        let option = el.val();
        if (el.data('status') === 'N') { //已建檔
            if(["M","V","A","P","E1","E2"].includes(option)){
                el.next().prop('disabled',true);
            } else {
                el.next().prop('disabled',false);
            }
        } else if (el.data('status') === 'P') {//上架中
            if(["M","V","A","P","E1","E2"].includes(option)){
                el.next().prop('disabled',true);
            } else {
                el.next().prop('disabled',false);
            }
        } else if (el.data('status') === 'A') {//報名中
            if(["A","E1"].includes(option)){
                el.next().prop('disabled',true);
            } else {
                el.next().prop('disabled',false);
            }
        } else if (el.data('status') === 'I') {//進行中
            if(["M","A","E1","U"].includes(option)){
                el.next().prop('disabled',true);
            } else {
                el.next().prop('disabled',false);
            }
        } else if (el.data('status') === 'C') {//已結束
            if(["M","V","P","U"].includes(option)){
                el.next().prop('disabled',true);
            } else {
                el.next().prop('disabled',false);
            }
        } else if (el.data('status') === 'D') {//停辦
            if(["M","V","A","P","E1","U"].includes(option)){
                el.next().prop('disabled',true);
            } else {
                el.next().prop('disabled',false);
            }
        }
    }

    $("#tb1 select").each(function (){
        changeSelectAction($(this));
    })
})
</script>
</jsp:attribute>
</tags:layout>