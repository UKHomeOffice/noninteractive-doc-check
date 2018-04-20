package uk.gov.homeoffice.digitalpermissions.noninteractive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.MessageStats;

@Repository
public interface StatsRepository extends JpaRepository<MessageStats, Long> {
}
