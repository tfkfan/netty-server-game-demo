var cnvs = document.getElementById("cnvs");
cnvs.width  = window.innerWidth - 350;
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

function drawPlayer(x, y) {
    ctx.beginPath();
    ctx.arc(x, y, 50, 0, 2 * Math.PI);
    ctx.stroke();
}

function clear() {
    ctx.clearRect(0, 0,cnvs.width , cnvs.height );
}

function drawPlayers() {
    for (let i in players) {
        let p = players[i];
        drawPlayer(p.position.x, p.position.y);
    }
}

document.onkeydown = (event) => {
    if (upKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "UP", state: true});
    if (downKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "DOWN", state: true});
    if (rightKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "RIGHT", state: true});
    if (leftKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "LEFT", state: true});
};

document.onkeyup = (event) => {
    if (upKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "UP", state: false});
    if (downKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "DOWN", state: false});
    if (rightKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "RIGHT", state: false});
    if (leftKeys[event.key])
        send(PLAYER_KEY_DOWN, {inputId: "LEFT", state: false});
};

document.getElementById("loginBtn").onclick = () => {
    send(CONNECT, {});
}

initializeWebsocket((evt) => {
    const eventData = JSON.parse(evt.data);
    console.log(`Message ${eventData.type} accepted`);
    if (eventData.type === CONNECT_WAIT) {
        debugMsg.innerText = "Wait"
    }
    if (eventData.type === CONNECT_SUCCESS) {
        debugMsg.innerText = "Connected to game room"
    }
    if (eventData.type === ROOM_START) {
        debugMsg.innerText = "Room started, please wait for battle start"
    }
    if (eventData.type === BATTLE_START) {
        debugMsg.innerText = "Battle started"
    }
    if (eventData.type === INIT) {
        initMsg.innerText = JSON.stringify(eventData.data);
    }
    if (eventData.type === UPDATE) {
        clear();
        players = eventData.data.players.reduce( (map, obj) =>{
            map[obj.id] = obj;
            return map;
        }, {});
        selfId = eventData.data.player.id;
        playerStats.innerText = JSON.stringify(players[selfId]);
        updateMsg.innerText = JSON.stringify(eventData.data);
        clear();
        drawPlayers();
    }
})

