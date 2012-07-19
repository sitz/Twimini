<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="/static/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/static/css/bootstrap-responsive.css" rel="stylesheet" />
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/ejs_production.js"></script>

    <script type="text/javascript">
        function addItem2(element) {
            var form = $(element).parent();
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
<body class="preview" data-spy="scroll" data-target=".subnav" data-offset="50" style="padding: 100px;">


<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="../">Mini Twitter</a>
            <div class="nav-collapse" id="main-menu"><ul class="nav" id="main-menu-left">
            </ul>
                <ul class="nav pull-right" id="main-menu-right">
                    <li><a rel="tooltip" href=""  data-original-title="Customize your settings">Profile</a></li>
                    <li><a rel="tooltip" href=""  data-original-title="Go to you Homepage">Home</a></li>
                    <li><a rel="tooltip" href=""  data-original-title="Logout">Logout</a></li>
                </ul></div>
        </div>
   </div>
 </div>
<div class="container">
    <div class="span4">
    <form class="form-horizontal well">
        <div>
        <textarea rows="5" name="tweet" class="input-xlarge" style="width:330px;padding-bottom: 10px"> Tweet </textarea>
        </div>
        <div style="text-align: right;padding-top: 10px;">
        <a class="btn" href="#" onclick="addItem2(this); return false;">Tweett!</a>
            </div>
    </form></div>
    <div class="span6">
        <form class="form-horizontal well">
        <h1>Some Fancy Title</h1>
<div id="tweetList">
    <c:forEach var='tweet' items='${tweets}'>

        <script type="text/javascript">
            appendItem({id:${tweet.id}, tweet :'${tweet.tweet}',receiverId : '${tweet.receiverId}'})
        </script>
    </c:forEach>
</div>
        </form>
</div>
</div>

<!-- JS at the end -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/bootswatch.js"></script>
</body>
</html>