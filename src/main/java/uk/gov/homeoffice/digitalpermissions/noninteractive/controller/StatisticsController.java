package uk.gov.homeoffice.digitalpermissions.noninteractive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.SummaryStats;
import uk.gov.homeoffice.digitalpermissions.noninteractive.repository.StatsRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class StatisticsController {

    private final StatsRepository repository;

    public StatisticsController(StatsRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(path = "/stats", method = GET)
    public String showStatistics(final Model model) {
        final List<SummaryStats> stats = repository.getSummaryStats(aWeekAgo(), new Date());

        model.addAttribute("carrierStats", stats);

        return "statistics";
    }

    private static Date aWeekAgo() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7);

        return cal.getTime();
    }
}
