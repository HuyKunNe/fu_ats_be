package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.constant.account.AccountErrorMessage;
import com.fu.fuatsbe.constant.account.AccountStatus;
import com.fu.fuatsbe.constant.candidate.CandidateStatus;
import com.fu.fuatsbe.constant.employee.EmployeeStatus;
import com.fu.fuatsbe.constant.role.RoleErrorMessage;
import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.entity.Role;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.AccountRepository;
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.RoleRepository;
import com.fu.fuatsbe.response.AccountResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.AccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImp implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public List<AccountResponse> getAllAccounts(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Account> pageResult = accountRepository.findAll(pageable);
        List<AccountResponse> result = new ArrayList<AccountResponse>();
        if (pageResult.hasContent()) {
            for (Account account : pageResult.getContent()) {
                AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
                result.add(accountResponse);
            }
        } else
            throw new ListEmptyException(AccountErrorMessage.LIST_ACCOUNT_IS_EMPTY);
        return result;
    }

    @Override
    public List<AccountResponse> getAllAccountsByRole(int roleId, int pageNo, int pageSize) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(RoleErrorMessage.ROLE_NOT_EXIST));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Account> pageResult = accountRepository.findByRole(role, pageable);

        List<AccountResponse> result = new ArrayList<AccountResponse>();
        if (pageResult.hasContent()) {
            for (Account account : pageResult.getContent()) {
                AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
                result.add(accountResponse);
            }
        } else
            throw new ListEmptyException(AccountErrorMessage.LIST_ACCOUNT_IS_EMPTY);
        return result;
    }

    @Override
    public AccountResponse getAccountByEmail(String email) {
        Account account = accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new NotFoundException(AccountErrorMessage.ACCOUNT_NOT_FOUND));
        AccountResponse response = modelMapper.map(account, AccountResponse.class);
        return response;
    }

    @Override
    public AccountResponse getAccountById(int id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(AccountErrorMessage.ACCOUNT_NOT_FOUND));
        AccountResponse response = modelMapper.map(account, AccountResponse.class);
        return response;
    }

    @Override
    public boolean deleteAccountById(int id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(AccountErrorMessage.ACCOUNT_NOT_FOUND));
        account.setStatus(AccountStatus.DISABLED);
        accountRepository.save(account);
        if (account.getEmployee() != null) {
            account.getEmployee().setStatus(EmployeeStatus.DISABLE);
            employeeRepository.save(account.getEmployee());
        }
        if (account.getCandidate() != null) {
            account.getCandidate().setStatus(CandidateStatus.DISABLED);
            candidateRepository.save(account.getCandidate());
        }
        return true;
    }

    @Override
    public List<AccountResponse> getActivateAccounts(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Account> pageResult = accountRepository.findByStatus(AccountStatus.ACTIVATED, pageable);

        List<AccountResponse> result = new ArrayList<AccountResponse>();
        if (pageResult.hasContent()) {
            for (Account account : pageResult.getContent()) {
                AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
                result.add(accountResponse);
            }
        } else
            throw new ListEmptyException(AccountErrorMessage.LIST_ACCOUNT_IS_EMPTY);
        return result;
    }

    @Override
    public List<AccountResponse> getDisableAccounts(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Account> pageResult = accountRepository.findByStatus(AccountStatus.DISABLED, pageable);

        List<AccountResponse> result = new ArrayList<AccountResponse>();
        if (pageResult.hasContent()) {
            for (Account account : pageResult.getContent()) {
                AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
                result.add(accountResponse);
            }
        } else
            throw new ListEmptyException(AccountErrorMessage.LIST_ACCOUNT_IS_EMPTY);
        return result;
    }

    @Override
    public ResponseWithTotalPage<AccountResponse> getEmployeeAccount(int pageNo, int pageSize, String name) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Account> pageResult = accountRepository.getEmployeeAccount(pageable);
        ResponseWithTotalPage<AccountResponse> response = new ResponseWithTotalPage<>();
        List<AccountResponse> list = new ArrayList<>();
        if (pageResult.hasContent()) {
            for (Account account : pageResult.getContent()) {
                if (account.getEmployee().getName().toLowerCase().contains(name.toLowerCase())) {
                    AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
                    list.add(accountResponse);
                }
            }
            response.setTotalPage(pageResult.getTotalPages());
            response.setResponseList(list);
        } else
            throw new ListEmptyException(AccountErrorMessage.LIST_ACCOUNT_IS_EMPTY);
        return response;
    }
}
