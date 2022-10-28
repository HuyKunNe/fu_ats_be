package com.fu.fuatsbe.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class ResponseWithTotalPage {
    private int totalPage;
    private List<Object> responseList;
}
