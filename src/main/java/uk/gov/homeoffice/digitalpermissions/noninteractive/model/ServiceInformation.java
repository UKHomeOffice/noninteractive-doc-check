package uk.gov.homeoffice.digitalpermissions.noninteractive.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static java.util.Optional.ofNullable;

public class ServiceInformation {
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date scheduledDepartureTime;
    private String carrier;
    private String route;

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setScheduledDepartureTime(Date scheduledDepartureTime) {
        this.scheduledDepartureTime = scheduledDepartureTime;
    }

    public Optional<Date> getScheduledDepartureTime() {
        return ofNullable(scheduledDepartureTime);
    }
}
