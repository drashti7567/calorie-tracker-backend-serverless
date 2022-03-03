# Java Serverless Project
This repository hosts the files a small demo of java serverless backend implementation using serverless framework with Dagger and hosted on AWS using Lambda, API Gateway and DynamoDb.

## Modules
This is a multi-module Maven Project.
* **[common](common/README.md)** - Module containing commonly used codes and libraries
* **[persistence](persistence/README.md)** - Module containing persistence-related codes for integrating with AWS DynamoDB.

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

### Invoking Functions using Serverless Invoke Local
If you try to run `sls invoke local ...` for the first time, you may encounter an issue where the command hangs indefinitely with console message `Serverless: Building Java bridge, first invocation might take a bit longer.`.
This could be due to not having the Serverless Java Runtime build.
To fix this, follow the next steps to build the Serverless Java Runtime wrapper.

#### Navigate to the java runtime wrapper directory
The path below is when using Node Version Manager and with Node version 12.22.1 being used.
If you are not using NVM, the path could be in: `${NPM_DIR}/node_modules/serverless/lib/plugins/aws/invokeLocal/runtimeWrappers/java`.

```shell
cd ~/.nvm/versions/node/v12.22.1/lib/node_modules/serverless/lib/plugins/aws/invokeLocal/runtimeWrappers/java/
```

#### Build the Java Runtime Wrapper
```shell
mvn clean package
```

#### Invoke a function
```shell
cd <project directory>/lambda
sls invoke local -f <function-name> --data '{ "body": "{your-json-body...}" }}'
```

### Deploy to AWS
```shell
cd <project directory>/lambda
sls deploy
```
