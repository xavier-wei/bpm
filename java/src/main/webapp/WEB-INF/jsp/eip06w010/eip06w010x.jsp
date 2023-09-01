<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 會議室查詢/維護作業 明細畫面 --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip06w010Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="buttons">
	    <tags:button id="btnBack">返回<i class="fas fa-reply"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <tags:fieldset legend="會議明細">
            <form:form id="eip06w010Form" name="eip06w010Form" modelAttribute="${caseKey}" method="POST">
                <tags:form-row>
                    <div class="col-md-3">
                        <form:label cssClass="col-form-label " path="meetingName">會議名稱：</form:label>
                        <c:out value="${caseData.meetingName}"/>
                    </div>
                    <div class="col-md-3">
                        <form:label cssClass="col-form-label " path="chairman">主持人：</form:label>
                        <c:out value="${caseData.chairman}"/>
                    </div>
                    <div class="col-md-3">
                        <form:label cssClass="col-form-label " path="organizerId">申請人：</form:label>
                        <c:out value="${caseData.organizerId}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md">
                        <form:label cssClass="col-form-label " path="meetingdt">會議日期：</form:label>
<%--                        <form:input path="meetingdt" cssClass="form-control" type="date"/>--%>
                        <func:minguo value="${caseData.meetingdt }" />
                    </div>
                    <div class="col-md">
                        <form:label cssClass="col-form-label " path="meetingBegin">會議時間：</form:label>
<%--                        <c:out value="${caseData.meetingBegin}"/>--%>
<%--                        <span>~</span>--%>
<%--                        <c:out value="${caseData.meetingEnd}"/>--%>
                        <c:out value='${caseData.meetingBegin.substring(0,2)}:${caseData.meetingBegin.substring(2)} ~ ${caseData.meetingEnd.substring(0,2)}:${caseData.meetingEnd.substring(2)}'/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md">
                        <form:label cssClass="col-form-label " path="roomId">場地：</form:label>
<%--                        <form:select path="roomId" cssClass="form-control selector" disabled="true" >--%>
<%--                        </form:select>--%>
                        <c:out value="${caseData.roomId}"/>
                    </div>
                    <div class="col-md">
                        <form:label cssClass="col-form-label " path="meetingQty">開會人數：</form:label>
<%--                        <form:input path="meetingQty" cssClass="form-control num_only" size="3" disabled="true"/>--%>
                        <c:out value="${caseData.meetingQty}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-6">
                        <form:label cssClass="col-form-label " path="itemId">會議物品：</form:label>
<%--                        <form:select path="itemId" cssClass="form-control selector mr-3">--%>
<%--                            <c:forEach items="${caseData.itemIdList}" var="item">--%>
<%--                                <form:option name="${item.itemName }" value="${item.itemId }">${item.itemName }</form:option>--%>
<%--                            </c:forEach>--%>
<%--                        </form:select>--%>
<%--                        <tags:button id="btnAddItem">新增<i class="fas fa-plus"></i></tags:button>--%>
                    </div>
                    <div class="col-md-6">
                        <form:label cssClass="col-form-label " path="foodId">會議餐點：</form:label>
