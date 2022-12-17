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
public class ReportGroupByDepartment {
    private String departmentName;
    private int totalDetailByDepartment;
    private int totalRowByDepartment;
    private List<ReportGroupByPlan> recruitmentPlans;
}
