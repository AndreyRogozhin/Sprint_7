package org.example;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import static io.restassured.RestAssured.*;
import static org.example.CourierGenerator.randomCourier;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.hamcrest.MatcherAssert;

public class LoginCourierTest {


    private String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private String LOGIN_URL = "/api/v1/courier/login";

    private Response response;
    private CourierClient courierClient;
    private Courier courier;
    private Credentials credentials;


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;

        courier = randomCourier();
        courierClient = new CourierClient();
        response = courierClient.create(courier);


    }

    @Test
    @Step("Успешная авторизация - код возврата 200")
    public void loginCourierSucessfullyStatus200() {
        credentials = courier.credsFromCourier();
        response = courierClient.login(credentials);

        response.then()
                .statusCode(200);
    }


    @Test
    @Step("Авторизация без указания логина - код возврата 400")
    public void loginCourierNoLoginStatus400() {
        Credentials credentialsNoLogin = new Credentials("", courier.getPassword());
        response = courierClient.login(credentialsNoLogin);

        response.then()
                .statusCode(400);
    }

    @Test
    @Step("Авторизация с указанием несуществующего логина - код возврата 400")
    public void loginCourierWrongLoginStatus404() {

        Credentials credentialsNewLogin = new Credentials(courier.getLogin() + "xxx", courier.getPassword());
        response = courierClient.login(credentialsNewLogin);
        response.then()
                .statusCode(404);
    }


    @Test
    @Step("Авторизация без указания пароля - код возврата 400")
    public void loginCourierNoPasswordStatus400() {
        Credentials credentialsNoPassword = new Credentials(courier.getLogin(), "");

        response = courierClient.login(credentialsNoPassword);

        response.then()
                .statusCode(400);
    }

    @After
    @Step("Удаление созданного клиента")
    public void tearDown() {
        credentials = courier.credsFromCourier();
        response = courierClient.login(credentials);
        Id id = response.body().as(Id.class);
        response = courierClient.delete(id.getId());

    }
}
