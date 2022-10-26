package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.CvCreateDTO;
import com.fu.fuatsbe.DTO.CvUpdateDTO;
import com.fu.fuatsbe.entity.CV;
import com.fu.fuatsbe.response.CvResponse;

public interface CVService {

    public List<CvResponse> getAllCvs(int pageNo, int pageSize);

    public List<CvResponse> getAllCvByCandidate(int candidateId, int pageNo, int pageSize);

    public List<CvResponse> getAllSuitableCvs(int pageNo, int pageSize);

    public CvResponse createCV(CvCreateDTO createDTO);

    public CvResponse updateCV(int id, CvUpdateDTO updateDTO);

    public CV deleteCV(int id);

}
