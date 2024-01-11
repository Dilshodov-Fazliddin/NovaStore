package uz.nova.novastore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nova.novastore.domain.RoleDto;
import uz.nova.novastore.domain.StandardResponse;
import uz.nova.novastore.domain.request.RoleForFront;
import uz.nova.novastore.entity.RoleEntity;
import uz.nova.novastore.service.RoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nova/role/")
public class RoleController {
    private final RoleService roleService;

    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @PostMapping("/create")
    public ResponseEntity<StandardResponse<RoleEntity>>save(@Valid @RequestBody RoleDto roleDto){
        return roleService.saveRole(roleDto);
    }

    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse<?>>delete(@PathVariable UUID id){
        return roleService.deleteRole(id);
    }


    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @GetMapping("/get-all")
    public ResponseEntity<StandardResponse<List<RoleForFront>>>getAll(@RequestParam int size, @RequestParam int page){
        return roleService.getRoles(size,page);
    }

    @ApiResponse(
            headers = @Header(
                    name = "authorization",
                    required = true
            ),
            responseCode = "200"
    )
    @Operation(security = @SecurityRequirement(name = "jwtBearerAuth"))
    @PutMapping("/update")
    public ResponseEntity<StandardResponse<RoleEntity>>update(@Valid @RequestBody RoleDto roleDto,@RequestParam UUID id){
        return roleService.updateRole(roleDto,id);
    }
}


