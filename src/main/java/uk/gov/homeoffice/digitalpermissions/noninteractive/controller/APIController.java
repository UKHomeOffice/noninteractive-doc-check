package uk.gov.homeoffice.digitalpermissions.noninteractive.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.NonInteractiveData;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.NonInteractiveResponse;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class APIController {

    @RequestMapping(path = "/non-interactive", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(ACCEPTED)
    public NonInteractiveResponse submitAPIData(@RequestBody final NonInteractiveData data) {
        return accepted();
    }

    private NonInteractiveResponse accepted() {
        return new NonInteractiveResponse("ACCEPTED");
    }
}
