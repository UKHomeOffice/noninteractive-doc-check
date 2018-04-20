package uk.gov.homeoffice.digitalpermissions.noninteractive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.MessageStats;
import uk.gov.homeoffice.digitalpermissions.noninteractive.repository.StatsRepository;

@Service
public class StatsLoggingService {
    private final StatsRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatsLoggingService.class);

    public StatsLoggingService(StatsRepository repository) {
        this.repository = repository;
    }

    public void logMessageStats(final MessageStats stats) {
        logStatsToConsole(stats);
        logStatsToDatabase(stats);
    }

    private void logStatsToConsole(final MessageStats stats) {
        LOGGER.info(stats.toLogLine());
    }

    private void logStatsToDatabase(final MessageStats stats) {
        repository.save(stats);
    }
}
