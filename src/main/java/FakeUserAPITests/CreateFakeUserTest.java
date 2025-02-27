package FakeUserAPITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateFakeUserTest {

    @Test
    public void createUserTestWith_LombokPOJO(){
        RestAssured.baseURI = "https://fakestoreapi.com";

        User.Address.Geolocation geolocation = new User.Address.Geolocation("-37.3159","81.1496");

        User.Address address = new User.Address("Delhi", "new road", 7682, "12926-3874", geolocation);

        User.Name name = new User.Name("Ankush", "Gupta");

        User user = new User("ankush@gmail.com","ankush","m38rmF$","1-570-236-7223",name,address);

        given().log().all()
            .contentType(ContentType.JSON)
                .body(user)
                    .when().log().all()
                        .post("/users")
                            .then().log().all()
                                .assertThat()
                                    .statusCode(200);
    }
}
