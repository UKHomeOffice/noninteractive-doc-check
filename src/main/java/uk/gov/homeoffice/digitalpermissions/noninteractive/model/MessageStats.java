package uk.gov.homeoffice.digitalpermissions.noninteractive.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
public class MessageStats {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATS_SEQ")
    @SequenceGenerator(sequenceName = "stats_seq", allocationSize = 1, name = "STATS_SEQ")
    private Long statsId;
    private final String sequenceId;
    private final String carrier;
    private final String route;
    private final Long minutesBeforeSTD;
    private final int passengerCount;
    private final Date receivedDate;

    MessageStats() {
        this(null, null, null, null, 0, null);
    }

    public MessageStats(String sequenceId,
                        String carrier,
                        String route,
                        Long minutesBeforeSTD,
                        int passengerCount,
                        Date receivedDate) {
        this.sequenceId = sequenceId;
        this.carrier = carrier;
        this.route = route;
        this.minutesBeforeSTD = minutesBeforeSTD;
        this.passengerCount = passengerCount;
        this.receivedDate = receivedDate;
    }

    public Long getStatsId() {
        return statsId;
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

    public String getSequenceId() {
        return sequenceId;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public String toLogLine() {
        return "sequenceId='" + sequenceId + "'" +
               " carrier='" + carrier + "'" +
               " route='" + route + "'" +
               getMinutesBeforeSTD().map(l -> " minutesBeforeSTD=" + l).orElse("") +
               " passengerCount=" + passengerCount;
    }
}
