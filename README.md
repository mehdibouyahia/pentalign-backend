# Pentalign Backend

This is the backend of a multiplayer online game inspired by Tic Tac Toe, developed using Java Spring Boot. In this game, players compete on a larger grid and win by aligning 5 symbols in a row instead of 3. The backend provides REST APIs for user management, friend management, messaging, rankings, and gameplay, while also supporting real-time features like game moves and messaging using WebSockets.

## Tech Stack

- Java 21
- Spring Boot 3.4.4
- Spring Security + JWT Authentication
- Spring Data JPA
- PostgreSQL 17
- Redis 7.4 (Caching & Leaderboard optimization)
- WebSocket (Real-time communication)
- Docker (Containerization)

## Features

- User Registration & Authentication (JWT)
- Friend Management (send, accept, remove friends)
- Private Messaging between friends
- Game Management (create game, play moves, win detection)
- Real-time Gameplay using WebSocket
- Real-time Messaging using WebSocket
- Global Rankings & Leaderboard
- RESTful API structure
- Modular and scalable architecture

## Project Structure

```
src/main/java/com/xofivegame/backend/
├── config         # Configuration classes
├── controller     # REST API controllers
├── model          # JPA Entities
├── repository     # Spring Data Repositories
├── service        # Business Logic
├── websocket      # WebSocket configuration & handlers
└── XofiveBackendApplication.java
```

## Running the Project Locally

1. Clone the repository:
```bash
git clone https://github.com/mehdibouyahia/pentalign-backend.git
```

2. Navigate to backend folder:
```bash
cd pentalign-backend
```

3. Configure `application.properties` with your PostgreSQL & Redis settings.

4. Run the app:
```bash
./mvnw spring-boot:run
```

## API Documentation

> Base URL: `/api/v1`

Some main endpoints:

| Endpoint               | Method | Description                          |
|-----------------------|--------|--------------------------------------|
| /auth/register        | POST   | Register new user                   |
| /auth/login           | POST   | Login and receive JWT token         |
| /users/{id}           | GET    | Get user profile                    |
| /friends/{id}         | POST   | Send friend request                 |
| /messages             | GET    | Retrieve messages                   |
| /games                | POST   | Create new game                     |
| /games/{id}/move      | POST   | Make a move in a game               |
| /rankings             | GET    | Get leaderboard                     |

## Environment Variables

| Variable                | Description                          |
|------------------------|--------------------------------------|
| SPRING_DATASOURCE_URL   | PostgreSQL connection string        |
| SPRING_REDIS_HOST       | Redis host                          |
| JWT_SECRET              | Secret key for JWT                  |

## Docker

To build and run the backend using Docker:

```bash
docker build -t pentalign-backend .
docker run -p 8080:8080 pentalign-backend
```

---

## Author

Built with ❤️ by Elmehdi
```

