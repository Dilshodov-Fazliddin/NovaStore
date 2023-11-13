package uz.nova.novastore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nova.novastore.domain.response.JwtResponse;
import uz.nova.novastore.domain.response.StandardResponse;
import uz.nova.novastore.domain.user.LoginDto;
import uz.nova.novastore.domain.user.UserCreateDto;
import uz.nova.novastore.entity.user.UserEntity;
import uz.nova.novastore.service.user.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/nova/user/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/client/sign-up")
    public StandardResponse<UserEntity>signUp(
            @RequestBody UserCreateDto userCreate
    ){

     return userService.signUp(userCreate,true );
    }

    @PostMapping("/consumer/sign-up")
    public StandardResponse<UserEntity>signUpConsumer(
            @RequestBody UserCreateDto userCreate
    ){
        return userService.signUp(userCreate,false);
    }
    @PutMapping("/verify/{id}")
    public StandardResponse<Boolean>verify(
            @RequestParam String code,
            @PathVariable UUID id
    ){
      return userService.verify(code,id);
    }


    @GetMapping("/login")
    private StandardResponse<JwtResponse>login(
            @RequestBody LoginDto loginDto
    ){
        return userService.login(loginDto);
    }
}
