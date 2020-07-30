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
<%@ taglib prefix="ctg" uri="/WEB-INF/custom/custom.tld" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<c:set var="refused" value="REFUSED"/>
<c:set var="approved" value="APPROVED"/>
<html>
<head>
    <title>Requests</title>
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

        .user_img {
            border-radius: 50%;
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
                            <small><ctg:dateTime dateTimeValue="${ request.getCreationDate() }"/></small>
                        </td>
                        <td>
                            <img src="data:image/jpg;base64,${applicant.getPhotoBase64()}" width="100" height="100" class="user_img"><br/>
                            <b>
                                <fmt:message key="requests.name"/> ${applicant.getFirstName()} ${applicant.getLastName()}<br/>
                                <fmt:message key="requests.description"/> ${request.getDescription()}<br/>
                            </b>
                            <fmt:message key="setting.birthday"/> : ${applicant.getBirthday()}<br/>
                            <fmt:message key="setting.phone"/> : ${applicant.getPhone()}<br/>
                            <fmt:message key="requests.registrationDate"/> <ctg:dateTime dateTimeValue="${applicant.getRegistrationDate()}"/><br/>
                        </td>
                        <td><fmt:message key="requests.expectedDate"/> ${ request.getExpectedDate() }" </td>
                        <td>
                            <c:choose>
                                <c:when test="${containsApproved == 'true'}">
                                    <c:choose>
                                        <c:when test="${request.getStatus() == approved}">
                                            <b><fmt:message key="requests.statusApproved"/></b>
                                        </c:when>
                                        <c:when test="${request.getStatus() == refused}">
                                            <strong><fmt:message key="requests.statusRefused"/></strong>
                                        </c:when>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${request.getStatus() == refused}">
                                            <strong><fmt:message key="requests.statusRefused"/></strong>
                                        </c:when>
                                        <c:otherwise>
                                            <button onclick="document.getElementById('id01').style.display='block'" style="width:auto;" class="btn btn-sm btn-success">
                                                <fmt:message key="requests.approveButton"/>
                                            </button>

                                            <div id="id01" class="modal">
                                                <form class="modal-content animate" action="${pageContext.request.contextPath}/control" method="get">
                                                        <input type="hidden" name="command" value="approve_request"/>
                                                        <input type="hidden" name="requestId" value="${ request.getId() }"/>
                                                        <input type="hidden" name="apartmentId" value="${ request.getApartmentId() }"/>
                                                    <div class="imgcontainer">
                                                        <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
                                                    </div>

                                                    <div class="container">
                                                        <b><fmt:message key="requests.acceptConfirmText"/></b><br/>
                                                        <p><fmt:message key="requests.acceptConfirmText2"/></p>
                                                        <button type="submit" style=""><fmt:message key="requests.approveButton"/></button>
                                                    </div>
                                                </form>
                                            </div>

<%--                                            <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                                <input type="hidden" name="command" value="approve_request"/>--%>
<%--                                                <input type="hidden" name="requestId" value="${ request.getId() }"/>--%>
<%--                                                <input type="hidden" name="apartmentId" value="${ request.getApartmentId() }"/>--%>
<%--                                                <input type="submit" name="button" value="<fmt:message key="requests.approveButton"/>">--%>
<%--                                            </form>--%>
                                            <a href="http://localhost:8080/apartments_web_war/control?command=refuse_request&=requestId=${ request.getId() }&apartmentId=${ request.getApartmentId() }"
                                               class="btn btn-sm btn-danger"><fmt:message key="myRequests.declineButton"/></a>
<%--                                            <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                                <input type="hidden" name="command" value="refuse_request"/>--%>
<%--                                                <input type="hidden" name="requestId" value="${ request.getId() }"/>--%>
<%--                                                <input type="hidden" name="apartmentId" value="${ request.getApartmentId() }"/>--%>
<%--                                                <input type="submit" name="button" value="<fmt:message key="requests.refuseButton"/>">--%>
<%--                                            </form>--%>
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