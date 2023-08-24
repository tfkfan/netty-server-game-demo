var socket;

function initializeWebsocket(listener) {
    if (!window.WebSocket) {
        // @ts-ignore
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        socket = new WebSocket("ws://localhost:8081/websocket");
    } else {
        alert("Your browser does not support Web Socket.");
    }

    socket.addEventListener('open', () => {
        console.log("Connection established");
    });

    socket.addEventListener('error', (event) => {
        console.log(event.message);
    });

    socket.addEventListener('close', () => {
        console.log("Web Socket closed");
    });

    socket.addEventListener('message', listener);
}

function send(type, data) {
    if (socket.readyState !== WebSocket.OPEN) {
        console.log("Socket is not ready");
        return;
    }

    socket.send(createEvent(type, data));
}

function createEvent(eventType, payload) {
    const obj = {
        type: eventType,
        data: null
    };
    if (payload) {
        obj.data = payload
    }
    return JSON.stringify(obj);
}
