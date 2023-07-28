// --------------------------------------------------------------------------------------
// for jQuery noty
// --------------------------------------------------------------------------------------
// [

Noty.setMaxVisible(1);

// Alert 視窗並且 focus 
// focusIds 格式 'id1,id2...'，顯示訊息後，按下後立刻根據id去focus input欄位，先依照畫面順序找，然後才比傳入ids順序。
function showAlert(message, focusIds) {
    if (message != null && $.trim(message) != "") {
        message = message.replace(/\r\n/gi, "<br>");
        message = message.replace(/\|\|\|/gi, "<br>");

        var notyDialog = new Noty({
            layout: 'center',
            theme: 'relax',
            type: 'warning',
            modal: true,
            text: message,
            callbacks: {
                afterShow: function() {
                    $('#notyButton1').focus();
                },
                afterClose: function(){
                    inputFieldFocus(focusIds);
                }
            },
            buttons: [
                Noty.button('確定', 'notyButton', function () {
                    notyDialog.close();
                }, {id: 'notyButton1', 'data-status': 'ok'})
            ]
        }).show();
    }
}

// Alert 視窗 with callback
function showAlertCallback(message, fn) {
    if (message != null && $.trim(message) != "") {
        message = message.replace(/\r\n/gi, "<br>");
        message = message.replace(/\|\|\|/gi, "<br>");

        var notyDialog = new Noty({
            layout: 'center',
            theme: 'relax',
            type: 'warning',
            modal: true,
            text: message,
            callbacks: {
                afterShow: function() {
                    $('#notyButton1').focus();
                }
            },
            buttons: [
                Noty.button('確定', 'notyButton', function () {
                    fn();
                    notyDialog.close();
                    inputFieldFocus();
                }, {id: 'notyButton1', 'data-status': 'ok'})
            ]
        }).show();
    }
}

// 顯示確認視窗，傳回 promise 物件
function showConfirmPromise(message, focusId) {
    return new Promise((resolve, reject) => {
        message = message.replace(/\r\n/gi, "<br>");
        message = message.replace(/\|\|\|/gi, "<br>");
        var notyDialog = new Noty({
            layout: 'center',
            theme: 'relax',
            type: 'warning',
            modal: true,
            text: message,
            closeWith: [],
            animation: {
                close: 'noty_effects_close_cont'
            },
            callbacks: {
                afterShow: function () {
                    $('#notyButton2').focus();
                }
            },
            buttons: [
                Noty.button('確定', 'notyButton', function () {
                    notyDialog.close();
                    resolve();
                }, {id: 'notyButton1', 'data-status': 'ok'}),
                Noty.button('取消', 'notyButton', function () {
                    notyDialog.options.animation.close = 'noty_effects_close';
                    notyDialog.close();
                    reject();
                    if(focusId){
                        $('#'+focusId).focus();
                    }else{
                        inputFieldFocus();
                    }
                }, {id: 'notyButton2'})
            ]
        }).show();
    });
}

// 後端傳回確認訊息，畫面確認後送出
function showConfirms(confirmResultString) {
    if (confirmResultString.length) {
        const confirmResult = confirmResultString.split('|||');
        confirmResult.reduce((pre, message) => {
            return pre.then(() => {
                let messageArray = message.split('|@|@|');
                return showConfirmPromise(messageArray[0], messageArray[1]);
            });
        }, Promise.resolve())
            .then(() => {
                let confirmResult = $('#_confirm_result');
                let form1 = $('form:first');
                if (form1.length) {
                    if (confirmResult.length) {
                        confirmResult.val("Y");
                    } else {
                        form1.append('<input type="hidden" id="_confirm_result" name="_confirm_result" value="Y"/>');
                    }
                    form1.submit();
                }
            }, () => null);
    }
}

// Confirm 視窗
function showConfirm(message, fn) {
    if (message != null && $.trim(message) != "") {
        message = message.replace(/\r\n/gi, "<br>");
        message = message.replace(/\|\|\|/gi, "<br>");

        var notyDialog = new Noty({
            layout: 'center',
            theme: 'relax',
            type: 'warning',
            modal: true,
            text: message,
            closeWith: [],
            animation: {
                close: 'noty_effects_close_cont'
            },
            callbacks: {
                afterShow: function() {
                    $('#notyButton2').focus();
                }
            },
            buttons: [
                Noty.button('確定', 'notyButton', function () {
                    fn();
                    notyDialog.close();
                }, {id: 'notyButton1', 'data-status': 'ok'}),
                Noty.button('取消', 'notyButton', function () {
                    notyDialog.options.animation.close = 'noty_effects_close';
                    notyDialog.close();
                }, {id: 'notyButton2'})
            ]
        }).show();
    }
}

