<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 24.06.2020
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="ru" scope="session" />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>Settings</title>
</head>
<body>
<c:import url="header.jsp"/>
    <h2>${greeting}</h2>
    <form action="fileController" method="post" enctype="multipart/form-data">
        <img src="fileController" width="100" height="100">
        <input type="file" name="image" height="150">
        <input type="hidden" name="login" value="${user.getLoginName()}"/>
        <input type="submit" name="button" value="<fmt:message key="setting.browseButton"/>">
    </form>
    <form name="Simple" action="control" method="post">
        <input type="hidden" name="command" value="update_user"/>
        <input type="hidden" name="login" value="${user.getLoginName()}"/>
        <fmt:message key="label.password"/> : <input required name="password" value="${user.getPassword()}"><br/>
        <fmt:message key="setting.firstName"/> : <input required name="firstName" value="${user.getFirstName()}"><br/>
        <fmt:message key="setting.lastName"/> : <input required name="lastName" value="${user.getLastName()}"><br/>
        <fmt:message key="setting.gender"/> : <input type="radio" name="gender" value="FEMALE" checked/><fmt:message key="setting.female"/>
        <input type="radio" name="gender" value="MALE" /><fmt:message key="setting.male"/><br/>
        <fmt:message key="setting.phone"/> : <input name="phone" value="${user.getPhone()}"><br/>
        <fmt:message key="setting.birthday"/> : <input type="date" name="birthday" >
        <br/> ${errorBirthday} <br/>
        <input type="submit" name="button" value=<fmt:message key="setting.saveButton"/>>
    </form>
<c:import url="footer.jsp"/>
</body>
</html>
