package uz.nova.novastore.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.nova.novastore.entity.user.UserEntity;
import uz.nova.novastore.entity.user.VerificationEntity;
import uz.nova.novastore.exception.DataNotFoundException;
import uz.nova.novastore.repository.verification.VerificationRepository;
import uz.nova.novastore.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class MailService {
    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender javaMailSender;
    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;


    public void sendVerifyCode(UserEntity user){
        VerificationEntity verificationEntity = verificationRepository.findVerificationEntityByUserId(user.getId())
                .orElseThrow(()->new DataNotFoundException("User not found"));
        String message="Your verification code 👩‍💻  "+ verificationEntity.getCode();


        UserEntity userEntity = userRepository.findUserEntityByUsername(user.getUsername())
                .orElseThrow(()-> new DataNotFoundException("Username not found"));


        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Verification");
        simpleMailMessage.setTo(userEntity.getEmail());
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
    }
}
