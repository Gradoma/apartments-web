<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 15.07.2020
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>Advertisement</title>
</head>
<body>
<c:import url="header.jsp"/>
    <h2>${advertisement.getTitle()}</h2>
    <h3>${apartment.getRegion()}, ${apartment.getCity()}</h3>
    <h3>${apartment.getAddress()}</h3>
    <table>
        <tr>
            <td>
                <b>${advertisement.getPrice()} <fmt:message key="advertisement.currency"/> </b>
            </td>
            <td>
                <fmt:message key="newEstate.rooms"/> : ${apartment.getRooms()}
            </td>
            <td>
                <fmt:message key="advertisement.owner"/> <br/>
                <c:set var="owner" value="${apartment.getOwner()}"/>
                <fmt:message key="advertisement.name"/> ${owner.getFirstName()} ${owner.getLastName()}<br/>
                <fmt:message key="setting.phone"/> : ${owner.getPhone()}<br/>
                <fmt:message key="setting.birthday"/> : ${owner.getBirthday()}<br/>
                <form action="control" method="get">
                    <input type="hidden" name="command" value="transition_to_request"/>
                    <input type="submit" name="button" value="<fmt:message key="advertisement.wantToRentButton"/>">
                </form>
            </td>
        </tr>
    </table>
    <h4>${apartment.getDescription()}</h4>
    <fmt:message key="advertisement.apartment"/> <br/>
        <fmt:message key="newEstate.floor"/> : ${apartment.getFloor() !=0 ? apartment.getFloor() : null} <br/>
        <fmt:message key="newEstate.square"/> : ${apartment.getSquare() !=0.0 ? apartment.getSquare() : null} <br/>
        <fmt:message key="newEstate.year"/> : ${apartment.getYear()} <br/>
        <fmt:message key="newEstate.furniture"/> :
            <c:choose>
                    <c:when test="${apartment.getYear() == 'true'}">
                        <fmt:message key="newEstate.yes"/> <br/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="estate.no"/> <br/>
                    </c:otherwise>
            </c:choose>
<c:import url="footer.jsp"/>
</body>
</html>
