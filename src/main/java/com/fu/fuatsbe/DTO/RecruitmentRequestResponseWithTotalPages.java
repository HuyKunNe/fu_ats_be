package com.fu.fuatsbe.DTO;

import java.util.List;
import com.fu.fuatsbe.response.RecruitmentRequestResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecruitmentRequestResponseWithTotalPages {
   private int totalPages;
   private List<RecruitmentRequestResponse> responseList;

}
