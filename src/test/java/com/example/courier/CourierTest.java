package com.example.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CourierTest {

    private final CourierClient client = new CourierClient();
    private final CourierCreating chek = new CourierCreating();
    private final CourierLogin chekLogin = new CourierLogin();
    private int courierId;

    @After
    @DisplayName("Удаление курьера по id")
    public void deleteCourier() {
        client.delete(courierId);
    }

    @Test
    @DisplayName("Создание курьера, создание курьера без передачи обязательных полей, попытка создания двух одинаковых курьеров")
    public void testCreateCourier() {
        var courier = CourierGenerator.random();
        ValidatableResponse response = client.create(Credentials.from(courier));
        chek.createdSuccessfully(response);

        ValidatableResponse createWithoutMandatoryFieldsResponse = client.createWithoutMandatoryFields();
        chek.insufficientDataForCreate(createWithoutMandatoryFieldsResponse);

        ValidatableResponse duplicateCreateResponse = client.create(Credentials.from(courier));
        chek.duplicateCreateLoginError(duplicateCreateResponse);
    }

    @Test
    @DisplayName("Создание двух разных курьеров, попытка создания курьера с существующим логином, попытка входа курьера без передачи обязательных полей, вход под несуществующим пользователем")
    public void testLoginCourier() {
        var courier1 = CourierGenerator.random();
        var courier2 = CourierGenerator.random();

        ValidatableResponse createResponse1 = client.create(Credentials.from(courier1));
        chek.createdSuccessfully(createResponse1);

        ValidatableResponse createResponse2 = client.create(Credentials.from(courier2));
        chek.createdSuccessfully(createResponse2);

        ValidatableResponse duplicateCreateResponse = client.create(Credentials.from(courier1));
        chekLogin.duplicateLoginError(duplicateCreateResponse);

        ValidatableResponse loginResponse1 = client.login(Credentials.from(courier1));
        chekLogin.loggedInSuccessfully(loginResponse1);

        ValidatableResponse loginResponse2 = client.login(Credentials.from(courier2));
        chekLogin.loggedInSuccessfully(loginResponse2);

        ValidatableResponse invalidLoginResponse = client.loginWithEmptyValues();
        chekLogin.insufficientDataForLogin(invalidLoginResponse);

        ValidatableResponse nonExistentAccountResponse = client.loginWithNonExistentAccount();
        chekLogin.accountNotFoundError(nonExistentAccountResponse);
    }
}

