package uk.gov.homeoffice.digitalpermissions.noninteractive.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.NonInteractiveData
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.Passenger
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.ServiceInformation

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class APIControllerTest extends Specification {

    @LocalServerPort
    int port

    @Autowired
    TestRestTemplate restTemplate


    def "Should return ACCEPTED response"() {
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", generateData(), Map.class)
        then:
            response.statusCodeValue == 202
            response.body.status == 'ACCEPTED'
            response.body.timestamp
    }

    def "Should return ACCEPTED response with no STD"() {
        given:
            def data = generateData()
            data.service.scheduledDepartureTime = null
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", data, Map.class)
        then:
            response.statusCodeValue == 202
            response.body.status == 'ACCEPTED'
            response.body.timestamp
    }

    def "Should return ACCEPTED response with no passengers"() {
        given:
            def data = generateData()
            data.passengers = null
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", data, Map.class)
        then:
            response.statusCodeValue == 202
            response.body.status == 'ACCEPTED'
            response.body.timestamp
    }

    def "Should return ACCEPTED response with no messageSequenceId"() {
        given:
            def data = generateData()
            data.messageSequenceId = null
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", data, Map.class)
        then:
            response.statusCodeValue == 202
            response.body.status == 'ACCEPTED'
            response.body.timestamp
    }

    def "Should return ACCEPTED response with no carrier"() {
        given:
            def data = generateData()
            data.service.carrier = null
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", data, Map.class)
        then:
            response.statusCodeValue == 202
            response.body.status == 'ACCEPTED'
            response.body.timestamp
    }

    def "Should return ACCEPTED response with no route"() {
        given:
            def data = generateData()
            data.service.route = null
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", data, Map.class)
        then:
            response.statusCodeValue == 202
            response.body.status == 'ACCEPTED'
            response.body.timestamp
    }

    def "Should return BAD_REQUEST response if no messageReceived"() {
        given:
            def data = generateData()
            data.messageReceived = null
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", data, Map.class)
        then:
            response.statusCodeValue == 400
    }

    def generateData() {
        def data = new NonInteractiveData()
        data.messageReceived = new Date()
        data.messageSequenceId = "0001"
        def service = new ServiceInformation()
        service.carrier = "Home Office Seaways"
        service.route = "G432"
        service.scheduledDepartureTime = new Date()
        data.service = service
        data.passengers = (1..4).collect {
            i -> new Passenger()
        }.asList()
        data
    }
}
