# My Retail Case Study
The goal is to create a RESTful service that can retrieve productInformation and price details by ID. It will respond to an HTTP GET request at /products/{id} and deliver productInformation data as JSON where {id} is a number.

## Setup (Ubuntu 18.04)
- Install Java 11 (SDKMAN):
```
sdk install java 11.0.7-zulu
```
- Install Gradle (SDKMAN):
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
