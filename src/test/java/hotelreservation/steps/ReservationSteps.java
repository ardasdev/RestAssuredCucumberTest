package hotelreservation.steps;

import hotelreservation.models.BookingResponse;
import hotelreservation.services.ReservationService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class ReservationSteps {

    ReservationService reservationService;
    String authkey;
    BookingResponse bookingResponse;

    @Given("Kullanici yeni bir rezervasyon olusturuyor")
    public void kullanici_yeni_bir_rezervasyon_olusturuyor() {

        reservationService = new ReservationService();


    }

    @Given("Kullanici rezervasyon icin gereken bilgileri veriyor")
    public void kullanici_rezervasyon_icin_gereken_bilgileri_veriyor() {

        authkey = reservationService.generateToken();

    }

    @When("Kullanici otel rezervasyonu yaratiyor")
    public void kullanici_otel_rezervasyonu_yaratiyor() {
        bookingResponse = reservationService.createBooking();
    }

    @Then("Rezervasyon basarili sekildi olusturuldu")
    public void rezervasyon_basarili_sekildi_olusturuldu() {
        Assertions.assertEquals("Arda", bookingResponse.getBooking().getFirstname());
        Assertions.assertEquals("Özel", bookingResponse.getBooking().getLastname());
        Assertions.assertEquals(1000, bookingResponse.getBooking().getTotalprice());
        Assertions.assertFalse(bookingResponse.getBooking().isDepositpaid());
        Assertions.assertEquals("Sigara içilen oda", bookingResponse.getBooking().getAdditionalneeds());


    }

    @Then("Kullanici olustulan rezervasyonu iptal ediyor")
    public void kullanici_olustulan_rezervasyonu_iptal_ediyor() {


        reservationService.deleteReservation(authkey,bookingResponse.getBookingid());


    }
}
