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
    <title>New Demand</title>
</head>
<body>
<c:import url="header.jsp"/>
<form name="Demand" action="${pageContext.request.contextPath}/control" method="get">
    <input type="hidden" name="command" value="new_demand">
    <input type="hidden" name="apartmentId" value="${apartmentId}">
    <h3><fmt:message key="newDemand.head"/><br/></h3>
    <small><fmt:message key="message.required"/> </small><br/>
    <fmt:message key="newDemand.date"/>* : <input type="date" required name="expectedDate">
    <c:choose>
        <c:when test="${dateError eq true}">
            <fmt:message key="newDemand.dateErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <br/><fmt:message key="newDemand.description"/> : <input name="description" pattern="^.{1,150}$">
    <c:choose>
        <c:when test="${descriptionError eq true}">
            <fmt:message key="newDemand.descriptionErrorMessage"/><br/>
        </c:when>
    </c:choose>
    <br/><i><fmt:message key="newDemand.note"/> </i>
    <input type="submit" name="button" value="<fmt:message key="newDemand.applyButton"/>"/>
    <c:choose>
        <c:when test="${error eq true}">
            <fmt:message key="newDemand.errorMessage"/><br/>
        </c:when>
    </c:choose>
</form>
<c:import url="footer.jsp"/>
</body>
</html>
