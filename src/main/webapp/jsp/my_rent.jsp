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
<%@ taglib prefix="ctg" uri="/WEB-INF/custom/custom.tld" %>
<c:set var="created" value="CREATED"/>
<c:set var="approved" value="APPROVED"/>
<c:set var="refused" value="REFUSED"/>
<c:set var="canceled" value="CANCELED"/>
<c:set var="apartmentRent" value="RENT"/>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../../../favicon.ico">

    <title>Offcanvas template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="bootstrap/css/offcanvas.css" rel="stylesheet">
</head>

<body class="bg-light">
<c:import url="header.jsp"/>
<main role="main" class="container">

    <div class="my-3 p-3 bg-white rounded box-shadow">
        <h6 class="border-bottom border-gray pb-2 mb-0"><fmt:message key="main.myRequestsButton"/></h6>
        <c:choose>
            <c:when test="${requestList == null}">
                <fmt:message key="myRequests.messageNoRequests"/>
            </c:when>
            <c:otherwise>
                <c:forEach var="request" items="${requestList}" varStatus="status">
                    <c:set var="requestId" value="${request.getId()}"/>
                    <c:set var="advertisement" value="${advertisementMap[requestId]}"/>
                    <c:set var="apartment" value="${apartmentMap[requestId]}"/>
                    <div class="media text-muted pt-3 border-bottom border-gray">

                        <p class="media-body pb-3 mb-0 small lh-125" style="width: 250px; min-width: 55px; height: 40px; max-width: 80px;">
                            <strong class="d-block text-gray-dark">
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
                            </strong>
                        </p>

                        <p class="media-body pb-3 mb-0 small lh-125" style="min-width: 450px; max-width: 460px; width: 390px;">
                            <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_advertisement&id=${advertisement.getId()}>${advertisement.getTitle()}</a>
                            <strong class="d-block text-gray-dark">
                                    ${advertisement.getPrice()} <fmt:message key="advertisement.currency"/>
                            </strong>
                            <strong class="d-block text-gray-dark">
                                ${apartment.getRegion()}, ${apartment.getCity()}, ${apartment.getAddress()}
                            </strong>
                        </p>

                        <p class="media-body pb-3 mb-0 small lh-125">
                            <strong class="d-block text-gray-dark">
                                <ctg:dateTime dateTimeValue="${ request.getCreationDate() }"/>
                            </strong>
                        </p>

                        <p class="media-body pb-3 mb-0 small lh-125">
                            <c:choose>
                                <c:when test="${advertisement.isVisible() == 'true'}">
                                    <c:choose>
                                        <c:when test="${request.getStatus() == created}">
                                        <strong>
                                            <form action="${pageContext.request.contextPath}/control" method="get">
                                                <input type="hidden" name="command" value="cancel_request"/>
                                                <input type="hidden" name="requestId" value="${ request.getId() }"/>
                                                <input type="submit" name="button" value="<fmt:message key="myRequests.cancelButton"/>">
                                            </form>
                                        </strong>
                                        </c:when>

                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${apartment.getStatus() == apartmentRent}">
                                            <strong><fmt:message key="myRequests.apartmentRent"/></strong>
                                            <strong>
                                                <form action="${pageContext.request.contextPath}/control" method="get">
                                                    <input type="hidden" name="command" value="finish_rent"/>
                                                    <input type="hidden" name="apartmentId" value="${apartment.getId() }"/>
                                                    <input type="hidden" name="requestId" value="${ request.getId() }"/>
                                                    <input type="submit" name="button" value="<fmt:message key="myRequests.finishRentButton"/>">
                                                </form>
                                            </strong>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${request.getStatus() == approved}">
                                                    <strong>
                                                        <form action="${pageContext.request.contextPath}/control" method="get">
                                                            <input type="hidden" name="command" value="accept_invitation"/>
                                                            <input type="hidden" name="requestId" value="${ request.getId() }"/>
                                                            <input type="hidden" name="apartmentId" value="${ request.getApartmentId() }"/>
                                                            <input type="submit" name="button" value="<fmt:message key="myRequests.acceptButton"/>">
                                                        </form>
                                                    </strong>
                                                    <strong>
                                                        <form action="${pageContext.request.contextPath}/control" method="get">
                                                            <input type="hidden" name="command" value="decline_invitation"/>
                                                            <input type="hidden" name="requestId" value="${ request.getId() }"/>
                                                            <input type="hidden" name="advertisementId" value="${ advertisement.getId() }"/>
                                                            <input type="submit" name="button" value="<fmt:message key="myRequests.declineButton"/>">
                                                        </form>
                                                    </strong>
                                                    <br/>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="myRequests.advetisementStatus"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>

                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</main>
</body>
</html>