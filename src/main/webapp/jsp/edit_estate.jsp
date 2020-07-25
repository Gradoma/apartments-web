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
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
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
<%--    <input type="hidden" name="apartmentId" value="${apartment.getId()}">--%>
    <fmt:message key="newEstate.region"/> : <input required name="region" value="${apartment.getRegion()}"
                                                   pattern="^[а-яА-я-.\s]{1,45}$"><br/>
    <c:choose>
        <c:when test="${regionError eq true}">
            <fmt:message key="estate.regionErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.city"/> : <input required name="city" value="${apartment.getCity()}"
                                                 pattern="^[а-яА-я-.\s]{1,45}$"><br/>
    <c:choose>
        <c:when test="${cityError eq true}">
            <fmt:message key="estate.cityErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.address"/> : <input required name="address" value="${apartment.getAddress()}"
                                                    pattern="^.{1,45}$"><br/>
    <c:choose>
        <c:when test="${addressError eq true}">
            <fmt:message key="estate.addressErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.rooms"/> : <input required type="number" name="rooms" value="${apartment.getRooms()}"
                                                  pattern="\p{Digit}{1,2}"><br/>
    <c:choose>
        <c:when test="${roomsError eq true}">
            <fmt:message key="estate.roomsErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.floor"/> : <input type="number" name="floor" pattern="\p{Digit}{1,2}"
                                                  value="${apartment.getFloor() !=0 ? apartment.getFloor() : null}" ><br/>
    <c:choose>
        <c:when test="${floorError eq true}">
            <fmt:message key="estate.floorErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.square"/> : <input type="text" name="square" pattern="^(\p{Digit}+([.,]\d)?)$"
                                                   value="${apartment.getSquare() !=0.0 ? apartment.getSquare() : null}" ><br/>
    <c:choose>
        <c:when test="${squareError eq true}">
            <fmt:message key="estate.squareErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.year"/> : <input type="text" pattern="\p{Digit}{4}" name="year"
                                                 value="${apartment.getYear()}"><br/>
    <c:choose>
        <c:when test="${yearError eq true}">
            <fmt:message key="estate.yearErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.furniture"/> :
    <input type="checkbox" name="furniture" value="true" ${apartment.hasFurniture() eq true ? 'checked' : ''}/><fmt:message key="newEstate.yes"/><br/>
    <fmt:message key="newEstate.description"/> : <input name="description" value="${apartment.getDescription()}"
                                                        pattern="^.{0,200}$"><br/>
    <c:choose>
        <c:when test="${descriptionError eq true}">
            <fmt:message key="estate.descriptionErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <input type="submit" name="button" value="<fmt:message key="setting.saveButton"/>"/>
</form>
<c:choose>
    <c:when test="${incorrectType eq true}">
        <h4><fmt:message key="photo.incorrectTypeMessage"/></h4>
    </c:when>
    <c:when test="${emptyFile eq true}">
        <h4><fmt:message key="photo.empty"/></h4>
    </c:when>
</c:choose>
<h5><fmt:message key="photo.formats"/> </h5>
<form action="fileController" method="post" enctype="multipart/form-data">
    <input type="file" name="image" height="150">
    <input type="hidden" name="page" value="EDIT">
    <input type="hidden" name="apartmentId" value="${apartment.getId()}"/>
    <input type="submit" name="button" value="<fmt:message key="setting.browseButton"/>">
</form>
<form name="delete_apartment" action="control" method="get">
    <input type="hidden" name="command" value="delete_apartment">
    <input type="hidden" name="apartmentId" value="${apartment.getId()}">
<%--    todo check remove apartment from session after delete ???--%>
    <input type="submit" name="button" value="<fmt:message key="estate.deleteButton"/>"/>
</form>
<table>
    <c:forEach var="photo" items="${apartment.getUnmodifiablePhotoList()}" varStatus="status">
        <tr>
            <td><c:out value="${ status.count }" /></td>
            <td><img src="data:image/jpg;base64,${photo}" width="250" height="100"></td>
            <td>
                <form action="control" method="get">
                    <input type="hidden" name="command" value="delete_photo"/>
                    <input type="submit" name="button" value="<fmt:message key="photo.deleteButton"/>">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<c:import url="footer.jsp"/>
</body>
</html>
