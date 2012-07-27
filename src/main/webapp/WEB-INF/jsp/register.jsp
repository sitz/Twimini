<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<c:if test="${not empty sessionScope.userName}">
    <script type="text/javascript">
        window.location = "/tweet"
    </script>
</c:if>

<c:if test="${not empty message}">
    ${message}</br>
</c:if>

Login:
<form action="/auth/register" method="post">
    email : <input type="text" name="email"></br>
    username: <input type="text" name="username"></br>
    password: <input type="password" name="password"></br>
    <input type="submit">
</form>
<jsp:include page="tail.jsp"/>
