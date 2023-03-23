<%--
  Created by IntelliJ IDEA.
  User: shaowen
  Date: 2023-03-19
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello World</title>
    <script type="text/javascript">
        function verify() {
            document.getElementById("loginForm").submit();
        }

        function verify2() {
            document.getElementById("loginForm2").submit();
        }
    </script>
</head>

<body>
<div id="content">
    <form method="post" id="loginForm" action="${pageContext.request.contextPath}/search">
        <div id="login">
            <label for="username"></label><input type="text" id="username" name="username" placeholder="username"/>
            <label for="password"></label><input type="password" id="password" name="password" placeholder="password"/>
            <button type="button" onclick="verify()" style="margin-right: 85px;">Login</button>
        </div>
    </form>

    <form method="post" id="loginForm2" action="${pageContext.request.contextPath}/blue/">
        <div id="login2">
            <label for="username"></label><input type="text" id="username2" name="username" placeholder="username"/>
            <label for="password"></label><input type="password" id="password2" name="password" placeholder="password"/>
            <button type="button" onclick="verify2()" style="margin-right: 85px;">Login2</button>
        </div>
    </form>
</div>
</body>
</html>
