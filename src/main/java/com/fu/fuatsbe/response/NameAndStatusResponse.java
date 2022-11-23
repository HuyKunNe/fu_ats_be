package com.fu.fuatsbe.response;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NameAndStatusResponse {
    private String name;
    private String status;
}
