package hotelreservation.steps;

import hotelreservation.models.Booking;
import hotelreservation.models.BookingResponse;
import hotelreservation.services.ReservationService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class ReservationSteps {

    ReservationService reservationService;
    String authkey;
    BookingResponse bookingResponse;
    Booking updatedBooking;
    Booking partialUpdatedBooking;


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

    @Then("Rezervasyon basarili sekilde silindi")
    public void rezervasyon_basarili_sekilde_silindi() {
        reservationService.assertBookingNotFound(bookingResponse.getBookingid());
    }

    @And("Kullanici olusturulan rezervasyonu güncelliyor")
    public void kullanici_olusturulan_rezervasyonu_güncelliyor() {

        updatedBooking = reservationService.updateBooking(authkey,bookingResponse.getBookingid());

    }

    @Then("Rezervasyon basarili sekilde guncellendi")
    public void rezervasyon_basarili_sekilde_guncellendi() {
        Assertions.assertEquals(2000, updatedBooking.getTotalprice());
        Assertions.assertTrue(updatedBooking.isDepositpaid());
        Assertions.assertEquals("Deniz manzarası", updatedBooking.getAdditionalneeds());
    }

    @And("Kullanici rezervasyonu kismi olarak guncelliyor")
    public void kullanici_rezervasyonu_kismi_olarak_guncelliyor() {
        partialUpdatedBooking = reservationService.partialUpdate(authkey, bookingResponse.getBookingid());
    }

    @Then("Sadece guncellenen alanlar degismis olmali")
    public void sadece_guncellenen_alanlar_degismis_olmali() {
        Assertions.assertEquals("Mehmet", partialUpdatedBooking.getFirstname());
        Assertions.assertEquals(500, partialUpdatedBooking.getTotalprice());
        Assertions.assertEquals("Özel", partialUpdatedBooking.getLastname());
    }

}
