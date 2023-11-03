package tests;

import dto.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteTest {
    @Test
public void deleteUser(){
    int requestId = 2;
CreateUserRequest requestBody = new CreateUserRequest("Moris", "tester");
        CreateUserResponse response =  given().baseUri("https://reqres.in")
                .body(requestBody)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("api/users")
                .then().log().all().statusCode(201)
                .extract().body().jsonPath().getObject("", CreateUserResponse.class);

     int id =response.getId();

     given().baseUri("https://reqres.in")
            .when().log().all()
            .delete("/api/users/" + requestId)
            .then().log().all().statusCode(204).extract().statusCode();

            //.extract().response().jsonPath().getObject("", DeleteUserResponse.class);


        UserDataResponse user =  given().baseUri("https://reqres.in")
                .when().log().all()
                .get("/api/users/" + requestId)
                .then().log().all().statusCode(200)
                .extract().body().jsonPath().getObject("data", UserDataResponse.class);

}
}
