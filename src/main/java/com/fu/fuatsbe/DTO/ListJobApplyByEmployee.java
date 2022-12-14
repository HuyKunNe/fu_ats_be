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
public class ListJobApplyByEmployee {
    private int requestId;
    private int employeeId;
    private List<JobApplyByEmployeeDTO> ListJobApplyByEmployee;
}
