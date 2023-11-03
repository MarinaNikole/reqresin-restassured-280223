package tests;


import dto.UserDataResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class GetSingleUserTest {
    @Test
    public void getValidUserTest(){
        int requestId = 2;
        UserDataResponse user =  given().baseUri("https://reqres.in")
                .when().log().all()
                .get("/api/users/" + requestId)
                .then().log().all().statusCode(200)
                .extract().body().jsonPath().getObject("data", UserDataResponse.class);
       // System.out.println("User id from User data class: " + user.getId());


        //Check that id from response equals to id from request (end-point)
        assertEquals(user.getId(), requestId);


        //Check that email is NOT empty
       // assertTrue(!user.getEmail().isEmpty());
        assertFalse(user.getEmail().isEmpty());

        //Check email ends "@reqres.in"
        assertTrue(user.getEmail().contains("@reqres.in"));
        assertTrue(user.getEmail().endsWith("@reqres.in"));


        //Check Avatar ends with "id-value" + "-image.jpg"
        assertTrue(user.getAvatar().endsWith(requestId + "-image.jpg"));

    }
}

