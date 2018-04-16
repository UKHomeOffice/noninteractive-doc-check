package uk.gov.homeoffice.digitalpermissions.noninteractive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.NonInteractiveData;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.NonInteractiveResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Controller
public class APIController {

    @RequestMapping(path = "/non-interactive", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public NonInteractiveResponse submitAPIData(@RequestBody final NonInteractiveData data) {
        return accepted();
    }

    private NonInteractiveResponse accepted() {
        return new NonInteractiveResponse();
    }
}
