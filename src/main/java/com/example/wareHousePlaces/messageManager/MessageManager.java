package com.example.wareHousePlaces.messageManager;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private static final String BASE_NAME = "messages";
    private static ResourceBundle messages;

    public static void loadMessages(Locale locale) {
        messages = ResourceBundle.getBundle(BASE_NAME, locale);
    }

    public static String getMessage(String key) {
        return messages.getString(key);
    }
}
