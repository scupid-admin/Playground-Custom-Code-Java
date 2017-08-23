package com.altin.custom_code;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import morph.base.beans.CustomCodeResponse;

import java.util.Map;

/**
 * @author ishanjain
 * @since 18/07/17
 */
public class MorphCustomCode implements RequestHandler<Map<String, Object>, CustomCodeResponse> {


    @Override
    public CustomCodeResponse handleRequest(Map<String, Object> input, Context context) {
        //Write your code here
        return null;
    }
}