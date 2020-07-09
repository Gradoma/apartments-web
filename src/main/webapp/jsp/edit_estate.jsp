<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 09.07.2020
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ru" scope="session" />
<fmt:setBundle basename="prop.pagecontent" />
<c:set var="apartmentFurniture" value="${furniture}"/>
<html>
<head>
    <title>Edit estate</title>
</head>
<body>
<c:import url="header.jsp"/>
<form name="update_apartment" action="control" method="get">
    <input type="hidden" name="command" value="edit_apartment">
    <input type="hidden" name="apartmentId" value="${apartmentId}">
    <fmt:message key="newEstate.region"/> : <input required name="region" value="${region}">
    ${regionErrorMessage}<br/>
    <fmt:message key="newEstate.city"/> : <input required name="city" value="${city}">
    ${cityErrorMessage}<br/>
    <fmt:message key="newEstate.address"/> : <input required name="address" value="${address}">
    ${addressErrorMessage}<br/>
    <fmt:message key="newEstate.rooms"/> : <input required type="number" name="rooms" value="${rooms}">
    ${roomsErrorMessage}<br/>
    <fmt:message key="newEstate.floor"/> : <input type="number" name="floor" value="${floor}">
    ${floorErrorMessage}<br/>
    <fmt:message key="newEstate.square"/> : <input type="text" pattern="\d+(\.\d{1})?" name="square" value="${square}"><br/>
    <fmt:message key="newEstate.year"/> : <input type="text" pattern="\d{4}" name="year" value="${year}"><br/>
    <fmt:message key="newEstate.furniture"/> :
    <input type="checkbox" name="furniture" value="true" ${apartmentFurniture eq true ? 'checked' : ''}/><fmt:message key="newEstate.yes"/><br/>
    <fmt:message key="newEstate.description"/> : <input name="description" value="${description}"><br/>
    <input type="submit" name="button" value="<fmt:message key="setting.saveButton"/>"/>
</form>
<form name="delete_apartment" action="control" method="get">
    <input type="hidden" name="command" value="delete_apartment">
    <input type="hidden" name="apartmentId" value="${apartmentId}">
    <input type="submit" name="button" value="<fmt:message key="estate.deleteButton"/>"/>
</form>
<c:import url="footer.jsp"/>
</body>
</html>
