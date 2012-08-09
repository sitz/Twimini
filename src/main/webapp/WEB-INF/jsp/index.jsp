<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<% if (null != request.getAttribute("userName")) { %>
    <script type="text/javascript">
        window.location = "/tweet"
    </script>
<% } %>
<div class="container">
    <div class="row">
        <div class="span6 offset3" />
            <form class="form-horizontal well" id="form">
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
                            <button class="btn" onclick="return sub();" >Submit</button>
                        </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    function sub(){
        $.post("/auth/login",$("#form").serialize(),function(data){
            if(data==1) {
                alert("Login Failed");
            }
            else {
                window.location = "/tweet"
            }
        });
        return false;
    }

</script>
<jsp:include page="tail.jsp"/>
