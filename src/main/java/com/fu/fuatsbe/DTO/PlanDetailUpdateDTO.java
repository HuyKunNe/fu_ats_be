package com.fu.fuatsbe.DTO;

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
public class PlanDetailUpdateDTO {

    private int amount;
    private String salary;
    private String name;
    private String reason;
    private Date periodFrom;
    private Date periodTo;
    private String note;
    private int positionId;

}
