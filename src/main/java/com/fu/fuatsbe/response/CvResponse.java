package com.fu.fuatsbe.response;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Position;

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
    private String result;
    private String note;
    private String experience;
    private String Location;
    private String suitablePosition;

    private Position position;

    private Candidate candidate;

}
