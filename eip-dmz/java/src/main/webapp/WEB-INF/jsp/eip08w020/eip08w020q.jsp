<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnConfirm">
    	確認<i class="fas fa-user-check"></i>
    </tags:button>          
    <tags:button id="btnClear">
    	清除<i class="fas fa-eraser"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip08w020Form" name="eip08w020Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	選項: 
            	<label><input type="radio" name="SelectType"  value="apply" checked>申請作業</label>
            	<label><input type="radio" name="SelectType"  value="search">查詢/更正作業</label>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="apply_user">申請人：</form:label>
                <div class="col-12 col-md">
                    <form:input path="apply_user" cssClass="add form-control star" disabled="true"/>
                </div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="apply_dept">申請單位：</form:label>
                <div class="col-12 col-md">
                    <form:input path="apply_dept" cssClass="add form-control" disabled="true"/>
                </div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="apply_date">申請日期：</form:label>
                <div class="col-12 col-md">
                    <form:input path="apply_date" cssClass="add form-control" />
                </div>
            </tags:form-row>
            <tags:form-note>
                <div class="apply">申請人、申請單位、申請日期為必輸欄位</div>
                <div class="search">申請日期為必輸欄位</div>
            </tags:form-note>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
        	$(".apply").show();
        	$(".search").hide();
        	$("#apply_user").addClass("star");
        	$("#apply_dept").addClass("star");
        	
        	
            $('#btnConfirm').click(function() {
            	if($("input[name='SelectType']")[0].checked){            		
           			$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_add.action" />').submit();
            	}else{
            		$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_query.action" />').submit();
            	}
            });
            
            $('#btnClear').click(function() {
                $("#apply_date").val('');
            });
            
         });
        
        $("input[name='SelectType']").change(function(){
            if($("input[name='SelectType']")[0].checked){
            	$(".apply").show();
            	$(".search").hide();
            	$("#apply_user").addClass("star");
            	$("#apply_dept").addClass("star");
            }else{
            	$("#apply_user").removeClass("star");
            	$("#apply_dept").removeClass("star");
            	$(".apply").hide();
            	$(".search").show();
            }
        });

</script>
</jsp:attribute>
</tags:layout>