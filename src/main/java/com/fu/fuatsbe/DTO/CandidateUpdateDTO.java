package com.fu.fuatsbe.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateUpdateDTO {

    private String name;
    private String phone;
    private String image;
    private String gender;
    private Date dob;
    private String address;

}
