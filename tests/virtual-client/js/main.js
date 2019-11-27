let userName = 'user' + Math.floor((Math.random() * 1000) + 1);
let clientLocation = 'lat: ' + Math.floor((Math.random() * 1000) + 1) + ", log: " + Math.floor((Math.random() * 1000) + 1);
let socket = null;
let trip = null;

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
    socket = io(`http://localhost:9092/client?token=${$("#token").val()}`, {
        transports: ['polling', 'websocket']
    });
    socket.on('connect', function (data) {
        output(`<span class="connect-msg">[connect] The client has connected with the server. Username: ${userName}</span>`);
    });
    socket.on('receive_location', function (data) {
        let driver = data.driver;
        output(`<span class="username-msg">[receive_location] Received Driver location: </span>${JSON.stringify(data)}`);
        $(`#driver-location-${driver.id}`).remove();
        $("#candidate-drivers").append(`<tr id="driver-location-${driver.id}"><td>${driver.first_name} ${driver.last_name} / ${driver.car_model} (${driver.car_registration_number})<br>${driver.email}</td><td><button class="btn btn-primary" onclick="requestTrip('${driver.id}')">Request Trip</button></td></tr>`);
    });
    socket.on('trip_accepted', function (_trip) {
        let driver = _trip.driver;
        trip = _trip;
        $("#trip-actions").removeClass('d-none');
        output(`<span class="username-msg">[trip_accepted] Driver accept your request: </span>${JSON.stringify(_trip)}`);
        $( "#trip_response" ).attr('title', 'Trip Accepted');
        $("#trip_response .text").text(`${driver.first_name} ${driver.last_name} / ${driver.car_model} (${driver.car_registration_number}) Accepted your Request! <br> ${JSON.stringify(_trip)}`);
        $( "#trip_response" ).dialog({
            buttons: {
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            }
        });
    });
    socket.on('trip_declined', function (data) {
        let driver = data.driver;
        output(`<span class="disconnect-msg">[trip_declined] Driver decline your request: </span>${JSON.stringify(data)}`);
        $( "#trip_response" ).attr('title', 'Trip Declined');
        $("#trip_response .text").text(`${driver.first_name} ${driver.last_name} / ${driver.car_model} (${driver.car_registration_number}) Declined your Request!`);
        $( "#trip_response" ).dialog({
            buttons: {
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            }
        });
    });
    socket.on('request_location', function (user) {
        output(`<span class="username-msg">[request_location] Received Location request from: </span>${JSON.stringify(user)}`);
        sendLocation(user);
    });
    socket.on('trip_updated', function (_trip) {
        trip = _trip;
        if (trip.status === "COULD_NOT_MEET" || trip.status === "FINISHED") {
            finish();
        }
        output(`<span class="username-msg">[trip_updated] Trip Updated: </span>${JSON.stringify(_trip)}`);
    });
    socket.on('disconnect', function () {
        output('<span class="disconnect-msg">[disconnect] The client has disconnected!</span>');
    });
    socket.on('invalid_token', function () {
        output('<span class="disconnect-msg">[invalid_token] Token is invalid!!</span>');
    });
    socket.on('reconnect_attempt', (attempts) => {
        console.log(`Try to reconnect at ${attempts} attempt(s).`);
    });
}

function sendDisconnect() {
    socket.disconnect();
}

function requestLocation() {
    finish();
    socket.emit('request_location');
}

function driverArrived() {
    socket.emit('driver_arrived', trip.id);
}

function couldNotMeet() {
    socket.emit('could_not_meet', trip.id);
    finish();
}

function arrivedToDest() {
    socket.emit('arrived_to_dest', trip.id);
    finish();
}

function sendLocation(user) {
    let jsonObject = {user: user, location: clientLocation};
    if (socket) socket.emit('get_location', jsonObject);
}

function finish() {
    $("#trip-actions").addClass('d-none');
    trip = null;
    $("#candidate-drivers").html("");
}

function requestTrip(driverID) {
    let jsonObject = {clientLocation: clientLocation, driver: driverID};
    socket.emit('request_trip', jsonObject);
}

function output(message) {
    let currentTime = `<span class='time'>${moment().format('HH:mm:ss.SSS')}</span>`;
    let element = $(`<div>${currentTime} ${message}</div>`);
    $('#console').prepend(element);
}