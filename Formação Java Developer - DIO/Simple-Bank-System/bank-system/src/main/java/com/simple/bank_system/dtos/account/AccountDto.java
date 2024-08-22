package com.simple.bank_system.dtos.account;

import com.simple.bank_system.dtos.card.CardDto;
import com.simple.bank_system.dtos.feature.FeatureDto;
import com.simple.bank_system.dtos.news.NewsDto;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

public record AccountDto(@NotBlank String number,
                         @NotBlank String agency,
                         @NotBlank BigDecimal balance,
                         @NotBlank BigDecimal accountLimit,
                         @NotBlank List<CardDto> cards,
                         @NotBlank List<FeatureDto> features,
                         @NotBlank List<NewsDto> news) {
}
