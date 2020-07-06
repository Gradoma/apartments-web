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
