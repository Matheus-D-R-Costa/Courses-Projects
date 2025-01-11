package br.com.dio.reactive_flashcards.domain.exception;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class BaseErrorMessage {

    private final String DEFAULT_RESOURCE = "messages";
    private final String KEY;
    private String[] params;

    public static final BaseErrorMessage GENERIC_EXCEPTION = new BaseErrorMessage("generic");
    public static final BaseErrorMessage GENERIC_NOT_FOUND = new BaseErrorMessage("generic.notFound");
    public static final BaseErrorMessage GENERIC_METHOD_NOT_ALLOW = new BaseErrorMessage("generic.methodNotAllow");
    public static final BaseErrorMessage GENERIC_BAD_REQUEST = new BaseErrorMessage("generic.badRequest");
    public static final BaseErrorMessage USER_NOT_FOUND = new BaseErrorMessage("user.notFound");
    public static final BaseErrorMessage DECK_NOT_FOUND = new BaseErrorMessage("deck.notFound");
    public static final BaseErrorMessage EMAIL_ALREADY_USED = new BaseErrorMessage("user.emailAlreadyUsed");

    public BaseErrorMessage params(final String... PARAMS) {
        this.params = ArrayUtils.clone(PARAMS);
        return this;
    }

    public String getMessage() {
        String message = tryGetMessageFromBundle();
        if (ArrayUtils.isNotEmpty(this.params)) {
            MessageFormat fmt = new MessageFormat(message);
            message = fmt.format(this.params);
        }

        return message;

    }

    private String tryGetMessageFromBundle() {
        return getResource().getString(KEY);
    }

    public ResourceBundle getResource() {
        return ResourceBundle.getBundle(DEFAULT_RESOURCE);
    }

}
