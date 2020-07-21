<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 16.07.2020
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<c:set var="refused" value="REFUSED"/>
<c:set var="approved" value="APPROVED"/>
<html>
<head>
    <title>Requests</title>
</head>
<body>
<c:import url="header.jsp"/>
    ${refuseErrorMessage}
    <c:choose>
        <c:when test="${requestList == null}">
            <fmt:message key="requests.messageNoRequests"/>
        </c:when>
        <c:otherwise>
            <table>
                <c:forEach var="request" items="${requestList}" varStatus="status">
                    <c:set var="applicant" value="${request.getApplicant()}"/>
                    <tr>
                        <td>
                            <c:out value="${ status.count }" /><br/>
                            <small>${ request.getCreationDate() }</small>
                        </td>
                        <td>
                            <img src="data:image/jpg;base64,${applicant.getPhotoBase64()}" width="100" height="100"><br/>
                            <b><fmt:message key="requests.name"/> ${applicant.getFirstName()} ${applicant.getLastName()}</b><br/>
                            <fmt:message key="setting.birthday"/> : ${applicant.getBirthday()}<br/>
                            <fmt:message key="setting.phone"/> : ${applicant.getPhone()}<br/>
                            <fmt:message key="requests.registrationDate"/> ${applicant.getRegistrationDate()}<br/>
                            <fmt:message key="requests.description"/> ${request.getDescription()}<br/>
                        </td>
                        <td><fmt:message key="requests.expectedDate"/> ${ request.getExpectedDate() }" </td>
                        <td>
                            <c:choose>
                                <c:when test="${containsApproved == 'true'}">
                                    <c:choose>
                                        <c:when test="${request.getStatus() == approved}">
                                            <h5><fmt:message key="requests.statusApproved"/></h5>
                                        </c:when>
                                        <c:when test="${request.getStatus() == refused}">
                                            <h5><fmt:message key="requests.statusRefused"/></h5>
                                        </c:when>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${request.getStatus() == refused}">
                                            <h5><fmt:message key="requests.statusRefused"/></h5>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="control" method="get">
                                                <input type="hidden" name="command" value="approve_request"/>
                                                <input type="hidden" name="requestId" value="${ request.getId() }"/>
                                                <input type="hidden" name="apartmentId" value="${ request.getApartmentId() }"/>
                                                <input type="submit" name="button" value="<fmt:message key="requests.approveButton"/>">
                                            </form>
                                            <form action="control" method="get">
                                                <input type="hidden" name="command" value="refuse_request"/>
                                                <input type="hidden" name="requestId" value="${ request.getId() }"/>
                                                <input type="hidden" name="apartmentId" value="${ request.getApartmentId() }"/>
                                                <input type="submit" name="button" value="<fmt:message key="requests.refuseButton"/>">
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
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
