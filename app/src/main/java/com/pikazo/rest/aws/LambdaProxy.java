package com.pikazo.rest.aws;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;
import com.pikazo.rest.dto.StylesInformation;

/**
 * Created by jorgesolis on 7/27/16.
 */
public interface LambdaProxy {

    /**
     * Invoke lambda function "styles"
     */
    @LambdaFunction
    StylesInformation styles();

}
