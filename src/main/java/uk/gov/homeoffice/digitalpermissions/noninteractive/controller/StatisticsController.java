package uk.gov.homeoffice.digitalpermissions.noninteractive.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.homeoffice.digitalpermissions.noninteractive.model.SummaryStats;
import uk.gov.homeoffice.digitalpermissions.noninteractive.repository.StatsRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class StatisticsController {
    private static final String DATE_FMT = "yyyy-MM-dd";

    private final StatsRepository repository;

    public StatisticsController(StatsRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(path = "/stats", method = GET)
    public String lastWeek(@RequestParam(name = "from", required = false) @DateTimeFormat(pattern=DATE_FMT) final Date from,
                           @RequestParam(name = "to", required = false) @DateTimeFormat(pattern=DATE_FMT) final Date to) {
        final SimpleDateFormat format = new SimpleDateFormat(DATE_FMT);
        return "redirect:/stats/" + format.format(from != null ? from : aWeekAgo())
                      + "/to/" + (to != null ? format.format(to) : "now");
    }

    @RequestMapping(path = "/stats/{from}/to/now", method = GET)
    public String statsToNow(@PathVariable("from") @DateTimeFormat(pattern=DATE_FMT) final Date from,
                             final Model model) {
        return statsRange(from, new Date(), model);
    }

    @RequestMapping(path = "/stats/{from}/to/{to}", method = GET)
    public String statsRange(@PathVariable("from") @DateTimeFormat(pattern=DATE_FMT) final Date from,
                             @PathVariable("to") @DateTimeFormat(pattern=DATE_FMT) final Date to,
                             final Model model) {
        final List<SummaryStats> stats = repository.getSummaryStats(from, to);

        model.addAttribute("carrierStats", stats);

        return "statistics";
    }

    private static Date aWeekAgo() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7);

        return cal.getTime();
    }
}
