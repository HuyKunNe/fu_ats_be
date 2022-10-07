package com.fu.fuatsbe.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListResponseDTO<T> {

    private String message;
    private List<T> data;
    private String status;
}
