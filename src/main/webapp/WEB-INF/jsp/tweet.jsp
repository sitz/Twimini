<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>

<script type="text/javascript">
    function appendItem(data) {
        var tweetItemLI = $(new EJS({url: '/static/ejs/tweet.ejs'}).render(data)).data("tweetID", data.id);
        $('#tweetList').append(tweetItemLI);
    }
</script>

<div class="container" style="height: 1000px;">
    <div class="span4" style="padding:15px;">
    <form class="form-horizontal well">
        <div>
        <textarea rows="5" name="tweet" class="input-xlarge" style="width:330px;padding-bottom: 10px"></textarea>
        </div>
        <div style="text-align: right;padding-top: 10px;">
        <a class="btn" href="#" onclick="addItem2(this); return false;">Tweett!</a>
            </div>
    </form></div>




    <div class="span7" style="padding:15px;padding-left: 0px;padding-right:0px">
        <div style="border-style:ridge;border-radius:15px;">
        <div style="height: 32px;padding: 15px;padding-bottom: 10px;padding-top: 20px;"><span style="font-size: 32px;"> Fancy Title</span></div>
        <table id="tweetList" class="table table-bordered table-striped">
            <c:forEach var='tweet' items='${tweets}'>
                <script type="text/javascript">
                    appendItem({id:${tweet.id}, tweet :'${tweet.tweet}',receiverId : '${tweet.receiverId}'})
                </script>
            </c:forEach>
        </table>
    </div>


</div>
</div>


<script type="text/javascript">
    function addItem2(element) {
        var form = $(element).parent().parent();
        $.post('/tweet/new.json', $(form).serialize(),function(data) {
            var tweetItemLI = $(new EJS({url: '/static/ejs/tweet.ejs'}).render(data)).data("tweetID", data.id);
            $('#tweetList').prepend(tweetItemLI);
        });
    }

    $(".tweetDelete").live("click", function(evt){
        var id = $(evt.target).parents("li").data("tweetID");
        $.post('/tweet/delete.json', {id:id},function(data) {
            $(evt.target).parents("li").remove();
        });
    });
    $(".tweetUpdate").live("submit", function(evt){
        var form = evt.target;
        var data = {id:form.id.value, name:form.name.value};
        $.post("/tweet/update.json", $(form).serialize(),function() {
            var tweetItemLI = $(new EJS({url: '/static/ejs/tweet.ejs'}).render(data)).data("tweetID", data.id);
            $('#tweetItem' + data.id).replaceWith(tweetItemLI);
        });
    });
</script>

<jsp:include page="tail.jsp"/>
