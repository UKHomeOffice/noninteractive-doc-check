package uk.gov.homeoffice.digitalpermissions.noninteractive

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import uk.gov.homeoffice.digitalpermissions.noninteractive.controller.APIController


@SpringBootTest
class ApplicationTest extends Specification {

    @Autowired
    def APIController controller

    def "Application starts up"() {
        when:
            true
        then:
            controller

    }
}
