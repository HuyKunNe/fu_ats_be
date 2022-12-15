package com.fu.fuatsbe.response;

import java.util.List;

import com.fu.fuatsbe.entity.PlanDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportGroupByPlanDetail {
    private PlanDetail planDetail;
    private List<ReportGroupByJobRequest> jobRequests;
}
