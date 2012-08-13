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
                <button style="z-index:100;width:100px"
                    class="chumba followButton btn <% if (request.getAttribute("followStatus").equals(Boolean.TRUE) ) {%> btn-danger <% }else{%> btn-info <%}%> "
                    id="<%= request.getAttribute("curUserName")%>" following="${followStatus}>">
                    <% if ( request.getAttribute("followStatus").equals(Boolean.TRUE) ) {%>Unfollow<% }else{%>Follow<%}%>
                </button>
            </div>
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
    document.title = 'TwiMini: <%= request.getAttribute("userName") %>';
    function ejs(data) {
        data.currentUser = "<%= request.getAttribute("curUserName") %>";
        return feed_ejs(data);
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
        <% if( (request.getAttribute("userName")).equals((request.getAttribute("curUserName"))) ) { %>
            $(".chumba").html("");
        <%}%>
        $(".followButton").live("click",function(){
            var el = $(this);
            function sendFollowTypeReq(action,callback) {
                $.post("/api/user/" + action + "/" + el.attr("id"),callback);
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
        $.get('/api/user/${userName}',function(data) {
            for(var i in data) {
                var tweetItemLI = ejs(data[i]);
                $('#tweetList').append(tweetItemLI);
            }
        });
    });
</script>
<jsp:include page="tail.jsp"/>
