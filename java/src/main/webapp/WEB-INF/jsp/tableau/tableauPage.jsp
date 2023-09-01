<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!doctype html>
<tags:layout>
<jsp:attribute name="contents">
    <div>個人儀表板</div>
</jsp:attribute>
<jsp:attribute name="footers">
<script>
        $(function() {
            getAllTableauData();
         });

        function checkIfFromMenu() {
            const path = window.location.pathname; // 获取URL路径部分，如 "/tableau_enter.action/BID_01_02"
            const parts = path.split('/'); // 将路径按"/"分割为数组
            const paramValue = parts[parts.length - 1]; // 获取数组中的最后一个部分，即 "BID_01_02"

            if (paramValue) {
                openTableau(paramValue);
            }
        }


        let ticket;
        //獲取tableau授權碼，不用再二次登入
        function getTicket() {
         $.ajax({
                url: '<c:url value="/get-ticket" />',
                type: 'POST',
                async: true,
                timeout: 100000,
                success: function(response) {
                    if (response) {
                        ticket = response.ticket;
                        console.log("ticket",ticket);
                    }
                },
                error: function(error) {
                    console.error(error);
                }
            });
        }

        let backendResponse;
        //取得所有儀表板資訊
        function getAllTableauData() {
        $.ajax({
              url: '<c:url value="/get-tableau-data" />',
              type: 'POST',
              async: true,
              timeout: 100000,
              success: function(response) {
              console.log(response);
              if (response) {
                backendResponse = response
                checkIfFromMenu();
              }
            },
            error: function(error) {
              console.error(error);
            }
            });
        }


        function openTableau(type) {
              console.log("type",type)
              // 顯示確認視窗
              const isConfirmed = confirm('將開啟Tableau視窗，確認繼續嗎？');
              if (isConfirmed) {
              //每新開一個url都要再拿一次ticket，不然ticket會失效
              getTicket();
              const dashboardFigId = type;
              const foundImage = backendResponse.find(item => item.dashboardFigId === dashboardFigId);
              if (foundImage) {
                  foundImage.tableauNewUrl = foundImage.tableauUrl.replace("#","trusted/"+ticket)
                  console.log(foundImage.tableauNewUrl)
                  window.open(foundImage.tableauNewUrl, "_blank");
              } else {
                alert('找不到對應的儀錶板網址');
              }
             }
        }
        

</script>
</jsp:attribute>
</tags:layout>