package com.duli.api.mail;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

public interface IMailService {
    String sendEmail(String to,String subject,String content,String path,String modelPath) throws MessagingException, MessagingException,  MessagingException;

}
