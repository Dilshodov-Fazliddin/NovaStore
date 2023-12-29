package uz.nova.novastore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nova/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<StandardResponse<Object>>getAll(
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "2") int page
    ){
        return userService.getAllUsers(page,size);
    }
}
