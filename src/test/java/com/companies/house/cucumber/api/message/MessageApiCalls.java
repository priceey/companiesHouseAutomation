package com.companies.house.cucumber.api.message;

import com.companies.house.annotations.LazyAutowired;
import com.companies.house.cucumber.api.auth.AuthApiCalls;
import com.companies.house.cucumber.api.message.data.ListOfMessages;
import com.companies.house.cucumber.api.message.data.MessageData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.restassured.RestAssured.given;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageApiCalls {

    @Value("${service.message.url}")
    private String messageUrl;

    @LazyAutowired
    private AuthApiCalls authApiCalls;

    public List<MessageData> getMessages() {

        String authToken = authApiCalls.getAuthToken();

        ListOfMessages response = given()
                .header("Cookie", "token=" + authToken)
                .when()
                .get(messageUrl)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(ListOfMessages.class);

        return response.getMessages();
    }
}
