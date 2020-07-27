<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 27.07.2020
  Time: 19:24
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
            Title
        </td>
        <td>
            Price
        </td>
        <td>Visible</td>
    </tr>
    <hr/>
    <c:forEach var="advertisement" items="${advertisementList}" varStatus="status">
        <tr>
            <td><c:out value="${ status.count }" /></td>
            <td>
                    ${advertisement.getCreationDate()}
            </td>
            <td>
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_advertisement_profile&id=${advertisement.getId()}>${advertisement.getTitle()}</a>
            </td>
            <td>
                    ${advertisement.getPrice()}
            </td>
            <td>${advertisement.isVisible()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
