<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 15.07.2020
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/bootstrap/css/offcanvas.css" rel="stylesheet">

    <style>
        .user_img {
            border-radius: 50%;
        }
    </style>
</head>

<body class="bg-light">
<c:import url="header.jsp"/>
<main role="main" class="container">


    <div class="my-3 p-3 bg-white rounded box-shadow">
        <h5>${advertisement.getTitle()}</h5>
        <h6 class="border-bottom border-gray pb-2 mb-0" style="">
                ${apartment.getRegion()}, ${apartment.getCity()}
                ${apartment.getAddress()}
        </h6>
        <div class="media text-muted pt-3">
            <c:set var="photoMap" value="${apartment.getUnmodifiablePhotoMap()}"/>
            <c:choose>
                <c:when test="${photoMap.isEmpty()}">
                    <img class="mr-3" src="${pageContext.request.contextPath}/bootstrap/image/def_apartment.jpg" alt=""
                         width="170" height="170">
                </c:when>
                <c:otherwise>
                    <c:set var="entry" value="${photoMap.entrySet().iterator().next()}"/>
                    <img class="mr-3" src="data:image/jpg;base64,${entry.value}" width="850" height="500"
                         style="position: static; left: 50px;">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="media text-muted pt-3 border-bottom border-gray" style="margin-right: -3px;">

            <p class="media-body pb-3 mb-0 small lh-125" style="display: block; left: 15px; right: 10px; width: 112px; min-width: 21px;">
                <strong class="d-block text-gray-dark" style="text-align: center;"><fmt:message key="newEstate.rooms"/> : ${apartment.getRooms()}</strong>
            </p>
            <p class="media-body pb-3 mb-0 small lh-125 border-left border-gray" style="top: auto; opacity: 1;">
                <strong class="d-block text-gray-dark"style="text-align: center;">${advertisement.getPrice()} <fmt:message key="advertisement.currency"/></strong>
            </p>
            <p class="media-body pb-3 mb-0 small lh-125 border-left border-gray">
                <strong class="d-block text-gray-dark"><fmt:message key="advertisement.owner"/></strong>
                    <c:set var="owner" value="${apartment.getOwner()}"/>
                    <c:choose>
                        <c:when test="${user.getId() == owner.getId()}">
                            <b style="color: #005cbf"><br/><fmt:message key="advertisement.authorMessage"/></b><br/>
                            <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_estate><fmt:message key="advertisement.linkToEstate"/> </a>
                        </c:when>
                        <c:otherwise>
                            <img src="data:image/jpg;base64,${owner.getPhotoBase64()}" width="100" height="100" class="user_img"><br/>
                            <fmt:message key="advertisement.name"/> ${owner.getFirstName()} ${owner.getLastName()}<br/>
                            <fmt:message key="setting.phone"/> : ${owner.getPhone()}<br/>
                            <fmt:message key="setting.birthday"/> : ${owner.getBirthday()}<br/>
                            <c:choose>
                                <c:when test="${wasCreated == 'true'}">
                                    <b style="color: #3e8e41"><br/><fmt:message key="advertisement.wasCreatedMessage"/></b>
                                    <form action="${pageContext.request.contextPath}/control" method="get">
                                        <input type="hidden" name="command" value="transition_to_my_rent"/>
                                        <input type="submit" name="button" value="<fmt:message key="main.myRentButton"/>">
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${advertisement.isVisible() eq true}">
                                            <form action="${pageContext.request.contextPath}/control" method="get">
                                                <input type="hidden" name="command" value="transition_to_new_demand"/>
                                                <input type="hidden" name="advertisementId" value="${advertisement.getId()}">
                                                <input type="hidden" name="apartmentId" value="${apartment.getId()}"/>
                                                <input type="submit" name="button" value="<fmt:message key="advertisement.wantToRentButton"/>">
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <b><br/><fmt:message key="advertisement.archivedMessage"/></b><br/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
            </p>
        </div>
        <h5><fmt:message key="advertisement.apartment"/></h5>
        <div class="media text-muted pt-3 border-bottom border-gray" style="margin-right: -3px;">

            <p class="media-body pb-3 mb-0 small lh-125 " style="top: auto; opacity: 1; background-color: rgb(255,255,255);">
                <strong class="d-block text-gray-dark">
                    ${apartment.getDescription()}
                </strong>
            </p>
        </div>
        <div class="media text-muted pt-3 border-bottom border-gray" style="margin-right: -3px;">

            <p class="media-body pb-3 mb-0 small lh-125 border-left border-gray" style="top: auto; opacity: 1; background-color: rgb(226,224,224);">
                <strong class="d-block text-gray-dark"><fmt:message key="newEstate.floor"/> </strong>
            </p>
            <p class="media-body pb-3 mb-0 small lh-125 border-left border-gray">
                ${apartment.getFloor() !=0 ? apartment.getFloor() : null}
            </p>
        </div>
        <div class="media text-muted pt-3 border-bottom border-gray" style="margin-right: -3px;">

            <p class="media-body pb-3 mb-0 small lh-125 border-left border-gray" style="top: auto; opacity: 1; background-color: rgb(226,224,224);">
                <strong class="d-block text-gray-dark"><fmt:message key="newEstate.square"/> </strong>
            </p>
            <p class="media-body pb-3 mb-0 small lh-125 border-left border-gray">
                ${apartment.getSquare() !=0.0 ? apartment.getSquare() : null}
            </p>
        </div>
        <div class="media text-muted pt-3 border-bottom border-gray" style="margin-right: -3px;">

            <p class="media-body pb-3 mb-0 small lh-125 border-left border-gray" style="top: auto; opacity: 1; background-color: rgb(226,224,224);">
                <strong class="d-block text-gray-dark"><fmt:message key="newEstate.year"/> </strong>
            </p>
            <p class="media-body pb-3 mb-0 small lh-125 border-left border-gray">
                ${apartment.getYear()}
            </p>
        </div>
        <div class="media text-muted pt-3 border-bottom border-gray" style="margin-right: -3px;">

            <p class="media-body pb-3 mb-0 small lh-125 border-left border-gray" style="top: auto; opacity: 1; background-color: rgb(226,224,224);">
                <strong class="d-block text-gray-dark"><fmt:message key="newEstate.furniture"/></strong>
            </p>
            <p class="media-body pb-3 mb-0 small lh-125 border-left border-gray">
                <c:choose>
                        <c:when test="${apartment.hasFurniture() == 'true'}">
                            <fmt:message key="newEstate.yes"/> <br/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="estate.no"/> <br/>
                        </c:otherwise>
                </c:choose>
            </p>
        </div>

        <c:if test="${!photoMap.isEmpty()}">
            <c:set var="iterator" value="${photoMap.entrySet().iterator()}"/>
            <c:forEach begin="1" end="${photoMap.size()}">
                <div class="media text-muted pt-3 border-bottom border-gray" style="margin-right: -3px;">
                    <c:set var="entry" value="${iterator.next()}"/>
                    <img class="mr-3" src="data:image/jpg;base64,${entry.value}" width="700" height="450"
                         style="position: static; left: 50px;">
                </div>
            </c:forEach>
        </c:if>

<%--        <c:choose>--%>
<%--            <c:when test="${!photoMap.isEmpty()}">--%>
<%--                <img class="mr-3" src="${pageContext.request.contextPath}/bootstrap/image/def_apartment.jpg" alt=""--%>
<%--                     width="170" height="170">--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--                <c:set var="entry" value="${photoMap.entrySet().iterator().next()}"/>--%>
<%--                <img class="mr-3" src="data:image/jpg;base64,${entry.value}" width="850" height="500"--%>
<%--                     style="position: static; left: 50px;">--%>
<%--            </c:otherwise>--%>
<%--        </c:choose>--%>

        <div class="media text-muted pt-3 border-bottom border-gray" style="margin-right: -3px;">

        </div>

        <small class="d-block text-right mt-3">

        </small>
    </div>


</main>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="../../js/vendor/popper.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/holderjs@2.9.4/holder.js"></script>
<script src="offcanvas.js"></script>


<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" preserveAspectRatio="none" style="display: none; visibility: hidden; position: absolute; top: -100%; left: -100%;"><defs><style type="text/css"></style></defs><text x="0" y="2" style="font-weight:bold;font-size:2pt;font-family:Arial, Helvetica, Open Sans, sans-serif">32x32</text></svg></body>
</html>