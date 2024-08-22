package com.simple.bank_system.dtos.feature;

import jakarta.validation.constraints.NotBlank;

public record FeatureDto(@NotBlank String icon,
                         @NotBlank String description) {
}
