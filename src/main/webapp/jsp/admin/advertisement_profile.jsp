<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 27.07.2020
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <c:import url="admin_header.jsp"/>
    Creation date : ${advertisement.getCreationDate()}<br/>
    Title : ${advertisement.getTitle()}<br/>
    Price : ${advertisement.getPrice()}<br/>
    Visibility : ${advertisement.isVisible()}<br/>

    <form action="${pageContext.demand.contextPath}/control" method="get">
        <input type="hidden" name="command" value="admin_ban_advertisement"/>
        <input type="hidden" name="id" value="${advertisement.getId()}">
        <input type="submit" name="button" value="Ban Advertisement">
    </form>

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
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_apartment_profile&id=${apartment.getId()}>${apartment.getId()}</a>
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

    <b>Author:</b><hr/>
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
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_user_profile&id=${author.getId()}>${author.getLoginName()}</a>
            </td>
            <td>
                ${author.getFirstName()} ${author.getLastName()}
            </td>
            <td><c:out value="${author.getRegistrationDate() }" /></td>
            <td>
                ${author.isVisible()}
            </td>
        </tr>
    </table>

    <b>Demands on this advertisement:</b><hr/>
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
