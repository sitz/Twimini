<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<div class="container">
    <div class="span4 ">
        <div class="form-horizontal well">
            <div class="row-fluid">
                <a href="/user/${userProfileItem.username}">
                    <div class="span4">
                        <img src="${userProfileItem.profilePicURL}" >
                    </div>
                    <div class="span4 pullright">
                        <h1>${userProfileItem.username}</h1>
                    </div>
                </a>
                <div class="buttonHolder span4">
                    <a class="btn btn-info" id="followStatus" style="width: 75px">Follow</a>
                </div>
            </div>
            <br />
            <div class="row-fluid">
                <a href="/user/${userName}"><div class="offset1 span4"><div><strong>${noTweets}</strong></div> Tweets</div> </a> 
                <a href="/user/followers/<%= session.getAttribute("userName")%>"><div class="span4"><div><strong>${noFollow}</strong></div>Followers</div> </a> 
                <a href="/user/following/<%= session.getAttribute("userName")%>"><div class="span4"><div><strong>${noFollowing}</strong> </div>Following </div> </a> 
            </div>
            <div id="StatusDiv" style="display:none;"> Status:<span id="status">Following</span></div>
            <div id="hidden2">${followStatus}</div>
        </div>
    </div>
    <div class="span6 right">
        <div class = "tweetContainer"  >
            <div class = "tweetContainerTitle">Fancy Title</div>
            <table id="tweetList" class="table">
            </table>
    </div>
</div>


<script type="text/javascript">
    function ejs(data) {
        data.currentUser = "<%= session.getAttribute("userName") %>";
        return $(new EJS({url: '/static/ejs/tweet.ejs'}).render(data)).data("tweetID", data.id);
    }
    function retweet(tweetid,userid) {
        $.get('/tweet/retweet/' + userid + '/' + tweetid,function(data) {
            alert("Re-Tweeted");
        });
    }
    function favorite(tweetid,userid,element) {
        $.get('/tweet/favorite/' + userid + '/' + tweetid,function(data) {
            $(element).parent().html('<i class="icon-star"></i>Liked</a>').hide().fadeIn();
        });
    }
    $(document).ready(function () {
        <% if( ((String)session.getAttribute("userName")).equals((String)(request.getAttribute("userName"))) ) { %>
        $("#StatusDiv").hide();
        $("#followStatus").hide();  <%}%>
        $("#hidden2").hide();
        if($("#hidden2").html() =="1") {
            setStatusFollowing();
        }
        else {
            setStatusNotFollowing();
        }
        function setStatusNotFollowing() {
            $("#status").html("Not Following");
            $("#hidden2").html("0");
            $("#followStatus").html("Follow");
            $("#followStatus").removeClass("btn-danger").addClass("btn-info")

        }
        function setStatusFollowing() {
            $("#status").html("Following");
            $("#hidden2").html("1");
            $("#followStatus").html("UnFollow");
            $("#followStatus").removeClass("btn-info").addClass("btn-danger")
        }
        $("#followStatus").click(function(){
            var following = $("#hidden2").html();
            var t = $("#followStatus");
            if(following == "1") {
                $.get('/user/unfollow/${userName}',function(data) {
                    setStatusNotFollowing();
                });
            }
            else {
                $.get('/user/follow/${userName}',function(data) {
                    setStatusFollowing();
                });
            }
        });
        $.post('/user/${userName}/json',function(data) {
            for(var i in data) {
                var tweetItemLI = ejs(data[i]);
                $('#tweetList').append(tweetItemLI);
            }
        });
    });
</script>
<jsp:include page="tail.jsp"/>
