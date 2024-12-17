package com.example.banking.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.banking.entity.Account;

public interface AccountRepo extends JpaRepository<Account, Long> {

}
