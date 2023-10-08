package com.example.courier;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CourierTest {

    private final CourierClient client = new CourierClient();
    private final CourierCreating chek = new CourierCreating();
    private final CourierLogin chekLogin = new CourierLogin();
    private int courierId;

    @After
    public void deleteCourier() {
        client.delete(courierId);
    }

    @Test
    public void testCreateCourier() {
        // Тест на создание курьера (проверка успешного создания)
        var courier = CourierGenerator.random();
        ValidatableResponse response = client.create(Credentials.from(courier));
        chek.createdSuccessfully(response);

        // Тест на создание курьера без передачи обязательных полей
        ValidatableResponse createWithoutMandatoryFieldsResponse = client.createWithoutMandatoryFields();
        chek.insufficientDataForCreate(createWithoutMandatoryFieldsResponse);

        // Тест на попытку создания двух одинаковых курьеров
        ValidatableResponse duplicateCreateResponse = client.create(Credentials.from(courier));
        chek.duplicateCreateLoginError(duplicateCreateResponse);
    }

    @Test
    public void testLoginCourier() {
        // Создание двух разных курьеров
        var courier1 = CourierGenerator.random();
        var courier2 = CourierGenerator.random();

        // Создание первого курьера
        ValidatableResponse createResponse1 = client.create(Credentials.from(courier1));
        chek.createdSuccessfully(createResponse1);

        // Создание второго курьера
        ValidatableResponse createResponse2 = client.create(Credentials.from(courier2));
        chek.createdSuccessfully(createResponse2);

        // Попытка создания курьера с уже использованным логином
        ValidatableResponse duplicateCreateResponse = client.create(Credentials.from(courier1));
        chekLogin.duplicateLoginError(duplicateCreateResponse);

        // Вход с учетными данными первого курьера
        ValidatableResponse loginResponse1 = client.login(Credentials.from(courier1));
        chekLogin.loggedInSuccessfully(loginResponse1);

        // Вход с учетными данными второго курьера
        ValidatableResponse loginResponse2 = client.login(Credentials.from(courier2));
        chekLogin.loggedInSuccessfully(loginResponse2);

        // Тест на вход курьера без передачи обязательных полей
        ValidatableResponse invalidLoginResponse = client.loginWithEmptyValues();
        chekLogin.insufficientDataForLogin(invalidLoginResponse);

        // Тест на вход под несуществующим пользователем
        ValidatableResponse nonExistentAccountResponse = client.loginWithNonExistentAccount();
        chekLogin.accountNotFoundError(nonExistentAccountResponse);
    }
}

