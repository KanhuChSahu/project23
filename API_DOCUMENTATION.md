# Fair Rummy API Documentation

## Base URLs
- Auth Service: `http://localhost:8081`
- Player Service: `http://localhost:8082`
- Game Service: `http://localhost:8083`

## Testing Flow Sequence

1. Generate OTP (Authentication)
2. Verify OTP and obtain JWT token
3. Create player profile
4. Join game room
5. Play game
6. View game history

## Prerequisites for Testing
- Postman or similar API testing tool
- Valid phone number for OTP verification
- JWT token (obtained after OTP verification)

## Authentication Service APIs

### 1. Generate OTP
```http
POST /api/auth/generate-otp
Content-Type: application/json
Authorization: Bearer <access_token>
Device-Id: <device_id>

Request Body:
{
    "mobileNumber": "1234567890"
}

Response (200 OK):
{
    "message": "OTP sent successfully"
}

Response (401 Unauthorized):
{
    "message": "Invalid or expired token"
}
```

### Verify OTP
```http
POST /api/v1/login/authenticate
Content-Type: application/json

Request Body:
{
    "mobileNo": "1234567890",
    "otp": "123456"
}

Response (200 OK):
{
    "error": false,
    "errorCode": 1000,
    "message": "Authenticated successfully",
    "data": {
        "sessionId": "550e8400-e29b-41d4-a716-446655440000",
        "authToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    }
}

Response (400 Bad Request):
{
    "error": true,
    "errorCode": 2004,
    "message": "Invalid or expired OTP",
    "data": null
}
```

## Player Service APIs

### 3. Create Player Profile
```http
POST /api/players
Authorization: Bearer <jwt_token>
Content-Type: application/json

Request Body:
{
    "username": "player123",
    "fullName": "John Doe",
    "email": "john@example.com",
    "dateOfBirth": "1990-01-01"
}

Response (201 Created):
{
    "playerId": "12345",
    "username": "player123",
    "fullName": "John Doe",
    "email": "john@example.com",
    "dateOfBirth": "1990-01-01",
    "createdAt": "2024-01-20T10:30:00Z"
}

Response (400 Bad Request):
{
    "error": "Username already exists",
    "timestamp": "2024-01-20T10:30:00Z"
}
```

### Get Player Profile
```http
GET /api/players/{playerId}
Authorization: Bearer <jwt_token>

Response (200 OK):
{
    "playerId": "12345",
    "username": "player123",
    "fullName": "John Doe",
    "email": "john@example.com",
    "dateOfBirth": "1990-01-01",
    "createdAt": "2024-01-20T10:30:00Z",
    "lastLoginAt": "2024-01-20T10:30:00Z"
}

Response (404 Not Found):
{
    "error": "Player not found",
    "timestamp": "2024-01-20T10:30:00Z"
}
```

### Update Player Profile
```http
PUT /api/players/{playerId}
Authorization: Bearer <jwt_token>
Content-Type: application/json

Request Body:
{
    "fullName": "John Smith",
    "email": "john.smith@example.com"
}

Response (200 OK):
{
    "playerId": "12345",
    "username": "player123",
    "fullName": "John Smith",
    "email": "john.smith@example.com",
    "dateOfBirth": "1990-01-01",
    "updatedAt": "2024-01-20T10:35:00Z"
}

Response (404 Not Found):
{
    "error": "Player not found",
    "timestamp": "2024-01-20T10:35:00Z"
}
```

## Game Service APIs

### 4. Join Game Room
```http
POST /api/games/join
Authorization: Bearer <jwt_token>
Content-Type: application/json

Request Body:
{
    "gameType": "POINTS_RUMMY",
    "entryFee": 100
}

Response (200 OK):
{
    "gameId": "game123",
    "players": ["player123"],
    "status": "WAITING",
    "gameType": "POINTS_RUMMY",
    "entryFee": 100,
    "timestamp": "2024-01-20T11:00:00Z"
}
```

### 5. Get Game Status
```http
GET /api/games/{gameId}
Authorization: Bearer <jwt_token>

Response (200 OK):
{
    "gameId": "game123",
    "players": ["player123", "player456"],
    "status": "IN_PROGRESS",
    "currentTurn": "player123",
    "deck": {
        "remainingCards": 52
    },
    "timestamp": "2024-01-20T11:05:00Z"
}
```

### 6. Play Move
```http
POST /api/games/{gameId}/move
Authorization: Bearer <jwt_token>
Content-Type: application/json

Request Body:
{
    "moveType": "DRAW_CARD",
    "cardId": "H7"
}

Response (200 OK):
{
    "moveId": "move123",
    "playerId": "player123",
    "moveType": "DRAW_CARD",
    "cardId": "H7",
    "timestamp": "2024-01-20T11:10:00Z"
}
```

## Authentication
All APIs except for OTP generation and verification require JWT authentication. Include the JWT token in the Authorization header as follows:

```http
Authorization: Bearer <jwt_token>
```

The JWT token is valid for 24 hours (86400000 milliseconds) from the time of issue.

## Error Responses
All API endpoints may return the following error responses:

```http
401 Unauthorized:
{
    "error": "Invalid or expired token",
    "timestamp": "2024-01-20T10:30:00Z"
}

403 Forbidden:
{
    "error": "Access denied",
    "timestamp": "2024-01-20T10:30:00Z"
}

500 Internal Server Error:
{
    "error": "Internal server error",
    "timestamp": "2024-01-20T10:30:00Z"
}
```