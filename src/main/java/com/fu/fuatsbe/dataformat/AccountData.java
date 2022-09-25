package com.fu.fuatsbe.dataformat;

import com.fu.fuatsbe.response.AccountResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountData {
    private String message;
    private AccountResponse data;
    private String status;
}
