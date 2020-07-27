<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 27.07.2020
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<c:import url="admin_header.jsp"/>
<table>
    <tr>
        <td>#</td>
        <td>
            Creation Date
        </td>
        <td>
            Description
        </td>
        <td>
            Expected Date
        </td>
        <td>Status</td>
    </tr>
    <hr/>
    <c:forEach var="demand" items="${demandList}" varStatus="status">
        <tr>
            <td><c:out value="${ status.count }" /></td>
            <td>
                ${demand.getCreationDate()}
            </td>
            <td>
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_demand&id=${demand.getId()}>${demand.getDescription()}</a>
            </td>
            <td>
                ${demand.getExpectedDate()}
            </td>
            <td>${demand.getStatus()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
