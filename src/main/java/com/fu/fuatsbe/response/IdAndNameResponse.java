package com.fu.fuatsbe.response;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdAndNameResponse {
    private int id;
    private String name;
}
