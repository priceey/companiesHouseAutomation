package com.companies.house.cucumber.api.rooms.data;

import lombok.Data;

@Data
public class RoomDataObject {
    private Integer roomid;
    private String roomName;
    private String type;
    private boolean accessible;
    private String image;
    private String description;
    private String[] features;
    private int roomPrice;
}
