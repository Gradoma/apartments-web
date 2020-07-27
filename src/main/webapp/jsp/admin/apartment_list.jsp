<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 27.07.2020
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<c:import url="admin_header.jsp"/>
<table>
    <tr>
        <td>#</td>
        <td>
            Registration Date
        </td>
        <td>
            id
        </td>
        <td>
           Region/City()/Address
        </td>
        <td>Rooms</td>
        <td>Floor</td>
        <td>Status</td>
        <td>Visible</td>
    </tr>
    <hr/>
    <c:forEach var="apartment" items="${apartmentList}" varStatus="status">
        <tr>
            <td><c:out value="${ status.count }" /></td>
            <td>
                <c:out value="${apartment.getRegistrationDate()}" />
            </td>
            <td>
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_apartment&id=${apartment.getId()}>${apartment.getId()}</a>
            </td>
            <td>
                ${apartment.getRegion()}, ${apartment.getCity()}, ${apartment.getAddress()}
            </td>
            <td>${apartment.getRooms()}</td>
            <td>${apartment.getFloor()}</td>
            <td>${apartment.getStatus()}</td>
            <td>${apartment.isVisible()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
