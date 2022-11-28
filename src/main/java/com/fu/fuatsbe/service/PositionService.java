package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.PositionCreateDTO;
import com.fu.fuatsbe.DTO.PositionUpdateDTO;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.response.IdAndNameResponse;
import com.fu.fuatsbe.response.PositionResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

import java.util.List;

public interface PositionService {
    public ResponseWithTotalPage<PositionResponse> getAllPositions(int pageNo, int pageSize);

    public PositionResponse getPosionById(int id);

    public PositionResponse createPosition(PositionCreateDTO createDTO);

    public PositionResponse updatePosition(int id, PositionUpdateDTO updateDTO);

    public Position deletePosition(int id);

    public ResponseWithTotalPage<PositionResponse> getPositionByDepartment(int id, int pageNo, int pageSize);
    List<IdAndNameResponse> getPositionIdAndName();

}
