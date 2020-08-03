<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 19.07.2020
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>Edit advertisement</title>
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
<body>
<c:import url="header.jsp"/>

<button class="btn btn-sm btn-danger" onclick="document.getElementById('id01').style.display='block'" style="width:auto;">
    <fmt:message key="advertisement.deleteButton"/>
</button>
<div id="id01" class="modal">

    <form class="modal-content animate" action="${pageContext.request.contextPath}/control" method="post">
        <input type="hidden" name="command" value="delete_advertisement">
        <div class="imgcontainer">
            <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
        </div>

        <div class="container">
            <b><fmt:message key="advertisement.deleteConfirmText"/></b>
            <p><fmt:message key="advertisement.deleteConfirmText2"/></p>
            <button class="btn btn-sm btn-danger" type="submit" style=""><fmt:message key="advertisement.deleteButton"/></button>
        </div>
    </form>
</div>

<form action="${pageContext.request.contextPath}/control" method="get">
    <small><fmt:message key="message.required"/> </small><br/>
    <input type="hidden" name="command" value="edit_advertisement">
    <fmt:message key="newAdvertisement.title"/> : <input required name="title" pattern="^.{1,70}$" value="${advertisement.getTitle()}"><br/>
    <c:choose>
        <c:when test="${titleError eq true}">
            <fmt:message key="newAdvertisement.titleErrorMesage"/><br/>
        </c:when>
    </c:choose>
    <fmt:message key="newAdvertisement.price"/>* : <input type="text" required name="price"
                                              pattern="^((\p{Digit}){1,5}([.,]\d{1,2})?)$" value="${advertisement.getPrice()}"><br/>
    <c:choose>
        <c:when test="${priceError eq true}">
            <fmt:message key="newAdvertisement.priceErrorMesage"/><br/>
        </c:when>
        <c:when test="${errorUpdate eq true}">
            <fmt:message key="advertisement.errorUpdateMessage"/>
        </c:when>
    </c:choose>
    <input type="submit" name="button" value="<fmt:message key="advertisement.saveButton"/>"/>
</form>
<%--<form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--    <input type="hidden" name="command" value="delete_advertisement">--%>
<%--    <input type="submit" name="button" value="<fmt:message key="advertisement.deleteButton"/>"/>--%>
<%--</form>--%>
<b><fmt:message key="advertisement.apartment"/></b><br/>
<fmt:message key="newEstate.region"/> : ${region}<br/>
<fmt:message key="newEstate.city"/> : ${city}"<br/>
<fmt:message key="newEstate.address"/> : ${address}<br/>
<fmt:message key="newEstate.rooms"/> : ${rooms}<br/>
<fmt:message key="newEstate.floor"/> : ${floor!=0 ? floor : null}<br/>
<fmt:message key="newEstate.square"/> : ${square!=0.0 ? square : null}<br/>
<fmt:message key="newEstate.year"/> : ${year}<br/>
<fmt:message key="newEstate.furniture"/> :
<c:choose>
    <c:when test="${apartmentFurniture eq true}">
        <fmt:message key="newEstate.yes"/><br/>
    </c:when>
    <c:otherwise>
        <fmt:message key="estate.no"/><br/>
    </c:otherwise>
</c:choose>
<fmt:message key="newEstate.description"/> : ${description}<br/>
<c:import url="footer.jsp"/>
</body>
</html>
