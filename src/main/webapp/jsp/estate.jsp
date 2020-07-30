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
<%--<html>--%>
<%--<head>--%>
<%--    <title>Estate</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<c:import url="header.jsp"/>--%>
<%--<c:choose>--%>
<%--    <c:when test="${noAppartmentsMessage eq true}">--%>
<%--        <h2><fmt:message key="estate.noAppartmentsMessage"/> </h2>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <table>--%>
<%--            <c:forEach var="elem" items="${apartmentList}" varStatus="status">--%>
<%--                <c:set var="apartmentId" value="${elem.getId()}"/>--%>
<%--                <c:set var="photoMap" value="${elem.getUnmodifiablePhotoMap()}"/>--%>
<%--                <tr>--%>
<%--                    <td><c:out value="${ status.count }" /></td>--%>
<%--                    <td>--%>
<%--                        <c:choose>--%>
<%--                            <c:when test="${photoMap.isEmpty()}">--%>
<%--                                No photo--%>
<%--                            </c:when>--%>
<%--                            <c:otherwise>--%>
<%--                                <c:set var="entry" value="${photoMap.entrySet().iterator().next()}"/>--%>
<%--                                <img src="data:image/jpg;base64,${entry.value}" width="250" height="150">--%>
<%--                            </c:otherwise>--%>
<%--                        </c:choose>--%>
<%--                    </td>--%>
<%--                    <td><c:out value="${ elem.getRegion() }" /></td>--%>
<%--                    <td><c:out value="${ elem.getCity() }" /></td>--%>
<%--                    <td><c:out value="${ elem.getAddress() }" /></td>--%>
<%--                    <td>--%>
<%--                        <c:choose>--%>
<%--                            <c:when test="${elem.getStatus() == registered}">--%>
<%--                                <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                    <input type="hidden" name="command" value="transition_to_estate_edit"/>--%>
<%--                                    <input type="hidden" name="apartmentId" value="${elem.getId()}">--%>
<%--                                    <input type="submit" name="button" value="<fmt:message key="estate.editButton"/>">--%>
<%--                                </form>--%>
<%--                                <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                    <input type="hidden" name="command" value="transition_to_new_ad"/>--%>
<%--                                    <input type="hidden" name="apartmentId" value="${elem.getId()}">--%>
<%--                                    <input type="submit" name="button" value="<fmt:message key="estate.newAdButton"/>">--%>
<%--                                </form>--%>
<%--                            </c:when>--%>
<%--                            <c:when test="${elem.getStatus() == demand}">--%>
<%--                                <c:set var="contains" value="${requestMap[apartmentId]}"/>--%>
<%--                                <h5><fmt:message key="estate.demandMessage"/></h5>--%>
<%--                                <c:choose>--%>
<%--                                    <c:when test="${contains eq true}">--%>
<%--                                        <fmt:message key="estate.cantChangeMessage"/>--%>
<%--                                    </c:when>--%>
<%--                                    <c:otherwise>--%>
<%--                                        <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                            <input type="hidden" name="command" value="transition_to_advertisement_edit"/>--%>
<%--                                            <input type="hidden" name="apartmentId" value="${elem.getId()}">--%>
<%--                                            <input type="hidden" name="region" value="${elem.getRegion()}">--%>
<%--                                            <input type="hidden" name="city" value="${elem.getCity()}">--%>
<%--                                            <input type="hidden" name="address" value="${elem.getAddress()}">--%>
<%--                                            <input type="hidden" name="city" value="${elem.getCity()}">--%>
<%--                                            <input type="hidden" name="rooms" value="${elem.getRooms()}">--%>
<%--                                            <input type="hidden" name="floor" value="${elem.getFloor()}">--%>
<%--                                            <input type="hidden" name="square" value="${elem.getSquare()}">--%>
<%--                                            <input type="hidden" name="year" value="${elem.getYear()}">--%>
<%--                                            <input type="hidden" name="furniture" value="${elem.hasFurniture()}">--%>
<%--                                            <input type="hidden" name="description" value="${elem.getDescription()}">--%>
<%--                                            <input type="submit" name="button" value="<fmt:message key="estate.editAdvertisementButton"/>">--%>
<%--                                        </form>--%>
<%--                                    </c:otherwise>--%>
<%--                                </c:choose>--%>
<%--                                <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_request_list&apartmentId=${elem.getId()}><fmt:message key="estate.requests"/> </a>--%>
<%--                            </c:when>--%>
<%--                            <c:when test="${elem.getStatus() == rent}">--%>
<%--                                <h5><fmt:message key="estate.rentedMessage"/></h5>--%>
<%--                            </c:when>--%>
<%--                        </c:choose>--%>
<%--                    </td>--%>
<%--                    <td><h4>${errorDeleteMessage}</h4></td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--        </table>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>
<%--<form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--    <input type="hidden" name="command" value="transition_to_new_estate"/>--%>
<%--    <input type="submit" name="button" value="<fmt:message key="estate.newApartmentButton"/>">--%>
<%--</form>--%>
<%--<c:import url="footer.jsp"/>--%>
<%--</body>--%>
<%--</html>--%>
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
                                <a href="http://localhost:8080/apartments_web_war/control?command=transition_to_estate_edit&apartmentId=${elem.getId()}"
                                   class="btn btn-info"><fmt:message key="estate.editButton"/></a>
