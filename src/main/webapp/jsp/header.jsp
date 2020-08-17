<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="en" value="en"/>
<c:set var="language" value="${not empty locale ? locale : en}"/>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>Header</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <style>
        .user_img {
            border-radius: 50%;
        }
        .dropbtn {
            background-color: #4CAF50;
            color: white;
            padding: 16px;
            font-size: 16px;
            border: none;
        }

        .dropdown {
            position: relative;
            display: inline-block;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f1f1f1;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }

        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }

        .dropdown-content a:hover {background-color: #ddd;}

        .dropdown:hover .dropdown-content {display: block;}

        .dropdown:hover .dropbtn {background-color: #3e8e41;}
    </style>
    <link rel="icon" href="../../../../favicon.ico">

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/bootstrap/css/offcanvas.css" rel="stylesheet">
</head>

<body class="bg-light" style="padding-top: 0px;">

<nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-success">
    <a class="navbar-brand mr-auto mr-lg-0" href="#">Gradomski apartment project</a>
    <button class="navbar-toggler p-0 border-0" type="button" data-toggle="offcanvas">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="navbar-collapse offcanvas-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active" style="width: 650px;">

            </li>
            <li class="nav-item">
                <form action="${pageContext.request.contextPath}/control">
                    <select name="language" onchange="submit()">
                        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
                    </select>
                </form>
            </li>
            <c:if test="${not empty user}">
                <li class="nav-item">
                    <img src="data:image/jpg;base64,${user.getPhotoBase64()}" width="70" height="70" class="user_img">
                </li>
                <li class="nav-item dropdown">
                    <div class="dropdown">
                        <button class="dropbtn">${user.getFirstName()} ${user.getLastName()}</button>
                        <div class="dropdown-content">
                            <a href="http://localhost:8080/apartments_web_war/control?command=transition_to_settings&login=${user.getLoginName()}"><fmt:message key="main.settingsButton"/></a>
                            <a href="http://localhost:8080/apartments_web_war/control?command=log_out"><fmt:message key="main.logoutButton"/></a>
                        </div>
                    </div>
                </li>
            </c:if>

        </ul>
        <form class="form-inline my-2 my-lg-0">


        </form>
    </div>
</nav>

<c:if test="${not empty user}">
    <div class="nav-scroller bg-white box-shadow">
        <nav class="nav nav-underline">
<%--            <a class="nav-link" href="#">Dashboard</a>--%>
<%--            <a class="nav-link" href="#">--%>
<%--                Friends--%>
<%--                <span class="badge badge-pill bg-light align-text-bottom">27</span>--%>
<%--            </a>--%>
<%--            <a class="nav-link" href="#">Explore</a>--%>
<%--            <a class="nav-link" href="#">Suggestions</a>--%>
<%--            <a class="nav-link" href="#">Link</a>--%>
<%--            <a class="nav-link" href="#">Link</a>--%>
<%--            <a class="nav-link" href="#">Link</a>--%>
<%--            <a class="nav-link" href="#">Link</a>--%>
<%--            <a class="nav-link" href="#">Link</a>--%>

            <a class="nav-link" href="http://localhost:8080/apartments_web_war/control?command=transition_to_user_page">
                <fmt:message key="header.home"/>
            </a>
            <a class="nav-link" href="http://localhost:8080/apartments_web_war/control?command=transition_to_estate">
                <fmt:message key="main.estateButton"/>

            </a>
            <a class="nav-link" href="http://localhost:8080/apartments_web_war/control?command=transition_to_my_rent">
                <fmt:message key="main.myRentButton"/>
            </a>
        </nav>
    </div>
</c:if>



<main role="main" class="container">





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

<%--<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-success" style="position: static;">--%>
<%--    <a class="navbar-brand mr-auto mr-lg-0" >Gradomski apartment project</a>--%>
<%--    <button class="navbar-toggler p-0 border-0" type="button" data-toggle="offcanvas">--%>
<%--        <span class="navbar-toggler-icon"></span>--%>
<%--    </button>--%>

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
<%--                <img src="data:image/jpg;base64,${user.getPhotoBase64()}" width="50" height="50" class="user_img">--%>
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
<%--                    &lt;%&ndash;                    <span class="badge badge-pill bg-light align-text-bottom">27</span>--%>
<%--            </a>--%>
<%--            <a class="nav-link" href="http://localhost:8080/apartments_web_war/control?command=transition_to_my_rent">--%>
<%--                <fmt:message key="main.myRentButton"/>--%>
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



<%--<body >--%>
<%--<h2>Gradomski apartment project</h2>--%>
<%--    <form action="${pageContext.demand.contextPath}/control">--%>
<%--        <select name="language" onchange="submit()">--%>
<%--            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>--%>
<%--            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>--%>
<%--        </select>--%>
<%--    </form>--%>
<%--    <c:if test="${not empty user}">--%>
<%--        <form name="Simple" action="${pageContext.demand.contextPath}/control" method="get">--%>
<%--            <input type="hidden" name="command" value="transition_to_user_page"/>--%>
<%--            <input type="submit" name="button" value="<fmt:message key="header.home"/>">--%>
<%--        </form>--%>
<%--    </c:if>--%>
<%--<hr/>--%>
</body>
</html>

