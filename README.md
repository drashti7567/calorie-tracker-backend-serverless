# Java Serverless Project
This repository hosts the files a small demo of java serverless backend implementation using serverless framework with Dagger and hosted on AWS using Lambda, API Gateway and DynamoDb.

## Modules
This is a multi-module Maven Project.
* **[common](common/README.md)** - Module containing commonly used codes and libraries

## Setup
### Prerequisites
The following must be properly installed in your development machine prior to working with this project
1. JDK 11
2. Maven
3. NodeJs v12 or higher
4. [Serverless Framework](https://www.serverless.com/) (**Note**: Please setup version 2.65.0)

### Install NPM Modules
```shell
cd <project directory>/lambda
npm install
```

### Building
The build process uses standard Maven build command.
```shell
mvn clean package
```