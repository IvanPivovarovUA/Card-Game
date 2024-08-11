//
//import setPhoto from "/options.js";
//import isHaveArmor from "/options.js";

function editHtml(message) {
    console.log(message);

    $("#eh").html("");
    $("#ed").html("");
    $("#et").html("");
    $("#yt").html("");
    $("#yd").html("");
    $("#yh").html("");

    for (i in message.enemyInfo.dropedCards) {
        var photo = setPhoto(message.enemyInfo.dropedCards[i].type);
        $("#ed").append(
           "<div class='card'>" +
               "<img src='" + photo + "'>" +
               "<p class='hp'>" +  message.enemyInfo.dropedCards[i].hp + "</p>" +
               "<p class='power'>" +  message.enemyInfo.dropedCards[i].power + "</p>" +
           "</div>"
        );
    }
    for (let i in message.yourInfo.dropedCards) {
        var photo = setPhoto(message.yourInfo.dropedCards[i].type);
        $("#yd").append(
           "<div class='card'>" +
               "<img src='" + photo + "'>" +
               "<p class='hp'>" +  message.yourInfo.dropedCards[i].hp + "</p>" +
               "<p class='power'>" +  message.yourInfo.dropedCards[i].power + "</p>" +
           "</div>"
        );
    }
    for (let i = 0; i < message.enemyInfo.hand; i++) {
        var photo = setPhoto('C');
        var hoverborder = "";

        if (!message.isYourStep) {
            if (message.hover.hand == i) {
                hoverborder = "yellowborder ";
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
            hoverborder = "blackborder";
        }

        if (message.enemyInfo.table[i].canAttack) {
            hoverborder = "blueborder";
        }

        if (!message.isYourStep) {
            if (message.hover.table == i) {
                hoverborder = "yellowborder ";
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
                hoverborder = "yellowborder";
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
                hoverborder = "yellowborder ";
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


    $("#enemy").html(
        '<div class="box1"></div>' +
        '<div class="box2">' + message.enemyInfo.nickname + '</div>' +
        '<div class="box3">Hp:' + message.enemyInfo.hp + '</div>' +
        '<div class="box4">Mana:' + message.enemyInfo.mana + '</div>'
    );
    $("#you").html(
        '<div class="box1"></div>' +
        '<div class="box2">' + message.yourInfo.nickname + '</div>' +
        '<div class="box3">Hp:' + message.yourInfo.hp + '</div>' +
        '<div class="box4">Mana:' + message.yourInfo.mana + '</div>'
    );


    $("#enemy").removeClass("redborder");
    $("#you").removeClass("redborder");
    if (message.isYourStep) {
        setInput();

        if (message.hover.player) {
            $("#enemy").addClass("redborder");
        }
    }
    else {
        setFadedButtons();

        if (message.hover.player) {
            $("#you").addClass("redborder");
        }
    }
}

function setInput() {
    $(".strip").html(
        "<button type='button' id='usecard' class='active_button'>use card</button>" +
        "<button type='button' id='reset' class='active_button'>reset</button>" +
        "<button type='button' id='next' class='active_button'>next</button>"
    );

    $(".card").click(function(){
        setHover($(this).index(), $(this).parent().attr('id'));
    });

    $( "#usecard" ).click(() => use_card());
    $( "#next" ).click(() => next());
    $( "#reset" ).click(() => reset());
}

function setFadedButtons() {
        $(".strip").html(
            "<div type='button' id='usecard' class='faded_button'>use card</div>" +
            "<div type='button' id='reset' class='faded_button'>reset</div>" +
            "<div type='button' id='next' class='faded_button'>next</div>"
        );
}

//
//export default editHtml;
