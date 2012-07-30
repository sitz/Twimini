<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<div class = "container">
    <div class = "span4">
        <form class="form-horizontal well" onsubmit="return searchUser();">
            <div>
                <input type="text" name="query" id="query" class="input-xlarge"/>
            </div>
            <div class="buttonHolder">
                <button type="submit" class="btn" href="#">
                    Search!
                </a>
            </div>
        </form>
    </div>
    <div class = "span6"> 
        <div class = "tweetContainer fill"  >
        <div class = "tweetContainerTitle">Search for Users</div>
        <table id="userList" class="table ">
        </table>
    </div>
    <script type="text/javascript">
        function ejs(data) {
            return (new EJS({url: '/static/ejs/user.ejs'}).render(data));
        }
        function searchUser() {
            $('#userList').html("");
            var query = $("#query").val();
            $.get("/search/" + query +"/json",function(data) {
                    for(var i in data) {
                    var userItem = ejs(data[i]);
                    $('#userList').append(userItem);
                }
            });
            return false;
        }
        $(document).ready(function () {
        });
    </script>
<jsp:include page="tail.jsp"/>
