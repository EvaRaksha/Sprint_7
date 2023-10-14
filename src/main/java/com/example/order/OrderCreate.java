package com.example.order;

import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

public class OrderCreate {
    private String trackNumber;

    public void createdOrderSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED);
        trackNumber = response.extract().path("track").toString();
    }

    public String getTrackNumber() {
        return trackNumber;
    }
}