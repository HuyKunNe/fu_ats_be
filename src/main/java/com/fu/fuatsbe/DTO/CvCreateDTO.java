package com.fu.fuatsbe.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CvCreateDTO {

    private String linkCV;
    private String experience;
    private String location;

    private int candidateId;

    private List<String> positionName;
}
