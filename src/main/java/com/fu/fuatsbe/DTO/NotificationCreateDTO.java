package com.fu.fuatsbe.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class NotificationCreateDTO {
    private String token;
    private String title;
    private String content;
}
