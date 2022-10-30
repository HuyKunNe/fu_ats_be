package com.fu.fuatsbe.DTO;

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
    private String skill;
    private int positionId;
    private int recruitmentPlanId;

}