<%--                                <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                    <input type="hidden" name="command" value="transition_to_estate_edit"/>--%>
<%--                                    <input type="hidden" name="apartmentId" value="${elem.getId()}">--%>
<%--                                    <input type="submit" name="button" value="">--%>
<%--                                </form>--%>
                                <a href="http://localhost:8080/apartments_web_war/control?command=transition_to_new_ad&apartmentId=${elem.getId()}"
                                   class="btn btn-success"><fmt:message key="estate.newAdButton"/></a>
<%--                                <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                    <input type="hidden" name="command" value="transition_to_new_ad"/>--%>
<%--                                    <input type="hidden" name="apartmentId" value="${elem.getId()}">--%>
<%--                                    <input type="submit" name="button" value="<fmt:message key="estate.newAdButton"/>">--%>
<%--                                </form>--%>
                            </c:when>
                            <c:when test="${elem.getStatus() == demand}">
                                <c:set var="contains" value="${requestMap[apartmentId]}"/>
                                <h5><fmt:message key="estate.demandMessage"/></h5><br/>
                                <c:choose>
                                    <c:when test="${contains eq true}">
                                        <fmt:message key="estate.cantChangeMessage"/><br/>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="http://localhost:8080/apartments_web_war/control?command=transition_to_advertisement_edit&apartmentId=${elem.getId()}&region=${elem.getRegion()}&city=${elem.getCity()}&address=${elem.getAddress()}&rooms=${elem.getRooms()}&floor=${elem.getFloor()}&square=${elem.getSquare()}&year=${elem.getYear()}&furniture=${elem.hasFurniture()}&description=${elem.getDescription()}"
                                           class="btn btn-warning"><fmt:message key="estate.editAdvertisementButton"/></a>
<%--                                        <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--                                            <input type="hidden" name="command" value="transition_to_advertisement_edit"/>--%>
<%--                                            <input type="hidden" name="apartmentId" value="${elem.getId()}">--%>
<%--                                            <input type="hidden" name="region" value="${elem.getRegion()}">--%>
<%--                                            <input type="hidden" name="city" value="${elem.getCity()}">--%>
<%--                                            <input type="hidden" name="address" value="${elem.getAddress()}">--%>
<%--                                            <input type="hidden" name="city" value="${elem.getCity()}">--%>
<%--                                            <input type="hidden" name="rooms" value="${elem.getRooms()}">--%>
<%--                                            <input type="hidden" name="floor" value="${elem.getFloor()}">--%>
<%--                                            <input type="hidden" name="square" value="${elem.getSquare()}">--%>
<%--                                            <input type="hidden" name="year" value="${elem.getYear()}">--%>
<%--                                            <input type="hidden" name="furniture" value="${elem.hasFurniture()}">--%>
<%--                                            <input type="hidden" name="description" value="${elem.getDescription()}">--%>
<%--                                            <input type="submit" name="button" value="<fmt:message key="estate.editAdvertisementButton"/>">--%>
<%--                                        </form>--%>
                                        <br/>
                                    </c:otherwise>
                                </c:choose>
                                <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_request_list&apartmentId=${elem.getId()}><fmt:message key="estate.requests"/> </a>
                            </c:when>
                            <c:when test="${elem.getStatus() == rent}">
                                <h5><fmt:message key="estate.rentedMessage"/></h5>
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

<%--        <form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--            <input type="hidden" name="command" value="transition_to_new_estate"/>--%>
<%--            <input type="submit" name="button" value="<fmt:message key="estate.newApartmentButton"/>">--%>
<%--        </form>--%>

<%--        <small class="d-block text-right mt-3">--%>

<%--        </small>--%>
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