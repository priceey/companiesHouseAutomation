package com.companies.house.cucumber.api.auth;

import com.companies.house.cucumber.api.auth.data.AuthData;
import com.companies.house.cucumber.api.auth.data.AuthResponse;
import com.google.gson.Gson;
import io.restassured.common.mapper.TypeRef;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthApiCalls {

    @Value("${service.auth.url}")
    private String authUrl;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    private static final String LOGIN = "/login";

    public String getAuthToken() {
        AuthData authData = new AuthData();
        authData.setUsername(username);
        authData.setPassword(password);

        Gson gson = new Gson();
        String json = gson.toJson(authData);
        return given().body(json).when().post(authUrl + LOGIN).then()
                .assertThat().statusCode(200)
                .extract().body().as(new TypeRef<AuthResponse>() {
                }).getToken();
    }
}
