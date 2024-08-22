package com.simple.bank_system.dtos.card;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CardDto(@NotBlank String number,
                      @NotBlank BigDecimal cardLimit) {
}
