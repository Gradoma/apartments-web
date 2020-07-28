<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 15.06.2020
  Time: 6:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/custom/custom.tld" %>
<c:set var="en" value="en"/>
<c:set var="language" value="${not empty pageContext.session.getAttribute('locale') ? pageContext.session.getAttribute('locale') : en}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="prop.pagecontent" />
<%--<html>--%>
<%--<head>--%>
<%--    <title><fmt:message key="label.title"/></title>--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
<%--    <style>--%>
<%--        .pagination a {--%>
<%--            color: black;--%>
<%--            float: left;--%>
<%--            padding: 8px 16px;--%>
<%--            text-decoration: none;--%>
<%--            transition: background-color .3s;--%>
<%--        }--%>

<%--        .pagination a.active {--%>
<%--            background-color: dodgerblue;--%>
<%--            color: white;--%>
<%--        }--%>

<%--        .pagination a:hover:not(.active) {background-color: #ddd;}--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--    <c:import url="header.jsp"/>--%>
<%--    <h3><fmt:message key="label.body"/></h3>--%>
<%--    <br/> ${errorAccess} <br/>--%>
<%--<form name="Simple" action="${pageContext.request.contextPath}/control" method="post">--%>
<%--    <input type="hidden" name="command" value="sign_in"/>--%>
<%--    <input required name="login" pattern="^[\p{Digit}\p{Alpha}_-]{3,15}$" placeholder=<fmt:message key="label.login"/>><br/>--%>
<%--    <input required name="password" pattern="^.{5,45}$" placeholder=<fmt:message key="label.password"/>>--%>
<%--    <c:choose>--%>
<%--        <c:when test="${errorSignInPass eq true}">--%>
<%--            <fmt:message key="signIn.errorSignInMessage"/>--%>
<%--        </c:when>--%>
<%--    </c:choose>--%>
<%--    <input type="submit" name="button" value="<fmt:message key="label.submitButton"/>">--%>
<%--</form>--%>
<%--<form action="${pageContext.request.contextPath}/control" method="get">--%>
<%--    <input type="hidden" name="command" value="transition_to_sign_up"/>--%>
<%--    <input type="submit" name="button" value="<fmt:message key="label.transitionButton"/>">--%>
<%--</form>--%>
<%--    <div class="pagination">--%>
<%--        <c:forEach var="page" varStatus="status" begin="1" end="${pagesAmount}">--%>
<%--            <a class="${status.count == currentPage ? 'active' : ''}" href="http://localhost:8080/apartments_web_war/control?command=next_advertisement&page=${status.count}"><c:out value="${status.count}" /></a>--%>
<%--        </c:forEach>--%>
<%--    </div><br/>--%>
<%--    <table>--%>
<%--        <c:forEach var="ad" items="${advertisementList}" varStatus="status" begin="${ not empty firstAdvertisement ? firstAdvertisement : 0}"--%>
<%--                   end="${ not empty lastAdvertisement ? lastAdvertisement : 4}">--%>
<%--            <c:set var="apartment" value="${apartmentMap[ad.getId()]}"/>--%>
<%--            <c:set var="photoMap" value="${apartment.getUnmodifiablePhotoMap()}"/>--%>
<%--            <tr>--%>
<%--                <td>--%>
<%--                    <c:choose>--%>
<%--                        <c:when test="${photoMap.isEmpty()}">--%>
<%--                            No photo--%>
<%--                        </c:when>--%>
<%--                        <c:otherwise>--%>
<%--                            <c:set var="entry" value="${photoMap.entrySet().iterator().next()}"/>--%>
<%--                            <img src="data:image/jpg;base64,${entry.value}" width="120" height="120">--%>
<%--                        </c:otherwise>--%>
<%--                    </c:choose>--%>
<%--                </td>--%>
<%--                <td>--%>
<%--                    <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_advertisement&id=${ad.getId()}>${ad.getTitle()}</a><br/>--%>
<%--                    <strong>${apartment.getAddress()}</strong><br/>--%>
<%--                    ${apartment.getRegion()}, ${apartment.getCity()}--%>
<%--                </td>--%>
<%--                <td><c:out value="${ad.getPrice() }" /></td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td><small><ctg:dateTime dateTimeValue="${ad.getCreationDate()}"/></small></td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
<%--    </table><br/>--%>
<%--    <div class="pagination">--%>
<%--        <c:forEach var="page" varStatus="status" begin="1" end="${pagesAmount}">--%>
<%--            <a class="${status.count == currentPage ? 'active' : ''}" href="http://localhost:8080/apartments_web_war/control?command=next_advertisement&page=${status.count}"><c:out value="${ status.count }" /></a>--%>
<%--        </c:forEach>--%>
<%--    </div>--%>
<%--    <c:import url="footer.jsp"/>--%>
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
<%--<c:import url="header.jsp"/>--%>
<%--<form name="Simple" action="control" method="post">--%>
<%--        <input type="hidden" name="command" value="sign_in"/>--%>
<%--        <input required name="login" pattern="^[\p{Digit}\p{Alpha}_-]{3,15}$" placeholder=<fmt:message key="label.login"/>><br/>--%>
<%--        <input required name="password" pattern="^.{5,45}$" placeholder=<fmt:message key="label.password"/>>--%>
<%--        <c:choose>--%>
<%--            <c:when test="${errorSignInPass eq true}">--%>
<%--                <fmt:message key="signIn.errorSignInMessage"/>--%>
<%--            </c:when>--%>
<%--        </c:choose>--%>
<%--        <input type="submit" name="button" value="<fmt:message key="label.submitButton"/>">--%>
<%--</form>--%>

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

<%--        <div class="media text-muted pt-3 border-bottom border-gray" style=""><img class="mr-3" src="https://getbootstrap.com/assets/brand/bootstrap-outline.svg" alt="" width="250" height="170">--%>

            <c:forEach var="ad" items="${advertisementList}" varStatus="status" begin="${ not empty firstAdvertisement ? firstAdvertisement : 0}"
                       end="${ not empty lastAdvertisement ? lastAdvertisement : 4}">
                <c:set var="apartment" value="${apartmentMap[ad.getId()]}"/>
                <c:set var="photoMap" value="${apartment.getUnmodifiablePhotoMap()}"/>
                <div class="media text-muted pt-3 border-bottom border-gray" style="">
                    <c:choose>
                        <c:when test="${photoMap.isEmpty()}">
                            No photo
                        </c:when>
                        <c:otherwise>
                            <c:set var="entry" value="${photoMap.entrySet().iterator().next()}"/>
                            <img class="mr-3" src="data:image/jpg;base64,${entry.value}" alt="" width="250" height="170">
                        </c:otherwise>
                    </c:choose>
                    <p class="media-body pb-3 mb-0 small lh-125" style="width: 322px;">
                        <strong class="d-block text-gray-dark">
                            <a href=http://localhost:8080/apartments_web_war/control?command=transition_to_advertisement&id=${ad.getId()}>${ad.getTitle()}</a>
                        </strong>
                            ${apartment.getAddress()}, ${apartment.getRegion()}, ${apartment.getCity()} <br/>
                        <small><ctg:dateTime dateTimeValue="${ad.getCreationDate()}"/></small>
                    </p>
                    <p class="media-body pb-3 mb-0 small lh-125" style="border-color: rgb(13, 32, 49); top: auto; left: 30%; right: 30%; width: 50%; min-width: 0%; font-weight: 700; height: 0%; min-height: 0%; max-width: 120px; max-height: 0%;">${ad.getPrice()}</p>
                </div>
<%--        <div class="media text-muted pt-3 border-bottom border-gray" style="">--%>
            </c:forEach>

<%--            <p class="media-body pb-3 mb-0 small lh-125" style="width: 322px;">--%>
<%--                <strong class="d-block text-gray-dark">@username</strong>--%>
<%--                Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.--%>
<%--            </p><p class="media-body pb-3 mb-0 small lh-125" style="border-color: rgb(13, 32, 49); top: auto; left: 30%; right: 30%; width: 50%; min-width: 0%; font-weight: 700; height: 0%; min-height: 0%; max-width: 120px; max-height: 0%;">Price</p>--%>
<%--        </div><div class="media text-muted pt-3 border-bottom border-gray" style=""><img class="mr-3" src="https://getbootstrap.com/assets/brand/bootstrap-outline.svg" alt="" width="250" height="170">--%>

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
<script src="bootstrap/js/vendor/popper.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/holderjs@2.9.4/holder.js"></script>
<script src="bootstrap/js/offcanvas.js"></script>


<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" preserveAspectRatio="none" style="display: none; visibility: hidden; position: absolute; top: -100%; left: -100%;"><defs><style type="text/css"></style></defs>
    <text x="0" y="2" style="font-weight:bold;font-size:2pt;font-family:Arial, Helvetica, Open Sans, sans-serif">32x32</text></svg>
</body>
</html>