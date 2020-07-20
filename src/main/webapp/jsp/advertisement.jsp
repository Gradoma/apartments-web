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
                <c:choose>
                    <c:when test="${user.getId() == owner.getId()}">
                        <b><fmt:message key="advertisement.authorMessage"/></b><br/>
                        <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_estate><fmt:message key="advertisement.linkToEstate"/> </a>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="advertisement.name"/> ${owner.getFirstName()} ${owner.getLastName()}<br/>
                        <fmt:message key="setting.phone"/> : ${owner.getPhone()}<br/>
                        <fmt:message key="setting.birthday"/> : ${owner.getBirthday()}<br/>
                        <c:choose>
                            <c:when test="${wasCreated == 'true'}">
                                <b><fmt:message key="advertisement.wasCreatedMessage"/></b>
                                <form action="control" method="get">
                                    <input type="hidden" name="command" value="transition_to_my_requests"/>
                                    <input type="submit" name="button" value="<fmt:message key="main.myRequestsButton"/>">
                                </form>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${advertisement.isVisible() eq true}">
                                        <form action="control" method="get">
                                            <input type="hidden" name="command" value="transition_to_new_request"/>
                                            <input type="hidden" name="advertisementId" value="${advertisement.getId()}">
                                            <input type="hidden" name="apartmentId" value="${apartment.getId()}"/>
                                            <input type="submit" name="button" value="<fmt:message key="advertisement.wantToRentButton"/>">
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <b><fmt:message key="advertisement.archivedMessage"/></b><br/>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
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
