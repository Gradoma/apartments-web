<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 15.06.2020
  Time: 5:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/custom/custom.tld" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../../../favicon.ico">

    <title>Apartment project</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/bootstrap/css/offcanvas.css" rel="stylesheet">

    <style>
        .pagination a {
            color: black;
            float: left;
            padding: 8px 16px;
            text-decoration: none;
            transition: background-color .3s;
        }

        .pagination a.active {
            background-color: dodgerblue;
            color: white;
        }

        .pagination a:hover:not(.active) {background-color: #ddd;}
    </style>
</head>
<body class="bg-light">
<c:import url="header.jsp"/>
<main role="main" class="container">

    <div class="my-3 p-3 bg-white rounded box-shadow" style="margin-top: 69px; padding-top: 63px;">
        <div class="media text-muted pt-3 border-bottom border-gray" style="">
            <p class="media-body pb-3 mb-0 small lh-125" style="border-color: rgb(13, 32, 49); top: auto; left: 67px; right: 30%; width: 86px; min-width: 14px; font-weight: 700; height: 0%; min-height: 0%; max-width: 159%; max-height: 0%; text-align: center; margin-top: 9px;">
            <div class="pagination">
                <c:forEach var="page" varStatus="status" begin="1" end="${pagesAmount}">
                    <a class="${status.count == currentPage ? 'active' : ''}" href="http://localhost:8080/apartments_web_war/control?command=next_advertisement&page=${status.count}"><c:out value="${status.count}" /></a>
                </c:forEach>
            </div>
            </p>
        </div>

        <c:forEach var="advertisement" items="${advertisementList}" varStatus="status" begin="${ not empty firstAdvertisement ? firstAdvertisement : 0}"
                   end="${ not empty lastAdvertisement ? lastAdvertisement : 4}">
            <c:set var="apartment" value="${apartmentMap[advertisement.getId()]}"/>
            <c:set var="photoMap" value="${apartment.getUnmodifiablePhotoMap()}"/>
            <div class="media text-muted pt-3 border-bottom border-gray" style="">
                <c:choose>
                    <c:when test="${photoMap.isEmpty()}">
                        <img class="mr-3" src="${pageContext.request.contextPath}/bootstrap/image/def_apartment.jpg" alt=""
                             width="170" height="170">
                    </c:when>
                    <c:otherwise>
                        <c:set var="entry" value="${photoMap.entrySet().iterator().next()}"/>
                        <img class="mr-3" src="data:image/jpg;base64,${entry.value}" alt="" width="250" height="170">
                    </c:otherwise>
                </c:choose>
                <p class="media-body pb-3 mb-0 small lh-125" style="width: 322px;">
                    <strong class="d-block text-gray-dark">
                        <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_advertisement&id=${advertisement.getId()}>${advertisement.getTitle()}</a>
                    </strong>
                        ${apartment.getAddress()}, ${apartment.getRegion()}, ${apartment.getCity()} <br/>
                    <small><ctg:dateTime dateTimeValue="${advertisement.getCreationDate()}"/></small>
                </p>
                <p class="media-body pb-3 mb-0 small lh-125" style="border-color: rgb(13, 32, 49); top: auto; left: 30%; right: 30%; width: 50%; min-width: 0%; font-weight: 700; height: 0%; min-height: 0%; max-width: 120px; max-height: 0%;">${advertisement.getPrice()} <fmt:message key="advertisement.currency"/></p>
            </div>
        </c:forEach>

        <div class="media text-muted pt-3 border-bottom border-gray" style="">
            <p class="media-body pb-3 mb-0 small lh-125" style="border-color: rgb(13, 32, 49); top: auto; left: 67px; right: 30%; width: 86px; min-width: 14px; font-weight: 700; height: 0%; min-height: 0%; max-width: 159%; max-height: 0%; text-align: center; margin-top: 9px;">
            <div class="pagination">
                <c:forEach var="page" varStatus="status" begin="1" end="${pagesAmount}">
                    <a class="${status.count == currentPage ? 'active' : ''}" href="http://localhost:8080/apartments_web_war/control?command=next_advertisement&page=${status.count}"><c:out value="${status.count}" /></a>
                </c:forEach>
            </div>
            </p>
        </div>
    </div>
</main>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/vendor/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/holderjs@2.9.4/holder.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/offcanvas.js"></script>


<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" preserveAspectRatio="none" style="display: none; visibility: hidden; position: absolute; top: -100%; left: -100%;"><defs><style type="text/css"></style></defs>
    <text x="0" y="2" style="font-weight:bold;font-size:2pt;font-family:Arial, Helvetica, Open Sans, sans-serif">32x32</text></svg>
</body>
</html>
