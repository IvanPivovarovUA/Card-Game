
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
    console.log(index);
    console.log(place);
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

////////////////////////
function setPhoto(card_type) {
    var photo = "";
    switch (card_type) {
        case 'C':
            photo = "C.jpg";
            break;
        case 'W':
            photo = "W.jpg";
            break;
        case 'Z':
            photo = "Z.webp";
            break;
        case 'K':
            photo = "K.jpg";
            break;
        case 'V':
            photo = "V.jpg";
            break;
        case 'B':
            photo = "B.jfif";
            break;
        case 'R':
            photo = "R.jpg";
            break;
        case 'r':
            photo = "rr.jfif";
            break;
        case 'L':
            photo = "L.jpg";
            break;
        case 'l':
            photo = "ll.jpg";
            break;
        case 'F':
            photo = "F.jpg";
            break;
        case 'A':
            photo = "A.webp";
            break;
        case 'M':
            photo = "M.jpg";
            break;
        case 'I':
            photo = "I.webp";
            break;
        case 'z':
            photo = "z.jpg";
            break;
        case 'T':
            photo = "T.jpg";
            break;
        case 'P':
            photo = "P.jpg";
            break;
        case 'H':
            photo = "H.jfif";
            break;
        case 'E':
            photo = "E.jpg";
            break;

    }
    photo = "cards/" + photo;
    return photo;
}

function isHaveArmor(card_type) {
    if (card_type == 'W' || card_type == 'Z') {
        return true;
    }
    return false;
}


function showGreeting(message) {

//    console.log(message);

    $("#eh").html("");
    $("#et").html("");
    $("#yt").html("");
    $("#yh").html("");

    for (let i = 0; i < message.enemyInfo.hand; i++) {
        var photo = setPhoto('C');
        var hoverborder = "";

        if (!message.isYourStep) {
            if (message.hover.hand == i) {
                hoverborder = "greenborder";
            }
        }

        $("#eh").append(
            "<div class='card " + hoverborder + "'>" +
                "<img src='" + photo + "'>" +
            "</div>"
        );
    }

    for (var i in message.enemyInfo.table) {
        var photo = setPhoto(message.enemyInfo.table[i].type);
        var hoverborder = "";

        if (isHaveArmor(message.enemyInfo.table[i].type)) {
            hoverborder = " blackborder";
        }

        if (message.enemyInfo.table[i].canAttack) {
            hoverborder = "blueborder";
        }

        if (!message.isYourStep) {
            if (message.hover.table == i) {
                hoverborder = "greenborder";
            }
        }
        if (message.isYourStep) {
            if (message.hover.enemy == i) {
                hoverborder = "redborder";
            }
        }



        $("#et").append(
           "<div class='card " + hoverborder + "'>" +
               "<img src='" + photo + "'>" +
               "<p class='hp'>" +  message.enemyInfo.table[i].hp + "</p>" +
               "<p class='power'>" +  message.enemyInfo.table[i].power + "</p>" +
           "</div>"
        );
    }

    for (var i in message.yourInfo.table) {
        var photo = setPhoto(message.yourInfo.table[i].type);
        var hoverborder = "";

        if (isHaveArmor(message.yourInfo.table[i].type)) {
            hoverborder = "blackborder";
        }

        if (message.yourInfo.table[i].canAttack) {
            hoverborder = "blueborder";
        }
        if (message.isYourStep) {
            if (message.hover.table == i) {
                hoverborder = "greenborder";
            }
        }
        if (!message.isYourStep) {
            if (message.hover.enemy == i) {
                hoverborder = "redborder";
            }
        }



        $("#yt").append(
           "<div class='card " + hoverborder + "'>" +
               "<img src='" + photo + "'>" +
               "<p class='hp'>" + message.yourInfo.table[i].hp + "</p>" +
               "<p class='power'>" + message.yourInfo.table[i].power + "</p>" +
           "</div>"
        );
    }

    for (var i in message.yourInfo.hand) {
        var photo = setPhoto(message.yourInfo.hand[i].type);
        var hoverborder = "";

        if (isHaveArmor(message.yourInfo.hand[i].type)) {
           hoverborder = " blackborder";
        }

        if (message.isYourStep) {
            if (message.hover.hand == i) {
                hoverborder = "greenborder";
            }
        }


        $("#yh").append(
            "<div class='card " + hoverborder + "'>" +
                "<img src='" + photo + "'>" +
                "<p class='mana'>" + message.yourInfo.hand[i].mana + "</p>" +
                "<p class='hp'>" + message.yourInfo.hand[i].hp + "</p>" +
                "<p class='power'>" + message.yourInfo.hand[i].power + "</p>" +
            "</div>"
        );
    }

//     $("you").html("<p id='you'> You 100 hp 10 mana </p>");
    $("#enemy").html(
        "<p>" + message.enemyInfo.nickname + "</p>" +
        "<p class='redback'>" + message.enemyInfo.hp + "</p>" +
        "<p class='blueback'>" + message.enemyInfo.mana + "</p>"
    );

    $("#you").html(
        "<p>" + message.yourInfo.nickname + "</p>" +
        "<p class='redback'>" + message.yourInfo.hp + "</p>" +
        "<p class='blueback'>" + message.yourInfo.mana + "</p>"
    );


    $("#enemy").removeClass("redborder");
    $("#you").removeClass("redborder");

    if (message.isYourStep) {
        $("#enemy").removeClass("greenback");
        $("#you").removeClass("yellowback");

        $("#enemy").addClass("yellowback");
        $("#you").addClass("greenback");

        setInput();

        if (message.hover.player) {
            $("#enemy").addClass("redborder");
        }
    }
    else {
        $("#enemy").removeClass("yellowback");
        $("#you").removeClass("greenback");

        $("#enemy").addClass("greenback");
        $("#you").addClass("yellowback");

        if (message.hover.player) {
            $("#you").addClass("redborder");
        }
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

    $( "#use_card" ).click(() => use_card());
    $( "#next" ).click(() => next());
    $( "#reset" ).click(() => reset());
    //getGameTableInfo()
}

////////////////////////
$(function () {
    stompClient.activate();

    $("#enemy").click(function(){
        setHover($(this).index(), "P");
    });
});

