package com.covidtracker.covid_tracker.controller;

import com.covidtracker.covid_tracker.model.LocationStats;
import com.covidtracker.covid_tracker.service.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Deque;


@Controller
public class HomeController {
    @Autowired
    CovidDataService covidDataService;

    @GetMapping("/")
    public String home(Model model) {
        Deque<LocationStats> lastestCases = covidDataService.getAllStateConfirmedStats();
        model.addAttribute("locationStats", lastestCases);
        Integer totalReportedCases = lastestCases.stream().mapToInt(stat -> stat.getConfirmedCaseNum()).sum();
        model.addAttribute("totalReportedCases", totalReportedCases);
        return "home";
    }

}

















