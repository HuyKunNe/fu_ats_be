package com.fu.fuatsbe.controller;

import java.util.List;

import javax.annotation.security.PermitAll;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fu.fuatsbe.constant.city.CitySuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.response.CityResponse;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.service.CityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/getAll")
    @PermitAll
    public ResponseEntity<ListResponseDTO> getAllCity() {
        ListResponseDTO<CityResponse> responseDTO = new ListResponseDTO();
        List<CityResponse> list = cityService.getAllCities();
        responseDTO.setData(list);
        responseDTO.setMessage(CitySuccessMessage.GET_ALL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getByName/{name}")
    @PermitAll
    public ResponseEntity<ResponseDTO> getByName(@RequestParam("name") String name) {
        ResponseDTO responseDTO = new ResponseDTO();
        CityResponse city = cityService.getCityByName(name);
        responseDTO.setData(city);
        responseDTO.setMessage(CitySuccessMessage.GET_BY_NAME);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

}
