# Fetch Receipt Processor

## Running Receipt Processor

NOTE :  Before you start, make sure **Docker** and **Git** are installed on your computer.

1. Copy the below shell script to your local machine and name it `setup.sh`

```shell
#!/bin/bash

# Clone the repository
git clone https://github.com/akshathsk/fetch-receipt-processor-challenge.git

# Navigate to the project directory
cd fetch-receipt-processor-challenge

# Build the Docker image locally
docker build -t fetch-receipt-processor-challenge .

# Run the created Docker image
docker run -p 8080:8080 fetch-receipt-processor-challenge

echo "The application is now running on http://localhost:8080"

```

2. Make the Script Executable:
   1. Open a terminal window.
   2. Navigate to the directory where setup.sh is saved.
   3. Make the script executable by running the command `chmod +x setup.sh`

3. Run the Shell Script:
   1. In the terminal, execute the script with `./setup.sh`

## OR

1. Clone the repository

```shell
git clone https://github.com/akshathsk/fetch-receipt-processor-challenge.git
```

2. Change directory
```shell
cd fetch-receipt-processor-challenge
```

3. Docker Build

```shell
docker build -t fetch-receipt-processor-challenge .
```

4. Run the created Docker image

```shell
docker run -p 8080:8080 fetch-receipt-processor-challenge
```

5. The application is now running on `http://localhost:8080`


## Test Using the Postman Collection:

1. After the application is running, you can test its endpoints using the `fetch.postman_collection.json` file. 
2. Open Postman, and import the `fetch.postman_collection.json` file. 
3. This file contains predefined requests for testing the application's endpoints. 
4. Use the imported collection in Postman to send requests to the application and validate its responses.


## Assumptions
1. The date format for the field `purchaseDate` in the receipts object is assumed to be in the format "yyyy-MM-dd".
2. The time format for the field `purchaseTime` in the receipts object is assumed to be in the format "HH:mm".
3. Based on the example provided in the problem description, the retailer name should allow the dollar sign ($) as shown with `"retailer": "M&M Corner Market"`. Therefore, the dollar sign is allowed in the regex for retailer name validation.
4. Sum of the prices of all the items should be equal to the total.

## Endpoints & Swagger (OpenAPI)

All endpoint documentation can be found at - [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

1. `POST /receipts/process Endpoint`
   1. 200 OK: The request was successful, and the receipt was processed and stored accordingly.
   2. 400 Bad Request: There was a validation failure with the input data. This response indicates that the request body did not meet the required schema or validation criteria set by the server for receipt data. 
   3. 500 Internal Server Error: The server encountered an unexpected condition that prevented it from fulfilling the request. 

2. `GET /receipt Endpoint Responses` 
   1. 200 OK: The request was successful, and the requested receipt data is returned in the response body. 
   2. 404 Not Found: The specified receipt could not be found. This response indicates that there is no receipt matching the identifiers provided in the request.

## Configurations

All the rules for calculating points are configured in the `application.properties`

```properties
# Points calculation rules
points.retailerName=1
points.roundDollar=50
points.multipleOfQuarter=25
points.perTwoItems=5
points.multipleOfThreeItemDescription=0.2
points.oddDay=6
points.afternoonBonus=10
points.afternoonStart=14:00
points.afternoonEnd=16:00
```
Changes to the point calculation logic can be made directly in the application.properties file without the need to alter and recompile the code.

## Testing

1. I used `Test-Driven Development (TDD)` for this project, writing tests first before coding the actual features.
2. This approach involved writing detailed test cases before implementing the actual validation and point calculation logic.
3. API Tests are added in `com/fetch/receiptprocessor/controller/ApiTest.java` to test the HTTP Response codes and the returned jsons.

## Database

1. A `ConcurrentHashMap` was utilized as an in-memory store to simulate database functionality, adhering to the requirement of not using an external database.
2. ConcurrentHashMap is thread-safe, meaning it can safely be accessed and modified by multiple threads concurrently without compromising data integrity.

## Logging

1. `SLF4J logging` has been implemented across the application, utilizing `info`, `warn`, and `error` levels to categorize messages according to their significance. 
2. Info level logs capture routine details, warn level logs alert to conditions that may potentially require attention, and error level logs record issues that affect the program's execution or result in failures.

## Documentation

`JavaDoc` documentation has been added to all classes and methods, and comments have been included where needed to clarify complex parts of the code. This makes the code easier to understand and maintain.

