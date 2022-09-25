package com.fu.fuatsbe.dataformat;

import java.util.List;

import com.fu.fuatsbe.response.AccountResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListAccountData {

    private String message;
    private List<AccountResponse> data;
    private String status;
}
