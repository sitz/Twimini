function user_ejs(data) {
    return (new EJS({url: '/static/ejs/user.ejs'}).render(data));
}
function feed_ejs(data) {
    var tweet = data.tweet.split(" ");
    data.tweet = tweet.map(function(b){
        if(b[0]!="@") 
            return b;
        b = b.replace("@",""); 
        return '<a href="/user/' + b + '">' + '@' + b +'</a>';
    }).join(" ");
    return (new EJS({url: '/static/ejs/tweet.ejs'}).render(data));
}
$(".followButton").live("click",function(){
    var el = $(this);
    function sendFollowTypeReq(action,callback) {
        $.post("/api/user/" + action + "/" + el.attr("id"),callback);
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

function login(){
    $.post("/api/auth/login",$("#formLogin").serialize(),function(data){
        if(data=="Error1") {
            alert("Login Failed");
        }
        else {
            window.location = "/tweet"
        }
    });
    return false;
}

function userAlert(data) {
    alert(data);
}

function register() {
    $.post("/api/auth/register",$("#formRegister").serialize(),function(data){
        if(data==0) {
            alert("Successfully registerd");
            window.location = "/auth/login"
        }
        if(data==1) {
            userAlert("Email already exists");
        }
        if(data==2) {
            userAlert("Username already exists");
        }
    });
    return false;
}
function forgot() {
    alert("ER");
    $.post('/api/auth/forgot/' + $('#forgotPassword').val(),function(data) {
        alert("DF");
    });
    alert("ER");
    $("#myModal").modal('hide');
    return false;
}
    function retweet(tweetid,userid) {
        $.post('/api/tweet/retweet/' + userid + '/' + tweetid,function(data) {
            alert("Re-Tweeted");
        });
    }
    function favorite(tweetid,userid,element) {
        $.post('/api/tweet/favorite/' + userid + '/' + tweetid,function(data) {
            $(element).parent().html('<i class="icon-star"></i>Liked</a>').hide().fadeIn();
        });
    }