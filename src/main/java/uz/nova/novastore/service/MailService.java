package uz.nova.novastore.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nova.novastore.domain.StandardResponse;

@Service
public interface MailService {
   void sendVerifyCode(Integer code,String email);
}