// Confirm 視窗 with Cancel callback
function showConfirmCallback(message, fn, fn2) {
    if (message != null && $.trim(message) != "") {
        message = message.replace(/\r\n/gi, "<br>");
        message = message.replace(/\|\|\|/gi, "<br>");

        var notyDialog = new Noty({
            layout: 'center',
            theme: 'relax',
            type: 'warning',
            modal: true,
            text: message,
            closeWith: [],
            animation: {
                close: 'noty_effects_close_cont'
            },
            callbacks: {
                afterShow: function() {
                    $('#notyButton2').focus();
                }
            },
            buttons: [
                Noty.button('確定', 'notyButton', function () {
                    fn();
                    notyDialog.close();
                }, {id: 'notyButton1', 'data-status': 'ok'}),
                Noty.button('取消', 'notyButton', function () {
                    fn2();
                    notyDialog.options.animation.close = 'noty_effects_close';
                    notyDialog.close();
                }, {id: 'notyButton2'})
            ]
        }).show();
    }
}

// ]
// for jQuery noty

// --------------------------------------------------------------------------------------
// for DataTabls.js
// --------------------------------------------------------------------------------------
// [

var _DETAIL_CONSTANT_STRING_ = " 詳細資料";
// DataTables language 屬性設定
var DATATABLES_LANG_CFG = {
    processing: "處理中...",
    loadingRecords: "載入中...",
    lengthMenu: "_MENU_ ",
    zeroRecords: "無資料",
    info: " 第 _START_ - _END_ 筆，共 _TOTAL_ 筆",
    infoEmpty: "第 0 到 0 筆，共 0 筆",
    infoFiltered: "(從 _MAX_ 項結果中過濾)",
    infoPostFix: "",
    search: "搜尋:",
    paginate: {
        first: '第一頁',
        last: '最後一頁',
        next: '下一頁',
        previous: '上一頁',
        info: "第 _INPUT_ 頁/共 _TOTAL_ 頁",
    },
    aria: {
        sortAscending: ": 升冪排列",
        sortDescending: ": 降冪排列",
    },
};


// 取得本系統預設之 DataTables Options
//
// 參數說明：
// columnDefs 的說明請參考 https://datatables.net/reference/option/
// exportOptions 的說明請參考 http://www.edihor.datatables.net/extensions/buttons/examples/html5/columns.html
function getDataTablesConfig(columnDefs, exportOptions) {

    if (exportOptions == null)
        exportOptions = {};

    var config = {
        dom: "<'pagination'pli>t",
        autoWidth: false,
        searching: false,
        paging: true,
        pagingType: "input",
        ordering:"true",
        pageLength: 20,
        lengthChange: false,
        order: [],
        language: DATATABLES_LANG_CFG,
    };

    if (columnDefs != null) {
        config.columnDefs = columnDefs;
    }

    return config;
}

function getDataTablesConfig(columnDefs, exportOptions, pageLength) {

    if (exportOptions == null)
        exportOptions = {};

    var config = {
        dom: "<'pagination'pli>t",
        autoWidth: false,
        searching: false,
        paging: true,
        pagingType: "input",
        ordering:"true",
        pageLength: pageLength,
        lengthChange: false,
        order: [],
        language: DATATABLES_LANG_CFG,
    };

    if (columnDefs != null) {
        config.columnDefs = columnDefs;
    }

    return config;
}

function getDataTablesPageIndex(key) {
    if (typeof (sessionStorage) !== "undefined") {
        var pageIndex = sessionStorage.getItem(key);
        if (pageIndex != null)
            return parseInt(pageIndex, 10);
        else
            return 0;
    } else {
        return 0;
    }
}

function saveDataTablesPageIndex(key, pageId) {
    if (typeof (sessionStorage) !== "undefined") {
        sessionStorage.setItem(key, pageId);
    }
}

function resetDataTablesPageIndex(key) {
    if (typeof (sessionStorage) !== "undefined") {
        if (key == null)
            sessionStorage.clear();
        else
            sessionStorage.removeItem(key);
    }
}

// ]
// for DataTabls.js

// --------------------------------------------------------------------------------------
// for 網頁元件處理
// --------------------------------------------------------------------------------------
// [

