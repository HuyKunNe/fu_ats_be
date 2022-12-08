package com.fu.fuatsbe.DTO;

import java.util.List;

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
public class CvCreateDTO {

    private String title;

    private String linkCV;

    private int candidateId;

    private List<String> positionName;
}
