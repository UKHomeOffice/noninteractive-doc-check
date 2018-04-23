package uk.gov.homeoffice.digitalpermissions.noninteractive.model;

public interface SummaryStats {
    String getCarrier();

    long getPassengerCount();

    long getMessageCount();

    long getMinutesBeforeSTD();
}
