<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 15.07.2020
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>New Request</title>
</head>
<body>
<c:import url="header.jsp"/>
<form name="Request" action="control" method="get">
    <input type="hidden" name="command" value="new_request">
    <input type="hidden" name="apartmentId" value="${apartmentId}">
    <h3><fmt:message key="newRequest.head"/><br/></h3>
    <fmt:message key="newRequest.date"/> : <input type="date" required name="expectedDate"><br/>
    <c:choose>
        <c:when test="${dateError eq true}">
            <fmt:message key="newRequest.dateErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newRequest.description"/> : <input name="description" pattern="^.{1,150}$"><br/>
    <c:choose>
        <c:when test="${descriptionError eq true}">
            <fmt:message key="newRequest.descriptionErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <i><fmt:message key="newRequest.note"/> </i>
    <input type="submit" name="button" value="<fmt:message key="newRequest.applyButton"/>"/>
    <c:choose>
        <c:when test="${error eq true}">
            <fmt:message key="newRequest.errorMessage"/><br/>
        </c:when>
    </c:choose>
</form>
<c:import url="footer.jsp"/>
</body>
</html>
