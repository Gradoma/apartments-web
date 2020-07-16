<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 03.07.2020
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="registered" value="REGISTERED"/>
<c:set var="demand" value="IN_DEMAND"/>
<c:set var="rent" value="RENT"/>
<c:set var="deleted" value="DELETED"/>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>Estate</title>
</head>
<body>
<c:import url="header.jsp"/>
    <h2>${noAppartmentsMessage}</h2>
<table>
    <c:forEach var="elem" items="${apartmentList}" varStatus="status">
        <tr>
            <td><c:out value="${ status.count }" /></td>
            <td><img src="fileController" width="50" height="50"></td>
            <td><c:out value="${ elem.getRegion() }" /></td>
            <td><c:out value="${ elem.getCity() }" /></td>
            <td><c:out value="${ elem.getAddress() }" /></td>
            <td>
                <c:choose>
                    <c:when test="${elem.getStatus() == registered}">
                        <form action="control" method="get">
                            <input type="hidden" name="command" value="transition_to_estate_edit"/>
                            <input type="hidden" name="apartmentId" value="${elem.getId()}">
                            <input type="hidden" name="region" value="${elem.getRegion()}">
                            <input type="hidden" name="city" value="${elem.getCity()}">
                            <input type="hidden" name="address" value="${elem.getAddress()}">
                            <input type="hidden" name="city" value="${elem.getCity()}">
                            <input type="hidden" name="rooms" value="${elem.getRooms()}">
                            <input type="hidden" name="floor" value="${elem.getFloor()}">
                            <input type="hidden" name="square" value="${elem.getSquare()}">
                            <input type="hidden" name="year" value="${elem.getYear()}">
                            <input type="hidden" name="furniture" value="${elem.hasFurniture()}">
                            <input type="hidden" name="description" value="${elem.getDescription()}">
                            <input type="submit" name="button" value="<fmt:message key="estate.editButton"/>">
                        </form>
                        <form action="control" method="get">
                            <input type="hidden" name="command" value="transition_to_new_ad"/>
                            <input type="hidden" name="apartmentId" value="${elem.getId()}">
                            <input type="submit" name="button" value="<fmt:message key="estate.newAdButton"/>">
                        </form>
                    </c:when>
                    <c:when test="${elem.getStatus() == demand}">
                        <h5><fmt:message key="estate.demandMessage"/></h5>
                        <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_request_list&apartmentId=${elem.getId()}><fmt:message key="estate.requests"/> </a>
                    </c:when>
                    <c:when test="${elem.getStatus() == rent}">
                        <h5><fmt:message key="estate.rentedMessage"/></h5>
                    </c:when>
                </c:choose>
            </td>
            <td><h4>${errorDeleteMessage}</h4></td>
        </tr>
    </c:forEach>
</table>
<form action="control" method="get">
    <input type="hidden" name="command" value="transition_to_new_estate"/>
    <input type="submit" name="button" value="<fmt:message key="estate.newApartmentButton"/>">
</form>
<c:import url="footer.jsp"/>
</body>
</html>
