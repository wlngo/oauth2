# OAuth2 Registered Client CRUD API Documentation

This document describes the CRUD operations for managing OAuth2 registered clients.

## Base URL
```
/oauth2-service/api/oauth2-clients
```

## Authentication
All endpoints require proper authorization with the following permissions:
- `oauth2-client:view` - For read operations
- `oauth2-client:create` - For create operations
- `oauth2-client:update` - For update operations
- `oauth2-client:delete` - For delete operations

## Endpoints

### 1. Get All Clients (with pagination and search)
```http
POST /getAllClients?page=1&size=10&keyword=test
```

**Request Parameters:**
- `page` (optional): Page number (default: 1)
- `size` (optional): Page size (default: 10)
- `keyword` (optional): Search keyword (searches in client_id and client_name)

**Response:**
```json
{
  "code": 200,
  "data": {
    "list": [
      {
        "id": "uuid-string",
        "clientId": "my-client",
        "clientName": "My Application",
        "clientSecret": "{noop}secret",
        "scopes": "read,write",
        "redirectUris": "http://localhost:8080/callback"
      }
    ],
    "total": 1,
    "pageNum": 1,
    "pageSize": 10
  },
  "msg": "",
  "identifier": true
}
```

### 2. Get Client by ID
```http
GET /findById/{id}
```

**Response:**
```json
{
  "code": 200,
  "data": {
    "id": "uuid-string",
    "clientId": "my-client",
    "clientName": "My Application",
    "clientSecret": "{noop}secret",
    "clientAuthenticationMethods": "client_secret_basic",
    "authorizationGrantTypes": "authorization_code,refresh_token",
    "redirectUris": "http://localhost:8080/callback",
    "scopes": "read,write",
    "clientSettings": "{}",
    "tokenSettings": "{}"
  },
  "msg": "",
  "identifier": true
}
```

### 3. Get Client by Client ID
```http
GET /findByClientId/{clientId}
```

### 4. Create New Client
```http
POST /createClient
Content-Type: application/json

{
  "clientId": "my-new-client",
  "clientName": "My New Application",
  "clientSecret": "{noop}newsecret",
  "clientAuthenticationMethods": "client_secret_basic",
  "authorizationGrantTypes": "authorization_code,refresh_token",
  "redirectUris": "http://localhost:8080/callback",
  "scopes": "read,write",
  "clientSettings": "{}",
  "tokenSettings": "{}"
}
```

**Response:**
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

### 5. Update Existing Client
```http
POST /updateClient
Content-Type: application/json

{
  "id": "existing-uuid",
  "clientId": "my-client",
  "clientName": "Updated Application Name",
  "clientSecret": "{noop}secret",
  "clientAuthenticationMethods": "client_secret_basic",
  "authorizationGrantTypes": "authorization_code,refresh_token",
  "redirectUris": "http://localhost:8080/callback,http://localhost:8080/new-callback",
  "scopes": "read,write,admin",
  "clientSettings": "{}",
  "tokenSettings": "{}"
}
```

### 6. Delete Client
```http
DELETE /deleteClient/{id}
```

**Response:**
```json
{
  "code": 200,
  "data": 1,
  "msg": "",
  "identifier": true
}
```

## Field Descriptions

- **id**: Unique identifier (UUID, auto-generated if not provided)
- **clientId**: OAuth2 client identifier (required)
- **clientName**: Human-readable client name
- **clientSecret**: Client secret (should be encoded, e.g., {noop}secret)
- **clientAuthenticationMethods**: Comma-separated authentication methods
- **authorizationGrantTypes**: Comma-separated grant types
- **redirectUris**: Comma-separated redirect URIs
- **postLogoutRedirectUris**: Comma-separated post logout redirect URIs
- **scopes**: Comma-separated OAuth2 scopes
- **clientSettings**: JSON string of client settings
- **tokenSettings**: JSON string of token settings
- **clientIdIssuedAt**: Timestamp when client ID was issued (auto-generated)
- **clientSecretExpiresAt**: Optional expiration time for client secret

## Error Responses

All endpoints return error responses in the following format:
```json
{
  "code": 400,
  "data": null,
  "msg": "Error message",
  "identifier": false
}
```

Common HTTP status codes:
- 200: Success
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error