package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.RecruimentPlanUpdateDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanActionDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanCreateDTO;
import com.fu.fuatsbe.response.CountStatusResponse;
import com.fu.fuatsbe.response.IdAndNameResponse;
import com.fu.fuatsbe.response.RecruitmentPlanResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

import java.util.List;

public interface RecruitmentPlanService {

    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllRecruitmentPlans(int pageNo, int pageSize);

    public RecruitmentPlanResponse getRecruitmentPlanById(int id);

    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllApprovedRecruitmentPlan(int pageNo, int pageSize);

    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllCanceledRecruitmentPlans(int pageNo, int pageSize);

    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllRejectedRecruitmentPlans(int pageNo, int pageSize);

    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllPedingRecruitmentPlans(int pageNo, int pageSize);

    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllRecruitmentPlansByApprover(int approverId, int pageNo,
            int pageSize);

    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllRecruitmentPlansByCreator(int creatorId, int pageNo,
            int pageSize);
     ResponseWithTotalPage<RecruitmentPlanResponse> getAllRecruitmentPlansByDepartment(int departmentId, int pageNo,
                                                                                          int pageSize);
    List<IdAndNameResponse> getApprovedByDepartment(int id);

    public RecruitmentPlanResponse updateRecruitmentPlan(int id, RecruimentPlanUpdateDTO updateDTO);

    public RecruitmentPlanResponse createRecruitmentPlan(RecruitmentPlanCreateDTO createDTO);

    public RecruitmentPlanResponse approvedRecruitmentPlan(RecruitmentPlanActionDTO actionDTO);

    public RecruitmentPlanResponse canceledRecruitmentPlan(RecruitmentPlanActionDTO actionDTO);

    public RecruitmentPlanResponse rejectedRecruitmentPlan(RecruitmentPlanActionDTO actionDTO);

    CountStatusResponse getStatusTotal();


}
