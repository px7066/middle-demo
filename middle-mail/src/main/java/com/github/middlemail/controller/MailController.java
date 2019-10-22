package com.github.middlemail.controller;

import com.github.middlemail.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    private IMailService mailService;


    @GetMapping("sendEmial")
    public void sendEmial(){
        mailService.sendSimpleMail();
    }

    @GetMapping("sendHTMLMail")
    public void sendHTMLMail(){
        mailService.sendHTMLMail();
    }

    @GetMapping("sendAttachmentMail")
    public void sendAttachmentMail(){
        mailService.sendAttachmentMail();
    }

    @GetMapping("sendTemplateMail")
    public void sendTemplateMail(){
        mailService.sendTemplateMail();
    }

}
