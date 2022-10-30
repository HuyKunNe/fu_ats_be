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
public class CvUpdateDTO {

    private String linkCV;
    private String experience;
    private String location;
    private List<String> positionName;

}