<%--                        <form:select path="foodId" cssClass="form-control selector mr-3">--%>
<%--                            <c:forEach items="${caseData.foodIdList}" var="item">--%>
<%--                                <form:option name="${item.itemName }" value="${item.itemId }">${item.itemName }</form:option>--%>
<%--                            </c:forEach>--%>
<%--                        </form:select>--%>
<%--                        <tags:button id="btnAddFood">新增<i class="fas fa-plus"></i></tags:button>--%>
                    </div>
                </tags:form-row>
                <%-- 會議物品/食物表格 --%>
                <tags:form-row cssClass="flex-nowrap">
                    <table id="itemTable" class="table table-hover m-2">
                        <thead>
                        <th style="width: 10%">編號</th>
                        <th style="width: 60%">名稱</th>
                        </thead>
                        <tbody></tbody>
                    </table>
                    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                        <th style="width: 10%">編號</th>
                        <th>名稱</th>
                        <th style="width: 20%">數量</th>
                        </thead>
                        <tbody></tbody>
                    </table>
                </tags:form-row>
		        <form:hidden path="organizerId" value="${caseData.organizerId }"/>
                <form:hidden path="itemIds" />
                <form:hidden path="foodId_Qty"/>
                <form:hidden path="food_Qty"/>
                <form:hidden path="meetingId"/>
                <form:hidden path="admin"/>
            </form:form>
        </tags:fieldset>
    </jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function(){
        //頁面一進入就先刷新場地下拉選單
        findSelectedItemandFood();

        //返回btnBack
        $('#btnBack').click(function(e) {
            e.preventDefault();
            $('#eip06w010Form').attr('action', '<c:url value="/Eip06w010_backHome.action" />').submit();
        });
    })
    //查詢已選會議物品和食物
    function findSelectedItemandFood() {
        let data = {
            'meetingId': $('#meetingId').val()
        };
        $.ajax({
            url: '<c:url value="Eip06w010_findSelectedItemandFood.action"/>',
            type: 'POST',
            contentType : 'application/json',
            data: JSON.stringify(data),
            success: function (data){
                data = JSON.stringify(data);
                // [{"B01-擴音":"1","B02-單槍":"1"},{"A01-茶水":"20","A02-水果":"20"}]
                let selectedItemIdMap = data.split("},{")[0].replace("[","");
                let selectedFoodIdMap = data.split("},{")[1].replace("]","");
                initItemTable(selectedItemIdMap);
                initFoodTable(selectedFoodIdMap);
            },
            error:function(jqXHR, textStatus, errorThrown) {
                // 請求失敗時的回調函數
                console.log(textStatus + ': ' + errorThrown);
            }
        })
    }
    //initItemTable
    function initItemTable(selectedItemIdMap) {
        // {B01-擴音=1, B02-單槍=1}
        // let selectedItemIdMap = $('#selectedItemIdMap').val().split(",");
        selectedItemIdMap = selectedItemIdMap.split(',');
        //{"B01-擴音":"1","B02-單槍":"1"

        for (let i = 0 ; i < selectedItemIdMap.length ; i++ ){
            let selectedText = selectedItemIdMap[i].split(":")[0].split("-")[1].replace("\"","");  //擴音
            let selectedId = selectedItemIdMap[i].split("-")[0].replace("{","").replace("\"","").trim();      //ID
            let rowCount = i+1;
            var rowHtml =
                '<tr>'+
                '<td>'.concat(rowCount) + '</td>'+
                '<td  id="itemContentTxt" class="text-left">' +  selectedText + '</td>'+
                '<td  id="itemContent" style="display: none;">' +  selectedId + '</td>'+
                '</tr>';
            $('#itemTable > tbody').append(rowHtml);
        }
    }
    //initFoodTable
    function initFoodTable(selectedFoodIdMap){
        // {A01-茶水=20, A02-水果=20}
        // let selectedFoodIdMap = $('#selectedFoodIdMap').val().split(",");
        selectedFoodIdMap = selectedFoodIdMap.split(",");
        // "A01-茶水":"20","A02-水果":"20"}

        for (let j = 0 ; j < selectedFoodIdMap.length ; j++ ){
            let selectedQty  = selectedFoodIdMap[j].split(":")[1].replace("}","").replace("\"","").replace("\"","");  //20
            let selectedText = selectedFoodIdMap[j].split(":")[0].split("-")[1].replace("\"","");  //茶水
            let selectedId = selectedFoodIdMap[j].split("-")[0].replace("{","").replace("\"","").trim();      //A01

            let rowCount = j+1;
            var rowHtml =
                '<tr>'+
                '<td>'.concat(rowCount) + '</td>'+
                '<td id="foodContentTxt" class="text-left">' +  selectedText + '</td>'+
                '<td id="foodContent" style="display: none;" >' +  selectedId + '</td>'+
                '<td class="text-right">' + <c:out value="selectedQty"/> + '</td>'+
                '</tr>';
            $('#foodTable > tbody').append(rowHtml);
        }
    }

</script>
</jsp:attribute>
</tags:layout>