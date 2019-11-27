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
        output('<span class="connect-msg">[connect] The client has connected with the server. Username: ' + userName + '</span>');
    });
    socket.on('receive_location', function (data) {
        let driver = data.driver;
        output('<span class="username-msg">[receive_location] Received Driver location: </span>' + JSON.stringify(data));
        $("#candidate-drivers").append(`<tr><td>${driver.first_name} ${driver.last_name} / ${driver.car_model} (${driver.car_registration_number})</td><td><button class="btn btn-primary" onclick="requestTrip('${driver.id}')">Request Trip</button></td></tr>`);
    });
    socket.on('trip_accepted', function (trip) {
        let driver = trip.driver;
        output('<span class="username-msg">[trip_accepted] Driver accept your request: </span>' + JSON.stringify(trip));
        alert(`${driver.first_name} ${driver.last_name} / ${driver.car_model} (${driver.car_registration_number}) Accepted your Request! <br> ${JSON.stringify(trip)}`);
    });
    socket.on('trip_declined', function (data) {
        let driver = data.driver;
        output('<span class="disconnect-msg">[trip_declined] Driver decline your request: </span>' + JSON.stringify(data));
        alert(`${driver.first_name} ${driver.last_name} / ${driver.car_model} (${driver.car_registration_number}) Declined your Request!`);
    });
    socket.on('request_location', function (user) {
        output('<span class="username-msg">Received Location request from: </span>' + JSON.stringify(user));
        sendLocation(user);
    });
    socket.on('disconnect', function () {
        output('<span class="disconnect-msg">[disconnect] The client has disconnected!</span>');
    });
    socket.on('invalid_token', function () {
        output('<span class="disconnect-msg">[invalid_token] Token is invalid!!</span>');
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
    var jsonObject = {clientLocation: clientLocation, driver: driverID};
    socket.emit('request_trip', jsonObject);
}

function output(message) {
    var currentTime = "<span class='time'>" + moment().format('HH:mm:ss.SSS') + "</span>";
    var element = $("<div>" + currentTime + " " + message + "</div>");
    $('#console').prepend(element);
}