package uk.gov.homeoffice.digitalpermissions.noninteractive.service

import spock.lang.Specification
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.NonInteractiveData
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.Passenger
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.ServiceInformation

class MessageAnalysisTest extends Specification {

    def analysis = new MessageAnalysis()

    def "Should return statistics"() {
        given:
            def data = generateData(12, 20)
        when:
            def stats = analysis.analyse(data)
        then:
            stats.carrier == "Home Office Seaways"
            stats.route == "G432"
            stats.passengerCount == 12
            stats.minutesBeforeSTD.get() == 20
            stats.toLogLine() == "carrier='Home Office Seaways' route='G432' minutesBeforeSTD=20 passengerCount=12"
    }

    def "Should return statistics message received after departure"() {
        given:
            def data = generateData(12, -35)
        when:
            def stats = analysis.analyse(data)
        then:
            stats.carrier == "Home Office Seaways"
            stats.route == "G432"
            stats.passengerCount == 12
            stats.minutesBeforeSTD.get() == -35
            stats.toLogLine() == "carrier='Home Office Seaways' route='G432' minutesBeforeSTD=-35 passengerCount=12"
    }

    def "Should return statistics for no passengers"() {
        given:
            def data = generateData(0, -35)
        when:
            def stats = analysis.analyse(data)
        then:
            stats.passengerCount == 0
            stats.toLogLine() == "carrier='Home Office Seaways' route='G432' minutesBeforeSTD=-35 passengerCount=0"
    }

    def "Should return statistics if STD is missing"() {
        given:
            def data = generateData(10, 35)
            data.service.scheduledDepartureTime = null
        when:
            def stats = analysis.analyse(data)
        then:
            !stats.minutesBeforeSTD.isPresent()
            stats.toLogLine() == "carrier='Home Office Seaways' route='G432' passengerCount=10"
    }

    def generateData(int passengers, int minutesBefore) {
        def data = new NonInteractiveData()
        data.messageReceived = new Date()
        def service = new ServiceInformation()
        service.carrier = "Home Office Seaways"
        service.route = "G432"
        service.scheduledDepartureTime = departureTime(data.messageReceived, minutesBefore)
        data.service = service

        if (passengers) {
            data.passengers = (1..passengers).collect {
                i -> new Passenger()
            }.asList()
        }
        data
    }

    def departureTime(Date received, int minutesBefore) {
        def departureTime = received.time + minutesBefore * 60000

        return new Date(departureTime)
    }


}
