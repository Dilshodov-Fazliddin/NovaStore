package uz.nova.novastore.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
   void sendVerifyCode(Integer code,String email);
   void sendNotificationBlockUser(String email);
   void sendNotificationUnBlockUser(String email);
}

