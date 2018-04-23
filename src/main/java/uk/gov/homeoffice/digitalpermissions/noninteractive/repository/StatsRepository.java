package uk.gov.homeoffice.digitalpermissions.noninteractive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.MessageStats;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.SummaryStats;

import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<MessageStats, Long> {

    @Query("SELECT m.carrier AS carrier," +
            "  count(m) as messageCount," +
            "  avg(m.passengerCount) as passengerCount," +
            "  avg(m.minutesBeforeSTD) as minutesBeforeSTD " +
            "FROM MessageStats m GROUP BY m.carrier")
    List<SummaryStats> getSummaryStats();
}
