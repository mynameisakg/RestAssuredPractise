package POSTWithAuthAPI;

import Pojo.Credentials;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class AuthAPITest {

    @Test
    public void getAuthTokenTest_WithJSON(){
        /*
        * We are currently fetching the token ID individually
        * but in future we can use this same token ID in before
        * test annotation so that this token ID can be used for
        * other test cases/API's also.
        * */

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String tokenID =
            given()
//                .contentType(ContentType.JSON)   //all three ways of contentType works.
//                    .header("Content-Type", "application/json")
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

    @Test
    public void getAuthTokenTest_WithJSONFile() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        try {
            // Read the JSON file content
//            String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/resources/jsons/auth.json")));

            String tokenID =
                    given().log().all()
                            .contentType(ContentType.JSON)
//                            .body(jsonContent)  // Pass the JSON content as the body
                            .body(new File("./src/test/resources/jsons/auth.json"))
                            .when().log().all()
                            .post("/auth")
                            .then().log().all()
                            .assertThat()
                            .statusCode(200)
                            .extract()
                            .path("token");

            System.out.println("Token is : " + tokenID);

            Assert.assertNotNull(tokenID);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred: " + e.getMessage());
        }
    }

    //WIP
    @Test
    public void getAuthTokenTest_WithPOJOClass() {
        //POJO- Plain old Java object
        //For creating a java file, create a package. For creating a non java
        //file, create a folder.
        //Earlier in RA, with body(Object obj) method, the seraialization i.e
        //conversion of pojo to json would take place automatically but with recent
        //RA we have to use Object Mapper class i.e. Jackson Databind,GSON, etc.
        //Object Mapper will convert POJO to Json.
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Credentials cred = new Credentials("admin", "password123");

        try {

            String tokenID =
                    given().log().all()
                            .contentType(ContentType.JSON)
                            .body(cred) //pojo to json : serialization: ObjectMapper(Jackson databind)
                            .when().log().all()
                            .post("/auth")
                            .then().log().all()
                            .assertThat()
                            .statusCode(200)
                            .extract()
                            .path("token");

            System.out.println("Token is : " + tokenID);

            Assert.assertNotNull(tokenID);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred: " + e.getMessage());

            //json ----> pojo: De-serialization
        }
    }
}
