package July2024APIPractice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;

public class ContactsAPITest {

    @Test
    public void getContactsAPITest(){
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com/";
        given().log().all()
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NjlkY2RkZDkwZDBmODAwMTM2MmU0ZjUiLCJpYXQiOjE3MzgwMDA2MDF9.Klf-ErKWGyQHiD7BaDoJdH65NFxpyp-YN_UoNhfRfuw")
                .when().log().all()
                   .get("/contacts")
                        .then().log().all()
                            .assertThat()
                                .statusCode(200)
                                    .and()
                                        .statusLine("HTTP/1.1 200 OK")
                                            .and()
                                                .contentType(ContentType.JSON)
                                                    .and()
                                                        .body("$.size()",equalTo(5));

    }

    @Test
    public void getContactsAPINegTest(){
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com/";
        given().log().all()
            .header("Authorization", "Bearer eyJhbGc") //Incorrect token
                .when().log().all()
                    .get("/contacts")
                        .then().log().all()
                            .assertThat()
                                .statusCode(401)
                                    .and()
                                        .statusLine("HTTP/1.1 401 Unauthorized");
    }
}
