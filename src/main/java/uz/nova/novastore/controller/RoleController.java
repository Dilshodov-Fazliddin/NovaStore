package uz.nova.novastore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nova.novastore.domain.RoleDto;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.entity.RoleEntity;
import uz.nova.novastore.service.RoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nova/role/")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<StandardResponse<RoleEntity>>save(@RequestBody RoleDto roleDto){
        return roleService.saveRole(roleDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse<?>>delete(@PathVariable UUID id){
        return roleService.deleteRole(id);
    }


    @GetMapping("/get-all")
    public ResponseEntity<StandardResponse<List<RoleEntity>>>getAll(){
        return roleService.getAllRoles();
    }

    @PutMapping("/update")
    public ResponseEntity<StandardResponse<RoleEntity>>update(@RequestParam String newName,@RequestParam String oldName){
        return roleService.updateRole(oldName,newName);
    }
}


