package com.fu.fuatsbe.response;

import com.fu.fuatsbe.entity.Department;

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
public class PositionResponse {
    private int id;
    private String name;
    private Department department;
    private String status;
    private int numberUsePosition;
}
