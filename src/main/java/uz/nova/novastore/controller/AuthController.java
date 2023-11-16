package uz.nova.novastore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true,
                    description = "Jwt token is required to check if the user has role or permission to access this api"
            ),
            responseCode = "200",
            description = "Get user by its username"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @PostMapping("/client/sign-up")
    public StandardResponse<UserEntity>signUp(
            @RequestBody UserCreateDto userCreate
    ){

     return userService.signUp(userCreate,true );
    }
    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true,
                    description = "Jwt token is required to check if the user has role or permission to access this api"
            ),
            responseCode = "200",
            description = "Get user by its username"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @PostMapping("/consumer/sign-up")
    public StandardResponse<UserEntity>signUpConsumer(
            @RequestBody UserCreateDto userCreate
    ){
        return userService.signUp(userCreate,false);
    }
    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true,
                    description = "Jwt token is required to check if the user has role or permission to access this api"
            ),
            responseCode = "200",
            description = "Get user by its username"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @PutMapping("/verify/{id}")
    public StandardResponse<Boolean>verify(
            @RequestParam String code,
            @PathVariable UUID id
    ){
      return userService.verify(code,id);
    }

    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true,
                    description = "Jwt token is required to check if the user has role or permission to access this api"
            ),
            responseCode = "200",
            description = "Get user by its username"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @GetMapping("/login")
    private StandardResponse<JwtResponse>login(
            @RequestBody LoginDto loginDto
    ){
        return userService.login(loginDto);
    }
}
