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
                    id="${userProfileItem.username}" following="${followStatus}">
                    <% if ( request.getAttribute("followStatus").equals(Boolean.TRUE) ) {%>Unfollow<% }else{%>Follow<%}%>
                </button>
            </div>
        </div>
    </div>
    <div class="span7 right">
        <div class = "tweetContainer"  >
            <div class = "tweetContainerTitle">Fancy Title</div>
            <table id="itemList" class="table">
            </table>
    </div>
</div>


<script type="text/javascript">
    document.title = 'TwiMini: <%= request.getAttribute("userName") %>';
    function fEjs(data) {
        data.currentUser = "<%= request.getAttribute("curUserName") %>";
        return feed_ejs(data);
    }
    $(document).ready(function () {
        <% if( (request.getAttribute("userName")).equals((request.getAttribute("curUserName"))) ) { %>
            $(".chumba").html("");
        <%}%>
        $.get('/api/user/${userName}',function(data) {
            for(var i in data) {
                var tweetItemLI = fEjs(data[i]);
                $('#itemList').append(tweetItemLI);
            }
        });
    });
</script>
<jsp:include page="tail.jsp"/>
