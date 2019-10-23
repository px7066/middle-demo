package com.github.mail.service.impl;

import com.github.mail.service.IMailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MailServiceImpl implements IMailService {


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration configuration; //freemarker

    @Override
    public void sendSimpleMail() {
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage .setFrom("7066450@qq.com");
            simpleMailMessage .setTo("panxi@zjport.gov.cn");
            simpleMailMessage.setSubject("主题");
            simpleMailMessage.setText("内容");
            javaMailSender.send(simpleMailMessage);//发送
        }catch(Exception e){
            log.error("发送失败",e.getMessage());
        }
    }

    @Override
    public void sendHTMLMail() {
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper .setFrom("7066450@qq.com");
            mimeMessageHelper .setTo("panxi@zjport.gov.cn");
            mimeMessageHelper.setSubject("HTML邮件发送测试");
            StringBuilder sb = new StringBuilder();
            sb.append("<h1>SpirngBoot测试邮件HTML</h1>")
                    .append("\"<p style='color:#F00'>你是真的太棒了！</p>")
                    .append("<p style='text-align:right'>右对齐</p>");
            mimeMessageHelper.setText(sb.toString(), true);
            javaMailSender.send(mimeMessage);//发送
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendAttachmentMail() {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper .setFrom("7066450@qq.com");
            mimeMessageHelper .setTo("panxi@zjport.gov.cn");
            mimeMessageHelper.setSubject("HTML邮件+附件发送测试");
            StringBuilder sb = new StringBuilder();
            sb.append("<h1>SpirngBoot测试邮件附件+HTML</h1>")
                    .append("\"<p style='color:#F00'>你是真的太棒了！</p>")
                    .append("<p style='text-align:right'>右对齐</p>");
            mimeMessageHelper.setText(sb.toString(), true);
            //文件路径
            File file = ResourceUtils.getFile("classpath:static/image/mail.png");
//            FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/mail.png"));
            mimeMessageHelper.addAttachment("mail.png", file);

            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendTemplateMail() {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper .setFrom("7066450@qq.com");
            mimeMessageHelper .setTo("panxi@zjport.gov.cn");
            mimeMessageHelper.setSubject("HTML邮件+附件发送测试");

            Map<String, Object> model = new HashMap<>();
            model.put("content", "测试");
            model.put("title", "标题Mail中使用了FreeMarker");
            Template template = configuration.getTemplate("mail.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            mimeMessageHelper.setText(text, true);

            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
