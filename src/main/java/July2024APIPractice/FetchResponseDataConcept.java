package July2024APIPractice;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class FetchResponseDataConcept {
    @Test
    public void fetchError_UsingExtract(){
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com/";

        String errorMessage =
            given()
                .header("Authorization", "Bearer eyJhbGci")
                    .when()
                        .get("/contacts")
                            .then()
                                .extract()
                                    .path("error");
        System.out.println("Error message is : " + errorMessage);
        Assert.assertEquals(errorMessage, "Please authenticate.");


    }

    @Test
    public void fetchError_UsingBody(){
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com/";
            given().log().all()
                .header("Authorization", "Bearer eyJhbGci")
                    .when().log().all()
                        .get("/contacts")
                            .then().log().all()
                                .assertThat()
                                    .body("error", equalTo("Please authenticate."))
                                        .and()
                                            .assertThat()
                                                .statusCode(401);


    }

    @Test
    public void getSingleUserData() {
        RestAssured.baseURI = "https://gorest.co.in";

        Response response =
                given()
                //header is not needed for this get call but we can add that too..
                    .header("Authorization", "dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
                        .when()
                            .get("/public/v2/users/7673077");

        System.out.println("Status Code is : " + response.statusCode());
        System.out.println("Status Line is : " + response.statusLine());

        response.prettyPrint();
        JsonPath js = response.jsonPath();
        int userID = js.getInt("id");
        String email = js.getString("email");
        String gender = js.getString("gender");
        System.out.println("User with id " + userID + " has email id as " + email + " and gender as " + gender);
    }

    @Test
    public void getUsersData() {
        RestAssured.baseURI = "https://gorest.co.in";

        Response response =
                given()
                        //header is not needed for this get call but we can add that too..
                        .header("Authorization", "dd10d5b9022a5542f410f62ca7b8a3126ce9493b0a5d14a180d53e77b8631203")
                        .when()
                        .get("/public/v2/users");

        System.out.println("Status Code is : " + response.statusCode());
        System.out.println("Status Line is : " + response.statusLine());

        response.prettyPrint();
        JsonPath js = response.jsonPath();

        List<Integer> usersId = js.getList("id");
        System.out.println(usersId);

        Assert.assertTrue(usersId.contains(7673077));

        List<String> names = js.getList("name");
        System.out.println(names);

        for(Integer e : usersId){
            System.out.println(e);
        }

    }

    @Test
    public void getProductsNestedData() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response =
                given()
                    .when()
                        .get("/products");

        System.out.println("Status Code is : " + response.statusCode());
        System.out.println("Status Line is : " + response.statusLine());

        response.prettyPrint();
        JsonPath js = response.jsonPath();
        List<Float> rateList = js.getList("rating.rate");
        System.out.println(rateList);

        List<Integer> countList = js.getList("rating.count");
        System.out.println(countList);

        List<Integer> productsIdList = js.getList("id");
        System.out.println(productsIdList);

        List<Float> priceList = js.getList("price");
        System.out.println(priceList);

        for(int i=0; i< productsIdList.size(); i++){
            System.out.println("The product id is " + productsIdList.get(i) + " with price " + productsIdList.get(i) + " and rating " + rateList.get(i) + " with an available count of " + countList.get(i));
        }

        Assert.assertTrue(rateList.contains((4.7f)));
    }
}
