package com.revoktek.motivus.core.i18n;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class MessageProvider {

    private static final String BUNDLE_NAME = "i18n.messages";
    private static final String MESSAGE_UNIQUE = "default.not.unique.message";
    private static final String MESSAGE_NOT_FOUND = "default.not.found.message";
    private static final String MESSAGE_NOT_ACTIVE = "default.doesnt.active.message";
    private static final String MESSAGE_CONSOLE_ERROR_UNEXPECTED = "console.error.unexpected";
    private static final String MESSAGE_CONSOLE_WARNING_BUSINESS = "console.warning.business.validation";
    private static final String MESSAGE_ERROR_UNEXPECTED = "message.error.unexpected";
    private static final String MESSAGE_ERROR_UNHANDLED = "message.error.unhandled";

    private String getMessage(String key, Locale locale, Object... params) {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        return MessageFormat.format(bundle.getString(key), params);

    }

    public String getMessage(String key, Object... params) {
        return getMessage(key, new Locale("es", "MX"), params);
    }

    public String getMessageUnique(Object... params) {
        return getMessage(MESSAGE_UNIQUE, params);
    }

    public String getMessageNotFound(Object... params) {
        return getMessage(MESSAGE_NOT_FOUND, params);
    }
    public String getUnexpectedErrorConsole(Object... params) {
        return getMessage(MESSAGE_CONSOLE_ERROR_UNEXPECTED, params);
    }

    public String getBusinessWarningConsole(Object... params) {
        return getMessage(MESSAGE_CONSOLE_WARNING_BUSINESS, params);
    }

    public String getUnexpectedError(Object... params) {
        return getMessage(MESSAGE_ERROR_UNEXPECTED, params);
    }
    public String getUnhandledError(Object... params) {
        return getMessage(MESSAGE_ERROR_UNHANDLED, params);
    }

    public String getMessageNotActive() {
        return getMessage(MESSAGE_NOT_ACTIVE, null);
    }
}
