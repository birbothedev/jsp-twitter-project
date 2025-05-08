<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Feed</title>
    </head>
    <body>
        <jsp:include page="topNav.jsp" />
        <h1>Hello World!</h1>
        <form action="HomeFeed" method="post" enctype="multipart/form-data">
            <label>Post:</label><br>
            <textarea id="postText" name="message" rows="5" cols="40" maxlength="140" placeholder="Type your post here..."></textarea><br><br>
            <input type="file" name="postImage"><br>
            <input type="hidden" name="action" value="addPost"/>
            <input type="submit" value="Post"/><br>
        </form>
        <table>
            <c:forEach var="post" items="${posts}">
                <tr>
                    <td><strong><c:out value="${post.username}"/></strong></td>
                </tr>
                <tr>
                    <td><c:out value="${post.text}"/></td>
                </tr>
                <tr>
                    <td>        
                        <c:if test="${post.base64postImage != null}">
                            <img src="data:image/jpeg;base64,${post.base64postImage}" alt="Post Image" />
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td><c:out value="${post.timestamp}"/></td>
                </tr>
                <tr>
                    <td>
                        <form action="HomeFeed" method="post">
                            <input type="hidden" name="action" value="addLike"/>
                            <input type="hidden" name="postId" value="${post.id}"/>
                            <input type="submit" value="Like"/>
                        </form>
                    </td>
                    <td> 
                        <label>Likes:</label>
                        <td><c:out value="${post.likes}"/></td>
                    </td>
                </tr>
                <tr><td colspan="2"><hr></td></tr> 
            </c:forEach>
        </table>
    </body>
</html>

