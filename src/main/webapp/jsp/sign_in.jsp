<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 15.06.2020
  Time: 6:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ru" scope="session" />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title><fmt:message key="label.title"/></title>
</head>
<body>
    <h2><fmt:message key="label.body"/></h2>
<form name="Simple" action="control" method="get">
    <input type="hidden" name="command" value="sign_in"/>
    <input required name="login" placeholder=<fmt:message key="label.login"/>><br/>
    <input required name="password" placeholder=<fmt:message key="label.password"/>>
        <br/> ${errorSignInPass} <br/>
    <input type="submit" name="button" value=<fmt:message key="label.submitButton"/>>
</form>
<form action="control" method="get">
    <input type="hidden" name="command" value="transition_to_sign_up"/>
    <input type="submit" name="button" value=<fmt:message key="label.transitionButton"/>>
</form>
</body>
</html>