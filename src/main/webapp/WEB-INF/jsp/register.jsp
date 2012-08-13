<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<% if (null != request.getAttribute("curUserName")) { %>
    <script type="text/javascript">
        window.location = "/tweet"
    </script>
<%}%>
<div class="container">
    <div class="row">
        <div class="span6 offset3"/ >
            <form class="form-horizontal well" id="form">
                <fieldset>
                    <legend>Login</legend>
                    <div class="control-group">
                        <label class='control-label' for="email">
                            Email
                        </label>
                        <div class="controls">
                            <input type="text" name="email" id="email"></br>
                        </div>
                    </div>
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
                        <button class="btn" onclick="return register();">Submit</button>
                        <a class="btn" href="/auth/login" id="back">Back to Login Page</a>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    document.title = "TwiMini: Register";
    function userAlert(data) {
        alert(data);
    }
    function register() {
        $.post("/auth/register",$("#form").serialize(),function(data){
            if(data==0) {
                alert("Successfully registerd");
                window.location = "/auth/login"
            }
            if(data==1) {
                userAlert("Email already exists");
            }
            if(data==2) {
                userAlert("Username already exists");
            }
        });
        return false;
    }
</script>
<jsp:include page="tail.jsp"/>
