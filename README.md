# Countries & Cities API

A Spring Boot service that exposes APIs for Countries and Cities.
Data is hard-coded in memory (no database needed).

## Prerequisites
- Java 21
- Maven
- Git

## How to run
```
mvn spring-boot:run
```
The app starts at http://localhost:8080

## Swagger / OpenAPI
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## APIs

| Method | URL | What it does |
|--------|-----|--------------|
| GET | `/countries` | List all countries |
| GET | `/countries/{countryId}/cities?page=0&size=10` | Cities of a country (paginated) |
| GET | `/cities/{cityId}` | Details of one city |

### Sample requests and responses

`GET /countries`
```json
[
  { "id": 1, "name": "India" },
  { "id": 2, "name": "France" },
  { "id": 3, "name": "Luxembourg" },
  { "id": 4, "name": "Japan" }
]
```

`GET /countries/1/cities?page=0&size=2`
```json
{
  "content": [
    { "id": 1, "name": "Mumbai", "countryId": 1 },
    { "id": 2, "name": "Delhi", "countryId": 1 }
  ],
  "totalElements": 12,
  "page": 0,
  "size": 2,
  "totalPages": 6
}
```

`GET /cities/1`
```json
{ "id": 1, "name": "Mumbai", "countryId": 1 }
```

If a country or city id does not exist, the API returns HTTP 404.

## Pagination
- `page` starts at 0 (default 0)
- `size` is how many cities per page (default 10)
- The response contains: total records (`totalElements`), current page (`page`), page size (`size`) and total pages (`totalPages`).

## Assumptions
- Data is hard-coded (4 countries, 23 cities).
- A City has: id, name, countryId.
- Swagger documentation is generated automatically from the code.
