package com.fu.fuatsbe.utils;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fu.fuatsbe.constant.account.AdminAccountDefault;
import com.fu.fuatsbe.constant.role.RoleName;
import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.Role;
import com.fu.fuatsbe.repository.AccountRepository;
import com.fu.fuatsbe.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class StartUpFunction {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void postConstruct() {
        Role role = roleRepository.findByName(RoleName.ROLE_ADMIN).get();
        if (role == null) {
            role.setName(RoleName.ROLE_ADMIN);
            roleRepository.save(role);
        }
        boolean isExist = accountRepository.existsByRole(role);
        if (!isExist) {
            Account account = Account.builder().email(AdminAccountDefault.ADMIN_EMAIL).role(role)
                    .password(passwordEncoder.encode(AdminAccountDefault.ADMIN_PASSWORD)).build();
            accountRepository.save(account);
        }
    }

}
