package uz.nova.novastore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nova.novastore.domain.*;
import uz.nova.novastore.service.UserService;

@RestController
@RequestMapping("/nova/user/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/client/sign-up")
    public ResponseEntity<StandardResponse<?>> signUp(@Valid @RequestBody UserCreateDto userCreate) {
        return userService.signUp(userCreate);
    }

    @PutMapping("/verify")
    public ResponseEntity<StandardResponse<?>> verify(@RequestParam int code, @RequestParam String email) {
        return userService.verify(code, email);
    }

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<Object>> login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PutMapping("/forgetPassword")
    public ResponseEntity<StandardResponse<?>> forgetPassword(@RequestParam String email) {
        return userService.forgetPassword(email);
    }

    @PutMapping("/verifyForgetPassword")
    public ResponseEntity<StandardResponse<?>> verifyForgetPassword(@RequestBody VerifyForgetPasswordDto verifyForgetPasswordDto) {
        return userService.verifyForgetPassword(verifyForgetPasswordDto);
    }
}
