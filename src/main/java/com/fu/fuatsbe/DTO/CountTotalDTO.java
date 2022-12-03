package com.fu.fuatsbe.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountTotalDTO {
    private int totalEmployee;
    private int totalDepartment;
    private int totalPosition;
}
