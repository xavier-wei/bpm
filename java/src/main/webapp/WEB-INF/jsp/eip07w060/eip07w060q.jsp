<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w060Controller).CASE_KEY" />
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
		<form:form id="eip07w060Form" name="eip07w060Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="carType">首長專用車：</form:label>
                <div class="col-12 col-md form-inline">
                	<form:radiobutton path="carType" label="Y是" value="Y"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <form:radiobutton path="carType" label="N否" value="N"/>
                </div>
            </tags:form-row>
            <div id = "div1">
	            <tags:form-row>
		           	<form:label cssClass="col-form-label star" path="applyid">派車單號：</form:label>
		               <div class="col-12 col-md d-flex align-items-center">
		               	<form:input path="applyid" cssClass="form-control num_eng_only" size="13" maxlength="13" />
		               </div>
	            </tags:form-row>
            </div>
            <div id = "div2">
	            <tags:form-row>
		            <form:label cssClass="col-form-label" path="bosscarno">車牌號碼：</form:label>
		                <div class="col-12 col-md-6 m-2">
		                    <form:select path="bosscarno">
		                    	<option value = ''>請選擇</option>
		                        <c:forEach items="${caseData.bosscarList}" var="item" varStatus="status">
									<form:option value="${item.carno1},${item.carno2}">${item.carno1}-${item.carno2}</form:option>
								</c:forEach>
		                    </form:select>
	               		</div>
	            </tags:form-row>
	            <tags:form-row>
		            <form:label path="btmk" cssClass="col-form-label">出差排程：</form:label>
	            	<div class="col-6 col-md form-inline">
	            		<form:select path="btmk" cssClass="form-control">
	                   		<form:option value="">請選擇</form:option>
							<form:option value="Y">是</form:option>
							<form:option value="N">否</form:option>
	                    </form:select>
	                </div>
	            </tags:form-row>
            </div>
            <tags:form-note>
                派車單號為必填欄位
            </tags:form-note>
        </form:form>
    </tags:fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
            $('#btnConfirm').click(function() {
            	$('#eip07w060Form').attr('action', '<c:url value="/Eip07w060_query.action" />').submit();
            });
     
            $('#btnClear').click(function() {
                $("#applyid").val('');
            });
            
            $('input[type=radio][name=carType]').change(function() {
            	changeCarDiv(this.value);
            });
         });
        
        function changeCarDiv(carType){
            if (carType == 'Y') {
            	$('#div1').hide();
            	$('#div2').show();
            }
            else if (carType == 'N') {
            	$('#div1').show();
            	$('#div2').hide();
            }
        }
        
        $(document).ready(function () {
        	var type = '<c:out value="${caseData.carType}"/>';
       		changeCarDiv(type);
        });
        
</script>
</jsp:attribute>
</tags:layout>