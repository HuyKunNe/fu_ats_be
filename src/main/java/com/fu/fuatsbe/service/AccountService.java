package com.fu.fuatsbe.service;

import java.util.List;
import com.fu.fuatsbe.response.AccountResponse;

public interface AccountService {

    List<AccountResponse> getAllAccounts();

    List<AccountResponse> getActivateAccounts();

    List<AccountResponse> getDisableAccounts();

    List<AccountResponse> getAllAccountsByRole(int roleId);

    AccountResponse getAccountByEmail(String email);

    AccountResponse getAccountById(int id);

    boolean deleteAccountById(int id);
}
