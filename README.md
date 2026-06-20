# SpaceInvaders-clone

> **VŠB-TUO** — School project · Java I & II

![Java](https://img.shields.io/badge/Java-17%2B-orange) ![Maven](https://img.shields.io/badge/Build-Maven-blue)

A two-semester recreation of the classic Space Invaders arcade game. The Java I phase delivers core gameplay with OOP design, interfaces, and collections. The Java II extension wraps it in a client-server REST architecture with JPA persistence, log4j2 logging, CompletableFuture concurrency, multilingual support, Lombok, and Maven packaging.

<p align="center">
  <img src="assets/MainMenu.png" alt="Main Menu" width="32%">
  <img src="assets/Game.png" alt="Gameplay" width="32%">
  <img src="assets/Leaderboard.png" alt="Leaderboard" width="32%">
</p>

## Features

- Classic Space Invaders gameplay
- Client-server architecture via REST
- Persistent leaderboard via JPA
- Console and file logging with log4j2
- Async processing with CompletableFuture
- Multilingual support (i18n)
- Executable JAR via Maven

## Requirements

- Java 17+
- Maven 3.x

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Firestone82/SpaceInvaders-clone.git
   cd SpaceInvaders-clone
   ```

2. Build:
   ```bash
   mvn clean package -DskipTests
   ```

3. Start the server:
   ```bash
   java -jar target/*-server.jar
   ```

4. Start the client (separate terminal):
   ```bash
   java -jar target/*-client.jar
   ```

## License

This project was created as a school assignment at VŠB-TUO.
