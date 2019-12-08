let userName = 'user' + Math.floor((Math.random() * 1000) + 1);
let driverLocation = 'lat: ' + Math.floor((Math.random() * 1000) + 1) + ", log: " + Math.floor((Math.random() * 1000) + 1);
let socket = null;
let trip = null;
let driver = null;

function login() {
    $.ajax({
        url: "https://15.188.103.133/Cuber/auth/driver/token",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify({
            email: $("#email").val(),
            password: $("#password").val()
        })
    }).done(function (data) {
        driver = data.body.entity;
        output('<span class="username-msg"> Login success... Token => <b>' + data.body.token + '</b></span>');
        $("#token").val(data.body.token);
    }).fail(function (jqXHR) {
        output('<span class="disconnect-msg">' + jqXHR.responseJSON.body + '</span>')
    });
}

function initSocket() {
    socket = io(`wss://15.188.103.133/driver?token=${$("#token").val()}`, {
        path: "/CuberSocket/socket.io",
        transports: ['websocket']
    });
    socket.on('connect', function () {
        output(`<span class="connect-msg">[connect] The driver has connected with the server. Username: ${userName}</span>`);
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
    socket.on('receive_location', function (data) {
        output(`<span class="username-msg">[receive_location] Received Client location: </span>${JSON.stringify(data)}`);
    });
    socket.on('request_trip', function (data) {
        output(`<span class="username-msg">[request_trip] Received Trip request from: </span>${JSON.stringify(data)}`);
        let client = data.client;
        $("#trip_request .text").text(`${client.first_name} ${client.last_name} / ${data.location}`);
        $( "#trip_request" ).dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true,
            buttons: {
                "On My Way": function() {
                    acceptTrip(client.id);
                    $( this ).dialog( "close" );
                },
                Cancel: function() {
                    declineTrip(client.id);
                    $( this ).dialog( "close" );
                }
            }
        });
    });
    socket.on('disconnect', function () {
        output('<span class="disconnect-msg">The client has disconnected!</span>');
    });
    socket.on('reconnect_attempt', (attempts) => {
        console.log(`Try to reconnect at ${attempts} attempt(s).`);
    });
}

function sendDisconnect() {
    if (socket) socket.disconnect();
}

function sendLocation(user) {
    console.log(user);
    let jsonObject = {user: user, location: driverLocation};
    if (socket) socket.emit('get_location', jsonObject);
}

function acceptTrip(clientID) {
    $("#trip-actions").removeClass('d-none');
    socket.emit('trip_accepted', clientID);
}

function driverArrived () {
    socket.emit('driver_arrived', trip.id);
}

function couldNotMeet () {
    socket.emit('could_not_meet', trip.id);
    finish();
}

function arrivedToDest () {
    socket.emit('arrived_to_dest', trip.id);
    finish();
}

function finish() {
    $("#trip-actions").addClass('d-none');
    trip = null;
    $("#candidate-drivers").html("");
}

function declineTrip(clientID) {
    socket.emit('trip_declined', clientID);
}

function output(message) {
    let currentTime = `<span class='time'>${moment().format('HH:mm:ss.SSS')}</span>`;
    let element = $(`<div>${currentTime} ${message}</div>`);
    $('#console').prepend(element);
}