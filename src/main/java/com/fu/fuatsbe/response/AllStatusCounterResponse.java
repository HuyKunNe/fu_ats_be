package com.fu.fuatsbe.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllStatusCounterResponse {
    private CountStatusResponse countStatusPlan;
    private CountStatusResponse countStatusDetail;
    private CountStatusResponse countStatusRequest;
}
