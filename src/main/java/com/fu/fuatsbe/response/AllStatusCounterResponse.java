package com.fu.fuatsbe.response;

import com.fu.fuatsbe.response.CountStatusResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllStatusCounterResponse {
    private String className;
    private List<CountStatusResponse> countStatusResponses;
}
