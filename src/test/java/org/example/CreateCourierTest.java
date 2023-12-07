package org.example;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import static io.restassured.RestAssured.*;
import static org.example.CourierGenerator.givenCourier;
import static org.example.CourierGenerator.randomCourier;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

import org.hamcrest.MatcherAssert;

public class CreateCourierTest {

    private Response response;
    private CourierClient courierClient;
    private Courier courier;
    private Credentials credentials;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        courier = randomCourier();
        courierClient = new CourierClient();
    }

    @Test
    @Step("Успешное создание курьера - код возврата 201")
    public void createCourierStatus201() {
        response = courierClient.create(courier);
        assertEquals("test fail!", 201, response.statusCode());
    }

    @Test
    @Step("Успешное создание курьера - ответ OK")
    public void createCourierGetOK() {
        response = courierClient.create(courier);
        response.then().assertThat().body("ok", equalTo(true));

    }

    @Test
    @Step("Повторное создание курьера - код возврата 409")
    public void createCourierTwiceStatus409() {
        response = courierClient.create(courier);
        response = courierClient.create(courier);
        assertEquals("test fail!", 409, response.statusCode());
    }


    @Test
    @Step("Создание курьера с тем же логином- код возврата 409")
    public void createCourierSameLoginStatus409() {
        response = courierClient.create(courier);

        Courier courier2 = givenCourier(courier.getLogin());
        response = courierClient.create(courier2);

        assertEquals("test fail!", 409, response.statusCode());
    }


    @Test
    @Step("Создание курьера без логина- код возврата 400")
    public void createCourierNoLoginStatus400() {
        courier.setLogin("");
        response = courierClient.create(courier);

        response.then().statusCode(400);
    }

    @Test
    @Step("Создание курьера без пароля - код возврата 400")
    public void createCourierNoPasswordStatus400() {
        courier.setPassword("");
        response = courierClient.create(courier);

        response.then().statusCode(400);

    }

    @After
    public void tearDown() {
        credentials = courier.credsFromCourier();
        response = courierClient.login(credentials);
        Id id = response.body().as(Id.class);
        response = courierClient.delete(id.getId());
    }

}

