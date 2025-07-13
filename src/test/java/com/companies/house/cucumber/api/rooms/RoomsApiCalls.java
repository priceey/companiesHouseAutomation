package com.companies.house.cucumber.api.rooms;

import com.companies.house.annotations.LazyAutowired;
import com.companies.house.context.ScenarioContext;
import com.companies.house.cucumber.api.auth.AuthApiCalls;
import com.companies.house.cucumber.api.rooms.data.ListOfRooms;
import com.companies.house.cucumber.api.rooms.data.RoomDataObject;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomsApiCalls {

    @Value("${service.rooms.url}")
    private String roomsUrl;

    @Autowired
    private ScenarioContext context;

    @LazyAutowired
    private AuthApiCalls authApiCalls;

    public List<RoomDataObject> getRoomsList() {

        String authToken = authApiCalls.getAuthToken();

        ListOfRooms response = given().when().header("Cookie", "token=" + authToken).get(roomsUrl).then()
                .assertThat().statusCode(200)
                .extract()
                .as(ListOfRooms.class);

        return response.getRooms();
    }

    public List<RoomDataObject> getRoomAvailability(String checkinDate, String checkoutDate) {
        String authToken = authApiCalls.getAuthToken();

        String urlEndPoint = String.join("", "?checkin=", checkinDate, "&checkout=", checkoutDate);

        ListOfRooms response = given()
                .header("Cookie", "token=" + authToken)
                .when()
                .get(roomsUrl + urlEndPoint)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(ListOfRooms.class);

        return response.getRooms();
    }

    public void createRoom(String roomType) {

        String authToken = authApiCalls.getAuthToken();

        // create a new room
        RoomDataObject payload = new RoomDataObject();
        payload.setRoomid(null);
        payload.setRoomName("999");
        payload.setType(roomType);
        payload.setAccessible(ThreadLocalRandom.current().nextBoolean());
        payload.setDescription("Room created when no other rooms are available.");
        payload.setImage("https://automationintesting.online/images/room1.jpg");
        payload.setRoomPrice(250);
        payload.setFeatures(new String[]{"Radio", "WiFi", "Safe"});

        Gson gson = new Gson();
        String json = gson.toJson(payload);

        given().when().header("Cookie", "token=" + authToken).body(json).post(roomsUrl).then().assertThat().statusCode(200);

        // get the id of the room so we can delete it later
        List<RoomDataObject> roomsList = getRoomsList();

        // reverse the list so we can find the room we just created
        Collections.reverse(roomsList);

        Optional<Integer> first = roomsList.stream()
                .filter(room -> room.getDescription().contains("Room created when no other rooms are available"))
                .map(RoomDataObject::getRoomid)
                .findFirst();

        context.put("roomId", first.orElse(0));
        System.out.println("Room Id: " + context.get("roomId", Integer.class));
    }

    public void deleteRoom(Integer roomId) {

        String authToken = authApiCalls.getAuthToken();

        given().when().header("Cookie", "token=" + authToken).delete(roomsUrl + "/" + roomId).then().assertThat().statusCode(200);
    }

    public RoomDataObject getRoomDetails(Integer roomId) {
        String authToken = authApiCalls.getAuthToken();

        return given().when().header("Cookie", "token=" + authToken).get(roomsUrl + "/" + roomId).then()
                .assertThat().statusCode(200)
                .extract()
                .as(RoomDataObject.class);
    }
}