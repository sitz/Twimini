<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<div class="container">
    <div class="span4 ">
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
                <a href="/user/followers/<%= request.getAttribute("userName")%>"><div class="span4"><div><strong>${noFollow}</strong></div>Followers</div> </a> 
                <a href="/user/following/<%= request.getAttribute("userName")%>"><div class="span4"><div><strong>${noFollowing}</strong> </div>Following </div> </a> 
            </div>
            <div class="buttonHolder">
                <button class="btn btn-info" id="followStatus" style="z-index:100;width:100px">Follow</button>
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
        data.currentUser = "<%= request.getAttribute("CurUserName") %>";
        return $(new EJS({url: '/static/ejs/tweet.ejs'}).render(data)).data("tweetID", data.id);
    }
    function retweet(tweetid,userid) {
        $.post('/tweet/retweet/' + userid + '/' + tweetid,function(data) {
            alert("Re-Tweeted");
        });
    }
    function favorite(tweetid,userid,element) {
        $.post('/tweet/favorite/' + userid + '/' + tweetid,function(data) {
            $(element).parent().html('<i class="icon-star"></i>Liked</a>').hide().fadeIn();
        });
    }
    $(document).ready(function () {
        <% if( ((String)request.getAttribute("userName")).equals((String)(request.getAttribute("curUserName"))) ) { %>
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
                $.post('/user/unfollow/${userName}',function(data) {
                    setStatusNotFollowing();
                });
            }
            else {
                $.post('/user/follow/${userName}',function(data) {
                    setStatusFollowing();
                });
            }
        });
        $.get('/user/${userName}/json',function(data) {
            for(var i in data) {
                var tweetItemLI = ejs(data[i]);
                $('#tweetList').append(tweetItemLI);
            }
        });
    });
</script>
<jsp:include page="tail.jsp"/>
