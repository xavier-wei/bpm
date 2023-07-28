<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w010Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
<!-- 選擇頁 -->
	<tags:button id="btnSearch">
    	查詢<i class="fas fa-search"></i>
    </tags:button>   
    <tags:button id="btnClear">
    	清除<i class="fas fa-eraser"></i>
    </tags:button>        
</jsp:attribute>

<jsp:attribute name="contents">
    <tags:fieldset>
		<form:form id="eip080w010Form" modelAttribute="${caseKey}" method="POST">
			<tags:form-row>
                <form:label cssClass="col-form-label" path="mainkindno">品名大類：</form:label>
                <div class="col-12 col-md-6 m-2">
                    <form:select path="mainkindno">
                    	<option value = ''>請選擇</option>
                    	<c:forEach items="${caseData.mainKindList}" var="item" varStatus="status">
							<form:option value="${item.itemno}">${item.itemno}-${item.itemname}</form:option>
						</c:forEach>
                    </form:select>
                </div>
            </tags:form-row>    
            <tags:form-row> 
                <form:label cssClass="col-form-label" path="detailkindno">品名：</form:label>
                <div class="col-12 col-md-6 m-2">
                    <form:select path="detailkindno">
                    	<option value = ''>請選擇</option>
                                        	<c:forEach items="${caseData.detailKindList}" var="item" varStatus="status">
							<form:option value="${item.itemno}">${item.itemno}-${item.itemname}</form:option>
						</c:forEach>
                    </form:select>
                </div>
            </tags:form-row>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
	$(function() {
	    $('#btnClear').click(function() {
	    	$('#mainkindno').val('');
	    	$('#detailkindno').val('');
	    	$('#detailkindno').empty();
	    	$('#detailkindno').append($('<option>',{
				value: '',
				text: '請選擇'
			}));
	    });
	    
	    $('#btnSearch').click(function() {
	    	if(($('#detailkindno').val() != '') && ($('#mainkindno').val()!=null)){
	    		$('#eip080w010Form').attr('action', '<c:url value="/Eip08w010_detailQuery.action" />').submit();
	    	}else{
	    		$('#eip080w010Form').attr('action', '<c:url value="/Eip08w010_query.action" />').submit();
	    	}
	        
	    });
	    
	    $('#mainkindno').change(function() {
	        $.ajax({
	            url: '<c:url value="Eip08w010_findDetalKindList.action"/>',
	            type: 'POST',
	            contentType : 'application/json',
	            data: JSON.stringify($(this).val()),
	            dataType : 'json',
	            success: function (data){
	            	var count = parseFloat(data.length);
            		$('#detailkindno').empty();
            		$('#detailkindno').append($('<option>',{
        				value: '',
        				text: '請選擇'
        			}));
            		$.each(data, function(itemno,itemname){
            			$('#detailkindno').append($('<option>',{
            				value: itemno,
            				text: itemno + '-' + itemname
            			}));
            		})
	            }
	        })
	    });
	    
	 });

</script>
</jsp:attribute>
</tags:layout>