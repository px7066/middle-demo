package com.github.middlemail.service;

public interface IMailService {
    void sendSimpleMail();

    void sendHTMLMail();

    void sendAttachmentMail();

    void sendTemplateMail();
}
