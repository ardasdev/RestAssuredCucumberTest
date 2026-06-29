package hotelreservation.services;

import hotelreservation.models.Auth;
import hotelreservation.models.Booking;
import hotelreservation.models.BookingResponse;
import hotelreservation.models.Bookingdates;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ReservationService extends BaseTest{

    //token oluştur

    /*
    curl -X POST \
  https://restful-booker.herokuapp.com/auth \
  -H 'Content-Type: application/json' \
  -d '{
    "username" : "admin",
    "password" : "password123"
}'
     */

    public String generateToken(){

        Auth authbody = new Auth("admin","password123");

        Response response = given(spec)
                .contentType(ContentType.JSON)
                .when()
                .body(authbody)
                .post("/auth");


        response
                .then()
                .statusCode(200);

        return response.jsonPath().getJsonObject("token");

    }

    //rezervasyon oluştur

    /*
    curl -X POST \
  https://restful-booker.herokuapp.com/booking \
  -H 'Content-Type: application/json' \
  -d '{
    "firstname" : "Jim",
    "lastname" : "Brown",
    "totalprice" : 111,
    "depositpaid" : true,
    "bookingdates" : {
        "checkin" : "2018-01-01",
        "checkout" : "2019-01-01"
    },
    "additionalneeds" : "Breakfast"
}'
     */

    public BookingResponse createBooking(){

        Bookingdates bookingdates = new Bookingdates("2026-01-01","2027-01-01");
        Booking booking = new Booking("Arda","Özel",1000,false, bookingdates,"Sigara içilen oda");

        Response response = given(spec)
                .contentType(ContentType.JSON)
                .when()
                .body(booking)
                .post("/booking");


        return response.as(BookingResponse.class);
    }

    //rezervasyon silme

    /*
    curl -X DELETE \
  https://restful-booker.herokuapp.com/booking/1 \
  -H 'Content-Type: application/json' \
  -H 'Cookie: token=abc123'
     */

    public void deleteReservation(String token , int bookingid){

        Response response = given(spec)
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+token)
                .when()
                .delete("/booking/"+ bookingid);

        response
                .then()
                .statusCode(201);

    }

    public void assertBookingNotFound(int bookingid){

        try {
            given(spec)
                    .when()
                    .get("/booking/" + bookingid);
            throw new AssertionError("Rezervasyon silinmemis");
        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            assert e.getMessage().contains("404") || e.getMessage().contains("Not Found");
        }

    }

    /*

curl -X PUT \
  https://restful-booker.herokuapp.com/booking/1 \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -H 'Cookie: token=abc123' \
  -d '{
    "firstname" : "James",
    "lastname" : "Brown",
    "totalprice" : 111,
    "depositpaid" : true,
    "bookingdates" : {
        "checkin" : "2018-01-01",
        "checkout" : "2019-01-01"
    },
    "additionalneeds" : "Breakfast"
}'


     */



    public Booking updateBooking(String token,int bookingid){


        Bookingdates bookingdates = new Bookingdates("2026-06-01","2026-12-31");
        Booking booking = new Booking("Arda","Özel",2000,true, bookingdates,"Deniz manzarası");


        Response response = given(spec)
                .contentType("application/json; charset=utf-8")
                .accept("application/json")
                .header("Cookie", "token=" + token)
                .body(booking)
                .when()
                .put("/booking/"+bookingid);

        response
                .then()
                .statusCode(200);

        return response.as(Booking.class);

    }


    /*

    curl -X PUT \
  https://restful-booker.herokuapp.com/booking/1 \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -H 'Cookie: token=abc123' \
  -d '{
    "firstname" : "James",
    "lastname" : "Brown"
}'
     */


    public Booking partialUpdate(String token, int bookingid){

        Map<String, Object> partialBody = new HashMap<>();
        partialBody.put("firstname", "Mehmet");
        partialBody.put("totalprice", 500);

        Response response = given(spec)
                .contentType("application/json; charset=utf-8")
                .accept("application/json")
                .header("Cookie", "token=" + token)
                .body(partialBody)
                .when()
                .patch("/booking/" + bookingid);

        response
                .then()
                .statusCode(200);

        return response.as(Booking.class);

    }


}
