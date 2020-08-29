<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 24.06.2020
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<c:set var="userGender" value="${user.getGender()}"/>
<c:set var="male" value="MALE"/>
<c:set var="female" value="FEMALE"/>
<html>
<head>
    <title>Settings</title>
</head>
<body>
<c:import url="header.jsp"/>
    <c:choose>
        <c:when test="${user.getFirstName() == null}">
            <h2><fmt:message key="setting.greetingMessage"/></h2>
        </c:when>
    </c:choose>
    <img src="data:image/jpg;base64,${user.getPhotoBase64()}" width="100" height="100" class="user_img">
    <c:choose>
        <c:when test="${incorrectType eq true}">
            <h4><fmt:message key="photo.incorrectTypeMessage"/></h4>
        </c:when>
        <c:when test="${emptyFile eq true}">
            <h4><fmt:message key="photo.empty"/></h4>
        </c:when>
    </c:choose>
    <h5><fmt:message key="photo.formats"/> </h5>
    <form action="${pageContext.request.contextPath}/fileController" method="post" enctype="multipart/form-data">
        <input type="file" name="image" height="150">
        <input type="hidden" name="login" value="${user.getLoginName()}"/>
        <input type="hidden" name="page" value="SETTINGS">
        <input type="submit" name="button" value="<fmt:message key="setting.browseButton"/>">
    </form>
    <form name="Simple" action="${pageContext.request.contextPath}/control" method="post">
        <small><fmt:message key="message.required"/> </small><br/>
        <input type="hidden" name="command" value="update_user"/>
        <input type="hidden" name="login" value="${user.getLoginName()}"/>
        <fmt:message key="setting.firstName"/>* : <input required name="firstName" value="${user.getFirstName()}" pattern="^[а-яА-я-]{1,45}$"><br/>
        <c:choose>
            <c:when test="${firstNameError eq true}">
                <fmt:message key="setting.firstNameErrorMessage"/>
            </c:when>
        </c:choose>
        <fmt:message key="setting.lastName"/>* : <input required name="lastName" value="${user.getLastName()}" pattern="^[а-яА-я-]{1,45}$"><br/>
        <c:choose>
            <c:when test="${lastNameError eq true}">
                <fmt:message key="setting.lastNameErrorMessage"/>
            </c:when>
        </c:choose>
        <fmt:message key="setting.gender"/> : <input type="radio" name="gender" value="FEMALE" ${userGender eq female ? 'checked' : ''}/><fmt:message key="setting.female"/>
        <input type="radio" name="gender" value="MALE" ${userGender eq male ? 'checked' : ''}/><fmt:message key="setting.male"/><br/>
        <c:choose>
            <c:when test="${genderErrorMessage eq true}">
                <fmt:message key="setting.genderErrorMessage"/>
            </c:when>
        </c:choose>
        <fmt:message key="setting.phone"/> : <input name="phone" value="${user.getPhone()}" pattern="^[+]?[(]?[0-9]{5}[)]?[-\s]?[0-9]{3}[-\s]?[0-9]{2}[-\s]?[0-9]{2}$"><br/>
        <c:choose>
            <c:when test="${phoneErrorMessage eq true}">
                <fmt:message key="setting.phoneErrorMessage"/>
            </c:when>
        </c:choose>
        <fmt:message key="setting.birthday"/> : <input type="date" name="birthday" value="${user.getBirthday()}">
        <c:choose>
            <c:when test="${birthdayErrorMessage eq true}">
                <fmt:message key="setting.birthdayErrorMessage"/>
            </c:when>
        </c:choose>
        <input type="submit" name="button" value=<fmt:message key="setting.saveButton"/>>
    </form>
<c:import url="footer.jsp"/>
</body>
</html>
