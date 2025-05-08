<%-- 
    Document   : login
    Created on : Apr 23, 2025, 3:37:43 PM
    Author     : ginaf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <jsp:include page="topNav.jsp" />
        <h2>${message}</h2>
        <h2>Login</h2>
        <form action="Login" method="post">
            <label>Username:</label>
            <input type="text" name="username"/><br>
            <label>Password:</label>
            <input type="password" name="password"/><br>
            </br>
            <input type="hidden" name="action" value="login"/>
            <input type="submit" value="Login"/><br>
        </form>
        </br>
        </br>
        <h2>Create User</h2>
        <form action="Login" method="post">
            <label>Username:</label>
            <input type="text" name="username"/><br>
            <label>Password:</label>
            <input type="password" name="password"/><br>
            </br>
<!--                <label>Confirm Password:</label>
            <input type="password" name="confirm_password"/><br>-->
            </br>
            <input type="hidden" name="action" value="register"/>
            <input type="submit" value="Sign Up"/><br>
        </form>
        </br>
    </body>
</html>
