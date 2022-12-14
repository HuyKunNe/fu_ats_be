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
public class RecruitmentRequestCreateDTO {

    private Date expiryDate;
    private String industry;
    private String name;
    private int amount;
    private String jobLevel;
    private String experience;
    private String typeOfWork;
    private String salaryFrom;
    private String salaryTo;
    private String foreignLanguage;
    private String educationLevel;
    private String address;
    private String description;
    private String benefit;
    private String requirement;
    private String cityName;
    private int employeeId;
    private int planDetailId;
    private String positionName;

}
