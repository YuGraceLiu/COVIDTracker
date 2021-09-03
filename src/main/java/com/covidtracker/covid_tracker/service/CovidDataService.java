package com.covidtracker.covid_tracker.service;

import com.covidtracker.covid_tracker.model.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayDeque;
import java.util.Deque;

@Service
public class CovidDataService {
    private static final String CONFIRMED_CASES_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/" +
            "master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private Deque<LocationStats> allStateConfirmedStats = new ArrayDeque<>();

    public Deque<LocationStats> getAllStateConfirmedStats() {
        return allStateConfirmedStats;
    }

    @PostConstruct //execute the below function once construction of the CovidDataService class is done
    @Scheduled(cron = "2 * * * * *")
    public void fetchConfirmedData() throws IOException, InterruptedException {
        Deque<LocationStats> newStateConfirmedStats = new ArrayDeque<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(CONFIRMED_CASES_URL)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        StringReader csvBodyReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            locationStat.setConfirmedCaseNum(Integer.parseInt(record.get(record.size() - 1)));
            System.out.println(locationStat);
            newStateConfirmedStats.push(locationStat);
        }
        this.allStateConfirmedStats = newStateConfirmedStats;

    }

}
