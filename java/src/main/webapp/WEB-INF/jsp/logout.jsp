<%@ page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>登出成功</title>
    <script>
        function closeWindow() {
            window.opener = null;
            window.close();
        }
    </script>
</head>
<body scroll="no">

    <span style="color:#FF0000;">
        您已登出本系統，請關閉瀏覽器視窗，謝謝。
    </span>

</body>
</html>
