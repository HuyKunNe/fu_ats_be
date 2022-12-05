package com.fu.fuatsbe.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteReapplyDTO {
    private List<Integer> cvIds;
    private String title;
    private String content;
}
