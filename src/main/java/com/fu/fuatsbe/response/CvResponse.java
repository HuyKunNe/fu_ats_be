package com.fu.fuatsbe.response;

import com.fu.fuatsbe.entity.Candidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class CvResponse {

    private int id;
    private String linkCV;
    private String note;
    private String title;
    private String recommendPositions;

    private String positionApplied;

    private Candidate candidate;

}
