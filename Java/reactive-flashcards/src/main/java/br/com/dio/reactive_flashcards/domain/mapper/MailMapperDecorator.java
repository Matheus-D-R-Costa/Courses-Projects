package br.com.dio.reactive_flashcards.domain.mapper;

import br.com.dio.reactive_flashcards.domain.dto.MailMessageDto;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public abstract class MailMapperDecorator implements MailMapper {

    @Autowired
    @Qualifier("delegate")
    private MailMapper delegate;

    @Override
    public MimeMessageHelper toMimeMessageHelper(final MimeMessageHelper helper, final MailMessageDto dto,
                                                 final String sender, final String body) throws MessagingException {

        delegate.toMimeMessageHelper(helper, dto, sender, body);
        helper.setText(body, true);
        return helper;
    }

}
