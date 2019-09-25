package com.duli.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailApplicationTests {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    //普通邮件
    @Test
    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("duli520china@163.com");
        message.setTo("1751949926@qq.com");
        message.setSubject("杜力邮件测试");
        message.setText("hi,杜力邮件测试"+"<a href='www.baidu.com'>");
        mailSender.send(message);
    }
    //包含html格式的邮件
    @Test
    public void sendHTML() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("duli520china@163.com");
        helper.setTo("1751949926@qq.com");
        helper.setSubject("杜力邮件测试");
        helper.setText("杜力邮件测试"+"<a href='https://www.baidu.com'>hi,duli</a>",true);
        javaMailSender.send(mimeMessage);
    }

    //附件
    @Test
    public void sendAttchment() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("duli520china@163.com");
        helper.setTo("1751949926@qq.com");
        helper.setSubject("杜力邮件测试");
        helper.setText("杜力邮件测试"+"<a href='https://www.baidu.com'>hi,duli</a>",true);
        //携带附件
        FileSystemResource resoure = new FileSystemResource("C:\\ecommerce\\duli-temp\\mail\\src\\main\\resources\\static\\duli.jpg");
        helper.addAttachment("duli.jpg",resoure);
        javaMailSender.send(mimeMessage);
    }
    //邮件模板
    //激活邮件，找回密码邮件，积分更新
    //附件
    @Test
    public void sendModel() throws MessagingException {
        //封装数据信息
        Context context = new Context();
        //获取到要发送的模板的信息
        context.setVariable("username","duli520china@163.com");
        String content = templateEngine.process("active", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("duli520china@163.com");
        helper.setTo("1751949926@qq.com");
        helper.setSubject("杜力邮件测试");
        helper.setText(content,true);
        //携带附件
        FileSystemResource resoure = new FileSystemResource("C:\\ecommerce\\duli-temp\\mail\\src\\main\\resources\\static\\duli.jpg");
        helper.addAttachment("duli.jpg",resoure);
        javaMailSender.send(mimeMessage);
    }
}
