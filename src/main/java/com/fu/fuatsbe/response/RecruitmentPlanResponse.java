package com.fu.fuatsbe.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentPlanResponse {

    private int id;
    private String preriod;
    private int amount;
    private String status;
    private EmployeeResponse approver;
    private EmployeeResponse creator;

}
