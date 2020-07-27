<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 27.07.2020
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <c:import url="admin_header.jsp"/>
    Creation date : ${demand.getCreationDate()}<br/>
    Description : ${demand.getDescription()}<br/>
    Expected date : ${demand.getExpectedDate()}<br/>
    Status : ${demand.getStatus()}<br/>

    <b>Apartment:</b><hr/>
    <table>
        <tr>
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
        <tr>
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
    </table>

    <b>Applicant:</b><hr/>
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
        <tr>
            <td><c:out value="${ status.count }" /></td>
            <td>
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_user_profile&id=${currentUser.getId()}>${currentUser.getLoginName()}</a>
            </td>
            <td>
                    ${currentUser.getFirstName()} ${currentUser.getLastName()}
            </td>
            <td><c:out value="${currentUser.getRegistrationDate() }" /></td>
            <td>
                    ${currentUser.isVisible()}
            </td>
        </tr>
    </table>
</body>
</html>
