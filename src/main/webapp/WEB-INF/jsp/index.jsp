<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<% if (null != request.getAttribute("curUserName")) { %>
    <script type="text/javascript">
        window.location = "/tweet"
    </script>
<% } %>
<div class="container">
    <div class="span7">
        <div class="hero-unit">
            <h1>Welcome</h1>
            <p>
                Twitter is an online social networking service and microblogging service that enables its users to send and read text-based messages of up to 140 characters, known as "tweets".
It was created in March 2006 by Jack Dorsey and launched that July. The service rapidly gained worldwide popularity, with over 500 million active users as of 2012, generating over 340 million tweets daily and handling over 1.6 billion search queries per day.[6][8][9] Since its launch, Twitter has become one of the top 10 most visited websites on the Internet, and has been described as "the SMS of the Internet."[5][10] Unregistered users can read tweets, while registered users can post tweets through the website interface, SMS, or a range of apps for mobile devices.[11]
Twitter Inc. is based in San Francisco, with additional servers and offices in New York City, Boston, and San Antonio.
            </p>
        </div>
    </div>
    <div class="offset1 span3">
        <div class="row">
            <div class=""/ >
                <form class="form-horizontal well" id="formRegister">
                    <fieldset>
                        <legend>Register</legend>
                        <label> Email </label>
                        <input type="text" name="email" id="email">
                        <label> Username </label>
                        <input type="text" name="username" id="username">
                        <label> Password </label>
                        <input type="password" name="password" id="password">
                        <div class="form-actions">
                            <button class="btn" onclick="return register();"></button>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal hide fade well" id="myModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Modal header</h3>
  </div>
  <div class="modal-body">
    <form>
        <label>Username</label>
        <input type="text" id="forgotPassword">
    </form>
  </div>
  <div class="modal-footer">
        <button class="btn btn-primary" onclick='return forgot();'>Save changes</button>
  </div>
</div>
<script type="text/javascript">
    document.title = "TwiMini";

    function login(){
        $.post("/api/auth/login",$("#formLogin").serialize(),function(data){
            if(data=="Error1") {
                alert("Login Failed");
            }
            else {
                window.location = "/tweet"
            }
        });
        return false;
    }
    function userAlert(data) {
        alert(data);
    }
    function register() {
        $.post("/api/auth/register",$("#formRegister").serialize(),function(data){
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
    function forgot() {
        alert("ER");
        $.post('/api/auth/forgot/' + $('#forgotPassword').val(),function(data) {
                alert("DF");
                });
        alert("ER");
        $("#myModal").modal('hide');
        return false;
    }
</script>
<jsp:include page="tail.jsp"/>
