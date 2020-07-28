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
<html>
<head>
    <title>User Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
<body>
<c:import url="header.jsp"/>
<img src="data:image/jpg;base64,${user.getPhotoBase64()}" width="100" height="100">
<h3>${user.getFirstName()} ${user.getLastName()}</h3><br/>
<form name="Simple" action="${pageContext.request.contextPath}/control" method="get">
    <input type="hidden" name="command" value="transition_to_settings"/>
    <input type="hidden" name="login" value="${user.getLoginName()}"/>
    <input type="submit" name="button" value=<fmt:message key="main.settingsButton"/>>
</form>
<form action="${pageContext.request.contextPath}/control" method="get">
    <input type="hidden" name="command" value="transition_to_estate"/>
    <input type="submit" name="button" value="<fmt:message key="main.estateButton"/>">
</form>
<form action="${pageContext.request.contextPath}/control" method="get">
    <input type="hidden" name="command" value="transition_to_my_rent"/>
    <input type="submit" name="button" value="<fmt:message key="main.myRequestsButton"/>">
</form>
<form action="${pageContext.request.contextPath}/control" method="get">
    <input type="hidden" name="command" value="log_out"/>
    <input type="submit" name="button" value="<fmt:message key="main.logoutButton"/>">
</form>
<div class="pagination">
    <c:forEach var="page" items="${pagesAmount}" varStatus="status">
        <a class="${status.count == currentPage ? 'active' : ''}" href="http://localhost:8080/apartments_web_war/control?command=next_advertisement&page=${status.count}"><c:out value="${status.count}" /></a>
    </c:forEach>
</div><br/>
<table>
    <c:forEach var="ad" items="${advertisementList}" varStatus="status" begin="${ not empty firstAdvertisement ? firstAdvertisement : 0}"
               end="${ not empty lastAdvertisement ? lastAdvertisement : 4}">
        <c:set var="apartment" value="${apartmentMap[ad.getId()]}"/>
        <c:set var="photoMap" value="${apartment.getUnmodifiablePhotoMap()}"/>
        <tr>
            <td>
                <c:choose>
                <c:when test="${photoMap.isEmpty()}">
                    No photo
                </c:when>
                <c:otherwise>
                    <c:set var="entry" value="${photoMap.entrySet().iterator().next()}"/>
                    <img src="data:image/jpg;base64,${entry.value}" width="120" height="120">
            </c:otherwise>
            </c:choose>
            </td>
            <td>
                <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_advertisement&id=${ad.getId()}>${ad.getTitle()}</a><br/>
                <strong>${apartment.getAddress()}</strong><br/>
                ${apartment.getRegion()}, ${apartment.getCity()}
            </td>
            <td><c:out value="${ad.getPrice() }" /></td>
        </tr>
        <tr>
            <td><small><ctg:dateTime dateTimeValue="${ad.getCreationDate()}"/></small></td>
<%--            <td><small><c:out value="${ad.getCreationDate() }" /></small></td>--%>
        </tr>
    </c:forEach>
</table><br/>
<div class="pagination">
    <c:forEach var="page" items="${pagesAmount}" varStatus="status">
        <a class="${status.count == currentPage ? 'active' : ''}" href="http://localhost:8080/apartments_web_war/control?command=next_advertisement&page=${status.count}"><c:out value="${ status.count }" /></a>
    </c:forEach>
</div>
<c:import url="footer.jsp"/>
</body>
<%--<!DOCTYPE html>--%>
<%--<head>--%>
<%--    <meta charset="utf-8">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">--%>
<%--    <meta name="description" content="">--%>
<%--    <meta name="author" content="">--%>
<%--    <link rel="icon" href="../../../../favicon.ico">--%>

<%--    <title>Offcanvas template for Bootstrap</title>--%>

<%--    <!-- Bootstrap core CSS -->--%>
<%--    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">--%>

<%--    <!-- Custom styles for this template -->--%>
<%--    <link href="bootstrap/css/offcanvas.css" rel="stylesheet">--%>
<%--</head>--%>
<%--<body class="bg-light">--%>
<%--<c:import url="header.jsp"/>--%>
<%--<main role="main" class="container">--%>
<%--    <div class="d-flex align-items-center p-3 my-3 text-white-50 bg-purple rounded box-shadow" style="opacity: 0.6; background-color: rgb(66, 194, 134);"><input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">--%>

<%--        <div class="lh-100">--%>


<%--        </div>--%>
<%--        <button class="btn my-2 my-sm-0 btn-success" type="submit">Search</button></div>--%>

<%--    <div class="my-3 p-3 bg-white rounded box-shadow">--%>

<%--        <div class="media text-muted pt-3"><img class="mr-3" src="https://getbootstrap.com/assets/brand/bootstrap-outline.svg" alt="" width="100" height="100">--%>

<%--            <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">--%>
<%--                <strong class="d-block text-gray-dark">@username</strong>--%>
<%--                Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.--%>
<%--            </p>--%>
<%--        </div>--%>
<%--        <div class="media text-muted pt-3"><img class="mr-3" src="https://getbootstrap.com/assets/brand/bootstrap-outline.svg" alt="" width="100" height="100">--%>

<%--            <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">--%>
<%--                <strong class="d-block text-gray-dark">@username</strong>--%>
<%--                Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.--%>
<%--            </p>--%>
<%--        </div>--%>
<%--        <div class="media text-muted pt-3">--%>
<%--            <img data-src="holder.js/32x32?theme=thumb&amp;bg=6f42c1&amp;fg=6f42c1&amp;size=1" alt="32x32" class="mr-2 rounded" src="data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%2232%22%20height%3D%2232%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%2032%2032%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_1738c50203d%20text%20%7B%20fill%3A%236f42c1%3Bfont-weight%3Abold%3Bfont-family%3AArial%2C%20Helvetica%2C%20Open%20Sans%2C%20sans-serif%2C%20monospace%3Bfont-size%3A2pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_1738c50203d%22%3E%3Crect%20width%3D%2232%22%20height%3D%2232%22%20fill%3D%22%236f42c1%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%2211.828125%22%20y%3D%2216.965625%22%3E32x32%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E" data-holder-rendered="true" style="width: 32px; height: 32px;">--%>
<%--            <p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">--%>
<%--                <strong class="d-block text-gray-dark">@username</strong>--%>
<%--                Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.--%>
<%--            </p>--%>
<%--        </div>--%>

<%--    </div>--%>


<%--</main>--%>

<%--<!-- Bootstrap core JavaScript--%>
<%--================================================== -->--%>
<%--<!-- Placed at the end of the document so the pages load faster -->--%>
<%--<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>--%>
<%--<script src="bootstrap/js/vendor/popper.min.js"></script>--%>
<%--<script src="bootstrap/js/bootstrap.min.js"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/holderjs@2.9.4/holder.js"></script>--%>
<%--<script src="bootstrap/js/offcanvas.js"></script>--%>


<%--<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" preserveAspectRatio="none" style="display: none; visibility: hidden; position: absolute; top: -100%; left: -100%;"><defs><style type="text/css"></style></defs>--%>
<%--    <text x="0" y="2" style="font-weight:bold;font-size:2pt;font-family:Arial, Helvetica, Open Sans, sans-serif">32x32</text></svg>--%>
<%--</body>--%>
</html>
