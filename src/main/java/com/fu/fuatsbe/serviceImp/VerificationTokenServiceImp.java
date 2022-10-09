package com.fu.fuatsbe.serviceImp;

import com.fu.fuatsbe.constant.common.CommonMessage;
import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.VerificationToken;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.VerificationRepository;
import com.fu.fuatsbe.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImp implements VerificationTokenService {
    private final VerificationRepository verificationRepository;
    @Override
    public VerificationToken findByToken(String token) {
        return verificationRepository.findByToken(token).orElseThrow(()->new NotFoundException(CommonMessage.VERIFICATION_TOKEN_EXCEPTION));
    }

    @Override
    public VerificationToken findByAccount(Account account) {
        return verificationRepository.findByAccount(account).orElseThrow(()->new NotFoundException(CommonMessage.VERIFICATION_TOKEN_EXCEPTION));
    }

    @Override
    public void save(Account account, String token, boolean isForgotPassword) {
        //set expiry date to 24 hours

        VerificationToken verificationToken =isForgotPassword ? VerificationToken.builder().expiryDate(calculateExpiryTime(60)).token(token).account(account).build():
                VerificationToken.builder().expiryDate(calculateExpiryTime(24*60)).token(token).account(account).build();
        verificationRepository.save(verificationToken);
    }

    private Timestamp calculateExpiryTime(int expiryTimeInMinutes){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }
}
