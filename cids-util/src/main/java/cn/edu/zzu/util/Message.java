package cn.edu.zzu.util;

import org.springframework.ui.Model;

/**
 * Created by qinhao on 2018/3/23.
 */
public class Message {

    private static final String MESSAGE_ERROR_NAME = "SSM_ERROR";
    private static final String MESSAGE_WARN_NAME = "SSM_WARNING";

    private MessageType messageType;

    private enum MessageType {
        error, warning, notice
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public static void setError(String message, Model model) {
        model.addAttribute(MESSAGE_ERROR_NAME, message);
    }

    public static void setWarn(String message, Model model) {
        model.addAttribute(MESSAGE_WARN_NAME, message);
    }
}
