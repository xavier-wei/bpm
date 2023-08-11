<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<%-- 重要列管事項_重要列管事項維護  修改頁--%>
<!doctype html>
<spring:eval var="caseKey" expression="T(tw.gov.pcc.eip.common.controllers.Eip03w010Controller).CASE_KEY" />
<c:set var="caseData" value="${requestScope[caseKey]}" />
<tags:layout>
    <jsp:attribute name="buttons">
        <c:if test="${caseData.trkSts == '暫存'}">
            <tags:button id="btnTemp" data-temp="0">暫存<i class="fas fa-check"></i></tags:button>
        </c:if>
        <tags:button id="btnSave" data-temp="1">儲存<i class="fas fa-user-check"></i></tags:button>
        <tags:button id="btnClear">清除<i class="fas fa-eraser"></i></tags:button>
        <tags:button id="btnBack">返回<i class="fas fa-reply"></i></tags:button>
    </jsp:attribute>
    <jsp:attribute name="contents" >
        <tags:fieldset legend="列管對象">
            <form:form id="eip03w010Form" name="eip03w010Form" modelAttribute="${caseKey}" method="POST">
                <tags:form-row>
                    <div class="col-md">
                        <form:label cssClass="col-form-label" path="trkID">列管編號：</form:label>
                        <c:out value="${caseData.trkID}" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md">
                        <form:label cssClass="col-form-label" path="trkSts">列管狀態：</form:label>
                        <c:out value="${caseData.trkSts}" />
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md d-flex">
                        <form:label cssClass="col-form-label star" path="trkCont">內容：</form:label>
                        <form:textarea path="trkCont" cssClass="form-control " cols="80" rows="5" size="100"  maxlength="1000"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
<%--                    <div class="col-md-6">--%>
<%--                        <form:label cssClass="col-form-label star" path="trkFrom">交辦來源：</form:label>--%>
<%--                        <form:select path="trkFrom" cssClass="form-control d-inline-block">--%>
<%--&lt;%&ndash;                            <form:option value="">請選擇</form:option>&ndash;%&gt;--%>
<%--                            <form:options items="${caseData.trkObjCombobox}" />--%>
<%--                        </form:select>--%>
<%--                    </div>--%>
                    <div class="col-md-6 d-flex">
                        <form:label cssClass="col-form-label" path="trkFrom">交辦來源：</form:label>
                        <form:select path="trkFrom" cssClass="selectpicker form-control" data-live-search="true">
<%--                            <form:option value="">請選擇</form:option>--%>
                            <form:options items="${caseData.trkFromCombobox}" />
                            <form:option value="others">其他</form:option>
                        </form:select>
                    </div>
                    <div class="col-md-4 d-flex" >
                        <form:input path="otherTrkFrom" cssClass="form-control" size="13" maxlength="7" style="display: none" placeholder="請輸入交辦來源"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-4 d-flex">
                        <form:label cssClass="col-form-label star" path="allStDt">全案列管日期：</form:label>
                        <form:input path="allStDt" cssClass="form-control num_only" size="13" maxlength="7"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-3">
                        <form:label cssClass="col-form-label" path="creUser">建立人員：</form:label>
                        <c:out value="${caseData.creDept}-${caseData.creUser}"/>
                    </div>
                    <div class="col-md-3">
                        <form:label cssClass="col-form-label" path="creDt">建立時間：</form:label>
                        <func:minguo value="${caseData.creDt}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-3">
                        <form:label cssClass="col-form-label" path="updUser">更新人員：</form:label>
                        <c:out value="${caseData.updDept }-${caseData.updUser}"/>
                    </div>
                    <div class="col-md-3">
                        <form:label cssClass="col-form-label" path="updDt">更新時間：</form:label>
                        <func:minguo value="${caseData.updDt}"/>
                    </div>
                </tags:form-row>
                <tags:form-row>
                    <div class="col-md-6">
                        <form:label cssClass="col-form-label star" path="trkObj">列管對象：</form:label>
                        <form:select path="trkObj" cssClass="form-control d-inline-block">
                            <form:options items="${caseData.trkObjCombobox}" />
                        </form:select>
                        &nbsp
                        <tags:button id="btnAddItem">加入對象<i class="fas fa-plus"></i></tags:button>
                    </div>
                </tags:form-row>
                <%-- 列管對象表格 --%>
                <tags:form-row cssClass="flex-nowrap">
                    <table id="trkObjTable" class="table table-hover m-2">
                        <thead>
                            <th style="width: 10%">項次</th>
                            <th style="width: 60%">列管對象</th>
                            <th style="display: none"></th>
                            <th>處理狀態</th>
                            <th><span style="color: red">* </span>列管起日</th>
                            <th>列管迄日</th>
                            <th>操作區</th>
                        </thead>
                        <tbody>
                            <c:forEach items="${caseData.keepTrkDtlList}" var="data" varStatus="status">
						        <tr>
                                    <td ><c:out value="${status.index + 1}"/></td>
                                    <td class="text-left" id="itemContentTxt" ><c:out value="${data.trkObj.split('-')[1]}"/></td>
                                    <td id="itemContent" style="display: none"><c:out value="${data.trkObj.split('-')[0]}"/></td>
                                    <td ><c:out value="${data.prcSts}"/></td>
                                    <td ><form:input path="stDt" class="form-control num_only ml-5 stDt" size="7" maxlength="7" value="${data.stDt}"/></td>
                                    <td ><form:input path="endDt" class="form-control num_only ml-5 endDt" size="7" maxlength="7" value="${data.endDt}"/></td>
                                    <td ><button class="btn btn-sm btn-outline-be" name="delete-item" type="button" onclick="deleteItem(${status.index})">刪除</button></td>
                                </tr>
					        </c:forEach>
                        </tbody>
                    </table>
                </tags:form-row>
