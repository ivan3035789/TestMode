package ru.netology.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class UserGeneratorData {

    private final static String activeStatus = "active";
    private final static String blockedStatus = "blocked";

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Gson gson = new Gson();
    private static final Faker faker = new Faker(new Locale("en"));


    private UserGeneratorData() {
    }

    private static void registrationUsers(UserData userData) {
        given()
                .spec(requestSpec)
                .body(gson.toJson(userData))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
    private static String signs() {
        String[] signs = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "|", "?", ">", ":"};
        Random randomSigns = new Random();
        return signs[randomSigns.nextInt(signs.length)];
    }
    public static String login() {
        String login1 = faker.name().firstName() + signs() + faker.name().lastName();
        String login2 = faker.name().firstName();
        String login3 = faker.name().lastName();
        String login4 = faker.name().username();
        String login5 = faker.name().fullName();
        String[] login = {login1, login2, login3, login4, login5};
        Random loginRandom = new Random();
        return login[loginRandom.nextInt(login.length)];
    }
    public static String password() {
        return faker.internet().password(true);
    }

    public static UserData validActive() {
        UserData userData = new UserData(login(), password(), activeStatus);
        registrationUsers(userData);
        return userData;
    }

    public static UserData validBlocked() {
        UserData userData = new UserData(login(), password(), blockedStatus);
        registrationUsers(userData);
        return userData;
    }

    public static UserData invalidLogin() {
        String password = password();

        UserData userDataRegistration = new UserData(login(), password, activeStatus);
        registrationUsers(userDataRegistration);
        return new UserData(login(), password, activeStatus);
    }

    public static UserData invalidPassword() {
        String login = login();

        UserData userDataRegistration = new UserData(login, password(), activeStatus);
        registrationUsers(userDataRegistration);
        return new UserData(login, password(), activeStatus);
    }
}