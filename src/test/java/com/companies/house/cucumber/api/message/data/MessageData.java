package com.companies.house.cucumber.api.message.data;

import lombok.Getter;

@Getter
public class MessageData {
    private int id;
    private String name;
    private String subject;
    private boolean read;
}