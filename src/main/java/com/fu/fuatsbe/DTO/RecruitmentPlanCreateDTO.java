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
public class RecruitmentPlanCreateDTO {

    private Date periodFrom;
    private Date periodTo;
    private int amount;
    private int creatorId;

}
