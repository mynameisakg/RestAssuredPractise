package BookingAPITests;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BookingApiFeatureTests {

    String tokenId;

    @BeforeMethod
    public void getToken(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        tokenId =
            given().log().all()
                .contentType(ContentType.JSON)
                    .body(new File("./src/test/resources/jsons/auth.json"))
                        .when().log().all()
                            .post("/auth")
                                .then().log().all()
                                    .assertThat()
                                        .statusCode(200)
                                            .extract()
                                                .path("token");

        System.out.println("token id is ===> " + tokenId);

    }

    @Test
    public void getBookingIdsTest(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        Response response =
            given().log().all()
                .when().log().all()
                    .get("/booking")
                        .then().log().all()
                            .extract()
                                .response();

        JsonPath js = response.jsonPath();
        List<Integer> bookingIdList = js.getList("bookingid");
        int bookingIdCount = bookingIdList.size();
        System.out.println("Total booking ids are : " + bookingIdCount);
        for(Integer id : bookingIdList){
            Assert.assertNotNull(id);
        }

    }

    @Test
    public void getBookingTest(){

        int newBookingId = createBookingId();

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all()
                .header("Accept", "application/json")
                .pathParam("bookingID", newBookingId)
                .when().log().all()
                .get("/booking/{bookingID}")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("firstname", equalTo("Jim"))
                .and()
                .body("bookingdates.checkin", equalTo("2018-01-01"));


    }

    @Test
    public void createBookingTest(){

        Assert.assertNotNull(createBookingId());

    }

    @Test
    public void updateBookingTest(){
        int newBookingId = createBookingId();

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        //get booking id is an additional step to check if booking ID has been
        //created in the DB or not. If not we will get to know before making any
        //update. However this is an optional step.
        //REMEMBER --- Arrange, Act & Assert (AAA pattern)

        given()
            .pathParam("bookingId", newBookingId)
                .when()
                    .get("/booking/{bookingId}")
                        .then()
                            .assertThat()
                                    .statusCode(200);

        //update starts from here
        given().log().all()
                .pathParam("bookingId", newBookingId)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .header("Cookie","token="+tokenId)
                .body("{\n" +
                        "    \"firstname\" : \"James\",\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                .when().log().all()
                .put("/booking/{bookingId}")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("firstname", equalTo("James"))
                .and()
                .body("additionalneeds", equalTo("Breakfast"));

    }

    @Test
    public void partialUpdateBookingTest(){
        int newBookingId = createBookingId();

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        //get booking id is an additional step to check if booking ID has been
        //created in the DB or not. If not we will get to know before making any
        //update. However this is an optional step.
        //REMEMBER --- Arrange, Act & Assert (AAA pattern)

        given()
                .pathParam("bookingId", newBookingId)
                .when()
                .get("/booking/{bookingId}")
                .then()
                .assertThat()
                .statusCode(200);

        //update starts from here
        given().log().all()
                .pathParam("bookingId", newBookingId)
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .header("Cookie","token="+tokenId)
                .body("{\n" +
                        "    \"firstname\" : \"Test\",\n" +
                        "    \"lastname\" : \"APIAutomation\"\n" +
                        "}")
                .when().log().all()
                .patch("/booking/{bookingId}")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("firstname", equalTo("Test"))
                .and()
                .body("lastname", equalTo("APIAutomation"))
                .and()
                .body("additionalneeds", equalTo("Breakfast"));

    }

    @Test
    public void deleteBookingTest(){
        int newBookingId = createBookingId();

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        //REMEMBER --- Arrange, Act & Assert (AAA pattern)

        given()
                .pathParam("bookingId", newBookingId)
                .when()
                .get("/booking/{bookingId}")
                .then()
                .assertThat()
                .statusCode(200);

        //delete starts from here
        given().log().all()
            .pathParam("bookingId", newBookingId)
                .contentType(ContentType.JSON)
                    .header("Accept", "application/json")
                        .header("Cookie","token="+tokenId)
                            .when().log().all()
                                .delete("/booking/{bookingId}")
                                    .then().log().all()
                                        .assertThat()
                                            .statusCode(201);

    }

    public int createBookingId(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        int bookingId =
            given().log().all()
                .contentType(ContentType.JSON)
                    .header("Accept", "application/json")
                    .body("{\n" +
                            "    \"firstname\" : \"Jim\",\n" +
                            "    \"lastname\" : \"Brown\",\n" +
                            "    \"totalprice\" : 111,\n" +
                            "    \"depositpaid\" : true,\n" +
                            "    \"bookingdates\" : {\n" +
                            "        \"checkin\" : \"2018-01-01\",\n" +
                            "        \"checkout\" : \"2019-01-01\"\n" +
                            "    },\n" +
                            "    \"additionalneeds\" : \"Breakfast\"\n" +
                            "}")
                        .when().log().all()
                            .post("/booking")
                                .then().log().all()
                                    .extract()
//                                        .jsonPath() // Use jsonPath() to parse the response as JSON
//                                            .getInt("bookingid"); // Extract the bookingid as an integer
                                                    .path("bookingid"); //both ways work, this and above

        System.out.println("booking id is : " + bookingId);
        return bookingId;
    }

}
