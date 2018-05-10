var elements = document.getElementsByClassName('controller')
for (var i = 0; i < elements.length; i++) {
    elements[i].addEventListener('click', function () {
        writeToDatabase(this.id)
    })
}

window.onkeyup = function (event) {
    var key = event.keyCode ? event.keyCode : event.which;
    console.log(key)
    switch (key) {
        case 37:
            writeToDatabase('LEFT')
            break
        case 38:
            writeToDatabase('TOP')
            break
        case 39:
            writeToDatabase('RIGHT')
            break
        case 40:
            writeToDatabase('BOTTOM')
            break
    }
}

function writeToDatabase(value) {
    firebase.database().ref('direction').set(value);
}