<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eipib0w050Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 明細頁 -->
    <tags:button id="btnInsert">
                        新增<i class="fas fa-user-plus"></i>
    </tags:button>          
    <tags:button id="btnDelete">
                        刪除<i class="fas fa-trash-alt"></i>
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
        <form:label cssClass="col-form-label star" path="codekind">主代號類別：</form:label>
        <div class="col-12 col-md">
        <c:if test="${caseData.codekind == null}">
            <form:input path="codekind" name="codekind" cssClass="form-control inputtext"/>
        </c:if>
        <c:if test="${caseData.codekind != null}">
            <form:input path="codekind" name="codekind" value="${caseData.codekind}" readonly="true" cssClass="form-control"/>
        </c:if>
        </div>
        <form:label cssClass="col-form-label" path="codeno">主代號：</form:label>
        <div class="col-12 col-md">
            <form:input path="codeno" name="codeno" value="${caseData.codeno}" cssClass="form-control inputtext"/>
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
    <br>
    <div class="table-responsive">
        <table class="table">
            <thead data-orderable="true">
                <tr>
                      <th data-orderable="false" class="text-center deleteAll">刪除</th>
                      <th class="text-center">主代號</th>
                      <th class="text-center">副代號類別</th>
                      <th class="text-center">副代號</th>
                      <th class="text-center">主代號名稱</th>
                      <th class="text-center">說明</th>
                </tr>
            </thead>
            
            
            <tbody>
                <c:forEach items="${caseData.detailList}" var="item" varStatus="status">
                <tr>
                    <td><form:checkbox path="delete" value="${item.codeno}"/></td>
                    <td class="text-left"><c:out value="${item.codeno}" /></td>
                    <td class="text-left"><c:out value="${item.scodekind}" /></td>
                    <td class="text-left"><c:out value="${item.scodeno}" /></td>
                    <td class="text-left"><c:out value="${item.codename}" /></td>
                    <td class="text-left"><c:out value="${item.remark}" /></td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <form:hidden path="doUpdate" value="false"/>
    

</form:form>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
$(function(){
	var config = getDataTablesConfig();
	var resultTable = $('.table').DataTable(config);
	//返回
    $('#btnReply').click(function(){
    	$('#bfib0w050Form').attr('action', '<c:url value="/Code_enter.action" />').submit();
    })
    //依據點擊位置執行 換頁或checkbox切換
    $('table>tbody>tr>td').on('click',function(){
    	var row = $(this).parent().parent().find("tr").index($(this).parent()[0]);//第幾行(排)
    	var column = $(this).index();//第幾列(個)
    	if(column === 0){
    		//checkbox 取消預設行為
            if ($(event.target).is('input:checkbox')){
            	preventDefault;
            }
            var checked = $('input[name="delete"]:checkbox').eq(row).prop('checked');
            $('input[name="delete"]:checkbox').eq(row).prop('checked',!checked);
    	}else{
            $('#codeno').val($(this).parent().children().eq(1).text())
            $('#scodekind').val($(this).parent().children().eq(2).text())
            $('#scodeno').val($(this).parent().children().eq(3).text())
            $('#codename').val($(this).parent().children().eq(4).text())
            $('#remark').val($(this).parent().children().eq(5).text())
    		$('#bfib0w050Form').attr('action', '<c:url value="/Code_update.action" />').submit();
    	}
    })
    //全選的切換
    var flag = false;
    $('.deleteAll').click(function(){
        $('input[name="delete"]:checkbox').each(function(){
            $(this).prop('checked',!flag);
        })
        flag = !flag;
    })
    //新增
    $('#btnInsert').click(function(){
        if($('#codekind').val().trim().length === 0){
            showAlert('主代號類別為必填欄位')
            return;
        }
        if($('#codeno').val().trim().length === 0){
            showAlert('主代號為必填欄位')
            return;
        }
        $('#bfib0w050Form').attr('action', '<c:url value="/Code_insert.action" />').submit();
    })
    //刪除
    $('#btnDelete').click(function(){
    	$('#bfib0w050Form').attr('action', '<c:url value="/Code_delete.action" />').submit();
    })
    //清除
    $('#btnClear').click(function(){
        $('.inputtext').val("");
    })
})
</script>
</jsp:attribute>
</tags:layout>