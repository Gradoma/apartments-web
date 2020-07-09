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
            </td>
            <td><h4>${errorDeleteMessage}</h4></td>
        </tr>
    </c:forEach>
</table>
<form action="control" method="get">
    <input type="hidden" name="command" value="transition_to_new_estate"/>
    <input type="submit" name="button" value="<fmt:message key="estate.addNewButton"/>">
</form>
<c:import url="footer.jsp"/>
</body>
</html>
