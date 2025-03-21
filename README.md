## Game netty server demo

# Attention! Highload java game server framework being in active development process now is available for usage and contribution.  If you like It - support this tool. https://github.com/tfkfan/orbital

Java highload game server demo with netty features
and matchmaking support

### Build and run

```
./mvnw clean verify spring-boot:run
```

### Properties

Server uses spring-boot-web to start http server, 
therefore - all spring boot properties are available for usage.
Custom properties are declared as ApplicationProperties.java.

application.yml:
```
application:
  server:
    port: 8081
    event-loop-threads: 1
    worker-threads: 2
    game-threads: 4
  room:
    start-delay: 5000
    max-players: 1
    loop-rate: 100
```
Port - particular websocket server port (not a web tomcat container port)

Event-loop-threads - master event loop threads used in netty server bootstrap setup

Worker-threads - worker event loop threads used in netty server bootstrap setup

Game-threads -  scheduled thread executor N threads adjusting game room scheduled events

Start-delay - battle start delay

Max-players - max players per room

Loop-rate - game logic loop fixed rate

### Html example
Simple html/js example already included as a static web resources,
just look at localhost:8080. Click login button and wait for game room and match start

