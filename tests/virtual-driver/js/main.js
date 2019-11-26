var userName = 'user' + Math.floor((Math.random() * 1000) + 1);
var driverLocation = 'lat: ' + Math.floor((Math.random() * 1000) + 1) + ", log: " + Math.floor((Math.random() * 1000) + 1);
var socket = null;

function login() {
    $.ajax({
        url: "http://localhost:8080/cuber/auth/driver/token",
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
    socket = io('http://localhost:9092/driver?token=' + $("#token").val(), {
        transports: ['polling', 'websocket']
    });
    socket.on('connect', function () {
        output('<span class="connect-msg">The driver has connected with the server. Username: ' + userName + '</span>');
    });
    socket.on('request_location', function (user) {
        output('<span class="username-msg">Received Location request from: </span>' + JSON.stringify(user));
        sendLocation(user);
    });
    socket.on('request_trip', function (data) {
        output('<span class="username-msg">Received Trip request from: </span>' + JSON.stringify(data));
        let client = data.client;
        $("#trip_request .text").text(`${client.first_name} ${client.last_name} / ${data.location}`);
        $( "#trip_request" ).dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true,
            buttons: {
                "On My Way": function() {
                    $( this ).dialog( "close" );
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            }
        });
    });
    socket.on('disconnect', function () {
        output('<span class="disconnect-msg">The client has disconnected!</span>');
    });
    socket.on('reconnect_attempt', (attempts) => {
        console.log('Try to reconnect at ' + attempts + ' attempt(s).');
    });
}

function sendDisconnect() {
    if (socket) socket.disconnect();
}

function sendLocation(user) {
    var jsonObject = {user: user, location: driverLocation};
    if (socket) socket.emit('get_location', jsonObject);
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