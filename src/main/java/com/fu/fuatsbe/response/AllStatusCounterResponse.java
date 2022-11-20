package com.fu.fuatsbe.response;

import java.util.List;

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
    private List<CountStatusResponse> countStatusPlan;
    private List<CountStatusResponse> countStatusDetail;
    private List<CountStatusResponse> countStatusRequest;
}
