package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.PlanDetailActionDTO;
import com.fu.fuatsbe.DTO.PlanDetailCreateDTO;
import com.fu.fuatsbe.DTO.PlanDetailUpdateDTO;
import com.fu.fuatsbe.response.PlanDetailResponseDTO;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

public interface PlanDetailService {

    public ResponseWithTotalPage<PlanDetailResponseDTO> getAllPlanDetails(int pageNo, int pageSize);

    public ResponseWithTotalPage<PlanDetailResponseDTO> getAllByRecruitmentPlans(int recruitmentPlanId, int pageNo,
            int pageSize);

    public PlanDetailResponseDTO getPlanDetailById(int id);

    public PlanDetailResponseDTO updatePlanDetailById(int id, PlanDetailUpdateDTO updateDTO);

    public PlanDetailResponseDTO createPlanDetail(PlanDetailCreateDTO createDTO);

    public ResponseWithTotalPage<PlanDetailResponseDTO> getPendingPlanDetails(int pageNo, int pageSize);

    public ResponseWithTotalPage<PlanDetailResponseDTO> getApprovedPlanDetails(int pageNo, int pageSize);

    public ResponseWithTotalPage<PlanDetailResponseDTO> getCanceledPlanDetails(int pageNo, int pageSize);

    public PlanDetailResponseDTO approvedPlanDetails(PlanDetailActionDTO actionDTO);

    public PlanDetailResponseDTO canceledPlanDetails(PlanDetailActionDTO actionDTO);

    public ResponseWithTotalPage<PlanDetailResponseDTO> getPlanDetailByApprover(int approverId, int pageNo,
            int pageSize);

}
