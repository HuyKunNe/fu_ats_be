package com.fu.fuatsbe.response;

import java.sql.Date;

import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.entity.RecruitmentPlan;

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
public class PlanDetailResponseDTO {

    private int id;
    private int amount;
    private Date date;
    private String reason;
    private String salary;
    private String name;
    private Date periodFrom;
    private Date periodTo;
    private String description;
    private String requirement;
    private String note;
    private String status;
    private RecruitmentPlan recruitmentPlan;
    private Employee creator;
    private Employee approver;
    private Employee ceo;
    private Position position;

}
