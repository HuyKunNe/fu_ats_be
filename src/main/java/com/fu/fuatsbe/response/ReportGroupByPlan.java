package com.fu.fuatsbe.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportGroupByPlan {
    private String recruitmentPlanName;
    private int totalDetailByPlan;
    private int totalRowByPlan;
    private List<ReportGroupByPlanDetail> planDetails;
}