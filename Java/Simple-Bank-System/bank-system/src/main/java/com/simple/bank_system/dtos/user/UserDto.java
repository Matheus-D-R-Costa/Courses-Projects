package com.simple.bank_system.dtos.user;

import com.simple.bank_system.dtos.account.AccountDto;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UserDto(@NotBlank String username, @NotBlank Set<AccountDto> accounts) {}