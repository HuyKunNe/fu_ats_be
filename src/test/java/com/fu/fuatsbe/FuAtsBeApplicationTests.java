package com.fu.fuatsbe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fu.fuatsbe.constant.role.RoleName;
import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.Role;
import com.fu.fuatsbe.repository.AccountRepository;
import com.fu.fuatsbe.repository.RoleRepository;
import com.fu.fuatsbe.service.RoleService;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class FuAtsBeApplicationTests {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void addRole() throws Exception {
		Role admin = Role.builder().name("ADMIN").build();
		roleRepository.save(admin);
		Role employee = Role.builder().name("EMPLOYEE").build();
		roleRepository.save(employee);
		Role candidate = Role.builder().name("CANDIDATE").build();
		roleRepository.save(candidate);
	}

	@Test
	void addDefaultAdminAccount() throws Exception {
		Role role = roleRepository.findByName(RoleName.ROLE_ADMIN).get();
		boolean isExist = accountRepository.existsByRole(role);
		Account account = new Account();
		if (!isExist) {
			account = Account.builder().email("admin@gmail.com").role(role)
					.password(passwordEncoder.encode("123456789")).build();
			accountRepository.save(account);
		}
		assertEquals(account.getRole(), role);
	}

}
