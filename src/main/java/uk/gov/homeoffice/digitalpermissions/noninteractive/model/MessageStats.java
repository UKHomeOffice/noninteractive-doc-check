package uk.gov.homeoffice.digitalpermissions.noninteractive.model;

import java.util.Optional;

public class MessageStats {
    private final String carrier;
    private final String route;
    private final Long minutesBeforeSTD;
    private final int passengerCount;

    public MessageStats(String carrier, String route, Long minutesBeforeSTD, int passengerCount) {
        this.carrier = carrier;
        this.route = route;
        this.minutesBeforeSTD = minutesBeforeSTD;
        this.passengerCount = passengerCount;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getRoute() {
        return route;
    }

    public Optional<Long> getMinutesBeforeSTD() {
        return Optional.ofNullable(minutesBeforeSTD);
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public String toLogLine() {
        return "carrier='" + carrier + "'" +
               " route='" + route + "'" +
               getMinutesBeforeSTD().map(l ->" minutesBeforeSTD=" + l).orElse("") +
               " passengerCount=" + passengerCount;
    }
}
