package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.PositionCreateDTO;
import com.fu.fuatsbe.DTO.PositionUpdateDTO;
import com.fu.fuatsbe.response.IdAndNameResponse;
import com.fu.fuatsbe.response.PositionResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

public interface PositionService {
    public ResponseWithTotalPage<PositionResponse> getAllPositions(int pageNo, int pageSize, String search);

    public PositionResponse getPosionById(int id);

    public PositionResponse createPosition(PositionCreateDTO createDTO);

    public PositionResponse updatePosition(int id, PositionUpdateDTO updateDTO);

    public void deletePosition(int id);

    void activePosition(int id);

    public ResponseWithTotalPage<PositionResponse> getPositionByDepartment(int id, int pageNo, int pageSize);

    List<IdAndNameResponse> getPositionIdAndName(String departmentName);

    public List<String> getPositionNameByDepartment(String departmentName);

}
