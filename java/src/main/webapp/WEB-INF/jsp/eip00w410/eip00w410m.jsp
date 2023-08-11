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
<!-- 選擇頁 -->
    <tags:button id="btnInsert">
        新增<i class="fas fa-user-plus"></i>
    </tags:button>          
    <tags:button id="btnDelete">
        刪除<i class="fas fa-trash-alt"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset legend="線上報名分類列表">
    <form:form id="eip00w410Form" name="eip00w410Form" modelAttribute="${caseKey}" method="POST">
        <tags:form-row>
            <div class="table-responsive">
                <table class="table" id="tb1">
                    <thead data-orderable="true">
                    <tr>
                        <th data-orderable="false" class="text-center align-middle"><input type="checkbox" id="selectAll"/><label for="selectAll" class="d-inline">全選</label></th>
                        <th class="text-center align-middle">項次</th>
                        <th class="text-center align-middle">分類名稱</th>
                        <th class="text-center align-middle">是否為課程</th>
                        <th class="text-center align-middle">簽到表格式</th>
                        <th class="text-center align-middle">操作區</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caseData.orcList}" var="item" varStatus="status">
                            <tr>
                                <td class="text-center align-middle"><form:checkbox path="orccodeList[${status.index}]" data-starting="${item.starting}" value="${item.orccode}"/></td>
                                <td class="text-center align-middle"><c:out value="${status.index+1}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.orcname}" /></td>
                                <td class="text-center align-middle"><c:out value="${item.iscourse}" /></td>
                                <td class="text-left align-middle"><c:out value="${item.signform}" /></td>
                                <td data-orccode="${item.orccode}" class="text-center align-middle">
                                    <tags:button>修改</tags:button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </tags:form-row>
        <form:hidden path="mode"/>
        <form:hidden path="orccode"/>
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
		$('#eip00w410Form').attr('action', '<c:url value="/Eip00w410_selectAction.action" />').submit();
	})
    $('#btnDelete').click(function(e){
        e.preventDefault();
        if($("input[name^='orccodeList']:checked").length == 0) {
            showAlert("請至少選擇一項");
            return;
        }
        var isExistsStarting = false;
        $("input[name^='orccodeList']:checked").each(function () {
            if ($(this).data('starting')==true){
                isExistsStarting = true;
            }
        });
        if (isExistsStarting) {
            showAlert("所選分類已被使用，無法刪除！");
            return;
        }
        showConfirm('確定刪除嗎？',()=>{
            $('#eip00w410Form').attr('action', '<c:url value="/Eip00w410_delete.action" />').submit();
        });
    })
    $('#tb1 button').click(function(e){
        e.preventDefault();
        $('#mode').val('U');
        $('#orccode').val($(this).parent().data('orccode'));
        $('#eip00w410Form').attr('action', '<c:url value="/Eip00w410_selectAction.action" />').submit();
    });
    //全選
    $("#selectAll").click(function() {
        if ($("#selectAll").is(":checked")) {
            $("input[name^='orccodeList']").each(function () {
                $(this).prop("checked", true);
            });
        } else {
            $("input[name^='orccodeList']").each(function () {
                $(this).prop("checked", false);
            });
        }
    });
    $("input[name^='orccodeList']").click(function() {
        var numberOfChecked = $("input[name^='orccodeList']:checked").length;
        var allcheckbox = $("input[name^='orccodeList']").length;
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