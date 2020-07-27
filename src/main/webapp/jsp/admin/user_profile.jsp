<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 27.07.2020
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <c:import url="admin_header.jsp"/>
    <img src="data:image/jpg;base64,${currentUser.getPhotoBase64()}" width="100" height="100"><br/>
    Login : ${currentUser.getLoginName()}<br/>
    Registration date : ${currentUser.getRegistrationDate()}<br/>
    First name : ${currentUser.getFirstName()}<br/>
    Last name : ${currentUser.getLastName()}<br/>
    Birthday : ${currentUser.getBirthday()}<br/>
    Gender : ${currentUser.getGender()}<br/>
    Phone : ${currentUser.getPhone()}<br/>
    Mail : ${currentUser.getMail()}<br/>
    Visibility : ${currentUser.isVisible()}<br/>

    <b>User's apartments:</b><hr/>
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

    <b>User's demands:</b><hr/>
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
        <c:forEach var="demand" items="${demandList}" varStatus="status">
            <tr>
                <td><c:out value="${ status.count }" /></td>
                <td>
                        ${demand.getCreationDate()}
                </td>
                <td>
                    <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_demand_profile&id=${demand.getId()}>${demand.getDescription()}</a>
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
