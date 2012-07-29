<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp"/>
<div class = "container">
    <div class = "offset2 span8">
        <div class = "tweetContainer fill"  >
            <div class = "tweetContainerTitle">${head}</div>
            <table id="userList" class="table ">
            </table>
        </div>
    </div>
        <script type="text/javascript">
            function ejs(data) {
                return (new EJS({url: '/static/ejs/user.ejs'}).render(data));
            }
            $(document).ready(function () {
                $.get("${url}",function(data) {
                    for(var i in data) {
                        $('#userList').append(ejs(data[i]));
                    }
                });
            });
        </script>
<jsp:include page="tail.jsp"/>
