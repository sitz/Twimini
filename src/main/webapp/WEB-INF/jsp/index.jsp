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
<div class="container">
    <div class="row">
        <div class="span6 offset3"/ >
            <form class="form-horizontal well" action="/auth/login" method="post">
                <fieldset>
                    <legend>Login</legend>
                        <div class="control-group">
                            <label class='control-label' for="username">
                                Username 
                            </label>
                            <div class="controls">
                                <input type="text" name="username" id="username"></br>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class='control-label' for="password">
                                Password 
                            </label>
                            <div class="controls">
                                <input type="password" name="password" id="password"></br>
                            </div>
                        </div>
                        <div class="form-actions">
                            <a class="btn" href="/auth/register">Register</a>
                            <button type="sumbit" class="btn" >Submit</button>
                        </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
<jsp:include page="tail.jsp"/>
