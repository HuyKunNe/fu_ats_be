package com.fu.fuatsbe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fu.fuatsbe.constant.account.AccountStatus;
import com.fu.fuatsbe.constant.role.RoleName;
import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.Role;
import com.fu.fuatsbe.repository.AccountRepository;
import com.fu.fuatsbe.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class FuAtsBeApplicationTests {

    // private final AccountRepository accountRepository;

    // private final RoleRepository roleRepository;

    // private final PasswordEncoder passwordEncoder;

    // @Test
    // void addAccount() {

    // Role role = Role.builder().name(RoleName.ROLE_ADMIN).build();

    // Account account =
    // Account.builder().email("admin@gmail.com").password(passwordEncoder.encode("123456789"))
    // .status(AccountStatus.ACTIVATED)
    // .role(role).build();
    // accountRepository.save(account);

    // }
}
