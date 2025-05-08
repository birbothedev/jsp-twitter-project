<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="topNav.jsp" />
        <h2>${message}</h2>
        <h2>Users</h2>
        <table>
            <tr>
                <th>Id</th>
                <th>Username</th>
                <th>Password Hash</th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.password}"/></td>
                </tr>
            </c:forEach>
        </table>
        
        </br>
        <h2>Update User</h2>
        <form action="YapSpace" method="post">
<!--            change username to be unique so you dont have to use UID-->
            <label>UID:</label>
            <input type="text" name="id"/><br>
            <label>Username:</label>
            <input type="text" name="username"/><br>
            <label>Password:</label>
            <input type="password" name="password"/><br>
            </br>
<!--            <label>Confirm Password:</label>
            <input type="password" name="confirm_password"/><br>-->
            </br>
            <input type="hidden" name="action" value="updateUser"/>
            <input type="submit" value="Update User"/><br>
        </form>
        </br>
        
        </br>
        <h2>Delete User</h2>
        <form action="YapSpace" method="post">
<!--            change username to be unique so you dont have to use UID-->
            <label>UID:</label>
            <input type="text" name="id"/><br>
            </br>
            <input type="hidden" name="action" value="deleteUser"/>
            <input type="submit" value="Delete User"/><br>
        </form>
        </br>
        
    </body>
</html>
