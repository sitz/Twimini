<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<div class = "container">
    <div class="span4">
        <div class="form-horizontal well">
            <a href="/user/${userProfileItem.username}">
                <div class="row-fluid">
                    <div class="span4">
                        <img src="${userProfileItem.profilePicURL}" >
                    </div>
                    <div class="span4">
                        <h1>${userProfileItem.username}</h1>
                    </div>
                </div>
            </a>
            <br />
            <div class="row-fluid">
                <a href="/user/${userName}"><div class="offset1 span4"><div><strong>${noTweets}</strong></div> Tweets</div> </a>
                <a href="/user/followers/<%= request.getAttribute("curUserName")%>"><div class="span4"><div><strong>${noFollow}</strong></div>Followers</div> </a>
                <a href="/user/following/<%= request.getAttribute("curUserName")%>"><div class="span4"><div><strong>${noFollowing}</strong> </div>Following </div> </a>
            </div>
            <div class="buttonHolder">
                <button class="btn btn-info" id="followStatus" style="z-index:100;width:100px">Follow</button>
            </div>
            <div id="StatusDiv" style="display:none;"> Status:<span id="status">Following</span></div>
            <div id="hidden2">${followStatus}</div>
        </div>
    </div>

    <div class = "span7">
        <div class = "tweetContainer fill"  >
            <div class = "tweetContainerTitle">${head}</div>
            <table id="userList" class="table ">
            </table>
        </div>
    </div>
        <script type="text/javascript">
            document.title = "Twimini : ${title}"
            function ejs(data) {
                return (new EJS({url: '/static/ejs/user.ejs'}).render(data));
            }
            $(document).ready(function () {
                $.get("${url}",function(data) {
                    for(var i in data) {
                        console.log(data[i]);
                        if (data[i]["username"] != '<%= request.getAttribute("curUserName") %>')

                        $('#userList').append(ejs(data[i]));
                    }
                });
                $(".followButton").live("click",function(){
                    var el = $(this);
                    function sendFollowTypeReq(action,callback) {
                        $.post("/user/" + action + "/" + el.attr("id"),callback);
                    }
                    if (el.attr("following") == "true") {
                        sendFollowTypeReq("unfollow",function() {
                            el.html("Follow").removeClass("btn-danger").addClass("btn-info");
                            el.attr("follow","false");
                        });
                    }
                    else {
                        sendFollowTypeReq("follow",function() {
                            el.html("Unfollow").removeClass("btn-info").addClass("btn-danger");
                            el.attr("follow","true");
                        });
                    }
                });
            });
        </script>
<jsp:include page="tail.jsp"/>
