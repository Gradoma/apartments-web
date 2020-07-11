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
</head>
<body>
    <h2>Gradomski apartment project</h2>
    <h3>Locale : ${pageContext.session.getAttribute('locale')}<h3/>
        <h3>Language : ${language}<h3/>
    <form action="control">
        <select name="language" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
        </select>
    </form>
    <c:if test="${not empty user}">
        <form name="Simple" action="control" method="get">
            <input type="hidden" name="command" value="transition_to_user_page"/>
            <input type="submit" name="button" value="<fmt:message key="header.home"/>">
        </form>
    </c:if>
<hr/>
</body>
</html>
