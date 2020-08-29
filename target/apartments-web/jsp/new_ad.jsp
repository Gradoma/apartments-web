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
<form name="Ad" action="${pageContext.request.contextPath}/control" method="get">
    <small><fmt:message key="message.required"/> </small><br/>
    <input type="hidden" name="command" value="new_ad">
    <input type="hidden" name="apartmentId" value="${apartmentId}">
    <fmt:message key="newAdvertisement.title"/>* : <input required name="title" pattern="^.{1,70}$"><br/>
    <c:choose>
        <c:when test="${titleError eq true}">
            <fmt:message key="newAdvertisement.titleErrorMesage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newAdvertisement.price"/>* : <input type="text" required name="price"
                                              pattern="^((\p{Digit}){1,5}([.,]\d{1,2})?)$"><br/>
    <c:choose>
        <c:when test="${priceError eq true}">
            <fmt:message key="newAdvertisement.priceErrorMesage"/><br/>
        </c:when>
    </c:choose>
    <input type="submit" name="button" value="<fmt:message key="newAdvertisement.postButton"/>"/>
</form>
<c:import url="footer.jsp"/>
</body>
</html>
