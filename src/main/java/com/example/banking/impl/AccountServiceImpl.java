package com.example.banking.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.banking.dto.AccountDto;
import com.example.banking.entity.Account;
import com.example.banking.mapper.AccountMapper;
import com.example.banking.repo.AccountRepo;
import com.example.banking.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	private AccountRepo accountRepo;
	
	public AccountServiceImpl(AccountRepo accountRepo) {
		this.accountRepo = accountRepo;
	}

	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = AccountMapper.mapToAccount(accountDto);
		Account savedAccount = accountRepo.save(account);
		return AccountMapper.maptoAccountDto(savedAccount);
	}

	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountRepo.
							findById(id).
							orElseThrow(() -> new RuntimeException("Account Does not exist"));
					
		return AccountMapper.maptoAccountDto(account);
	}

	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account = accountRepo.
				findById(id).
				orElseThrow(() -> new RuntimeException("Account Does not exist"));
		double total = account.getBalance() + amount;
		account.setBalance(total);
		Account savedAccount = accountRepo.save(account);
		return AccountMapper.maptoAccountDto(savedAccount);
	}

	@Override
	public AccountDto withdraw(Long id, double amount) {
		Account account = accountRepo.
				findById(id).
				orElseThrow(() -> new RuntimeException("Account Does not exist"));
		if(account.getBalance() < amount) {
			throw new RuntimeException("Insufficient Balance");
		}
		
		double total = account.getBalance() - amount;
		account.setBalance(total);
		Account savedAccount = accountRepo.save(account);
		return AccountMapper.maptoAccountDto(savedAccount);
	}

	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts = accountRepo.findAll();
		return accounts.stream().map((account) -> AccountMapper.maptoAccountDto(account)).collect(Collectors.toList());
		
	 
	}

	@Override
	public void deleteAccount(Long id) {
		Account account = accountRepo.
				findById(id).
				orElseThrow(() -> new RuntimeException("Account Does not exist"));
		accountRepo.deleteById(id);
		
	}
	

}
