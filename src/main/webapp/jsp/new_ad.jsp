<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 12.07.2020
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>New Ad</title>
</head>
<body>
<c:import url="header.jsp"/>
${newAdErrorMessage}
<form name="Ad" action="control" method="get">
    <input type="hidden" name="command" value="new_ad">
    <input type="hidden" name="apartmentId" value="${apartmentId}">
    <fmt:message key="newAd.title"/> : <input required name="title" ><br/>
    <fmt:message key="newAd.price"/> : <input type="number" required name="price" ><br/>
    <input type="submit" name="button" value="<fmt:message key="newAd.postButton"/>"/>
</form>
<c:import url="footer.jsp"/>
</body>
</html>
