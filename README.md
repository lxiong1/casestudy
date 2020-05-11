# My Retail Case Study
The goal is to create a RESTful service that can retrieve productInformation and price details by ID. It will respond to an HTTP GET request at /products/{id} and deliver productInformation data as JSON where {id} is a number.

## Setup (Ubuntu 18.04/20.04)
- Install SDKMAN:
```
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

- Install Java 11:
```
sdk install java 11.0.7-zulu
```

- Install Gradle:
```
sdk install gradle 6.4
```

- Install MongoDB with the following commands:
```
sudo apt-get install gnupg
wget -qO - https://www.mongodb.org/static/pgp/server-4.2.asc | sudo apt-key add -
echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu bionic/mongodb-org/4.2 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.2.list
sudo apt-get update
sudo apt-get install -y mongodb-org
```

- Start MongoDB:
```
sudo systemctl start mongod
```

- Run the following commands to create database, collection, and document for current price:
```
mongo
use myretaildb
db.createCollection("product_current_price", {})
db.test.insert({ "product_id": 13860428, "value": 13.49, "currency_code": "USD" })
``` 
*Note: On insert operation, a unique ObjectId will automatically be created for the document*

## Test Application Code
- Run the following command to run unit & integration tests:
```
./gradlew clean test
```

## Create GET Request
- Run the following command to start up the application:
```
./gradlew bootRun
```
*Note: The Spring Boot application will run on port 8080 so make sure this port is open.*

- Run the following command to GET request for product information:
```
curl -X GET --url http://localhost:8080/products/13860428
```
- Run the following command to PUT request for saving/updating product information:
```
curl -X PUT -H "Content-Type: application/json" -d '{"productId":13860428,"name":"The Big Lebowski (Blu-ray)","current_price":{"value":20.49,"currency_code":"USD"}}' --url http://localhost:8080/products/13860428
```