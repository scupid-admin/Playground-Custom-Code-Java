package com.altin.custom_code;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import morph.base.beans.CustomCodeResponse;

import java.util.Map;

/**
 * This contains example codes to refer. Please note that class name should be MorphCustomCode when you paste the
 * code in the editor.
 *
 * @author ishanjain
 * @since 15/09/17
 */
public class Examples implements RequestHandler<Map<String, Object>, CustomCodeResponse> {

    /**
     * This is the method that needs to be implemented with your logic.
     *
     * @param input   The input object contains the information, like userVariables or flowVariables
     * @param context Ignore this attribute.
     * @return The {@link CustomCodeResponse} object. This should contain the {@link morph.base.actions.Action}
     */
    @Override
    public CustomCodeResponse handleRequest(Map<String, Object> input, Context context) {

        CustomCodeResponse customCodeResponse = new CustomCodeResponse();

        /**
         * Reading a property & setting a property
         */


        return null;
    }
}