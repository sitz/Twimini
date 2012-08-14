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
    $("#modalErrorBody").html(data);
    $("#modalError").modal();
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
        if(data==3) {
            userAlert("Invalid Email");
        }
        if(data==4) {
            userAlert("Invalid Username");
        }
        if(data==5) {
            userAlert("Invalid Password");
        }
    });
    return false;
}
function forgot() {
    $.post('/api/auth/forgot/' + $('#forgotPassword').val(),function(data) {
        alert("DF");
    });
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
function resetPassword(form) {
    var a = $("#input01").val();
    var b = $("#input02").val();
    if(a==b) {
        $.post("/api/user/change/"+a,function(data) {
            $(".alert-success").alert();
            $(".alert-success").fadeIn('fast');
            $(".alert-success").addClass("in");
        });
    }
    else {
        $(".alert-error").alert();
        $(".alert-error").fadeIn('fast');
        $(".alert-error").addClass("in");
    }
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
$('#myTab a').click(function (e) {
  e.preventDefault();
  $(this).tab('show');
})
$('.tweettextarea').live("keyup",function(data){
    var charDiff = 140 - $(this).val().length;
    $("#charLeftRemaining").html(charDiff);
    if(charDiff<0)
        $("#tweetButton").attr('disabled','disabled');
    else 
        $("#tweetButton").removeAttr('disabled')
});
