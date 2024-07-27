
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8081/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/user/queue/game_table_info', (greeting) => {
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

}
function next() {
    stompClient.publish({
        destination: "/app/next",
        body: JSON.stringify()
    });
}
function setHover(index, place) {
    stompClient.publish({
        destination: "/app/hover",
        body: JSON.stringify({"index": index, "place": place})
    });
    console.log(index);
    console.log(place);
}

////////////////////////

function showGreeting(message) {

    console.log(message);

    $("#eh").html("");
    $("#et").html("");
    $("#yt").html("");
    $("#yh").html("");

    for (let i = 0; i < message.enemyHand; i++) {

        $("#eh").append(
            "<div class='card sph_card'>" +
                "<img src='test.jpg'>" +
            "</div>"
        );
    }

    for (var user in message.enemyTable) {

        var blueborder = "";

        if (message.enemyTable[user].canAttack) {
            blueborder = "blueborder";
        }

        $("#et").append(
           "<div class='card" + blueborder + "'>" +
               "<img src='test.jpg'>" +
               "<p class='hp'>" +  message.enemyTable[user].hp + "</p>" +
               "<p class='power'>" +  message.enemyTable[user].power + "</p>" +
           "</div>"
        );
    }

    for (var user in message.yourTable) {

        var blueborder = "";

        if (message.yourTable[user].canAttack) {
            blueborder = "blueborder";
        }

        $("#yt").append(
           "<div class='card" + blueborder + "'>" +
               "<img src='test.jpg'>" +
               "<p class='hp'>" + message.yourTable[user].hp + "</p>" +
               "<p class='power'>" + message.yourTable[user].power + "</p>" +
           "</div>"
        );
    }

    for (var user in message.yourHand) {
        $("#yh").append(
            "<div class='card'>" +
                "<img src='test.jpg'>" +
                "<p class='mana'>" + message.yourHand[user] + "</p>" +
                "<p class='hp'>" + message.yourHand[user] + "</p>" +
                "<p class='power'>" + message.yourHand[user] + "</p>" +
            "</div>"
        );
    }


//     $("you").html("<p id='you'> You 100 hp 10 mana </p>");
     $("#enemy").html(message.enemyNickname);
     $("#you").html(message.yourNickname);


    if (message.isYourStep) {
        $("#enemy").removeClass("greenback");
        $("#you").removeClass("yellowback");

        $("#enemy").addClass("yellowback");
        $("#you").addClass("greenback");

        setInput();
    }
    else {
        $("#enemy").removeClass("yellowback");
        $("#you").removeClass("greenback");

        $("#enemy").addClass("greenback");
        $("#you").addClass("yellowback");
    }
}

///////////////////////
function setInput() {
       $("#you").append(
            "<button type='button' id='use_card'>use card</button>" +
            "<button type='button' id='reset'>reset</button>" +
            "<button type='button' id='next'>next</button>"

       );

      $(".card").click(function(){
        setHover($(this).index(), $(this).parent().attr('id'));
      });

      $( "#next" ).click(() => next());
      //getGameTableInfo()
}
////////////////////////
$(function () {
    stompClient.activate();
//    setInput();


});

