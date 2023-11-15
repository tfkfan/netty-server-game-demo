var cnvs = document.getElementById("cnvs");
cnvs.width = window.innerWidth - 350;
cnvs.height = window.innerHeight - 50;

var initMsg = document.getElementById("initMsg");
var updateMsg = document.getElementById("updateMsg");
var playerStats = document.getElementById("playerStats");
var debugMsg = document.getElementById("debugMsg");

var ctx = cnvs.getContext("2d");


let upKeys = {'w': true, 'ц': true, 'W': true, 'Ц': true};
let downKeys = {'s': true, 'ы': true, 'S': true, 'Ы': true};
let rightKeys = {'d': true, 'D': true, 'В': true, 'в': true};
let leftKeys = {'a': true, 'A': true, 'ф': true, 'Ф': true};
let players = {};
let selfId = null;
let text = null;

function drawText(x, y, text) {
    ctx.font = "48px serif";
    const lines = text.split('\n')
    for (let i = 0; i < lines.length; i++)
        ctx.strokeText(lines[i], x, y + (i * 48));
}

function drawPlayer(x, y) {
    ctx.beginPath();
    ctx.arc(x, y, 50, 0, 2 * Math.PI);
    ctx.stroke();
}

function clear() {
    ctx.clearRect(0, 0, cnvs.width, cnvs.height);
}

function drawPlayers() {
    for (let i in players) {
        let p = players[i];
        drawPlayer(p.position.x, p.position.y);
    }
}

document.onkeydown = function (event) {
    text = null
    if (upKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "UP", state: true});
    if (downKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "DOWN", state: true});
    if (rightKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "RIGHT", state: true});
    if (leftKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "LEFT", state: true});
};

document.onkeyup = function (event) {
    if (upKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "UP", state: false});
    if (downKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "DOWN", state: false});
    if (rightKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "RIGHT", state: false});
    if (leftKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "LEFT", state: false});
};

document.getElementById("loginBtn").onclick = function () {
    send(AUTHENTICATION, {"BEARER_TOKEN": "token1"});
}
document.getElementById("joinBtn").onclick = function () {
    send(CONNECT, {});
}
initializeWebsocket();
on(CONNECT_WAIT, function (evt) {
    debugMsg.innerText = "Wait"
});
on(AUTHENTICATION, function (evt) {
    debugMsg.innerText = "Authorized"
    document.getElementById("joinBtn").removeAttribute("disabled");
    document.getElementById("loginBtn").setAttribute("disabled", "disabled");
});
on(CONNECT_SUCCESS, function (evt) {
    debugMsg.innerText = "Connected to game room"
    document.getElementById("joinBtn").setAttribute("disabled", "disabled");
});
on(ROOM_START, function (evt) {
    debugMsg.innerText = "Room started, please wait for battle start"
});
on(BATTLE_START, function (evt) {
    const msg = "Battle started. Click on battlefield.\nUse WASD to move."
    debugMsg.innerText = msg
    text = msg
});
on(FAILURE, function (evt) {
    debugMsg.innerText = "Internal error occurred"
});

on(INIT, function (evt) {
    initMsg.innerText = JSON.stringify(evt);
});

on(UPDATE, function (evt) {
    clear();
    players = evt.players.reduce(function (map, obj) {
        map[obj.id] = obj;
        return map;
    }, {});
    selfId = evt.player.id;
    playerStats.innerText = JSON.stringify(players[selfId]);
    updateMsg.innerText = JSON.stringify(evt);
    clear();
    drawPlayers();
    if (text)
        drawText(200, 300, text)
});