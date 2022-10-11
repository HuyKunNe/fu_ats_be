package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.PositionCreateDTO;
import com.fu.fuatsbe.DTO.PositionUpdateDTO;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.response.PositionResponse;

public interface PositionService {
    public List<PositionResponse> getAllPositions(int pageNo, int pageSize);

    public PositionResponse getPosionById(int id);

    public PositionResponse createPosition(PositionCreateDTO createDTO);

    public PositionResponse updatePosition(int id, PositionUpdateDTO updateDTO);

    public Position deletePosition(int id);

    public List<PositionResponse> getPositionByDepartment(int id, int pageNo, int pageSize);

}
