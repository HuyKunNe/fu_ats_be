package com.fu.fuatsbe.response;

import java.sql.Date;

import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.PlanDetail;
import com.fu.fuatsbe.entity.Position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentRequestResponse {

    private int id;
    private Date date;
    private Date expiryDate;
    private String industry;
    private int amount;
    private String jobLevel;
    private String experience;
    private String salary;
    private String typeOfWork;
    private String location;
    private String description;
    private String benefit;
    private String requirement;
    private String status;
    private Employee creator;
    private PlanDetail planDetail;
    private Position position;

}
