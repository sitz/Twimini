<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<div class="container">
    <div class="span4 ">
        <form class="form-horizontal fill" id="#tweetform">
            <div class="well">
                <a href="/user/${userProfileItem.username}">
                    <div class="row-fluid">
                        <div class="span4">
                            <img src="${userProfileItem.profilePicURL}" >
                        </div>
                        <div class="span4 pullright">
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
                <div id="StatusDiv" style="display:none;"> Status:<span id="status">Following</span></div>
                <div id="hidden2">${followStatus}</div>
                <div class="page-header">
                    <div>
                        <textarea rows="5" name="tweet" id="tweet" class="input-xlarge tweetarea" style="padding-bottom: 10px"></textarea>
                    </div>
                    <div class="buttonHolder">
                        <a class="btn" href="#" onclick="addItem2(this); return false;">Tweet!</a>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="span6">
        <div class = "tweetContainer fill"  >
            <div class = "tweetContainerTitle">Tweet Feed</div>
            <button class="btn" onclick="refresh();expandtweets();" id="extraTweetList" style="width:100%">
                <span id="newTweetNumber">0</span> new tweets 
            </button>
            <table id="tweetList" class="table ">
            </table>
            <a class="btn" href="#" onclick="more();return false;">More</a>
        </div>
    </div>
</div>

<script type="text/javascript">
    var maxId = 0;
    var minId = undefined;
    var stacK = [];
    function expandtweets() {
        for(var i in stacK) {
            $("#tweetList").prepend(stacK[i]);
        }
        stacK = [];
        $("#extraTweetList").hide();
    }
    function refresh() {
        $.get('/tweet/feed/new/' + maxId,function(data) {
            for(var i in data) {
                console.log("SOmething happend");
                prepenD(data[i]);
            }
        });
    }
    function more() {
        $.get('/tweet/feed/old/' + minId,function(data) {
            for(var i in data) {
                appenD(data[i]);
            }
        });
    }
    function prepenD(data){
        $("#extraTweetList").show();
        maxId = data.id;
        data = ejs(data);
        stacK.push(data);
        $("#newTweetNumber").html(stacK.length);
    }
    function appenD(data) {
        if(minId == undefined) maxId = data.id;
        minId = data.id;
        data = ejs(data);
        $('#tweetList').append(data);
    }
    function ejs(data) {
        data.currentUser = "<%= request.getAttribute("userName") %>";
        return $(new EJS({url: '/static/ejs/tweet.ejs'}).render(data)).data("tweetID", data.id);
    }
    function addItem2(element) {
        var form = $("#tweetform");
        $.post('/tweet/new.json',{"tweet" : $("#tweet").val()} ,function(data) {
                refresh();
        });
        $("#tweet").val("");

    }
    function retweet(tweetid,userid) {
        $.post('/tweet/retweet/' + userid + '/' + tweetid,function(data) {
                refresh();
        });
    }
    function favorite(tweetid,userid,element) {
        $.post('/tweet/favorite/' + userid + '/' + tweetid,function(data) {
            $(element).parent().html('<i class="icon-star"></i>Liked</a>').hide().fadeIn();
        });
    }
    $(document).ready(function () {
        $("#extraTweetList").hide();
        window.setInterval("refresh()",5000);
        $.get('/tweet/feed.json',function(data) {
            for(var i in data) {
                appenD(data[i]);
            }
        });
    });
</script>
<jsp:include page="tail.jsp"/>
