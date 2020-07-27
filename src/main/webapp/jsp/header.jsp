<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="en" value="en"/>
<c:set var="language" value="${not empty locale ? locale : en}"/>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
<%--    <title>Header</title>--%>
<%--    <meta charset="utf-8">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">--%>
<%--    <meta name="description" content="">--%>
<%--    <meta name="author" content="">--%>
<%--    <style>--%>
<%--        img {--%>
<%--            border-radius: 50%;--%>
<%--        }--%>
<%--    </style>--%>
<%--    <link rel="icon" href="../../../../favicon.ico">--%>

<%--    <!-- Bootstrap core CSS -->--%>
<%--    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">--%>

<%--    <!-- Custom styles for this template -->--%>
<%--    <link href="bootstrap/css/offcanvas.css" rel="stylesheet">--%>
</head>

<%--<body class="bg-light">--%>

<%--<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-success" style="position: static;">--%>
<%--    <a class="navbar-brand mr-auto mr-lg-0" >Gradomski apartment project</a>--%>
<%--&lt;%&ndash;    <button class="navbar-toggler p-0 border-0" type="button" data-toggle="offcanvas">&ndash;%&gt;--%>
<%--&lt;%&ndash;        <span class="navbar-toggler-icon"></span>&ndash;%&gt;--%>
<%--&lt;%&ndash;    </button>&ndash;%&gt;--%>

<%--    <div class="navbar-collapse offcanvas-collapse" id="navbarsExampleDefault">--%>
<%--        <ul class="navbar-nav mr-auto">--%>
<%--            <li class="nav-item active">--%>

<%--            </li>--%>
<%--            <li class="nav-item">--%>

<%--            </li>--%>
<%--            <li class="nav-item">--%>
<%--                <form action="control">--%>
<%--                    <select name="language" onchange="submit()">--%>
<%--                        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>--%>
<%--                        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>--%>
<%--                    </select>--%>
<%--                </form>--%>
<%--            </li>--%>
<%--            <li class="nav-item">--%>
<%--            <c:if test="${not empty user}">--%>
<%--                <img src="data:image/jpg;base64,${user.getPhotoBase64()}" width="50" height="50">--%>
<%--                ${user.getFirstName()} ${user.getLastName()}--%>
<%--                <form action="control" method="get">--%>
<%--                    <input type="hidden" name="command" value="log_out"/>--%>
<%--                    <input type="submit" name="button" value="<fmt:message key="main.logoutButton"/>">--%>
<%--                </form>--%>
<%--            </c:if>--%>
<%--            </li>--%>
<%--        </ul>--%>
<%--    </div>--%>
<%--</nav>--%>
<%--<c:if test="${not empty user}">--%>
<%--    <div class="nav-scroller bg-white box-shadow">--%>
<%--        <nav class="nav nav-underline">--%>
<%--            <a class="nav-link active" href="http://localhost:8080/apartments_web_war/control?command=transition_to_user_page">--%>
<%--                <fmt:message key="header.home"/>--%>
<%--            </a>--%>
<%--            <a class="nav-link" href="http://localhost:8080/apartments_web_war/control?command=transition_to_estate">--%>
<%--                <fmt:message key="main.estateButton"/>--%>
<%--                    &lt;%&ndash;                    <span class="badge badge-pill bg-light align-text-bottom">27</span>todo add on requests&ndash;%&gt;--%>
<%--            </a>--%>
<%--            <a class="nav-link" href="http://localhost:8080/apartments_web_war/control?command=transition_to_my_rent">--%>
<%--                <fmt:message key="main.myRequestsButton"/>--%>
<%--            </a>--%>
<%--        </nav>--%>
<%--    </div>--%>
<%--</c:if>--%>
<%--<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>--%>
<%--<script src="bootstrap/js/vendor/popper.min.js"></script>--%>
<%--<script src="bootstrap/js/bootstrap.min.js"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/holderjs@2.9.4/holder.js"></script>--%>
<%--<script src="bootstrap/js/offcanvas.js"></script>--%>


<%--<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 32 32" preserveAspectRatio="none" style="display: none; visibility: hidden; position: absolute; top: -100%; left: -100%;"><defs><style type="text/css"></style></defs>--%>
<%--    <text x="0" y="2" style="font-weight:bold;font-size:2pt;font-family:Arial, Helvetica, Open Sans, sans-serif">32x32</text></svg>--%>
<body >
<h2>Gradomski apartment project</h2>
    <form action="${pageContext.request.contextPath}/control">
        <select name="language" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
        </select>
    </form>
    <c:if test="${not empty user}">
        <form name="Simple" action="${pageContext.request.contextPath}/control" method="get">
            <input type="hidden" name="command" value="transition_to_user_page"/>
            <input type="submit" name="button" value="<fmt:message key="header.home"/>">
        </form>
    </c:if>
<hr/>
</body>
</html>

