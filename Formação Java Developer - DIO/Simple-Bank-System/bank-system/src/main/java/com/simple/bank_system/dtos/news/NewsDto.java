package com.simple.bank_system.dtos.news;

import jakarta.validation.constraints.NotBlank;

public record NewsDto(@NotBlank String icon, @NotBlank String description) {
}
