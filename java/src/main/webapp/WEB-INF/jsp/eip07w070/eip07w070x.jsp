<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.orderCar.controllers.Eip07w070Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnPrint">
    	列印<i class="fas fa-user-check"></i>
    </tags:button>
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
		<form:form id="eip07w070Form" name="eip07w070Form" modelAttribute="${caseKey}" method="POST">
		<c:if test="${caseData.orderCondition eq '1'}">
		<fieldset>
			<legend>依用車日期排序</legend>
 		            <tags:form-row>
            	    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th class="align-middle"  style="width: 10%">派車單號</th>
                            <th class="align-middle"  style="width: 10%">用車日期<br>用車時間起迄</th>
                            <th class="align-middle" style="width: 10%">車牌號碼</th>
                            <th class="align-middle" style="width: 10%">駕駛人姓名</th>
                            <th class="align-middle"  style="width: 60%">用車事由</th>
                        </thead>
                        <c:forEach items="${caseData.dataList}" var="item" varStatus="status">
                        <tbody>
                        	<tr>
                        		<td><c:out value="${item.applyid}"/></td>
                        		<td>
                        			<func:minguo value="${item.using_date}"/><br>
                        			<c:out value="${item.using_time_s}"/>~<c:out value="${item.using_time_e}"/>
                        		</td>
                        		<td>
                        			<c:out value="${item.carno1}"/>-<c:out value="${item.carno2}"/>
                        		</td>
                        		<td>
                        			<c:out value="${item.name}"/>
                        		</td>
                        		<td class="text-left">
                        			<span class="ellipsisStr">
										<c:out value="${item.apply_memo}"/>
									</span>
                        		</td>
                        	</tr>
                        </tbody>
                        </c:forEach>
                    </table>
            </tags:form-row>
         </fieldset>   
         </c:if>
         <c:if test="${caseData.orderCondition eq '2'}">
			<fieldset>
		        <legend>依車牌號碼排序</legend>
		        <tags:form-row>
            	    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                        	<th class="align-middle" style="width: 10%">車牌號碼</th>
                            <th class="align-middle"  style="width: 10%">派車單號</th>
                            <th class="align-middle"  style="width: 10%">用車日期<br>用車時間起迄</th>
                            <th class="align-middle" style="width: 10%">駕駛人姓名</th>
                            <th class="align-middle"  style="width: 60%">用車事由</th>
                        </thead>
                        <c:forEach items="${caseData.dataList}" var="item" varStatus="status">
                        <tbody>
                        	<tr>
                        	    <td>
                        			<c:out value="${item.carno1}"/>-<c:out value="${item.carno2}"/>
                        		</td>
                        		<td><c:out value="${item.applyid}"/></td>
                        		<td>
                        			<func:minguo value="${item.using_date}"/><br>
                        			<c:out value="${item.using_time_s}"/>~<c:out value="${item.using_time_e}"/>
                        		</td>
                        		<td>
                        			<c:out value="${item.name}"/>
                        		</td>
                        		<td class="text-left">
                        			<span class="ellipsisStr">
										<c:out value="${item.apply_memo}"/>
									</span>
                        		</td>
                        	</tr>
                        </tbody>
                        </c:forEach>
                    </table>
            </tags:form-row>
		 	</fieldset>
         </c:if>
        </form:form>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
 
            $('#btnReturn').click(function(){
           		$('#eip07w070Form').attr('action', '<c:url value="/Eip07w070_enter.action" />').submit();
            });
            
            
            $('#btnPrint').click(function(){
            	$('#eip07w070Form').attr('action', '<c:url value="/Eip07w070_print.action" />').submit();
            });
            

            
         });

</script>
</jsp:attribute>
</tags:layout>