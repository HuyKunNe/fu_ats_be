package com.fu.fuatsbe.serviceImp;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.constant.account.AccountErrorMessage;
import com.fu.fuatsbe.constant.account.AccountStatus;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.role.RoleName;
import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Role;
import com.fu.fuatsbe.jwt.JwtConfig;
import com.fu.fuatsbe.repository.AccountRepository;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.RoleRepository;
import com.fu.fuatsbe.response.LoginResponseDto;
import com.fu.fuatsbe.response.RegisterResponseDto;
import com.fu.fuatsbe.service.AuthService;
import com.fu.fuatsbe.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    @Override
    public RegisterResponseDto register(RegisterCandidateDto candidate) throws RoleNotFoundException {
        return null;
    }

    @Override
    public RegisterResponseDto registerByAdmin(RegisterDto registerDto) throws RoleNotFoundException {
        Optional<Account> optionalUser = accountRepository.findAccountByEmail(registerDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException(EmployeeErrorMessage.EMAIL_EXIST);
        }
        Optional<Department> optionalDepartment = departmentRepository.findDepartmentByName(registerDto.getDepartmentName());
        if (!optionalDepartment.isPresent()) {
            throw new IllegalStateException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION);
        }
        Employee employee = Employee.builder().name(registerDto.getName()).EmployeeCode(registerDto.getEmployeeCode()).status(AccountStatus.ACTIVATED)
                .phone(registerDto.getPhone()).department(optionalDepartment.get()).address(registerDto.getAddress()).build();
        Role role = roleRepository.findByName(registerDto.getRole()).orElseThrow(() -> new IllegalStateException("this role does not exist"));
        Account account = Account.builder()
                .email(registerDto.getEmail())
                .role(role)
                .employee(employee)
                .status(AccountStatus.ACTIVATED)
                .password(passwordEncoder.encode(registerDto.getPassword())).build();
        employeeRepository.save(employee);
        Account credentialInRepo = accountRepository.save(account);
        employee.setAccount(credentialInRepo);
        employeeRepository.save(employee);
        RegisterResponseDto registerResponseDto = modelMapper.map(credentialInRepo, RegisterResponseDto.class);
        return registerResponseDto;
    }

    @Override
    public LoginResponseDto login(LoginDto employee) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(employee.getEmail(), employee.getPassword());
        LoginResponseDto loginResponseDTO = null;
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (authenticate.isAuthenticated()) {
            Optional<Account> accountAuthencatedOptional = accountRepository.findAccountByEmail(employee.getEmail());
            Account accountAuthencated = accountAuthencatedOptional.get();
            if (accountAuthencated.getEmployee() != null) {
                if (!accountAuthencated.getEmployee().getStatus().equals(AccountStatus.ACTIVATED)) {
                    throw new IllegalStateException(EmployeeErrorMessage.EMPLOYEE_UNAVAILABLE);
                }
            }
            String token = Utils.buildJWT(authenticate, accountAuthencated, secretKey, jwtConfig);
            loginResponseDTO = LoginResponseDto.builder()
                    .accountId(accountAuthencated.getId())
                    .email(accountAuthencated.getEmail())
                    .status(accountAuthencated.getStatus())
                    .roleName(accountAuthencated.getRole().getName())
                    .token(jwtConfig.getTokenPrefix() + token).build();
        }
        return loginResponseDTO;
    }
}
