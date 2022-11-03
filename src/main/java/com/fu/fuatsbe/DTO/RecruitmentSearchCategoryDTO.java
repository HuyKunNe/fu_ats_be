package com.fu.fuatsbe.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecruitmentSearchCategoryDTO {
    private List<String> jobTitle = new ArrayList<>();
    private List<String> industry = new ArrayList<>();
    private List<String> province;
}
