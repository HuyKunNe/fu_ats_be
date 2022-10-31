package com.fu.fuatsbe.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDetailCreateDTO {

    private int amount;
    private String salary;
    private String reason;
    private Date periodFrom;
    private Date periodTo;
    private String note;
    private String description;
    private String requirement;
    private int positionId;
    private int recruitmentPlanId;
    private int creatorId;

}
