
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
            photo = "M.png";
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
        case 'S':
            photo = "S.jpg";
            break;
        case 'm':
            photo = "mm.jpg";
            break;
        case 't':
            photo = "tt.jpg";
            break;
        case 'T':
            photo = "T.jpg";
            break;
    }
    photo = "cards/" + photo;
    return photo;
}

function isHaveArmor(card_type) {
    if (
        card_type == 'W'
        || card_type == 'Z'
        || card_type == 'E'
    ) {
        return true;
    }
    return false;
}


//export default setPhoto;
//export default isHaveArmor;