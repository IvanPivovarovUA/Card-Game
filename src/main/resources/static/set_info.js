//
//import setPhoto from "/options.js";
//import isHaveArmor from "/options.js";

var save_message = "";


function editHtml(message) {
//    console.log(message);

    $("#eh").html("");
    $("#ed").html("");
    $("#et").html("");
    $("#yt").html("");
    $("#yd").html("");
    $("#yh").html("");

    for (let i in message.enemyInfo.dropedCards) {
        var photo = setPhoto(message.enemyInfo.dropedCards[i].type);
        $("#ed").append(
              `<div class='card'>
                  <div class='sprite' style='background-image: url("${photo}")'></div>
                  <p class='hp'>${message.enemyInfo.dropedCards[i].hp}</p>
                  <p class='power'>${message.enemyInfo.dropedCards[i].power}</p>
              </div>`
        );
    }

    for (let i in message.yourInfo.dropedCards) {
        var photo = setPhoto(message.yourInfo.dropedCards[i].type);
        $("#yd").append(
           `<div class='card'>
               <div class='sprite' style='background-image: url("${photo}")'></div>
               <p class='hp'>${message.yourInfo.dropedCards[i].hp}</p>
               <p class='power'>${message.yourInfo.dropedCards[i].power}</p>
           </div>`
        );
    }

    for (let i = 0; i < message.enemyInfo.hand; i++) {
        var photo = setPhoto('C');
        var hoverborder = "";

        if (!message.isYourStep) {
            if (message.hover.hand == i) {
                hoverborder += " yellowborder";
            }
        }
        $("#eh").append(
            `<div class='card ${hoverborder}'>
                <div class='sprite' style='background-image: url("${photo}")'></div>
            </div>`
        );
    }
    for (var i in message.enemyInfo.table) {
        var photo = setPhoto(message.enemyInfo.table[i].type);
        var hoverborder = "";

        if (isHaveArmor(message.enemyInfo.table[i].type)) {
            hoverborder += " blackborder";
        }

        if (message.enemyInfo.table[i].canAttack) {
            hoverborder += " blueborder";
        }

        if (!message.isYourStep) {
            if (message.hover.table == i) {
                hoverborder += " yellowborder ";
            }
        }
        if (message.isYourStep) {
            if (message.hover.enemy == i) {
                hoverborder += " yellowborder ";
            }
        }

        var d = 0;
        var s = "";
        if (save_message != "") {
            for (var c in save_message.enemyInfo.table) {
                if (
                    save_message.enemyInfo.table[c].id == message.enemyInfo.table[i].id
                    && save_message.enemyInfo.table[c].hp != message.enemyInfo.table[i].hp
                ) {
                    d = - save_message.enemyInfo.table[c].hp + message.enemyInfo.table[i].hp
                    hoverborder += " redborder";
                    s = " show ";
                }
            }
        }

        $("#et").append(
           `<div class='card ${hoverborder}'>
               <div class='sprite' style='background-image: url("${photo}")'></div>
               <p class='hp'>${message.enemyInfo.table[i].hp}</p>
               <p class='power'>${message.enemyInfo.table[i].power}</p>
               <div class='hitblock ${s}'> ${d} </div>
           </div>`
        );
    }
    for (var i in message.yourInfo.table) {
        var photo = setPhoto(message.yourInfo.table[i].type);
        var hoverborder = "";

        if (isHaveArmor(message.yourInfo.table[i].type)) {
            hoverborder += " blackborder";
        }

        if (message.yourInfo.table[i].canAttack) {
            hoverborder += " blueborder";
        }

        if (message.isYourStep) {
            if (message.hover.table == i) {
                hoverborder += " yellowborder";
            }
        }
        if (!message.isYourStep) {
            if (message.hover.enemy == i) {
                hoverborder += " yellowborder";
            }
        }

        var d = 0;
        var s = "";
        if (save_message != "") {
            for (var c in save_message.yourInfo.table) {
                 if (
                     save_message.yourInfo.table[c].id == message.yourInfo.table[i].id
                     && save_message.yourInfo.table[c].hp != message.yourInfo.table[i].hp
                 ) {
                    d = - save_message.yourInfo.table[c].hp + message.yourInfo.table[i].hp;
                    hoverborder += " redborder";
                    s = " show ";
                 }
            }
        }

        $("#yt").append(
           `<div class='card ${hoverborder}'>
               <div class='sprite' style='background-image: url("${photo}")'></div>
               <p class='hp'>${message.yourInfo.table[i].hp}</p>
               <p class='power'>${message.yourInfo.table[i].power}</p>
               <div class='hitblock ${s}'> ${d} </div>
           </div>`
        );
    }
    for (var i in message.yourInfo.hand) {
        var photo = setPhoto(message.yourInfo.hand[i].type);
        var hoverborder = "";

        if (isHaveArmor(message.yourInfo.hand[i].type)) {
           hoverborder += " blackborder";
        }

        if (message.isYourStep) {
            if (message.hover.hand == i) {
                hoverborder += " yellowborder";
            }
        }
        $("#yh").append(
            `<div class='card ${hoverborder}'>
                <div class='sprite' style='background-image: url("${photo}")'></div>
                <p class='mana'>${message.yourInfo.hand[i].mana}</p>
                <p class='hp'>${message.yourInfo.hand[i].hp}</p>
                <p class='power'>${message.yourInfo.hand[i].power}</p>
            </div>`
        );
    }


    editPlayerBord(message);
    customizeButtons(message);
    setPlayerHover(message);

    setHitHover(message);



    if (save_message != "") {
       for (let i in save_message.enemyInfo.table) {

          var t = true;
          for (let c in message.enemyInfo.table) {
                if (save_message.enemyInfo.table[i].id == message.enemyInfo.table[c].id) {
                    t = false;
                }
          }

          if (t) {
             var photo = setPhoto(save_message.enemyInfo.table[i].type);
             $("#ed").append(
                   `<div class='card'>
                       <div class='sprite' style='background-image: url("${photo}")'></div>
                       <p class='hp'>${save_message.enemyInfo.table[i].hp}</p>
                       <p class='power'>${save_message.enemyInfo.table[i].power}</p>
                   </div>`
             );
          }

       }

       for (let i in save_message.yourInfo.table) {

          var t = true;
          for (let c in message.yourInfo.table) {
                if (save_message.yourInfo.table[i].id == message.yourInfo.table[c].id) {
                    t = false;
                }
          }

            if (t) {
                var photo = setPhoto(save_message.yourInfo.table[i].type);
                $("#yd").append(
                    `<div class='card'>
                     <div class='sprite' style='background-image: url("${photo}")'></div>
                     <p class='hp'>${save_message.yourInfo.table[i].hp}</p>
                     <p class='power'>${save_message.yourInfo.table[i].power}</p>
                </div>`
                );
            }

       }
    }


//
//    setTimeout(
//        () => {
//            clearHitHover();
//        },
//        5000
//    );

     save_message = message;
}

