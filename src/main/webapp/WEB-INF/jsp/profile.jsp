<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<div class="container">
    <div class="span12 well" />
        <ul class="nav nav-tabs" id="myTab">
            <li class="active"><a href="#password" data-toggle="tab">Change Password</a></li>
            <li><a href="#profilepic" data-toggle="tab">Change Profile Picture</a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="password">
                <form class="form-horizontal" onsubmit="resetPassword(this);return false;">
                    <div class="alert alert-block alert-error fade" style="display:none;">
                        <button type="button" class="close" onclick='$(this).parent().fadeOut("fast").removeClass("in")'>×</button>
                            Oops. Make sure your both passwords match.
                        </div>
                        <div class="alert alert-block alert-success fade" style="display:none;">
                        <button type="button" class="close" onclick='$(this).parent().fadeOut("fast").removeClass("in")'>×</button>
                            Password Changed.
                        </div>
                    <fieldset>
                        <legend>Change Password</legend>
                        <div class="control-group">
                        <label class="control-label" for="input01">New Password</label>
                            <div class="controls">
                                <input type="password" class="input-xlarge" id="input01">
                                <p class="help-block">Use a secure password</p>
                            </div>
                        </div>
                        <label class="control-label" for="input02">Confirm Password</label>
                            <div class="controls">
                                <input type="password" class="input-xlarge" id="input02">
                            </div>
                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">Change</button>
                        </div>
                    </fieldset>

                </form>
            </div>
            <div class="tab-pane" id="profilepic">
                To change ProfilePic, go to <a href="http://gravatar.com">Gravatar</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function resetPassword(form) {
        var a = $("#input01").val();
        var b = $("#input02").val();
        if(a==b) {
            $.post("/api/user/change/"+a,function(data) {
                $(".alert-success").alert();
                $(".alert-success").fadeIn('fast');
                $(".alert-success").addClass("in");
            });
        }
        else {
            $(".alert-error").alert();
            $(".alert-error").fadeIn('fast');
            $(".alert-error").addClass("in");
        }
    }
</script>
<jsp:include page="tail.jsp"/>
