package com.drash.common;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

import java.nio.charset.Charset;

public class LambdaInvoker {

    public static String invokeLambda(String functionName, String payload, InvocationType invocationType) throws Exception {
        /**
         * Function to invoke the lambda model
         * @param functionName: aws function ARN
         * @param payload: Json Stringified payload
         * @param incoationType: defines whether the lambda is called asynchronously or synchronously.
         *      Pass InvocationType.RequestResponse for synchronous execution
         *      Pass InvocationType.Event for asynchronous.
         */
        AWSCredentials credentials = new BasicAWSCredentials(
                System.getenv().get("aws.access.keyId"), System.getenv().get("aws.access.secret"));
        InvokeRequest lmbRequest = new InvokeRequest()
                .withFunctionName(functionName)
                .withPayload(payload);
        lmbRequest.setInvocationType(invocationType);

        AWSLambda lambda = AWSLambdaClientBuilder.standard()
                .withRegion(System.getenv().get("aws.access.region"))
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

        InvokeResult lmbResult = lambda.invoke(lmbRequest);
        String resultJSON = new String(lmbResult.getPayload().array(), Charset.forName("UTF-8"));
        return resultJSON;
    }

}
