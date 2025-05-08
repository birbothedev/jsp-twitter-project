<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
    </head>
    <body>
        <jsp:include page="topNav.jsp" />
        <h2>${message}</h2>
<!--        <h2>${user.username}</h2>-->
        <h2>${profileUsername}</h2>
        <c:if test="${base64ProfileImage != null}">
            <img src="data:image/jpeg;base64,${base64ProfileImage}" alt="Profile Picture" />
        </c:if>

        <h3>Upload a Profile Picture</h3>
        <form action="Upload" method="post" enctype="multipart/form-data">
            <div id="data">
                <input type="file" accept="image/*" name="file">
            </div>
            <div id="buttons">
                <label>&nbsp;</label>
                <input type="submit" value="Upload"><br>
            </div>
        </form>
        <c:choose>
            <c:when test="${isFollowing}">
                <form action="Profile" method="post">
                    <input type="hidden" name="action" value="unfollow">
                    <input type="hidden" name="username" value="${profileUsername}">
                    <input type="submit" value="Unfollow">
                </form>
            </c:when>
            <c:otherwise>
                <form action="Profile" method="post">
                    <input type="hidden" name="action" value="follow">
                    <input type="hidden" name="username" value="${profileUsername}">
                    <input type="submit" value="Follow">
                </form>
            </c:otherwise>
        </c:choose>
        <table>
            <c:forEach var="post" items="${profilePosts}">
                <tr>
                    <td><strong><c:out value="${post.username}"/></strong></td>
                </tr>
                <tr>
                    <td><c:out value="${post.text}"/></td>
                </tr>
                <tr>
                    <td><c:out value="${post.timestamp}"/></td>
                </tr>
                <tr>
                    <td>        
                        <c:if test="${post.base64postImage != null}">
                            <img src="data:image/jpeg;base64,${post.base64postImage}" alt="Post Image" />
                        </c:if>
                    </td>
                </tr>
                <tr><td colspan="2"><hr></td></tr> 
            </c:forEach>
        </table>
    </body>
</html>
