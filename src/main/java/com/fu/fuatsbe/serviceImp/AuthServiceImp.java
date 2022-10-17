package com.fu.fuatsbe.serviceImp;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.constant.account.AccountErrorMessage;
import com.fu.fuatsbe.constant.account.AccountStatus;
import com.fu.fuatsbe.constant.candidate.CandidateStatus;
import com.fu.fuatsbe.constant.common.CommonMessage;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeStatus;
import com.fu.fuatsbe.constant.postion.PositionErrorMessage;
import com.fu.fuatsbe.constant.role.RoleErrorMessage;
import com.fu.fuatsbe.constant.role.RoleName;
import com.fu.fuatsbe.constant.validation_message.ValidationMessage;
import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.entity.Role;
import com.fu.fuatsbe.exceptions.*;
import com.fu.fuatsbe.jwt.JwtConfig;
import com.fu.fuatsbe.repository.AccountRepository;
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.PositionRepository;
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
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository PositionRepository;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final CandidateRepository candidateRepository;

    @Override
    public RegisterResponseDto register(RegisterCandidateDto registerDTO) throws RoleNotFoundException {
        Optional<Account> optionalUser = accountRepository.findAccountByEmail(registerDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailExistException(EmployeeErrorMessage.EMAIL_EXIST);
        }
        Role role = roleRepository.findByName(RoleName.ROLE_CANDIDATE)
                .orElseThrow(() -> new NotFoundException(RoleErrorMessage.ROLE_NOT_EXIST));

        Optional<Candidate> optionalCandidate = candidateRepository.findByPhone(registerDTO.getPhone());
        if (optionalCandidate.isPresent()) {
            throw new ExistException(ValidationMessage.PHONE_IS_EXIST);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dob = simpleDateFormat.format(registerDTO.getDob());

        Candidate candidate = Candidate.builder().name(registerDTO.getName()).email(registerDTO.getEmail())
                .phone(registerDTO.getPhone()).image(registerDTO.getImage()).dob(Date.valueOf(dob))
                .gender(registerDTO.getGender()).address(registerDTO.getAddress())
                .status(CandidateStatus.ACTIVATED)
                .build();
        ;

        Account account = Account.builder()
                .email(candidate.getEmail())
                .role(role)
                .candidate(candidate)
                .status(AccountStatus.ACTIVATED)
                .password(passwordEncoder.encode(registerDTO.getPassword())).build();
        candidateRepository.save(candidate);
        Account credentialInRepo = accountRepository.save(account);
        candidate.setAccount(credentialInRepo);
        candidateRepository.save(candidate);
        RegisterResponseDto registerResponseDto = modelMapper.map(credentialInRepo, RegisterResponseDto.class);
        return registerResponseDto;
    }

    @Override
    public RegisterResponseDto registerByAdmin(RegisterDto registerDto) throws RoleNotFoundException {
        Optional<Account> optionalUser = accountRepository.findAccountByEmail(registerDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailExistException(EmployeeErrorMessage.EMAIL_EXIST);
        }
        Optional<Department> optionalDepartment = departmentRepository
                .findDepartmentByName(registerDto.getDepartmentName());
        if (!optionalDepartment.isPresent()) {
            throw new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION);
        }

        Optional<Position> optionalPosition = PositionRepository.findPositionByName(registerDto.getPositionName());
        if (!optionalPosition.isPresent()) {
            throw new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST);
        }

        Optional<Employee> optionalEmployee = employeeRepository.findByPhone(registerDto.getPhone());
        if (optionalEmployee.isPresent()) {
            throw new ExistException(ValidationMessage.PHONE_IS_EXIST);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dob = simpleDateFormat.format(registerDto.getDob());

        Employee employee = Employee.builder().name(registerDto.getName()).employeeCode(registerDto.getEmployeeCode())
                .image(registerDto.getImage())
                .gender(registerDto.getGender())
                .Dob(Date.valueOf(dob))
                .status(EmployeeStatus.ACTIVATE)
                .phone(registerDto.getPhone()).department(optionalDepartment.get()).address(registerDto.getAddress())
                .position(optionalPosition.get())
                .build();
        Role role = roleRepository.findByName(registerDto.getRole())
                .orElseThrow(() -> new NotFoundException(RoleErrorMessage.ROLE_NOT_EXIST));
        Account account = Account.builder()
                .email(registerDto.getEmail())
                .role(role)
                .employee(employee)
                .status(AccountStatus.ACTIVATED)
                .password(passwordEncoder.encode(registerDto.getPassword())).build();
        employeeRepository.save(employee);
        Account accountInRepo = accountRepository.save(account);
        employee.setAccount(accountInRepo);
        employeeRepository.save(employee);
        RegisterResponseDto registerResponseDto = modelMapper.map(accountInRepo, RegisterResponseDto.class);
        return registerResponseDto;
    }

    @Override
    public LoginResponseDto login(LoginDto employee) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(employee.getEmail(),
                employee.getPassword());
        LoginResponseDto loginResponseDTO = null;
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (authenticate.isAuthenticated()) {
            Optional<Account> accountAuthencatedOptional = accountRepository.findAccountByEmail(employee.getEmail());
            Account accountAuthencated = accountAuthencatedOptional.get();
            if (accountAuthencated.getEmployee() != null) {
                if (!accountAuthencated.getEmployee().getStatus().equals(AccountStatus.ACTIVATED)) {
                    throw new NotFoundException(EmployeeErrorMessage.EMPLOYEE_UNAVAILABLE);
                }
            }
            String token = Utils.buildJWT(authenticate, accountAuthencated, secretKey, jwtConfig);
            loginResponseDTO = LoginResponseDto.builder()
                    .accountId(accountAuthencated.getId())
                    .email(accountAuthencated.getEmail())
                    .status(accountAuthencated.getStatus())
                    .roleName(accountAuthencated.getRole().getName())
                    .token(token).build();
            if (accountAuthencated.getRole().getName().equalsIgnoreCase(RoleName.ROLE_CANDIDATE)) {
                loginResponseDTO.setCandidate(accountAuthencated.getCandidate());
            } else {
                loginResponseDTO.setEmployee(accountAuthencated.getEmployee());
            }
        }
        return loginResponseDTO;
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        Account account = accountRepository.findAccountByEmail(changePasswordDTO.getEmail())
                .orElseThrow(() -> new NotFoundException(AccountErrorMessage.ACCOUNT_NOT_FOUND));
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), account.getPassword())) {
            throw new NotValidException(AccountErrorMessage.PASSWORD_NOT_MATCH);
        }
        account.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        accountRepository.save(account);
    }
}
