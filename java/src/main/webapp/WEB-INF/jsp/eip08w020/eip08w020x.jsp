<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.apply.controllers.Eip08w020Controller).CASE_KEY" />
<c:set var="caseData" value="${sessionScope[caseKey]}" />
<tags:layout>
<jsp:attribute name="buttons">
    <tags:button id="btnConfirm">
    	確認申請<i class="fas fa-user-check"></i>
    </tags:button>          
    <tags:button id="btnReturn">
    	回主畫面<i class="fas fa-reply"></i>
    </tags:button>
</jsp:attribute>

<jsp:attribute name="contents">
     	<fieldset>
      	<legend>領物單申請作業</legend>
		<form:form id="eip08w020Form" name="eip08w020Form" modelAttribute="${caseKey}" method="POST">
            <tags:form-row>
                <div class="col-4 col-md-4">
	            	<tags:text-item label="申請人">
	            		<c:out value="${caseData.apply_user}"/>
	            	</tags:text-item>
            	</div>
            	<div class="col-4 col-md-4">
            		<tags:text-item label="申請單位">
            			<c:out value="${caseData.apply_dept}"/>
            		</tags:text-item>
            	</div>
            	<div class="col-4 col-md-4">
	            	<tags:text-item label="申請日期">
	            		<func:minguo value="${caseData.apply_date}"/>
	            	</tags:text-item>
            	</div>
            </tags:form-row>
            <tags:form-row>
            	<form:label cssClass="col-form-label star" path="apply_memo">申請用途：</form:label>
                <div class="col-12 col-md">
                    <form:input path="apply_memo" cssClass="add form-control"/>
                </div>
            </tags:form-row>
            <tags:form-row>
            	    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th style="width: 10%">序號</th>
                            <th style="width: 20%"><font color="red">*</font>品名大類</th>
                            <th style="width: 30%"><font color="red">*</font>品名</th>
                            <th style="width: 10%">庫存數量</th>
                            <th style="width: 10%"><font color="red">*</font>申請數量</th>
                            <th style="width: 10%">單位</th>
                        </thead>
					<c:forEach  begin="0" end="14" step="1" var="num"  varStatus="numstatus">
                        <tbody>
	                        <tr class="dataRow">
	                        	<td>${num+1}</td>
	                        	<td>
	                        	<form:select  path="allData[${numstatus.index}].itemkind" class="itemkind form-control mr-2" data-width="300px"> 
									<form:option value="">請選擇</form:option>
									<c:forEach items="${caseData.itemList}" var="item" varStatus="status">
										<form:option value="${item.itemno}">${item.itemno}-${item.itemname}</form:option>
									</c:forEach>
								</form:select>
								</td>
	                        	<td>
			                    <form:select path="allData[${numstatus.index}].itemno" class="itemno form-control mr-2" data-width="300px"> 	
			                    	<form:option value="">請選擇</form:option>
			                    </form:select>
			                    <form:hidden path="allData[${numstatus.index}].keepitemno" class="keepitemno"/>
	                        	</td>
	                        	<td>
	                        	<form:input type="number" path="allData[${numstatus.index}].book_cnt" cssClass="book_cnt form-control num_only" readOnly="true"/>
	                        	<form:hidden path="allData[${numstatus.index}].withhold_cnt" class="withhold_cnt"/>
	                        	</td>
	                        	<td><form:input type="number" path="allData[${numstatus.index}].apply_cnt" cssClass="apply_cnt form-control num_only"/></td>
	                        	<td><form:input path="allData[${numstatus.index}].unit"  cssClass="unit form-control" maxlength="4"/></td>
	                        </tr>
                        </tbody>
                     </c:forEach>
                    </table>
            </tags:form-row>
        </form:form>
        </fieldset>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
	$(document).ready(function(){
		for(var i=0; i<15; i++){
			if($('.keepitemno').eq(i).val()!==''){
				getItemno(i,$('.itemkind').eq(i).val(),$('.keepitemno').eq(i).val());
			}
		}
	});

        $(function() {
            $('#btnConfirm').click(function() {
            	var returnStr = '';
            	
            	if($("#apply_memo").val()==''){
            		returnStr += "「申請用途」必須輸入\r\n";
            	}
            	
            	$(".itemkind").each(function(e){
            		var index = $('.itemkind').index(this);
            		var num = "序號"+ (index+1)+"：";
            		var eachStr = '';
            		
            		if($(".itemkind").eq(index).val()!==''){
                		if($(".itemno").eq(index).val()==''){
                			eachStr += '品名未填寫、';
                		}
                		
                		if($(".itemno").eq(index).val()!=='' && $(".book_cnt").eq(index).val()==''){
                			eachStr += '庫存為零，請調整品項、';
                		}
                		
                		if($(".apply_cnt").eq(index).val()=='' && $(".book_cnt").eq(index).val()!=='' ){
                			eachStr += '數量未填寫';
                		}
                		
                		if($(".apply_cnt").eq(index).val()!=='' && $(".book_cnt").eq(index).val()!==''){
                			var applycnt = $(".apply_cnt").eq(index).val();
                			var bookcnt = $(".book_cnt").eq(index).val();
                			if(parseInt(applycnt)>parseInt(bookcnt)){                				
                				eachStr +='申請數量不得大於庫存數量';
                			}
                		}
            		}
            		
            		if(eachStr !==''){
            			returnStr += (num+eachStr+"\r\n");	
            		}
       
            	});
            	
            	if(returnStr!==''){
            		showAlert(returnStr);
            		return
            	}
            	
           		$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_insert.action" />').submit();
            });
            
            $('#btnReturn').click(function() {
            	$('#eip08w020Form').attr('action', '<c:url value="/Eip08w020_enter.action" />').submit();
            });
            
         });
        
        
         $(".itemkind").change(function(e){
        	 	var index = $('.itemkind').index(this);
    			var itemkind = $('.itemkind').eq(index).val();
    			var url = '<c:url value='/Eip08w020_getItemCodekind.action' />';
    			var data = {};
    			data["itemkind"] = itemkind;
    			$.ajax({
    				type : "POST",
    				contentType : "application/json",
    				url : url,
    				data : JSON.stringify(data),
    				dataType : 'json',
    				timeout : 100000,
    				success : function(data) {
    				$(".itemno").eq(index).empty();
    				
   					   let countyList = [];
   					   for (var i = 0; i < data.length; i++) {
   						   if(i==0){
   							countyList.push("<option value=''>"+"請選擇"+ "</option>")
   						   }
   					    countyList.push("<option value='" + data[i].itemno + "'>"
	    					      + data[i].itemno + "-" + data[i].itemname+ "</option>")
   					   }
   					   
   					   
   					   for (var a = 0; a < countyList.length; a++) {
   							$(".itemno").eq(index).append(countyList[a]);
   					   }
   					
   					
    				},
    				error : function(e) {
    					showAlert("取得品項失敗");
    				}
    	    	});
    		
        });
        
		        $(".itemno").change(function(e){
		        	var index = $('.itemno').index(this);
		        	var itemkind = $('.itemkind').eq(index).val();
	    			var itemno = $(this).val();
	    			$('.apply_cnt').eq(index).attr("disabled",false);
	    			$('.unit').eq(index).attr("disabled",false);
	    	

	    			if($(this).val()!==''){	
						for(var i=0; i<30; i++){
							if(i!==index && $(this).val()==$('.itemno').eq(i).val()){
								showAlert("申請項目不可重複");
								return
							}
							
						}
	    			
	    			
	    			var url = '<c:url value='/Eip08w020_getWithholdCnt.action'/>';
	    			var data = {};
	    			data["itemkind"] = itemkind;
	    			data["itemno"] = itemno;
	    	
	    			$.ajax({
	    				type : "POST",
	    				contentType : "application/json",
	    				url : url,
	    				data : JSON.stringify(data),
	    				dataType : 'json',
	    				timeout : 100000,
	    				success : function(data) {
	    					$(".keepitemno").eq(index).val(itemno);
	    					$(".book_cnt").eq(index).val(data.book_cnt);
	    					$(".withhold_cnt").eq(index).val(data.withhold_cnt);
	    					if(data.book_cnt==0){
	    						$('.apply_cnt').eq(index).attr("readonly",true);
	    						$('.unit').eq(index).attr("readonly",true);
	    						$('.unit').eq(index).val('');
	    						$('.apply_cnt').eq(index).val('');
	    						$('.withhold_cnt').eq(index).val('');
	    					} else {
	    						$('.apply_cnt').eq(index).attr("readonly",false);
	    						$('.unit').eq(index).attr("readonly",false);
	    					}
	    				},
	    				error : function(e) {
	    					showAlert("取得品項失敗"+e);
	    				}
	    	    	});
	    			
		        } else{
		        	$(".book_cnt").eq(index).val('');
		        	$(".withhold_cnt").eq(index).val('');
		        }
	    		
	        });
		        

	        function getItemno(index,itemkind,itemno){
	        	var data = {};
    			data["itemkind"] = itemkind;
    			data["itemno"] = itemno;
    			var url = '<c:url value='/Eip08w020_getItemCodekind.action' />';
    			$.ajax({
    				type : "POST",
    				contentType : "application/json",
    				url : url,
    				data : JSON.stringify(data),
    				dataType : 'json',
    				timeout : 100000,
    				success : function(data) {
    				$(".itemno").eq(index).empty();
    				
   					   let countyList = [];
   					   for (var i = 0; i < data.length; i++) {
   						if(i==0){
      						countyList.push("<option value=''>"+"請選擇"+ "</option>")
      					}
   					    countyList.push("<option value='" + data[i].itemno + "'>"
	    					      + data[i].itemno + "-" + data[i].itemname+ "</option>")
   					   }
   					   
   					   
   					   for (var a = 0; a < countyList.length; a++) {
   							$(".itemno").eq(index).append(countyList[a]);
   							
   							
   					   }
   					   
   					   
   					   
   					   $(".itemno option").each(function() {
	   					    if($(this).val() ==  itemno) {
	   					     $(this).prop("selected",true);
	   					    }

   					   })
   					   
   					   
	   					if($(".book_cnt").eq(index).val()==0){
	   						$(".apply_cnt").eq(index).attr("readonly",true);
	   						$(".unit").eq(index).attr("readonly",true);
	   					}
   					
    				},
    				error : function(e) {
    					showAlert("取得品項失敗");
    				}
    	    	});
    		
	        }
</script></jsp:attribute>
</tags:layout>