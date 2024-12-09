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

    private static final BaseErrorMessage GENERIC_EXCEPTION = new BaseErrorMessage("generic");
    private static final BaseErrorMessage GENERIC_NOT_FOUND = new BaseErrorMessage("generic.notFound");
    private static final BaseErrorMessage GENERIC_METHOD_NOT_ALLOW = new BaseErrorMessage("generic.methodNotAllow");

    public BaseErrorMessage params(final String... params) {
        this.params = ArrayUtils.clone(params);
        return this;
    }

    public String getMessage() {
        String message = tryGetMessageFromBundle();
        if (ArrayUtils.isNotEmpty(params)) {
            MessageFormat fmt = new MessageFormat(message);
            message = fmt.format(params);
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
