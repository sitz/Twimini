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
                        console.log(data[i]);
                        if (data[i]["username"] != '<%= request.getAttribute("userName") %>')

                        $('#userList').append(ejs(data[i]));
                    }
                });
                $(".followButton").live("click",function(){
                    var el = $(this);
                    function sendFollowTypeReq(action,callback) {
                        $.get("/user/" + action + "/" + el.attr("id"),callback);
                    }
                    if (el.attr("following") == "true") {
                        sendFollowTypeReq("unfollow",function() {
                            el.html("Follow").removeClass("btn-danger").addClass("btn-info");
                            el.attr("follow","false");
                        });
                    }
                    else {
                        sendFollowTypeReq("follow",function() {
                            el.html("Unfollow").removeClass("btn-info").addClass("btn-danger");
                            el.attr("follow","true");
                        });
                    }
                });
            });
        </script>
<jsp:include page="tail.jsp"/>
