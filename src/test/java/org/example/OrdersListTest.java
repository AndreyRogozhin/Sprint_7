package org.example;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.hamcrest.MatcherAssert;


public class OrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @Step("Получение списка заказов без указания ID курьера")
    public void runTestOrdersListEmptyIdStatus200() {

        Response response =
                (Response) given()
                        .get("/api/v1/orders");
        response.then().assertThat().body("orders", notNullValue())
                .and()
        .statusCode(200);

    }

    @Test
    @Step("Получение списка заказов для курьера с неизвестным ID")
    public void runTestOrdersListUnknownIdStatus404() {

        Response response =
                (Response) given()
                        .get("/api/v1/orders?courierId=-1");
        response.then().assertThat().body("message", notNullValue())
                .and()
                .statusCode(404);

    }

}

