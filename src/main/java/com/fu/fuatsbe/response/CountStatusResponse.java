package com.fu.fuatsbe.response;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountStatusResponse {
    private String status;
    private int total;
}