<%--                <tags:form-row>--%>
<%--                    <div class="col-md d-flex">--%>
<%--                        <form:label cssClass="col-form-label" path="trkSts">列管狀態：</form:label>--%>
<%--                        <form:select path="trkSts" cssClass="form-control selector">--%>
<%--                            <form:option name="" value="">全部</form:option>--%>
<%--                            <c:forEach items="${caseData.trkStsList}" var="item">--%>
<%--                                <form:option name="${item.codeno }" value="${item.codeno }">${item.codename }</form:option>--%>
<%--                            </c:forEach>--%>
<%--                        </form:select>--%>
<%--                    </div>--%>
<%--                </tags:form-row>--%>
                <form:hidden path="temp"/>
                <form:hidden path="jsonMap"/>
                <form:hidden path="mode" value="modify"/>
            </form:form>
        </tags:fieldset>
    </jsp:attribute>
    <jsp:attribute name="footers">
<script>
    $(function(){
        showOtherTrkFrom();

        // 監聽下拉選單的 change 事件
        $('.selectpicker').on('change', function() {
            showOtherTrkFrom();
        });


        // btnTemp 暫存 btnSave儲存
        $('#btnTemp, #btnSave').click(function(e) {
            e.preventDefault();
            let temp = JSON.stringify($(this).data('temp'));  //0為暫存 1為儲存
            $('#temp').val(temp);

            let jsonMap = {};
            $('table tr').each(function (){
                var itemIds = $(this).find('#itemContent').text();
                var rowText = $(this).text();
                var prcSts = $(this).find('td').eq(3).text();
                var stDt = $(this).find('.stDt').val();
                var endDt = $(this).find('.endDt').val();
                // alert(itemIds + rowText  + stDt + allStDtEnd);
                var row = {
                    "rowText" : rowText,
                    "prcSts" : prcSts,
                    "stDt" : stDt,
                    "endDt" : endDt
                }
                jsonMap[itemIds] = row;
            })
            // let trkFrom = $('#trkFrom option:selected').text();
            // alert(trkFrom)
            // $('#trkFrom').val(trkFrom);
            $('#jsonMap').val(JSON.stringify(jsonMap));
            $('#eip03w010Form').attr('action', '<c:url value="/Eip03w010_update.action" />').submit();
        });


        // btnClear 清除
        $('#btnClear').click(function(e) {
            e.preventDefault();
            $("#trkCont").val("");
            //取得當天日期
            var currentDate = new Date(); // 获取当前日期
            var year = (currentDate.getFullYear() - 1911).toString(); // 获取年份
            var month = ((currentDate.getMonth() + 1) < 10 ? '0' + (currentDate.getMonth() + 1) : (currentDate.getMonth() + 1)).toString(); // 获取月份（注意月份从0开始，所以要加1）
            var day = (currentDate.getDate()) < 10 ? '0' + (currentDate.getDate()) : (currentDate.getDate()).toString(); // 获取日期
            $("#stDt").val(year + month + day);
            deleteAllItem();
        });

        //btnBack 返回
        $('#btnBack').click(function(e){
            e.preventDefault();
            $('#eip03w010Form').attr('action', '<c:url value="/Eip03w010_backHome.action" />').submit();

        });

        //btnAddItem
        $('#btnAddItem').click(function(e) {
            var selectedID = $("select[name='trkObj']").val();
            var selectedText = $("select[name='trkObj'] option:selected").text();
            var optionCount = $('select[name="trkObj"] option').length;
            if(optionCount <= 1){
                $('#btnAddItem').prop('disabled', true);
            }else {
                $('#btnAddItem').prop('disabled', false);
            }
            insertItem();
        });
    })

    function deleteAllItem(){
        let rows = $('#trkObjTable > tbody > tr');
        let row_length = rows.length;
        for(let index = 0; index < row_length ; index++){
            insertOptionList(rows.eq(index));
            $(rows[index]).remove();
        }
        var optionCount = $('select[name="trkObj"] option').length;
        if(optionCount < 1){
            $('#btnAddItem').prop('disabled', true);
        }else {
            $('#btnAddItem').prop('disabled', false);
        }
    }

    //新增列管對象資料行
    function insertItem(selectedText, selectedID){
        let rows = $('#trkObjTable > tbody > tr');
        let rowCount = rows.length;
        if(selectedText === undefined){
            selectedText = $('#trkObj option:selected').text();
            selectedID = $('#trkObj option:selected').val();
        }

        var allStDt = $('#allStDt').val();
        var today = new Date();
        var sysdate = parseInt ((today.getFullYear() - 1911).toString() + ((today.getMonth() + 1) >= 10 ? (today.getMonth() + 1).toString() :'0' + (today.getMonth() + 1))   + (today.getDate().toString()));

        $("#trkObj option[value='" + selectedID + "']").remove();
        var rowHtml =
            '<tr>'+
                '<td>'.concat(rowCount+1) + '</td>'+
                '<td id="itemContentTxt" class="text-left">' +  selectedText + '</td>'+
                '<td id="itemContent" style="display: none;">' +  selectedID + '</td>'+
                '<td >' +  "待處理" + '</td>'+
                '<td >' +  '<input path="stDt" class="form-control num_only ml-5 stDt" size="7" maxlength="7" value="' + (allStDt > sysdate? allStDt : sysdate) + '"/>' + '</td>'+
                '<td >' +  '<input path="endDt" class="form-control num_only ml-5 endDt" size="7" maxlength="7"/>' + '</td>'+
                '<td>' + buildDeleteItemButton(rowCount).prop('outerHTML') + '</td>'+
            '</tr>';
        $('#trkObjTable > tbody').append(rowHtml);
    }

    //回填已增加列管對象資料行
    function insertExistItem(selectedText, selectedID, prcSts, stDt, endDt){
        let rows = $('#trkObjTable > tbody > tr');
        let rowCount = rows.length;
        if(selectedText === undefined){
            selectedText = $('#trkObj option:selected').text();
            selectedID = $('#trkObj option:selected').val();
        }

        // var allStDt = $('#allStDt').val();
        // var today = new Date();
        // var sysdate = (today.getFullYear() - 1911) + (today.getMonth() + 1) + (today.getDate());

        $("#trkObj option[value='" + selectedID + "']").remove();
        var rowHtml =
            '<tr>'+
            '<td>'.concat(rowCount+1) + '</td>'+
            '<td id="itemContentTxt" class="text-left">' +  selectedText + '</td>'+
            '<td id="itemContent" style="display: none;">' +  selectedID + '</td>'+
            '<td>' + ( prcSts != null? prcSts : '待處理' ) + '</td>'+
            // '<td >' +  '待處理' + '</td>'+
            '<td>' +  '<input path="stDt" class="form-control num_only ml-5 stDt" size="7" maxlength="7" value="' + stDt + '"/>' + '</td>'+
            '<td>' +  '<input path="endDt" class="form-control num_only ml-5 endDt" size="7" maxlength="7" value="' + endDt + '"/>' + '</td>'+
            '<td>' + buildDeleteItemButton(rowCount).prop('outerHTML') + '</td>'+
            '</tr>';
        $('#trkObjTable > tbody').append(rowHtml);
    }

    //新增列管對象資料行-刪除按鍵
    function buildDeleteItemButton(index) {
        return $('<button/>').text('刪除')
            .addClass('btn btn-sm btn-outline-be')
            .attr('name', 'delete-item')
            .attr('type', 'button')
            .attr('data-row', index)
            .attr('onclick', 'deleteItem(' + index + ')');
    }

    //新增列管對象資料行-刪除按鍵功能
    function deleteItem(index){
        let rows = $('#trkObjTable > tbody > tr');
        insertOptionList(rows.eq(index));
        $(rows[index]).remove();
        rows = $('#trkObjTable > tbody > tr');
        $('#trkObjTable > tbody').html('');
        rows.each(function (index){
            let row =  rows[index];
            let selectedText = $(row).find('td').eq(1).text();
            let selectedId = $(row).find('td').eq(2).text();
            let prcSts = $(row).find('td').eq(3).text();
            let stDt = $(row).find('input').eq(0).val();
            let endDt = $(row).find('input').eq(1).val();
            insertExistItem(selectedText, selectedId, prcSts, stDt, endDt);
        })
        var optionCount = $('select[name="trkObj"] option').length;
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
        $('#trkObj').append('<option value="' + itemContent + '">' + itemContentTxt + '</option>')
    }

    //其他交辦來源輸入框
    function showOtherTrkFrom(){
        let selectedValue = $('#trkFrom option:selected').text();
        if(selectedValue === "其他"){
            $('#otherTrkFrom').show();
        }else{
            $('#otherTrkFrom').val("");
            $('#otherTrkFrom').hide();
        }
    }
</script>

</jsp:attribute>
</tags:layout>