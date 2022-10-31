package com.fu.fuatsbe.response;

import java.sql.Date;

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
public class RecruitmentPlanResponse {

    private int id;
    private Date periodFrom;
    private Date periodTo;
    private String totalSalary;
    private int amount;
    private String status;
    private EmployeeResponse approver;
    private EmployeeResponse creator;

}
