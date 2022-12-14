package com.fu.fuatsbe.response;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NameAndCodeResponse {
    private String name;
    private String code;
}
