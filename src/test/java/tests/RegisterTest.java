package tests;

import dto.ErrorRegisterResponse;
import dto.RegisterRequest;
import dto.SuccessRegisterResponse;
import dto.UserDataResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    @Test
    public void successRegistration(){
        RegisterRequest requestBody = new RegisterRequest("eve.holt@reqres.in", "pistol");
       SuccessRegisterResponse response =  given().baseUri("https://reqres.in")
                .body(requestBody)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(200)
               .extract().body().jsonPath().getObject("", SuccessRegisterResponse.class);

        System.out.println("id from response: " + response.getId());


        //id, token fields are not empty
        assertNotNull(response.getId());
        assertFalse(response.getToken().isEmpty());

        //id is positive value
        assertTrue(response.getId().intValue() > 0);




        // "id": 4,
        assertEquals(response.getId(), 4);

        //    "token": "QpwL5tke4Pnpja7X4"
        assertEquals(response.getToken(), "QpwL5tke4Pnpja7X4");

//"Missing password"


    }

    @Test
    public void registerWithoutPassword(){
        RegisterRequest requestBody = new RegisterRequest("eve.holt@reqres.in", "");
        ErrorRegisterResponse response =  given().baseUri("https://reqres.in")
                .body(requestBody)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(400)
                .extract().body().jsonPath().getObject("", ErrorRegisterResponse.class);
        assertEquals(response.getError(), "Missing password");

    }


    //register without email
    @Test
    public void registerWithoutEmail(){
        RegisterRequest requestBody = new RegisterRequest("", "pistol");
        ErrorRegisterResponse response =  given().baseUri("https://reqres.in")
                .body(requestBody)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(400)
                .extract().body().jsonPath().getObject("", ErrorRegisterResponse.class);
        assertEquals(response.getError(), "Missing email or username");

    }



    //register with empty body
    @Test
    public void registerWithEmptyBody(){
        RegisterRequest requestBody = new RegisterRequest("", "");
        ErrorRegisterResponse response =  given().baseUri("https://reqres.in")
                .body(requestBody)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(400)
                .extract().body().jsonPath().getObject("", ErrorRegisterResponse.class);
        assertEquals(response.getError(), "Missing email or username");

    }


    //register with invalid data
    @Test
    public void registerWithInvalidData_Email(){
        RegisterRequest requestBody = new RegisterRequest("eve.holt@reqres.i", "pistol");
        ErrorRegisterResponse response =  given().baseUri("https://reqres.in")
                .body(requestBody)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/api/register")
                .then().log().all().statusCode(400)
                .extract().body().jsonPath().getObject("", ErrorRegisterResponse.class);
        assertEquals(response.getError(), "Note: Only defined users succeed registration");
    }




}
