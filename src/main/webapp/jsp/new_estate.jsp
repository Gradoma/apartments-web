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
    <fmt:message key="newEstate.region"/> : <input required name="region" >
    ${regionErrorMessage}<br/>
    <fmt:message key="newEstate.city"/> : <input required name="city" >
    ${cityErrorMessage}<br/>
    <fmt:message key="newEstate.address"/> : <input required name="address" >
    ${addressErrorMessage}<br/>
    <fmt:message key="newEstate.rooms"/> : <input required type="number" name="rooms">
    ${roomsErrorMessage}<br/>
    <fmt:message key="newEstate.floor"/> : <input type="number" name="floor"><br/>
    <fmt:message key="newEstate.square"/> : <input type="text" pattern="\d+(\.\d{1})?" name="square"><br/>
    <fmt:message key="newEstate.year"/> : <input type="text" pattern="\d{4}" name="year"><br/>
    <fmt:message key="newEstate.furniture"/> :
        <input type="checkbox" name="furniture" value="true" /><fmt:message key="newEstate.yes"/><br/>
    <fmt:message key="newEstate.description"/> : <input name="description" ><br/>
    <input type="submit" name="button" value="<fmt:message key="setting.saveButton"/>"/>
</form>
<c:import url="footer.jsp"/>
</body>
</html>
