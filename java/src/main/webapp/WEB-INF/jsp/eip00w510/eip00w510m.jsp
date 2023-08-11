<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip00w510Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="heads">
    <style>
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
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="意見調查分類列表">
    <form:form id="eip00w510Form" name="eip00w510Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="table-responsive">
                <table class="table" id="tb1">
                    <thead data-orderable="true">
                    <tr>
                        <th data-orderable="false" class="text-center align-middle"><input type="checkbox" id="selectAll"/><label for="selectAll" class="d-inline">全選</label></th>
                        <th class="text-center align-middle">項次</th>
                        <th class="text-center align-middle">類別名稱</th>
                        <th class="text-center align-middle">上次異動時間</th>
                        <th class="text-center align-middle">操作區</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.oscList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle"><form:checkbox path="osccodeList[${status.index}]" data-starting="${item.starting}" value="${item.osccode}"/></td>
                                <td class="text-center align-middle"><c:out value="${status.index+1}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.oscname}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.upddt}" /></td>
                                <td data-osccode="${item.osccode}" class="text-center align-middle">
                                    <tags:button>修改</tags:button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
        <form:hidden path="mode"/>
        <form:hidden path="osccode"/>
        <tags:form-note>
        <tags:form-note-item>如所屬分類項目已被使用，將無法刪除。</tags:form-note-item>
        </tags:form-note>
    </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
    // dataTable 設定
    let config = getDataTablesConfig();

    $('#tb1').DataTable(config);
	$('#btnInsert').click(function(e){
        e.preventDefault();
        $('#mode').val('A');
		$('#eip00w510Form').attr('action', '<c:url value="/Eip00w510_selectAction.action" />').submit();
	})
    $('#btnDelete').click(function(e){
        e.preventDefault();
        if($("input[name^='osccodeList']:checked").length == 0) {
            showAlert("請至少選擇一項");
            return;
        }
        var isExistsStarting = false;
        $("input[name^='osccodeList']:checked").each(function () {
            if ($(this).data('starting')==true){
                isExistsStarting = true;
            }
        });
        if (isExistsStarting) {
            showAlert("所選分類已被使用，無法刪除！");
            return;
        }
        showConfirm('確定刪除嗎？',()=>{
            $('#eip00w510Form').attr('action', '<c:url value="/Eip00w510_delete.action" />').submit();
        });
    })
    $('#tb1 button').click(function(e){
        e.preventDefault();
        $('#mode').val('U');
        $('#osccode').val($(this).parent().data('osccode'));
        $('#eip00w510Form').attr('action', '<c:url value="/Eip00w510_selectAction.action" />').submit();
    });
    //全選
    $("#selectAll").click(function() {
        if ($("#selectAll").is(":checked")) {
            $("input[name^='osccodeList']").each(function () {
                $(this).prop("checked", true);
            });
        } else {
            $("input[name^='osccodeList']").each(function () {
                $(this).prop("checked", false);
            });
        }
    });
    $("input[name^='osccodeList']").click(function() {
        var numberOfChecked = $("input[name^='osccodeList']:checked").length;
        var allcheckbox = $("input[name^='osccodeList']").length;
        if (numberOfChecked === allcheckbox) {
            $("#selectAll").prop("checked",true);
        }else {
            $("#selectAll").prop("checked",false);
        }
    });
})
</script>
</jsp:attribute>
</tags:layout>