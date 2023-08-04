<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 會議室批次預約作業 --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip06w030Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="buttons">
    <!-- 選擇頁 -->
        <tags:button id="btnSave">儲存<i class="fas fa-user-check"></i></tags:button>
        <tags:button id="btnClear">清除<i class="fas fa-eraser"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <tags:fieldset>
            <form:form id="eip06w030Form" name="eip06w030Form" modelAttribute="${caseKey}" method="POST">
                <tags:form-row>
                    <div class="col-md-3 d-flex">
                        <form:label cssClass="col-form-label star" path="meetingName">會議名稱：</form:label>
                        <form:input path="meetingName" cssClass="form-control" size="21"/>
                    </div>
                    <div class="col-md-3 d-flex">
                         <form:label cssClass="col-form-label star" path="chairman">主持人：</form:label>
                         <form:input path="chairman" cssClass="form-control" size="9"/>
                    </div>
                    <div class="col-md-3">
                         <form:label cssClass="col-form-label" path="organizerId">申請人：</form:label>
                         <c:out value="${caseData.organizerId}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md d-flex"></div>
                    <div class="col-md-6 d-flex">
                        <form:label cssClass="col-form-label star" path="meetingBegin">會議時間：</form:label>
                        <form:select path="meetingBegin" cssClass="form-control selector">
                            <form:option value="">開始時間</form:option>
                            <form:options items="${caseData.meetingTimeCombobox }" />
                        </form:select>
                        <span class="input-group-text">~</span>
                        <form:select path="meetingEnd" cssClass="form-control selector">
                            <form:option value="">結束時間</form:option>
                            <form:options items="${caseData.meetingTimeCombobox }" />
                        </form:select>
                    </div>
                    <div class="col-md d-flex"></div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-3 d-flex">
                        <form:label cssClass="col-form-label star" path="roomId">場地：</form:label>
                        <form:select path="roomId" cssClass="form-control selector" >
                            <form:option value="">請選擇會議場地</form:option>
                            <c:forEach items="${caseData.roomIdList}" var="item">
                                <form:option name="${item.itemName }" value="${item.itemId }">${item.itemId }-${item.itemName }</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label star" path="meetingQty">開會人數：</form:label>
                        <form:input path="meetingQty" cssClass="form-control num_only" size="3" disabled="true"/>
                    </div>
                     <div class="col-md d-flex"></div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label star" path="meetingdt">會議日期：</form:label>
                        <form:select path="repeat" cssClass="form-control selector mr-5">
                            <form:option value="false">不重複</form:option>
                            <form:option value="true">自訂</form:option>
                        </form:select>
                        <span class="pt-2">每</span>
                        <form:select path="dateWeekMonth" cssClass="form-control selector mx-1" disabled="true">
                            <form:option value="date">日</form:option>
                            <form:option value="week">週</form:option>
                            <form:option value="month">月</form:option>
                        </form:select>
                        <span class="pt-2 ml-3">第</span>
                        <form:select path="week" cssClass="form-control selector mx-1" disabled="true">
                            <form:option value="1">一</form:option>
                            <form:option value="2">二</form:option>
                            <form:option value="3">三</form:option>
                            <form:option value="4">四</form:option>
                        </form:select>
                        <span class="pt-2">個</span>
                        <span class="pt-2 ml-3">星期</span>
                        <form:select path="day" cssClass="form-control selector mx-1" disabled="true">
                            <form:option value="1">一</form:option>
                            <form:option value="2">二</form:option>
                            <form:option value="3">三</form:option>
                            <form:option value="4">四</form:option>
                            <form:option value="5">五</form:option>
                            <form:option value="6">六</form:option>
                            <form:option value="7">日</form:option>
                        </form:select>
                        <form:input id="periodStart"  name="periodStart"  path="periodStart" cssClass="form-control num_only ml-3 dateTW" size="8" maxlength="7" />
                        <span class="pt-2 mx-1">~</span>
                        <form:input id="periodEnd"  name="periodEnd"  path="periodEnd" cssClass="form-control num_only dateTW" size="8" maxlength="7" disabled="true"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-6 d-flex">
                        <form:label cssClass="col-form-label star" path="itemId">會議物品：</form:label>
                        <form:select path="itemId" cssClass="form-control selector mr-3">
                            <c:forEach items="${caseData.itemIdList}" var="item">
                                <form:option name="${item.itemName }" value="${item.itemId }">${item.itemName }</form:option>
                            </c:forEach>
                        </form:select>
                        <tags:button id="btnAddItem">新增<i class="fas fa-plus"></i></tags:button>
                    </div>
                    <div class="col-md-6 d-flex">
                        <form:label cssClass="col-form-label star" path="foodId">會議餐點：</form:label>
                        <form:select path="foodId" cssClass="form-control selector mr-3">
                            <c:forEach items="${caseData.foodIdList}" var="item">
                                <form:option name="${item.itemName }" value="${item.itemId }">${item.itemName }</form:option>
                            </c:forEach>
                        </form:select>
                        <tags:button id="btnAddFood">新增<i class="fas fa-plus"></i></tags:button>
                    </div>
                </tags:form-row>
                <%-- 會議物品/食物表格 --%>
                <tags:form-row cssClass="flex-nowrap">
                    <table id="itemTable" class="table table-hover m-2">
                        <thead>
                            <th style="width: 10%">編號</th>
                            <th style="width: 60%">名稱</th>
                            <th></th>
                        </thead>
                        <tbody></tbody>
                    </table>
                    <table id="foodTable" class="table table-hover m-2">
                        <thead>
                            <th style="width: 10%">編號</th>
                            <th>名稱</th>
                            <th style="width: 20%">數量</th>
                            <th style="width: 30%"></th>
                        </thead>
                        <tbody></tbody>
                    </table>
                </tags:form-row>
		        <form:hidden path="organizerId" value="${caseData.organizerId }"/>
                <form:hidden path="itemIds" />
                <form:hidden path="foodId_Qty"/>
                <form:hidden path="food_Qty"/>
                <form:hidden path="meetingsNeedCancel"/>
                 <tags:form-note>
                    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
                </tags:form-note>
            </form:form>
        </tags:fieldset>
    </jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function(){
        //初始化會議畫面

        $('#repeat, #dateWeekMonth').change(function () {
            let repeat = $('#repeat').val();
            let dateWeekMonth = $('#dateWeekMonth').val();

            if(repeat === 'false'){
                $('#dateWeekMonth').prop('disabled', true);
                $('#week').prop('disabled', true);
                $('#day').prop('disabled', true);
                $('#periodEnd').prop('disabled', true);
            }else {
                $('#dateWeekMonth').prop('disabled', false);
                if(dateWeekMonth === 'date'){
                    $('#week').prop('disabled', true);
                    $('#day').prop('disabled', true);
                    $('#periodEnd').prop('disabled', false);
                }else if(dateWeekMonth === 'week'){
                    $('#week').prop('disabled', true);
                    $('#day').prop('disabled', false);
                    $('#periodEnd').prop('disabled', false);
                }else if (dateWeekMonth === 'month'){
                    $('#week').prop('disabled', false);
                    $('#day').prop('disabled', false);
                    $('#periodEnd').prop('disabled', false);
                }
            }
        })

        //btnsave
        $('#btnSave').click(function(e) {
            e.preventDefault();

            let meetingQty = $('#meetingQty');
            if(meetingQty.val() === ""){
                meetingQty.val("0");
            }
            var foodIds = $('#foodTable #foodContent').map(function() {
                return $(this).text();
            }).get();
            var foodQtys = $('input[path="foodQty"]').map(function() {
                return $(this).val();
            }).get();

            var food_Qtys = [];
            for (var i = 0; i < foodIds.length; i++){
                food_Qtys.push(parseInt(foodQtys[i]));
            }
            var foodId_Qty = [];
            for (var i = 0; i < foodIds.length; i++){
                foodId_Qty.push({fooId: foodIds[i], foodQty: foodQtys[i]});
            }

            $('#foodId_Qty').val(JSON.stringify(foodId_Qty));
            $('#food_Qty').val(JSON.stringify(food_Qtys));

            let serializeForm = $('#eip06w030Form').serialize();

            $.ajax({
                url: '<c:url value="/Eip06w030_validate.action" />',
                cache: false,
                method: 'GET',
                data: serializeForm,
                xhrFields: {
                    responseType: "json"
                }
            }).done(function (){
                    var itemIds = $('#itemTable #itemContent').map(function() {
                        return $(this).text();
                    }).get();
                    var foodIds = $('#foodTable #foodContent').map(function() {
                        return $(this).text();
                    }).get();
                    var foodQtys = $('input[path="foodQty"]').map(function() {
                        return $(this).val();
                    }).get();

                    var food_Qtys = [];
                    for (var i = 0; i < foodIds.length; i++){
                        food_Qtys.push(parseInt(foodQtys[i]));
                    }
                    var foodId_Qty = [];
                    for (var i = 0; i < foodIds.length; i++){
                        foodId_Qty.push({fooId: foodIds[i], foodQty: foodQtys[i]});
                    }

                    $('#itemIds').val(itemIds);
                    $('#foodId_Qty').val(JSON.stringify(foodId_Qty));
                    $('#food_Qty').val(JSON.stringify(food_Qtys));

                    var data = {};
                    data["repeat"] = $('#repeat').val();
                    data["meetingBegin"] = $('#meetingBegin').val();
                    data["meetingEnd"] = $('#meetingEnd').val();
                    data["periodStart"] = $('#periodStart').val();
                    data["periodEnd"] = $('#periodEnd').val();
                    data["dateWeekMonth"] = $('#dateWeekMonth').val();
                    data["week"] = $('#week').val();
                    data["day"] = $('#day').val();
                    data["roomId"] = $('#roomId').val();
                    $.ajax({
                        url: '<c:url value="/Eip06w030_findExistedMeeting.action" />',
                        type: 'POST',
                        contentType : 'application/json',
                        data: JSON.stringify(data),
                        success: function (data){

                            var showData = JSON.stringify(data.show).replace("[","").replace("]","").replaceAll(",","\r\n").trim();
                            if (showData !== "\"no\"") {  //如果data有值是否繼續執行
                                showConfirm("以下會議將被取消，是否仍要預訂?\r\n" + showData, function() {
                                    // let newData = data.split("\r\n");
                                    $('#meetingsNeedCancel').val(data.hide);
                                    $('#eip06w030Form')
                                        .attr('action', '<c:url value="/Eip06w030_save.action" />')
                                        .submit();
                                });
                            }else{
                                $('#eip06w030Form')
                                    .attr('action', '<c:url value="/Eip06w030_save.action" />')
                                    .submit();
                            }
                        },
                        error:function(jqXHR, textStatus, errorThrown) {
                            // 請求失敗時的回調函數
                            console.log(textStatus + ': ' + errorThrown);
                        }
                    })
                }

            ).fail((xhr) => {
                showMessageWhenAjaxFail(xhr);
            });
        });

        <%--//btnClear 清除--%>
        $('#btnClear').click(function(e) {
            e.preventDefault();
            $('#meetingName').val('');
            $('#chairman').val('');
            $('#meetingBegin').val("");
            $('#meetingEnd').val("");
            $('#roomId').val("");
            $('#meetingQty').val("0").prop('disabled', true);
            $('#repeat').val("false");
            $('#dateWeekMonth').val("date").prop('disabled', true);
            $('#week').val("first").prop('disabled', true);
            $('#day').val("mon").prop('disabled', true);
            $('#periodStart').val("");
            $('#periodEnd').val("").prop('disabled', true);
            deleteAllItem();
            deleteAllFood();
        });


        //新增會議室物品
        $('#btnAddItem').click(function(){
            var optionCount = $('select[name="itemId"] option').length;
            if(optionCount <= 1){
                $('#btnAddItem').prop('disabled', true);
            }else {
                $('#btnAddItem').prop('disabled', false);
            }
            insertItem();
        })
        //新增會議室餐點
        $('#btnAddFood').click(function(){
            var optionCount = $('select[name="foodId"] option').length;
            if(optionCount <= 1){
                $('#btnAddFood').prop('disabled', true);
            }else {
                $('#btnAddFood').prop('disabled', false);
            }
            insertFood();
        })

        //場地有變，更新開會人數
        $('select[name="roomId"]').on('change',function (){
                //當會議室場地有值就enable會議人數
                let roomId = $('#roomId').val();
                if(roomId ){
                    $('#meetingQty').prop('disabled', false);
                }else {
                    $('#meetingQty').prop('disabled', true);
                }
            updateMaxMeetingQty();
        })

        //欄位驗證: 開會人數是否大於最大值
        $('input[name="meetingQty"]').blur(function (){
            let meetingQty = $(this).val();
            var roomId = $('#roomId').val();
            var data = {
                'roomId': roomId
            };
            $.ajax({
                url: '<c:url value="Eip06w020_findMaxMeetingQty.action"/>',
                type: 'POST',
                contentType : 'application/json',
                data: JSON.stringify(data),
                success: function (data){
                    if(parseInt(data) < parseInt(meetingQty)){
                        showAlert('「開會人數」需小於或等於'+ data, null)
                    }
                },
                error:function(jqXHR, textStatus, errorThrown) {
                    // 請求失敗時的回調函數
                    console.log(textStatus + ': ' + errorThrown);
                }
            })
        })
    })

    //查詢會議室人數最大值
    function updateMaxMeetingQty() {
        var roomId = $('#roomId').val();
        //任一欄位為空，直接返回，不執行以下代碼(ajax)
        if(!roomId ){
            return;
        }
        var data = {
            'roomId': roomId
        };
        $.ajax({
            url: '<c:url value="Eip06w020_findMaxMeetingQty.action"/>',
            type: 'POST',
            contentType : 'application/json',
            data: JSON.stringify(data),
            success: function (data){
                $('#meetingQty').val(data);
            },
            error:function(jqXHR, textStatus, errorThrown) {
                // 請求失敗時的回調函數
                console.log(textStatus + ': ' + errorThrown);
            }
        })
    }
    //新增會議物品資料行
    function insertItem(selectedText, selectedId){
        let rows = $('#itemTable > tbody > tr');
        let rowCount = rows.length;
        if(selectedText === undefined){
            selectedText = $('#itemId option:selected').text();
            selectedId = $('#itemId option:selected').val();
        }
        $("#itemId option[value='" + selectedId + "']").remove();
        var rowHtml =
            '<tr>'+
            '<td>'.concat(rowCount+1) + '</td>'+
            '<td  id="itemContentTxt" class="text-left">' +  selectedText + '</td>'+
            '<td  id="itemContent" style="display: none;">' +  selectedId + '</td>'+
            '<td>' + buildDeleteItemButton(rowCount).prop('outerHTML') + '</td>'+
            '</tr>';
        $('#itemTable > tbody').append(rowHtml);
    }
    //新增會議物品資料行-刪除按鍵
    function buildDeleteItemButton(index) {
        return $('<button/>').text('刪除')
            .addClass('btn btn-sm btn-outline-be')
            .attr('name', 'delete-item')
            .attr('type', 'button')
            .attr('data-row', index)
            .attr('onclick', 'deleteItem(' + index + ')');
    }
    //新增會議物品資料行-刪除按鍵功能
    function deleteItem(index){
        let rows = $('#itemTable > tbody > tr');
        insertOptionList(rows.eq(index));
        $(rows[index]).remove();
        rows = $('#itemTable > tbody > tr');
        $('#itemTable > tbody').html('');
        rows.each(function (index){
            let row =  rows[index];
            let selectedText = $(row).find('td').eq(1).text();
            let selectedId = $(row).find('td').eq(2).text();
            insertItem(selectedText, selectedId);
        })
        var optionCount = $('select[name="itemId"] option').length;
        if(optionCount < 1){
            $('#btnAddItem').prop('disabled', true);
        }else {
            $('#btnAddItem').prop('disabled', false);
        }
    }
    //將刪除項目重新塞回optoins中
    function insertOptionList(row) {
        let itemContent = $(row).find('#itemContent').text();
        let itemContentTxt = $(row).find('#itemContentTxt').text();
        $('#itemId').append('<option value="' + itemContent + '">' + itemContentTxt + '</option>')
    }
    //新增會議餐點資料行
    function insertFood(selectedText, selectedId){
        let rows = $('#foodTable > tbody > tr');
        let rowCount = rows.length;
        if(selectedText === undefined){
            selectedText =  $('#foodId option:selected').text();
            selectedId = $('#foodId option:selected').val();
        }
        $("#foodId option[value='" + selectedId + "']").remove();
        var rowHtml =
            '<tr>'+
            '<td>'.concat(rowCount+1) + '</td>'+
            '<td id="foodContentTxt" class="text-left">' +  selectedText + '</td>'+
            '<td id="foodContent" style="display: none;" >' +  selectedId + '</td>'+
            '<td>' + buildQtyCol(rowCount, $('#meetingQty').val()).prop('outerHTML') + '</td>'+
            '<td>' + buildDeleteFoodButton(rowCount).prop('outerHTML') + '</span>'+
            '</tr>';
        $('#foodTable > tbody').append(rowHtml);
    }
    //新增會議餐點資料行-數量欄位
    function buildQtyCol(index, qty) {
        return $('<input/>').addClass('form-control')
            .attr('path', 'foodQty')
            .attr('data-row', index)
            .attr('cssClass', 'form-control d-inline-block num_only')
            .attr('value', qty)
            // .attr('onkeyup', 'storeCaret(this)')
            // .attr('onclick', 'storeCaret(this)')
            .css('width', '60%')
            .css('margin', '0 auto');
    }
    //新增會議餐點資料行-刪除按鍵
    function buildDeleteFoodButton(index) {
        return $('<button/>').text('刪除')
            .addClass('btn btn-sm btn-outline-be')
            .attr('name', 'delete-food')
            .attr('type', 'button')
            .attr('data-row', index)
            .attr('onclick', 'deleteFood(' + index + ')');
    }
    //新增會議餐點資料行-刪除按鍵功能
    function deleteFood(index){
        let rows = $('#foodTable > tbody > tr');
        insertFoodOptionList(rows.eq(index));
        $(rows[index]).remove();
        rows = $('#foodTable > tbody > tr');
        $('#foodTable > tbody').html('');
        rows.each(function (index){
            let row = rows[index];
            let selectedText = $(row).find('td').eq(1).text();
            let selectedId = $(row).find('td').eq(2).text();
            insertFood(selectedText, selectedId);
        })
        var optionCount = $('select[name="foodId"] option').length;
        if(optionCount < 1){
            $('#btnAddFood').prop('disabled', true);
        }else {
            $('#btnAddFood').prop('disabled', false);
        }
    }
    //將刪除項目重新塞回optoins中
    function insertFoodOptionList(row) {
        let foodContent = $(row).find('#foodContent').text();
        let foodContentTxt = $(row).find('#foodContentTxt').text();
        $('#foodId').append('<option value="' + foodContent + '">' + foodContentTxt + '</option>')
    }

    function deleteAllItem(){
        let rows = $('#itemTable > tbody > tr');
        let row_length = rows.length;
        for(let index = 0; index < row_length ; index++){
            insertOptionList(rows.eq(index));
            $(rows[index]).remove();
        }
        var optionCount = $('select[name="itemId"] option').length;
        if(optionCount < 1){
            $('#btnAddItem').prop('disabled', true);
        }else {
            $('#btnAddItem').prop('disabled', false);
        }
    }
    function deleteAllFood(){
        let rows = $('#foodTable > tbody > tr');
        let row_length = rows.length;
        for(let index = 0; index < row_length ; index++){
            insertFoodOptionList(rows.eq(index));
            $(rows[index]).remove();
        }
        var optionCount = $('select[name="foodId"] option').length;
        if(optionCount < 1){
            $('#btnAddFood').prop('disabled', true);
        }else {
            $('#btnAddFood').prop('disabled', false);
        }
    }

</script>
</jsp:attribute>
</tags:layout>