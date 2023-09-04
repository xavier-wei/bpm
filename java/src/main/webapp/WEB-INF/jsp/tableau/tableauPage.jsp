<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<tags:layout>
<jsp:attribute name="contents">
    <div>個人儀表板</div>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        // let ticket = '';
        $(async function () {
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
                    url: '<c:url value="/get-ticket" />',
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
              url: '<c:url value="/get-tableau-data" />',
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

                    const dashboardFigId = type;
                    const foundImage = backendResponse.find(item => item.dashboardFigId === dashboardFigId);
                    if (foundImage) {
                        foundImage.tableauNewUrl = foundImage.tableauUrl.replace("#", "trusted/" + ticket);
                        console.log(foundImage.tableauNewUrl);
                        window.open(foundImage.tableauNewUrl, "_blank");
                    } else {
                        alert('找不到對應的儀錶板網址');
                    }
                } catch (error) {
                    alert(error.message || '發生錯誤');
                }
            }
        }

        

</script>
</jsp:attribute>
</tags:layout>