<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 會議室查詢/維護作業 編輯畫面 --%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip06w010Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="buttons">
    <!-- 選擇頁 -->
        <tags:button id="btnUpdate">儲存<i class="fas fa-user-check"></i></tags:button>
        <tags:button id="btnClear">清除<i class="fas fa-eraser"></i></tags:button>
	    <tags:button id="btnBack">返回<i class="fas fa-reply"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents">
        <tags:fieldset legend="會議編輯">
            <form:form id="eip06w010Form" name="eip06w010Form" modelAttribute="${caseKey}" method="POST">
                <tags:form-row>
                    <div class="col-md-5 d-flex">
                        <form:label cssClass="col-form-label star" path="meetingName">會議名稱：</form:label>
                        <form:input path="meetingName" cssClass="form-control" size="21" maxlength="100"/>
                    </div>
                    <div class="col-md-3 d-flex">
                         <form:label cssClass="col-form-label star" path="chairman">主持人：</form:label>
                         <form:input path="chairman" cssClass="form-control" size="9" maxlength="15"/>
                    </div>
                    <div class="col-md-3">
                         <form:label cssClass="col-form-label" path="organizerId">申請人：</form:label>
                         <c:out value="${caseData.organizerIdName}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-5 d-flex">
                        <form:label cssClass="col-form-label star" path="meetingdt">會議日期：</form:label>
<%--                        <form:input path="meetingdt" cssClass="form-control" type="date"/>--%>
                        <form:input id="meetingdt"  name="meetingdt"  path="meetingdt" cssClass="form-control num_only dateTW" size="8" maxlength="7" />
                    </div>
                    <div class="col-md d-flex">
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
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-5 d-flex">
                        <form:label cssClass="col-form-label star" path="roomId">場地：</form:label>
                        <form:select path="roomId" cssClass="form-control selector" disabled="true" >
