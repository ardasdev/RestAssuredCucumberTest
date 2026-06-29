package hotelreservation.services;

import hotelreservation.models.Auth;
import hotelreservation.models.Booking;
import hotelreservation.models.BookingResponse;
import hotelreservation.models.Bookingdates;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

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


}
