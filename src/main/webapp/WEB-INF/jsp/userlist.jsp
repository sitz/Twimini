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
        </div>
    </div>

    <div class = "span7">
        <div class = "tweetContainer fill"  >
            <div class = "tweetContainerTitle">${head}</div>
            <table id="itemList" class="table ">
            </table>
        </div>
    </div>
        <script type="text/javascript">
            document.title = "Twimini : ${title}"
            function uEjs(data) {
                data.curUserName = '<%= request.getAttribute("curUserName") %>';
                return user_ejs(data);;
            }
            $(document).ready(function () {
                $.get("${url}",function(data) {
                    for(var i in data) {
                        $('#itemList').append(uEjs(data[i]));
                    }
                });
            });
        </script>
<jsp:include page="tail.jsp"/>
