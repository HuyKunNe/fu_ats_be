package com.fu.fuatsbe.response;

import java.util.List;

import com.fu.fuatsbe.entity.Department;

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
    private Department department;
    private List<ReportGroupByPlan> recruitmentPlans;
}
