<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Greenhouse Monitor</title>
    <script type="text/javascript">
        function verify() {
            document.getElementById("loginForm").submit();
        }
    </script>
</head>

<body>
<div id="content">
    <form method="post" id="loginForm" action="${pageContext.request.contextPath}/temperature">
        <div id="login">
            <label for="username"></label><input type="text" id="username" name="username" placeholder="username"/>
            <label for="password"></label><input type="password" id="password" name="password" placeholder="password"/>
            <button type="button" onclick="verify()" style="margin-right: 85px;">Login</button>
        </div>
    </form>
</div>
</body>
</html>
