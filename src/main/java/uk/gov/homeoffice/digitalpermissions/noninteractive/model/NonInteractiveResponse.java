package uk.gov.homeoffice.digitalpermissions.noninteractive.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class NonInteractiveResponse {

    private final String status;
    private final Date timestamp = new Date();

    public NonInteractiveResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    public Date getTimestamp() {
        return timestamp;
    }
}
