<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<tags:layout>
<jsp:attribute name="contents">
    <div>個人儀表板</div>
    <div id="imgDiv"></div>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        // let ticket = '';
        $(async function () {
            getTableauPicture()
            let ticket = await getTicket();
            console.log("ticket",ticket)
            if(ticket!==''){
                getAllTableauData(ticket);
            }
        });

        function checkIfFromMenu(ticket) {
            const path = window.location.pathname; // URL，如 "/tableau_enter.action/BID_01_02"
            const parts = path.split('/');
            const paramValue = parts[parts.length - 1];

            if (paramValue) {
                openTableau(paramValue,ticket);
            }
        }


        //獲取tableau授權碼，不用再二次登入
        function getTicket() {
            return new Promise((resolve, reject) => {
                $.ajax({
                    url: '<c:url value="/Common_getTableauTicket.action" />',
                    type: 'POST',
                    async: true,
                    timeout: 100000,
                    success: function(response) {
                        if (response && response.ticket) {
                            let ticket = response.ticket;
                            resolve(ticket);
                        } else {
                            reject(new Error('無法獲取有效的 ticket'));
                        }
                    },
                    error: function(error) {
                        console.error(error);
                        reject(error);
                    }
                });
            });
        }


        let backendResponse;
        //取得所有儀表板資訊
        function getAllTableauData(ticket) {
        $.ajax({
              url: '<c:url value="/Common_getAllTableauData.action" />',
              type: 'POST',
              async: true,
              timeout: 100000,
              success: function(response) {
              if (response) {
                backendResponse = response
                checkIfFromMenu(ticket);
              }
            },
            error: function(error) {
              console.error(error);
            }
            });
        }


        function openTableau(type,ticket) {
            console.log("type", type);
            // 顯示確認視窗
            const isConfirmed = confirm('將開啟Tableau視窗，確認繼續嗎？');
            if (isConfirmed) {
                try {
                    // 等待获取 ticket 的 Promise 解决
                    // let ticket =  getTicket();

                    const dashboardFigId = type;  //BID_01_01.action
                    const foundImage = backendResponse.find(item =>  dashboardFigId.include(item.dashboardFigId));
                    if (foundImage) {
                        foundImage.tableauNewUrl = foundImage.tableauUrl.replace("#", "trusted/" + ticket);
                        console.log(foundImage.tableauNewUrl);
                        window.open(foundImage.tableauNewUrl, "_blank");
                        // window.history.back();
                    } else {
                        alert('找不到對應的儀錶板網址');
                    }
                } catch (error) {
                    alert(error.message || '發生錯誤');
                }
            }
        }

        //取得儀錶板圖片
        function getTableauPicture() {
            const path = window.location.pathname; // URL，如 "/tableau_enter.action/BID_01_02.action"
            const parts = path.split('/');
            const tableauId = parts[parts.length - 1].replace(".action",""); //BID_01_02
            var data = {};
            data["dashboardFigId"] = tableauId;
            $.ajax({
                url: '<c:url value="/Common_getTableauPicture.action" />',
                type: 'POST',
                contentType : "application/json",
                data : JSON.stringify(data),
                success: function(response) {
                    if (response) {
                        if(response.length!=0){
                            createImage(response)
                        }else{
                            console.log("查無儀表板")
                        }
                    }
                },
                error: function(error) {
                    console.error(error);
                }
            });
        }

        function createImage(imageData){
            const imgDiv = document.getElementById("imgDiv");
            console.log("imgDiv",imgDiv)
            const img = document.createElement("img");
            const base64String = imageData.imageBase64String;
            console.log("base64String",base64String)
            img.src = "data:image/png;base64," + base64String;
            img.style.borderRadius = "10px";
            img.style.maxWidth = "100%";
            img.style.maxHeight = "100%";
            img.alt = "儀錶板";
            imgDiv.appendChild(img);
        }

        

</script>
</jsp:attribute>
</tags:layout>