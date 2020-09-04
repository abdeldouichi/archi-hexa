package com.heiwait.tripagency.domain.cucumber.steps;

import com.heiwait.tripagency.domain.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculateTripFeesSteps {

    @Mock
    private TripRepositoryPort tripRepositoryPort;
    @InjectMocks
    private TripPricer tripPricer;

    private final Trip trip = new Trip();
    private final Destination destination = new Destination();
    private TravelClass travelClass;

    private Integer computedPrice;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("^the customer wants to travel to \"([^\"]*)\"$")
    public void the_customer_wants_to_travel_to(String dest) {
        destination.setName(dest);
        trip.setDestination(destination);
        Mockito.when(tripRepositoryPort.findTripByDestination(destination)).thenReturn(trip);
    }

    @Given("^the customer wants to travel in \"([^\"]*)\" class$")
    public void the_customer_wants_to_travel_in_class(TravelClass travelClass) {
        this.travelClass = travelClass;
    }

    @Given("^the economic travel ticket price is (\\d+)€$")
    public void the_economic_travel_ticket_price_is_€(int ticketPrice) {
        trip.setTicketPrice(ticketPrice);
    }

    @Given("^the stay fees are (\\d+)€$")
    public void the_stay_fees_are_€(Integer stayFees) {
        trip.setStayFees(stayFees);
    }

    @Given("^the agency fees are (\\d+)€$")
    public void the_agency_fees_are_€(Integer agencyFees) {
        trip.setAgencyFees(agencyFees);
    }

    @When("^the system calculate the trip price")
    public void the_system_calculate_the_trip_price() {
        computedPrice = tripPricer.priceTrip(destination, travelClass);
    }

    @Then("^the trip price is (\\d+)€$")
    public void the_trip_price_is_€(Integer expectedPrice) {
        assertThat(expectedPrice).isEqualTo(computedPrice);
    }
}