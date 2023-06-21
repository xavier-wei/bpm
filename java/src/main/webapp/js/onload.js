var _disableZoneId = "screenDisabledZone";
var _messageZoneId = "loadingMessageZone";
var _socket = null;

// 進入頁面後, 游標停輸入欄位中，首先存在focusNames的欄位，若無指定則停留在第一筆輸入欄位
function inputFieldFocus(focusNames) {
    try {
        const inputFieldList = $(":input:not(:image, :button, :submit, :reset)").not(":hidden").not("[disabled]").not("[readonly]").not(function () {
            return ($(this).css("visibility").toLowerCase() === "hidden");
        });
        if (inputFieldList.length > 0) {
            let focusName = '';
            if (focusNames) {
                let focusNamesOne = focusNames.toLowerCase().split(',');
                for (let i = 0; i < inputFieldList.length; i++) {
                    let eid = inputFieldList[i].name;
                    if (eid && $.inArray(eid.toLowerCase(), focusNamesOne) > -1) {
                        focusName = eid;
                        break;
                    }
                }
                if (!focusName) {
                    for (let i = 0; i < inputFieldList.length; i++) {
                        let eName = inputFieldList[i].name;
                        if ($.inArray('valid' + eName.toLowerCase(), focusNamesOne) > -1) {
                            focusName = eName;
                            break;
                        }
                    }
                }
                if (!focusName) {
                    let autofocusName = $(':text').filter('[autofocus]').first();
                    if (autofocusName.length > 0) {
                        focusName = autofocusName[0].name;
                    }
                }
            }

            if (focusName) {
                $('[name="' + focusName + '"]').focus();
            } else {
                inputObj = inputFieldList.first();
                if (inputObj.is(":text") || inputObj.is("textarea"))
                    inputObj.focus();
            }
        }
    } catch (e) {
        console.log(e);
    }
}


function beforeUnload() {
    $("#systemMessage").text(""); // 清空訊息區
    $('.alert-danger').text(""); //清空多筆資料的錯誤訊息
    createBlockUiZone();

    if(!checkSocketStatus()){
        setTimeout(unlockAllButtons, 2000);
    }
}

function checkSocketStatus(){
    try {
        if (_socket != null && _socket.readyState === WebSocket.OPEN) {
            return true;
        }
    } catch (e) {
        console.log(e);
    }
    return false;
}

function createBlockUiZone() {
    var bodyObj = document.getElementsByTagName("body").item(0);

    var disabledZone = document.getElementById(_disableZoneId);

    if (!disabledZone) {
        disabledZone = document.createElement('div');
        disabledZone.setAttribute('id', _disableZoneId);

        disabledZone.style.position = "fixed";
        disabledZone.style.zIndex = "999999";
        disabledZone.style.left = "0px";
        disabledZone.style.top = "0px";
        disabledZone.style.width = "100%";
        disabledZone.style.height = "100%";
        disabledZone.style.backgroundImage = "url('images/blockUi.gif')";

        var messageZone = document.createElement('div');
        messageZone.setAttribute('id', _messageZoneId);
        messageZone.style.position = "absolute";
        messageZone.style.bottom = "0px";
        messageZone.style.right = "0px";
        messageZone.style.background = "red";
        messageZone.style.color = "white";
        messageZone.style.fontFamily = "新細明體,細明體,Arial,Helvetica,sans-serif";
        messageZone.style.padding = "4px";
        messageZone.style.border = "1px solid #000000";
        var text = document.createTextNode("處理中, 請稍候...");
        messageZone.appendChild(text);

        disabledZone.appendChild(messageZone);
        bodyObj.appendChild(disabledZone);

        disabledZone.style.display = 'block';
    } else {
        document.getElementById(_messageZoneId).innerHTML = "處理中, 請稍候...";
        disabledZone.style.display = 'block';
        disabledZone.style.visibility = 'visible';
    }
}

function unlockAllButtons() {
    var disabledZone = document.getElementById(_disableZoneId);
    if (disabledZone) {
        disabledZone.style.display = 'none';
    }
}

$(function () {
    $(window).on("beforeunload", beforeUnload);
    inputFieldFocus();
    //dataTable轉中文設定
    $.extend($.fn.dataTable.defaults, getDataTablesConfig());
});