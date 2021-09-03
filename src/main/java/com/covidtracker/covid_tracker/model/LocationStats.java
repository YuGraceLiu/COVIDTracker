package com.covidtracker.covid_tracker.model;

public class LocationStats {
    private String State;
    private String Country;
    private Integer ConfirmedCaseNum;

    @Override
    public String toString() {
        return "LocationStats{" +
                "State='" + State + '\'' +
                ", Country='" + Country + '\'' +
                ", ConfirmedCaseNum=" + ConfirmedCaseNum +
                '}';
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public Integer getConfirmedCaseNum() {
        return ConfirmedCaseNum;
    }

    public void setConfirmedCaseNum(Integer confirmedCaseNum) {
        ConfirmedCaseNum = confirmedCaseNum;
    }

}
