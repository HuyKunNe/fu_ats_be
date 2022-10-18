package com.fu.fuatsbe.DTO;

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
    private String result;
    private String suitablePosition;
    private String note;

    private int positionId;

}
