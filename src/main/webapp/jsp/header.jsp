<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="en" value="en"/>
<c:set var="language" value="${not empty sessionScope.lang ? sessionScope.lang : en}"/>
<html>
<head>
    <title>Header</title>
</head>
<body>
    <h2>Gradomski apartment project</h2>
    <form>
        <select name="language" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
        </select>
    </form>
</body>
</html>
