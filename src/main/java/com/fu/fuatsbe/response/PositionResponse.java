package com.fu.fuatsbe.response;

import com.fu.fuatsbe.entity.Department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionResponse {
    private int id;
    private String name;
    private Department department;
}
