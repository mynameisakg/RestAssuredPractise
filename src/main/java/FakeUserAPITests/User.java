package FakeUserAPITests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JSON Schema for payload
 * {
 *     "email": "ankush@gmail.com",
 *     "username": "ankush",
 *     "password": "m38rmF$",
 *     "name": {
 *         "firstname": "ankush",
 *         "lastname": "gupta"
 *     },
 *     "address": {
 *         "geolocation": {
 *             "lat": "-37.3159",
 *             "long": "81.1496"
 *         },
 *         "city": "Delhi",
 *         "street": "new road",
 *         "number": 7682,
 *         "zipcode": "12926-3874"
 *     },
 *     "phone": "1-570-236-7223"
 * }
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;
    private String username;
    private String password;
    private String phone;
    private Name name;
    private Address address;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Name {
        private String firstname;
        private String lastname;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address {
        private String city;
        private String street;
        private int number;
        private String zipcode;
        private Geolocation geolocation;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Geolocation {
            private String lat;
            @JsonProperty("long")
            private String longitude;
        }
    }
}
