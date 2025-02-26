package UpdateUser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserWithPOJO {

    public String getRandomUserEmailId(){
        String email = "testAutomation" + System.currentTimeMillis() + "@gmail.com";
        return email;
    }

    @Test
    public void updateUserWith_POJO(){

        RestAssured.baseURI = "https://gorest.co.in";

        Users user = new Users("test", "male", getRandomUserEmailId(), "active");

        Response response = given().log().all()
                                .contentType(ContentType.JSON)
                                    .header("Authorization", "Bearer dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
                                        .body(user)
                                            .when().log().all()
                                                .post("/public/v2/users");

        response.prettyPrint(); // Pretty printing is possible for content-types JSON, XML and HTML.

        int userId = response.jsonPath().getInt("id");
        System.out.println("user id is ===> " + userId);

        //update user
        user.setName("testApiUser");
        user.setStatus("inactive");
        given().log().all()
            .contentType(ContentType.JSON)
                .header("Authorization", "Bearer dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
                    .body(user)
                        .when().log().all()
                            .patch("/public/v2/users/" + userId)
                                .then().log().all()
                                    .assertThat()
                                        .statusCode(200)
                                            .and()
                                                .body("id", equalTo(userId))
                                                    .and()
                                                        .body("name",equalTo(user.getName()))
                                                            .and()
                                                                .body("status", equalTo(user.getStatus()));

    }

    @Test
    public void createUserWith_Lombok(){

        RestAssured.baseURI = "https://gorest.co.in";

//        Users user = new Users("test", "male", getRandomUserEmailId(), "active");
        UserLombok user = new UserLombok("test", "male", "apiBuilder"+getRandomUserEmailId(), "active");

        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
                .body(user)
                .when().log().all()
                .post("/public/v2/users");

        response.prettyPrint(); // Pretty printing is possible for content-types JSON, XML and HTML.

        int userId = response.jsonPath().getInt("id");
        System.out.println("user id is ===> " + userId);

    }

    @Test
    public void createUserWith_Builder(){

        RestAssured.baseURI = "https://gorest.co.in";

        UserLombok userLombok = new UserLombok.UserLombokBuilder()
                .name("test123").gender("female").email("apiBuilder"+getRandomUserEmailId()).status("active").build();

        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
                .body(userLombok)
                .when().log().all()
                .post("/public/v2/users");

        response.prettyPrint(); // Pretty printing is possible for content-types JSON, XML and HTML.

        int userId = response.jsonPath().getInt("id");
        System.out.println("user id is ===> " + userId);

    }
}
