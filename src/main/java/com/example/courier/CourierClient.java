package com.example.courier;

import com.example.Client;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

public class CourierClient extends Client {
    static final String COURIER_PATH = "/courier";

    public ValidatableResponse create(Credentials courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    public ValidatableResponse login(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER_PATH + "/login")
                .then().log().all();
    }

    public ValidatableResponse loginWithEmptyValues() {
        Credentials emptyCredentials = new Credentials("", "");

        return login(emptyCredentials);
    }

    public ValidatableResponse loginWithNonExistentAccount() {
        Credentials nonExistentCredentials = new Credentials("non_existent_login", "non_existent_password");

        return login(nonExistentCredentials);
    }

    public ValidatableResponse delete(int courierId) {
        return spec()
                .body(Map.of("id",courierId))
                .when()
                .delete(COURIER_PATH + "/" + courierId)
                .then().log().all();
    }

    public ValidatableResponse createWithoutMandatoryFields() {
            Credentials emptyCredentials = new Credentials("", "");

            return create(emptyCredentials);
    }
}