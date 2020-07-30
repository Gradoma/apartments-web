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
    <style>
        /* Set a style for all buttons */
        button {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        button:hover {
            opacity: 0.8;
        }


        .container {
            padding: 16px;
        }

        span.psw {
            float: right;
            padding-top: 16px;
        }

        /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
            padding-top: 60px;
        }

        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
            border: 1px solid #888;
            width: 80%; /* Could be more or less, depending on screen size */
        }

        .close:hover,
        .close:focus {
            color: red;
            cursor: pointer;
        }

        /* Add Zoom Animation */
        .animate {
            -webkit-animation: animatezoom 0.6s;
            animation: animatezoom 0.6s
        }

        @-webkit-keyframes animatezoom {
            from {-webkit-transform: scale(0)}
            to {-webkit-transform: scale(1)}
        }

        @keyframes animatezoom {
            from {transform: scale(0)}
            to {transform: scale(1)}
        }
    </style>
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

                        <p class="media-body pb-3 mb-0 small lh-125" style="width: 350px; min-width: 80px; height: 40px; max-width: 120px;">
                                <c:choose>
                                    <c:when test="${request.getStatus() == created}">
                                        <strong style="color: #005cbf">
                                            <fmt:message key="myRequests.statusCreated"/>
                                        </strong>
                                    </c:when>
                                    <c:when test="${request.getStatus() == approved}">
                                        <strong style="color: #4CAF50">
                                            <fmt:message key="myRequests.statusApproved"/>
                                        </strong>
                                    </c:when>
                                    <c:when test="${request.getStatus() == refused}">
                                        <strong style="color: #b21f2d">
                                            <fmt:message key="myRequests.statusRefused"/>
                                        </strong>
                                    </c:when>
                                    <c:when test="${request.getStatus() == canceled}">
                                        <strong class="d-block text-gray-dark">
                                            <fmt:message key="myRequests.statusCanceled"/>
                                        </strong>
                                    </c:when>
                                </c:choose>
                        </p>

                        <p class="media-body pb-3 mb-0 small lh-125" style="min-width: 450px; max-width: 460px; width: 390px;">
                            <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_advertisement&id=${advertisement.getId()}>${advertisement.getTitle()}</a>
                            <strong class="d-block text-gray-dark">
                                <b>${advertisement.getPrice()} <fmt:message key="advertisement.currency"/></b>
                            </strong>
                                ${apartment.getRegion()}, ${apartment.getCity()}, ${apartment.getAddress()}
                        </p>

                        <p class="media-body pb-3 mb-0 small lh-125">
                            <ctg:dateTime dateTimeValue="${ request.getCreationDate() }"/>
                        </p>

                        <p class="media-body pb-3 mb-0 small lh-125">
                            <c:choose>
                                <c:when test="${advertisement.isVisible() == 'true'}">
                                    <c:choose>
                                        <c:when test="${request.getStatus() == created}">
                                        <strong>
                                            <a href="http://localhost:8080/apartments_web_war/control?command=cancel_request&requestId=${ request.getId() }"
                                               class="btn btn-sm btn-danger"><fmt:message key="myRequests.cancelButton"/></a>
<%--                                            <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                                <input type="hidden" name="command" value="cancel_request"/>--%>
<%--                                                <input type="hidden" name="requestId" value="${ request.getId() }"/>--%>
<%--                                                <input type="submit" name="button" value="<fmt:message key="myRequests.cancelButton"/>">--%>
<%--                                            </form>--%>
                                        </strong>
                                        </c:when>

                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${apartment.getStatus() == apartmentRent}">
                                            <strong><fmt:message key="myRequests.apartmentRent"/></strong>
                                            <strong>
                                                <a href="http://localhost:8080/apartments_web_war/control?command=finish_rent&apartmentId=${apartment.getId() }&requestId=${ request.getId() }"
                                                   class="btn btn-sm btn-primary"><fmt:message key="myRequests.finishRentButton"/></a>
<%--                                                <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                                    <input type="hidden" name="command" value="finish_rent"/>--%>
<%--                                                    <input type="hidden" name="apartmentId" value="${apartment.getId() }"/>--%>
<%--                                                    <input type="hidden" name="requestId" value="${ request.getId() }"/>--%>
<%--                                                    <input type="submit" name="button" value="<fmt:message key="myRequests.finishRentButton"/>">--%>
<%--                                                </form>--%>
                                            </strong>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${request.getStatus() == approved}">
                                                    <strong>
                                                        <button onclick="document.getElementById('id01').style.display='block'" style="width:auto;" class="btn btn-sm btn-success">
                                                            <fmt:message key="myRequests.acceptButton"/>
                                                        </button>
                                                        <div id="id01" class="modal">

                                                            <form class="modal-content animate" action="${pageContext.request.contextPath}/control" method="post">
                                                                <input type="hidden" name="command" value="accept_invitation">
                                                                <input type="hidden" name="requestId" value="${ request.getId() }"/>
                                                                <input type="hidden" name="apartmentId" value="${ request.getApartmentId() }"/>
                                                                <div class="imgcontainer">
                                                                    <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
                                                                </div>

                                                                <div class="container">
                                                                    <b><fmt:message key="myRequests.acceptConfirmText"/></b><br/>
                                                                    <p><fmt:message key="myRequests.acceptConfirmText2"/></p>
                                                                    <button type="submit" style=""><fmt:message key="myRequests.acceptButton"/></button>
                                                                </div>
                                                                </form>
                                                            </div>
<%--                                                        <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                                            <input type="hidden" name="command" value="accept_invitation"/>--%>
<%--                                                            <input type="hidden" name="requestId" value="${ request.getId() }"/>--%>
<%--                                                            <input type="hidden" name="apartmentId" value="${ request.getApartmentId() }"/>--%>
<%--                                                            <input type="submit" name="button" value="<fmt:message key="myRequests.acceptButton"/>">--%>
<%--                                                        </form>--%>
                                                    </strong>
                                                    <strong>
                                                        <a href="http://localhost:8080/apartments_web_war/control?command=decline_invitation&=requestId=${ request.getId() }&advertisementId=${ advertisement.getId() }"
                                                           class="btn btn-sm btn-danger"><fmt:message key="myRequests.declineButton"/></a>
<%--                                                        <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                                            <input type="hidden" name="command" value="decline_invitation"/>--%>
<%--                                                            <input type="hidden" name="requestId" value="${ request.getId() }"/>--%>
<%--                                                            <input type="hidden" name="advertisementId" value="${ advertisement.getId() }"/>--%>
<%--                                                            <input type="submit" name="button" value="<fmt:message key="myRequests.declineButton"/>">--%>
<%--                                                        </form>--%>
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