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
