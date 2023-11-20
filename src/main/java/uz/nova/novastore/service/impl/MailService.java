package uz.nova.novastore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.nova.novastore.entity.UserEntity;
import uz.nova.novastore.exception.DataNotFoundException;
import uz.nova.novastore.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MailService {
    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender javaMailSender;


    public void sendVerifyCode(Integer code, String email) {
        String message = "Your verification code 👩‍💻  " + code;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Verification");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
    }
}
