# Lambda
This module contains the Serverless Framework project for provisioning AWS Resources and contains the Handlers for Lambda functions with AWS API Gateway HTTP endpoints.

## AWS Resources Provisioning
We use the [Serverless Framework](https://www.serverless.com/) to provision AWS Resources. Main configuration is done through the [serverless.yml](serverless.yml) file.

### Imported CloudFormation Templates
* [dynamodb.yml](serverless-resources/dynamodb.yml) - Provisions the DynamoDB table and its Global Secondary Indexes
* [roles.yml](serverless-resources/roles.yml) - Contains the resource definitions for IAM Roles and Managed Policies used by Lambda Functions