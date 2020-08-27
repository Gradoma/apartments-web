<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 03.07.2020
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="registered" value="REGISTERED"/>
<c:set var="demand" value="IN_DEMAND"/>
<c:set var="rent" value="RENT"/>
<c:set var="deleted" value="DELETED"/>
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
        <h6 class="border-bottom border-gray pb-2 mb-0"><fmt:message key="main.estateButton"/></h6>
        <c:choose>
            <c:when test="${noAppartmentsMessage eq true}">
                <fmt:message key="estate.noAppartmentsMessage"/>
            </c:when>
            <c:otherwise>
                <c:forEach var="elem" items="${apartmentList}" varStatus="status">
                    <c:set var="apartmentId" value="${elem.getId()}"/>
                    <c:set var="photoMap" value="${elem.getUnmodifiablePhotoMap()}"/>
                    <div class="media text-muted pt-3 border-bottom border-gray">

                        <p class="media-body pb-3 mb-0 small lh-125" style="width: 148px; min-width: 37px; height: 40px; max-width: 38px;">
                            <strong class="d-block text-gray-dark">
                                <c:out value="${ status.count }" />
                            </strong>
                        </p>

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

                        <p class="media-body pb-3 mb-0 small lh-125" style="min-width: 450px; max-width: 460px; width: 390px;">
                            <strong class="d-block text-gray-dark">
                                    ${ elem.getRegion() }, ${ elem.getCity() }, ${ elem.getAddress() }
                            </strong>
                        </p>

                        <p class="media-body pb-3 mb-0 small lh-125">
                            <c:choose>
                            <c:when test="${elem.getStatus() == registered}">
                                <strong class="d-block text-gray-dark">
                                    <a href="http://localhost:8080/apartments_web_war/control?command=transition_to_estate_edit&apartmentId=${elem.getId()}"
                                   class="btn btn-info"><fmt:message key="estate.editButton"/></a>
                                </strong>
                                <strong class="d-block text-gray-dark">
                                    <a href="http://localhost:8080/apartments_web_war/control?command=transition_to_new_ad&apartmentId=${elem.getId()}"
                                   class="btn btn-success"><fmt:message key="estate.newAdButton"/></a>
                                </strong>
                            </c:when>
                            <c:when test="${elem.getStatus() == demand}">
                                <c:set var="contains" value="${demandMap[apartmentId]}"/>
                                <h6><fmt:message key="estate.demandMessage"/></h6><br/>
                                <c:choose>
                                    <c:when test="${contains eq true}">
                                        <strong class="d-block text-gray-dark">
                                            <fmt:message key="estate.cantChangeMessage"/><br/>
                                        </strong>
                                    </c:when>
                                    <c:otherwise>
                                        <strong class="d-block text-gray-dark">
                                            <a href="http://localhost:8080/apartments_web_war/control?command=transition_to_advertisement_edit&apartmentId=${elem.getId()}&region=${elem.getRegion()}&city=${elem.getCity()}&address=${elem.getAddress()}&rooms=${elem.getRooms()}&floor=${elem.getFloor()}&square=${elem.getSquare()}&year=${elem.getYear()}&furniture=${elem.hasFurniture()}&description=${elem.getDescription()}"
                                           class="btn btn-warning"><fmt:message key="estate.editAdvertisementButton"/></a>
                                        </strong>
                                        <br/>
                                    </c:otherwise>
                                </c:choose>
                                    <strong class="d-block text-gray-dark">
                                        <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_demand_list&apartmentId=${elem.getId()}><fmt:message key="estate.demands"/> </a>
                                    </strong>
                            </c:when>
                            <c:when test="${elem.getStatus() == rent}">
                                <h6><fmt:message key="estate.rentedMessage"/></h6>
                            </c:when>
                            </c:choose>
                        </p>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <a href="http://localhost:8080/apartments_web_war/control?command=transition_to_new_estate"
           class="btn btn-success">
            <span class="glyphicon glyphicon-plus"></span><fmt:message key="estate.newApartmentButton"/>
        </a>
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


<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" preserveAspectRatio="none" style="display: none; visibility: hidden; position: absolute; top: -100%; left: -100%;"><defs><style type="text/css"></style></defs><text x="0" y="2" style="font-weight:bold;font-size:2pt;font-family:Arial, Helvetica, Open Sans, sans-serif">32x32</text></svg>
</body>
</html>