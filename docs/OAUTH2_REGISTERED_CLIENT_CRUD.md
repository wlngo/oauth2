# OAuth2 Registered Client CRUD Operations

This document describes the CRUD operations implemented for `Oauth2RegisteredClient` entity.

## Overview

The implementation provides complete CRUD (Create, Read, Update, Delete) operations for managing OAuth2 registered clients in the system. It follows the established patterns used throughout the application.

## Architecture

The implementation follows the layered architecture pattern:

```
Controller → Service → Mapper → Database
```

- **Entity**: `Oauth2RegisteredClient` - JPA entity with MyBatis Plus annotations
- **Mapper**: `Oauth2RegisteredClientMapper` - Data access layer extending `BaseMapper`
- **Service**: `Oauth2RegisteredClientService` & `Oauth2RegisteredClientServiceImpl` - Business logic layer
- **Controller**: `Oauth2RegisteredClientController` - REST API layer

## API Endpoints

### Base URL: `/api/oauth2/registered-clients`

| Method | Endpoint | Description | Required Permission |
|--------|----------|-------------|-------------------|
| POST | `/getAllClients` | Get all clients with pagination | `oauth2:client:view` |
| GET | `/{id}` | Get client by ID | `oauth2:client:view` |
| POST | `/createClient` | Create new client | `oauth2:client:create` |
| POST | `/updateClient` | Update existing client | `oauth2:client:update` |
| DELETE | `/deleteClient/{id}` | Delete client | `oauth2:client:delete` |

## Request/Response Examples

### Get All Clients
```http
POST /api/oauth2/registered-clients/getAllClients?page=1&size=10&keyword=test
```

### Get Client by ID
```http
GET /api/oauth2/registered-clients/client-id-123
```

### Create Client
```http
POST /api/oauth2/registered-clients/createClient
Content-Type: application/json

{
  "id": "client-id-123",
  "clientId": "my-oauth-client",
  "clientName": "My OAuth Client",
  "clientSecret": "secret123",
  "clientAuthenticationMethods": "client_secret_basic",
  "authorizationGrantTypes": "authorization_code,refresh_token",
  "redirectUris": "http://localhost:8080/callback",
  "scopes": "read,write",
  "clientSettings": "{}",
  "tokenSettings": "{}"
}
```

### Update Client
```http
POST /api/oauth2/registered-clients/updateClient
Content-Type: application/json

{
  "id": "client-id-123",
  "clientName": "Updated Client Name",
  "scopes": "read,write,admin"
}
```

### Delete Client
```http
DELETE /api/oauth2/registered-clients/deleteClient/client-id-123
```

## Service Methods

### `Oauth2RegisteredClientService`

- `createOauth2RegisteredClient(Oauth2RegisteredClient)` - Creates a new client
- `getOauth2RegisteredClientById(String)` - Retrieves client by ID
- `selectAllOauth2RegisteredClients(Integer, Integer, String)` - Gets paginated list with optional keyword search
- `updateOauth2RegisteredClient(Oauth2RegisteredClient)` - Updates existing client
- `deleteOauth2RegisteredClient(String)` - Deletes client by ID

## Security

All endpoints are protected with Spring Security's `@PreAuthorize` annotations requiring specific OAuth2 client management permissions:

- `oauth2:client:view` - For read operations
- `oauth2:client:create` - For creation
- `oauth2:client:update` - For updates
- `oauth2:client:delete` - For deletion

## Search and Pagination

The `selectAllOauth2RegisteredClients` method supports:
- **Pagination**: Via `pageNum` and `pageSize` parameters
- **Search**: Via `keyword` parameter (searches client_id, client_name, and id fields)
- **Ordering**: Results ordered by `client_id_issued_at` DESC

## Business Logic

- When creating a client, if `clientIdIssuedAt` is null, it's automatically set to current timestamp
- Search functionality uses LIKE queries across multiple fields
- All operations return the number of affected rows for create/update/delete operations

## Testing

Unit tests are provided for both service and controller layers:
- `Oauth2RegisteredClientServiceTest` - Tests service layer with mocked mapper
- `Oauth2RegisteredClientControllerTest` - Tests controller layer with mocked service

## Integration with Existing OAuth2 Infrastructure

This CRUD implementation complements the existing `RegisteredClientRepositoryImpl` which implements Spring Security's `RegisteredClientRepository` interface. The new CRUD operations provide administrative capabilities while the existing repository handles OAuth2 protocol operations.