package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.RecruimentPlanUpdateDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanActionDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanCreateDTO;
import com.fu.fuatsbe.entity.RecruitmentPlan;
import com.fu.fuatsbe.response.RecruitmentPlanResponse;

public interface RecruitmentPlanService {

    public List<RecruitmentPlanResponse> getAllRecruitmentPlans();

    public RecruitmentPlanResponse getRecruitmentPlanById(int id);

    public List<RecruitmentPlanResponse> getAllApprovedRecruitmentPlan();

    public List<RecruitmentPlanResponse> getAllCanceledRecruitmentPlans();

    public List<RecruitmentPlanResponse> getAllRejectedRecruitmentPlans();

    public List<RecruitmentPlanResponse> getAllPedingRecruitmentPlans();

    public List<RecruitmentPlanResponse> getAllRecruitmentPlansByApprover(int approverId);

    public List<RecruitmentPlanResponse> getAllRecruitmentPlansByCreator(int creatorId);

    public RecruitmentPlanResponse updateRecruitmentPlan(int id, RecruimentPlanUpdateDTO updateDTO);

    public RecruitmentPlanResponse createRecruitmentPlan(RecruitmentPlanCreateDTO createDTO);

    public RecruitmentPlanResponse approvedRecruitmentPlan(RecruitmentPlanActionDTO actionDTO);

    public RecruitmentPlanResponse canceledRecruitmentPlan(RecruitmentPlanActionDTO actionDTO);

    public RecruitmentPlanResponse rejectedRecruitmentPlan(RecruitmentPlanActionDTO actionDTO);

}
