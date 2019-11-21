var userName = 'user' + Math.floor((Math.random() * 1000) + 1);
var clientLocation = 'lat: ' + Math.floor((Math.random() * 1000) + 1) + ", log: " + Math.floor((Math.random() * 1000) + 1);
var socket = null;

function login() {
    $.ajax({
        url: "http://localhost:8080/cuber/auth/token",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify({
            email: $("#email").val(),
            password: $("#password").val()
        })
    }).done(function (data) {
        output('<span class="username-msg"> Login success... <br>Token => <b>' + data.body + '</b></span>');
        $("#token").val(data.body);
    }).fail(function (jqXHR) {
        output('<span class="disconnect-msg">' + jqXHR.responseJSON.body + '</span>')
    });
}

function initSocket() {
    socket = io('http://localhost:9092/client?token=' + $("#token").val(), {
        transports: ['polling', 'websocket']
    });

    socket.on('connect', function () {
        output('<span class="connect-msg">The client has connected with the server. Username: ' + userName + '</span>');
    });
    socket.on('receive_location', function (data) {
        console.log(data);
        output('<span class="username-msg">Received Driver location: </span>' + JSON.stringify(data));
    });
    socket.on('disconnect', function () {
        output('<span class="disconnect-msg">The client has disconnected!</span>');
    });
    socket.on('invalid_token', function () {
        output('<span class="disconnect-msg">Token is invalid!!</span>');
    });
    socket.on('reconnect_attempt', (attempts) => {
        console.log('Try to reconnect at ' + attempts + ' attempt(s).');
    });
}

function sendDisconnect() {
    socket.disconnect();
}

function requestLocation() {
    var jsonObject = {userName: userName, message: clientLocation, actionTime: new Date()};
    socket.emit('request_location', jsonObject);
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