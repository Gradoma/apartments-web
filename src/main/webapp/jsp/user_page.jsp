<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 15.06.2020
  Time: 5:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="ru"/>
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>User Page</title>
</head>
<body>
<c:import url="header.jsp"/>
<h3>${user.getFirstName()} ${user.getLastName()}</h3><br/>
<form name="Simple" action="control" method="get">
    <input type="hidden" name="command" value="transition_to_settings"/>
    <input type="hidden" name="login" value="${user.getLoginName()}"/>
    <input type="submit" name="button" value=<fmt:message key="main.settingsButton"/>>
</form>
<c:import url="footer.jsp"/>
</body>
</html>
