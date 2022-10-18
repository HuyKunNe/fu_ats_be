package com.fu.fuatsbe.response;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CvResponse {

    private int id;
    private String linkCV;
    private String result;
    private String suitablePosition;
    private String note;

    private Position position;

    private Candidate candidate;

}
