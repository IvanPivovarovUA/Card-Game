const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8081/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body));
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

/////////////////////

function sendName() {
    console.log("hi0");
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify()
    });
    console.log("hi1");
}

function showGreeting(message) {

//    message.FirstPlayerId();

    console.log("hi2");


    $("#userlist").html("<li>" + message.WantToPlayUsers + "</li>");
    $("#f").html("<li>" + message.FirstPlayerId + "</li>");
    $("#s").html("<li>" + message.SecondPlayerId + "</li>");
    $("#w").html("<li>" + message.Winner + "</li>");

}
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

$(function () {
    stompClient.activate();

    $( "#start" ).click(() => sendName());

    sleep(1000).then(() => { sendName(); });

});

