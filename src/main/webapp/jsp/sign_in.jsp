<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 15.06.2020
  Time: 6:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="ctg" uri="/WEB-INF/custom/custom.tld" %>--%>
<c:set var="en" value="en"/>
<c:set var="language" value="${not empty pageContext.session.getAttribute('locale') ? pageContext.session.getAttribute('locale') : en}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title><fmt:message key="label.title"/></title>
</head>
<body>
    <c:import url="header.jsp"/>
    <h3><fmt:message key="label.body"/></h3>
<form name="Simple" action="control" method="post">
    <input type="hidden" name="command" value="sign_in"/>
    <input required name="login" placeholder=<fmt:message key="label.login"/>><br/>
    <input required name="password" placeholder=<fmt:message key="label.password"/>>
        <br/> ${errorSignInPass} <br/>
    <input type="submit" name="button" value="<fmt:message key="label.submitButton"/>">
</form>
<form action="control" method="get">
    <input type="hidden" name="command" value="transition_to_sign_up"/>
    <input type="submit" name="button" value="<fmt:message key="label.transitionButton"/>">
</form>
    <table>
        <c:forEach var="ad" items="${advertisementList}" varStatus="status">
            <tr>
                <td><img src="fileController" width="80" height="80"></td>
                <td><a href=http://localhost:8080/apartments_web_war/control?command=transition_to_advertisement&id=${ad.getId()}>${ad.getTitle()}</a></td>
<%--                <td><c:out value="${ad.getTitle() }" /></td>--%>
                <td><c:out value="${ad.getPrice() }" /></td>
            </tr>
            <tr>
<%--                <td><ctg:dateTime dateTimeValue="${ad.getCreationDate()}"/></td>    //TODO(format tag)--%>
                <td><c:out value="${ad.getCreationDate() }" /></td>
            </tr>
        </c:forEach>
    </table>
    <c:import url="footer.jsp"/>
</body>
</html>