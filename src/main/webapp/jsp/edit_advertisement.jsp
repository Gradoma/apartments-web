<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 19.07.2020
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>Edit advertisement</title>
</head>
<body>
<c:import url="header.jsp"/>
<form action="control" method="get">
    <input type="hidden" name="command" value="edit_advertisement">
    <fmt:message key="newAd.title"/> : <input required name="title" ><br/>
    <fmt:message key="newAd.price"/> : <input type="number" required name="price" ><br/>
    <input type="submit" name="button" value="<fmt:message key="advertisement.saveButton"/>"/>
</form>
<form action="control" method="get">
    <input type="hidden" name="command" value="delete_advertisement">
    <input type="submit" name="button" value="<fmt:message key="advertisement.deleteButton"/>"/>
</form>
<b><fmt:message key="advertisement.apartment"/></b><br/>
<fmt:message key="newEstate.region"/> : ${region}<br/>
<fmt:message key="newEstate.city"/> : ${city}"<br/>
<fmt:message key="newEstate.address"/> : ${address}<br/>
<fmt:message key="newEstate.rooms"/> : ${rooms}<br/>
<fmt:message key="newEstate.floor"/> : ${floor!=0 ? floor : null}<br/>
<fmt:message key="newEstate.square"/> : ${square!=0.0 ? square : null}<br/>
<fmt:message key="newEstate.year"/> : ${year}<br/>
<fmt:message key="newEstate.furniture"/> :
<c:choose>
    <c:when test="${apartmentFurniture eq true}">
        <fmt:message key="newEstate.yes"/><br/>
    </c:when>
    <c:otherwise>
        <fmt:message key="estate.no"/><br/>
    </c:otherwise>
</c:choose>
<fmt:message key="newEstate.description"/> : ${description}<br/>
<c:import url="footer.jsp"/>
</body>
</html>
