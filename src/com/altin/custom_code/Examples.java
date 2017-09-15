package com.altin.custom_code;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jersey.repackaged.com.google.common.collect.Lists;
import morph.base.actions.Action;
import morph.base.actions.StringVariable;
import morph.base.actions.VariableScope;
import morph.base.actions.impl.PublishMessageAction;
import morph.base.actions.impl.SetVariableAction;
import morph.base.beans.CustomCodeResponse;

import java.util.List;
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
        List<Action> actions = Lists.newArrayList();

        /**
         * Reading a property & setting a property
         */
        SetVariableAction setVariableAction = readAndSetAnAttribute(input);
        actions.add(setVariableAction);

        /**
         * Sending response to users
         */
        PublishMessageAction publishMessageAction = createPublishMessageAction();

        return null;
    }


    /**
     * This reads an attribute & then sets an attribute
     *
     * @param input The input object contains the information, like userVariables or flowVariables
     * @return The {@link SetVariableAction} which contains which attribute to set
     */
    private SetVariableAction readAndSetAnAttribute(Map<String, Object> input) {

        /**
         * Use this to read from Customer Scope Properties.
         */
        //noinspection unchecked
        Map<String, Object> userVariables = (Map<String, Object>) input.get("userVariables");

        /**
         * Use this to read from Conversation Scope Properties.
         */
        //noinspection unchecked
        Map<String, Object> flowVariables = (Map<String, Object>) input.get("flowVariables");

        //Lets say we want to check user's response which is stored in Conversation scope "response" property
        String response = (String) flowVariables.get("response");

        if (response != null && response.equals("Link Account")) {
            // If the property value is Link Account then I am storing the customer id in customer scope variable
            // Use VariableScope.FLOW for setting conversation scope variables
            // The attributes should be defined first in UI using storage modules
            return new SetVariableAction("Customer Id", VariableScope.USER, new StringVariable().value("ID_1234"));
        }
        // If its not linked, then lets store it in a conversation scope attribute linked which stores false for
        // unlinked account
        return new SetVariableAction("Linked", VariableScope.FLOW, new StringVariable().value("false"));
    }

    /**
     * This creates a {@link PublishMessageAction} which is used to reply back to the users
     *
     * @return The {@link PublishMessageAction} to reply back to the users
     */
    private PublishMessageAction createPublishMessageAction() {
        return null;
    }
}