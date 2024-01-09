package uz.nova.novastore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import org.springframework.web.bind.annotation.*;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nova/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<StandardResponse<Object>>getAll(
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "1") int page
    ){
        return userService.getAllUsers(page,size);
    }

    @PutMapping("/block")
    public ResponseEntity<StandardResponse<?>>block(
            @RequestParam UUID id
    ){
        return userService.blockUsers(id);
    }

    @PutMapping("/unblock")
    public ResponseEntity<StandardResponse<?>>unblock(
            @RequestParam UUID id
    ){
        return userService.unblockUsers(id);
    }

}
