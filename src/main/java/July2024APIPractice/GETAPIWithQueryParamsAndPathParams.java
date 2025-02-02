package July2024APIPractice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class GETAPIWithQueryParamsAndPathParams {

    @Test
    public void getUsersWith_QueryParams(){
        RestAssured.baseURI = "https://gorest.co.in";

        given().log().all()
                //header is not needed for this get call but we can add that too..
            .header("Authorization","dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
                .queryParam("name","trivedi")
                .queryParam("status","active")
                    .when().log().all()
                        .get("/public/v2/users")
                            .then().log().all()
                                .assertThat()
                                    .statusCode(200)
                                        .and()
                                            .statusLine("HTTP/1.1 200 OK")
                                                .and()
                                                    .contentType(ContentType.JSON)
                                                        .and()
                                                            .body("$.size()",equalTo(10));

    }

//    @Test
//    public void getUsersWith_QueryParams_WithHashMap(){
//        RestAssured.baseURI = "https://gorest.co.in";
//        Map<String, String> queryMap = new HashMap<String, String>();
//        queryMap.put("name", "trivedi");
//        queryMap.put("status", "active");
//        queryMap.put("gender", "male");
//
//        given().log().all()
//                //header is not needed for this get call but we can add that too..
//                .header("Authorization","dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
//                    .queryParams(queryMap)
//                            .when().log().all()
//                                .get("/public/v2/users")
//                                    .then().log().all()
//                                        .assertThat()
//                                            .statusCode(200)
//                                                .and()
//                                                    .statusLine("HTTP/1.1 200 OK")
//                                                        .and()
//                                                            .contentType(ContentType.JSON)
//                                                                .and()
//                                                                    .body("$.size()",equalTo(10));
//
//    }
//
//    @Test
//    public void getUsersWith_QueryParams_WithHashMapof() {
//        RestAssured.baseURI = "https://gorest.co.in";
//        Map<String, String> queryMap = Map.of(
//                "name", "trivedi",
//                "status", "active",
//                "gender", "male"
//        );
//
//
//        given().log().all()
//                //header is not needed for this get call but we can add that too..
//                .header("Authorization", "dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
//                .queryParams(queryMap)
//                .when().log().all()
//                .get("/public/v2/users")
//                .then().log().all()
//                .assertThat()
//                .statusCode(200);
//    }

//    @DataProvider
//    public Object[][] getUserDetails(){
//        Map<String, String> queryParams = new HashMap<String, String>();
//        queryParams.put("name", "trivedi");
//        queryParams.put("gender", "male");
//        queryParams.put("status", "active");
//
//        return new Object[][]{{queryParams}};
//    }
//
//    @Test(dataProvider = "getUserDetails")
//    public void getUsersWith_QueryParams_WithDataProvider(Map<String, String> queryParams) {
//        RestAssured.baseURI = "https://gorest.co.in";
//        given().log().all()
//                //header is not needed for this get call but we can add that too..
//                .header("Authorization", "dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
//                    .queryParams(queryParams)
//                        .when().log().all()
//                            .get("/public/v2/users")
//                                .then().log().all()
//                                    .assertThat()
//                                        .statusCode(200);
//    }

//    @DataProvider
//    public Object[][] getUserId(){
//        return new Object[][]{
//                {"7381532"},
//                {"7381533"},
//                {"7381534"}
//        };
//
//    }
//    @Test(dataProvider = "getUserId")
//    public void getUsersWith_PathParams(String userId) {
//        RestAssured.baseURI = "https://gorest.co.in";
//
//
//        given().log().all()
//                //header is not needed for this get call but we can add that too..
//                .header("Authorization", "dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
//                .pathParam("userID",userId)
//                .when().log().all()
//                .get("/public/v2/users/{userID}/posts")
//                .then().log().all()
//                .assertThat()
//                .statusCode(200);
//    }

    @DataProvider
    public Object[][] getUserId_ForHasItemMethod(){
        return new Object[][]{
                {"7673098"}
        };

    }
    @Test(dataProvider = "getUserId_ForHasItemMethod")
    public void getUsersWith_PathParams_ForHasItemMethod(String userId) {
        RestAssured.baseURI = "https://gorest.co.in";


        given().log().all()
                //header is not needed for this get call but we can add that too..
                .header("Authorization", "dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
                .pathParam("userID",userId)
                .when().log().all()
                .get("/public/v2/users/{userID}/posts")
                .then().log().all()
                .assertThat()
                .statusCode(200)
//                .body("title", equalTo("Substantia caterva alo odit animus.")); //This will not work for json array will only work for json object
                .body("title", hasItem("Substantia caterva alo odit animus."));
    }
}
