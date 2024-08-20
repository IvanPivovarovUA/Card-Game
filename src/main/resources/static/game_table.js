
//import editHtml from "./set_info.js"

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8081/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/user/queue/game_table_info', (greeting) => {
        editHtml(JSON.parse(greeting.body));
    });

    stompClient.subscribe('/queue/redirect_to_lobby', (greeting) => {
        redirectToLobby();
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
    console.log("w");
    stompClient.publish({
        destination: "/app/next",
        body: JSON.stringify()
    });
}


function setHover(index, place) {

    if (place == "yh") {
        place = 'H';
    }
    if (place == "yt") {
        place = 'T';
    }
    if (place == "et") {
        place = 'E';
    }
    if (place == "P") {
        place = 'P';
    }

    stompClient.publish({
        destination: "/app/hover",
        body: JSON.stringify({"index": index, "place": place})
    });

}

function reset() {
    console.log("e");
    stompClient.publish({
        destination: "/app/reset",
        body: JSON.stringify()
    });
}

function use_card() {
    console.log("e");
    stompClient.publish({
        destination: "/app/use_card",
        body: JSON.stringify()
    });
}

function redirectToLobby() {
    document.location.href = "http://localhost:8081/lobby";
}


////////////////////////
$(function () {
    stompClient.activate();

    $("#enemy").click(function(){
        setHover($(this).index(), "P");
    });
});

