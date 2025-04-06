# Fair Rummy API Documentation

## Base URLs
- Auth Service: `http://localhost:8081`
- Player Service: `http://localhost:8082`

## Authentication Service APIs

### Generate OTP
```http
POST /api/auth/generate-otp
Content-Type: application/json

Request Body:
{
    "phoneNumber": "+919876543210"
}

Response (200 OK):
{
    "message": "OTP sent successfully",
    "timestamp": "2024-01-20T10:30:00Z"
}

Response (400 Bad Request):
{
    "error": "Invalid phone number format",
    "timestamp": "2024-01-20T10:30:00Z"
}
```

### Verify OTP
```http
POST /api/auth/verify-otp
Content-Type: application/json

Request Body:
{
    "phoneNumber": "+919876543210",
    "otp": "123456"
}

Response (200 OK):
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiresIn": 86400000
}

Response (400 Bad Request):
{
    "error": "Invalid OTP",
    "timestamp": "2024-01-20T10:30:00Z"
}
```

## Player Service APIs

### Create Player Profile
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