package me.warmte.eventsourcing.controller;

import me.warmte.eventsourcing.service_reporting.ReportingService;
import me.warmte.eventsourcing.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportingController {
    @Autowired
    private ReportingService reportingService;

    @GetMapping("/reporting")
    public String reportingPage() {
        return "ReportingPage";
    }

    @GetMapping("/reporting/update")
    public String updatePage(
    ) {
        reportingService.update();
        return "ReportingPage";
    }

    @GetMapping("/reporting/date")
    public String datePage(
            @RequestParam(name = "d") String date,
            Model model
    ) {
        Integer result = reportingService.getDayStatistic(date);
        if (result == null) {
            return "ReportingPage";
        }
        model.addAttribute("param", date);
        model.addAttribute("result", String.valueOf(result));
        return "StatPage";
    }

    @GetMapping("/reporting/average")
    public String averagePage(
            @RequestParam(name = "id") long subscriptionId,
            Model model
    ) {
        Pair<Long, Long> result = reportingService.getAverageStatistic(subscriptionId);
        if (result == null) {
            return "ReportingPage";
        }
        model.addAttribute("param", String.valueOf(subscriptionId));
        model.addAttribute("result", String.valueOf((double) result.first / (double) result.second));
        return "StatPage";
    }
}
