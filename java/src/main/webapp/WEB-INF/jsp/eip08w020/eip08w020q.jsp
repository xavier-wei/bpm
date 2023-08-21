<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnConfirm">
    	申請<i class="fas fa-user-check"></i>
    </tags:button>          
    <tags:button id="btnClear">
    	清除<i class="fas fa-eraser"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <fieldset>
	<legend>申請條件</legend>
		<form:form id="eip08w020Form" name="eip08w020Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            <div class="col-md d-flex align-items-center SelectType">
		            <form:label cssClass="col-form-label mr-1" path="SelectType">選項：</form:label>
		            <div class="mt-3">
		            <label><input type="radio" id="SelectType" name="SelectType" value="apply" checked="true"/>申請作業</label>
		            <label><input type="radio" id="SelectType" name="SelectType" value="search"/>查詢作業</label>
		            </div>
			</div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label apply_user" path="apply_user">申請人：</form:label>
                <div class="col-12 col-md">
                    <form:input path="apply_user" cssClass="add form-control star" disabled="true"/>
                </div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label apply_dept" path="apply_dept">申請單位：</form:label>
                <div class="col-12 col-md">
                    <form:input path="apply_dept" cssClass="add form-control" disabled="true"/>
                </div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label apply_date" path="apply_date">申請日期：</form:label>
                <div class="col-12 col-md">
                    <form:input path="apply_date" cssClass="add form-control num_only dateTW date" maxlength="7"/>
                </div>
            </tags:form-row>
            <tags:form-note>
                <div class="apply">申請人、申請單位、申請日期為必輸欄位</div>
                <div class="search">申請人為必輸欄位</div>
            </tags:form-note>
            <form:hidden path="oriApply_user"/>
            <form:hidden path="oriApply_dept"/>
        </form:form>
    </fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
        	setPage();

        	
            $('#btnConfirm').click(function() {
            	if($("input[name='SelectType']")[0].checked){
            		if($('#apply_date').val()==''){
            			showAlert('申請日期為必填');
            			return;
            		}
           			$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_add.action" />').submit();
            	}else{
            		if($('#apply_user').val()==''){
            			showAlert('申請人為必填');
            			return;
            		}
            		$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_query.action" />').submit();
            	}
            });
            
            $('#btnClear').click(function() {
            	$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_enter.action" />').submit();
            });
            
         });
        
        $("input[name='SelectType']").change(function(){
        	setPage();
        });
        
    	function setPage(){
            if($("input[name='SelectType']")[0].checked){
            	$(".apply").show();
            	$(".search").hide();
            	$(".apply_user").addClass('star');
            	$('.apply_date').addClass('star');
            	$('.apply_dept').addClass('star');
            	$("#apply_user").val( $('#oriApply_user').val());
            	$("#apply_dept").val( $('#oriApply_dept').val());
            	$("#apply_user").attr("disabled",true);
            	$("#apply_dept").attr("disabled",true)
            	var Str = '申請'+ '<i class="fas fa-user-check"></i>';
            	$("#btnConfirm>.btn-txt").text('').append(Str);
            	$("legend").text("申請條件");
            } else {
            	var Str = '查詢'+ '<i class="fas fa-search"></i>';
            	$("#btnConfirm>.btn-txt").text('').append(Str);
            	$("#apply_user").attr("disabled",false);
            	$("#apply_dept").attr("disabled",false);
            	
            	$('.apply_date').removeClass('star');
            	$('.apply_dept').removeClass('star');
            	
            	$(".apply").hide();
            	$(".search").show();
            	$("legend").text("查詢條件");
            }
    	}

</script>
</jsp:attribute>
</tags:layout>