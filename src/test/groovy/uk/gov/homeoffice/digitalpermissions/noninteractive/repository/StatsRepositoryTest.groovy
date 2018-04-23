package uk.gov.homeoffice.digitalpermissions.noninteractive.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.MessageStats

@SpringBootTest
class StatsRepositoryTest extends Specification {

    @Autowired
    StatsRepository statsRepository

    def "Should return summary statistics"() {
        given:
            statsRepository.save(new MessageStats("12", "Carrier1", "route", 23, 12))
            statsRepository.save(new MessageStats("12", "Carrier2", "route", 34, 34))
            statsRepository.save(new MessageStats("12", "Carrier2", "route", 36, 30))

        when:
            def stats = statsRepository.getSummaryStats()

        then:
            stats.size() == 2
            stats[0].carrier == "Carrier1"
            stats[0].passengerCount == 12
            stats[0].messageCount == 1
            stats[0].minutesBeforeSTD == 23
            stats[1].carrier == "Carrier2"
            stats[1].passengerCount == 32
            stats[1].messageCount == 2
            stats[1].minutesBeforeSTD == 35
    }


}
