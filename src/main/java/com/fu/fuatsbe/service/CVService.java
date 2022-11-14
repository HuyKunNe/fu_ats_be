package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.CvCreateDTO;
import com.fu.fuatsbe.DTO.CvUpdateDTO;
import com.fu.fuatsbe.entity.CV;
import com.fu.fuatsbe.response.CvResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

public interface CVService {

    public ResponseWithTotalPage<CvResponse> getAllCvs(int pageNo, int pageSize);

    public ResponseWithTotalPage<CvResponse> getAllCvByCandidate(int candidateId, int pageNo, int pageSize);

    public ResponseWithTotalPage<CvResponse> getAllSuitableCvs(int pageNo, int pageSize);

    public CvResponse createCV(CvCreateDTO createDTO);

    public CvResponse updateCV(int id, CvUpdateDTO updateDTO);

    public CV deleteCV(int id);

    public List<CvResponse> getRejectedCv();
}
