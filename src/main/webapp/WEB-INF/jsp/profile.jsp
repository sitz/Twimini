<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<div class="container">
    <div class="offset2 span8 well" />
        <ul class="nav nav-tabs" id="myTab">
            <li class="active"><a href="#password" data-toggle="tab">Change Password</a></li>
            <li><a href="#profilepic" data-toggle="tab">Change Profile Picture</a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="password">
                <form class="form-horizontal">
                    <fieldset>
                        <legend>Change Password</legend>
                        <div class="control-group">
                        <label class="control-label" for="input01">New Password</label>
                            <div class="controls">
                                <input type="text" class="input-xlarge" id="input01">
                                <p class="help-block">Use a secure password</p>
                            </div>
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

</script>
<jsp:include page="tail.jsp"/>
