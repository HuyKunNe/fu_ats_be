package com.fu.fuatsbe.DTO;

import java.sql.Date;
import java.util.List;

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
public class RecruitmentRequestUpdateDTO {

    private Date expiryDate;
    private String industry;
    private int amount;
    private String jobLevel;
    private String experience;
    private String typeOfWork;
    private String salaryFrom;
    private String salaryTo;
    private List<String> cityName;
    private String foreignLanguage;
    private String educationLevel;
    private String address;
    private String description;
    private String benefit;
    private String requirement;
    private int positionId;
}
