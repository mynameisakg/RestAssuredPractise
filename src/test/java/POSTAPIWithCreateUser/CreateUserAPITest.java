package POSTAPIWithCreateUser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.SQLOutput;

import static io.restassured.RestAssured.given;

public class CreateUserAPITest {

    @Test
    public void getUserDetails_WithJSONFile(){
        //For this test to revise, create a new user email in user.json file.
        RestAssured.baseURI = "https://gorest.co.in";
        int userId =
             given().log().all()
                .header("Authorization","Bearer dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
                    .contentType(ContentType.JSON)
                        .body(new File("./src/test/resources/jsons/user.json"))
                            .when().log().all()
                                .post("/public/v2/users")
                                    .then().log().all()
                                        .assertThat()
                                            .statusCode(201)
                                                .extract()
                                                    .path("id");

        System.out.println("The user id is : " + userId);
        Assert.assertNotNull(userId);


    }

}
