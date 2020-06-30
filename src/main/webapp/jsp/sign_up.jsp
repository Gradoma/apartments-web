<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 17.06.2020
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="ru" scope="session" />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<h2>Sign Up, please!</h2>
</body>
<c:import url="header.jsp"/>
<form name="Simple" action="control" method="post">
    <input type="hidden" name="command" value="sign_up">
    <input required name="login" placeholder=<fmt:message key="label.login"/>/>
        <br/> ${loginErrorMessage} <br/>
    <input required name="password" placeholder=<fmt:message key="label.password"/>/>
        <br/> ${passErrorMessage} <br/>
    <input required name="email" placeholder=<fmt:message key="label.mail"/>/>
        <br/> ${emailErrorMessage} <br/>
    <input type="submit" name="button" value="<fmt:message key="label.registerButton"/>"/>
</form>
<c:import url="footer.jsp"/>
</html>
