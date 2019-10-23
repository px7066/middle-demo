package com.github.mail.service;

public interface IMailService {
    void sendSimpleMail();

    void sendHTMLMail();

    void sendAttachmentMail();

    void sendTemplateMail();
}
