<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 17.06.2020
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" >
</head>
<body>
<c:import url="header.jsp"/>
<form class="form-horizontal" action="${pageContext.request.contextPath}/control" method="post">
    <input type="hidden" name="command" value="sign_up">
    <fieldset>

        <!-- Form Name -->
        <legend><fmt:message key="legend.signUp"/> </legend>
        <small><fmt:message key="message.required"/> </small>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="textinput"><fmt:message key="label.login"/>*</label>
            <div class="col-md-4">
                <input name="login" type="text" class="form-control input-md" required="" width="50" pattern="^[\p{Digit}\p{Alpha}_-]{3,15}$">
            </div>
            <c:choose>
                <c:when test="${loginError eq true}">
                    <fmt:message key="signUp.loginErrorMessage"/>
                </c:when>
                <c:when test="${uniqLoginError eq true}">
                    <fmt:message key="signUp.uniqLoginErrorMessage"/>
                </c:when>
            </c:choose>
        </div>

        <!-- Password input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="passwordinput"><fmt:message key="label.password"/>*</label>
            <div class="col-md-4">
                <input id="passwordinput" name="password" type="password" class="form-control input-md" required="" width="100" pattern="^.{5,45}$">
            </div>
            <c:choose>
                <c:when test="${passError eq true}">
                    <fmt:message key="signUp.passErrorMessage"/>
                </c:when>
            </c:choose>
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="textinput"><fmt:message key="label.mail"/>*</label>
            <div class="col-md-4">
                <input id="textinput" name="email" type="text" class="form-control input-md" required="" width="100" pattern="[^@ \t\r\n]+@[^@ \t\r\n]+\.[^@ \t\r\n]+">

            </div>
            <c:choose>
                <c:when test="${emailError eq true}">
                    <fmt:message key="signUp.emailErrorMessage"/>
                </c:when>
            </c:choose>
        </div>

        <!-- Button -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="singlebutton"></label>
            <div class="col-md-4">
                <button id="singlebutton" name="singlebutton" class="btn btn-success"><fmt:message key="label.registerButton"/></button>
            </div>
        </div>

    </fieldset>
</form>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<c:import url="footer.jsp"/>
</body>
</html>
