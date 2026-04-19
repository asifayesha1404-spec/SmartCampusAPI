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



---

---

## Report Answers

### Part 1

**1. JAX-RS Lifecycle**  
In JAX-RS, a new instance of a resource class is usually created for each request. This means each request is handled separately, which helps avoid problems with shared data. However, in this project I used static lists to store rooms and sensors, so they are shared across requests. This could cause issues if multiple requests try to modify the data at the same time.

**2. HATEOAS**  
HATEOAS means the API can include links in responses to show what actions are possible next. This makes the API easier to use because the client does not need to rely completely on documentation. It also makes the system more flexible if endpoints change later.

---

### Part 2

**1. IDs vs Full Objects**  
Returning only IDs reduces the amount of data sent, which improves performance. However, the client may need to make extra requests to get more details. Returning full objects provides more information straight away, but increases the response size. So there is a trade-off between efficiency and convenience.

**2. DELETE Idempotency**  
DELETE is idempotent because sending the same request multiple times does not change the result after the first request. For example, once a room is deleted, sending the DELETE request again will not delete anything further. It may return a not found error, but the system state stays the same.

---

### Part 3

**1. @Consumes JSON**  
The @Consumes annotation ensures that the API only accepts JSON input. If a client sends data in another format, such as plain text, the request will fail. This helps keep the API consistent and prevents unexpected input.

**2. QueryParam vs Path**  
Using query parameters (for example, ?type=CO2) is better for filtering because they are optional and flexible. If filtering was part of the path, it would make the API less flexible and harder to extend in the future.

---

### Part 4

**1. Sub-Resource Locator**  
The sub-resource locator pattern helps organise the code by splitting it into smaller classes. Instead of handling everything in one class, each part of the API has its own responsibility. This makes the code easier to read and maintain.

**2. Data Consistency**  
When a new reading is added, the sensor’s current value is also updated. This ensures that the latest value is always available without needing to check all readings, keeping the data consistent.

---

### Part 5

**1. 409 Conflict**  
A 409 error is returned when trying to delete a room that still has sensors. This shows that the request cannot be completed because of the current state of the system.

**2. 422 vs 404**  
422 is more appropriate because the request itself is valid, but the data is incorrect (the room does not exist). A 404 would usually mean the endpoint itself cannot be found.

**3. 403 Forbidden**  
403 is used when a sensor is in maintenance mode and cannot accept readings. The request is understood, but not allowed at that time.

**4. Security Risk of Stack Traces**  
Stack traces can expose internal details like class names or file structure. This could help someone find weaknesses in the system, so they should not be shown to users.

**5. Logging Filters**  
Using filters for logging is better because it keeps logging separate from the main code. This avoids repeating logging logic in every method and makes the system easier to manage.
