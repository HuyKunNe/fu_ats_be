package com.fu.fuatsbe.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplyCreateDTO {

    private int recruitmentRequestId;
    private int cvId;
    private int candidateId;
}
