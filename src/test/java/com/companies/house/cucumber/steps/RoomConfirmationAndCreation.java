package com.companies.house.cucumber.steps;

import com.companies.house.annotations.LazyAutowired;
import com.companies.house.context.ScenarioContext;
import com.companies.house.cucumber.api.rooms.RoomsApiCalls;
import com.companies.house.cucumber.api.rooms.data.RoomDataObject;
import com.companies.house.utils.Formatters;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class RoomConfirmationAndCreation {

    @LazyAutowired
    private RoomsApiCalls roomsApiCalls;

    @Autowired
    private ScenarioContext context;

    @And("I confirm there is a room available or create a room for test")
    public void iConfirmThereIsARoomAvailableOrCreateARoom() {

        LocalDate checkinDate = context.get("checkInDate", LocalDate.class);
        String checkinDateFormatted = Formatters.YYYY_MM_DD_DASHED.format(checkinDate);

        LocalDate checkOutDate = context.get("checkOutDate", LocalDate.class);
        String checkOutDateFormatted = Formatters.YYYY_MM_DD_DASHED.format(checkOutDate);

        List<RoomDataObject> roomAvailability = roomsApiCalls.getRoomAvailability(checkinDateFormatted, checkOutDateFormatted);

        // check there is an available room of the correct type
        boolean roomAvailable = false;
        for (RoomDataObject room : roomAvailability) {
            if (room.getType().equals(context.get("roomType", String.class))) {
                roomAvailable = true;
                break;
            }
        }

        // no room available to we will create one
        if (!roomAvailable) {
            roomsApiCalls.createRoom(context.get("roomType", String.class));
        }
    }
}

