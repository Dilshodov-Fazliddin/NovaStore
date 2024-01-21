package uz.nova.novastore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.domain.request.ProfileDto;
import uz.nova.novastore.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor

@RequestMapping("/nova/users")
public class UserController {
    private final UserService userService;
    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @GetMapping("/getAll")
    public ResponseEntity<StandardResponse<Object>>getAll(
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "0") int page
    ){
        return userService.getAllUsers(page,size);
    }
    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @PutMapping("/block")
    public ResponseEntity<StandardResponse<?>>block(
            @RequestParam UUID id
    ){
        return userService.blockUsers(id);
    }
    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @PutMapping("/unblock")
    public ResponseEntity<StandardResponse<?>>unblock(
            @RequestParam UUID id
    ){
        return userService.unblockUsers(id);
    }

    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @GetMapping("/get-customers")
    public ResponseEntity<StandardResponse<List<ProfileDto>>>getCustomers(
            @RequestParam int page,
            @RequestParam int size
    ){
      return userService.getCustomers(page,size);
    }


    @GetMapping("/get-number-users")
    public ResponseEntity<StandardResponse<Integer>>numberOfUsers(){
        return userService.getNumberOfUsers();
    }

}
