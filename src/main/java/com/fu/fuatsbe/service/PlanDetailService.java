package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.PlanDetailActionDTO;
import com.fu.fuatsbe.DTO.PlanDetailCreateDTO;
import com.fu.fuatsbe.DTO.PlanDetailUpdateDTO;
import com.fu.fuatsbe.response.PlanDetailResponseDTO;

public interface PlanDetailService {

    public List<PlanDetailResponseDTO> getAllPlanDetails();

    public List<PlanDetailResponseDTO> getAllByRecruitmentPlans(int recruitmentPlanId);

    public PlanDetailResponseDTO getPlanDetailById(int id);

    public PlanDetailResponseDTO updatePlanDetailById(int id, PlanDetailUpdateDTO updateDTO);

    public PlanDetailResponseDTO createPlanDetail(PlanDetailCreateDTO createDTO);

    public List<PlanDetailResponseDTO> getPendingPlanDetails();

    public List<PlanDetailResponseDTO> getApprovedPlanDetails();

    public List<PlanDetailResponseDTO> getCanceledPlanDetails();

    public PlanDetailResponseDTO approvedPlanDetails(PlanDetailActionDTO actionDTO);

    public PlanDetailResponseDTO canceledPlanDetails(PlanDetailActionDTO actionDTO);

}
