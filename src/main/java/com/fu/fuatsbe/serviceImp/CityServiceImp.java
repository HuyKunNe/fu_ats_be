package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.constant.city.CityErrorMessage;
import com.fu.fuatsbe.entity.City;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.CityRepository;
import com.fu.fuatsbe.response.CityResponse;
import com.fu.fuatsbe.service.CityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityServiceImp implements CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CityResponse> getAllCities() {
        List<CityResponse> result = new ArrayList<CityResponse>();
        List<City> cities = cityRepository.findAll();

        if (!cities.isEmpty()) {
            for (City city : cities) {
                CityResponse response = modelMapper.map(city, CityResponse.class);
                result.add(response);
            }
        } else
            throw new ListEmptyException(CityErrorMessage.LIST_EMPTY);
        return result;
    }

    @Override
    public CityResponse getCityByName(String name) {
        CityResponse response = new CityResponse();
        City city = cityRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(CityErrorMessage.NOT_FOUND));
        response = modelMapper.map(city, CityResponse.class);
        return response;
    }

}
