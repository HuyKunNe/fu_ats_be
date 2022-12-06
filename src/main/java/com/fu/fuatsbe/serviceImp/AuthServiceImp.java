package com.fu.fuatsbe.serviceImp;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.constant.account.AccountErrorMessage;
import com.fu.fuatsbe.constant.account.AccountProvider;
import com.fu.fuatsbe.constant.account.AccountStatus;
import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.candidate.CandidateStatus;
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

import org.apache.commons.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.management.relation.RoleNotFoundException;

import java.sql.Date;
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

        Role role = roleRepository.findByName(RoleName.ROLE_CANDIDATE)
                .orElseThrow(() -> new NotFoundException(RoleErrorMessage.ROLE_NOT_EXIST));

        RegisterResponseDto registerResponseDto = new RegisterResponseDto();

        Optional<Candidate> optionalCandidate = candidateRepository.findByPhone(registerDTO.getPhone());
        if (optionalCandidate.isPresent()) {
            throw new ExistException(ValidationMessage.PHONE_IS_EXIST);
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob = LocalDate.parse(registerDTO.getDob().toString(), format);

        if (optionalUser.isPresent()) {
            if (optionalUser.get().getProvider().equalsIgnoreCase(AccountProvider.LOCAL)) {
                throw new EmailExistException(EmployeeErrorMessage.EMAIL_EXIST);
            } else {

                Candidate candidateExist = candidateRepository.findByEmail(registerDTO.getEmail())
                        .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));

                candidateExist.setName(registerDTO.getName());
                candidateExist.setPhone(registerDTO.getPhone());
                candidateExist.setImage(registerDTO.getImage());
                candidateExist.setDob(registerDTO.getDob());
                candidateExist.setGender(registerDTO.getGender());
                candidateExist.setAddress(registerDTO.getAddress());

                Candidate candidateSave = candidateRepository.save(candidateExist);

                Optional<Account> accountExist = accountRepository.findAccountByEmail(registerDTO.getEmail());

                if (accountExist.isPresent()) {
                    accountExist.get().setProvider(AccountProvider.LOCAL);
                    accountExist.get().setPassword(passwordEncoder.encode(registerDTO.getPassword()));
                    Account accountSaved = accountRepository.save(accountExist.get());
                    registerResponseDto = modelMapper.map(accountSaved, RegisterResponseDto.class);
                } else {
                    Account newAccount = Account.builder()
                            .email(registerDTO.getEmail())
                            .role(role)
                            .candidate(candidateSave)
                            .provider(AccountProvider.LOCAL)
                            .status(AccountStatus.ACTIVATED)
                            .password(passwordEncoder.encode(registerDTO.getPassword())).build();

                    Account newAccountSaved = accountRepository.save(newAccount);
                    candidateSave.setAccount(newAccountSaved);
                    candidateRepository.save(candidateSave);
                    registerResponseDto = modelMapper.map(newAccountSaved, RegisterResponseDto.class);
                }
            }

        } else {
            Candidate candidate = Candidate.builder()
                    .name(registerDTO.getName())
                    .email(registerDTO.getEmail())
                    .phone(registerDTO.getPhone())
                    .image(registerDTO.getImage())
                    .dob(Date.valueOf(dob))
                    .gender(registerDTO.getGender())
                    .address(registerDTO.getAddress())
                    .status(CandidateStatus.ACTIVATED)
                    .build();

            Account account = Account.builder()
                    .email(candidate.getEmail())
                    .role(role)
                    .candidate(candidate)
                    .provider(AccountProvider.LOCAL)
                    .status(AccountStatus.ACTIVATED)
                    .password(passwordEncoder.encode(registerDTO.getPassword())).build();

            candidateRepository.save(candidate);
            Account credentialInRepo = accountRepository.save(account);
            candidate.setAccount(credentialInRepo);
            candidateRepository.save(candidate);
            registerResponseDto = modelMapper.map(credentialInRepo, RegisterResponseDto.class);
        }

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

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob = LocalDate.parse(registerDto.getDob().toString(), format);

        Employee employee = Employee.builder().name(registerDto.getName()).employeeCode(registerDto.getEmployeeCode())
                .image(registerDto.getImage())
                .gender(registerDto.getGender())
                .dob(Date.valueOf(dob))
                .jobLevel(registerDto.getJobLevel())
                .status(EmployeeStatus.ACTIVATE)
                .phone(registerDto.getPhone()).department(optionalDepartment.get()).address(registerDto.getAddress())
                .position(optionalPosition.get())
                .build();
        Role role;
        if (optionalDepartment.get().getName().equals("Phòng điều hành")) {
            role = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new NotFoundException(RoleErrorMessage.ROLE_NOT_EXIST));
        } else {
            role = roleRepository.findByName(registerDto.getRole())
                    .orElseThrow(() -> new NotFoundException(RoleErrorMessage.ROLE_NOT_EXIST));
        }
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
    public LoginResponseDto login(LoginDto loginDTO) {
        Account account = accountRepository.findAccountByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new NotFoundException(AccountErrorMessage.ACCOUNT_NOT_FOUND));
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                loginDTO.getPassword());
        LoginResponseDto loginResponseDTO = null;
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (authenticate.isAuthenticated()) {
            Optional<Account> accountAuthencatedOptional = accountRepository.findAccountByEmail(loginDTO.getEmail());
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
                    .token(token)
                    .build();
            if (!loginDTO.getNotificationToken().isEmpty()) {
                account.setNotificationToken(loginDTO.getNotificationToken());
                accountRepository.save(account);
            }
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

    @Override
    public LoginResponseDto loginWithGoogle(String token) throws JSONException {

        String[] split_string = token.split("\\.");
        String base64EncodedBody = split_string[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        JSONObject jsonObject = new JSONObject(body);

        String email = jsonObject.get("email").toString();
        LoginResponseDto loginResponseDTO = null;

        Optional<Account> account = accountRepository.findAccountByEmail(email);

        if (!account.isPresent()) {
            String picture = jsonObject.get("picture").toString();
            String name = jsonObject.get("name").toString();

            Candidate candidate = Candidate.builder()
                    .name(name)
                    .email(email)
                    .image(picture)
                    .dob(Date.valueOf("2000-01-01"))
                    .status(CandidateStatus.ACTIVATED)
                    .build();

            Role role = roleRepository.findByName(RoleName.ROLE_CANDIDATE)
                    .orElseThrow(() -> new NotFoundException(RoleErrorMessage.ROLE_NOT_EXIST));

            Account newAccount = Account.builder()
                    .email(candidate.getEmail())
                    .role(role)
                    .candidate(candidate)
                    .provider(AccountProvider.GOOGLE)
                    .password(passwordEncoder.encode(""))
                    .status(AccountStatus.ACTIVATED).build();

            candidateRepository.save(candidate);
            Account credentialInRepo = accountRepository.save(newAccount);
            candidate.setAccount(credentialInRepo);
            candidateRepository.save(candidate);

            Authentication authentication = new UsernamePasswordAuthenticationToken(credentialInRepo.getEmail(), "");
            Authentication authenticate = authenticationManager.authenticate(authentication);
            if (authenticate.isAuthenticated()) {

                String tokenResponse = Utils.buildJWT(authenticate, credentialInRepo, secretKey, jwtConfig);

                loginResponseDTO = LoginResponseDto.builder()
                        .accountId(credentialInRepo.getId())
                        .email(credentialInRepo.getEmail())
                        .status(credentialInRepo.getStatus())
                        .roleName(credentialInRepo.getRole().getName())
                        .token(tokenResponse)
                        .candidate(credentialInRepo.getCandidate())
                        .build();
            }

        } else {
            Authentication authentication = new UsernamePasswordAuthenticationToken(account.get().getEmail(),
                    account.get().getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            if (authenticate.isAuthenticated()) {

                Account accountAuthencated = accountRepository.findAccountByEmail(account.get().getEmail()).get();
                String tokenResponse = Utils.buildJWT(authenticate, accountAuthencated, secretKey, jwtConfig);

                loginResponseDTO = LoginResponseDto.builder()
                        .accountId(accountAuthencated.getId())
                        .email(accountAuthencated.getEmail())
                        .status(accountAuthencated.getStatus())
                        .roleName(accountAuthencated.getRole().getName())
                        .token(tokenResponse)
                        .candidate(accountAuthencated.getCandidate())
                        .build();
            }
        }

        return loginResponseDTO;
    }
}