function setHitHover(message) {
    if (
        save_message != ""
    ) {
        if (save_message.yourInfo.hp != message.yourInfo.hp) {
            $("#you").addClass("redborder");
            $("#you .hitblock_for_playerbord").addClass("show");
            $("#you .hitblock_for_playerbord").html(-save_message.yourInfo.hp + message.yourInfo.hp);

        }
        if (save_message.enemyInfo.hp != message.enemyInfo.hp) {
            $("#enemy").addClass("redborder");
            $("#enemy .hitblock_for_playerbord").addClass("show");
            $("#enemy .hitblock_for_playerbord").html(-save_message.enemyInfo.hp + message.enemyInfo.hp);
        }


    }
}

//function clearHitHover() {
//    console.log("ky!!!");
//    $("#you").removeClass("redborder");
//    $("#enemy").removeClass("redborder");
//    $(".card").removeClass("redborder");
//    $(".hitblock").removeClass("show");
//    $(".hitblock_for_playerbord").removeClass("show");
//}




function editPlayerBord(message) {
    $(".hitblock_for_playerbord").removeClass("show");
    $("#you").removeClass("redborder");
    $("#enemy").removeClass("redborder");

    $("#yn").html(message.yourInfo.nickname);
    $("#yhp").html(message.yourInfo.hp);
    $("#ym").html(message.yourInfo.mana);

    $("#en").html(message.enemyInfo.nickname);
    $("#ehp").html(message.enemyInfo.hp);
    $("#em").html(message.enemyInfo.mana);

}


function customizeButtons(message) {
    if (message.isYourStep) {
        setInput();
    }
    else {
        setFadedButtons();
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


function setPlayerHover(message) {
    $("#enemy").removeClass("yellowborder");
    $("#you").removeClass("yellowborder");


    if (message.hover.player) {
        if (message.isYourStep) {
            $("#enemy").addClass("yellowborder");
        }
        else {
            $("#you").addClass("yellowborder");
        }
    }

}


//
//export default editHtml;
