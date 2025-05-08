
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Top Navigation</title>
    </head>
    <body>
        <div style="background-color:#333; padding:10px;">
            <a href="Login" style="color:white; margin-right:20px;">Login</a>
            <a href="Profile" style="color:white; margin-right:20px;">Profile</a>
            <a href="HomeFeed" style="color:white; margin-right:20px;">Home Feed</a>
            
            <form action="SearchUser" method="GET" style="display:inline;">
                <input type="text" name="username" placeholder="Search for a user..." required style="display:inline; padding:5px;">
                <button type="submit" style="display:inline; padding:5px 10px; cursor:pointer;">Search</button>
            </form>
        </div>
    </body>
</html>
