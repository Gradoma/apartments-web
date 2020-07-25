<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>New Estate</title>
</head>
<body>
<c:import url="header.jsp"/>
<form name="Apartment" action="control" method="get">
    <input type="hidden" name="command" value="add_new_apartment">
    <fmt:message key="newEstate.region"/> : <input required name="region" pattern="^[а-яА-я-.\s]{1,45}$"><br/>
    <c:choose>
        <c:when test="${regionError eq true}">
            <fmt:message key="estate.regionErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.city"/> : <input required name="city" pattern="^[а-яА-я-.\s]{1,45}$"><br/>
    <c:choose>
        <c:when test="${cityError eq true}">
            <fmt:message key="estate.cityErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.address"/> : <input required name="address" pattern="^.{1,45}$"><br/>
    <c:choose>
        <c:when test="${addressError eq true}">
            <fmt:message key="estate.addressErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.rooms"/> : <input required type="number" name="rooms" pattern="\p{Digit}{1,2}"><br/>
    <c:choose>
        <c:when test="${roomsError eq true}">
            <fmt:message key="estate.roomsErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.floor"/> : <input type="number" name="floor" pattern="\p{Digit}{1,2}"><br/>
    <c:choose>
        <c:when test="${floorError eq true}">
            <fmt:message key="estate.floorErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.square"/> : <input type="text" pattern="^(\p{Digit}+([.,]\d)?)$" name="square"><br/>
    <c:choose>
        <c:when test="${squareError eq true}">
            <fmt:message key="estate.squareErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.year"/> : <input type="text" pattern="\p{Digit}{4}" name="year"><br/>
    <c:choose>
        <c:when test="${yearError eq true}">
            <fmt:message key="estate.yearErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newEstate.furniture"/> :
        <input type="checkbox" name="furniture" value="true" /><fmt:message key="newEstate.yes"/><br/>
    <fmt:message key="newEstate.description"/> : <input name="description" pattern="^.{0,200}$"><br/>
    <c:choose>
        <c:when test="${descriptionError eq true}">
            <fmt:message key="estate.descriptionErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <input type="submit" name="button" value="<fmt:message key="setting.saveButton"/>"/>
</form>
<c:import url="footer.jsp"/>
</body>
</html>
