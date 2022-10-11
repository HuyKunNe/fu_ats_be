package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.PlanDetailActionDTO;
import com.fu.fuatsbe.DTO.PlanDetailCreateDTO;
import com.fu.fuatsbe.DTO.PlanDetailUpdateDTO;
import com.fu.fuatsbe.response.PlanDetailResponseDTO;

public interface PlanDetailService {

    public List<PlanDetailResponseDTO> getAllPlanDetails(int pageNo, int pageSize);

    public List<PlanDetailResponseDTO> getAllByRecruitmentPlans(int recruitmentPlanId, int pageNo, int pageSize);

    public PlanDetailResponseDTO getPlanDetailById(int id);

    public PlanDetailResponseDTO updatePlanDetailById(int id, PlanDetailUpdateDTO updateDTO);

    public PlanDetailResponseDTO createPlanDetail(PlanDetailCreateDTO createDTO);

    public List<PlanDetailResponseDTO> getPendingPlanDetails(int pageNo, int pageSize);

    public List<PlanDetailResponseDTO> getApprovedPlanDetails(int pageNo, int pageSize);

    public List<PlanDetailResponseDTO> getCanceledPlanDetails(int pageNo, int pageSize);

    public PlanDetailResponseDTO approvedPlanDetails(PlanDetailActionDTO actionDTO);

    public PlanDetailResponseDTO canceledPlanDetails(PlanDetailActionDTO actionDTO);

    public List<PlanDetailResponseDTO> getPlanDetailByApprover(int approverId, int pageNo, int pageSize);

}
