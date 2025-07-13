package com.companies.house.cucumber.steps;

import com.companies.house.annotations.LazyAutowired;
import com.companies.house.context.ScenarioContext;
import com.companies.house.cucumber.api.message.MessageApiCalls;
import com.companies.house.cucumber.api.message.data.MessageData;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Messages {

    @Autowired
    ScenarioContext context;

    @LazyAutowired
    private MessageApiCalls messageApiCalls;

    @And("I can confirm that the booking message has been sent")
    public void iCanConfirmThatTheBookingMessageHasBeenSent() {
        List<MessageData> messages = messageApiCalls.getMessages();

        // last ones first for quicker parsing
        Collections.reverse(messages);

        String fullName = String.join(" ", context.get("firstNameSubmit", String.class), context.get("lastNameSubmit", String.class));

        boolean found = messages.stream()
                .anyMatch(m -> fullName.equals(m.getName()) &&
                        "You have a new booking!".equals(m.getSubject()));

        assertTrue(found, "Expected message with name and subject not found!");
    }

    @And("the message is received in the back end")
    public void theMessageIsReceivedInTheBackEnd() {
        List<MessageData> messages = messageApiCalls.getMessages();

        // last ones first for quicker parsing
        Collections.reverse(messages);

        String fullName = context.get("fullNameMessage", String.class);
        String subject = context.get("messageSubject", String.class);

        boolean found = messages.stream()
                .anyMatch(m -> fullName.equals(m.getName()) &&
                        subject.equals(m.getSubject()));

        assertTrue(found, "Expected message with name and subject not found!");
    }
}
