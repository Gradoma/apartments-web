<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 27.07.2020
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<c:import url="admin_header.jsp"/>
<table>
    <tr>
        <td>#</td>
        <td>
            LoginName</a>
        </td>
        <td>
            First Name/Last Name
        </td>
        <td>Registration Date</td>
        <td>
            isVisible
        </td>
    </tr>
    <hr/>
    <c:forEach var="user" items="${userList}" varStatus="status">
        <tr>
            <td><c:out value="${ status.count }" /></td>
            <td>
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_user&login=${user.getLoginName()}>${user.getLoginName()}</a>
            </td>
            <td>
                ${user.getFirstName()} ${user.getLastName()}
            </td>
            <td><c:out value="${user.getRegistrationDate() }" /></td>
            <td>
                    ${user.isVisible()}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
