package com.fu.fuatsbe.response;

public interface ReportDTO {
    String getPlanName();

    String getDepartmentName();

    String getJobRequestName();

    String getSource();

    String getDateRecruited();

    String getExpiryDate();

    Integer getTotalCV();

    Integer getTotalAcceptableCV();

    Integer getTotalJoinInterview();

    Integer getTotalPassInterview();
}
