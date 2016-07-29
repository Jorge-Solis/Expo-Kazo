package com.pikazo.rest.aws;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;
import com.google.gson.JsonObject;
import com.pikazo.rest.dto.Job;

/**
 * Created by jorgesolis on 7/27/16.
 */
public interface LambdaProxy {

    /**
     * Returns information about the styles available for the user
     */
    @LambdaFunction
    JsonObject styles();

    /**
     * Returns the queue for the current user
     * @return
     */
    @LambdaFunction
    Job[] queue(JsonObject params);

    /**
     * Returns the user settings
     * @return
     */
    @LambdaFunction
    JsonObject user(JsonObject params);

    /**
     * Sends a paint job
     * @return
     */
    @LambdaFunction
    JsonObject submit(JsonObject params);

    /**
     * Returns the information of a processed image from a paint job
     * @return
     */
    @LambdaFunction
    JsonObject image(JsonObject params);

}
