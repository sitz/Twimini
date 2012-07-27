var maxId = 0;
var minId = undefined;
function refresh() {
    $.get('/tweet/feed/new/' + maxId,function(data) {
        for(var i in data) {
            prepenD(data[i]);
        }
    });
}
function more() {
    $.get('/tweet/feed/old/' + minId,function(data) {
        for(var i in data) {
            appenD(data[i]);
        }
    });
}
function prepenD(data) {
    maxId = data.id;
    data = ejs(data);
    $('#tweetList').prepend(data);
}
function appenD(data) {
    if(minId == undefined) maxId = data.id;
    minId = data.id;
    data = ejs(data);
    $('#tweetList').append(data);
}
