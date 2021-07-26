package com.daka.user.email;

import com.daka.user.entity.EmailObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class EmailSend {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    public void sendSimpleMail(EmailObj emailObj) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 构建一个邮件对象
            //SimpleMailMessage message = new SimpleMailMessage();
            // 设置邮件主题
            helper.setSubject("打卡成功提醒");
            // 设置邮件发送者，这个跟application.yml中设置的要一致
            helper.setFrom("806859957@qq.com");
            // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
            // message.setTo("10*****16@qq.com","12****32*qq.com");
            helper.setTo(emailObj.getEmail_address());
            // 设置邮件抄送人，可以有多个抄送人
            //message.setCc("12****32*qq.com");
            // 设置隐秘抄送人，可以有多个
            //message.setBcc("7******9@qq.com");
            // 设置邮件发送日期
            helper.setSentDate(new Date());
            // 设置邮件的正文
            //message.setText(text);
            Context context = new Context();
            // 设置模板中的变量
            context.setVariable("username",emailObj.getUsr_name());
            context.setVariable("num",emailObj.getUsr_ID());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String time = emailObj.getTime().format(formatter);
            context.setVariable("time",time);
            String type = emailObj.getType();
            context.setVariable("type",type);
            String cloths = emailObj.getCloths();
            context.setVariable("cloths",cloths);
            String hair = emailObj.getHair();
            context.setVariable("hair",hair);
            context.setVariable("statement",emailObj.getStatement());
            // 第一个参数为模板的名称
            String process = templateEngine.process("email_1.html", context);
            // 第二个参数true表示这是一个html文本
            helper.setText(process,true);

            javaMailSender.send(mimeMessage);
            // 发送邮件
            //javaMailSender.send(message);
        }catch (Exception e){
            System.out.println("邮件发送异常");
        }
    }
}
