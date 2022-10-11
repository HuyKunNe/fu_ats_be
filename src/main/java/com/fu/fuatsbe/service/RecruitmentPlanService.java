package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.RecruimentPlanUpdateDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanActionDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanCreateDTO;
import com.fu.fuatsbe.response.RecruitmentPlanResponse;

public interface RecruitmentPlanService {

    public List<RecruitmentPlanResponse> getAllRecruitmentPlans(int pageNo, int pageSize);

    public RecruitmentPlanResponse getRecruitmentPlanById(int id);

    public List<RecruitmentPlanResponse> getAllApprovedRecruitmentPlan(int pageNo, int pageSize);

    public List<RecruitmentPlanResponse> getAllCanceledRecruitmentPlans(int pageNo, int pageSize);

    public List<RecruitmentPlanResponse> getAllRejectedRecruitmentPlans(int pageNo, int pageSize);

    public List<RecruitmentPlanResponse> getAllPedingRecruitmentPlans(int pageNo, int pageSize);

    public List<RecruitmentPlanResponse> getAllRecruitmentPlansByApprover(int approverId, int pageNo, int pageSize);

    public List<RecruitmentPlanResponse> getAllRecruitmentPlansByCreator(int creatorId, int pageNo, int pageSize);

    public RecruitmentPlanResponse updateRecruitmentPlan(int id, RecruimentPlanUpdateDTO updateDTO);

    public RecruitmentPlanResponse createRecruitmentPlan(RecruitmentPlanCreateDTO createDTO);

    public RecruitmentPlanResponse approvedRecruitmentPlan(RecruitmentPlanActionDTO actionDTO);

    public RecruitmentPlanResponse canceledRecruitmentPlan(RecruitmentPlanActionDTO actionDTO);

    public RecruitmentPlanResponse rejectedRecruitmentPlan(RecruitmentPlanActionDTO actionDTO);

}