<%--                            <c:forEach items="${caseData.roomIdList}" var="item">--%>
<%--                                <form:option name="${item.itemName }" value="${item.itemId }">${item.itemId } - ${item.itemName }</form:option>--%>
<%--                            </c:forEach>--%>
                        </form:select>
                    </div>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label star" path="meetingQty">開會人數：</form:label>
                        <form:input path="meetingQty" cssClass="form-control num_only" size="3" disabled="true"/>
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
                <form:hidden path="maxMeetingDays"/>
                <form:hidden path="meetingId"/>
                <form:hidden path="admin"/>
                <tags:form-note>
                    <tags:form-note-item><span class="red">＊</span>為必填欄位。</tags:form-note-item>
                </tags:form-note>
            </form:form>
        </tags:fieldset>
    </jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function(){
        //設定會議日期最大值
        let meetingdt = (parseInt($('#meetingdt').val().substring(0,3)) + 1911) + "-" + $('#meetingdt').val().substring(3,5) + "-" + $('#meetingdt').val().substring(5)
        let maxMeetingDays = parseInt($('#maxMeetingDays').val());
        var maxDate = new Date();
        maxDate.setDate(maxDate.getDate() + maxMeetingDays)
        let $dateTW = $(".dateTW");
        $dateTW.datepicker("setStartDate", new Date() > new Date(meetingdt) ? new Date(meetingdt) : new Date());
        $dateTW.datepicker("setEndDate", maxDate);

        //頁面一進入就先刷新場地下拉選單
        findValidRoom();
        findSelectedItemandFood();

        // 依選擇會議日期、時間判斷會議室是否已預約或禁用
        $('#meetingdt_OUTSIDE, #meetingBegin, #meetingEnd').change(function () {
            findValidRoom();
        })

        //btnsave
        $('#btnUpdate').click(function(e) {
            e.preventDefault();
            let itemIds = $('#itemTable #itemContent').map(function() {
                return $(this).text();
            }).get();
            let foodIds = $('#foodTable #foodContent').map(function() {
                return $(this).text();
            }).get();
            let foodQtys = $('input[path="foodQty"]').map(function() {
                return $(this).val();
            }).get();

            let food_Qtys = [];
            for (let i = 0; i < foodIds.length; i++){
                food_Qtys.push(parseInt(foodQtys[i]));
            }
            let foodId_Qty = [];
            for (let i = 0; i < foodIds.length; i++){
                foodId_Qty.push({fooId: foodIds[i], foodQty: foodQtys[i]});
            }

            $('#itemIds').val(itemIds);
            $('#foodId_Qty').val(JSON.stringify(foodId_Qty));
            $('#food_Qty').val(JSON.stringify(food_Qtys));
            $('#eip06w010Form').attr('action', 'Eip06w010_update.action').submit();
        });

        //btnClear 清除
        $('#btnClear').click(function(e) {
            e.preventDefault();
            $('#meetingName').val('');
            $('#chairman').val('');
            $('#meetingdt').val("");
            $('#meetingBegin').val("");
            $('#meetingEnd').val("");
            $('#roomId').prop('disabled', true);
            $('#meetingQty').prop('disabled', true);

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
        //返回btnBack
        $('#btnBack').click(function(e) {
            e.preventDefault();
            $('#eip06w010Form').attr('action', '<c:url value="/Eip06w010_backHome.action" />').submit();
        });
        //場地有變，更新開會人數
        $('select[name="roomId"]').on('change',function (){
            updateMaxMeetingQty();
        })
        //欄位驗證: 會議結束時間有變，判斷時間是否早於會議開始時間
        $('select[name="meetingEnd"]').change(function (){
            let meetingBegin = $('#meetingBegin').val();
            let meetingEnd = $(this).val();
            var data = {
                'meetingBegin': meetingBegin,
                'meetingEnd': meetingEnd
            };
            $.ajax({
                url: '<c:url value="Eip06w010_isMeetingEndValid.action"/>',
                type: 'POST',
                contentType : 'application/json',
                data: JSON.stringify(data),
                success: function (data){
                    if(data === false){
                        showAlert('「會議開始時間」須早於「會議結束時間」', null)
                    }
                },
                error:function(jqXHR, textStatus, errorThrown) {
                    // 請求失敗時的回調函數
                    console.log(textStatus + ': ' + errorThrown);
                }
            })
        })
        //欄位驗證: 開會人數是否大於最大值
        $('input[name="meetingQty"]').blur(function (){
            let meetingQty = $(this).val();
            let roomId = $('#roomId').val();
            let data = {
                'roomId': roomId
            };
            $.ajax({
                url: '<c:url value="Eip06w010_findMaxMeetingQty.action"/>',
                type: 'POST',
                contentType : 'application/json',
                data: JSON.stringify(data),
                success: function (data){
                    if(parseInt(data) < parseInt(meetingQty)){
                        showAlert('此會議室可容納人數上限為'+ data + '人，請確認是否仍要借用此會議室', null)
                    }
                },
                error:function(jqXHR, textStatus, errorThrown) {
                    // 請求失敗時的回調函數
                    console.log(textStatus + ': ' + errorThrown);
                }
            })
        })
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
        if(selectedItemIdMap[0] !== "{" ){
            for (let i = 0 ; i < selectedItemIdMap.length ; i++ ){
                let selectedText = selectedItemIdMap[i].split(":")[0].split("-")[1].replace("\"","");  //擴音
                let selectedId = selectedItemIdMap[i].split("-")[0].replace("{","").replace("\"","").trim();      //ID
                let rowCount = i+1;
                var rowHtml =
                    '<tr>'+
                    '<td>'.concat(rowCount) + '</td>'+
                    '<td  id="itemContentTxt" class="text-left">' +  selectedText + '</td>'+
                    '<td  id="itemContent" style="display: none;">' +  selectedId + '</td>'+
                    '<td>' + buildDeleteItemButton(rowCount).prop('outerHTML') + '</td>'+
                    '</tr>';
                $('#itemTable > tbody').append(rowHtml);
            }
        }
    }
    //initFoodTable
    function initFoodTable(selectedFoodIdMap){
        // {A01-茶水=20, A02-水果=20}
        // let selectedFoodIdMap = $('#selectedFoodIdMap').val().split(",");
        selectedFoodIdMap = selectedFoodIdMap.split(",");
        // "A01-茶水":"20","A02-水果":"20"}
        if(selectedFoodIdMap !== "{"){
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
                    '<td>' + buildQtyCol(rowCount, selectedQty).prop('outerHTML') + '</td>'+
                    '<td>' + buildDeleteFoodButton(rowCount).prop('outerHTML') + '</span>'+
                    '</tr>';
                $('#foodTable > tbody').append(rowHtml);
            }
        }
    }
    //findValidRoom
    function findValidRoom() {
        //依選擇會議日期、時間判斷會議室是否已預約或禁用
        let meetingdt = $('#meetingdt_OUTSIDE').val();
        let meetingBegin = $('#meetingBegin').val();
        let meetingEnd = $('#meetingEnd').val();
        //當會議室時間及起始有值就enable會議室選單
        if(meetingdt && meetingBegin && meetingEnd ){
            $('#roomId').prop('disabled', false);
            $('#meetingQty').prop('disabled', false);
        }else {
            $('#roomId').prop('disabled', true);
            $('#meetingQty').prop('disabled', true);
        }

        //任一欄位為空，直接返回，不執行以下代碼(ajax)
        if(!meetingdt || !meetingBegin || !meetingEnd){
            return;
        }
        var data = {
            'meetingdt': meetingdt,
            'meetingBegin': meetingBegin,
            'meetingEnd': meetingEnd,
            'meetingId': $('#meetingId').val()
        };
        $.ajax({
            url: '<c:url value="Eip06w010_findValidRoominclBooked.action"/>',
            type: 'POST',
            contentType : 'application/json',
            data: JSON.stringify(data),
            cache:false,
            ifModified :true ,
            async:false,
            success: function (data){
                $("#roomId").html("");
                var meetingRoomCombobox = JSON.stringify(data.meetingRoomCombobox);

                for(var i = 0; i < meetingRoomCombobox.split(",").length ; i++){
                    var option = $("<option></option>").attr("value", meetingRoomCombobox.split(",")[i].split(':')[0].replace("{","").replaceAll("\"",""))
                        .text(meetingRoomCombobox.split(",")[i].split(':')[1].replace("}","").replaceAll("\"",""));
                    $("#roomId").append(option);
                }
                // 如果meetingQty = 0 才執行更新會議人數
                if($('#meetingQty').val() === 0){
                    updateMaxMeetingQty();
                }

            },
            error:function(jqXHR, textStatus, errorThrown) {
                // 請求失敗時的回調函數
                console.log(textStatus + ': ' + errorThrown);
            }
        })
    }
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
            url: '<c:url value="Eip06w010_findMaxMeetingQty.action"/>',
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