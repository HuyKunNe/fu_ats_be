package com.fu.fuatsbe.response;

import java.sql.Date;
import java.util.Collection;

import com.fu.fuatsbe.entity.CV;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResponseDTO {

    private int id;
    private String name;
    private String phone;
    private String email;
    private String image;
    private String gender;
    private Date dob;
    private String address;
    private String status;
    private String positionApplied;
    private Collection<CV> cvs;
}
