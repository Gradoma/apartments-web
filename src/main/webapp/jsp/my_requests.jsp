<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 17.07.2020
  Time: 12:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="created" value="CREATED"/>
<c:set var="approved" value="APPROVED"/>
<c:set var="refused" value="REFUSED"/>
<c:set var="canceled" value="CANCELED"/>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>My Requests</title>
</head>
<body>
<c:import url="header.jsp"/>
<c:choose>
    <c:when test="${requestList == null}">
        <fmt:message key="myRequests.messageNoRequests"/>
    </c:when>
    <c:otherwise>
        <table>
            <c:forEach var="request" items="${requestList}" varStatus="status">
                <c:set var="requestId" value="${request.getId()}"/>
                <c:set var="advertisement" value="${advertisementMap[requestId]}"/>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${request.getStatus() == created}">
                                <fmt:message key="myRequests.statusCreated"/>
                            </c:when>
                            <c:when test="${request.getStatus() == approved}">
                                <fmt:message key="myRequests.statusApproved"/>
                            </c:when>
                            <c:when test="${request.getStatus() == refused}">
                                <fmt:message key="myRequests.statusRefused"/>
                            </c:when>
                            <c:when test="${request.getStatus() == canceled}">
                                <fmt:message key="myRequests.statusCanceled"/>
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_advertisement&id=${advertisement.getId()}>${advertisement.getTitle()}</a><br/>
                        <b>${advertisement.getPrice()} <fmt:message key="advertisement.currency"/> </b>
                    </td>
                    <td>
                        ${ request.getCreationDate() }
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${advertisement.isVisible() == 'true'}">
                                <form action="control" method="get">
                                    <input type="hidden" name="command" value="cancel_request"/>
                                    <input type="hidden" name="requestId" value="${ request.getId() }"/>
                                    <input type="submit" name="button" value="<fmt:message key="myRequests.cancelButton"/>">
                                </form>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="myRequests.advetisementStatus"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<c:import url="footer.jsp"/>
</body>
</html>