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
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>User Page</title>
</head>
<body>
<c:import url="header.jsp"/>
<img src="fileController" width="100" height="100">
<h3>${user.getFirstName()} ${user.getLastName()}</h3><br/>
<form name="Simple" action="control" method="get">
    <input type="hidden" name="command" value="transition_to_settings"/>
    <input type="hidden" name="login" value="${user.getLoginName()}"/>
    <input type="submit" name="button" value=<fmt:message key="main.settingsButton"/>>
</form>
<form action="control" method="get">
    <input type="hidden" name="command" value="transition_to_estate"/>
    <input type="submit" name="button" value="<fmt:message key="main.estateButton"/>">
</form>
<form action="control" method="get">
    <input type="hidden" name="command" value="transition_to_my_requests"/>
    <input type="submit" name="button" value="<fmt:message key="main.myRequestsButton"/>">
</form>
<form action="control" method="get">
    <input type="hidden" name="command" value="log_out"/>
    <input type="submit" name="button" value="<fmt:message key="main.logoutButton"/>">
</form>
<table>
    <c:forEach var="ad" items="${advertisementList}" varStatus="status">
        <tr>
            <td><img src="fileController" width="80" height="80"></td>
            <td><a href=http://localhost:8080/apartments_web_war/control?command=transition_to_advertisement&id=${ad.getId()}>${ad.getTitle()}</a></td>
                <%--                <td><c:out value="${ad.getTitle() }" /></td>--%>
            <td><c:out value="${ad.getPrice() }" /></td>
        </tr>
        <tr>
                <%--                <td><ctg:dateTime dateTimeValue="${ad.getCreationDate()}"/></td>    //TODO(format tag)--%>
            <td><c:out value="${ad.getCreationDate() }" /></td>
        </tr>
    </c:forEach>
</table>
<c:import url="footer.jsp"/>
</body>
</html>
