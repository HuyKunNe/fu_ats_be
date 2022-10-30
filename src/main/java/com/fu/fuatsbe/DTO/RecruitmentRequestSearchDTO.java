package com.fu.fuatsbe.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentRequestSearchDTO {
    private String jobName;
    private String province;
    private String industry;
    private String jobLevel;
    private String typeOfWork;
    private String salaryFrom;
    private String salaryTo;
    private String experience;

}
