package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.JobApplyCreateDTO;
import com.fu.fuatsbe.response.JobApplyResponse;

public interface JobApplyService {
    public List<JobApplyResponse> getAllJobApplies(int pageNo, int pageSize);

    public List<JobApplyResponse> getJobApplyByCandidate(int candidateId, int pageNo, int pageSize);

    public List<JobApplyResponse> getAllJobAppliesByRecruitmentRequest(int requestId, int pageNo, int pageSize);

    public List<JobApplyResponse> getAllPendingJobApplies(int pageNo, int pageSize);

    public List<JobApplyResponse> getAllApprovedJobApplies(int pageNo, int pageSize);

    public List<JobApplyResponse> getAllCancelJobApplies(int pageNo, int pageSize);

    public JobApplyResponse createJobApply(JobApplyCreateDTO createDTO);

    public JobApplyResponse cancelJobApply(int id, int employeeId);

    public JobApplyResponse approvedJobApply(int id, int employeeId);

    public JobApplyResponse getJobApplyById(int id);

}
