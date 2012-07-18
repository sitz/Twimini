<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/ejs_production.js"></script>

    <script type="text/javascript">
        function addItem2(form) {
            $.post('/tweet/new.json', $(form).serialize(),function(data) {
                var tweetItemLI = $(new EJS({url: '/static/ejs/tweet.ejs'}).render(data)).data("tweetID", data.id);
              $('#tweetList').append(tweetItemLI);
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

        function appendItem(data) {
            var tweetItemLI = $(new EJS({url: '/static/ejs/tweet.ejs'}).render(data)).data("tweetID", data.id);
            $('#tweetList').append(tweetItemLI);
        }
    </script>
</head>
<body>
Hello ${sessionScope.userName} <a href="/user/logout">Logout</a>

<h1>Tweet</h1>
<ul id="tweetList">
    <li> Hllo</li>
    <c:forEach var='tweet' items='${tweets}'>
        <script type="text/javascript">
            console.log("ER");
            appendItem({id:${tweet.id}, tweet :'${tweet.tweet}',receiverId : '${tweet.receiverId}'})
        </script>
    </c:forEach>
</ul>

<form action="/tweet/create" onsubmit="addItem2(this); return false;">
    New tweet:
    <input type="text" name="tweet"/>
    <input type="submit" value="Add"/>
</form>

</body>
</html>