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
<form class="form-horizontal" action="control" method="post">
    <input type="hidden" name="command" value="sign_up">
    <fieldset>

        <!-- Form Name -->
        <legend><fmt:message key="legend.signUp"/> </legend>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="textinput"><fmt:message key="label.login"/></label>
            <div class="col-md-4">
                <input name="login" type="text" class="form-control input-md" required="" width="50" pattern="^[a-zA-Z0-9_-]{3,15}$">
            </div>
            ${loginErrorMessage}
        </div>

        <!-- Password input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="passwordinput"><fmt:message key="label.password"/></label>
            <div class="col-md-4">
                <input id="passwordinput" name="password" type="password" class="form-control input-md" required="" width="100" pattern="^.{5,}$">
            </div>
            ${passErrorMessage}
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="textinput"><fmt:message key="label.mail"/></label>
            <div class="col-md-4">
                <input id="textinput" name="email" type="text" class="form-control input-md" required="" width="100" pattern="[^@ \t\r\n]+@[^@ \t\r\n]+\.[^@ \t\r\n]+">

            </div>
            ${emailErrorMessage}
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
<!-- Подключаем Bootstrap JS -->
<script src="bootstrap/js/bootstrap.min.js"></script>
<c:import url="footer.jsp"/>
</body>
</html>
