package com.wannago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.wannago.dto.ResponseDTO;
import com.wannago.util.CodeGenerator;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true로 설정하여 HTML 형식 활성화
            helper.setFrom(from);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.", e);
        }
    }

    public void sendVerificationEmail(String email,String code) {
        String subject = "본인확인 인증코드 입니다!";
        StringBuffer sb = new StringBuffer();
        sb.append("<div style='width: 100%; max-width: 600px; margin: 0 auto; padding: 20px; font-family: Arial, sans-serif; background-color: #f9f9f9;'>");
        sb.append("<div style='text-align: center; margin-bottom: 20px;'>");
        sb.append("<img src='https://postfiles.pstatic.net/MjAyNDA2MjRfMjQx/MDAxNzE5MTk4OTc0NTM4.qQNgcGjEUQK6skXmJ96_UbzK8Du3O0o5xIeZKrFGiBMg.A51Rese6nRQbHVwdvO4mQ34KLTDTcsU6eZDsw53i9HAg.PNG/logo.png?type=w966' alt='Logo' style='max-width: 100px;' loading='lazy'>");
        sb.append("</div>");
        sb.append("<div style='text-align: center; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>");
        sb.append("<h1 style='color: #333333; font-size: 24px;'>본인확인 인증코드 입니다!</h1>");
        sb.append("<p style='color: #666666; font-size: 16px;'>본인 확인을 위해 아래의 <strong>인증코드</strong>를 화면에 입력해주세요.</p>");
        sb.append("<div style='font-size: 32px; font-weight: bold; color: #333333; margin: 20px 0;'>").append(code).append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        String content = sb.toString();
        sendEmail(email, subject, content);
    }
    
    public void sendPasswordEmail(String email,String password) {
        String subject = "임시비밀번호입니다";
        StringBuffer sb = new StringBuffer();
        sb.append("<div style='width: 100%; max-width: 600px; margin: 0 auto; padding: 20px; font-family: Arial, sans-serif; background-color: #f9f9f9;'>");
        sb.append("<div style='text-align: center; margin-bottom: 20px;'>");
        sb.append("<img src='https://postfiles.pstatic.net/MjAyNDA2MjRfMjQx/MDAxNzE5MTk4OTc0NTM4.qQNgcGjEUQK6skXmJ96_UbzK8Du3O0o5xIeZKrFGiBMg.A51Rese6nRQbHVwdvO4mQ34KLTDTcsU6eZDsw53i9HAg.PNG/logo.png?type=w966' alt='Logo' style='max-width: 100px;' loading='lazy'>");
        sb.append("</div>");
        sb.append("<div style='text-align: center; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>");
        sb.append("<h1 style='color: #333333; font-size: 24px;'>임시비밀번호입니다</h1>");
        sb.append("<p style='color: #666666; font-size: 16px;'>임시비밀번호를 확인하기 위해 아래의 <strong>임시비밀번호</strong>를 화면에 입력해주세요.</p>");
        sb.append("<div style='font-size: 32px; font-weight: bold; color: #333333; margin: 20px 0;'>").append(password).append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        String content = sb.toString();
        sendEmail(email, subject, content);
    }


}
