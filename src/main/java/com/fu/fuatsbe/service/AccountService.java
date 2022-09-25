package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.AccountCreateDTO;
import com.fu.fuatsbe.DTO.AccountUpdateDTO;
import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.response.AccountResponse;

public interface AccountService {

    List<AccountResponse> getAllAccounts();

    AccountResponse getAccountByEmail(String email);

    AccountResponse getAccountById(int id);

    AccountResponse updateAccount(int id, AccountUpdateDTO updateDTO);

    Account createAccount(AccountCreateDTO createDTO);

    boolean deleteAccountById(int id);
}
