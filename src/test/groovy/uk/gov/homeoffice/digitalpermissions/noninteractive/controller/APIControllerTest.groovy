package uk.gov.homeoffice.digitalpermissions.noninteractive.controller

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.NonInteractiveData
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.Passenger
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.ServiceInformation
import uk.gov.homeoffice.digitalpermissions.noninteractive.service.StatsLoggingService

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class APIControllerTest extends Specification {

    @LocalServerPort
    int port

    @Autowired
    TestRestTemplate restTemplate

    def appender = new ListAppender<ILoggingEvent>()
    def logger = (Logger) LoggerFactory.getLogger(StatsLoggingService)

    void setup() {
        appender.setContext(LoggerFactory.getILoggerFactory())
        appender.start()
        logger.addAppender(appender)
    }

    void cleanup() {
        logger.detachAppender(appender)
    }

    def "Should return ACCEPTED response"() {
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", generateData(), Map)
        then:
            response.statusCodeValue == 202
            response.body.status == 'ACCEPTED'
            response.body.timestamp
            appender.list.size() == 1
    }

    def "Should return ACCEPTED response with no STD"() {
        given:
            def data = generateData()
            data.service.scheduledDepartureTime = null
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", data, Map)
        then:
            response.statusCodeValue == 202
            response.body.status == 'ACCEPTED'
            response.body.timestamp
            appender.list.size() == 1
    }

    def "Should return ACCEPTED response with no passengers"() {
        given:
            def data = generateData()
            data.passengers = null
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", data, Map)
        then:
            response.statusCodeValue == 202
            response.body.status == 'ACCEPTED'
            response.body.timestamp
            appender.list.size() == 1
    }

    def "Should return ACCEPTED response with no messageSequenceId"() {
        given:
            def data = generateData()
            data.messageSequenceId = null
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", data, Map)
        then:
            response.statusCodeValue == 202
            response.body.status == 'ACCEPTED'
            response.body.timestamp
            appender.list.size() == 1
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
            appender.list.size() == 1
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
            appender.list.size() == 1
    }

    def "Should return BAD_REQUEST response if no messageReceived"() {
        given:
            def data = generateData()
            data.messageReceived = null
        when:
            def response = restTemplate.postForEntity("http://localhost:${port}/non-interactive", data, Map.class)
        then:
            response.statusCodeValue == 400
            appender.list.size() == 0
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
