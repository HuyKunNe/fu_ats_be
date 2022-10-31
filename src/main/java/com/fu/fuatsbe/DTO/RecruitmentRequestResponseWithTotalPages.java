package com.fu.fuatsbe.DTO;

import java.util.List;
import com.fu.fuatsbe.response.RecruitmentRequestResponse;
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
public class RecruitmentRequestResponseWithTotalPages {
   private int totalPages;
   private List<RecruitmentRequestResponse> responseList;

}
