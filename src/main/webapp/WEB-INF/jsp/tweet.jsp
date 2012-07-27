<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="head.jsp"/>
<div class="container">
    <div class="span4">
    <form class="form-horizontal well">
        <div>
        <textarea rows="5" name="tweet" class="input-xlarge tweetarea" style="padding-bottom: 10px"></textarea>
        </div>
        <div class="buttonHolder">
        <a class="btn" href="#" onclick="addItem2(this); return false;">Tweet!</a>
            </div>
    </form>
    </div>

    <div class="span6">
        <div class = "tweetContainer fill"  >
        <div class = "tweetContainerTitle">Tweet Feed</div>
        <table id="tweetList" class="table ">
        </table>
    </div>


</div>
</div>

<script type="text/javascript">
    var maxId = 0;
    var minId = undefined;
    function refresh() {
        alert(maxId);
    }
    function more() {
        alert(minId);
    }
    function prepenD(data) {
        maxId = data.id;
        data = ejs(data);
        $('#tweetList').prepend(data);
    }
    function appenD(data) {
        if(minId == undefined) maxId = data.id;
        minId = data.id;
        data = ejs(data);
        $('#tweetList').append(data);
    }
    function ejs(data) {
        data.currentUser = "<%= session.getAttribute("userName") %>";
        return $(new EJS({url: '/static/ejs/tweet.ejs'}).render(data)).data("tweetID", data.id);
    }
    function addItem2(element) {
        var form = $(element).parent().parent();
        $.post('/tweet/new.json', $(form).serialize(),function(data) {
                refresh();
        });
    }
    function retweet(tweetid,userid) {
        $.get('/tweet/retweet/' + userid + '/' + tweetid,function(data) {
                refresh();
        });
    }
    function favorite(tweetid,userid,element) {
        $.get('/tweet/favorite/' + userid + '/' + tweetid,function(data) {
            $(element).parent().html("Liked").hide().fadeIn();
        });
    }
    $(document).ready(function () {
        $.post('/tweet/feed.json',function(data) {
            for(var i in data) {
                appenD(data[i]);
            }
        });
    });
</script>

<jsp:include page="tail.jsp"/>
