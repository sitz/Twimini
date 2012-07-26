<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="head.jsp"/>


<div class="container containers">
    <div class="span4 left fill">
    <form class="form-horizontal well">
        <div>
        <textarea rows="5" name="tweet" class="input-xlarge tweetarea" style="width:330px;padding-bottom: 10px"></textarea>
        </div>
        <div class="buttonHolder">
        <a class="btn" href="#" onclick="addItem2(this); return false;">Tweet!</a>
            </div>
    </form>
    </div>

    <div class="span6 right fill">
        <div class = "tweetContainer fill"  >
        <div class = "tweetContainerTitle">Tweet Feed</div>
        <table id="tweetList" class="table ">
        </table>
    </div>


</div>
</div>

<script type="text/javascript">
    function ejs(data) {
        data.currentUser = <%= session.getAttribute("userName") %>;
        return $(new EJS({url: '/static/ejs/tweet.ejs'}).render(data)).data("tweetID", data.id);
    }
    function addItem2(element) {
        var form = $(element).parent().parent();
        $.post('/tweet/new.json', $(form).serialize(),function(data) {
                data.currentUser = <%= session.getAttribute("userName") %>;
            var tweetItemLI = ejs(data);
            $('#tweetList').prepend(tweetItemLI);
        });
    }
    function retweet(tweetid,userid) {
        $.get('/tweet/retweet/' + userid + '/' + tweetid,function(data) {
            data.currentUser = <%= session.getAttribute("userName") %>;
            var tweetItemLI = ejs(data);
            $('#tweetList').prepend(tweetItemLI).fadeIn();
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
                var tweetItemLI = ejs(data[i]);
                $('#tweetList').append(tweetItemLI);
            }
        });
    });
</script>

<jsp:include page="tail.jsp"/>
