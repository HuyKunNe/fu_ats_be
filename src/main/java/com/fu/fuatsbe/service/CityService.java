package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.response.CityResponse;

public interface CityService {
    public List<CityResponse> getAllCities();

    public CityResponse getCityByName(String name);
}
