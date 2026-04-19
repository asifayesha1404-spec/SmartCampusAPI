## Smart Campus API

## Overview

This project is a REST API built using JAX-RS and Grizzly. It manages rooms, sensors, and sensor readings in a simple smart campus system.

The API supports:

* Creating, viewing, and deleting rooms
* Creating sensors linked to rooms
* Filtering sensors by type
* Adding and viewing sensor readings
* Error handling using exception mappers
* Logging requests and responses

---

## How to Run the Project

1. Open the project in NetBeans
2. Right-click → Clean and Build
3. Run `SmartCampusAPI.java`
4. Server runs at:
   http://localhost:8081/

---

## Sample curl Commands

### Get all rooms

```
curl http://localhost:8081/rooms
```

### Create a room

```
curl -X POST http://localhost:8081/rooms -H "Content-Type: application/json" -d "{\"id\":4,\"name\":\"Lab\",\"capacity\":40}"
```

### Get sensors

```
curl http://localhost:8081/sensors
```

### Create sensor

```
curl -X POST http://localhost:8081/sensors -H "Content-Type: application/json" -d "{\"id\":1,\"type\":\"CO2\",\"currentValue\":400,\"roomId\":1}"
```

### Add reading

```
curl -X POST http://localhost:8081/sensors/1/readings -H "Content-Type: application/json" -d "100"
```

---

## Report Answers

### JAX-RS Lifecycle

In JAX-RS, a new resource object is usually created for each request. This helps avoid shared state issues. In this project, static lists are used, so the data is shared and could be accessed by multiple requests at once.

### HATEOAS

HATEOAS allows APIs to include links in responses so clients know what actions they can take next. This makes the API easier to use.

### IDs vs Full Objects

Returning IDs is more efficient, but returning full objects gives more useful information. It depends on the situation.

### DELETE Idempotency

DELETE is idempotent because repeating the request does not change the result after the first time.

### @Consumes JSON

This ensures the API only accepts JSON input. Other formats will be rejected.

### QueryParam vs Path

Query parameters are better for filtering because they are optional and flexible.

### Sub-Resource Locator

This helps organise the code by separating logic into smaller classes.

### Data Consistency

Updating the current value when adding a reading keeps the data consistent.

### 409 Conflict

Used when trying to delete a room that still has sensors.

### 422 vs 404

422 is used because the request is valid but the data is incorrect.

### 403 Forbidden

Used when a sensor is in maintenance mode and cannot accept readings.

### Stack Trace Risk

Stack traces can expose internal system details which is a security risk.

### Logging Filters

Filters are better for logging because they keep logging separate from business logic.

