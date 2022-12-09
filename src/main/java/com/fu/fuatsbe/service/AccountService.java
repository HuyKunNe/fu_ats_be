package com.fu.fuatsbe.service;

import java.util.List;
import com.fu.fuatsbe.response.AccountResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

public interface AccountService {

    List<AccountResponse> getAllAccounts(int pageNo, int pageSize);

    List<AccountResponse> getActivateAccounts(int pageNo, int pageSize);

    List<AccountResponse> getDisableAccounts(int pageNo, int pageSize);

    List<AccountResponse> getAllAccountsByRole(int roleId, int pageNo, int pageSize);

    AccountResponse getAccountByEmail(String email);

    AccountResponse getAccountById(int id);

    boolean deleteAccountById(int id);

    ResponseWithTotalPage<AccountResponse> getEmployeeAccount(int pageNo, int pageSize, String name);
}
