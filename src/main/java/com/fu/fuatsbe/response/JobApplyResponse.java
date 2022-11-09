package com.fu.fuatsbe.response;

import java.sql.Date;
import java.util.List;

import com.fu.fuatsbe.entity.CV;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.City;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.RecruitmentRequest;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplyResponse {
    private int id;

    private Date date;
    private String foreignLanguage;
    private String educationLevel;
    private String status;

    private RecruitmentRequest recruitmentRequest;
    private Candidate candidate;
    private CV cv;
    private Employee applier;
    private List<City> cities;
}
