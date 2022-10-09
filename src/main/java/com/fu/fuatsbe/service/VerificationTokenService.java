package com.fu.fuatsbe.service;

import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.VerificationToken;

public interface VerificationTokenService {
    public VerificationToken findByToken(String token);
    public VerificationToken findByAccount(Account account);
    public void save(Account account, String token, boolean isForgotPassword);
}