// 增加下拉選單項目
//
// 用法：
// addSelectListOptions($("#queryDeptId"), listData);
// 其中 listData 是 DWR 回傳的 list 物件
function addSelectListOptions(elementObj, list) {
    if (list != null) {
        for (i = 0; i < list.length; i++) {
            $(elementObj).append($("<option></option>").attr("value", list[i].value).text(list[i].label));
        }
    }
}

// 清除下拉選單項目
//
// 用法：
// removeAllSelectListOptions($("#queryDeptId"));
function removeAllSelectListOptions(elementObj) {
    $(elementObj).val("");
    $(elementObj).find('option').remove();
}

// ]
// for 網頁元件處理

// --------------------------------------------------------------------------------------
// for JSON 處理
// --------------------------------------------------------------------------------------
// [
function javaEncodeStringToJSON(str) {
    if (str == null)
        return null;
    return JSON.parse(str.replace(/&#034;/gi, "\""));
}
// ]
// for JSON 處理

// --------------------------------------------------------------------------------------
// for 文數字處理
// --------------------------------------------------------------------------------------
// [

function unicodeEscape(str) {
    return str.replace(/[\s\S]/g, function (character) {
        var escape = character.charCodeAt().toString(16),
            longhand = escape.length > 2;
        return '\\' + (longhand ? 'u' : 'x') + ('0000' + escape).slice(longhand ? -4 : -2);
    });
}

// 文字全形轉半形
function decodeToAscii(text) {
    var asciiTable = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    var big5Table = "%u3000%uFF01%u201D%uFF03%uFF04%uFF05%uFF06%u2019%uFF08%uFF09%uFF0A%uFF0B%uFF0C%uFF0D%uFF0E%uFF0F%uFF10%uFF11%uFF12%uFF13%uFF14%uFF15%uFF16%uFF17%uFF18%uFF19%uFF1A%uFF1B%uFF1C%uFF1D%uFF1E%uFF1F%uFF20%uFF21%uFF22%uFF23%uFF24%uFF25%uFF26%uFF27%uFF28%uFF29%uFF2A%uFF2B%uFF2C%uFF2D%uFF2E%uFF2F%uFF30%uFF31%uFF32%uFF33%uFF34%uFF35%uFF36%uFF37%uFF38%uFF39%uFF3A%uFF3B%uFF3C%uFF3D%uFF3E%uFF3F%u2018%uFF41%uFF42%uFF43%uFF44%uFF45%uFF46%uFF47%uFF48%uFF49%uFF4A%uFF4B%uFF4C%uFF4D%uFF4E%uFF4F%uFF50%uFF51%uFF52%uFF53%uFF54%uFF55%uFF56%uFF57%uFF58%uFF59%uFF5A%uFF5B%uFF5C%uFF5D%uFF5E";
    var result = "";
    for (var i = 0; i < text.length; i++) {
        var val = unicodeEscape(text.charAt(i)).replace("\\", "%");
        val = val.substring(0, 2) + val.substring(2, 6).toUpperCase();
        var j = big5Table.indexOf(val);
        result += (((j > -1) && (val.length == 6)) ? asciiTable.charAt(j / 6) : text.charAt(i));
    }
    return result;
}

// 文字半形轉全形
function encodeFromAscii(text) {
    var asciiTable = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    var big5Table = "\u3000\uFF01\u201D\uFF03\uFF04\uFF05\uFF06\u2019\uFF08\uFF09\uFF0A\uFF0B\uFF0C\uFF0D\uFF0E\uFF0F\uFF10\uFF11\uFF12\uFF13\uFF14\uFF15\uFF16\uFF17\uFF18\uFF19\uFF1A\uFF1B\uFF1C\uFF1D\uFF1E\uFF1F\uFF20\uFF21\uFF22\uFF23\uFF24\uFF25\uFF26\uFF27\uFF28\uFF29\uFF2A\uFF2B\uFF2C\uFF2D\uFF2E\uFF2F\uFF30\uFF31\uFF32\uFF33\uFF34\uFF35\uFF36\uFF37\uFF38\uFF39\uFF3A\uFF3B\uFF3C\uFF3D\uFF3E\uFF3F\u2018\uFF41\uFF42\uFF43\uFF44\uFF45\uFF46\uFF47\uFF48\uFF49\uFF4A\uFF4B\uFF4C\uFF4D\uFF4E\uFF4F\uFF50\uFF51\uFF52\uFF53\uFF54\uFF55\uFF56\uFF57\uFF58\uFF59\uFF5A\uFF5B\uFF5C\uFF5D\uFF5E";
    var result = "";
    for (var i = 0; i < text.length; i++) {
        var val = text.charAt(i);
        var j = asciiTable.indexOf(val);
        result += (j > -1 ? decodeURI(big5Table.substring(j, j + 1)) : val);
    }
    return result;
}

// 將字串轉換為數值
// 由於 JavaScript 的 parseFloat() 內定會將 0 開頭的字串當成八進位處理,
// 所以若要處理十進位則必須自行處理轉換的動作
function parseNumber(value) {
    if ($.trim(value).length == 0)
        return "";

    // 刪除字串開頭的 "0"
    var zeroIndex = 0;
    while (value.charAt(zeroIndex) == '0') {
        zeroIndex++;
    }
    value = value.substring(zeroIndex, value.length);

    if (value == "")
        value = "0";
    if (value.charAt(0) == '.')
        value = "0" + value;

    if (isNaN(value))
        return "";
    else
        return parseFloat(value);
}

// 字串處理 - 在字串左邊補上指定字元至指定長度
// begin ... [
function leftPad(str, size, padChar) {
    str = "" + str; // 如果傳入的為數字先轉為字串

    if (padChar == null || padChar == "")
        padChar = " ";

    while (str.length < size) {
        str = padChar + str;
    }

    return str;
}

// ] ... end

// 字串處理 - 在字串右邊補上指定字元至指定長度
// begin ... [
function rightPad(str, size, padChar) {
    str = "" + str; // 如果傳入的為數字先轉為字串

    if (padChar == null || padChar == "")
        padChar = " ";

    while (str.length < size) {
        str = str + padChar;
    }

    return str;
}

// ] ... end

// 判斷傳入的字串是否超過指定長度<br>
// 一個中文字為兩個字元
function checkStringMaxLength(str, max) {
    var isValid = true;
    var strlen = 0;
    if (str != null && str != "") {
        for (i = 0; i < str.length; i++) {
            if (isValid) {
                if (str.charCodeAt(i) >= 10000) {
                    strlen += 2;
                } else {
                    strlen += 1;
                }
            }
        }
        if (strlen > max) {
            isValid = false;
        } else {
            isValid = true;
        }
    }
    return isValid;
}

function showMessageByMessageType(message) {
    if (message.startsWith(('A'))) {
        showAlert(message, null);
    } else {
        $('#msgarea').addClass('newheight');
        $('.infoword').text(message);
    }
}

function showMessageWhenAjaxFail(xhr) {
    let message = xhr.getResponseHeader('errors');
    if (message) {
        message = decodeURIComponent(message);
        showAlert(message, null);
    }
}

// ]
// for 文數字處理

// --------------------------------------------------------------------------------------
// 日期處理函式
// --------------------------------------------------------------------------------------

// 取得系統日期(八碼)，若使用民國日請搭配changeDateType
//
// 參數:
//
// 回傳值:
//
// 範例:
// getSysdate() - 20210101

//
// begin ... [
function getSysdate() {
    var d = new Date();
    var month = d.getMonth() + 1;
    var day = d.getDate();
    var output = d.getFullYear()  +
        (month < 10 ? '0' : '') + month +
        (day < 10 ? '0' : '') + day;
    return output;
}

// ] ... end

// 檢查傳入之日期是否為有效日期
//
// 參數:
// dateValue - 日期, 可接受民國及西元日期
//
// 回傳值:
// 是 - true
// 否 - false
//
// 範例:
// isValidDate("0960229") - false
// isValidDate("20070229") - false
// isValidDate("0970229") - true
// isValidDate("20080229") - true
//
// begin ... [
function isValidDate(dateValue) {
    return isValidDateWithROC(dateValue, false)
}

// ] ... end

// 檢查傳入之日期是否為有效日期
//
// 參數:
// dateValue - 日期, 可接受民國及西元日期
// bBeforeROC - true 民國前 false 民國
//
// 回傳值:
// 是 - true
// 否 - false
//
// begin ... [
function isValidDateWithROC(dateValue, bBeforeROC) {
    var isValid = true;
    var iValue = $.trim(dateValue);
    var pDateType = iValue.length;
    var regN = /^[0-9]+$/;

    if ((iValue.length != 8 && iValue.length != 7) || !regN.test(iValue) || (isNaN(parseNumber(iValue)))) {
        isValid = false;
    } else {
        var nYear = 0;
        var nDay = parseNumber(iValue.substr((pDateType - 2), 2));
        var nMon = parseNumber(iValue.substr((pDateType - 4), 2)) - 1;

        if (pDateType == 7) {
            if (bBeforeROC)
                nYear = 1912 - parseNumber(iValue.substr(0, (pDateType - 4)));
            else
                nYear = parseNumber(iValue.substr(0, (pDateType - 4))) + 1911;
        } else {
            nYear = parseNumber(iValue.substr(0, (pDateType - 4)));
        }

        var sDate = new Date(Date.UTC(nYear, nMon, nDay, 0, 0, 0));

        if (!((sDate.getUTCFullYear() == nYear) && (sDate.getMonth() == nMon) && (sDate.getDate() == nDay))) {
            isValid = false;
        }
    }

    return isValid;
}

// ] ... end

// 民國日期轉西元, 西元日期轉民國
// (若傳入民國日期, 僅可傳入民國後日期)
//
// 參數:
// sDate - 民國或西元日期
//
// 範例:
// changeDateType("0961231") - "20071231"
// changeDateType("20071231") - "0961231"
// changeDateType("0960229") - "0960229" (日期不合法, 回傳原值)
// changeDateType("20070229") - "20070229" (日期不合法, 回傳原值)
//
// begin ... [
function changeDateType(sDate) {
    sDate = $.trim(sDate);

    if (!isValidDate(sDate))
        return sDate;

    if (sDate.length == 7) {
        return leftPad(parseNumber(sDate.substring(0, 3)) + 1911, 4, "0") + sDate.substring(3, 5) + sDate.substring(5, 7);
    } else if (sDate.length == 8) {
        return leftPad(parseNumber(sDate.substring(0, 4)) - 1911, 3, "0") + sDate.substring(4, 6) + sDate.substring(6, 8);
    } else {
        return sDate;
    }
}
// 傳入民國年/西元年 傳回民國年
function changeDateTypeToChineseDate(sDate) {
    sDate = $.trim(sDate);

    if (!isValidDate(sDate))
        return sDate;

    if (sDate.length == 8) {
        return leftPad(parseNumber(sDate.substring(0, 4)) - 1911, 3, "0") + sDate.substring(4, 6) + sDate.substring(6, 8);
    } else {
        return sDate;
    }
}

// 傳入民國年/西元年 傳回西元國年
function changeDateTypeToWestDate(sDate) {
    sDate = $.trim(sDate);

    if (!isValidDate(sDate))
        return sDate;

    if (sDate.length == 7) {
        return leftPad(parseNumber(sDate.substring(0, 3)) + 1911, 4, "0") + sDate.substring(3, 5) + sDate.substring(5, 7);
    } else {
        return sDate;
    }
}

// ] ... end

// 日期加減運算
// 往後為加, 往前為減
//
// 參數:
// sDate - 民國或西元日期
// nDay - 欲加減的天數
//
// 回傳值:
// 傳入 民國日期 - 計算後之民國日期
// 傳入 西元日期 - 計算後之西元日期
//
// 範例:
// calDay("0961231", 1) - "0970101"
// calDay("20071231", 1) - "20080101"
// calDay("0960101", 1) - "0960102"
// calDay("20070101", 1) - "20070102"
// calDay("0961211", -15) - "0961126"
// calDay("20071211", -15) - "20071126"
//
// begin ... [
function calDay(sDate, nDay) {
    sDate = $.trim(sDate);

    if (!isValidDate(sDate))
        return sDate;

    var bChinese = false;

    if (sDate.length == 7)
        bChinese = true;

    var baseDate = sDate;
    if (bChinese)
        baseDate = changeDateType(baseDate);

    var theDate = new Date(parseNumber(baseDate.substring(0, 4)), parseNumber(baseDate.substring(4, 6)) - 1, baseDate.substring(6, 8), 00, 00, 00);
    var b = theDate.getDate();
    b = b + nDay;
    theDate.setDate(b);

    if (bChinese)
        return leftPad(theDate.getFullYear() - 1911, 3, "0") + leftPad(theDate.getMonth() + 1, 2, "0") + leftPad(theDate.getDate(), 2, "0");
    else
        return leftPad(theDate.getFullYear(), 4, "0") + leftPad(theDate.getMonth() + 1, 2, "0") + leftPad(theDate.getDate(), 2, "0");
}

// ]
// for 日期處理函式

// --------------------------------------------------------------------------------------
// Other
// --------------------------------------------------------------------------------------
// [

// 畫面清空
function cleanForm() {
    for (i = 0; i < document.forms[0].length; i++) {
        obj = document.forms[0].elements[i];
        if (obj.disabled != true && obj.readOnly != true) {
            switch (obj.type) {
                case "text":
                    obj.value = "";
                    break;
                case "textarea":
                    obj.value = "";
                    break;
                case "password":
                    obj.value = "";
                    break;
                case "radio":
                    var objName = obj.getAttribute("name");
                    elements = document.getElementsByName(objName);
                    for (j = 0; j < elements.length; j++) {
                        if (j == 0)
                            elements[j].checked = true;
                        else
                            elements[j].checked = false;
                    }
                    break;
                case "checkbox":
                    obj.checked = false;
                    break;
                case "select-one":
                    obj.options[0].selected = true;
                    break;
                case "select-multiple":
                    while (obj.selectedIndex != -1) {
                        indx = obj.selectedIndex;
                        obj.options[indx].selected = false;
                    }
                    obj.selected = false;
                    break;
                case "file":
                    obj.outerHTML = obj.outerHTML;
                    break;
            }
        }
    }
}

// 取代 window.showModalDialog 因為 chrome 沒有 showModalDialog
var ModalDialog = {
    // 跟 showModalDialog 參數一樣，最前方帶入 callback 即可
    // 因為使用 window.open 所以視窗樣式的參數也請修正為 open 的方式
    show: function (callback, url, obj, options) {
        if (obj) window.dialogArguments = obj;
        var sub = window.open(url, '', options);
        sub.focus();

        window.showModalDialogDtd = $.Deferred();
        $.when(window.showModalDialogDtd).done(function (obj) {
            window.dialogArguments = undefined;
            window.showModalDialogDtd = undefined;
            sub.close();
            callback(obj);
        });
    },
    // 在子視窗取得 父視窗參數的方法
    arg: function () {
        return window.opener.dialogArguments;
    },
    // 子視窗關閉時，請務必呼叫此方法，才會呼叫 callback，若有回傳的 object 也須在此傳入
    close: function (obj) {
        window.opener.showModalDialogDtd.resolve(obj);
        window.close();
    }
};

// 類似 disabled method，true > pass, false > disabled
(function ($) {
    $.fn.passVal = function (isPass) {
        if (isPass)
            this.off('focus');
        else
            this.focus(function () {
                this.blur();
            });
    };
})(jQuery);

function unAsc(text) {
    var asciiTable = "*";
    var big5Table = "\uFF0A";
    var result = "";
    for (var i = 0; i < text.length; i++) {
        var val = text.charAt(i);
        result += (val == "*" ? decodeURI(big5Table) : val);
    }
    return result;
}

function unAscCode(text) {
    var asciiTable = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    var big5Table = "\uFF01\u201D\uFF03\uFF04\uFF05\uFF06\u2019\uFF08\uFF09\uFF0A\uFF0B\uFF0C\uFF0D\uFF0E\uFF0F\uFF10\uFF11\uFF12\uFF13\uFF14\uFF15\uFF16\uFF17\uFF18\uFF19\uFF1A\uFF1B\uFF1C\uFF1D\uFF1E\uFF1F\uFF20\uFF21\uFF22\uFF23\uFF24\uFF25\uFF26\uFF27\uFF28\uFF29\uFF2A\uFF2B\uFF2C\uFF2D\uFF2E\uFF2F\uFF30\uFF31\uFF32\uFF33\uFF34\uFF35\uFF36\uFF37\uFF38\uFF39\uFF3A\uFF3B\uFF3C\uFF3D\uFF3E\uFF3F\u2018\uFF41\uFF42\uFF43\uFF44\uFF45\uFF46\uFF47\uFF48\uFF49\uFF4A\uFF4B\uFF4C\uFF4D\uFF4E\uFF4F\uFF50\uFF51\uFF52\uFF53\uFF54\uFF55\uFF56\uFF57\uFF58\uFF59\uFF5A\uFF5B\uFF5C\uFF5D\uFF5E";
    var result = "";
    for (var i = 0; i < text.length; i++) {
        var val = text.charAt(i);
        var j = asciiTable.indexOf(val);
        result += (j > -1 ? decodeURI(big5Table.substring(j, j + 1)) : val);
    }
    return result;
}

// for prototype String method
if (typeof String.prototype.startsWith != 'function') {
    String.prototype.startsWith = function (pattern) {
        return this.indexOf(pattern) === 0;
    };
}

if (typeof String.prototype.endsWith != 'function') {
    String.prototype.endsWith = function (pattern) {
        var d = this.length - pattern.length;
        return d >= 0 && this.lastIndexOf(pattern) === d;
    };
}

if (typeof String.prototype.include != 'function') {
    String.prototype.include = function (pattern) {
        return this.indexOf(pattern) > -1;
    };
}

// ]
// for Other
// =====檢查身份證字號邏輯===========================================================
function chkId(TId) {
    if (TId != "") {
        var Id = TId.charAt(0).toUpperCase();
        var Falg = "";

        if (TId.length != 10) {
            return false;
        }

        if (Id < 'A' || Id > 'Z') {
            return false;
        }

        //if ((TId.charAt(1) != '1') && (TId.charAt(1) != '2')) {
        if('1,2,8,9'.indexOf(TId.charAt(1)) < 0){
            return false;
        }

        var val = new Array(11);

        for (i = 1; i <= 9; i++)
            val[i + 1] = parseInt(TId.charAt(i));

        if (Id == 'A') {
            val[0] = 1;
            val[1] = 0;
        } else if (Id == 'B') {
            val[0] = 1;
            val[1] = 1;
        } else if (Id == 'C') {
            val[0] = 1;
            val[1] = 2;
        } else if (Id == 'D') {
            val[0] = 1;
            val[1] = 3;
        } else if (Id == 'E') {
            val[0] = 1;
            val[1] = 4;
        } else if (Id == 'F') {
            val[0] = 1;
            val[1] = 5;
        } else if (Id == 'G') {
            val[0] = 1;
            val[1] = 6;
        } else if (Id == 'H') {
            val[0] = 1;
            val[1] = 7;
        } else if (Id == 'I') {
            val[0] = 3;
            val[1] = 4;
        } else if (Id == 'J') {
            val[0] = 1;
            val[1] = 8;
        } else if (Id == 'K') {
            val[0] = 1;
            val[1] = 9;
        } else if (Id == 'L') {
            val[0] = 2;
            val[1] = 0;
        } else if (Id == 'M') {
            val[0] = 2;
            val[1] = 1;
        } else if (Id == 'N') {
            val[0] = 2;
            val[1] = 2;
        } else if (Id == 'O') {
            val[0] = 3;
            val[1] = 5;
        } else if (Id == 'P') {
            val[0] = 2;
            val[1] = 3;
        } else if (Id == 'Q') {
            val[0] = 2;
            val[1] = 4;
        } else if (Id == 'R') {
            val[0] = 2;
            val[1] = 5;
        } else if (Id == 'S') {
            val[0] = 2;
            val[1] = 6;
        } else if (Id == 'T') {
            val[0] = 2;
            val[1] = 7;
        } else if (Id == 'U') {
            val[0] = 2;
            val[1] = 8;
        } else if (Id == 'V') {
            val[0] = 2;
            val[1] = 9;
        } else if (Id == 'W') {
            val[0] = 3;
            val[1] = 2;
        } else if (Id == 'X') {
            val[0] = 3;
            val[1] = 0;
        } else if (Id == 'Y') {
            val[0] = 3;
            val[1] = 1;
        } else if (Id == 'Z') {
            val[0] = 3;
            val[1] = 3;
        }

        var check = 0;
        check = (val[1] * 9 + val[0]) % 10;
        for (i = 2; i <= 9; i++)
            check += val[i] * (10 - i);

        var remainder = check % 10;
        remainder = remainder + val[10];
        if ((remainder % 10) == 0) {
            return true;
        } else {
            return false;
        }
    }
    return true;
}

//=====檢查居留證號邏輯===========================================================
function chkIdRp(TId) {
    if (TId != "") {
        var Id = TId.charAt(0).toUpperCase();
        var Id2 = TId.charAt(1).toUpperCase();
        var Falg = "";

        if (TId.length != 10) {
            return false;
        }

        if (Id < 'A' || Id > 'Z') {
            return false;
        }

        //if ((TId.charAt(1) != '1') && (TId.charAt(1) != '2')) {
        if('A,B,C,D'.indexOf(TId.charAt(1)) < 0){
            return false;
        }

        var val = new Array(11);

        for (i = 2; i <= 9; i++){
            val[i + 1] = parseInt(TId.charAt(i));
        }

        if (Id == 'A') {
            val[0] = 1;
            val[1] = 0;
        } else if (Id == 'B') {
            val[0] = 1;
            val[1] = 1;
        } else if (Id == 'C') {
            val[0] = 1;
            val[1] = 2;
        } else if (Id == 'D') {
            val[0] = 1;
            val[1] = 3;
        } else if (Id == 'E') {
            val[0] = 1;
            val[1] = 4;
        } else if (Id == 'F') {
            val[0] = 1;
            val[1] = 5;
        } else if (Id == 'G') {
            val[0] = 1;
            val[1] = 6;
        } else if (Id == 'H') {
            val[0] = 1;
            val[1] = 7;
        } else if (Id == 'I') {
            val[0] = 3;
            val[1] = 4;
        } else if (Id == 'J') {
            val[0] = 1;
            val[1] = 8;
        } else if (Id == 'K') {
            val[0] = 1;
            val[1] = 9;
        } else if (Id == 'L') {
            val[0] = 2;
            val[1] = 0;
        } else if (Id == 'M') {
            val[0] = 2;
            val[1] = 1;
        } else if (Id == 'N') {
            val[0] = 2;
            val[1] = 2;
        } else if (Id == 'O') {
            val[0] = 3;
            val[1] = 5;
        } else if (Id == 'P') {
            val[0] = 2;
            val[1] = 3;
        } else if (Id == 'Q') {
            val[0] = 2;
            val[1] = 4;
        } else if (Id == 'R') {
            val[0] = 2;
            val[1] = 5;
        } else if (Id == 'S') {
            val[0] = 2;
            val[1] = 6;
        } else if (Id == 'T') {
            val[0] = 2;
            val[1] = 7;
        } else if (Id == 'U') {
            val[0] = 2;
            val[1] = 8;
        } else if (Id == 'V') {
            val[0] = 2;
            val[1] = 9;
        } else if (Id == 'W') {
            val[0] = 3;
            val[1] = 2;
        } else if (Id == 'X') {
            val[0] = 3;
            val[1] = 0;
        } else if (Id == 'Y') {
            val[0] = 3;
            val[1] = 1;
        } else if (Id == 'Z') {
            val[0] = 3;
            val[1] = 3;
        }

        if (Id2 == 'A') {
            val[2] = 0;
        } else if (Id2 == 'B') {
            val[2] = 1;
        } else if (Id2 == 'C') {
            val[2] = 2;
        } else if (Id2 == 'D') {
            val[2] = 3;
        } else if (Id2 == 'E') {
            val[2] = 4;
        } else if (Id2 == 'F') {
            val[2] = 5;
        } else if (Id2 == 'G') {
            val[2] = 6;
        } else if (Id2 == 'H') {
            val[2] = 7;
        } else if (Id2 == 'I') {
            val[2] = 4;
        } else if (Id2 == 'J') {
            val[2] = 8;
        } else if (Id2 == 'K') {
            val[2] = 9;
        } else if (Id2 == 'L') {
            val[2] = 0;
        } else if (Id2 == 'M') {
            val[2] = 1;
        } else if (Id2 == 'N') {
            val[2] = 2;
        } else if (Id2 == 'O') {
            val[2] = 5;
        } else if (Id2 == 'P') {
            val[2] = 3;
        } else if (Id2 == 'Q') {
            val[2] = 4;
        } else if (Id2 == 'R') {
            val[2] = 5;
        } else if (Id2 == 'S') {
            val[2] = 6;
        } else if (Id2 == 'T') {
            val[2] = 7;
        } else if (Id2 == 'U') {
            val[2] = 8;
        } else if (Id2 == 'V') {
            val[2] = 9;
        } else if (Id2 == 'W') {
            val[2] = 2;
        } else if (Id2 == 'X') {
            val[2] = 0;
        } else if (Id2 == 'Y') {
            val[2] = 1;
        } else if (Id2 == 'Z') {
            val[2] = 3;
        }

        var check = 0;
        check = (val[1] * 9 + val[0]) % 10;

        for (i = 2; i <= 9; i++)
            check += val[i] * (10 - i);

        var remainder = check % 10;
        remainder = remainder + val[10];
        if ((remainder % 10) == 0) {
            return true;
        } else {
            return false;
        }
    }
    return true;
}

//=====全選===========================================================
function checkall(){
    var group = $(this).data('checkall');
    if(group){
        if($(this).prop('checked')){
            $("input:checkbox:enabled[data-check="+group+"]:not(:checked)").click();
        }else{
            $("input:checkbox:enabled[data-check="+group+"]:checked").click();
        }
    }
}
