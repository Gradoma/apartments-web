<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 15.06.2020
  Time: 6:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>
    <h2>Sign In, please!</h2>
</body>
<form name="Simple" action="control" method="get">
    <input type="hidden" name="command" value="sign_in">
    <input required name="login" placeholder="login"/>
    <input required name="password" placeholder="password"/>
    <input type="submit" name="button" value="Sign In"/>
</form>
<form action="control" method="get">
    <input type="hidden" name="command" value="transition_to_sign_up">
    <input type="submit" name="button" value="Sign Up"/>
</form>
</html>
