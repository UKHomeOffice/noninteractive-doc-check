package uk.gov.homeoffice.digitalpermissions.noninteractive.service;

import uk.gov.homeoffice.digitalpermissions.noninteractive.model.MessageStats;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.NonInteractiveData;

public class MessageAnalysis {
    public MessageStats analyse(final NonInteractiveData message) {
        return new MessageStats(message.getMessageSequenceId(),
                                message.getService().getCarrier(),
                                message.getService().getRoute(),
                                minutesBeforeSTD(message),
                                message.getPassengers().size());

    }

    private Long minutesBeforeSTD(final NonInteractiveData message) {
        return message.getService().getScheduledDepartureTime().map(d -> {
            final long std = d.getTime();
            final long received = message.getMessageReceived().getTime();

            return (std - received) / 60000;
        }).orElse(null);
    }
}
