package uk.gov.homeoffice.digitalpermissions.noninteractive.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.NonInteractiveData

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class APIControllerTest extends Specification {

    @LocalServerPort
    int port

    @Autowired
    TestRestTemplate restTemplate


    def "Should return ACCEPTED response"() {
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", new NonInteractiveData(), Map.class)
        then:
            response.statusCodeValue == 202
            response.body.status == 'ACCEPTED'
            response.body.timestamp
    }
}
