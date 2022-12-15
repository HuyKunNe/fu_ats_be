package com.fu.fuatsbe.response;

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
public class ReportDetailDTO {
    private String source;
    private int totalCV;
    private int totalAcceptableCV;
    private int totalJoinInterview;
    private int totalPassInterview;
}
