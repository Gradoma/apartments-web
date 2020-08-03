<%--
  Created by IntelliJ IDEA.
  User: TTN
  Date: 27.07.2020
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.session.getAttribute('locale')}"  />
<fmt:setBundle basename="prop.pagecontent" />
<html>
<head>
    <title>Admin Header</title>
</head>
<body>
    <table>
        <tr>
            <td>
                <h2>Gradomski apartment project</h2>
            </td>
            <td>
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_user_list>Users</a>
            </td>
            <td>
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_apartment_list>Apartments</a>
            </td>
            <td>
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_demand_list>Demands</a>
            </td>
            <td>
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_advertisement_list>Advertisements</a>
            </td>
            <td>
                <a href=http://localhost:8080/apartments_web_war/control?command=admin_to_new_admin_form>Create new admin</a>
<%--                <form action="${pageContext.demand.contextPath}/control" method="get">--%>
<%--                    <input type="hidden" name="command" value="create_new_admin"/>--%>
<%--                    <input type="submit" name="button" value="Create new admin">--%>
<%--                </form>--%>
            </td>
            <td>
                <form action="${pageContext.demand.contextPath}/control" method="get">
                    <input type="hidden" name="command" value="log_out"/>
                    <input type="submit" name="button" value="<fmt:message key="main.logoutButton"/>">
                </form>
            </td>
        </tr>
    </table>
    <hr/>
</body>
</html>
