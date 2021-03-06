package uk.gov.homeoffice.digitalpermissions.noninteractive.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public class NonInteractiveData {
    private String messageSequenceId;
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @JsonProperty(required = true)
    private Date messageReceived;

    private ServiceInformation service = new ServiceInformation();
    private List<Passenger> passengers = new ArrayList<>();

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        if (passengers == null) {
            this.passengers = new ArrayList<>();
        } else {
            this.passengers = passengers;
        }
    }

    public ServiceInformation getService() {
        return service;
    }

    public void setService(ServiceInformation service) {
        this.service = service;
    }

    public Date getMessageReceived() {
        return messageReceived;
    }

    public void setMessageReceived(Date messageReceived) {
        this.messageReceived = messageReceived;
    }

    public String getMessageSequenceId() {
        return messageSequenceId;
    }

    public void setMessageSequenceId(String messageSequenceId) {
        this.messageSequenceId = messageSequenceId;
    }
}
