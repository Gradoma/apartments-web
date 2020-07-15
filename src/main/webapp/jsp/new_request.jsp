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
    <fmt:message key="newRequest.date"/> : <input type="date" required name="expectedDate"> ${errorDate} <br/>
    <fmt:message key="newRequest.description"/> : <input name="description" ><br/>
    <i><fmt:message key="newRequest.note"/> </i>
    <input type="submit" name="button" value="<fmt:message key="newRequest.applyButton"/>"/>
</form>
<c:import url="footer.jsp"/>
</body>
</html>
