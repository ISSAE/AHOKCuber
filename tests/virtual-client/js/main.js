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
        output('<span class="username-msg"> Login success... Token => <b>' + data.body + '</b></span>');
        $("#token").val(data.body);
    }).fail(function (jqXHR) {
        output('<span class="disconnect-msg">' + jqXHR.responseJSON.body + '</span>')
    });
}

function initSocket() {
    socket = io('http://localhost:9092/client?token=' + $("#token").val(), {
        transports: ['polling', 'websocket']
    });

    socket.on('connect', function (data) {
        output('<span class="connect-msg">The client has connected with the server. session id: ' + socket.get + '</span>');
    });
    socket.on('receive_location', function (data) {
        let driver = data.driver;
        output('<span class="username-msg">Received Driver location: </span>' + JSON.stringify(data));
        $("#candidate-drivers").append(`<tr><td>${driver.first_name} ${driver.last_name} / ${driver.car_model} (${driver.car_registration_number})</td><td><button class="btn btn-primary" onclick="requestTrip('${driver.id}')">Request Trip</button></td></tr>`);
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
    $("#candidate-drivers").html("");
    socket.emit('request_location');
}

function requestTrip(driverID) {
    socket.emit('request_trip', driverID);
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