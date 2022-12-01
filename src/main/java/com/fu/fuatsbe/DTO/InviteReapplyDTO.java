package com.fu.fuatsbe.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteReapplyDTO {
    private String email;
    private String candidateName;
    private String jobName;
}
