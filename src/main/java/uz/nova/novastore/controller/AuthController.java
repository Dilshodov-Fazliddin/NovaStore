package uz.nova.novastore.controller;

import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    public ResponseEntity<StandardResponse<?>> verify(@Valid @RequestParam int code, @RequestParam String email) {
        return userService.verify(code, email);
    }

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<Object>> login(@Valid @RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }
    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200",
            description = "first you need to send a request to this api"
    )
    @PutMapping("/forgetPassword")
    public ResponseEntity<StandardResponse<?>> forgetPassword(@Valid @RequestParam String email) {
        return userService.forgetPassword(email);
    }

    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200",
            description = "after sending, we need to enter the verification code in the user's mail"
    )
    @PutMapping("/verifyForgetCode")
    public ResponseEntity<StandardResponse<?>> verifyForgetPassword(@Valid @RequestBody VerifyForgetPasswordDto verifyForgetPasswordDto) {
        return userService.verifyForgetCode(verifyForgetPasswordDto);

    }

    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200",
            description = "After everything is good, enter the new password"
    )
    @PutMapping("/set-new-password")
    public ResponseEntity<StandardResponse<?>> setNewPassword(@RequestParam String email,@RequestParam String newPassword) {
        return userService.verifyForgetSetNewPassword(email,newPassword);

    }



}
