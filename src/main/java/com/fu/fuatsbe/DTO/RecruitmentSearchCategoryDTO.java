package com.fu.fuatsbe.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RecruitmentSearchCategoryDTO {
    private List<String> jobTitle = new ArrayList<>();
    private List<String> industry = new ArrayList<>();
}
