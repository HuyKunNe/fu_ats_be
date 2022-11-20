package com.fu.fuatsbe.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountStatusResponse {
    private List<String> status;
    private List<Integer> total;
}
