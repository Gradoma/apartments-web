<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 28.07.2020
  Time: 0:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" >
</head>
<body>
    <c:import url="admin_header.jsp"/>
    <form class="form-horizontal" action="${pageContext.request.contextPath}/control" method="post">
        <input type="hidden" name="command" value="register_new_admin">
        <fieldset>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="textinput">Admin Login</label>
                <div class="col-md-4">
                    <input name="login" type="text" class="form-control input-md" required="" width="50" pattern="^[\p{Digit}\p{Alpha}_-]{3,15}$">
                </div>
                <c:choose>
                    <c:when test="${loginError eq true}">
                        Invalid login(3-15 characters)
                    </c:when>
                    <c:when test="${uniqLoginError eq true}">
                        User with this login already exist
                    </c:when>
                </c:choose>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="passwordinput">Password</label>
                <div class="col-md-4">
                    <input id="passwordinput" name="password" type="password" class="form-control input-md" required="" width="100" pattern="^.{5,45}$">
                </div>
                <c:choose>
                    <c:when test="${passError eq true}">
                        Invalid password(5-45 characters)
                    </c:when>
                </c:choose>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="textinput">Email</label>
                <div class="col-md-4">
                    <input id="textinput" name="email" type="text" class="form-control input-md" required="" width="100" pattern="[^@ \t\r\n]+@[^@ \t\r\n]+\.[^@ \t\r\n]+">

                </div>
                <c:choose>
                    <c:when test="${emailError eq true}">
                        Incorrect email
                    </c:when>
                </c:choose>
            </div>

            <!-- Button -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="singlebutton"></label>
                <div class="col-md-4">
                    <button id="singlebutton" name="singlebutton" class="btn btn-success">Register New Admin</button>
                </div>
            </div>

        </fieldset>
    </form>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <!-- Подключаем Bootstrap JS -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
