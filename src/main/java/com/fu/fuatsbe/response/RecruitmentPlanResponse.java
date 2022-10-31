package com.fu.fuatsbe.response;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
