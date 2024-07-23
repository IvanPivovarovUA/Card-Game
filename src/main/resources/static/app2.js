const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8081/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/game_table_info', (greeting) => {
        showGreeting(JSON.parse(greeting.body));
    });

    getGameTableInfo();

};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

/////////////////////

function getGameTableInfo() {
    stompClient.publish({
        destination: "/app/get_game_table_info",
        body: JSON.stringify()
    });
    console.log("startsdfgs");
}
function next() {
    stompClient.publish({
        destination: "/app/next",
        body: JSON.stringify()
    });
}
function put_card() {
    stompClient.publish({
        destination: "/app/put_card",
        body: JSON.stringify({"mainCardId": 0, "workCardId": 0})
    });
}
function card_attack() {
    stompClient.publish({
        destination: "/app/card_attack",
        body: JSON.stringify({"mainCardId": 0, "workCardId": 0})
    });
}

function showGreeting(message) {

    console.log(message);

    $("#sp").html(message.lobbyEntity.secondPlayerId);
    $("#fp").html(message.lobbyEntity.firstPlayerId);

    $("#sph").html("");
    $("#fph").html("");
    $("#spt").html("");
    $("#fpt").html("");

    for (var user in message.secondPlayer.cardsOnHand) {
        $("#sph").append("<li>" + message.secondPlayer.cardsOnHand[user] + "</li>");
    }
    for (var user in message.firstPlayer.cardsOnHand) {
        $("#fph").append("<li>" + message.firstPlayer.cardsOnHand[user] + "</li>");
    }

    for (var user in message.secondPlayer.cardsOnTable) {
        $("#spt").append("<li>" + message.secondPlayer.cardsOnTable[user].hp + message.secondPlayer.cardsOnTable[user].power + "</li>");
    }
    for (var user in message.firstPlayer.cardsOnTable) {
        $("#fpt").append("<li>" + message.firstPlayer.cardsOnTable[user].hp + message.firstPlayer.cardsOnTable[user].power  + "</li>");
    }

}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

$(function () {
    stompClient.activate();

    $( "#next" ).click(() => next());
    $( "#use_card").click(() => put_card());
    $( "#reset").click(() => card_attack());

//    sleep(1000).then(() => { getGameTableInfo(); });

});

