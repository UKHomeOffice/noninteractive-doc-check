package uk.gov.homeoffice.digitalpermissions.noninteractive.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.MessageStats
import uk.gov.homeoffice.digitalpermissions.noninteractive.repository.StatsRepository

@SpringBootTest
class StatsLoggingServiceTest extends Specification {

    @Autowired
    StatsLoggingService service
    @Autowired
    StatsRepository repository

    void setup() {
        repository.deleteAll()
    }

    def "Should persist to database"() {
        given:
            def stats = new MessageStats("1234", "Flyways", "543", 12, 0)

        when:
            service.logMessageStats(stats)
            def saved = repository.findAll()[0]
        then:
            saved.carrier == stats.carrier
            saved.route == stats.route
            saved.passengerCount == stats.passengerCount
            saved.minutesBeforeSTD == stats.minutesBeforeSTD
            saved.sequenceId == stats.sequenceId
            saved.statsId
    }

    def "Should persist to database with no minutes before STD"() {
        given:
            def stats = new MessageStats("1234", "Flyways", "543", null, 0)

        when:
            service.logMessageStats(stats)
            def saved = repository.findAll()[0]
        then:
            saved.carrier == stats.carrier
            saved.route == stats.route
            saved.passengerCount == stats.passengerCount
            !saved.minutesBeforeSTD.isPresent()
            saved.sequenceId == stats.sequenceId
            saved.statsId
    }
}
