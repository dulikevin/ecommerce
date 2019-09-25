package com.duli.ecommerceproductmailservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.duli.api.mail.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Service
public class MailServiceImpl implements IMailService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendEmail(String to, String subject, String content, String path, String modelPath){
        //封装数据信息
        Context context = new Context();
        //获取到要发送的模板的信息
        context.setVariable("username","duli520china@163.com");
        String contentModel = templateEngine.process("active", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom("duli520china@163.com");
            helper.setTo("1751949926@qq.com");
            helper.setSubject("杜力邮件测试");
            helper.setText(contentModel,true);
            //携带附件
            FileSystemResource resoure = new FileSystemResource("C:\\ecommerce\\duli-temp\\mail\\src\\main\\resources\\static\\duli.jpg");
            helper.addAttachment("duli.jpg",resoure);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
