package uk.gov.homeoffice.digitalpermissions.noninteractive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.MessageStats;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.NonInteractiveData;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.NonInteractiveResponse;
import uk.gov.homeoffice.digitalpermissions.noninteractive.service.MessageAnalysis;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class APIController {
    private static final Logger LOGGER = LoggerFactory.getLogger(APIController.class);

    private final MessageAnalysis analysis;

    public APIController(MessageAnalysis analysis) {
        this.analysis = analysis;
    }

    @RequestMapping(path = "/non-interactive", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(ACCEPTED)
    public NonInteractiveResponse submitAPIData(@RequestBody final NonInteractiveData data) {
        if (data.getMessageReceived() == null) {
            throw new MissingFieldException("messageReceived");
        }
        try {
            final MessageStats stats = analysis.analyse(data);
            LOGGER.info(stats.toLogLine());
        } catch (RuntimeException e) {
            LOGGER.warn("Unable to calculate statistics for API message sequenceId '{}': '{}'", data.getMessageSequenceId(), e.getMessage(), e);
        }
        return accepted();
    }

    @ResponseStatus(BAD_REQUEST)
    private final class MissingFieldException extends RuntimeException {
        public MissingFieldException(String message) {
            super("The field '" + message + "' was missing.");
        }
    }


    private NonInteractiveResponse accepted() {
        return new NonInteractiveResponse("ACCEPTED");
    }
}
