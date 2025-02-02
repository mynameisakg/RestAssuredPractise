package POSTWithAuthAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class AuthAPITest {

    @Test
    public void getAuthTokenTest_WithJSON(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String tokenID =
            given()
//                .contentType(ContentType.JSON)   //both way of contentType works.
                    .contentType("application/json")
                    .body("{\n" +
                            "    \"username\" : \"admin\",\n" +
                            "    \"password\" : \"password123\"\n" +
                            "}")
                        .when()
                            .post("/auth")
                                .then()
                                    .assertThat()
                                        .statusCode(200)
                                            .extract()
                                                .path("token");

        System.out.println("Token is : " + tokenID);

        Assert.assertNotNull(tokenID);
    }
}
