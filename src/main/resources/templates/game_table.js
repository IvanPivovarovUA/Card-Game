
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

///////////////////////

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
function setHover(index, place) {
//    stompClient.publish({
//        destination: "/app/put_card",
//        body: JSON.stringify({"index": 0, "place": 0})
//    });
    console.log(index);
    console.log(place);
}

////////////////////////

function showGreeting(message) {

    console.log(message);

    $("#sp").html(message.lobby.secondPlayerId);
    $("#fp").html(message.lobby.firstPlayerId);

    $("#sph").html("");
    $("#fph").html("");
    $("#spt").html("");
    $("#fpt").html("");

    for (var user in message.secondPlayer.cardsOnHand) {
        $("#sph").append(
            "<div class='card sph_card'>" +
                "<img src='test.jpg'>" +
                "<p class='mana'>" + message.secondPlayer.cardsOnHand[user] + "</p>" +
                "<p class='hp'>" + message.secondPlayer.cardsOnHand[user] + "</p>" +
                "<p class='power'>" + message.secondPlayer.cardsOnHand[user] + "</p>" +
            "</div>"
        );
    }
    for (var user in message.secondPlayer.cardsOnTable) {

        var blueborder = "";

        if (message.secondPlayer.cardsOnTable[user].canAttack) {
            blueborder = "blueborder";
        }

        $("#spt").append(
           "<div class='card" + blueborder + "'>" +
               "<img src='test.jpg'>" +
               "<p class='hp'>" + message.secondPlayer.cardsOnTable[user].hp + "</p>" +
               "<p class='power'>" + message.secondPlayer.cardsOnTable[user].power + "</p>" +
           "</div>"
        );
    }

    for (var user in message.firstPlayer.cardsOnTable) {

        var blueborder = "";

        if (message.firstPlayer.cardsOnTable[user].canAttack) {
            blueborder = "blueborder";
        }

        $("#fpt").append(
           "<div class='card" + blueborder + "'>" +
               "<img src='test.jpg'>" +
               "<p class='hp'>" + message.firstPlayer.cardsOnTable[user].hp + "</p>" +
               "<p class='power'>" + message.firstPlayer.cardsOnTable[user].power + "</p>" +
           "</div>"
        );

    }


    for (var user in message.firstPlayer.cardsOnHand) {
        $("#fph").append(
            "<div class='card'>" +
                "<img src='test.jpg'>" +
                "<p class='mana'>" + message.firstPlayer.cardsOnHand[user] + "</p>" +
                "<p class='hp'>" + message.firstPlayer.cardsOnHand[user] + "</p>" +
                "<p class='power'>" + message.firstPlayer.cardsOnHand[user] + "</p>" +
            "</div>"
        );
    }

    setInput();
}


function setInput() {
      $(".card").click(function(){
        setHover($(this).index(), $(this).parent().attr('id'));
      });
}

$(function () {
    stompClient.activate();

    $( "#next" ).click(() => next());

});

