<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 24.06.2020
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="ru" scope="session" />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>Settings</title>
</head>
<body>
    <h2>${greeting}</h2>
    <form name="Simple" action="control" method="get">
        <input type="hidden" name="command" value="update_user"/>
        <fmt:message key="label.login"/> : <p>${user.getLoginName()}</p><br/>
        <input type="hidden" name="login" value="${user.getLoginName()}"/>
        <fmt:message key="label.password"/> : <input required name="password" value="${user.getPassword()}"><br/>
        <fmt:message key="setting.firstName"/> : <input required name="firstName" value="${user.getFirstName()}"><br/>
        <fmt:message key="setting.lastName"/> : <input required name="lastName" value="${user.getLastName()}"><br/>
        <fmt:message key="setting.gender"/> : <input type="radio" name="gender" value="FEMALE" checked /><fmt:message key="setting.female"/>
        <input type="radio" name="gender" value="MALE" /><fmt:message key="setting.male"/><br/>
        <fmt:message key="setting.phone"/> : <input name="phone" value="${user.getPhone()}"><br/>
        <fmt:message key="setting.birthday"/> : <input name="birthday" value="${user.getBirthday()}"><br/>
        <input type="submit" name="button" value=<fmt:message key="setting.saveButton"/>>
    </form>
</body>
</html>