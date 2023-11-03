package tests;

import dto.UserDataResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetUserListTest {
    @Test
    public void getUserListPage2(){
        List<UserDataResponse> users = given().baseUri("https://reqres.in")
                .when().log().all()
                .get("/api/users?page=2")
                .then().log().all().statusCode(200)
                .extract().body().jsonPath().getList("data", UserDataResponse.class);

        //check that 6 items are in list
        assertEquals(users.size(), 6);

        //check that all ids are positive values
        for (UserDataResponse user : users){
            assertTrue(user.getId() > 0);
            assertTrue(user.getEmail().endsWith("@reqres.in"));
            assertTrue(user.getAvatar().endsWith(user.getId() + "-image.jpg"));
        }


        users.forEach(user -> assertTrue(user.getId() > 0));

        //Check all emails end with "@reqres.in"
        users.forEach(user ->assertTrue(user.getEmail().endsWith("@reqres.in")));

        //All Avatars end with "id-value" + "-image.jpg"
        users.forEach(user -> assertTrue(user.getAvatar().endsWith(user.getId() + "-image.jpg")));

    }

}
