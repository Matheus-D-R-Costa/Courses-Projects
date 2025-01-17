package br.com.dio.reactive_flashcards.domain.exception;

import lombok.RequiredArgsConstructor;

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
    public static final BaseErrorMessage GENERIC_MAX_RETRIES = new BaseErrorMessage("generic.maxRetries");
    public static final BaseErrorMessage USER_NOT_FOUND = new BaseErrorMessage("user.notFound");
    public static final BaseErrorMessage DECK_NOT_FOUND = new BaseErrorMessage("deck.notFound");
    public static final BaseErrorMessage STUDY_DECK_NOT_FOUND = new BaseErrorMessage("studyDeck.notFound");
    public static final BaseErrorMessage STUDY_NOT_FOUND = new BaseErrorMessage("study.notFound");
    public static final BaseErrorMessage STUDY_QUESTION_NOT_FOUND = new BaseErrorMessage("studyQuestion.notFound");
    public static final BaseErrorMessage EMAIL_ALREADY_USED = new BaseErrorMessage("user.emailAlreadyUsed");
    public static final BaseErrorMessage DECK_PENDING = new BaseErrorMessage("study.deckPending");

    public BaseErrorMessage params(final String... PARAMS) {
        this.params = (PARAMS != null) ? PARAMS.clone() : null;
        return this;
    }

    public String getMessage() {
        String message = tryGetMessageFromBundle();
        if (hasParams()) {
            MessageFormat fmt = new MessageFormat(message);
            message = fmt.format(this.params);
        }

        return message;

    }

    private boolean hasParams() {
        return this.params != null && this.params.length > 0;
    }

    private String tryGetMessageFromBundle() {
        return getResource().getString(KEY);
    }

    public ResourceBundle getResource() {
        return ResourceBundle.getBundle(DEFAULT_RESOURCE);
    }

}
