package br.com.dio.reactive_flashcards.domain.service;

import br.com.dio.reactive_flashcards.domain.dto.MailMessageDto;
import br.com.dio.reactive_flashcards.domain.helper.RetryHelper;
import br.com.dio.reactive_flashcards.domain.mapper.MailMapper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
public class MailService {

    private final RetryHelper RETRY_HELPER;
    private final JavaMailSender MAIL_SENDER;
    private final TemplateEngine TEMPLATE_ENGINE;
    private final MailMapper MAPPER;
    private final String SENDER;

    public MailService(final RetryHelper RETRY_HELPER,
                       final JavaMailSender MAIL_SENDER,
                       final TemplateEngine TEMPLATE_ENGINE,
                       final MailMapper MAPPER,
                       @Value("${reactive-flashcards.mail.sender}") final String SENDER) {

        this.RETRY_HELPER = RETRY_HELPER;
        this.MAIL_SENDER = MAIL_SENDER;
        this.TEMPLATE_ENGINE = TEMPLATE_ENGINE;
        this.MAPPER = MAPPER;
        this.SENDER = SENDER;
    }

    public Mono<Void> send(final MailMessageDto DTO) {
        return Mono.just(MAIL_SENDER.createMimeMessage())
                .flatMap(mimeMessage -> buildMessage(mimeMessage, DTO))
                .flatMap(this::send)
                .then();
    }

    private Mono<MimeMessage> buildMessage(final MimeMessage MESSAGE, final MailMessageDto DTO) {
        return Mono.fromCallable(() -> {
            var helper = new MimeMessageHelper(MESSAGE, StandardCharsets.UTF_8.name());
            MAPPER.toMimeMessageHelper(helper, DTO, SENDER, buildTemplate(DTO.template(), DTO.variables()));
            return MESSAGE;
        });
    }

    private String buildTemplate(final String TEMPLATE, final Map<String, Object> VARIABLES) {
        var context = new Context(new Locale("pt", "BR"));
        context.setVariables(VARIABLES);
        return TEMPLATE_ENGINE.process(TEMPLATE, context);
    }

    private Mono<Void> send(final MimeMessage MESSAGE) {
        return Mono.fromCallable(() -> {
            MAIL_SENDER.send(MESSAGE);
            return MESSAGE;
        }).retryWhen(RETRY_HELPER.processRetry(UUID.randomUUID().toString(), Throwable -> Throwable instanceof MailException))
                .then();
    }


}
