package com.fu.fuatsbe.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class NotificationCreateDTO {
    private List<Integer> candidateIDs;
    private List<Integer> employeeIDs;
    private String title;
    private String content;
}
