var userName = 'user' + Math.floor((Math.random() * 1000) + 1);
var driverLocation = 'lat: ' + Math.floor((Math.random() * 1000) + 1) + ", log: " + Math.floor((Math.random() * 1000) + 1);

var socket = io('http://localhost:9092/driver', {
    transports: ['polling', 'websocket']
});
socket.on('connect', function () {
    output('<span class="connect-msg">The driver has connected with the server. Username: ' + userName + '</span>');
});
socket.on('request_location', function (data) {
    output('<span class="username-msg">Received Location request from: </span>' + JSON.stringify(data));
    sendLocation();
});
socket.on('disconnect', function () {
    output('<span class="disconnect-msg">The client has disconnected!</span>');
});
socket.on('reconnect_attempt', (attempts) => {
    console.log('Try to reconnect at ' + attempts + ' attempt(s).');
});

function sendDisconnect() {
    socket.disconnect();
}

function sendLocation() {
    var jsonObject = {userName: userName, message: driverLocation, actionTime: new Date()};
    socket.emit('get_location', jsonObject);
}

function output(message) {
    var currentTime = "<span class='time'>" + moment().format('HH:mm:ss.SSS') + "</span>";
    var element = $("<div>" + currentTime + " " + message + "</div>");
    $('#console').prepend(element);
}

$(document).keydown(function (e) {
    if (e.keyCode == 13) {
        $('#send').click();
    }
});