<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 17.06.2020
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<h2>Sign Up, please!</h2>
</body>
<form name="Simple" action="control" method="get">
    <input type="hidden" name="command" value="sign_up">
    <input required name="login" placeholder="login"/>
    <input required name="password" placeholder="password"/>
    <input required name="email" placeholder="email"/>
    <input type="submit" name="button" value="Sign Up"/>
</form>
</html>
