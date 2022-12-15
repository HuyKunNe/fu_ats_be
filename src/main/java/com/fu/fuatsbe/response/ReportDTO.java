package com.fu.fuatsbe.response;

public interface ReportDTO {

    Integer getJobRequestId();

    Integer getPlanId();

    Integer getPlanDetailId();

    Integer getDepartmentId();

    String getSource();

    Integer getTotalCV();

    Integer getTotalAcceptableCV();

    Integer getTotalJoinInterview();

    Integer getTotalPassInterview();
}
