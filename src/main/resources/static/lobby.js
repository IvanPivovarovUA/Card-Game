const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8081/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/user_list', (greeting) => {
        showGreeting(JSON.parse(greeting.body));
    });

    getUserList();
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

/////////////////////

function getUserList() {
    stompClient.publish({
        destination: "/app/get_lobby_info",
        body: JSON.stringify()
    });
}
function start() {
    stompClient.publish({
        destination: "/app/start",
        body: JSON.stringify()
    });
}

function showGreeting(message) {

    $("#userlist").html("");
    for (var user in message.lobby) {
        $("#userlist").append("<li>" + message.lobby[user] + "</li>");
    }

    $("#f").html("<li>" + message.firstPlayerId + "</li>");
    $("#s").html("<li>" + message.secondPlayerId + "</li>");
    $("#w").html("<li>" + message.winner + "</li>");

}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

$(function () {
    stompClient.activate();

    $( "#start" ).click(() => start());



});




