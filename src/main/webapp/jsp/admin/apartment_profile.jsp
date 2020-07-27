<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 27.07.2020
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <c:import url="admin_header.jsp"/>
    Registration date : ${apartment.getRegistrationDate()}<br/>
    Region : ${apartment.getRegion()}<br/>
    City : ${apartment.getCity()}<br/>
    Address : ${apartment.getAddress()}<br/>
    Rooms : ${apartment.getRooms()}<br/>
    Floor : ${apartment.getFloor()}<br/>
    Square : ${apartment.getSquare()}<br/>
    Year : ${apartment.getYear()}<br/>
    Furniture : ${apartment.hasFurniture()}<br/>
    Description : ${apartment.getDescription()}<br/>
    Visibility : ${apartment.isVisible()}<br/>

    <table>
        <c:forEach var="entryMap" items="${photoMap}" varStatus="status">
            <c:set var="photoId" value="${entryMap.key}"/>
            <c:set var="photo" value="${entryMap.value}"/>
            <tr>
                <td><c:out value="${ status.count }" /></td>
                <td><img src="data:image/jpg;base64,${photo}" width="450" height="220"></td>
            </tr>
        </c:forEach>
    </table>

    <b>Owner:</b><hr/>
    <c:set var="owner" value="${apartment.getOwner()}"/>
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
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_user_profile&id=${owner.getId()}>${owner.getLoginName()}</a>
            </td>
            <td>
                ${owner.getFirstName()} ${owner.getLastName()}
            </td>
            <td><c:out value="${owner.getRegistrationDate() }" /></td>
            <td>
                ${owner.isVisible()}
            </td>
        </tr>
    </table>

    <b>Advertisement:</b><hr/>
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
    </table>
</body>
</html>
