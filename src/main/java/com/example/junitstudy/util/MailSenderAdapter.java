package com.example.junitstudy.util;

import org.springframework.stereotype.Component;

// @Component
public class MailSenderAdapter implements MailSender {
    private Mail mail;

    public MailSenderAdapter() {
        this.mail = new Mail();
    }

    @Override
    public boolean sender() {
        return true;
    }
}
