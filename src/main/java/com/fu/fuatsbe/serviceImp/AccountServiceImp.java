package com.fu.fuatsbe.serviceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.AccountCreateDTO;
import com.fu.fuatsbe.DTO.AccountUpdateDTO;
import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.response.AccountResponse;
import com.fu.fuatsbe.service.AccountService;

@Service
public class AccountServiceImp implements AccountService {

    @Override
    public AccountResponse getAccountByEmail(String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AccountResponse getAccountById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AccountResponse updateAccount(int id, AccountUpdateDTO updateDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account createAccount(AccountCreateDTO createDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteAccountById(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        // TODO Auto-generated method stub
        return null;
    }

}
