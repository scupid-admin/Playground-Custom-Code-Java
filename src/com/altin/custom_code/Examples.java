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
import morph.base.beans.simplifiedmessage.*;

import java.util.ArrayList;
import java.util.Collections;
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
     * PublishMessageAction contains {@link SimplifiedMessage}
     * {@link SimplifiedMessage} is a collection of {@link SimplifiedMessagePayload}
     * {@link SimplifiedMessagePayload} represents a single message
     * <p>
     * Thus PublishMessageAction lets you publish multiple messages
     *
     * @return The {@link PublishMessageAction} to reply back to the users
     */
    private PublishMessageAction createPublishMessageAction() {
        PublishMessageAction publishMessageAction = new PublishMessageAction();

        SimplifiedMessage simplifiedMessage = new SimplifiedMessage();
        publishMessageAction.setSimplifiedMessage(simplifiedMessage);


        ArrayList<SimplifiedMessagePayload> messages = Lists.newArrayList();
        simplifiedMessage.setMessages(messages);

        TextMessagePayload textMessagePayload = createTextMessage();
        messages.add(textMessagePayload);

        return publishMessageAction;
    }

    /**
     * This creates a Text message. Note that text message can also contain buttons & suggestions (QRs)
     *
     * @return The {@link TextMessagePayload} representing a text message
     */
    private TextMessagePayload createTextMessage() {
        TextMessagePayload payload = new TextMessagePayload();
        payload.setText(
                "Thank you for booking the appointment. Please click on the button below to pay appointment fees.");
        Button button = new Button();
        button.setTitle("Pay");
        button.setButtonType(Button.ButtonType.URL);
        button.setUrl("http://www.morph.ai");
        button.setWebviewHeightRatio(Button.WebviewHeightRatio.TALL);
        payload.setButtons(Collections.singletonList(button));

        ArrayList<SuggestionElement> suggestionElements = Lists.newArrayList();
        SuggestionElement suggestion = new SuggestionElement();
        suggestion.setImageUrl("http://image-url.com");
        suggestion.setTitle("Back");
        suggestion.setPayload("back");
        suggestionElements.add(suggestion);
        payload.setSuggestionElements(suggestionElements);
        return payload;
    }
}