package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.CVScreeningDTO;
import com.fu.fuatsbe.DTO.JobApplyCreateDTO;
import com.fu.fuatsbe.entity.CVScreening;
import com.fu.fuatsbe.response.JobApplyResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

public interface JobApplyService {
        public ResponseWithTotalPage<JobApplyResponse> getAllJobApplies(int pageNo, int pageSize);

        public ResponseWithTotalPage<JobApplyResponse> getJobApplyByCandidate(int candidateId, int pageNo,
                        int pageSize);

        public ResponseWithTotalPage<JobApplyResponse> getAllJobAppliesByRecruitmentRequest(int requestId, int pageNo,
                        int pageSize);

        public ResponseWithTotalPage<JobApplyResponse> getAllPendingJobApplies(int pageNo, int pageSize);

        public ResponseWithTotalPage<JobApplyResponse> getAllApprovedJobApplies(int pageNo, int pageSize);

        public ResponseWithTotalPage<JobApplyResponse> getAllCancelJobApplies(int pageNo, int pageSize);

        public ResponseWithTotalPage<JobApplyResponse> getJobApplyByDepartment(int departmentId, int pageNo,
                        int pageSize);

        public JobApplyResponse createJobApply(JobApplyCreateDTO createDTO);

        public JobApplyResponse cancelJobApply(int id, int employeeId);

        public JobApplyResponse approvedJobApply(int id, int employeeId);

        public JobApplyResponse getJobApplyById(int id);

        public CVScreening cvScreeningSetting(CVScreeningDTO screeningDTO);

        public CVScreening getCVScreening();

        public ResponseWithTotalPage<JobApplyResponse> getJobApplyNotReject(int recruitmentRequest, int pageNo,
                        int pageSize);

        public ResponseWithTotalPage<JobApplyResponse> getJobApplyPassScreenig(int pageNo, int pageSize);

        public ResponseWithTotalPage<JobApplyResponse> getAllFailedJobApplies(int pageNo, int pageSize);

}
